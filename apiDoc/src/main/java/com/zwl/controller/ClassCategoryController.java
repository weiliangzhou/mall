package com.zwl.controller;

import com.github.pagehelper.PageHelper;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.PageResult;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.ClassCategory;
import com.zwl.service.ClassCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api2Doc(name = "课程分类")
@ApiComment(seeClass = ClassCategory.class)
@RestController
@RequestMapping("/admin/classcategory")
public class ClassCategoryController {
    @Autowired
    private ClassCategoryService classCategoryService;
    @ApiComment("获取课程分类列表")
    @RequestMapping(name = "获取课程分类列表",
            value = "/getPageList", method = RequestMethod.POST)
    public String getPageListByMerchantId(@ApiComment("商户id") String merchantId,@ApiComment("当前页码") Integer pageNum,
                                          @ApiComment("每页条数") Integer pageSize) {

        Result result = new Result();
       /* String merchantId=jsonObject.getString("merchantId");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");*/
        PageHelper.startPage(pageNum,pageSize);
        ClassCategory query = new ClassCategory();
        /*query.setMerchantId(merchantId);
        List<ClassCategory> listRuslt = classCategoryService.getListByParams(query);
        PageResult p=new PageResult(listRuslt);
*/
        ClassCategory classCategory=new ClassCategory();
        List<ClassCategory> list=new ArrayList<>();
        list.add(classCategory);
        PageResult p=new PageResult(list);
        result.setData(p);
        return com.alibaba.fastjson.JSON.toJSONString(result);

    }
}
