<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>主页面</title>
</head>
<body>
    <h1 align="center">主页面</h1>
    <!--通过方法登出-->
    <a href="/user/logout.do">退出</a><br/>

    <!-- 通过过滤器登出-->
    <a href="/login/exit">退出2</a>

    <hr />

    <shiro:hasRole name="stu">
        <a href="/student.jsp">学生界面</a><br/>
    </shiro:hasRole>

    <shiro:hasRole name="tea">
        <a href="/teacher.jsp">老师界面</a>
    </shiro:hasRole>

    <shiro:hasRole name="stu">
        <shiro:hasRole name="tea">
            <a href="/list.jsp">列表页面（拥有stu和tea才能访问）</a>
        </shiro:hasRole>
    </shiro:hasRole>

    <shiro:lacksRole name="stu">
        我没有stu角色
    </shiro:lacksRole>
    <shiro:lacksRole name="tea">
        我没有tea角色
    </shiro:lacksRole>

</body>
</html>
