package cn.valinaa.auction.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-03 09:07
 */
public class GoodAuction {

    private Integer id;
    private String goodName;
    private Integer goodType;
    private Double startPrice;
    private Double pricePlus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Integer accountId;
    private String goodsDec;
    private String pic;
    private Integer status;
    private String salerName;
    private Double nowPrice;
    private String packMail;
    private String oimei;
    private String ensure;



    public GoodAuction() {
    }


    public String getPackMail() {
        return packMail;
    }

    public void setPackMail(String packMail) {
        this.packMail = packMail;
    }

    public String getOimei() {
        return oimei;
    }

    public void setOimei(String oimei) {
        this.oimei = oimei;
    }

    public String getEnsure() {
        return ensure;
    }

    public void setEnsure(String ensure) {
        this.ensure = ensure;
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

    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getPricePlus() {
        return pricePlus;
    }

    public void setPricePlus(Double pricePlus) {
        this.pricePlus = pricePlus;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getGoodsDec() {
        return goodsDec;
    }

    public void setGoodsDec(String goodsDec) {
        this.goodsDec = goodsDec;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "GoodAuction{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", goodType='" + goodType + '\'' +
                ", startPrice=" + startPrice +
                ", pricePlus=" + pricePlus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", accountId=" + accountId +
                ", goodsDec='" + goodsDec + '\'' +
                ", pic='" + pic + '\'' +
                ", status=" + status +
                ", salerName='" + salerName + '\'' +
                ", nowPrice=" + nowPrice +
                ", packMail='" + packMail + '\'' +
                ", oimei='" + oimei + '\'' +
                ", ensure='" + ensure + '\'' +
                '}';
    }
}
