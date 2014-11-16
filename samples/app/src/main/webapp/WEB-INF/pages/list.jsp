<%@ page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="e" uri="http://lychie.github.io/tags/jetl" %>
<html>
  <head>
    <title>Squirrel — 分页示例</title>
    <c:set value="${pageContext.request.contextPath}" var="ctx" />
    <link href="${ctx}/styles/table.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/styles/style.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link href="${ctx}/styles/pagination.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/js/pagination.js" type="text/javascript"></script>
  </head>
  <body>
    <table class="bordered">
      <thead>
        <tr>
          <th>编号</th>
          <th>姓名</th>
          <th>性别</th>
          <th>部门</th>
          <th>职位</th>
          <th>工资</th>
          <th>入职时间</th>
        </tr>
      </thead>
      <c:forEach items="${p.resultSet}" var="item">
        <tr>
          <td>${item.id}</td>
          <td>${item.name}</td>
          <td>${item.sex}</td>
          <td>${item.deptName}</td>
          <td>${item.job}</td>
          <td>${item.salary}</td>
          <td>${item.hiredate}</td>
        </tr>
      </c:forEach>
    </table>
    <e:pagination 
      link="${ctx}/query/page/_index_" 
      pageitems="${p.pageItems}" 
      currentpage="${p.currentPage}" 
      totalamount="${p.totalAmount}" 
      dependent="false"
      style="margin-left:80px" />
  </body>
</html>