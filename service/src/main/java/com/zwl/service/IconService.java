package com.zwl.service;

import com.zwl.model.po.Icon;

import java.util.List;

public interface IconService {
    List<Icon> getIconList(Icon icon);

    int addIcon(Icon icon);

    int updateIcon(Icon icon);

    int deleteIcon(Integer id);
}
