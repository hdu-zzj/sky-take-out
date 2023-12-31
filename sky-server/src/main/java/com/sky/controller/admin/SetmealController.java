package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐操作")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    @ApiOperation(value = "新增套餐")
    @PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }


    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @ApiOperation(value = "套餐分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @ApiOperation(value = "批量删除套餐")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据Id查询套餐信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据Id查询套餐信息")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据Id查询套餐信息：{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐信息
     *
     * @param setmealDTO
     * @return
     */
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @ApiOperation(value = "修改套餐信息")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐信息：{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }


    /**
     * 启用与禁用
     *
     * @param status
     * @param id
     * @return
     */
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @ApiOperation(value = "启用与禁用")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用与禁用：{}，{}", status, id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }

}
