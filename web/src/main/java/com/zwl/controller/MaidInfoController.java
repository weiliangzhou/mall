package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.baseresult.Result;
import com.zwl.model.MaidInfo;
import com.zwl.service.MaidInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1019:26
 */
@RestController
public class MaidInfoController {
    @Autowired
    private MaidInfoService maidInfoService;

    @PostMapping("/wx/getMaidInfoList")
    public String getMaidInfoList(@RequestBody JSONObject jsonObject) {
        String userId=jsonObject.getString("userId");
        Result result = new Result();
        List<MaidInfo> maidInfoList = maidInfoService.getMaidInfoList(userId);
        result.setData(maidInfoList);
        return JSON.toJSONString(result);

    }


}
