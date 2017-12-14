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

	<div class="alert alert-danger" role="alert" style="margin-top:20px;">
		<strong>${response.statusCode}</strong>
		${response.message}
	</div>

	<p>
		Maybe you should go back to the <a href="/index.jsp">home page</a>...
	</p>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>