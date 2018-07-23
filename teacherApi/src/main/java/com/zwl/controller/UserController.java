package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.groups.QueryInfo;
import com.zwl.model.po.OperateUserRecord;
import com.zwl.model.po.User;
import com.zwl.model.vo.PageUserVo;
import com.zwl.model.vo.UserQueryVo;
import com.zwl.model.vo.UserVo;
import com.zwl.service.MaidInfoService;
import com.zwl.service.OperateUserRecordService;
import com.zwl.service.UserService;
import com.zwl.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员管理controller
 */
@RequestMapping("/teacher/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OperateUserRecordService operateUserRecordService;
    @Autowired
    private MaidInfoService maidInfoService;

    /**
     * 商户后台 添加用户
     */
    @PostMapping("/add")
    public Result add(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String realName = jsonObject.getString("realName");
        String merchantId = jsonObject.getString("merchantId");
        String registerMobile = jsonObject.getString("registerMobile");
//        String referrerMobile = jsonObject.getString("referrerMobile");
        Integer memberLevel = jsonObject.getInteger("memberLevel");
        String levelName = jsonObject.getString("levelName");
        String operator = jsonObject.getString("operator");

        //根据导入的注册手机号，查找
        User query = new User();
        query.setRegisterMobile(registerMobile);
//        query.setRealName(realName);
        query.setMerchantId(merchantId);

        User user = userService.getOneByParams(query);

        if (StringUtils.isEmpty(user)) {
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("查无此手机号和此姓名，请确保手机号为：" + registerMobile + "已经完成绑定手机号");
//            result.setMessage("查无此手机号和此姓名，请确保手机号为："+registerMobile+"已经完成绑定手机号和实名认证");
            return result;
        }
        //更新用户会员等级 和 姓名
        User userParams = new User();
        userParams.setUserId(user.getUserId());
        userParams.setMemberLevel(memberLevel);
        if(memberLevel==1){
            levelName="小班";
        } else if(memberLevel==4){
            levelName="学员";
        }else if(memberLevel==5){
            levelName="班长";
        }else if(memberLevel==6){
            levelName="院长";
        }
        userParams.setLevelName(levelName);
        userParams.setRealName(realName);
        //用户购买会员渠道，2-线下渠道
        userParams.setRegisterFrom(2);
        userService.updateUserByUserId(userParams);

        //记录操作表
        OperateUserRecord operateUserRecord = new OperateUserRecord();
        operateUserRecord.setOperator(operator);
        operateUserRecord.setBeforeLevel(user.getMemberLevel());
        operateUserRecord.setAfterLevel(memberLevel);
        operateUserRecord.setUserId(user.getUserId());
        operateUserRecord.setMerchantId(merchantId);
        operateUserRecordService.add(operateUserRecord);

        return result;
    }

    /**
     * 根据会员等级获取用户列表
     *
     * @param userQueryVo
     * @return
     */
    @PostMapping("/getUserList")
    public String getPageList(@Validated(QueryInfo.class) @RequestBody UserQueryVo userQueryVo) {
        Result result = new Result();
        Integer pageNum = userQueryVo.getPageNum();
        Integer pageSize = userQueryVo.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userService.getUserListByMerchantId(userQueryVo);
        List<UserVo> listVo = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getUserId());
            userVo.setRealName(user.getRealName());
            userVo.setMemberLevel(user.getMemberLevel());
            userVo.setLevelName(user.getLevelName());
            userVo.setRegisterMobile(user.getRegisterMobile());
            userVo.setModifyTime(DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", user.getModifyTime()));
            userVo.setRegisterFrom(user.getRegisterFrom());
            userVo.setRegisterTime(DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", user.getRegisterTime()));
            //存在推荐人为null
            User userTemp = userService.getByUserId(user.getReferrer());
            userVo.setReferrerRealName(userTemp == null ? "" : userTemp.getRealName());
            //下线人数
            Integer xiaxianCount = maidInfoService.getXiaXianCountByUserId(user.getUserId());
            userVo.setXiaxianCount(StringUtils.isEmpty(xiaxianCount) ? 0 : xiaxianCount);
            listVo.add(userVo);
        }

        PageUserVo pageVo = new PageUserVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        result.setData(pageVo);
        return JSON.toJSONString(result);
    }

    /**
     * 商户后台 修改用户等级
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String userId = jsonObject.getString("userId");
        String merchantId = jsonObject.getString("merchantId");
        Integer memberLevel = jsonObject.getInteger("memberLevel");
        String operator = jsonObject.getString("operator");
        String levelName = jsonObject.getString("levelName");

        //更新用户会员等级
        User userParams = new User();
        userParams.setUserId(userId);
        userParams.setMemberLevel(memberLevel);
        userParams.setLevelName(levelName);
        //用户购买会员渠道，2-线下渠道
        userParams.setRegisterFrom(2);
        userService.updateUserByUserId(userParams);

        //根据userId查找
        User user = userService.getByUserId(userId);
        //记录操作表
        OperateUserRecord operateUserRecord = new OperateUserRecord();
        operateUserRecord.setOperator(operator);
        operateUserRecord.setBeforeLevel(user.getMemberLevel());
        operateUserRecord.setAfterLevel(memberLevel);
        operateUserRecord.setUserId(userId);
        operateUserRecord.setMerchantId(merchantId);
        operateUserRecordService.add(operateUserRecord);
        return result;
    }

    /**
     * 搜索会员列表
     * @param merchantId
     * @param registerFrom
     * @param registerMobile"
     */
    @PostMapping("search")
    public Result  search(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer registerFrom = jsonObject.getInteger("registerFrom");
        String registerMobile = jsonObject.getString("registerMobile");
        List<User> userList=userService.search(merchantId,registerMobile,registerFrom);
        if (StringUtils.isEmpty(userList))
            return result;
        List<UserVo> listVo = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getUserId());
            userVo.setRealName(user.getRealName());
            userVo.setMemberLevel(user.getMemberLevel());
            userVo.setLevelName(user.getLevelName());
            userVo.setRegisterMobile(user.getRegisterMobile());
            userVo.setModifyTime(DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", user.getModifyTime()));
            userVo.setRegisterFrom(user.getRegisterFrom());
            userVo.setRegisterTime(DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", user.getRegisterTime()));
            //存在推荐人为null
            User userTemp = userService.getByUserId(user.getReferrer());
            userVo.setReferrerRealName(userTemp == null ? "" : userTemp.getRealName());
            //下线人数
            Integer xiaxianCount = maidInfoService.getXiaXianCountByUserId(user.getUserId());
            userVo.setXiaxianCount(StringUtils.isEmpty(xiaxianCount) ? 0 : xiaxianCount);
            listVo.add(userVo);
        }
        result.setData(listVo);
        return result;
    }

}
