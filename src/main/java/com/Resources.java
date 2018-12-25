package com;

import java.io.InputStream;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 */

public class Resources {
    /**
     * 读取该路径下的文件
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path){
        //读取类路径下的文件，获取文件字节输入流
        InputStream is = Resources.class.getClassLoader().getResourceAsStream(path);
        return is;
    }
}
