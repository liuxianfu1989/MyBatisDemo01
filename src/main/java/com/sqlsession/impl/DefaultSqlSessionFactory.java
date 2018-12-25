package com.sqlsession.impl;

import com.XMLConfigBuilder;
import com.sqlsession.SqlSession;
import com.sqlsession.SqlSessionFactory;

import java.io.InputStream;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    //SqlMapConfig.xml的字节输入流
    private InputStream is;

    public void setIs(InputStream is){
        this.is = is;
    }

    @Override
    public SqlSession openSession() {
        //创建一个DefaultSqlSession
        DefaultSqlSession sqlSession = new DefaultSqlSession();

        //加载解析配置文件
        XMLConfigBuilder.loadConfiguration(sqlSession, is);

        return sqlSession;
    }
}
