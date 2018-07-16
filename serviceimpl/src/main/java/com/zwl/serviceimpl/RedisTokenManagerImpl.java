package com.zwl.serviceimpl;

import com.zwl.model.po.TokenModel;
import com.zwl.service.TokenManager;
import com.zwl.util.Des3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄超级帅
 * @Title: TokenManager
 * @ProjectName parent
 * @Description: 通过Redis存储和验证token的实现类
 * @date 2018/7/615:26
 */
@Service
public class RedisTokenManagerImpl implements TokenManager {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public TokenModel createToken(String userId) {
        // 使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        // 存储到redis并设置过期时间
        stringRedisTemplate.boundValueOps(userId).set(token, 30, TimeUnit.MINUTES);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String token_a = Des3.decryptMode(authentication);
        String[] param = token_a.split("_");
        if (param.length != 2) {
            return null;
        }
        // 使用userId和源token简单拼接成的token，可以增加加密措施
        String userId = param[0];
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = stringRedisTemplate.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间  hyq
        stringRedisTemplate.boundValueOps(model.getUserId()).expire(30, TimeUnit.MINUTES);
        return true;
    }

    public void deleteToken(String userId) {
        stringRedisTemplate.delete(userId);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
