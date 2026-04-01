package com.naixue.mapper;

import com.naixue.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 门店Mapper接口
 *
 * 对应数据库表: store
 * 主要功能:
 * - 查询所有可用门店
 * - 根据ID查询门店
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface StoreMapper {

    /**
     * 查询所有可见门店
     *
     * 只返回 is_show = 1 的门店,按ID排序
     *
     * @return 门店列表
     */
    @Select("SELECT * FROM store WHERE is_show = 1 ORDER BY id")
    List<Store> selectAll();

    /**
     * 根据ID查询门店
     *
     * @param id 门店ID
     * @return 门店信息,未找到返回null
     */
    @Select("SELECT * FROM store WHERE id = #{id}")
    Store selectById(Long id);
}
