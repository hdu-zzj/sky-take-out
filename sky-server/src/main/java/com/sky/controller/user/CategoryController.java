package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "分类接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 条件查询
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("条件查询：{}", type);
        List<Category> categoryList = categoryService.list(type);
        return Result.success(categoryList);
    }
}
