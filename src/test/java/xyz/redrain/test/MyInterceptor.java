package xyz.redrain.test;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by RedRain on 2020/3/14.
 *
 * @author RedRain
 * @version 1.0
 */

/**
 * 执行器Executor（update、query、commit、rollback等方法）；
 * 参数处理器ParameterHandler（getParameterObject、setParameters方法）；
 * 结果集处理器ResultSetHandler（handleResultSets、handleOutputParameters等方法）；
 * SQL语法构建器StatementHandler（prepare、parameterize、batch、update、query等方法）；
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class MyInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        Object value = metaObject.getValue("delegate.boundSql.sql");
        System.out.println("intercept");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        //System.out.println("plugin");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
