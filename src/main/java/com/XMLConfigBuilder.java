package com;

import com.domain.Configuration;
import com.domain.Mapper;
import com.sqlsession.impl.DefaultSqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: LiuXianfu
 * @version: 1.0
 * @date: 2018/12/25
 * @since: 1.0
 *
 * 解析XMLConfigBuilder解析SqlMapConfig.xml
 */

public class XMLConfigBuilder {
    /**
     * 解析SqlMapConfig.xml配置文件
     * @param is
     */
    public static void loadConfiguration(DefaultSqlSession sqlSession, InputStream is){
        try {
            //数据库配置信息存储
            Configuration cfg = new Configuration();

            //创建SAXReader对象读取xml文件字节输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);

            //解析配置文件，获取根节点信息，//property表示获取根节点下所有的property节点对象
            List<Element> rootList = document.selectNodes("//property");

            //循环迭代所有节点对象
            for (Element element : rootList) {
                //name属性的值
                String name = element.attributeValue("name");
                //value属性的值
                String value = element.attributeValue("value");

                //将解析的数据库连接信息存储到Configuration中
                //数据库驱动
                if(("driver").equals(name)){
                    cfg.setDriver(value);
                } else if("url".equals(name)){
                    //数据库连接地址
                    cfg.setUrl(value);
                } else if("username".equals(name)){
                    //数据库账号
                    cfg.setUsername(value);
                } else if("password".equals(name)){
                    //数据库密码
                    cfg.setPassword(value);
                }
            }

            //解析<mapper resource="com/mapper/UseMapper.xml">
            List<Element> mapperList = document.selectNodes("//mappers//mapper");

            for (Element element : mapperList) {
                //获取需要解析的XML路径
                String resource = element.attributeValue("resource");
                Map<String, Mapper> mappers = loadMapper(resource);

                //将mappers存入Configuration中
                cfg.setMappers(mappers);
            }

            //将cfg给DefaultSqlSession
            sqlSession.setCfg(cfg);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析UseMapper.xml，提取SQL语句和返回JavaBean全限定名
     * path为UseMapper.xml的路径
     */
    public static Map<String, Mapper> loadMapper(String path){

        try {
            /**
             * 1) 定义一个Map<String, Mapper> mappers
             *  用于存储解析的XML封装的Mapper信息
             */
            Map<String, Mapper> mappers = new HashMap<>();

            // 获得文件字节输入流
            InputStream is = Resources.getResourceAsStream(path);

            //创建SAXReader对象，加载文件字节输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);

            //获得根节点
            Element rootElement = document.getRootElement();

            //获取命名空间的值
            String namespace = rootElement.attributeValue("namespace");

            //获取所有select节点
            List<Element> selectList = document.selectNodes("//select");

            //循环所有select节点
            for (Element element : selectList) {
                //获取ID属性值
                String id = element.attributeValue("id");

                //获取resultType属性值
                String resultType = element.attributeValue("resultType");

                //获取SQL语句
                String sql = element.getText();

                //构建Mapper对象
                Mapper mapper = new Mapper(sql, resultType);

                //key=namespace+.+id
                String key = namespace+"."+id;

                //存储到Map中
                mappers.put(key, mapper);
            }
            return mappers;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
