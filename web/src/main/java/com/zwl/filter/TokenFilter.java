package com.zwl.filter;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.TokenModel;
import com.zwl.serviceimpl.RedisTokenManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 二师兄超级帅
 * @Title: TokenFilter
 * @ProjectName parent
 * @Description: token过滤器
 * @date 2018/7/615:26
 */
@Order(1)
// 重点
@WebFilter(filterName = "tokenFilter", urlPatterns = "/wx/*")
@Slf4j
public class TokenFilter implements Filter {

    @Autowired
    private RedisTokenManagerImpl manager;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest h_request = (HttpServletRequest) request;

        String token = request.getParameter("token");
        String requestURL = h_request.getRequestURL().toString();
        log.info("<<token>>请求url:" + requestURL + "  token:" + token);


        // 注册、登录、注册短信、首页、回调 不需要token
        if (requestURL.contains("/pay_notify.do")||requestURL.contains("/information/getInformationList")) {
            chain.doFilter(request, response);
            return;
        }
        if (requestURL.contains("/user/authorization")) {
            chain.doFilter(request, response);
            return;
        }
        //测试图片上传
        if (requestURL.contains("/file/*")) {
            chain.doFilter(request, response);
            return;
        }
        //H5支付
        if (requestURL.contains("/wx/product/H5Buy")||requestURL.contains("/wx/pay/auth/pay.do")) {
            chain.doFilter(request, response);
            return;
        }
        //发送验证码

        if (requestURL.contains("/wx/user/sendRegisterCode")) {
            chain.doFilter(request, response);
            return;
        }
        //套课程
        if (requestURL.contains("/classset/getPageAllClass")||requestURL.contains("/classset/setpAddBrowseCount")) {
            chain.doFilter(request, response);
            return;
        }
        //节课程
        if (requestURL.contains("/classinfo/getPageByClassSetId")||requestURL.contains("/classinfo/getById")
                ||requestURL.contains("/classinfo/setpAddBrowseCount")) {
            chain.doFilter(request, response);
            return;
        }
        //分享绑定上下级关系
        if (requestURL.contains("/user/shareRelation")) {
            chain.doFilter(request, response);
            return;
        }
        //用户信息展示
        if (requestURL.contains("/user/getUserInfoByUserId")) {
            chain.doFilter(request, response);
            return;
        }
        // 验证token
        // token="nprI2s/ITAaGaLJfL+QkZZYKhMAH3C3PkQEbpv+qQwOFG7hECEPeR4lKz5NTpQ177Sk1MdBj1GEjyNM2V0G2Nbj3FfKz6X+C";
        // 这里token如果接收有空格的地方，，那就是+号没有处理好。。可以考虑变成%2B
        token = token.replaceAll(" ", "+");
        TokenModel model = manager.getToken(token);
        if (manager.checkToken(model)) {
            // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
            // request.setAttribute(Constants.CURRENT_USER_ID, model.getName());
            // app请求就一次，所有session没有用处 除非pc
            // session.setAttribute(Constants.CURRENT_USER_ID,
            // model.getuserId());
            chain.doFilter(request, response);
        } else {
            // 如果验证token失败
            Result result = new Result();
            result.setCode(ResultCodeEnum.TOKEN_ERROR);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(JSON.toJSONString(result));
            return;
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}