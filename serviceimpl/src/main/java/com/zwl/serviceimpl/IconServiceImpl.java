package com.zwl.serviceimpl;

import com.zwl.dao.mapper.IconMapper;
import com.zwl.model.po.Icon;
import com.zwl.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IconServiceImpl implements IconService {
    @Autowired
    private IconMapper iconMapper;
    @Override
    public List<Icon> getIconList(Icon icon) {
        return iconMapper.getIconList(icon);
    }

    @Override
    public int addIcon(Icon icon) {
        return iconMapper.insertSelective(icon);
    }

    @Override
    public int updateIcon(Icon icon) {
        return iconMapper.updateByPrimaryKeySelective(icon);
    }

    @Override
    public int deleteIcon(Integer id) {
        return iconMapper.deleteByPrimaryKey(id);
    }
}
