<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/blog-admin-menu.jsp"/>
				<form:form modelAttribute="postVo" id="post" name ="post"
					action="${pageContext.request.contextPath}/${authUser.id}/admin/write" method="post">
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<form:input path="title"/>
				      			<select name="category_no">
			      					<c:forEach items="${categoryList }" var="vo" varStatus="status">
			      						<c:choose>
			      							<c:when test="${vo.name eq '기본'}">
			      								<option value="${vo.no}" selected="selected">${vo.name }</option>
			      							</c:when>
			      							<c:otherwise>
			      								<option value="${vo.no}">${vo.name }</option>
			      							</c:otherwise>
			      						</c:choose>
				      					
				      				</c:forEach>
				      			</select>
				      		</td>
			      		</tr>
			      		<tr><td colspan="2"><p class="validCheck"><form:errors path="title"/></p></td></tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td><form:textarea path="content"></form:textarea></td>
			      		</tr>
			      		<tr><td colspan="2"><p class="validCheck"><form:errors path="content"/></p></td></tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="submit" value="포스트하기"></td>
			      		</tr>
			      	</table>
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
	</div>
</body>
</html>