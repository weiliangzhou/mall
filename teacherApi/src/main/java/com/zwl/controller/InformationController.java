package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Information;
import com.zwl.model.vo.InformationVo;
import com.zwl.service.FileUploadService;
import com.zwl.service.InformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: 资讯列表
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1215:04
 */
@RequestMapping("/teacher/information")
@RestController
@Slf4j
public class InformationController {
    @Autowired
    private InformationService informationService;
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/getInformationList")
    public String getInformationList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String title = jsonObject.getString("title");
        Information information = new Information();
        information.setTitle(title);
        information.setMerchantId(merchantId);
        Result result = new Result();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Information> informationList = informationService.getInformationList(information);
        InformationVo informationVo = new InformationVo();
        informationVo.setTotalPage(page.getTotal());
        informationVo.setPageNum(pageNum);
        informationVo.setInformationList(informationList);
        result.setData(informationVo);
        return JSON.toJSONString(result);
    }

    @PostMapping("/add")
    public String addInformation(@Validated(Update.class) @RequestBody Information information) {
        Result result = new Result();
        int count = informationService.addInformation(information);
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/update")
    public String updateInformation(@Validated(Update.class) @RequestBody Information information, @RequestParam("ueditor") MultipartFile file) {
        Result result = new Result();
        int count = informationService.updateInformation(information);
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/delete")
    public String deleteInformation(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Integer id = jsonObject.getInteger("id");
        int count = informationService.deleteInformation(id);
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/upload")
    public String imageUpload(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        String url = fileUploadService.upload(file, 1);
        result.setData(url);
        return JSON.toJSONString(result);
    }


}
