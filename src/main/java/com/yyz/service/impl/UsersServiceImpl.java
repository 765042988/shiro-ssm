package com.yyz.service.impl;

import com.yyz.mapper.IUsersMapper;
import com.yyz.pojo.Users;
import com.yyz.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private IUsersMapper usersMapper;

    /**
     * 通过用户名查找到用户
     * @param username 用户名
     * @return 用户
     */
    public Users selectUsers(String username) {

        Users users = usersMapper.selectUsers(username);

        return users;
    }

    /**
     * 通过用户名查找到用户所拥有的角色权限
     * @param username 用户名
     * @return 用户所拥有的权限的集合
     */
    public Set<String> selectUsersRoles(String username) {

        Set<String> roles = usersMapper.selectUsersRoles(username);

        return roles;
    }

    /**
     * 查询所有的角色权限
     * @return 所有权限的集合
     */
    public Set<String> selectAllRoles() {

        Set<String> roles = usersMapper.selectAllRoles();

        return roles;
    }
}
