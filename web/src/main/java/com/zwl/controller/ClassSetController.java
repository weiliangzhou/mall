package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.ClassSetStatistics;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.ClassVo;
import com.zwl.model.vo.PageClassVo;
import com.zwl.service.ClassInfoService;
import com.zwl.service.ClassSetService;
import com.zwl.service.ClassSetStatisticsService;
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
    private ClassInfoService classInfoService;

    @PostMapping("/getPageAllClass")
    public Result getPageAllClass(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list = classSetService.getAllClass(merchantId,null);
        for (ClassVo classVo : list
                ) {
            if (classVo.getClassType() == 1) {
                ClassSetStatistics css = classSetStatisticsService.getByClassSetId(classVo.getId());
                classVo.setBrowseCount(css == null ? 0 : css.getBrowseCount());
                //    如果是堂，logo是节的可配置优先级），
                //    按照发布时间倒序
                String logoUrl = classInfoService.getLogoUrlByClassSetId(classVo.getId());
                classVo.setLogoUrl(logoUrl);
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
            temp.setBrowseCount(1);
            classSetStatisticsService.add(temp);
        } else {
            classSetStatisticsService.setpAddBrowseCount(classSetId);
        }
        return result;
    }


}
