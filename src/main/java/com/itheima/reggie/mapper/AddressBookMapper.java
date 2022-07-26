package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Diyang Li
 * @create 2022-07-25 10:11 PM
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
