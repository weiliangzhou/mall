package com.zwl.dao.mapper;

import com.zwl.model.po.User;
import com.zwl.model.vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {


    int deleteByPrimaryKey(Long id);

    /**
     * 新增用户
     *
     * @return userId
     */
    int insert(User user);

    /**
     * @param user
     * @return
     */
    User selectOneByParams(User user);

    User getUserByUserId(String userId);

    //    User getUserById(String userId);
    int updateUserByUserId(User user);

    Integer getMemberLevel(String userId);

    Integer getMaidPercentByUserId(String userId);

    /**
     * 根据用户参数获取list
     *
     * @param user
     * @return
     */
    List<User> selectListByParams(User user);

    /**
     * 获取用户列表
     *
     * @param merchantId
     * @param queryType:普通会员列表 0
     * @param queryType:付费会员列表 1
     * @return
     */
    List<User> getUserListByMerchantId(UserQueryVo userQueryVo);

    /**
     * 搜索
     *
     * @return
     */
    List<User> search(@Param("merchantId") String merchantId, @Param("registerMobile") String registerMobile, @Param("registerFrom") Integer registerFrom);

    User getReferrerByUserId(String userId);

    Integer getTotalPerformanceByUserId(String userId);

    @Select("select count(*) from ss_user where register_mobile=#{registerMobile} and is_stock_data = 0")
    int findStockData(@Param("registerMobile") String registerMobile);

    @Update("update ss_user set wechat_openid=#{openid} , is_stock_data =1 where register_mobile=#{registerMobile} ")
    int updateUserStockDataByRegisterMobile(@Param("registerMobile") String registerMobile, @Param("openid") String openid);

    @Select("select user_id from ss_user where wechat_openid=#{openid}")
    String getUserIdByOpenId(@Param("openid") String openid);
}