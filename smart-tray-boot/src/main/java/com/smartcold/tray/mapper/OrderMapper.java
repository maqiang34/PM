package com.smartcold.tray.mapper;

import com.smartcold.tray.entity.tray.Order_Detail;
import com.smartcold.tray.entity.tray.Tray_Good_Info;
import com.smartcold.tray.entity.tray.User_Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) SmartCold 版权所有
 *
 * @author MaQiang34
 * @Description:  绑定托盘信息
 * @createDate:2018-09-18 16:40
 * @version:V1.0
 */
@Mapper
public interface OrderMapper {




 	void addTaryGoodInfo( List<Tray_Good_Info> list);

 	void updateOrderStock( List<Tray_Good_Info> list);

	void rebackOrderStock(List<Tray_Good_Info> list);

	Integer verifData(@Param("orderid")String orderid);

	void upateOrderSatus(@Param("orderid")String orderid,@Param("status") int status);


	List<User_Order> getOderListByUid(@Param("uid") int uid);

	List<Map> getOrderDetailByrderid(@Param("orderid")String orderid);
	/**
	 * 2.1
	 * 根据商品编号查询订单订购商品信息数量
	 * 订单状态变成：1.正在处理
	 *
	 * @param goodids
	 * @return
	 * @throws Exception
	 */
	List<Order_Detail> getOrderDetailByGoodid(@Param("orderid")String orderid, @Param("goodids")String goodids);


	/**
	 * 根据订单&&托盘,查询商品装载数量信息
	 * @param orderid
	 * @param trayno
	 * @return
	 */
	List<Tray_Good_Info> getBalanceNumbebyTrays(@Param("orderid")String orderid, @Param("trayno")String trayno);



    /**
     * 获得剩余装载数量
     * @param orderid
     * @return
     */
    List<Order_Detail> getBalanceNumbebyGoogid(@Param("orderid")String orderid ,@Param("goodids") String goodids);

	/**
	 * 解绑托盘
 	 * @param orderid
	 * @param trayno
	 * @return
	 */
	int delTaryGoodByFilte(@Param("orderid") String orderid,@Param("trayno")  String trayno);


}
