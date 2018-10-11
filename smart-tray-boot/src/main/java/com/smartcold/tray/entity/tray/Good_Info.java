package com.smartcold.tray.entity.tray;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 商品信息
 */
public class Good_Info  implements Serializable {
    private int id;//商品编号
    private int type;//商品品类
    private String name;//商品名称
    private String barcode;//条形码
    private String unit;//单位
    private String weight;
    private int number;//
    private static final long serialVersionUID = -2875932349754314486L;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
