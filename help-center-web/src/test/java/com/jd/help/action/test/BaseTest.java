package com.jd.help.action.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by gaofei on 14-2-26
 */
public abstract class BaseTest {

    protected static ApplicationContext appContext;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            long start = System.currentTimeMillis();
            System.out.println("正在加载配置文件...");

            if(appContext == null){
                appContext = new FileSystemXmlApplicationContext("D:\\ideaworkspace\\pop.hc.man\\help-center-web\\src\\main\\resources\\spring-config.xml");


            }
            System.out.println("配置文件加载完毕,耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(BaseTest.class.getResource("/"));
    }

    @Before
    public void autoSetBean() {
        appContext.getAutowireCapableBeanFactory().autowireBeanProperties(this, DefaultListableBeanFactory.AUTOWIRE_BY_NAME, false);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }
}
