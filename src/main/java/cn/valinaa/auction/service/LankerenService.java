package cn.valinaa.auction.service;

public interface LankerenService {


    public Object getUserList( Integer page, Integer limit);

    public Object getGoodAuctionList( Integer curr, Integer pageSize);

    public Object getAuctionRecordList( Integer curr, Integer pageSize);

    public Object getOrderList( Integer curr, Integer pageSize);

    public Object getSalerApply( Integer curr, Integer pageSize);

    public Object forbiddenAccount( Integer aid, Integer status);

    public Object pswReset( Integer aid );

    public Object delAccount( Integer aid );

    public Object salerApply( Integer sid, Integer status );

    public Object identityManagerInfoList();

    public Object updateIndentityInfo( Integer aid, Integer identity);


}
