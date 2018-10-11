package com.smartcold.tray.entity.tray;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * 订单详细
 * table:order_detail
 * Created by maqiang34 on 2018/9/4.
 * extends hashMap
 * o.orderid,o.onumber-SUM(t.number) bnumber
 */
public class Order_Detail    implements Serializable  {
    private int id;
    private String orderid;
    private int goodid;
    private int onumber;//实际要货量
    private int hnumber;//已处理数量
   // private int lnumber;// 托盘已经装载的数量
    private int bnumber;// 可以装载的数量

    private String addtime;

    private static final long serialVersionUID = -2875982349754314486L;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getGoodid() {
        return goodid;
    }

    public void setGoodid(int goodid) {
        this.goodid = goodid;
    }

    public int getOnumber() {
        return onumber;
    }

    public void setOnumber(int onumber) {
        this.onumber = onumber;
    }

    public int getHnumber() {
        return hnumber;
    }

    public void setHnumber(int hnumber) {
        this.hnumber = hnumber;
    }

    public String getAddtime() {
        return addtime;
    }

    public int getBnumber() {
        return bnumber;
    }

    public void setBnumber(int bnumber) {
        this.bnumber = bnumber;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
