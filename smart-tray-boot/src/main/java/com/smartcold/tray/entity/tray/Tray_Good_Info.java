package com.smartcold.tray.entity.tray;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 托盘装载商品信息
 */
public class Tray_Good_Info  implements Serializable {
    private int id;
    private String orderid;
    private String trayno;
    private int goodid;
    private int number;
    private static final long serialVersionUID = -2875932349384314486L;


    public Tray_Good_Info() {
        super();
    }

    public Tray_Good_Info(String orderid, String trayno, int goodid, int number) {
        super();
        this.orderid = orderid;
        this.trayno = trayno;
        this.goodid = goodid;
        this.number = number;
    }

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

    public String getTrayno() {
        return trayno;
    }

    public void setTrayno(String trayno) {
        this.trayno = trayno;
    }

    public int getGoodid() {
        return goodid;
    }

    public void setGoodid(int goodid) {
        this.goodid = goodid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
