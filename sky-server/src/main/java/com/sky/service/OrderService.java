package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     */
    void paySuccess(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 历史订单查询
     *
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult pageQuery4User(OrdersPageQueryDTO ordersPageQueryDTO);
}
