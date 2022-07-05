package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author Diyang Li
 * @create 2022-06-21 12:25 PM
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    //annotations-driven Dependency Injection
    @Autowired
    private EmployeeService employeeService;

    /**
     * Employee login
     * @param request
     * @param employee
     * @return
     */
    // the uml from page
    // 1. @RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization
    //2. the type we annotate with the @RequestBody annotation must correspond to the JSON sent from our client-side controller:
    // 3. put the username and password in the session after login successfully
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 1. encrypt password from page with md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2. find the username from request in database
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3. if username not in database
        if (emp == null){
            return R.error("login failed");
        }

        //4. compare the password
        if (!emp.getPassword().equals(password)){
            return R.error("login failed");
        }

        // 5. check employee status
        if (emp.getStatus() == 0){
            return R.error("employee is not allowed to login");
        }

        // 6. login successfully, put the employee id in the session
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * employee logout
     * @return
     */
    @PostMapping ("/logout")
    public R<String> logout(HttpServletRequest request){
        // clean the session that include the information of current employee
        request.getSession().removeAttribute("employee");
        return R.success("logout successfully");
    }

    /**
     * Add an employer
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("Add employee: employee boday: {}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // Get current user id
        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("Add new employee successfully!");
    }

    /**
     * Paging
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        // paging constructor
        Page pageInfo = new Page(page, pageSize);

        //Conditional constructor
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //add condition
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //sort by update time
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //execute query
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    // used to update resource, and for mapping HTTP PUT requests onto specific handler methods
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("Update successfully");
    }

    /**
     * Get employee information by id
     * @param id
     * @return
     */
    //@PathVariable annotation can be used to handle template variables in the request URI mapping
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("Get employee information by id from reques URI...");
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }
        return R.error("Did't find the employee");
    }
}













