package com.yyz.web.cotroller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UsersController {
    /**
     * 登录功能
     * 登录失败回到登录界面login.jsp
     * 登录成功进入主界面mmain.jsp
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,@RequestParam("password") String password){

        ModelAndView model = new ModelAndView();

        // get the currently executing user:   得到当前正在执行的用户
        Subject currentUser = SecurityUtils.getSubject();

        //让我们登录当前用户，以便我们检查角色和权限
        if (!currentUser.isAuthenticated()) { //当前Subject是否进行认证（登录）
            //前台用户传入的用户名和密码 (将用户名和密码封装到UsernamePasswordToken对象中）
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);//记住我
            try {
                //进行认证（登录）功能
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                //未知帐户异常(用户不存在)
                System.out.println(uae.getMessage());
                model.addObject("msg","用户名不存在");
                model.setViewName("/login.jsp");
                return model;
            } catch (IncorrectCredentialsException ice) {
                //凭证匹配器异常 不正确的凭据异常（密码错误）
                System.out.println(ice.getMessage());
                model.addObject("msg","密码错误");
                model.setViewName("/login.jsp");
                return model;
            } catch (LockedAccountException lae) {
                //帐户锁定异常 锁定帐户例外  (将来要在业务逻辑中进行判断）
                System.out.println(lae.getMessage());
                model.addObject("msg","用户被锁定");
                model.setViewName("/login.jsp");
                return model;
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                // 认证异常 身份验证异常
                return null;
            }
        }

        model.setViewName("redirect:/main.jsp");
        return  model;
    }

    /**
     * 退出功能
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(){
        Subject currentUser = SecurityUtils.getSubject();
        ModelAndView model = new ModelAndView();

        model.setViewName("/login.jsp");
        //退出登录
        currentUser.logout();
        return model;
    }

}
