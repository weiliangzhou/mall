package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassInfoComment;
import com.zwl.model.po.ClassInfoStatistics;
import com.zwl.model.po.ClassSetStatistics;
import com.zwl.model.vo.ClassVo;
import com.zwl.model.vo.PageClassInfoVo;
import com.zwl.service.ClassCategoryService;
import com.zwl.service.ClassInfoCommentService;
import com.zwl.service.ClassInfoService;
import com.zwl.service.ClassInfoStatisticsService;
import com.zwl.util.CheckUtil;
import com.zwl.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 节课程controller
 */
@RequestMapping("/wx/classinfo")
@RestController
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ClassInfoStatisticsService classInfoStatisticsService;
    @Autowired
    private ClassInfoCommentService classInfoCommentService;

    private static final long CONSTANT_WAN=10000L;
    /**
     * 根据ClassSetId获取所属的节课程列表
     * @return
     */
    @PostMapping("/getPageByClassSetId")
    public Result getPageByClassSetId(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        Long classSetId = jsonObject.getLong("classSetId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String userId = jsonObject.getString("userId");
        String merchantId = jsonObject.getString("merchantId");
        Page page=PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> classInfoList = classInfoService.getByClassSetId(classSetId);
        List<ClassVo> listVo=new ArrayList<>();
        if(CheckUtil.isNotEmpty(classInfoList)){
            for (ClassInfo c:classInfoList
                 ) {
                ClassVo classVo = new ClassVo();
                classVo.setId(c.getId());
                classVo.setLogoUrl(c.getLogoUrl());
                classVo.setTitle(c.getTitle());
                classVo.setContentText(c.getContentText());
                classVo.setAudioUrl(c.getAudioUrl());
                classVo.setContent(c.getContent());

                ClassInfoStatistics cis = classInfoStatisticsService.getByClassInfoId(c.getId());
                Long browseCount = cis==null||cis.getListenCount() == null ? 0L : cis.getListenCount();
                String browseCountDesc=String.valueOf(browseCount);
                if(browseCount>CONSTANT_WAN){
                    browseCountDesc=MathUtil.changeWan(browseCountDesc)+"万";
                }
                classVo.setBrowseCount(browseCount);
                classVo.setBrowseCountDesc(browseCountDesc+"人收听");
                if(null != c.getPlayTime()){
                    Integer playTime = c.getPlayTime();
                    String playTimeDesc = playTime/60 + ":" + playTime%60;
                    classVo.setPlayTimeDesc(playTimeDesc);
                }
                classVo.setStyle(c.getStyle());
                classVo.setIsRecommend(c.getIsRecommend());
                ClassInfoComment classInfoComment = new ClassInfoComment();
                classInfoComment.setClassInfoId(c.getId());
                classInfoComment.setUserId(userId);
                classInfoComment.setMerchantId(merchantId);
                //查该用户该节课有无评论
                List<ClassInfoComment> classInfoCommentList = classInfoCommentService.getClassInfoCommentListByClassInfoId(classInfoComment);
                if(CollectionUtils.isEmpty(classInfoCommentList)){
                    //没有评论返回0，前端显示"课后感"，链接跳添加评论
                    classVo.setCommentType(0);
                }else {
                    //已评论返回1，前端显示"已读"，链接跳评论列表
                    classVo.setCommentType(1);
                }
                listVo.add(classVo);
           }
        }
        PageClassInfoVo pageVo=new PageClassInfoVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        result.setData(pageVo);
        return result;
    }

    /**
     * 根据Id获取节课程
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        Long id = jsonObject.getLong("id");
        ClassInfo classInfo=classInfoService.getById(id);
        result.setData(classInfo);
        return result;
    }

    /**
     * 浏览人数+1
     * @return
     */
    @PostMapping("/setpAddBrowseCount")
    public Result setpAddBrowseCount(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long classInfoId = jsonObject.getLong("classInfoId");
        ClassInfoStatistics classInfoStatistics= classInfoStatisticsService.getByClassInfoId(classInfoId);
        if(StringUtils.isEmpty(classInfoStatistics)) {
            ClassInfoStatistics temp = new ClassInfoStatistics();
            temp.setClassInfoId(classInfoId);
            temp.setListenCount(1L);
            classInfoStatisticsService.insert(temp);
        }else {
            classInfoStatisticsService.setpAddBrowseCount(classInfoId);
        }
        return result;
    }
}
