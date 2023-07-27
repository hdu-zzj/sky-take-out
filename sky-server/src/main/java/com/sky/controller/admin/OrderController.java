package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单管理接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询订单详情")
    @GetMapping("/details/{id}")
    public Result<OrderVO> details(@PathVariable Long id) {
        log.info("查询订单详情：{}", id);
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }


    /**
     * 订单搜素
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @ApiOperation(value = "订单搜素")
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单搜素：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    @ApiOperation(value = "各个状态的订单数量统计")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }


    /**
     * 接单
     *
     * @param ordersConfirmDTO
     * @return
     */
    @ApiOperation(value = "接单")
    @PutMapping("/confirm")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("接单：{}", ordersConfirmDTO);
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }


    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     * @return
     */
    @ApiOperation(value = "拒单")
    @PutMapping("/rejection")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("拒单：{}", ordersRejectionDTO);
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }


    /**
     * 取消订单
     *
     * @param ordersCancelDTO
     * @return
     */
    @ApiOperation(value = "取消订单")
    @PutMapping("/cancel")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        log.info("取消订单：{}", ordersCancelDTO);
        orderService.adminCancelById(ordersCancelDTO);
        return Result.success();
    }


    /**
     * 派送订单
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "派送订单")
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable Long id) {
        log.info("派送订单：{}", id);
        orderService.delivery(id);
        return Result.success();
    }


    /**
     * 完成订单
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "完成订单")
    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable Long id) {
        log.info("完成订单：{}", id);
        orderService.complete(id);
        return Result.success();
    }
}
