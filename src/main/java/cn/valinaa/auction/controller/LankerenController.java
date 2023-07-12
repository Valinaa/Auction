package cn.valinaa.auction.controller;

import cn.valinaa.auction.service.LankerenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-06 01:42
 */
@RestController
@Tag(name = "LankerenController", description = "管理员相关接口")
public class LankerenController {

    @Autowired
    private LankerenService lankerenService;

    @GetMapping(value = "/getUserList")
    @Operation(summary = "获取用户列表",description = "getUserList")
    public  Object getUserList( Integer page,  Integer limit){
        return lankerenService.getUserList(page, limit);
    }

    @GetMapping(value = "/getGoodAuctionList")
    @Operation(summary = "获取商品列表",description = "getGoodAuctionList")
    public  Object getGoodAuctionList(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        return lankerenService.getGoodAuctionList(curr, pageSize);
    }

    @GetMapping(value = "/getAuctionRecordList")
    @Operation(summary = "获取拍卖记录列表",description = "getAuctionRecordList")
    public  Object getAuctionRecordList(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        return lankerenService.getAuctionRecordList(curr, pageSize);
    }

    @GetMapping(value = "/getOrderList")
    @Operation(summary = "获取订单列表",description = "getOrderList")
    public  Object getOrderList(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        return lankerenService.getOrderList(curr, pageSize);
    }

    @GetMapping(value = "/getSalerApply")
    @Operation(summary = "获取商家申请列表",description = "getSalerApply")
    public  Object getSalerApply(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        return lankerenService.getSalerApply(curr, pageSize);
    }

    @GetMapping(value = "/forbiddenAccount/{aid}/{status}")
    @Operation(summary = "禁用账号",description = "forbiddenAccount")
    public Object forbiddenAccount(@PathVariable Integer aid, @PathVariable Integer status){
        return lankerenService.forbiddenAccount(aid, status);
    }

    @GetMapping(value = "/pswReset/{aid}")
    @Operation(summary = "重置密码",description = "pswReset")
    public Object pswReset(@PathVariable Integer aid){
        return lankerenService.pswReset(aid);
    }


    @GetMapping(value = "/delAccount/{aid}")
    @Operation(summary = "删除账号",description = "delAccount")
    public Object delAccount(@PathVariable Integer aid){
        return lankerenService.delAccount(aid);
    }

    @GetMapping(value = "/adsalerApply/{sid}/{status}")
    @Operation(summary = "商家申请",description = "adsalerApply")
    public Object adsalerApply(@PathVariable Integer sid, @PathVariable Integer status){
        return lankerenService.salerApply(sid, status);
    }

    @GetMapping(value = "/identityManagerInfoList")
    @Operation(summary = "获取身份管理列表",description = "identityManagerInfoList")
    public Object identityManagerInfoList(){
        return lankerenService.identityManagerInfoList();
    }

    @GetMapping(value = "/updateIndentityInfo/{aid}/{identity}")
    @Operation(summary = "更新身份信息",description = "updateIndentityInfo")
    public Object updateIndentityInfo(@PathVariable Integer aid, @PathVariable Integer identity){
        return lankerenService.updateIndentityInfo(aid, identity);
    }
}
