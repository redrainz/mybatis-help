## mybatis-help

1. 功能
   1. 自动生成简单的增删改查功能。
   2. 侵入性低，使用简单，继承BaseMapper即可
1. 要求
   1. mybatis:3.4.0+
2. maven引入
     ```xml
       <dependency>
         <groupId>xyz.redrain</groupId>
         <artifactId>mybatis-help</artifactId>
         <version>1.0.6</version>
       </dependency>
     ```  

3. mapper 接口继承
    ```java
    public interface UserMapper extends BaseMapper<User> {

    }
    ```

4. POJO对象与表映射

   1. 非必填 主键属性名必须为id,可加上`@Id`注解
   2. 非必填 `@Table`为表名 可选表名或属性名是否映射成下划线模式
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

5. 1.0.2 增加了 是否选择映射下划线模式,当Table为空时，取默认值

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
6. 1.0.5 增加了 countByParams方法