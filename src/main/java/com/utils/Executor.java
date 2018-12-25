package com.utils;

import com.domain.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class Executor {

    public static <E> List<E> list(Connection conn, Mapper mapper){
        //执行查询
        PreparedStatement stm = null;
        ResultSet resultSet = null;

        try {
            //获取PreparedStatement
            stm = conn.prepareStatement(mapper.getSql());

            //执行查询
            resultSet = stm.executeQuery();

            //调用Converter实现转换
            List<E> list = Converter.list(resultSet, Class.forName(mapper.getResultType()));

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null){
                    resultSet.close();
                }
                if(stm != null){
                    stm.close();
                }
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
