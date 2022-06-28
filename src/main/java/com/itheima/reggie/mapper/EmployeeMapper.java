package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Diyang Li
 * @create 2022-06-21 12:11 PM
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
