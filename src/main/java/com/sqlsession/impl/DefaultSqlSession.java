package com.sqlsession.impl;

import com.MapperProxyFactory;
import com.domain.Configuration;
import com.domain.Mapper;

import com.sqlsession.SqlSession;

import com.utils.Executor;


import java.lang.reflect.Proxy;

import java.util.List;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class DefaultSqlSession implements SqlSession {

    //把Configuration对象给DefaultSqlSession
    private Configuration cfg;

    //创建一个set方法，给Configuration赋值
    public void setCfg(Configuration cfg){
        this.cfg = cfg;
    }

    /**
     * 修改DefaultSqlSession添加代理实现
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<T> clazz) {
        /**
         * 参数：
         *      1) 被代理对象的类加载器
         *      2） 字节数组，让代理对象和被代理对象有相同的行为【行为也就是有相同的方法】
         *      3） InvocationHandler: 增强代码，需要使用提供者增强的代码，该代码是以接口的实现类方式提供的，通常用匿名内部类，但不绝对
         */
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxyFactory(this));
    }

    @Override
    public <E> List<E> selectList(String statement) {
        //获取对应的Mapper
        Mapper mapper = cfg.getMappers().get(statement);

        //JDBC操作流程实现
        if(mapper != null){
            //执行查询
            return Executor.list(cfg.getConnection(), mapper);
        }
        return null;
    }

    @Override
    public void close() {

    }
}
