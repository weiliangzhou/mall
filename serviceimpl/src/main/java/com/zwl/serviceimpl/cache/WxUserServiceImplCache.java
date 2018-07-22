package com.zwl.serviceimpl.cache;

import com.zwl.model.po.User;
import com.zwl.service.TokenManager;
import com.zwl.serviceimpl.WxUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 二师兄超级帅
 * @Title: WxUserServiceImplCache
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2210:21
 */
public class WxUserServiceImplCache extends WxUserServiceImpl {
    @Autowired
    private TokenManager tokenManager;

    @Override
    public User getUserByUserId(String token) {
        User user=new User();
        //先查询redis信息
        //通过userid查询登录用户的基本信息
        //如果没有调用父类，查询db


        return user;
    }


}
