package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
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
public class ClassSetController {
    @Autowired
    private ClassSetService classSetService;
    @Autowired
    private ClassSetStatisticsService classSetStatisticsService;
    @Autowired
    private ClassInfoStatisticsService classInfoStatisticsService;
    @Autowired
    private ClassInfoService classInfoService;

    @PostMapping("/getPageAllClass")
    public Result getPageAllClass(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list = classSetService.getAllClassOrderById(merchantId);
        for (ClassVo classVo : list
                ) {
            if (classVo.getClassType() == 1) {
                ClassSetStatistics css = classSetStatisticsService.getByClassSetId(classVo.getId());
                classVo.setBrowseCount(css == null ? 0L : css.getBrowseCount());
                //    如果是堂，logo是节的可配置优先级），
                //    按照发布时间倒序
                String logoUrl = classInfoService.getLogoUrlByClassSetId(classVo.getId());
                classVo.setLogoUrl(logoUrl);
            }
            if (classVo.getClassType() == 2) {
                ClassInfoStatistics csi = classInfoStatisticsService.getByClassInfoId(classVo.getId());
                classVo.setBrowseCount(csi == null ? 0L : csi.getListenCount());
            }
        }

        PageClassVo pageClassVo = new PageClassVo();
        pageClassVo.setPageNum(pageNum);
        pageClassVo.setTotalPage(page.getTotal());
        pageClassVo.setList(list);
        result.setData(pageClassVo);
        return result;
    }

    /**
     * 浏览人数+1
     *
     * @return
     */
    @PostMapping("/setpAddBrowseCount")
    public Result setpAddBrowseCount(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
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
        return result;
    }
    /**
     * @param jsonObject
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = jsonObject.getLong("id");
        ClassSet classSet=classSetService.getById(id);
        if(classSet==null){
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无此套课，请确认套课id传入正确");
        }
        ClassVo classVo=new ClassVo();
        classVo.setId(classSet.getId());
        classVo.setTitle(classSet.getTitle());
        classVo.setContent(classSet.getContent());
        classVo.setLogoUrl(classSet.getBannerUrl());
        //浏览人数
        ClassSetStatistics css = classSetStatisticsService.getByClassSetId(id);
        classVo.setBrowseCount(css.getBrowseCount()==null?0:css.getBrowseCount());
        List<ClassInfo> classInfoList = classInfoService.getByClassSetId(id);
        classVo.setChildrenSize(CheckUtil.isEmpty(classInfoList)?0:classInfoList.size());

        //下属的节课程
       /* List<ClassInfo> classInfoList = classInfoService.getByClassSetId(id);
        List<ClassVo> children =new ArrayList<>();
        for (ClassInfo classInfo: classInfoList
                ) {
            ClassVo classVoTemp = new ClassVo();
            classVoTemp.setId(classInfo.getId());
            classVoTemp.setTitle(classInfo.getTitle());
            classVoTemp.setCreateTime(classInfo.getCreateTime());
            classVoTemp.setModifyTime(classInfo.getModifyTime());
            classVoTemp.setCategoryTitle(c.getCategoryTitle());
            children.add(classVoTemp);
        }
        classVo.setChildren(children);
*/

        result.setData(classVo);
        return result;
    }

}
