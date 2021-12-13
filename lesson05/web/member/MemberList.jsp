<%--
  Created by IntelliJ IDEA.
  User: kukjunlee
  Date: 2021/12/13
  Time: 4:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="spms.vo.Member" %>
<%@page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>회원 목록</title>
</head>
<body>
<h1>회원 목록</h1>
<p><a href='add'>신규 회원</a> </p>
<%
    ArrayList<Member> members = (ArrayList<Member>) request.getAttribute("members");
    for (Member member : members) {
%>
<%=member.getNo()%>,
<a href='update?no=<%=member.getNo()%>'><%=member.getName()%></a>,
<%=member.getEmail()%>,
<%=member.getCreateDate()%>,
<a href='delete?no=<%=member.getNo()%>'>[삭제]</a><br>
<%} %>

</body>
</html>
