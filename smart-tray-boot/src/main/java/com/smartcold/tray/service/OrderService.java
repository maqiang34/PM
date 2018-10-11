package com.smartcold.tray.service;


import com.smartcold.tray.entity.tray.Order_Detail;
import com.smartcold.tray.entity.tray.User_Order;

import java.util.List;
import java.util.Map;


public interface OrderService {
    //1============================================================================================================================================

    /**
     * 1.0
     * 根据登录用户信息查询登录未处理订单
     * 订单初始状态：待处理
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public List<User_Order> getOrderBuyUid(int uid) throws Exception;

    /**
     * 2.0
     * 根据订单编号查询订单详细
     * 订单状态变成：正在处理
     *
     * @param orderid
     * @return
     * @throws Exception
     */
    public List<Map> getOrderDetailByrderid(String orderid) throws Exception;


    /**
     * 2.1
     * 根据商品编号查询订单订购商品信息数量
     * 订单状态变成：正在处理
     *
     * @param goodids
     * @return
     * @throws Exception
     */
    public List<Order_Detail> getOrderDetailByGoodid(String orderid, String goodids) throws Exception;

    /**2.2
     * 通过订单编号 和 商品编号计已经装载的商品数量（sum）
     * 订单状态变成：正在处理
     * @param orderid
     * @param goodids
     * @return
     * @throws Exception
     */
//        public List<Order_Detail> getLodTraybyGoogid(String orderid,String goodids)throws Exception;


    /**
     * 2.2
     * 通过订单编号 和 商品编号获得结余商品数量（sum）
     * 订单状态变成：正在处理
     *
     * @param orderid
     * @param goodids
     * @return
     * @throws Exception
     */
   public List<Order_Detail> getBalanceNumbebyGoogid(String orderid, String goodids) throws Exception;



    /**
     * 回滚操作
     * @param UUID
     * @return
     * @throws Exception
     */


    /**
     * 2.3
     * 装载托盘数据
     *
     * @param orderid
     * @param trayno
     * @param goodList
     * @return
     * @throws Exception
     */
   public String addTaryGoodInfoAndHandStock(String orderid, String trayno, String goodList) throws Exception;

    /**
     * //设备写入失败 希望别用到
     * @param UUID
     * @return
     * @throws Exception
     */
   public String rollbackAddTGAHSStock(String UUID) throws Exception;




    /**
     * 2.3
     * 解绑托盘数据
     *
     * @param orderid
     * @param trayno
     * @param goodLis
     * @return
     * @throws Exception
     */
    public String delTaryGoodInfoAndHandStock(String orderid, String trayno, String goodLis) throws Exception;

    /**
     * //设备写入失败 希望别用到
     * @param UUID
     * @return
     * @throws Exception
     */
    public String rollbackDelTGAHSStock(String UUID) throws Exception;



    /**
     * 2.4
     * 校验订单完整一致性
     * true: 完成装载，装载数量一致 反之
     * 订单状态变成：正在处理
     *
     * @param orderid
     * @return
     * @throws Exception
     */
    public boolean getLodTraybyGoogid(String orderid) throws Exception;

    /**
     * 绑定货架
     *
     * @param orderNum
     * @param trayNo
     * @param rackNo
     * @throws Exception
     */
    public void bindingRack(String orderNum, String trayNo, String rackNo) throws Exception;

    /**
     * 解绑货架
     *
     * @param orderNum
     * @param trayNo
     * @param rackNo
     */
    public void untieRack(String orderNum, String trayNo, String rackNo);


}
