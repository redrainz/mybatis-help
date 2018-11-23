## mybatis-help

1. 使用mybatis的mapper接口方法
2. mapper 接口继承 
    ```java
    public interface UserMapper extends BaseMapper<User> {

    }
    ```
3. POJO对象与表映射

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
4. mybatis与spring集成：
    1. mybatis:3.4.0+
    2. mybatis-spring:1.3.0+
    3. spring配置文档
        ```xml
        <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
            <property name="basePackage" value="xxxxxxxx"/>
            <property name="markerInterface" value="com.redrain.BaseMapper"/>
        </bean>  
        ```
                  
    