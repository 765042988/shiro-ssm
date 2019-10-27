<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>" />
    <title>用户登录</title>
</head>
<body>
    <h1 align="center">登录界面</h1>

    <shiro:authenticated>登录成功，您的用户名为：<shiro:principal /> </shiro:authenticated>

    <shiro:notAuthenticated>
        <form action="/user/login.do" method="post">
            <table align="center" width="400px">
                <tr>
                    <td>用户名：</td>
                    <td>
                        <input type="text" name="username" />
                    </td>
                </tr>

                <tr>
                    <td>密码：</td>
                    <td>
                        <input type="password" name="password" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <div style="color: red;font-size: 16px;">
                            ${msg}
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" name="登录" />
                    </td>
                </tr>
            </table>
        </form>
    </shiro:notAuthenticated>
</body>
</html>
