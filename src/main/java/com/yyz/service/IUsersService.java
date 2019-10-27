package com.yyz.service;

import com.yyz.pojo.Users;

import java.util.Set;

public interface IUsersService {
    //根据用户名查找对象是否存在
    public Users selectUsers(String username);

    //根据用户名查找用户所拥有的角色权限
    public Set<String> selectUsersRoles(String username);

    //查询所有的角色权限
    public Set<String> selectAllRoles();
}
