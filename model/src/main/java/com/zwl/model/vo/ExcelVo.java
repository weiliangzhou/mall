package com.zwl.model.vo;

import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: ExcelVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/12/2719:47
 */

public class ExcelVo {
    //    渠道
//    注册手机号
//    等级
//    注册时间
//    性别
//    真实姓名
//    消费金额
//    推荐人手机号
//    推荐人姓名
//    推荐人等级
//    上级
//    引荐人
//    card
    @ExcelConfig(head = "渠道", width = 5)
    private String registerFrom;
    @ExcelConfig(head = "注册手机号", width = 5)
    private String registerPhone;
    @ExcelConfig(head = "等级", width = 5)
    private Integer memberLevel;
    @ExcelConfig(head = "等级名称", width = 5)
    private String levelName;
    @ExcelConfig(head = "注册时间", width = 5)
    private Date registerTime;
    @ExcelConfig(head = "性别", width = 5)
    private String gender;
    @ExcelConfig(head = "真实姓名", width = 5)
    private String realName;
    @ExcelConfig(head = "消费金额", width = 5)
    private Integer consumeAmount;
    @ExcelConfig(head = "推荐人手机号", width = 5)
    private String refPhone;
    @ExcelConfig(head = "推荐人姓名", width = 5)
    private String refRealName;
    @ExcelConfig(head = "推荐人等级", width = 5)
    private Integer refMemberLevel;
    @ExcelConfig(head = "推荐人等级名称", width = 5)
    private String refLevelName;
    @ExcelConfig(head = "上级手机号码", width = 5)
    private String finalRefPhone;
    @ExcelConfig(head = "上级姓名", width = 5)
    private String finalRefName;
    @ExcelConfig(head = "上级等级", width = 5)
    private Integer finalRefMemberLevel;
    @ExcelConfig(head = "上级等级名称", width = 5)
    private String finalRefLevelName;
    @ExcelConfig(head = "引荐人手机", width = 5)
    private String recommendPhone;
    @ExcelConfig(head = "引荐人姓名", width = 5)
    private String recommendName;
    @ExcelConfig(head = "引荐人等级", width = 5)
    private Integer recommendMemberLevel;
    @ExcelConfig(head = "引荐人等级名称", width = 5)
    private String recommendLevelName;
    @ExcelConfig(head = "补卡数", width = 5)
    private int cardNum = 0;


    public String getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(String registerFrom) {
        this.registerFrom = registerFrom;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(Integer consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getRefPhone() {
        return refPhone;
    }

    public void setRefPhone(String refPhone) {
        this.refPhone = refPhone;
    }

    public String getRefRealName() {
        return refRealName;
    }

    public void setRefRealName(String refRealName) {
        this.refRealName = refRealName;
    }

    public Integer getRefMemberLevel() {
        return refMemberLevel;
    }

    public void setRefMemberLevel(Integer refMemberLevel) {
        this.refMemberLevel = refMemberLevel;
    }

    public String getRefLevelName() {
        return refLevelName;
    }

    public void setRefLevelName(String refLevelName) {
        this.refLevelName = refLevelName;
    }

    public String getFinalRefPhone() {
        return finalRefPhone;
    }

    public void setFinalRefPhone(String finalRefPhone) {
        this.finalRefPhone = finalRefPhone;
    }

    public String getFinalRefName() {
        return finalRefName;
    }

    public void setFinalRefName(String finalRefName) {
        this.finalRefName = finalRefName;
    }

    public Integer getFinalRefMemberLevel() {
        return finalRefMemberLevel;
    }

    public void setFinalRefMemberLevel(Integer finalRefMemberLevel) {
        this.finalRefMemberLevel = finalRefMemberLevel;
    }

    public String getFinalRefLevelName() {
        return finalRefLevelName;
    }

    public void setFinalRefLevelName(String finalRefLevelName) {
        this.finalRefLevelName = finalRefLevelName;
    }

    public String getRecommendPhone() {
        return recommendPhone;
    }

    public void setRecommendPhone(String recommendPhone) {
        this.recommendPhone = recommendPhone;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public Integer getRecommendMemberLevel() {
        return recommendMemberLevel;
    }

    public void setRecommendMemberLevel(Integer recommendMemberLevel) {
        this.recommendMemberLevel = recommendMemberLevel;
    }

    public String getRecommendLevelName() {
        return recommendLevelName;
    }

    public void setRecommendLevelName(String recommendLevelName) {
        this.recommendLevelName = recommendLevelName;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }
}
