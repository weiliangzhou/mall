package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.zwl.model.groups.ApplyWithdraw;
import com.zwl.model.groups.CertificationVal;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserCertification {
    private Long id;
    @ApiComment(value = "审核状态", sample = "0未提交1审核中 2审核通过 3未通过")
    private Integer status;
    @ApiComment(value = "审核人员", sample = "admin")
    private String operator;
    @NotBlank(message = "真实姓名不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "真实姓名", sample = "张三")
    private String realname;
    @NotBlank(message = "身份证不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "身份证", sample = "311588199410122531")
    private String idCard;
    @NotBlank(message = "身份证正面照不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "身份证正面照", sample = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%BA%AB%E4%BB%BD%E8%AF%81&step_word=&hs=0&pn=1&spn=0&di=142439799150&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=680756696%2C610110959&os=105825624%2C1083131701&simid=0%2C0&adpicid=0&lpn=0&ln=1867&fr=&fmq=1531297598795_R&fm=detail&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171111%2F8a2058c30d614674a4fa7e40cf171ebf.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bf5i7_z%26e3Bv54AzdH3FwAzdH3Fdan0dnm0m_lll000db&gsm=0&rpstart=0&rpnum=0&islist=&querylist=")
    private String img1Url;
    @NotBlank(message = "身份证国徽照不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "身份证国徽照", sample = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%BA%AB%E4%BB%BD%E8%AF%81&step_word=&hs=0&pn=5&spn=0&di=39185907220&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1501101382%2C1888955963&os=317452660%2C1755359320&simid=1171053660%2C579283596&adpicid=0&lpn=0&ln=1867&fr=&fmq=1531297598795_R&fm=detail&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=13&oriquery=&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%3Dpixel_huitu%2C0%2C0%2C294%2C40%2Fsign%3Db05d0b3c38fa828bc52e95a394672458%2Fd788d43f8794a4c2717d681205f41bd5ad6e39a8.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bi7tp7_z%26e3Bv54AzdH3Fri5p5AzdH3Ffi5oAzdH3Fda8ma98cAzdH3Fal9nc99bdda8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=")
    private String img2Url;
    @NotBlank(message = "手持身份证不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "手持身份证", sample = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%89%8B%E6%8C%81%E8%BA%AB%E4%BB%BD%E8%AF%81&step_word=&hs=0&pn=1&spn=0&di=19307542101&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=30378554%2C1776136751&os=2734060140%2C4109532893&simid=4231119681%2C617668854&adpicid=0&lpn=0&ln=1954&fr=&fmq=1531297750123_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Farticle.fd.zol-img.com.cn%2Ft_s640x2000%2Fg5%2FM00%2F0D%2F07%2FChMkJ1hbq4iIN6g-AADHtLmdducAAYyYADoKCkAAMfM815.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3F31_z%26e3Bz5s_z%26e3Bv54_z%26e3BvgAzdH3FmanAzdH3Fmana0ab_wss_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=")
    private String img3Url;
    @ApiComment(value = "用户id", sample = "ec28f6b9183f498eb9153f1593151aca")
    private String userId;
    @ApiComment(value = "商户id", sample = "0571XUDONGYA")
    private String merchantId;
    @ApiComment(value = "驳回原因", sample = "身份证号有误")
    private String rejectReason;
    @ApiComment(value = "审核时间", sample = "2018-7-11 16:41:06")
    private Date auditTime;
    @ApiComment(value = "创建时间", sample = "2018-7-11 16:41:43")
    private Date createTime;
    @ApiComment(value = "修改时间", sample = "2018-7-11 16:41:55")
    private Date modifyTime;
    @ApiComment(value = "可用与否", sample = "1")
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;

}