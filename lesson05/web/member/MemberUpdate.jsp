<%--
  Created by IntelliJ IDEA.
  User: kukjunlee
  Date: 2021/12/22
  Time: 10:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>회원정보</title>
</head>
<body>
<h1>회원정보</h1>
<form action="/member/update" method="post">
    번호: <input type="text" name="no" value="${updateMember.getNo()}" readonly><br>
    이름: <input type="text" name="name" value="${updateMember.getName()}"><br>
    이메일: <input type="text" name="email" value="${updateMember.getEmail()}"><br>
    가입일: ${updateMember.createDate}<br>
    <input type="submit" value="저장">
    <input type="button" value="삭제" onclick="location.href='delete?no=${updateMember.getNo()}'">
    <input type="button" value="취소" onclick="location.href='list'">
</form>
</body>
</html>
