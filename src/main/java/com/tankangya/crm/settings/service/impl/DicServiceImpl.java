package com.tankangya.crm.settings.service.impl;

import com.tankangya.crm.settings.dao.DicTypeDao;
import com.tankangya.crm.settings.dao.DicValueDao;
import com.tankangya.crm.settings.entity.DicType;
import com.tankangya.crm.settings.entity.DicValue;
import com.tankangya.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<>();

        List<DicType> dtList = dicTypeDao.getDicTypeList();

        for (DicType dt: dtList) {
            String code = dt.getCode();
            List<DicValue> dvList = dicValueDao.getDicValueList(code);
            map.put(code+"List",dvList);
        }

        return map;
    }
}
