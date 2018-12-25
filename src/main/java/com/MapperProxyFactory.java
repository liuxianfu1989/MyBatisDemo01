package com;

import com.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class MapperProxyFactory implements InvocationHandler {

    private SqlSession sqlSession;
    public MapperProxyFactory(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //1. 获取当前操作所对应的Mapper信息
        String className = method.getDeclaringClass().getName();//类的名字，和UserMapper.xml中mapper的namespace一致
        String methodName = method.getName();//方法名字，和UserMapper.xml中的id值一致
        String key = className+"."+methodName;

        //确定当前操作是否是查询所有
        Class<?> returnType = method.getReturnType();
        if(returnType == List.class){
            //执行集合查询操作
            return sqlSession.selectList(key);
        } else {
            return null;
        }
    }
}
