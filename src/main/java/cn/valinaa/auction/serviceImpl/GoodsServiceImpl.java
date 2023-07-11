package cn.valinaa.auction.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.valinaa.auction.bean.*;
import cn.valinaa.auction.enums.Constant;
import cn.valinaa.auction.mapper.AccountMapper;
import cn.valinaa.auction.mapper.GoodsMapper;
import cn.valinaa.auction.service.GoodsService;
import cn.valinaa.auction.utils.DealOld;
import cn.valinaa.auction.utils.MFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-03 09:05
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AccountMapper accountMapper;

    private static volatile Map<Integer, Object> picPathWrapper = new ConcurrentHashMap<>();


    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Object saveGoodInfo(GoodAuction goodAuction) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        boolean b= goodAuction.getGoodType() == null || goodAuction.getStartPrice() == null || goodAuction.getPricePlus() == null;
        if(b){ return  res;}
        Integer id = goodAuction.getAccountId();
        if(goodsMapper.getIdentity(id) < Constant.SalerUser){
            return res;
        }
        goodAuction.setPic((String)picPathWrapper.get(id));
        picPathWrapper.remove(id);
        goodAuction.setStartTime(LocalDateTime.now());
        goodAuction.setNowPrice(goodAuction.getStartPrice());
        int f = goodsMapper.saveGoodInfo(goodAuction);
        GoodEnsure goodEnsure = new GoodEnsure(goodAuction);
        goodsMapper.saveGoodEnsure(goodEnsure);
        if(f == 0){
            return res;
        }
        res.put("msg", "ok");
        return res;
    }

    @Override
    public Object savePic(MultipartFile file, Integer id) {
        JSONObject res = new JSONObject();
        String picPath = null;
        try {
            picPath = "/picFiles/" + MFileUtil.sacePicFile(file);
            res.put("src",  picPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        picPathWrapper.put(id, picPath);
        return res;
    }

    @Override
    public Object salerApply(SalerInfo salerInfo) {
        // 参数懒得检验了....
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if("null".equals(salerInfo.getAccount())  || "null".equals(salerInfo.getBusineName()) ){return  res;}
        int exists = goodsMapper.theUserIsExists(salerInfo.getAccount());
        System.out.println(exists);
        if(exists == 1){
            res.put("msg", "exists");
            return res;
        }
        salerInfo.setApplyTime(LocalDateTime.now());
        int f = goodsMapper.saveSalerInfo(salerInfo);
        if(f == 1){
            res.put("msg", "ok");
        }
        return res;
    }

    @Override
    public Object getAuctionList(Integer currentPage, Integer pageSize) {
        JSONObject res0 = new JSONObject();
        JSONObject res1 = new JSONObject();
        try {
            PageHelper.startPage(currentPage, pageSize);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowTime = formatter.format(LocalDateTime.now());
            List<GoodCard> list = goodsMapper.getAuctionList(nowTime);
            PageInfo<GoodCard> pageInfo = new PageInfo<>(list);
            res0.put("msg", "ok");
            res1.put("totalSize", pageInfo.getPages());
            res1.put("AuctionNums", goodsMapper.getAucNums(nowTime));
            res1.put("AuctionList", list);
            res0.put("data", res1);
            new Thread(new DealOld(goodsMapper)).start();
        }catch (Exception e){
            res0.put("msg", "f");
            System.out.println(e);
        }
        return res0;
    }

    @Override
    public Object getGoodInfoById(Integer gid, Integer aid) {
        JSONObject res = new JSONObject();
        Map<String, Object> goodInfo = goodsMapper.getGoodInfoById(gid, aid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        goodInfo.put("start_time", formatter.format(((Timestamp) goodInfo.get("start_time")).toLocalDateTime()));
        goodInfo.put("end_time", formatter.format(((Timestamp) goodInfo.get("end_time")).toLocalDateTime()));

        res.put("GoodInfo", goodInfo);
        res.put("msg", "ok");

        return res;
    }

    @Override
    public Object addShopCart(Integer aid, Integer gid) {
        JSONObject res = new JSONObject();
        if(aid == null || gid == null){
            res.put("msg", "f");
            return res;
        }
        try {
            Integer exists = goodsMapper.shoppingCartAddDel(aid, gid);
            if(exists > 0){
                res.put("msg", "cancle");
                return res;
            }else if(exists == 0){
                int i = goodsMapper.saveShoppingCart(aid, gid);
                if(i > 0){
                    res.put("msg", "ok");
                }
            }
        }catch (Exception e){
            res.put("msg", "f");
            return res;
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Object auction(AuctionRecord auctionRecord) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        boolean b = auctionRecord == null || auctionRecord.getMyPlus() == null ||
                auctionRecord.getMyPlus() < goodsMapper.getMixPricePlus(auctionRecord.getGid()) ||
                goodsMapper.getGoodsStatus(auctionRecord.getGid()) != 1;
        if(b)
        { return res; }
        auctionRecord.setNowPrice(auctionRecord.getStartPrice() + auctionRecord.getMyPlus());
        auctionRecord.setCreateTime(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        auctionRecord.setEndTime(LocalDateTime.parse(auctionRecord.geteTime(), formatter));
        auctionRecord.setStartTime(LocalDateTime.parse(auctionRecord.getsTime(), formatter));
        Integer i = goodsMapper.saveAuctionRecord(auctionRecord);
        goodsMapper.updateNowPrice(auctionRecord.getGid(), auctionRecord.getNowPrice());
        if(i != 1){return res;}
        res.put("msg", "ok");
        return res;
    }

    @Override
    public Object getShoppingCartList(Integer aid, Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(aid, curr, pageSize, goodsMapper.getShoppingCartList(aid), "list");
    }

    @Override
    public Object getAuctionRecord(Integer aid, Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(aid, curr, pageSize, goodsMapper.getAuctionRecord(aid) , "list");
    }

    private Object theSame(Integer aid, Integer curr, Integer pageSize, List<Map<String, Object>> list, String name){
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(aid == null){ return res; }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        res.put(name, list);
        res.put("msg", "ok");
        res.put("totalSize", pageInfo.getPages());
        return res;
    }

    @Override
    public Object getMyAuction(Integer aid, Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(aid, curr, pageSize, goodsMapper.getMyAuction(aid) , "list");
    }

    @Override
    public Object getOrderList(Integer aid, Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(aid, curr, pageSize, goodsMapper.getOrderList(aid) , "list");
    }

    @Override
    public Object delMyGoods(Integer aid, Integer gid) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(aid == null || gid == null){
            return res;
        }
        Integer f = goodsMapper.delMyGoods(aid, gid);
        if(f != 0){
            res.put("msg", "ok");
        }
        return res;
    }

    @Override
    public Object searchAuctionList(String condition, Integer curr, Integer pageSize) {
        JSONObject res0 = new JSONObject();
        JSONObject res1 = new JSONObject();
        try {
            PageHelper.startPage(curr, pageSize);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowTime = formatter.format(LocalDateTime.now());
            List<GoodCard> list = goodsMapper.searchAuctionList(nowTime, "%"+condition+"%");
            PageInfo<GoodCard> pageInfo = new PageInfo<>(list);
            res0.put("msg", "ok");
            res1.put("totalSize", pageInfo.getPages());
            res1.put("AuctionNums", goodsMapper.getAucNums(nowTime));
            res1.put("AuctionList", list);
            res0.put("data", res1);
        }catch (Exception e){
            res0.put("msg", "f");
            System.out.println(e);
        }
        return res0;
    }


}
