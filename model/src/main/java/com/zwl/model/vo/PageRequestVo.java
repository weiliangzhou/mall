package com.zwl.model.vo;

import lombok.Data;

@Data
public class PageRequestVo {
    //当前页码
    private Integer pageNum;
    //每页显示条数
    private Integer pageSize;
}
