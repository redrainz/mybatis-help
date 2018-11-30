package com.redrain;

import com.redrain.helper.DeleteHelper;
import com.redrain.helper.InsertHelper;
import com.redrain.helper.SelectHelper;
import com.redrain.helper.UpdateHelper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public interface BaseMapper<T> {

    @InsertProvider(type = InsertHelper.class, method = "insertObj")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertObj(T param);

    @InsertProvider(type = InsertHelper.class, method = "insertObjSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertObjSelective(T param);

    @UpdateProvider(type = UpdateHelper.class, method = "updateObjById")
    Integer updateObjById(T param);

    @UpdateProvider(type = UpdateHelper.class, method = "updateObjSelectiveById")
    Integer updateObjSelectiveById(T param);

    @DeleteProvider(type = DeleteHelper.class, method = "deleteObjById")
    Integer deleteObjById(T param);

    @DeleteProvider(type = DeleteHelper.class, method = "deleteObjByParams")
    Integer deleteObjByParams(T param);

    @SelectProvider(type = SelectHelper.class, method = "selectObjById")
    @ResultMap("BaseResultMap")
    T selectObjById(T param);

    @SelectProvider(type = SelectHelper.class, method = "selectObjByParams")
    @ResultMap("BaseResultMap")
    T selectObjByParams(T param);

    @SelectProvider(type = SelectHelper.class, method = "selectListByParams")
    @ResultMap("BaseResultMap")
    List<T> selectListByParams(T param);

    @SelectProvider(type = SelectHelper.class, method = "selectListByParamsPages")
    @ResultMap("BaseResultMap")
    List<T> selectListByParamsPages(@Param("param") T param, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
