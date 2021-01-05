## mybatis-help

1. 要求
   1. mybatis:3.4.0+
   2. mybatis-spring:1.3.0+
2. maven引入
     ```xml
       <dependency>
         <groupId>xyz.redrain</groupId>
         <artifactId>mybatis-help</artifactId>
         <version>1.0.6</version>
       </dependency>
     ```  
3. 使用mybatis的mapper接口方法
   1. mapper.xml中namespace为mapper全限定类名

     ```xml
     <mapper namespace="UserMapper" >
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

   1. 非必填 主键属性名必须为id,可加上`@Id`注解
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
            @UpdateSetNull
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
7. 1.0.2 增加了 是否选择映射下划线模式,当Table为空时，取默认值

    ```
    public @interface Table {
       /**
         * 属性是否映射成下划线模式
         * true  aB -> a_b
         * false aB -> aB
         *
         */
        boolean propertyUseUnderlineStitching() default true;
    
        /**
         * 表名是否映射成下划线模式
         * true  aB -> a_b
         * false aB -> aB
         */
        boolean tableUseUnderlineStitching() default true;
    }
   ```   
8. 1.0.5 增加了 countByParams方法