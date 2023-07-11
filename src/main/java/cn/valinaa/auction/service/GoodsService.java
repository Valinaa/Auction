package cn.valinaa.auction.service;

import cn.valinaa.auction.bean.AuctionRecord;
import cn.valinaa.auction.bean.GoodAuction;
import cn.valinaa.auction.bean.SalerInfo;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {

    public  Object saveGoodInfo(GoodAuction goodAuction);

    public Object savePic(MultipartFile file, Integer id);

    public Object salerApply(SalerInfo salerInfo);

    public Object getAuctionList(Integer currentPage, Integer pageSize);

    public Object getGoodInfoById(Integer gid, Integer aid);

    public Object addShopCart(Integer aid, Integer gid);

    public Object auction(AuctionRecord auctionRecord);

    public Object getShoppingCartList(Integer aid, Integer curr, Integer pageSize);

    public Object getAuctionRecord(Integer aid, Integer curr, Integer pageSize);

    public Object getMyAuction(Integer aid, Integer curr, Integer pageSize);

    public Object getOrderList(Integer aid, Integer curr, Integer pageSize);

    public Object delMyGoods(Integer aid, Integer gid);

    public Object searchAuctionList(String condition, Integer curr, Integer pageSize);

}
