
<%@ page import="com.wordpath.Puzzle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'puzzle.label', default: 'Puzzle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-puzzle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-puzzle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list puzzle">
			
				<g:if test="${puzzleInstance?.startWord}">
				<li class="fieldcontain">
					<span id="startWord-label" class="property-label"><g:message code="puzzle.startWord.label" default="Start Word" /></span>
					
						<span class="property-value" aria-labelledby="startWord-label"><g:fieldValue bean="${puzzleInstance}" field="startWord"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${puzzleInstance?.paths}">
				<li class="fieldcontain">
					<span id="paths-label" class="property-label"><g:message code="puzzle.paths.label" default="Paths" /></span>
					
						<g:each in="${puzzleInstance.paths}" var="p">
						<span class="property-value" aria-labelledby="paths-label"><g:link controller="path" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${puzzleInstance?.endWord}">
				<li class="fieldcontain">
					<span id="endWord-label" class="property-label"><g:message code="puzzle.endWord.label" default="End Word" /></span>
					
						<span class="property-value" aria-labelledby="endWord-label"><g:fieldValue bean="${puzzleInstance}" field="endWord"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${puzzleInstance?.possiblePaths}">
				<li class="fieldcontain">
					<span id="possiblePaths-label" class="property-label"><g:message code="puzzle.possiblePaths.label" default="Possible Paths" /></span>
					
						<span class="property-value" aria-labelledby="possiblePaths-label"><g:fieldValue bean="${puzzleInstance}" field="possiblePaths"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${puzzleInstance?.wordLength}">
				<li class="fieldcontain">
					<span id="wordLength-label" class="property-label"><g:message code="puzzle.wordLength.label" default="Word Length" /></span>
					
						<span class="property-value" aria-labelledby="wordLength-label"><g:fieldValue bean="${puzzleInstance}" field="wordLength"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${puzzleInstance?.isActive}">
				<li class="fieldcontain">
					<span id="isActive-label" class="property-label"><g:message code="puzzle.isActive.label" default="Is Active" /></span>
					
						<span class="property-value" aria-labelledby="isActive-label"><g:formatBoolean boolean="${puzzleInstance?.isActive}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${puzzleInstance?.id}" />
					<g:link class="edit" action="edit" id="${puzzleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
