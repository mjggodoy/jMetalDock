<!DOCTYPE html>
<html>

<head>
	<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>
<c:set var="response" value='${it}' />
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="page" value="none" />
</jsp:include>

<div class="container">

	<div class="alert alert-info" role="alert" style="margin-top:20px;">
		Your task has been successfully created.
		<p>
			Save this URL to check your task status:<br/>
			<a href="${response.url}">${response.url}</a><br/>
			Additionally, if you filled your email address when setting the task parameters, an email will be sent to
			you when the task is finished.
		</p>
	</div>

</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>