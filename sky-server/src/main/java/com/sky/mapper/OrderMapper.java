package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {


    /**
     * 插入订单数据
     *
     * @param orders
     */
    public void insert(Orders orders);

    /**
     * 根据订单号查询信息
     *
     * @param orderNumber
     * @return
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 分页条件查询
     *
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据订单Id查询信息
     *
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 统计不同状态的订单数量
     *
     * @param status
     * @return
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countByStatus(Integer status);

    /**
     * 根据状态和下单时间获取订单信息
     *
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 计算不同日期已完成订单的营业额
     *
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 订单统计
     *
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 指定时间内的销量前十
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    List<GoodsSalesDTO> getSaleTop10(LocalDateTime beginTime, LocalDateTime endTime);
}
