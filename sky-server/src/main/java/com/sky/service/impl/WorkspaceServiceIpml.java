package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceIpml implements WorkspaceService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;


    /**
     * 查询今日运营数据
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);

        //当天总订单数量
        Integer totalOrderCount = orderMapper.countByMap(map);
        totalOrderCount = totalOrderCount == null ? 0 : totalOrderCount;

        map.put("status", Orders.COMPLETED);

        //当天有效订单数量
        Integer validOrderCount = orderMapper.countByMap(map);
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;
        //当天营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;
        //订单完成率
        Double orderCompletionRate = totalOrderCount == 0 ? 0.0 : validOrderCount / totalOrderCount;
        //平均客单价
        Double unitPrice = validOrderCount == 0 ? 0.0 : turnover / validOrderCount;
        //新增用户数
        Integer newUsers = userMapper.countByDate(begin, end);

        BusinessDataVO businessDataVO = BusinessDataVO.builder()
                .validOrderCount(validOrderCount)
                .newUsers(newUsers)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .build();

        return businessDataVO;
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    @Override
    public SetmealOverViewVO getOverviewSetmeals() {
        // 已启售数量
        Integer sold = setmealMapper.countByStatus(StatusConstant.ENABLE);

        // 已停售数量
        Integer discontinued = setmealMapper.countByStatus(StatusConstant.ENABLE);

        return SetmealOverViewVO.builder().sold(sold).discontinued(discontinued).build();
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    @Override
    public DishOverViewVO getOverviewDishes() {
        // 已启售数量
        Integer sold = dishMapper.countByStatus(StatusConstant.ENABLE);

        // 已停售数量
        Integer discontinued = dishMapper.countByStatus(StatusConstant.ENABLE);

        return DishOverViewVO.builder().sold(sold).discontinued(discontinued).build();
    }

    /**
     * 查询订单管理数据
     *
     * @return
     */
    @Override
    public OrderOverViewVO getOverviewOrders() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));

        //待接单数量
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.countByMap(map);

        //待派送数量
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        //已完成数量
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        //已取消数量
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.countByMap(map);
        return OrderOverViewVO.builder()
                .allOrders(allOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders)
                .waitingOrders(waitingOrders)
                .build();
    }
}
