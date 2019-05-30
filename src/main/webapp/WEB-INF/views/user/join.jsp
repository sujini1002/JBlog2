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
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	$(function(){
			$('#id').change(function(){
				$('#check-button').show();
				$('#check-image').hide();
			});
		$('#check-button').click(function(){
			var id = $('#id').val();
			if(id == ''){
				return;
			}
			// ajax 통신
			$.ajax({
				url : "${pageContext.servletContext.contextPath }/user/api/checkemail/"+id, //문자열로 인식이 되는게 아니라 서버에서 el값으로 먼저 치환후 js통신을 한다.
				type : "GET",
				dataType : "json",
				data : "", //post방식일때 값을 여기에 넣어줌
				success:function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					if(response.data == true){
						alert('이미 존재하는 이메일 입니다.\n 다른 이메일을 사용해 주세요');
						$('#id').focus();
						$('#id').val("");
						$('#submit').hide();
						return;
					}
					$('#check-button').hide();
					$('#submit').show();
					$('#check-image').show();
				},
				error : function(xhr, error){ //xmlHttpRequest?
						console.error("error : "+error);
				}
			});
		})
	});
	
	/* //전송
	function submitBtn(){
		var result = $("#agree-prov").is(":checked");
		if(result != 'y'){
			alert('이용약관에 동의해 주세요.');
			return false;
		}
		return true;
	} */
</script>
</head>
<body>
	<div class="center-content">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		
		<form:form modelAttribute="userVo" name="user" class="join-form" id="join-form"
			method="post" action="${pageContext.servletContext.contextPath }/user/join">
			<label class="block-label" for="name">이름</label>
			<form:input path="name"/>
			<p class="validCheck"><form:errors path="name"/></p>
			
			<label class="block-label" for="blog-id">아이디</label>
			<form:input path="id"/>
			<p class="validCheck"><form:errors path="id"/></p>
			
			<input id="check-button" type="button" value="id 중복체크">
			<img id="check-image" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<form:password path="password"/>
			<p class="validCheck"><form:errors path="password"/></p>
			
			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기" id="submit">

		</form:form>
	</div>
</body>
</html>
