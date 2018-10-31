package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.Gift;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: GiftController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3111:40
 */
@Api2Doc(name = "书籍管理")
@RestController
@RequestMapping("/wx/gift")
public class GiftController {
    @RequestMapping(name = "书籍列表",
            value = "/wx/gift/getGiftList", method = RequestMethod.POST)
    public Gift getGiftList(@ApiComment("merchantId") String merchantId,
                            @ApiComment("queryType  0查询推荐列表 1查询全部") String queryType,
                            @ApiComment("pageSize") String pageSize,
                            @ApiComment("pageNum") String pageNum
    ) {
        Gift gift = new Gift();
        return gift;
    }
}
