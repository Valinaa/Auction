package cn.valinaa.auction.service;

 public interface LankerenService {


     Object getUserList( Integer page, Integer limit);

     Object getGoodAuctionList( Integer curr, Integer pageSize);

     Object getAuctionRecordList( Integer curr, Integer pageSize);

     Object getOrderList( Integer curr, Integer pageSize);

     Object getSalerApply( Integer curr, Integer pageSize);

     Object forbiddenAccount( Integer aid, Integer status);

     Object pswReset( Integer aid );

     Object delAccount( Integer aid );

     Object salerApply( Integer sid, Integer status );

     Object identityManagerInfoList();

     Object updateIndentityInfo( Integer aid, Integer identity);


}
