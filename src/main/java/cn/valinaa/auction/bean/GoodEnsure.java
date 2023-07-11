package cn.valinaa.auction.bean;

/**
 * @author Valinaa
 * @Description:
 * @Date: 2023-07-03 16:11
 */
public class GoodEnsure {

    private Integer gid;
    private Integer packMail;
    private Integer oimei;
    private Integer ensure;

    public GoodEnsure() {
    }

    public GoodEnsure(GoodAuction goodAuction) {
        this.gid = goodAuction.getId();
        this.packMail = "on".equals(goodAuction.getPackMail()) ? 1 : 0;
        this.oimei = "on".equals(goodAuction.getOimei()) ? 1 : 0;
        this.ensure = "on".equals(goodAuction.getEnsure()) ? 1 : 0;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getPackMail() {
        return packMail;
    }

    public void setPackMail(Integer packMail) {
        this.packMail = packMail;
    }

    public Integer getOimei() {
        return oimei;
    }

    public void setOimei(Integer oimei) {
        this.oimei = oimei;
    }

    public Integer getEnsure() {
        return ensure;
    }

    public void setEnsure(Integer ensure) {
        this.ensure = ensure;
    }
}
