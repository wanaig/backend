package com.naixue.mapper;

import com.naixue.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 轮播图Mapper接口
 *
 * 对应数据库表: banner
 * 主要功能:
 * - 根据位置查询轮播图
 * - 查询所有轮播图
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface BannerMapper {

    /**
     * 根据位置查询轮播图
     *
     * @param position 位置 (如: "home")
     * @return 轮播图列表
     */
    @Select("SELECT * FROM banner WHERE status = 1 AND position = #{position} ORDER BY sort")
    List<Banner> selectByPosition(@Param("position") String position);

    /**
     * 查询所有轮播图
     *
     * @return 轮播图列表
     */
    @Select("SELECT * FROM banner WHERE status = 1 ORDER BY sort")
    List<Banner> selectAll();
}
