package com.test;

import com.Resources;
import com.domain.User;
import com.mapper.UseMapper;

import com.sqlsession.SqlSession;
import com.sqlsession.SqlSessionFactory;
import com.sqlsession.SqlSessionFactoryBuilder;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class MyBatisTest {
    @Test
    public void testFindAll() throws IOException {
        //读取配置文件
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");

        //创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        /**
         * 1) 构建者模式：
         * 通过SqlSessionBuilder对象构建一个SqlSessionFactory
         * 是将一个复杂的对象的构建过程分离出来，隐藏了复杂对象的创建过程，使我们不用关心对象构建的过程
         */
        //通过SqlSessionBuilder对象构建一个SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = builder.build(is);

        /**
         * 2）工厂模式
         * 通过SqlSessionFactory构建一个SqlSession
         * 工厂模式是我们最常用的实例化对象模式了，是用工厂方法代替new操作的一种模式。
         * 用工厂模式能够降低程序之间的耦合程度，给系统带来更大的可扩展性和尽量少的修改量
         */
        //通过SqlSessionFactory构建一个SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        /**
         * 3) 代理模式
         *  通过SqlSession实现增删改查
         *  构建一个UserMapper接口的代理类，让代理对象完成增删改查
         *  在某些情况下，一个对象不适合或者不能直接引用另一个对象，而代理对象可以在
         *  客户端和目标对象之间起到中介的作用。
         *
         *  优点
         *  1） 职责清晰
         *      真实的角色就是实现实际的业务逻辑，不用关心其他非本职责的事务，通过后期的代理完成一件事务，附带的结果就是编程简洁清晰
         *  2） 代理对象可以在客户端和目标对象之间起到中介的作用，这样起到了中介的作用和保护了目标对象的作用
         *  3） 高扩展性
         */
        //通过SqlSession实现增删改查
        UseMapper useMapper = sqlSession.getMapper(UseMapper.class);
        List<User> users = useMapper.findAll();

        //打印输出
        for (User user : users) {
            System.out.println(user);
        }

        //关闭资源
        sqlSession.close();
        is.close();
    }
}
