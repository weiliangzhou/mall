package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.ProductItemVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: 下拉框值
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2219:58
 */
@Api2Doc(name = "下拉框取值")
@RestController
public class ItemsListController {
    @ApiComment("根据分类ID获取套课下拉值")
    @RequestMapping(name = "根据分类ID获取套课下拉值",
            value = "/teacher/items/getClassSetItemsList", method = RequestMethod.POST)
    public List<ClassSetItemVo> addInformation(@ApiComment("商户号") String merchantId, @ApiComment("分类id") String categoryId) {
        List<ClassSetItemVo> classSetItemVoList = new ArrayList<>();
        return classSetItemVoList;
    }

    @ApiComment("根据merchantId获取会员等级下拉")
    @RequestMapping(name = "根据merchantId获取会员等级下拉",
            value = "/teacher/items/getUserLevelItemsList", method = RequestMethod.POST)
    public List<ProductItemVo> addInformation(@ApiComment("商户号") String merchantId) {
        List<ProductItemVo> productItemVoList = new ArrayList<>();
        return productItemVoList;
    }
}
