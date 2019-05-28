<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1 class="logo">JBlog</h1>
<c:choose>

	<c:when test="${not empty authUser }">
		<ul class="menu">
			<li><a href="${pageContext.request.contextPath }">HOME</a></li>
			<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
			<li><a href="${pageContext.request.contextPath }/${authUser.id}">내블로그</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<ul class="menu">
			<li><a href="${pageContext.request.contextPath }">HOME</a></li>
			<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
			<li><a href="${pageContext.request.contextPath }/user/join">회원가입</a></li>
		</ul>
	</c:otherwise>

</c:choose>