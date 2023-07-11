package cn.valinaa.auction.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface LankerenMapper {

    @Select("SELECT id, name, account, identity, email, phone, reg_time, `status` from account " +
            "LEFT JOIN account_info on id = aid")
    List<Map<String, Object>> getUserList();

    @Select("SELECT id, good_name, good_type, saler_name, start_price, price_plus, now_price, end_time, `status` from goods_auction ")
    List<Map<String, Object>> getGoodAuctionList();

    @Select("SELECT id, good_name, start_price, now_price, my_plus, account_name, saler_id, `status`, start_time, end_time, create_time from auction_record ")
    List<Map<String, Object>> getAuctionRecordList();

//    @Select("SELECT order_id, account, good_name, start_price, end_price, account_name, address, create_time, `status` from `order`")
    @Select("SELECT order_id, o.account, good_name, start_price, end_price, account_name, address, create_time, `status`, saler_id, goods_id, (SELECT busine_address from saler_info WHERE id = saler_id) salerAddress from `order` o")
    List<Map<String, Object>> getOrderList();

    @Select("SELECT id, busine_name, saler_name, busine_contact, saler_email, apply_reason, account, `status` from saler_info ")
    List<Map<String, Object>> getSalerApply();

    @Update("UPDATE `auctiononlinesys`.`account` SET `status` = #{status} WHERE `id` = #{aid} ")
    Integer forbiddenAccount(Integer aid, Integer status);

    @Update("UPDATE `auctiononlinesys`.`account` SET `password` = #{DefualtPsw} WHERE `id` = #{aid}")
    Integer pswReset(String DefualtPsw, Integer aid);


    @Delete("DELETE FROM `auctiononlinesys`.`account` WHERE `id` = #{aid}")
    Integer delAccount(Integer aid);


    @Update("UPDATE `auctiononlinesys`.`saler_info` SET `status` = #{status} WHERE `id` = #{sid}")
    Integer updateSalerInfo(Integer status, Integer sid);


    @Select("SELECT `dec`, id from identity where identity_name != 'admin'")
    List<Map<String, Object>> identityManagerInfoList();


    @Update("UPDATE `auctiononlinesys`.`account` SET `identity` = #{identity} WHERE `id` = #{aid}")
    Integer updateIndentityInfo(Integer identity, Integer aid);

    @Select("SELECT a.id from saler_info s " +
            "LEFT JOIN account a on s.account = a.account " +
            "where s.id = #{sid}")
    Integer getAccountbySalerInfo(Integer sid);
}
