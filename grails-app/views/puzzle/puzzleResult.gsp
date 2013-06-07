<%@ page import="com.wordpath.Puzzle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
	</head>
	<body>
		<g:form controller="puzzle">
			<br/>
			<br/>
			<div align="center">
				<g:if test="${status == 'CORRECT'}">	
					<p  style="font-size: 30px;">${g.message(code:'puzzle.message.success')}</p>
				</g:if>
				<g:if test="${status == 'INCORRECT'}">	
					<p  style="font-size: 30px;"><font color="red">${g.message(code:'puzzle.message.failure')}</font><p>
				</g:if>
				<br/>
				<br/>
				<g:actionSubmit value="Get Next Puzzle" action="getPuzzle"/>
			</div>
			</g:form>		
	</body>
</html>
