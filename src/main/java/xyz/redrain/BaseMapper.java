package xyz.redrain;

import org.apache.ibatis.annotations.*;
import xyz.redrain.helper.DeleteHelper;
import xyz.redrain.helper.InsertHelper;
import xyz.redrain.helper.SelectHelper;
import xyz.redrain.helper.UpdateHelper;

import java.util.List;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public interface BaseMapper<T> {

    /**
     * 插入数据
     *
     * @param param 数据
     * @return 影响条数
     */
    @InsertProvider(type = InsertHelper.class, method = "insertObj")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertObj(T param);

    /**
     * 插入数据（去除为NULL的属性）
     *
     * @param param 数据
     * @return 影响条数
     */
    @InsertProvider(type = InsertHelper.class, method = "insertObjSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertObjSelective(T param);

    /**
     * 通过参数里的Id更新数据
     *
     * @param param 参数 id不为空
     * @return 影响条数
     */
    @UpdateProvider(type = UpdateHelper.class, method = "updateObjById")
    Integer updateObjById(T param);

    /**
     * 通过参数里的Id更新数据（去除为NULL的属性）
     *
     * @param param 参数 id不为空
     * @return 影响条数
     */
    @UpdateProvider(type = UpdateHelper.class, method = "updateObjSelectiveById")
    Integer updateObjSelectiveById(T param);

    /**
     * 通过参数里的Id删除数据
     *
     * @param param 参数 id不为空
     * @return 影响条数
     */
    @DeleteProvider(type = DeleteHelper.class, method = "deleteObjById")
    Integer deleteObjById(T param);

    /**
     * 通过参数删除数据
     *
     * @param param 参数
     * @return 影响条数
     */
    @DeleteProvider(type = DeleteHelper.class, method = "deleteObjByParams")
    Integer deleteObjByParams(T param);

    /**
     * 通过参数里的Id查询数据
     *
     * @param param 参数 id不为空
     * @return 数据
     */
    @SelectProvider(type = SelectHelper.class, method = "selectObjById")
    T selectObjById(T param);

    /**
     * 通过参数查询数据
     *
     * @param param 参数
     * @return 数据
     */
    @SelectProvider(type = SelectHelper.class, method = "selectObjByParams")
    T selectObjByParams(T param);

    /**
     * 通过参数批量查询数据
     *
     * @param param 参数
     * @return 数据
     */
    @SelectProvider(type = SelectHelper.class, method = "selectListByParams")
    List<T> selectListByParams(T param);

    /**
     * 通过参数分页查询数据
     * limit {offset},{limit}
     * 当offset为NULL时
     * limit {limit}
     *
     * @param param  参数
     * @param offset 开始位置，可NULL
     * @param limit  分页大小
     * @return 数据
     */
    @SelectProvider(type = SelectHelper.class, method = "selectListByParamsPages")
    List<T> selectListByParamsPages(@Param("param") T param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * count(*)
     * 通过参数查询数据条数
     *
     * @param param 参数
     * @return 条数
     */
    @SelectProvider(type = SelectHelper.class, method = "countByParams")
    long countByParams(T param);

}
