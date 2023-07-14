package cn.valinaa.auction.serviceImpl;


import cn.valinaa.auction.bean.Result;
import cn.valinaa.auction.enums.ResultCodeEnum;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.valinaa.auction.enums.Constant;
import cn.valinaa.auction.mapper.LankerenMapper;
import cn.valinaa.auction.service.LankerenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-06 01:42
 */
@Service
public class LankerenServiceImpl implements LankerenService {

    @Autowired
    private  LankerenMapper lankerenMapper;

    @Override
    public Object getUserList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return theSame(lankerenMapper.getUserList());
    }

    @Override
    public Object getGoodAuctionList(Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(lankerenMapper.getGoodAuctionList());
    }

    @Override
    public Object getAuctionRecordList(Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(lankerenMapper.getAuctionRecordList());
    }

    @Override
    public Object getOrderList(Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame( lankerenMapper.getOrderList());
    }

    @Override
    public Object getSalerApply(Integer curr, Integer pageSize) {
        PageHelper.startPage(curr, pageSize);
        return theSame(lankerenMapper.getSalerApply());
    }

    @Override
    public Object forbiddenAccount(Integer aid, Integer status) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(aid != null && status != null) {
            status = status == 1 ? 0 : 1;
            lankerenMapper.forbiddenAccount(aid, status);
            res.put("msg", "ok");
            res.put("Leaststatus", status);
        }
        return res;
    }

    @Override
    public Object pswReset(Integer aid) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(aid == null){ return  res; }
        lankerenMapper.pswReset(Constant.DefualtPsw, aid);
        res.put("msg", "ok");
        return res;
    }

    @Override
    public Object delAccount(Integer aid) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        lankerenMapper.delAccount(aid);
        res.put("msg", "ok");
        return res;
    }

    @Override
    public Object salerApply(Integer sid, Integer status) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(status == null) {return  res;}
        Integer f = lankerenMapper.updateSalerInfo(status, sid);
        //如果同意卖家， 身份跟着改
        if(status == 1){
            Integer aid = lankerenMapper.getAccountbySalerInfo(sid);
            updateIndentityInfo(aid, Constant.SalerUser);
        }
        if(f != 0){
            res.put("msg", "ok");
        }
        return res;
    }

    @Override
    public Object identityManagerInfoList() {
        JSONObject res = new JSONObject();
        List<Map<String, Object>> list = lankerenMapper.identityManagerInfoList();
        res.put("msg", "ok");
        res.put("identityNameList", list);
        return res;
    }

    @Override
    public Object updateIndentityInfo(Integer aid, Integer identity) {
        JSONObject res = new JSONObject();
        res.put("msg", "f");
        if(aid == null || identity == null){  return  res; }
        try {
            // 修改成卖家的时候还有给卖家的那里添加一下东西
            lankerenMapper.updateIndentityInfo(identity, aid);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return res;
        }
        res.put("msg", "ok");
        return res;
    }


    private Result<Map<String,Object>> theSame(List<Map<String, Object>> list){
        Map<String,Object> res1 = new HashMap<>();
        try {
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
            res1.put("data", list);
            res1.put("count", pageInfo.getTotal());
            return Result.success(res1);
        }catch (Exception e){
            res1.put("count", 0);
            return  Result.failure(res1,ResultCodeEnum.GET_FAILED);
        }
    }

}
