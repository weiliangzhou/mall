package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.ClassCategory;
import com.zwl.model.vo.PageClassCategoryVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api2Doc(name = "课程分类")
@ApiComment(seeClass = ClassCategory.class)
@RestController
@RequestMapping("/teacher/classcategory")
public class ClassCategoryController {
    @ApiComment("分页获取课程分类列表")
    @RequestMapping(name = "获取课程分类列表",
            value = "/getPageList", method = RequestMethod.POST)
    public PageClassCategoryVo getPageListByMerchantId(@ApiComment("商户id") String merchantId,@ApiComment("当前页码") Integer pageNum,
                                          @ApiComment("每页条数") Integer pageSize) {
        PageClassCategoryVo pageClassCategoryVo=new PageClassCategoryVo();
        return pageClassCategoryVo;
    }

    @ApiComment(value = "新增课程分类", seeClass = ClassCategory.class)
    @RequestMapping(name = "新增课程分类",
            value = "/add", method = RequestMethod.POST)
    public Result add(@ApiComment("title") String title, @ApiComment("isShow") String isShow,
                                   @ApiComment("merchantId") String merchantId) {
        Result result = new Result();

        return result;
    }
}
