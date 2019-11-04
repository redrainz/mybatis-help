## mybatis-help

1. 使用场景
    1. mybatis:3.3.0
    2. xml与mapper混合使用
2. maven引入
     ```xml
       <dependency>
       <groupId>xyz.redrain</groupId>
       <artifactId>mybatis-help</artifactId>
       <version>0.1.1-SNAPSHOT</version>
       </dependency> 
     ```  
3. 使用mybatis的mapper接口方法
     1. mapper.xml中namespace为mapper全限定类名
     2. mapper.xml中必须有BaseResultMap的resultMap
     ```xml
     <mapper namespace="xyz.redrain.UserMapper" >
     <resultMap id="BaseResultMap" type="User" >
     <id column="id" property="id" jdbcType="INTEGER" />
     <result column="name" property="name" jdbcType="VARCHAR" />
     <result column="password" property="password" jdbcType="VARCHAR" />
     </resultMap>
     ...
     </mapper>
     ```
     ```xml
     <mapper namespace="xyz.redrain.UserMapper" >
       <resultMap id="BaseResultMap" type="User" />
     ...
     </mapper>
     ```
4. mapper 接口继承 
    ```java
    public interface UserMapper extends BaseMapper<User> {

    }
    ```
5. POJO对象与表映射

    1. 必填 主键必须加上`@Id`注解,且属性名必须为id
    2. 非必填 `@Table`为表名
    3. 非必填 `@Column` 为列名
    4. 非必填 `@Ignore`不映射
    5. 非必填 `@JavaType`设置Java类型
    
    ```java
        @Table("t_user")
        public class User {
            @Id()
            private Integer id;
            private String userName;
            @Column(jdbcType = "varchar")
            private String password;
            private Date time;
            @Ignore
            private Date time1;
           //getter,setter
        }
    ``` 
6. mybatis与spring集成：
    
    1. spring配置文档
        ```xml
        <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
            <property name="basePackage" value="xxxxxxxx"/>
            <property name="markerInterface" value="BaseMapper"/>
        </bean>  
        ```
                  
    