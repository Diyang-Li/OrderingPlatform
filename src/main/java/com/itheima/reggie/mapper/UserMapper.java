package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Diyang Li
 * @create 2022-07-20 11:35 PM
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
