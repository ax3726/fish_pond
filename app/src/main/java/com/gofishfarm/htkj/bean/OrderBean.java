package com.gofishfarm.htkj.bean;

/**
 * @author：MrHu
 * @Date ：2019-01-10
 * @Describtion ：
 */
public class OrderBean {

        private String orderInfo;
        private String prepayid;
        private String package_str;
        private String noncestr;
        private String timestamp;
        private String sign;

    public String getOrderInfo() {
        return orderInfo == null ? "" : orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPrepayid() {
        return prepayid == null ? "" : prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage_str() {
        return package_str == null ? "" : package_str;
    }

    public void setPackage_str(String package_str) {
        this.package_str = package_str;
    }

    public String getNoncestr() {
        return noncestr == null ? "" : noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp == null ? "" : timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
