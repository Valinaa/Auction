package cn.valinaa.auction.mapper;

import cn.valinaa.auction.bean.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {

    @Insert("INSERT INTO `goods_auction`(`good_name`, `good_type`, `start_price`, `price_plus`, `start_time`, `end_time`, `account_id`, " +
            "`goods_dec`, `pic`, `status`, `saler_name`, `now_price`) " +
            "VALUES (#{goodName}, #{goodType}, #{startPrice}, #{pricePlus}, #{startTime}, #{endTime}, #{accountId}, " +
            "#{goodsDec}, #{pic}, #{status}, #{salerName}, #{nowPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveGoodInfo(GoodAuction goodAuction);


    @Insert("INSERT INTO `goods_ensure`(`gid`, `pack_mail`, `oimei`, `ensure`) VALUES (#{gid}, #{packMail}, #{oimei}, #{ensure})")
    int saveGoodEnsure(GoodEnsure goodEnsure);


    @Select("SELECT identity from account where id = #{id}")
    Integer getIdentity(Integer id);

    @Insert("INSERT INTO `saler_info`(`busine_name`, `saler_name`, `busine_address`, `busine_contact`, `saler_email`, `apply_reason`, " +
            "`account`, `apply_time`) VALUES " +
            "(#{busineName}, #{salerName}, #{busineAddress}, #{busineContact}, #{salerEmail}, #{applyReason}, #{account}, #{applyTime})")
    int saveSalerInfo(SalerInfo salerInfo);


    @Select("select count(*) from saler_info where account = #{account}")
    int theUserIsExists(String account);


    @Select("SELECT g.id, g.good_name, g.pic, g.saler_name, g.now_price,(SELECT count(*) ttt from auction_record WHERE gid = g.id) aucNum from goods_auction g " +
            "WHERE end_time > #{nowTime}  and `status` = 1 " +
            "ORDER BY end_time ASC")
    List<GoodCard> getAuctionList(String nowTime);

    @Select("SELECT (SELECT count(*) from goods_auction WHERE `status` = 1 and end_time > #{nowTime} " +
            "and start_time < #{nowTime}) allowAuc, " +
            "(SELECT count(*) from goods_auction WHERE `status` = 1 and start_time > #{nowTime}) unOpen")
    Map<String, Integer> getAucNums(String nowTime);


    /**
     *  goodInfo 页面
     * @param gid aid
     * @return
     */
    @Select("SELECT a.id, a.good_name, a.start_price, a.start_time, a.pic, a.price_plus, a.end_time, a.account_id, " +
            "a.goods_dec, a.`status` , a.now_price, a.saler_name, b.goodType, e.pack_mail, e.oimei, e.ensure, " +
            "(select count(*) from shoppingcart where aid = #{aid} and gid = #{gid}) Exshoppingcart FROM goods_auction a " +
            " LEFT JOIN goodtype b ON b.id = a.good_type " +
            " LEFT JOIN goods_ensure e ON e.gid = a.id " +
            "WHERE " +
            " a.id = #{gid}")
    Map<String, Object> getGoodInfoById(Integer gid, Integer aid);


    @Select("SELECT count(*) from shoppingcart where aid = #{aid} and gid = #{gid}")
    Integer shoppingCartIsExists(Integer aid, Integer gid);

    @Insert("INSERT INTO `auctiononlinesys`.`shoppingcart`(`gid`, `aid`) VALUES (#{gid}, #{aid})")
    Integer saveShoppingCart(Integer aid, Integer gid);


    @Insert("INSERT INTO `auction_record`(`good_name`, `start_price`, `now_price`, `my_plus`, `account_id`, `account_name`, `status`, " +
            "`end_time`, `start_time`, `saler_id`, `gid`, `create_time`) " +
            "VALUES (#{goodName}, #{startPrice}, #{nowPrice}, #{myPlus}, #{accountId}, #{accountName}, #{status}, #{endTime}, #{startTime}, " +
            "#{salerId}, #{gid}, #{createTime})")
    Integer saveAuctionRecord(AuctionRecord auctionRecord);

    @Select("SELECT price_plus from goods_auction where id = #{gid}")
    Double getMixPricePlus(Integer gid);

    @Update("UPDATE `goods_auction` SET `now_price` = #{nowPrice} WHERE `id` = #{gid}")
    Integer updateNowPrice(Integer gid, double nowPrice);

    @Delete("DELETE from shoppingcart where gid = #{gid} and aid = #{aid}")
    Integer shoppingCartAddDel(Integer aid, Integer gid);


    @Select("SELECT g.id, g.good_name, g.now_price, g.`status` from goods_auction g " +
            "LEFT JOIN shoppingcart s on g.id = s.gid " +
            "where s.aid = #{aid}")
    List<Map<String , Object>> getShoppingCartList(Integer aid);


    @Select("SELECT a.id , a.good_name, a.now_price, a.my_plus, a.`status`, a.gid, a.create_time from auction_record a WHERE account_id = #{aid}")
    List<Map<String, Object>> getAuctionRecord(Integer aid);

    @Select("SELECT g.id, g.good_name, g.end_time, g.start_price, g.now_price, g.`status` , (SELECT o.`status` from `order` o WHERE o.goods_id = g.id ) orderStatus " +
            "from goods_auction g where account_id = #{aid}")
    List<Map<String , Object>> getMyAuction(Integer aid);


    @Select("SELECT o.order_id, o.good_name, o.end_price, o.create_time, o.`status` from `order`o where account_id = #{aid}")
    List<Map<String , Object>> getOrderList(Integer aid);


    @Delete("DELETE from goods_auction WHERE account_id = #{aid} and id = #{gid}")
    Integer delMyGoods(Integer aid, Integer gid);


    @Select("SELECT id from goods_auction where end_time < #{nowTime} and `status` = 1")
    List<Integer> getOldId(String nowTime);

    @Update("UPDATE `goods_auction` SET `status` = '0' WHERE `id` in ( ${ids} )")
    Integer updateOldGoods(String ids);

    @Select("SELECT `status` from goods_auction where id = #{gid}")
    Integer getGoodsStatus(Integer gid);

    @Select("SELECT g.id, g.good_name, g.pic, g.saler_name, g.now_price,(SELECT count(*) ttt from auction_record WHERE gid = g.id) aucNum from goods_auction g " +
            "WHERE end_time > #{nowTime}  and `status` = 1 and good_name like #{condition}" +
            "ORDER BY end_time ASC")
    List<GoodCard> searchAuctionList(String nowTime, String condition);



}
