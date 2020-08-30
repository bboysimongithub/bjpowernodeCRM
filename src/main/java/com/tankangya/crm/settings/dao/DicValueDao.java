package com.tankangya.crm.settings.dao;

import com.tankangya.crm.settings.entity.DicType;
import com.tankangya.crm.settings.entity.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getDicValueList(String code);
}
