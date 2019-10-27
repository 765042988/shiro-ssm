package com.yyz.mapper;

import com.yyz.pojo.Resources;

import java.util.List;

public interface IResourcesMapper {
    //查找所有的状态有效的资源，且按sortnum升序排列
    public List<Resources> selectAllResources();
}
