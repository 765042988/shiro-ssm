package com.yyz.web.shiro;

import com.yyz.pojo.Users;
import com.yyz.service.IUsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired(required = true)
    private IUsersService usersService;
    /**
     * 用作认证功能
     * 1.改方法是在执行登录功能的时候执行
     * 2.获取到的token就是UsernamePasswordToken
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1.获取用户名
        UsernamePasswordToken auToken = (UsernamePasswordToken) token;
        String username = auToken.getUsername();
        //2.判断用户是否存在
        Users users = usersService.selectUsers(username);

        if(users == null){
            //3.用户不存在抛出异常
            throw new UnknownAccountException("用户名不存在");
        }
        //4.得到用户的状态（是否锁定）
        if(users.getStatus() == 0){
            throw new LockedAccountException("您的用户因违规被锁定");
        }
        //5.判断密码是否正确
        //密码的比较（前台的密码=数据库中查询的密码）  Shiro内部来完成的。
        // Object principal, Object credentials,ByteSource credentialSalt, String realmName (Realm接口的名字)
        /*
            身份验证：一般需要提供如身份 ID 等一些标识信息来表明登录者的身份，如提供 email，用户名/密码来证明。
            在 shiro 中，用户需要提供 principals （身份）和 credentials（证  明）给 shiro，从而应用能验证用户身份：
            •  principals：身份，即主体的标识属性，可以是任何属性，如用户名、  邮箱等，唯一即可。
                一个主体可以有多个 principals，但只有一个  Primary principals，一般是用户名/邮箱/手机号。
            •credentials：证明/凭证，即只有主体知道的安全值，如密码/数字证  书等。
            •	最常见的 principals 和 credentials 组合就是用户名/密码了
            credentialSalt（解决密码一样加密后密文一样的问题） 盐值的主体 一般为用户名
         */

        Object principals = username;
        //注意这里一定要是数据库中存的密码
        Object credentials = users.getPassword();
        //未使用盐值加密（相同的密码加密后的密文相同）
        //AuthenticationInfo info = new SimpleAuthenticationInfo(principals,credentials,super.getName());
        //如果密码错误，Shiro会在底层抛出IncorrectCredentialsException异常

        ByteSource credentialSalt = ByteSource.Util.bytes(username);//字符串：加盐的主体（一般为用户名）
        //采用盐值加密
        AuthenticationInfo info = new SimpleAuthenticationInfo(principals,credentials,credentialSalt,super.getName());
        return info;
    }

    /**
     * 用作授权功能
     * 1.该方法是在访问具有用户权限的页面时才会被调用
     *   但是当用户第一次访问该方法并且赋权限之后就不会再次访问该方法，如果换了一个用户还会访问该方法
     * 2.它的参数是 principals:身份，即主体的标识属性一般是用户名
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取用户名
        //一个主体可以有多个 principals，但只有一个  Primary principals，一般是用户名/邮箱/手机号。
        String username = (String) principals.getPrimaryPrincipal();

        //Set<String> roles = new HashSet<String>();

        //给不同的用户角色授权
        /*if(username.equals("zhangsan")){
            roles.add("stu");
        }else if (username.equals("haolaoshi")){
            roles.add("tea");
        }else if (username.equals("admin")){
            roles.add("stu");
            roles.add("tea");
        }*/
        //2.查询数据库根据用户名查询该用户具有哪些角色权限
        Set<String> roles = usersService.selectUsersRoles(username);

        if(roles.contains("admin")){
            roles = usersService.selectAllRoles();
        }
        AuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        return info;
    }


    /*public static void main(String[] args) {
        //注册时存入的数据
        //对数据库的密码按照相应规则加密
        *//**
         * hashAlgorithmName 加密码名称
         * credentials 要加密的密码
         * hashIterations 加密的次数
         *//*
        Object credentials="123456";
        String hashAlgorithmName ="MD5";
        String username="haolaoshi";  //用户名
        //加盐的主题一般为用户名
        Object salt=ByteSource.Util.bytes(username);
        int hashIterations=1902;  //加密次数
        Object result=new SimpleHash(hashAlgorithmName, credentials
                , salt, hashIterations);
        System.out.println(result);

    }*/
}
