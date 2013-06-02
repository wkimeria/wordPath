<%@ page import="com.wordpath.Puzzle" %>



<div class="fieldcontain ${hasErrors(bean: puzzleInstance, field: 'startWord', 'error')} required">
	<label for="startWord">
		<g:message code="puzzle.startWord.label" default="Start Word" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="startWord" required="" value="${puzzleInstance?.startWord}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: puzzleInstance, field: 'paths', 'error')} ">
	<label for="paths">
		<g:message code="puzzle.paths.label" default="Paths" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${puzzleInstance?.paths?}" var="p">
    <li><g:link controller="path" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="path" action="create" params="['puzzle.id': puzzleInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'path.label', default: 'Path')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: puzzleInstance, field: 'endWord', 'error')} required">
	<label for="endWord">
		<g:message code="puzzle.endWord.label" default="End Word" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="endWord" required="" value="${puzzleInstance?.endWord}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: puzzleInstance, field: 'possiblePaths', 'error')} required">
	<label for="possiblePaths">
		<g:message code="puzzle.possiblePaths.label" default="Possible Paths" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="possiblePaths" type="number" value="${puzzleInstance.possiblePaths}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: puzzleInstance, field: 'depth', 'error')} required">
	<label for="depth">
		<g:message code="puzzle.depth.label" default="Depth" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="depth" type="number" value="${puzzleInstance.depth}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: puzzleInstance, field: 'isActive', 'error')} ">
	<label for="isActive">
		<g:message code="puzzle.isActive.label" default="Is Active" />
		
	</label>
	<g:checkBox name="isActive" value="${puzzleInstance?.isActive}" />
</div>

