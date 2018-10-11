package com.smartcold.tray.controller.api;

import com.google.gson.Gson;
import com.smartcold.tray.entity.tray.Order_Detail;
import com.smartcold.tray.entity.tray.Tray_Good_Info;
import com.smartcold.tray.service.OrderService;
import com.smartcold.tray.util.ResponseData;
import com.smartcold.tray.util.SetUtil;
import com.smartcold.tray.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;
import java.util.*;

/**
 * Copyright (C) SmartCold 版权所有
 *
 * @author MaQiang34
 * @Description: 手持终端使用
 *    订单数据信息
 * @createDate:2018-09-18 16:40
 * @version:V1.0
 */
@RestController
@RequestMapping(value="i/order")
public class OrderController {

    @Autowired
    private OrderService orderService;




    /**
     * 查询当前用户订单
     * @param uid
     * @return
     */
    @RequestMapping(value="/getOrder")
    public ResponseData selectOrder(String systoken, Integer uid) {
        try {
            return ResponseData.newSuccess(orderService.getOrderBuyUid(uid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.newFailure("查询失败");
    }

    /**
     * 根据订单获得商品详情
     * @param orderid：订单编号
     *
     */
    @RequestMapping(value="/getOrderInfo")
    public ResponseData getOrderInfo(String token, @RequestParam(value="orderid",required=true) String orderid) {
        try {
            List<Map> orderEntities = orderService.getOrderDetailByrderid(orderid);
            if(SetUtil.isNullList(orderEntities)){
                return ResponseData.newFailure("单号不存在!");
            }
            return ResponseData.newSuccess(orderEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.newFailure("查询失败");
    }

    /**
     * 1.绑定商品（装载托盘）
     * 2.处理订单（修改处理数量）
     * 3.数据一致性校验
     * @param
     * @return
     */
    @RequestMapping(value="/bindingTary")
    public ResponseData bindingTary(String orderid, String  trayno ,String goodList) {
       if(StringUtil.isNull(orderid)||StringUtil.isNull(trayno)||StringUtil.isNull(goodList)){  return ResponseData.newFailure("参数不全！没有绑定商品信息！"); }
        try {
            this.orderService.addTaryGoodInfoAndHandStock( orderid,   trayno , goodList);//插入托盘信息 --变更商品信息
            return ResponseData.newSuccess("绑定成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.newFailure(e.getMessage());
        }
    }

    /**
     * 1.绑定商品（装载托盘）
     * 2.处理订单（修改处理数量）
     * 3.数据一致性校验
     * @param
     * @return
     */
    @RequestMapping(value="/rollbackAddTGAHSStock")
    public ResponseData rollbackAddTGAHSStock(String uuid) {
        try {
            this.orderService.rollbackAddTGAHSStock(uuid);
            return ResponseData.newSuccess("回滚成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.newFailure(e.getMessage());
        }
    }

    /**
     * 1.解绑商品（解绑托盘）
     * 2.处理订单（修改处理数量）
     * @param
     * @return
     */
    @RequestMapping(value="/untieTary")
    public ResponseData untieTary(String orderid, String  trayno ,String goodList) {
        if(StringUtil.isNull(orderid)||StringUtil.isNull(trayno)||StringUtil.isNull(goodList)){  return ResponseData.newFailure("没有解绑的商品信息！"); }
        try {
            this.orderService.delTaryGoodInfoAndHandStock( orderid,   trayno , goodList);//插入托盘信息 --变更商品信息
            return ResponseData.newSuccess("绑定成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.newFailure("绑定失败！");
    }
    @RequestMapping(value="/rollbackDelTGAHSStock")
    public ResponseData rollbackDelTGAHSStock(String uuid) {
        try {
            this.orderService.rollbackDelTGAHSStock(uuid);
            return ResponseData.newSuccess("回滚成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.newFailure(e.getMessage());
        }
    }


    /**
     * 绑定货架
     * @param
     * @return
     */
    @RequestMapping(value="/bindingRack")
    public ResponseData untieOrders(@RequestParam(value="orderid",required=true) String orderid,@RequestParam(value="trayNo",required=true)  String trayno,@RequestParam(value="rackno",required=true) String rackno) {
        try {
            this.orderService.bindingRack( orderid, trayno,  rackno);
            return ResponseData.newSuccess("绑定成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.newFailure("没有绑定商品信息！");
    }

    /**
     * 解绑货架
     * @param
     * @return
     */
    @RequestMapping(value="/untieRack")
    public ResponseData untieRack(@RequestParam(value="orderid",required=true) String orderid,@RequestParam(value="trayno",required=true)  String trayno) {
        try {
            this.orderService.untieRack( orderid, trayno,  null);
            return ResponseData.newSuccess("绑定成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.newFailure("jie！");
    }

}
