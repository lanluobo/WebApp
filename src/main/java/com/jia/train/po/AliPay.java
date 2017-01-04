package com.jia.train.po;

/**
 * Created by jiaxl on 2016/12/31.
 *
 * 支付宝支付参数，html代码解析
 */
public class AliPay {
    private String ord_time_ext;
    private String ord_name;
    private String ord_desc;
    private String notify_url;
    private String sign;
    private String ord_amt;
    private String service;
    private String partner;
    private String dispatch_cluster_target;
    private String ord_cur;
    private String ord_pmt_timeout;
    private String req_access_type;
    private String _input_charset;
    private String ord_id_ext;
    private String sign_type;
    private String return_url;
    private String req_client_ip_ext;

    public String getOrd_time_ext() {
        return ord_time_ext;
    }

    public void setOrd_time_ext(String ord_time_ext) {
        this.ord_time_ext = ord_time_ext;
    }

    public String getOrd_name() {
        return ord_name;
    }

    public void setOrd_name(String ord_name) {
        this.ord_name = ord_name;
    }

    public String getOrd_desc() {
        return ord_desc;
    }

    public void setOrd_desc(String ord_desc) {
        this.ord_desc = ord_desc;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrd_amt() {
        return ord_amt;
    }

    public void setOrd_amt(String ord_amt) {
        this.ord_amt = ord_amt;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getDispatch_cluster_target() {
        return dispatch_cluster_target;
    }

    public void setDispatch_cluster_target(String dispatch_cluster_target) {
        this.dispatch_cluster_target = dispatch_cluster_target;
    }

    public String getOrd_cur() {
        return ord_cur;
    }

    public void setOrd_cur(String ord_cur) {
        this.ord_cur = ord_cur;
    }

    public String getOrd_pmt_timeout() {
        return ord_pmt_timeout;
    }

    public void setOrd_pmt_timeout(String ord_pmt_timeout) {
        this.ord_pmt_timeout = ord_pmt_timeout;
    }

    public String getReq_access_type() {
        return req_access_type;
    }

    public void setReq_access_type(String req_access_type) {
        this.req_access_type = req_access_type;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public String getOrd_id_ext() {
        return ord_id_ext;
    }

    public void setOrd_id_ext(String ord_id_ext) {
        this.ord_id_ext = ord_id_ext;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getReq_client_ip_ext() {
        return req_client_ip_ext;
    }

    public void setReq_client_ip_ext(String req_client_ip_ext) {
        this.req_client_ip_ext = req_client_ip_ext;
    }

    @Override
    public String toString() {
        return  "ord_time_ext=" + ord_time_ext +
                "&ord_name=" + ord_name +
                "&ord_desc=" + ord_desc +
                "&notify_url=" + notify_url +
                "&sign=" + sign +
                "&ord_amt=" + ord_amt +
                "&service=" + service +
                "&partner=" + partner +
                "&dispatch_cluster_target=" + dispatch_cluster_target +
                "&ord_cur=" + ord_cur +
                "&ord_pmt_timeout=" + ord_pmt_timeout +
                "&req_access_type=" + req_access_type +
                "&_input_charset=" + _input_charset +
                "&ord_id_ext=" + ord_id_ext +
                "&sign_type=" + sign_type +
                "&return_url=" + return_url +
                "&req_client_ip_ext=" + req_client_ip_ext;
    }
}
