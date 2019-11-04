package xyz.redrain.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */


public class Test {
    /*private static SqlSessionFactory sqlSessionFactory = null;
    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void insertObj() {

        User user = new User();
        //user.setId(37);
        user.setUsername("aassa");
        user.setPassword("bbb");
        user.setTime(new Date());
        user.setUserId("1");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.insertObj(user);
        sqlSession.commit();
        Assert.assertNotNull(user.getId());
        }

    @org.junit.Test
    public void insertObjSelective() {

        User user = new User();
        //user.setId(37);
        user.setUsername("aassa");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.insertObjSelective(user);
        System.out.println(a);
        sqlSession.commit();
        Assert.assertNotNull(user.getId());
    }

    public User before() {

        User user = new User();
        //user.setId(37);
        user.setUsername("aassa");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.insertObjSelective(user);
        System.out.println(a);
        sqlSession.commit();
        return user;
    }


    @org.junit.Test
    public void updateObjSelectiveById() {

        User user = before();
        user.setUsername("cccc");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.updateObjSelectiveById(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void updateObjById() {

        User user = before();
        user.setUsername("aassa");
        user.setPassword("dddd");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.updateObjById(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void deleteObjById() {

        User user = before();
        user.setUsername("aaererssa");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.deleteObjById(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void deleteObjByParams() {

        User user = new User();
        //user.setId(37);
        user.setUsername("aassa");
        // user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.deleteObjByParams(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void selectObjById() {

        User user = before();
        User newUser = new User();
        newUser.setId(user.getId());

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        newUser = userMapper.selectObjById(newUser);
        System.out.println(newUser);
        sqlSession.commit();
        Assert.assertEquals(user.getUsername(), newUser.getUsername());
    }

    @org.junit.Test
    public void selectObjByParams() {
        User user = new User();
        //user.setId(37);
        user.setUsername("aassa");
        user.setPassword("a");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int a = userMapper.insertObjSelective(user);
        System.out.println(a);
        sqlSession.commit();


        user = new User();
        user.setUsername("aassa");
        user.setPassword("a");
        user = userMapper.selectObjByParams(user);
        System.out.println(user);
        sqlSession.commit();
        Assert.assertNotNull(user.getId());
    }


    @org.junit.Test
    public void selectListByParams() {

        User user = new User();
        user.setUsername("aassa");
        //user.setPassword("a");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectListByParams(user);
        System.out.println(user);
        sqlSession.commit();
        Assert.assertTrue(users.size() > 1);
    }


    @org.junit.Test
    public void selectListByParamsPages() {

        User user = new User();
        user.setUsername("aassa");
        //user.setPassword("a");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectListByParamsPages(user, 0, 1);
        System.out.println(user);
        sqlSession.commit();

    }
*/

}
