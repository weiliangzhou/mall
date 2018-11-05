package com.zwl.model.baseresult;


import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一的Json返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @ApiComment(value = "code", sample = "200 205 4031 900 ")
    private String code;
    @ApiComment(value = "code", sample = "成功 操作失败 参数为空 token无效")
    private String message;
    @ApiComment(value = "data", sample = "数据")
    private Object data;
}
