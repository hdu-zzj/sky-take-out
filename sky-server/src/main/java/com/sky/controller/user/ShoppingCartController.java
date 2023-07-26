package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车接口")
@Slf4j
public class ShoppingCartController {


    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @ApiOperation(value = "添加购物车")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }


    /**
     * 查看购物车
     *
     * @return
     */
    @ApiOperation(value = "查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
        return Result.success(list);
    }


    /**
     * 清空购物车
     *
     * @return
     */
    @ApiOperation(value = "清空购物车")
    @DeleteMapping("/clean")
    public Result clean() {
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }


    /**
     * 删除购物车一个商品
     *
     * @param shoppingCartDTO
     * @return
     */
    @ApiOperation(value = "删除购物车一个商品")
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车一个商品：{}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
