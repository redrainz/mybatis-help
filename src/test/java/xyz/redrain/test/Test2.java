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

 */


public class Test2 {
    private static SqlSessionFactory sqlSessionFactory = null;
    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void insertObj() {

        UserInfo user = new UserInfo();
        //user.setId(37);
        user.setUserName("aassa");
        user.setPassword("bbb");
        user.setTime(new Date());
        user.setUserId("1");
        user.setBdContent("dfsdfsdfsd");
        user.setTime1(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.insertObj(user);
        sqlSession.commit();
        Assert.assertNotNull(user.getId());
        }

    @org.junit.Test
    public void insertObjSelective() {

        UserInfo user = new UserInfo();
        //user.setId(37);
        user.setUserName("aassa");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.insertObjSelective(user);
        System.out.println(a);
        sqlSession.commit();
        Assert.assertNotNull(user.getId());
    }

    public UserInfo before() {

        UserInfo user = new UserInfo();
        //user.setId(37);
        user.setUserName("aassa");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.insertObjSelective(user);
        System.out.println(a);
        sqlSession.commit();
        return user;
    }


    @org.junit.Test
    public void updateObjSelectiveById() {

        UserInfo user = before();
        user.setUserName("cccc");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.updateObjSelectiveById(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void updateObjById() {

        UserInfo user = before();
        user.setUserName("aassa");
        user.setPassword("dddd");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.updateObjById(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void deleteObjById() {

        UserInfo user = before();
        user.setUserName("aaererssa");
        user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.deleteObjById(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void deleteObjByParams() {

        UserInfo user = new UserInfo();
        //user.setId(37);
        user.setUserName("aassa");
        // user.setTime(new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.deleteObjByParams(user);
        System.out.println(a);
        sqlSession.commit();

    }

    @org.junit.Test
    public void selectObjById() {

        UserInfo user = before();
        UserInfo newUser = new UserInfo();
        newUser.setId(user.getId());

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        newUser = userMapper.selectObjById(newUser);
        System.out.println(newUser);
        sqlSession.commit();
        Assert.assertEquals(user.getUserName(), newUser.getUserName());
    }

    @org.junit.Test
    public void selectObjByParams() {
        UserInfo user = new UserInfo();
        //user.setId(37);
        user.setUserName("aassa");
        user.setPassword("a");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        int a = userMapper.insertObjSelective(user);
        System.out.println(a);
        sqlSession.commit();


        user  = new UserInfo();
        user.setUserName("aassa");
        user.setPassword("a");
        user = userMapper.selectObjByParams(user);
        System.out.println(user);
        sqlSession.commit();
        Assert.assertNotNull(user.getId());
    }


    @org.junit.Test
    public void selectListByParams() {

        UserInfo user = new UserInfo();
        user.setUserName("aassa");
        //user.setPassword("a");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfo> users = userMapper.selectListByParams(user);
        System.out.println(user);
        sqlSession.commit();
        Assert.assertTrue(users.size() > 1);
    }


    @org.junit.Test
    public void selectListByParamsPages() {

        UserInfo user = new UserInfo();
        user.setUserName("aassa");
        //user.setPassword("a");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userMapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfo> users = userMapper.selectListByParamsPages(user, 0, 1);
        System.out.println(user);
        sqlSession.commit();

    }

}
