package com.zwl.dao.mapper;

import com.zwl.model.po.MaidInfo;
import com.zwl.model.vo.XiaXianVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MaidInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaidInfo record);

    int insertSelective(MaidInfo record);

    MaidInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaidInfo record);

    int updateByPrimaryKey(MaidInfo record);

    List<MaidInfo> getMaidInfoList(String userId);

    List<XiaXianVo> getXiaXianList(String userId);

    @Select("SELECT sum(so.actual_money*sp.maid_percent/100) FROM ss_user usr,ss_order so,ss_product sp WHERE usr.available=1 AND usr.referrer=#{userId} AND so.available=1 AND usr.user_id=so.user_id AND usr.member_level=sp.`level`")
    Integer getTotalMaidMoneyByUserId(@Param("userId") String userId);
}