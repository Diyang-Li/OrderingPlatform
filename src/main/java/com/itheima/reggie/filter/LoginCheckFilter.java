package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import jdk.security.jarsigner.JarSigner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** check if the user has login successfully
 * @author Diyang Li
 * @create 2022-06-23 11:43 PM
 */

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // help to compare the path, support the wildcard
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. get the URI from request
        String requestURI = request.getRequestURI();

        log.info("Intersept: {}", requestURI);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        //2. check if the url in the ursl doesn't need any treatment
        boolean check = check(urls, requestURI);

        if (check){
            log.info("Current request doesn't need treatment: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 3. if already login
        if (request.getSession().getAttribute("employee") != null){
            log.info("the user already login, the id is: {}", request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        //4. not login, use output stream
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("The user not login: {}", requestURI);
        return;
    }

    public boolean check(String[] urls, String requestURI){
        for (String url: urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
