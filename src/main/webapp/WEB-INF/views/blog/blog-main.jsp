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
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty postVo }">
							<h4>해당 카테고리에는 글이 존재 하지 않습니다.</h4>
						</c:when>
						<c:otherwise>
							<h4>${postVo.title }</h4>
							<p>
								${postVo.content }
							<p>
						</c:otherwise>
					</c:choose>			
				
				
					
				</div>
				<ul class="blog-list">
					<c:forEach items="${postList}" var="vo">
						<li><a href="${pageContext.request.contextPath}/${adminId}/${vo.category_no}/${vo.no}">
								${vo.title }</a> <span>${vo.reg_date }</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/images/${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList}" var="vo">
					<li><a href="${pageContext.request.contextPath}/${vo.id}/${vo.no}">${vo.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
	</div>
</body>
</html>