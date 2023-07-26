package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {


    /**
     * 插入地址簿数据
     *
     * @param addressBook
     */
    void insert(AddressBook addressBook);

    /**
     * 查询地址簿数据
     *
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 修改地址
     *
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 删除地址
     *
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据Id查询地址信息
     *
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * 修改默认地址信息
     *
     * @param addressBook
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);
}
