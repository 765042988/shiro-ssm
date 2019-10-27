package com.yyz.service;

import com.yyz.pojo.Resources;

import java.util.List;

public interface IResourcesService {

    //查找所有的状态有效的资源，且按sortnum升序排列
    public List<Resources> selectAllResources();
}
