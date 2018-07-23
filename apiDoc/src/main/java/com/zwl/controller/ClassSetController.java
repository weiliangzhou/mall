package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.PageClassVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 套课程controller
 */
@Api2Doc(name = "套课程管理")
@ApiComment(seeClass = ClassSet.class)
@RestController
public class ClassSetController {

    @ApiComment("新增套课程")
    @RequestMapping(name = "新增套课程",
            value = "/teacher/classset/add", method = RequestMethod.POST)
    public Result add(@ApiComment("title") String title, @ApiComment("bannerUrl") String bannerUrl, @ApiComment("categoryId") String categoryId
            , @ApiComment("merchantId") String merchantId, @ApiComment("requiredMemberLevel") Integer requiredMemberLevel, @ApiComment("content") String content
    ) {
        Result result = new Result();
        return result;
    }



    @ApiComment("修改套课程")
    @RequestMapping(name = "修改套课程",
            value = "/teacher/classset/modify", method = RequestMethod.POST)
    public Result modify(@ApiComment("删除和修改都要传id") Long id,@ApiComment("title") String title, @ApiComment("bannerUrl") String bannerUrl, @ApiComment("categoryId") Long categoryId
            ,  @ApiComment("requiredMemberLevel") Integer requiredMemberLevel, @ApiComment("content") String content,@ApiComment("删除时传available") String available) {
        Result result = new Result();

        return result;
    }

 /*   @ApiComment("删除套课程")
    @RequestMapping(name = "修改套课程",
            value = "/teacher/classset/modify", method = RequestMethod.POST)
    public Result modify2(@ApiComment("id") Long id,@ApiComment("available") String available) {
        Result result = new Result();

        return result;
    }
*/

    @ApiComment("课程列表")
    @RequestMapping(name = "课程列表",
            value = "/teacher/classset/getPageAllClass", method = RequestMethod.POST)
    public PageClassVo getPageAllClass(@ApiComment("merchantId") String merchantId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
        PageClassVo pageClassVo=new PageClassVo();
        return pageClassVo;
    }

    @ApiComment("搜索")
    @RequestMapping(name = "搜索",
            value = "/teacher/classset/search", method = RequestMethod.POST)
    public PageClassVo search(@ApiComment("merchantId") String merchantId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize,@ApiComment("title") String title) {
        PageClassVo pageClassVo=new PageClassVo();
        return pageClassVo;
    }

    @ApiComment("根据id查询套课程")
    @RequestMapping(name = "根据id查询套课程",
            value = "/teacher/classset/getById", method = RequestMethod.POST)
    public ClassSet getById(@ApiComment("id") Long id) {
        ClassSet classSet=new ClassSet();
        return classSet;
    }


    @ApiComment("根据分类id获取套课列表")
    @RequestMapping(name = "根据分类id获取套课列表",
            value = "/teacher/classset/getClassSetItemsList", method = RequestMethod.POST)
    public  List<ClassSetItemVo> getClassSetItemsList(@ApiComment("merchantId") String merchantId,@ApiComment("categoryId") String categoryId) {
        List<ClassSetItemVo> classSetItemVoList=new ArrayList<>();
        return classSetItemVoList;
    }


}
