package com.zwl.service;

import com.zwl.model.po.Information;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: InformationService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1215:07
 */
public interface InformationService {
    List<Information> getInformationList(Information information);

    int addInformation(Information information);

    int updateInformation(Information information);

    int deleteInformation(Integer id);

    List<Information> getWxInformationList(Information information);

    List<Information> getInformationInfo(Information information);
}
