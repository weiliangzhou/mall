package com.zwl.dao.mapper;

import com.zwl.model.Withdraw;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface WithdrawMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Withdraw record);

    int insertSelective(Withdraw record);

    Withdraw selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Withdraw record);

    int updateByPrimaryKey(Withdraw record);

    @Update("update ss_withdraw set status=#{status} ,operator=#{operator},success_time= now() where withdraw_id=#{withdrawId}")
    int updateWithdrawById(@Param("status") Integer status, @Param("operator") String operator, @Param("withdrawId") String withdrawId);

    List<Withdraw> getWithdrawList();

    Withdraw getByWithdrawId(String withdrawId);

    int updateByWithdrawId(String partner_trade_no, String partner_trade_no1, String payment_no);
}