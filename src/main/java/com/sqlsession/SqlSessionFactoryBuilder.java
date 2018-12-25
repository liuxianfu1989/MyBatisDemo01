package com.sqlsession;

import com.sqlsession.impl.DefaultSqlSessionFactory;

import java.io.InputStream;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream is){
        //创建一个SqlSessionFactory的实例
        DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory();
        //给SqlSessionFactory的is属性赋值
        sqlSessionFactory.setIs(is);
        return sqlSessionFactory;
    }
}
