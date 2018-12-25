package com.sqlsession;

import com.mapper.UseMapper;

import java.util.List;

public interface SqlSession {
    <T> T getMapper(Class<T> clazz);

    /**
     * 集合查询
     * @param statement
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statement);

    void close();
}
