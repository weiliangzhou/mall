package com.zwl.controller;

import com.zwl.dao.mapper.MaidInfoByMonthMapper;
import com.zwl.model.po.MaidInfoByMonth;
import com.zwl.model.po.MemberLevel;
import com.zwl.model.po.User;
import com.zwl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: XZFenYongTask
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/8/2015:38
 */
//@Configuration
//@EnableScheduling
@Slf4j
public class XZFenYongTask {
    @Autowired
    private UserService userService;
    @Autowired
    private MaidInfoByMonthMapper maidInfoByMonthMapper;

    //每个月的最后一天的23点59分
    //@Scheduled(cron = "0 59 23 L * ?")
    //每月最后一日的上午10:15触发
    //测试 3分钟执行一次
//    @Scheduled(cron = "0 */3 *  * * * ")
    public void xzfy() throws ParseException {
        log.info("开始校长分佣=======================>");
        //查询等级为校长的userid
        //遍历userid
        //统计下线直属的业绩
        User queryUser = new User();
        queryUser.setMemberLevel(MemberLevel.XZ);
        List<User> xzList = userService.getListByParams(queryUser);
        for (User xzUser : xzList) {
            if (xzUser == null)
                continue;
            log.info("开始校长分佣=======================>校长userid" + xzUser.getUserId());
            //根据校长的userid获取useridlist（非校长）
            String xzUserId = xzUser.getUserId();
            User queryAllUser = new User();
            queryAllUser.setReferrer(xzUserId);
            List<User> userList = userService.getListByParams(queryAllUser);
            Integer tdMaidMoney = 0;
            Integer zxMaidMoney = 0;
            Integer maidPercent = 0;
            Integer totalPerformanceWithXZ = 0;
            Integer totalPerformanceWithOutXZ = 0;
            for (User userTemp : userList) {
                if (userTemp == null)
                    continue;
//                log.info("开始校长分佣=======================>线下userid" + userTemp.getUserId());
                //统计非校长的总业绩（排除小班的业绩） 按照每月的新增业绩
                Integer memberLevel = userTemp.getMemberLevel() == null ? -1 : userTemp.getMemberLevel();
                String userIdTemp = userTemp.getUserId();

                //团队奖
                if (memberLevel <= MemberLevel.YZ) {
                    Integer totalPerformanceWithOutXZTemp = userService.getTotalPerformanceByUserId(userIdTemp);
                    totalPerformanceWithOutXZTemp = totalPerformanceWithOutXZTemp == null ? 0 : totalPerformanceWithOutXZTemp;
                    totalPerformanceWithOutXZ += totalPerformanceWithOutXZTemp;
                }
                //纵向奖
                else if (memberLevel == MemberLevel.XZ) {
                    Integer totalPerformanceWithXZTemp = userService.getTotalPerformanceByUserId(userIdTemp);
                    totalPerformanceWithXZTemp = totalPerformanceWithXZTemp == null ? 0 : totalPerformanceWithXZTemp;
                    totalPerformanceWithXZ += totalPerformanceWithXZTemp;
                }

            }

            //10万 2%
            //50万 4%
            //100万 6%
            //300万 8%
            if (totalPerformanceWithXZ >= 100000 && totalPerformanceWithXZ < 500000)
                maidPercent = 2;
            else if (totalPerformanceWithXZ >= 500000 && totalPerformanceWithXZ < 1000000)
                maidPercent = 4;
            else if (totalPerformanceWithXZ >= 1000000 && totalPerformanceWithXZ < 3000000)
                maidPercent = 6;
            else if (totalPerformanceWithXZ >= 3000000)
                maidPercent = 8;
            //团队奖
            tdMaidMoney = totalPerformanceWithOutXZ * 5/100;
            log.info("开始校长分佣=======================>团队奖总业绩" + totalPerformanceWithOutXZ);
            log.info("开始校长分佣=======================>团队奖" + tdMaidMoney);
            //纵向奖
            zxMaidMoney = totalPerformanceWithXZ * maidPercent/100;
            log.info("开始校长分佣=======================>纵向奖总业绩" + totalPerformanceWithXZ);
            log.info("开始校长分佣=======================>纵向奖" + zxMaidMoney);

            SimpleDateFormat sdf_yM = new SimpleDateFormat("yyyy-MM");
            String recordTime = sdf_yM.format(new Date());
            //记录到ss_maid_info_by_month
            //需要防重设置
            //根据当前校长id和当前时间查询当前月份是否有数据了，如果有则不执行插入动作
            //maidType 0 1
            MaidInfoByMonth maidInfoByMonth = new MaidInfoByMonth();
            maidInfoByMonth.setUserId(xzUserId);
            maidInfoByMonth.setMerchantId(xzUser.getMerchantId());
            maidInfoByMonth.setRecordTime(sdf_yM.parse(recordTime));
            if (tdMaidMoney > 0) {
                Integer maidType = 0;
                int existCount = maidInfoByMonthMapper.getExistCountByUserIdAndRecordTime(xzUserId, recordTime, maidType);
                if (existCount >= 1)
                    log.info("已经存在当月记录则不插入新数据");
                else {
                    maidInfoByMonth.setTotalPerformance(totalPerformanceWithOutXZ);
                    maidInfoByMonth.setMaidType(0);
                    maidInfoByMonth.setMaidMoney(tdMaidMoney);
                    maidInfoByMonth.setMaidPercent(5);
                    maidInfoByMonthMapper.insertSelective(maidInfoByMonth);
                    log.info("结束校长分佣=======================>团队奖" + tdMaidMoney);
                }

            }
            if (zxMaidMoney > 0) {
                Integer maidType = 1;
                int existCount = maidInfoByMonthMapper.getExistCountByUserIdAndRecordTime(xzUserId, recordTime, maidType);
                if (existCount >= 1)
                    log.info("已经存在当月记录则不插入新数据");
                else {
                    maidInfoByMonth.setTotalPerformance(totalPerformanceWithXZ);
                    maidInfoByMonth.setMaidType(1);
                    maidInfoByMonth.setMaidMoney(zxMaidMoney);
                    maidInfoByMonth.setMaidPercent(maidPercent);
                    maidInfoByMonthMapper.insertSelective(maidInfoByMonth);
                    log.info("结束校长分佣=======================>纵向奖" + tdMaidMoney);
                }
            }

        }
    }

}
