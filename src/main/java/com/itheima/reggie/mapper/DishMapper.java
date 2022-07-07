package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Diyang Li
 * @create 2022-07-07 11:31 AM
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
