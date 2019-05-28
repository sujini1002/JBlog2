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
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		<c:set var='count' value='${fn:length(categoryList)}'/>
		      		<c:forEach items="${categoryList }" var="vo" varStatus="status">
					<tr>
						<td>${count-status.index }</td>
						<td>${vo.name }</td>
						<td>10</td>
						<td>${vo.description }</td>
						<td><a href="${pageContext.request.contextPath}/${authUser.id}/admin/category/delete"><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a></td>
					</tr>  
					</c:forEach>					  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      	<form id="category-form" method="post" action="${pageContext.servletContext.contextPath }/${authUser.id}/admin/category/add">
		      		<tr>
		      			<td class="t">카테고리명</td>
							<td><input name="name" value=""/>
							</td>
						</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input name="description" value=""/>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"/></td>
		      		</tr> 
		      	</form>     		      		
		      	</table> 
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
	</div>
</body>
</html>