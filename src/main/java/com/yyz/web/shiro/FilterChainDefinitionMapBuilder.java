package com.yyz.web.shiro;

import com.yyz.pojo.Resources;
import com.yyz.service.IResourcesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterChainDefinitionMapBuilder {

    @Autowired
    private IResourcesService resourcesService;
    public LinkedHashMap<String,String> builder(){
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        //从数据库查询资源数据
        List<Resources> resourcesList = resourcesService.selectAllResources();

        for (Resources resources : resourcesList) {

            map.put(resources.getKey(),resources.getVal());

        }
        /*map.put("/login.jsp","anon");
        map.put("/user/login.do","anon");
        map.put("/login/exit","logout");
        map.put("/css/**","anon");
        map.put(" /images/**","anon");
        map.put("/js/**","anon");
        map.put("/student.jsp","roles[stu]");
        map.put("/teacher.jsp","roles[tea]");
        map.put("/teacher.jsp","roles[tea]");
        map.put("/list.jsp","roles[stu,tea]");
        map.put("/**","authc");*/
        /*
         /login.jsp = anon
                 /user/login.do=anon
                 /login/exit = logout
                 /css/**=anon
                 /images/**=anon
                 /js/**=anon
                 /student.jsp=roles[stu]
                 /teacher.jsp=roles[tea]
                 /list.jsp=roles[stu,tea]
                 /** = authc
        * */
        return map;
    }
}
