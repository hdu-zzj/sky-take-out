package com.sky.controller.user;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿接口")
@Slf4j
public class AddressBookController {


    @Autowired
    private AddressBookService addressBookService;


    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    @ApiOperation(value = "新增地址")
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success();
    }


    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return
     */
    @ApiOperation(value = "查询当前登录用户的所有地址信息")
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }


    /**
     * 查询默认地址
     *
     * @return
     */
    @ApiOperation(value = "查询默认地址")
    @GetMapping("/default")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .isDefault(1)
                .build();
        List<AddressBook> list = addressBookService.list(addressBook);
        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }
        return Result.error(MessageConstant.NO_DEFAULT_ADDRESS);
    }


    /**
     * 根据Id修改地址
     *
     * @param addressBook
     * @return
     */
    @ApiOperation(value = "根据Id修改地址")
    @PutMapping
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("根据Id修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }


    /**
     * 根据id删除地址
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据Id删除地址")
    @DeleteMapping
    public Result deleteById(Long id) {
        log.info("根据Id删除地址：{}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }


    /**
     * 根据Id查询地址
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据Id查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id) {
        log.info("根据Id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }


    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @ApiOperation(value = "设置默认地址")
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址：{}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }
}
