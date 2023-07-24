package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺操作")
@Slf4j
public class ShopController {

    private static final String KEY = "SHOP_STATUS";
    private static final Integer ON = 1;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 设置店铺营业状态
     *
     * @param status
     * @return
     */
    @ApiOperation(value = "设置店铺营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态：{}", status.equals(ON) ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }


    /**
     * 获取店铺营业状态
     *
     * @return
     */
    @ApiOperation(value = "获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺营业状态：{}", status.equals(ON) ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
