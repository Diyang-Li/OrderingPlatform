package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Diyang Li
 * @create 2022-06-20 11:31 AM
 */
@Slf4j
@SpringBootApplication
// Scan web filter annotation
@ServletComponentScan
// start transaction
@EnableTransactionManagement
@EnableCaching
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("prject start----------------");
    }
}
