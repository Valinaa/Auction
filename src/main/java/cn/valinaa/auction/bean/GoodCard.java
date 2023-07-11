package cn.valinaa.auction.bean;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-04 17:37
 */
public class GoodCard {

    private Integer id;
    private String goodName;
    private String pic;
    private String salerName;
    private Double nowPrice;
    /**
     *  竞拍人数.
     */
    private Integer aucNum;

    public GoodCard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Integer getAucNum() {
        return aucNum;
    }

    public void setAucNum(Integer aucNum) {
        this.aucNum = aucNum;
    }
}
