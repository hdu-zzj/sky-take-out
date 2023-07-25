package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "菜品浏览操作")
@Slf4j
public class DishController {


    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类Id查询菜品
     *
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "根据分类Id查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类Id查询菜品：{}", categoryId);
        //构造Redis中的key
        String key = "dish_" + categoryId;

        //查询Redis中是否存在数据
        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (dishVOList != null && dishVOList.size() > 0) {
            return Result.success(dishVOList);
        }

        //如果没有数据，则从数据库中查询
        Dish dish = Dish.builder()
                .status(StatusConstant.ENABLE)
                .categoryId(categoryId)
                .build();
        dishVOList = dishService.listWithFlavor(dish);

        //往Redis中存放数据
        redisTemplate.opsForValue().set(key, dishVOList);

        return Result.success(dishVOList);
    }
}
