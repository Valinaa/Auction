package cn.valinaa.auction.controller;

import cn.valinaa.auction.bean.AuctionRecord;
import cn.valinaa.auction.bean.GoodAuction;
import cn.valinaa.auction.bean.SalerInfo;
import cn.valinaa.auction.service.GoodsService;
import cn.valinaa.auction.utils.RedisUtil;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-03 00:01
 */
@RestController
@Tag(name = "GoodsController", description = "商品相关接口")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping(value = "/savePic/{id}")
    @Operation(summary = "保存图片", description = "savePic")
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


    @PostMapping(value = "/saveGood")
    @Operation(summary = "保存商品信息", description = "saveGood")
    public  Object saveGoodInfo(@RequestBody GoodAuction goodAuction){
        return goodsService.saveGoodInfo(goodAuction);
    }


    @PostMapping(value = "/salerApply")
    @Operation(summary = "卖家申请", description = "salerApply")
    public  Object salerApply(@RequestBody SalerInfo salerInfo){
        return goodsService.salerApply(salerInfo);
    }


    @GetMapping(value = "/getAuctionList/{currentPage}/{pageSize}")
    @Operation(summary = "获取拍卖列表（分页）", description = "getAuctionList")
    public  Object getAuctionList(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        return goodsService.getAuctionList(currentPage, pageSize);
    }


    @GetMapping(value = "/getGoodInfoById/{gid}/{aid}")
    @Operation(summary = "根据商品id获取商品信息", description = "getGoodInfoById")
    public  Object getGoodInfoById(@PathVariable Integer gid, @PathVariable Integer aid){
        return goodsService.getGoodInfoById(gid, aid);
    }


    @GetMapping(value = "/addShopCart/{aid}/{gid}")
    @Operation(summary = "添加购物车", description = "addShopCart")
    public  Object addShopCart(@PathVariable Integer aid, @PathVariable Integer gid){
        return goodsService.addShopCart(aid, gid);
    }


    @PostMapping(value = "/auction")
    @Operation(summary = "竞拍", description = "auction")
    public  Object auction(@RequestBody AuctionRecord auctionRecord){
        return goodsService.auction(auctionRecord);
    }


    @GetMapping(value = "/getShoppingCartList/{aid}/{curr}/{pageSize}")
    @Operation(summary = "获取购物车列表", description = "getShoppingCartList")
    public  Object getShoppingCartList(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        return goodsService.getShoppingCartList(aid, curr, pageSize);
    }


    @GetMapping(value = "/getAuctionRecord/{aid}/{curr}/{pageSize}")
    @Operation(summary = "获取竞拍记录", description = "getAuctionRecord")
    public  Object getAuctionRecord(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        return goodsService.getAuctionRecord(aid, curr, pageSize);
    }


    @GetMapping(value = "/getMyAuction/{aid}/{curr}/{pageSize}")
    @Operation(summary = "获取我的拍卖", description = "getMyAuction")
    public  Object getMyAuction(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        return goodsService.getMyAuction(aid, curr, pageSize);
    }

    @GetMapping(value = "/getAuctionRank/{gid}")
    @Operation(summary = "获取拍卖排行", description = "getAuctionRank")
    public Object getAuctionRank(@PathVariable Integer gid){
        return goodsService.getAuctionRank(gid,1,100);
    }

    @GetMapping(value = "/getOrderList/{aid}/{curr}/{pageSize}")
    @Operation(summary = "获取订单列表", description = "getOrderList")
    public  Object getOrderList(@PathVariable Integer aid, @PathVariable Integer curr, @PathVariable Integer pageSize){
        return goodsService.getOrderList(aid, curr, pageSize);
    }


    @GetMapping(value = "/delMyGoods/{aid}/{gid}")
    @Operation(summary = "删除我的商品", description = "delMyGoods")
    public  Object delMyGoods(@PathVariable Integer aid, @PathVariable Integer gid){
        return goodsService.delMyGoods(aid, gid);
    }

    @PostMapping(value = "/searchAuctionList")
    @Operation(summary = "搜索拍卖列表", description = "searchAuctionList")
    public  Object searchAuctionList(String condition, Integer curr, Integer pageSize){
        return goodsService.searchAuctionList(condition, curr, pageSize);
    }

    @GetMapping(value = "/getGoodsList/{curr}/{pageSize}")
    @Operation(summary = "获取商品列表", description = "getGoodsList")
    public  Object getGoodsList(@PathVariable Integer curr, @PathVariable Integer pageSize){
        return goodsService.getGoodsList(curr, pageSize);
    }

}
