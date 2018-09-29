package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityThemeMapper;
import com.zwl.model.po.OfflineActivityTheme;
import com.zwl.service.OfflineActivityThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityThemeServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2911:30
 */
@Service
public class OfflineActivityThemeServiceImpl implements OfflineActivityThemeService {
    @Autowired
    private OfflineActivityThemeMapper offlineActivityThemeMapper;
    @Override
    public List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(String merchantId, String queryType) {
        return offlineActivityThemeMapper.getOfflineActivityThemeListByQueryType(merchantId,queryType);
    }
}
