package com.zwl.dao.mapper;

import com.zwl.model.po.MaidInfo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.model.vo.XiaXianVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MaidInfoMapper {
    int insertSelective(MaidInfo record);
    int updateByPrimaryKeySelective(MaidInfo record);
    List<MaidInfoVo> getMaidInfoList(String userId);
    List<XiaXianVo> getXiaXianList(String userId);
//    @Select("SELECT sum(so.actual_money*sp.maid_percent/100) FROM ss_user usr,ss_order so,ss_product sp WHERE usr.available=1 AND usr.referrer=#{userId} AND so.available=1 AND usr.user_id=so.user_id AND usr.member_level=sp.`level` and so.order_status=1")
    @Select("SELECT sum(maid_money) from ss_maid_info where user_id=#{userId} and available=1")
    Integer getTotalMaidMoneyByUserId(@Param("userId") String userId);
    Integer getXiaXianCountByUserId(String userId);
    @Select("select count(*) from ss_maid_info where user_id=#{userId}")
    Integer getMaidInfoCount(String userId);
}