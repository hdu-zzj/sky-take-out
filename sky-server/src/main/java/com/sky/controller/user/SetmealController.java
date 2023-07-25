package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "套餐浏览接口")
@Slf4j
public class SetmealController {


    @Autowired
    private SetmealService setmealService;


    /**
     * 根据分类Id查询套餐
     *
     * @param categoryId
     * @return
     */
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    @ApiOperation(value = "根据分类Id查询套餐")
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("根据分类Id查询套餐：{}", categoryId);
        Setmeal setmeal = Setmeal.builder()
                .status(StatusConstant.ENABLE)
                .categoryId(categoryId)
                .build();
        List<Setmeal> setmealList = setmealService.list(setmeal);
        return Result.success(setmealList);
    }

    /**
     * 根据套餐Id查询包含的菜品
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据套餐Id查询包含的菜品")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> dishList(@PathVariable Long id) {
        log.info("根据套餐Id查询包含的菜品：{}", id);
        List<DishItemVO> dishItemVOList = setmealService.getDishItemById(id);
        return Result.success(dishItemVOList);
    }

}
