<!DOCTYPE html>
<html>

<head>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
</head>
<c:set var="response" value='${it}' />
<body>

	<div class="container">
		<h4>
			<strong>Task successfully created!<br></strong>
		</h4>

		<p>
			Save this URL to check your task status:
			<a href="${response.url}">${response.url}</a>
		</p>
	</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
