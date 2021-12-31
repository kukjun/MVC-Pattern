<%--
  Created by IntelliJ IDEA.
  User: kukjunlee
  Date: 2021/11/29
  Time: 2:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
  public static class MyMember {
    int no;
    String name;

    public int getNo() {
      return no;
    }

    public void setNo(int no) {
      this.no = no;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
%>
<%
  MyMember member = new MyMember();
  member.setNo(100);
  member.setName("홍길동");
  session.setAttribute("member", member);
%>
<html>
<head>
  <title>Test</title>
</head>
<body>
<h3>객체의 프로퍼티 값 변경</h3>
${member.name}<br>
<c:set target="${member}" property="name" value="임꺽정"/>
${member.name}<br>
</body>
</html>
