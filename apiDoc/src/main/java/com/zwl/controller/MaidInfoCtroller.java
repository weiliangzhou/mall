//package com.zwl.controller;
//
//import com.terran4j.commons.api2doc.annotations.Api2Doc;
//import com.terran4j.commons.api2doc.annotations.ApiComment;
//import com.zwl.model.vo.XiaXianVVo;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author 二师兄超级帅
// * @Title: MaidInfoCtroller
// * @ProjectName parent
// * @Description: TODO
// * @date 2018/7/1311:27
// */
//@Api2Doc(name = "下线列表")
//@RestController
//public class MaidInfoCtroller {
//    @ApiComment(seeClass = XiaXianVVo.class)
//    @RequestMapping(name = "下线列表",
//            value = "/teacher/getXiaXianList", method = RequestMethod.POST)
//    public XiaXianVVo getXiaXianList(@ApiComment("userId") String userId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
//        XiaXianVVo xiaXianVVo = new XiaXianVVo();
//        return xiaXianVVo;
//    }
//
//}
