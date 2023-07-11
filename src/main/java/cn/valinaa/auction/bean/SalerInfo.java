package cn.valinaa.auction.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-04 12:12
 */
public class SalerInfo {

    private Integer id;
    private String salerName;
    private String busineName;
    private String busineAddress;
    private String salerEmail;
    private String busineContact;
    private String account;
    private String applyReason;
    // 默认0
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    public SalerInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public String getBusineName() {
        return busineName;
    }

    public void setBusineName(String busineName) {
        this.busineName = busineName;
    }

    public String getBusineAddress() {
        return busineAddress;
    }

    public void setBusineAddress(String busineAddress) {
        this.busineAddress = busineAddress;
    }

    public String getSalerEmail() {
        return salerEmail;
    }

    public void setSalerEmail(String salerEmail) {
        this.salerEmail = salerEmail;
    }

    public String getBusineContact() {
        return busineContact;
    }

    public void setBusineContact(String busineContact) {
        this.busineContact = busineContact;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }
}
