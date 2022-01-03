<%--
  Created by IntelliJ IDEA.
  User: kukjunlee
  Date: 2021/12/21
  Time: 5:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="background-color: #00008b; color: #ffffff; height: 20px; padding: 5px;">
    SPMS(Simple Project Management System)
    <span style="float: right;">
        ${member.name}
        <a style="color: white;"
           href="${request.getContextPath()}/auth/logout.do">로그아웃</a>
    </span>
</div>
