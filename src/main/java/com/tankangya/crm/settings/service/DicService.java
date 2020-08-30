package com.tankangya.crm.settings.service;

import com.tankangya.crm.settings.entity.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    Map<String, List<DicValue>> getAll();
}
