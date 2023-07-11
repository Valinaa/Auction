package cn.valinaa.auction.controller;

import cn.valinaa.auction.service.LankerenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-06 01:42
 */
@RestController
public class LankerenController {

    @Autowired
    private LankerenService lankerenService;

    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public  Object getUserList( Integer page,  Integer limit){
        Object res =  lankerenService.getUserList(page, limit);
        return res;
    }

    @RequestMapping(value = "/getGoodAuctionList", method = RequestMethod.GET)
    public  Object getGoodAuctionList(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        Object res =  lankerenService.getGoodAuctionList(curr, pageSize);
        return res;
    }

    @RequestMapping(value = "/getAuctionRecordList", method = RequestMethod.GET)
    public  Object getAuctionRecordList(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        Object res =  lankerenService.getAuctionRecordList(curr, pageSize);
        return res;
    }

    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    public  Object getOrderList(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        Object res =  lankerenService.getOrderList(curr, pageSize);
        return res;
    }

    @RequestMapping(value = "/getSalerApply", method = RequestMethod.GET)
    public  Object getSalerApply(@RequestParam("page") Integer curr, @RequestParam("limit") Integer pageSize){
        Object res =  lankerenService.getSalerApply(curr, pageSize);
        return res;
    }

    @RequestMapping(value = "/forbiddenAccount/{aid}/{status}", method = RequestMethod.GET)
    public Object forbiddenAccount(@PathVariable Integer aid, @PathVariable Integer status){
        Object res =  lankerenService.forbiddenAccount(aid, status);
        return res;
    }

    @RequestMapping(value = "/pswReset/{aid}", method = RequestMethod.GET)
    public Object pswReset(@PathVariable Integer aid){
        Object res =  lankerenService.pswReset(aid);
        return res;
    }


    @RequestMapping(value = "/delAccount/{aid}", method = RequestMethod.GET)
    public Object delAccount(@PathVariable Integer aid){
        Object res =  lankerenService.delAccount(aid);
        return res;
    }

    @RequestMapping(value = "/adsalerApply/{sid}/{status}", method = RequestMethod.GET)
    public Object adsalerApply(@PathVariable Integer sid, @PathVariable Integer status){
        Object res =  lankerenService.salerApply(sid, status);
        return res;
    }

    @RequestMapping(value = "/identityManagerInfoList", method = RequestMethod.GET)
    public Object identityManagerInfoList(){
        Object res =  lankerenService.identityManagerInfoList();
        return res;
    }

    @RequestMapping(value = "/updateIndentityInfo/{aid}/{identity}", method = RequestMethod.GET)
    public Object updateIndentityInfo(@PathVariable Integer aid, @PathVariable Integer identity){
        Object res =  lankerenService.updateIndentityInfo(aid, identity);
        return res;
    }


}
