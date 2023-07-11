package cn.valinaa.auction.controller;

import com.alibaba.fastjson.JSONObject;
import cn.valinaa.auction.bean.AuctionRecord;
import cn.valinaa.auction.bean.GoodAuction;
import cn.valinaa.auction.bean.SalerInfo;
import cn.valinaa.auction.service.AccountService;
import cn.valinaa.auction.service.GoodsService;
import cn.valinaa.auction.serviceImpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.spi.http.HttpContext;
import java.util.List;
import java.util.Map;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-03 00:01
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    @RequestMapping(value = "/savePic/{id}", method = RequestMethod.POST)
    public  Object savePic(MultipartFile file, @PathVariable Integer id){
        JSONObject r = new JSONObject();
       try {
           Object res =  goodsService.savePic(file, id);
           r.put("data", res);
           r.put("code", 0);
           r.put("msg", "ok");
       }catch (Exception e){
           r.put("msg", "f");
           return r;
       }
        return r;
    }


    @RequestMapping(value = "/saveGood", method = RequestMethod.POST)
    public  Object saveGoodInfo(@RequestBody GoodAuction goodAuction){
        Object res =  goodsService.saveGoodInfo(goodAuction);
        return res;
    }


    @RequestMapping(value = "/salerApply", method = RequestMethod.POST)
    public  Object salerApply(@RequestBody SalerInfo salerInfo){
        Object res =  goodsService.salerApply(salerInfo);
        return res;
    }


    @RequestMapping(value = "/getAuctionList/{currentPage}/{pageSize}", method = RequestMethod.GET)
    public  Object getAuctionList(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        Object res =  goodsService.getAuctionList(currentPage, pageSize);
        return res;
    }


    @RequestMapping(value = "/getGoodInfoById/{gid}/{aid}", method = RequestMethod.GET)
    public  Object getGoodInfoById(@PathVariable Integer gid, @PathVariable Integer aid){
        Object res =  goodsService.getGoodInfoById(gid, aid);
        return res;
    }


    @RequestMapping(value = "/addShopCart/{aid}/{gid}", method = RequestMethod.GET)
    public  Object addShopCart(@PathVariable Integer aid, @PathVariable Integer gid){
        Object res =  goodsService.addShopCart(aid, gid);
        return res;
    }


    @RequestMapping(value = "/auction", method = RequestMethod.POST)
    public  Object auction(@RequestBody AuctionRecord auctionRecord){
        Object res =  goodsService.auction(auctionRecord);
        return res;
    }


    @RequestMapping(value = "/getShoppingCartList/{aid}/{curr}/{pageSize}", method = RequestMethod.GET)
    public  Object getShoppingCartList(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        Object res =  goodsService.getShoppingCartList(aid, curr, pageSize);
        return res;
    }


    @RequestMapping(value = "/getAuctionRecord/{aid}/{curr}/{pageSize}", method = RequestMethod.GET)
    public  Object getAuctionRecord(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        Object res =  goodsService.getAuctionRecord(aid, curr, pageSize);
        return res;
    }


    @RequestMapping(value = "/getMyAuction/{aid}/{curr}/{pageSize}", method = RequestMethod.GET)
    public  Object getMyAuction(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        Object res =  goodsService.getMyAuction(aid, curr, pageSize);
        return res;
    }



    @RequestMapping(value = "/getOrderList/{aid}/{curr}/{pageSize}", method = RequestMethod.GET)
    public  Object getOrderList(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        Object res =  goodsService.getOrderList(aid, curr, pageSize);
        return res;
    }


    @RequestMapping(value = "/delMyGoods/{aid}/{gid}", method = RequestMethod.GET)
    public  Object delMyGoods(@PathVariable Integer aid, @PathVariable Integer gid){
        Object res =  goodsService.delMyGoods(aid, gid);
        return res;
    }

    @RequestMapping(value = "/searchAuctionList", method = RequestMethod.POST)
    public  Object searchAuctionList(String condition, Integer curr, Integer pageSize){
        Object res =  goodsService.searchAuctionList(condition, curr, pageSize);
        return res;
    }



}
