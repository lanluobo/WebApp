package com.jia.train.po;

import java.util.List;

/**
 * Created by jiaxl on 2016/12/30.
 * 车票订单信息
 */
public class TrainOrder {
    private String start_time_page;
    private String arrive_time_page;
    private String from_station_name_page;
    private String to_station_name_page;
    private String train_code_page;
    private String order_date;
    private String sequence_no;
    private String ticket_total_price_page;
//    private String arrive_time_page;
//    private String arrive_time_page;
//    private String arrive_time_page;
//    private String arrive_time_page;
//    private String arrive_time_page;


    public String getStart_time_page() {
        return start_time_page;
    }

    public void setStart_time_page(String start_time_page) {
        this.start_time_page = start_time_page;
    }

    public String getArrive_time_page() {
        return arrive_time_page;
    }

    public void setArrive_time_page(String arrive_time_page) {
        this.arrive_time_page = arrive_time_page;
    }

    public String getFrom_station_name_page() {
        return from_station_name_page;
    }

    public void setFrom_station_name_page(String from_station_name_page) {
        this.from_station_name_page = from_station_name_page;
    }

    public String getTo_station_name_page() {
        return to_station_name_page;
    }

    public void setTo_station_name_page(String to_station_name_page) {
        this.to_station_name_page = to_station_name_page;
    }

    public String getTrain_code_page() {
        return train_code_page;
    }

    public void setTrain_code_page(String train_code_page) {
        this.train_code_page = train_code_page;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getSequence_no() {
        return sequence_no;
    }

    public void setSequence_no(String sequence_no) {
        this.sequence_no = sequence_no;
    }

    public String getTicket_total_price_page() {
        return ticket_total_price_page;
    }

    public void setTicket_total_price_page(String ticket_total_price_page) {
        this.ticket_total_price_page = ticket_total_price_page;
    }
}
