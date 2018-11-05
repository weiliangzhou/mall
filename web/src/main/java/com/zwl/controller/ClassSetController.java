package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.baseController.BaseController;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassInfoStatistics;
import com.zwl.model.po.ClassSet;
import com.zwl.model.po.ClassSetStatistics;
import com.zwl.model.vo.ClassVo;
import com.zwl.model.vo.PageClassVo;
import com.zwl.service.ClassInfoService;
import com.zwl.service.ClassInfoStatisticsService;
import com.zwl.service.ClassSetService;
import com.zwl.service.ClassSetStatisticsService;
import com.zwl.util.CheckUtil;
import com.zwl.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套课程controller
 */
@RequestMapping("/wx/classset")
@RestController
public class ClassSetController extends BaseController {
    @Autowired
    private ClassSetService classSetService;
    @Autowired
    private ClassSetStatisticsService classSetStatisticsService;
    @Autowired
    private ClassInfoStatisticsService classInfoStatisticsService;
    @Autowired
    private ClassInfoService classInfoService;
    private static final long CONSTANT_WAN = 10000L;

    @PostMapping("/getPageAllClass")
    public String getPageAllClass(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer queryType = jsonObject.getInteger("queryType");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list = classSetService.getAllClassOrderById(merchantId, queryType);
        for (ClassVo classVo : list
        ) {
            Long browseCount = 0L;
            //套课程
            if (classVo.getClassType() == 1) {
                List<ClassInfo> classInfoList = classInfoService.getByClassSetId(classVo.getId());
                if (CheckUtil.isNotEmpty(classInfoList)) {
                    for (ClassInfo c : classInfoList
                    ) {
                        ClassInfoStatistics csi = classInfoStatisticsService.getByClassInfoId(c.getId());
                        Long temp = csi == null || csi.getListenCount() == null ? 0L : csi.getListenCount();
                        browseCount = temp + browseCount;
                    }
                }
                //    如果是堂，logo是节的可配置优先级），
                //    按照发布时间倒序
                String logoUrl = classInfoService.getLogoUrlByClassSetId(classVo.getId());
                classVo.setLogoUrl(logoUrl);
            }
            //单节课程
            if (classVo.getClassType() == 2) {
                ClassInfoStatistics csi = classInfoStatisticsService.getByClassInfoId(classVo.getId());
                browseCount = csi == null || csi.getListenCount() == null ? 0L : csi.getListenCount();
                //节课因没封面，封面设置为它的logo图片
                classVo.setFrontCover(classVo.getLogoUrl());
            }
            classVo.setBrowseCount(browseCount);
            String classListenCountDesc = String.valueOf(browseCount);
            if (browseCount >= CONSTANT_WAN) {
                classListenCountDesc = MathUtil.changeWan(classListenCountDesc) + "万";
            }
            classVo.setBrowseCountDesc(classListenCountDesc);
            classVo.setBrowseCountDesc2("人收听");
        }

        PageClassVo pageClassVo = new PageClassVo();
        pageClassVo.setPageNum(pageNum);
        pageClassVo.setTotalPage(page.getTotal());
        pageClassVo.setList(list);
        return setSuccessResult(pageClassVo);
    }

    /**
     * 浏览人数+1
     *
     * @return
     */
    @PostMapping("/setpAddBrowseCount")
    public String setpAddBrowseCount(@RequestBody JSONObject jsonObject) {
        Long classSetId = jsonObject.getLong("classSetId");
        ClassSetStatistics classSetStatistics = classSetStatisticsService.getByClassSetId(classSetId);
        if (StringUtils.isEmpty(classSetStatistics)) {
            ClassSetStatistics temp = new ClassSetStatistics();
            temp.setClassSetId(classSetId);
            temp.setBrowseCount(1L);
            classSetStatisticsService.add(temp);
        } else {
            classSetStatisticsService.setpAddBrowseCount(classSetId);
        }
        return setSuccessResult();
    }

    /**
     * @param jsonObject
     * @return
     */
    @PostMapping("/getById")
    public String getById(@RequestBody JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        ClassSet classSet = classSetService.getById(id);
        if (classSet == null) {
            return setFailResult(ResultCodeEnum.EXCEPTION + "", "查无此套课，请确认套课id传入正确");
        }
        ClassVo classVo = new ClassVo();
        classVo.setId(classSet.getId());
        classVo.setTitle(classSet.getTitle());
        classVo.setContent(classSet.getContent());
        classVo.setContentText(classSet.getContentText());
        classVo.setLogoUrl(classSet.getBannerUrl());
        //浏览人数
        List<ClassInfo> classInfoList = classInfoService.getByClassSetId(id);
        classVo.setChildrenSize(CheckUtil.isEmpty(classInfoList) ? 0 : classInfoList.size());
        if (CheckUtil.isNotEmpty(classInfoList)) {
            Long classListenCount = 0L;
            for (ClassInfo c : classInfoList
            ) {
                ClassInfoStatistics cis = classInfoStatisticsService.getByClassInfoId(c.getId());
                Long browseCount = cis == null || cis.getListenCount() == null ? 0L : cis.getListenCount();
                classListenCount += browseCount;
            }
            classVo.setBrowseCount(classListenCount);
            String classListenCountDesc = String.valueOf(classListenCount);
            if (classListenCount >= CONSTANT_WAN) {
                classListenCountDesc = MathUtil.changeWan(classListenCountDesc) + "万";
            }
            classVo.setBrowseCountDesc(classListenCountDesc + "人收听");
        }
        return setSuccessResult(classVo);
    }

}
