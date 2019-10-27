package com.yyz.service.impl;

import com.yyz.mapper.IResourcesMapper;
import com.yyz.pojo.Resources;
import com.yyz.service.IResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesServiceImpl implements IResourcesService {

    @Autowired
    private IResourcesMapper resourcesMapper;
    /**
     *  查找所有的状态有效的资源，且按sortnum升序排列
     * @return 所有资源的集合
     */
    public List<Resources> selectAllResources() {

        List<Resources> resourcesList = resourcesMapper.selectAllResources();

        return resourcesList;
    }
}
