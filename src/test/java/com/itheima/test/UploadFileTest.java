package com.itheima.test;

import org.junit.jupiter.api.Test;

/**
 * @author Diyang Li
 * @create 2022-07-10 7:30 PM
 */
public class UploadFileTest {
    @Test
    public void test1(){
        String fileName = "erere.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
