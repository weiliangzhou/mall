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

    User getUserByOpenIdAndMerchantId(@Param("openId") String openId, @Param("merchantId") String merchantId);

    User getUserByGzhOpenIdAndMerchantId(@Param("openId") String openId, @Param("merchantId") String merchantId);

    User getUserByPhone(@Param("phone") String phone);

    void updateUserPhoneByUserId(@Param("userId") String userId, @Param("phone") String phone);

    void updateUserGzhOpenIdByUserId(@Param("userId") String userId, @Param("gzhOpenId") String gzhOpenId);

    void updateUserWechatOpenidByUserId(@Param("userId") String userId, @Param("wechatOpenId") String wechatOpenId);

    @Update("update ss_user set register_mobile =#{registerMobile} where wechat_openid=#{openid}")
    void updateRegisterMobileByWxOpenId(@Param("registerMobile") String registerMobile, @Param("openid") String openid);

    @Select(" SELECT count(*) from ss_user where member_level =6   and referrer=#{userId} group by referrer HAVING count(*) >=10")
    Integer getXzCountByUserId(String userId);

    List<User> getAllList();
}
