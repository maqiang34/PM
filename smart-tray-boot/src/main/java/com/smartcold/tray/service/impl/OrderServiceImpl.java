package com.smartcold.tray.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartcold.tray.entity.tray.Order_Detail;
import com.smartcold.tray.entity.tray.Tray_Good_Info;
import com.smartcold.tray.entity.tray.User_Order;
import com.smartcold.tray.mapper.OrderMapper;
import com.smartcold.tray.service.OrderService;
import com.smartcold.tray.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by admin on 2018/9/4.
 */
@Service
class OrderSeccrviceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;


    private Gson gson=new Gson();

    private static HashMap<String,List<Tray_Good_Info> > REBACKMAP=new HashMap<>();

    /**
     * 1.0
     * 根据登录用户信息查询登录未处理订单
     * 订单初始状态：0:待处理
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public List<User_Order> getOrderBuyUid(int uid) throws Exception {
        return orderMapper.getOderListByUid(uid);
    }

    /**
     * 2.0
     * 根据订单编号查询订单详细
     * 订单状态变成：1:正在处理
     *
     * @param orderid
     * @return
     * @throws Exception
     */
    public List<Map> getOrderDetailByrderid(String orderid) throws Exception {
        return orderMapper.getOrderDetailByrderid(orderid);
    }


    /**
     * 2.1
     * 根据商品编号查询订单订购商品信息数量
     * 订单状态变成：1.正在处理
     *
     * @param goodids
     * @return
     * @throws Exception
     */
    public List<Order_Detail> getOrderDetailByGoodid(String orderid, String goodids) throws Exception {
       return orderMapper.getOrderDetailByGoodid(orderid, goodids);
    }


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
    @Override
    public List<Order_Detail> getBalanceNumbebyGoogid(String orderid, String goodids) throws Exception {
        return orderMapper.getBalanceNumbebyGoogid(orderid, goodids);
    }


    /***
     * 2.3
     * 装载托盘数据
     * @param
     */
    @Transactional
    public String addTaryGoodInfoAndHandStock(String orderid, String  trayno ,String goodListstr) throws Exception {
        int goodid = 0;

        if(this.orderMapper.verifData(orderid)>0){//校验订单是否存在异常，未考虑性能
            throw new Exception("当前订单已装载数量存在异常！请手动核对后装载！");//强制异常
        }
        List<Tray_Good_Info> goodList =gson.fromJson(goodListstr,new TypeToken< List<Tray_Good_Info>>() {}.getType()) ;
        HashMap<Integer, Integer> load_good_count = new HashMap<>();
        List<Tray_Good_Info> tray_good_infos = new ArrayList<>();
        for (Tray_Good_Info goodInfo : goodList) {
            goodInfo.setTrayno(trayno);
            goodid = goodInfo.getGoodid();
            if (load_good_count.containsKey(goodid)) {
                load_good_count.put(goodid, load_good_count.get(goodid) + goodInfo.getNumber());
            } else {
                load_good_count.put(goodid, goodInfo.getNumber());
            }
            tray_good_infos.add(goodInfo);
        }
        //1.校验装载数据和已处理状态
        String idS = StringUtil.getIdS(load_good_count.keySet().toArray(new Integer[load_good_count.keySet().size()]));
        List<Order_Detail> OrderList = this.orderMapper.getBalanceNumbebyGoogid(orderid, idS);//订单数量
        for (Order_Detail order : OrderList) {
            if (load_good_count.containsKey(order.getGoodid())) {
                if (load_good_count.get(order.getGoodid()) > order.getBnumber()) {
                    throw new Exception("绑定失败！商品装载数量发生变化，请出重新处理订单！");//强制异常
                }
            }else{
                throw new Exception("绑定失败！商品存在异常");//强制异常
            }
        }
        this.orderMapper.addTaryGoodInfo(tray_good_infos);//添加商品至托盘
        this.orderMapper.updateOrderStock(tray_good_infos);//修改处理数量
        String UUID=StringUtil.getUUID();
       // REBACKMAP.put(UUID,tray_good_infos);
        return UUID;
    }

    @Override
    public String rollbackAddTGAHSStock(String UUID) throws Exception {
        return null;
    }


    /**
     * 2.3
     * 解绑托盘数据
     *
     * String orderid
     * String  trayno
     * String goodLis
     * @throws Exception
     */
    @Transactional
    public String delTaryGoodInfoAndHandStock(String orderid, String  trayno ,String goodListstr) throws Exception {
        int goodid = 0;

        List<Tray_Good_Info> goodList = gson.fromJson(goodListstr,new TypeToken< List<Tray_Good_Info>>() {}.getType());
        HashMap<Integer, Integer> load_good_count = new HashMap<>();
        List<Tray_Good_Info> tray_good_infos = new ArrayList<>();
            for (Tray_Good_Info goodInfo : goodList) {
                goodInfo.setTrayno(trayno);
                goodid = goodInfo.getGoodid();
                if (load_good_count.containsKey(goodid)) {
                    load_good_count.put(goodid, load_good_count.get(goodid) + goodInfo.getNumber());
                } else {
                    load_good_count.put(goodid, goodInfo.getNumber());
                }
                tray_good_infos.add(goodInfo);
            }
        //1.解绑商品信息
        List<Tray_Good_Info> old_tray_goods = this.orderMapper.getBalanceNumbebyTrays(orderid, trayno);
        //1.1  删除托盘商品关联数据
        int count = this.orderMapper.delTaryGoodByFilte(orderid, trayno);//10删除关联商品信息
//        if (count ==0) {
//            throw new Exception("绑定失败！解绑托盘与当前上传信息存在异常！");//强制异常
//        }
        //1.2  更新商品已处理数量
        this.orderMapper.rebackOrderStock(old_tray_goods);//修改处理数量


        //1.校验装载数据和已处理状态
        String idS = StringUtil.getIdS(load_good_count.keySet().toArray(new Integer[load_good_count.keySet().size()]));
        List<Order_Detail> OrderList = this.getBalanceNumbebyGoogid(orderid, idS);//订单数量
        for (Order_Detail order : OrderList) {
            if (load_good_count.containsKey(order.getGoodid())) {
                if (load_good_count.get(order.getGoodid()) > order.getBnumber()) {
                    throw new Exception("绑定失败！商品装载数量发生变化，请出重新处理订单！");//强制异常
                }
            }
        }
        this.orderMapper.addTaryGoodInfo(tray_good_infos);//添加商品至托盘
        this.orderMapper.updateOrderStock(tray_good_infos);//修改处理数量
        String UUID=StringUtil.getUUID();
     //   REBACKMAP.put(UUID,tray_good_infos);
        return UUID;
    }

    @Override
    public String rollbackDelTGAHSStock(String UUID) throws Exception {
        return null;
    }


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
    public boolean getLodTraybyGoogid(String orderid) throws Exception {
        int count = orderMapper.verifData(orderid);
        return count == 0;
    }



    /**
     * 绑定货架
     * @param orderNum
     * @param trayNo
     * @param rackNo
     * @throws Exception
     */
    @Transactional
    public  void bindingRack(String orderNum, String trayNo, String rackNo) throws Exception{

    }

    /**
     * 解绑货架
     * @param orderNum
     * @param trayNo
     * @param rackNo
     */
    @Transactional
    public  void untieRack(String orderNum, String trayNo, String rackNo){


    }
}
