
<%@ page import="com.wordpath.Puzzle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'puzzle.label', default: 'Puzzle')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-puzzle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-puzzle" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="startWord" title="${message(code: 'puzzle.startWord.label', default: 'Start Word')}" />
					
						<g:sortableColumn property="endWord" title="${message(code: 'puzzle.endWord.label', default: 'End Word')}" />
					
						<g:sortableColumn property="possiblePaths" title="${message(code: 'puzzle.possiblePaths.label', default: 'Possible Paths')}" />
					
						<g:sortableColumn property="wordLength" title="${message(code: 'puzzle.wordLength.label', default: 'Word Length')}" />
					
						<g:sortableColumn property="isActive" title="${message(code: 'puzzle.isActive.label', default: 'Is Active')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${puzzleInstanceList}" status="i" var="puzzleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${puzzleInstance.id}">${fieldValue(bean: puzzleInstance, field: "startWord")}</g:link></td>
					
						<td>${fieldValue(bean: puzzleInstance, field: "endWord")}</td>
					
						<td>${fieldValue(bean: puzzleInstance, field: "possiblePaths")}</td>
					
						<td>${fieldValue(bean: puzzleInstance, field: "wordLength")}</td>
					
						<td><g:formatBoolean boolean="${puzzleInstance.isActive}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${puzzleInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
