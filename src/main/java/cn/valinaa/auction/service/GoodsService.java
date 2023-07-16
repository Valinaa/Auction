package cn.valinaa.auction.service;

import cn.valinaa.auction.bean.AuctionRecord;
import cn.valinaa.auction.bean.GoodAuction;
import cn.valinaa.auction.bean.SalerInfo;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {

     Object saveGoodInfo(GoodAuction goodAuction);

     Object savePic(MultipartFile file, Integer id);

     Object salerApply(SalerInfo salerInfo);

     Object getAuctionList(Integer currentPage, Integer pageSize);

     Object getGoodInfoById(Integer gid, Integer aid);
     Object getRecentRecordByGid(Integer gid);
     Object addShopCart(Integer aid, Integer gid);
     Object auction(AuctionRecord auctionRecord);

     Object getShoppingCartList(Integer aid, Integer curr, Integer pageSize);

     Object getAuctionRecord(Integer aid, Integer curr, Integer pageSize);

     Object getMyAuction(Integer aid, Integer curr, Integer pageSize);
     Object getAuctionRank(Integer gid,Integer curr, Integer pageSize);

     Object getOrderList(Integer aid, Integer curr, Integer pageSize);

     Object delMyGoods(Integer aid, Integer gid);

     Object searchAuctionList(String condition, Integer curr, Integer pageSize);

     Object getGoodsList(Integer curr, Integer pageSize);
}
