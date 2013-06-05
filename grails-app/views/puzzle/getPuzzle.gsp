<%@ page import="com.wordpath.Puzzle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		
	</head>
	<body>
		<div id="description">
		<p>The goal of this game is to get from the start word to the end word only changing one letter in the preceding word at each step</p>
		</div>
		<br/>
		<div align="center">
		<p>Possible Solutions for this particular puzzle = ${puzzle.possiblePaths}</p>
		<g:if test="${puzzle?.startWord}">	
			<g:set var="firstPath" value="${puzzle?.paths.get(0)}" />
			<p style="font-size: 30px;">${puzzle?.startWord}</p><br/>
			<g:each in="${(2..firstPath.size()-1).toList()}" var="count" >
			<input type="text" name="word_${count}" maxlength="${puzzle?.startWord.size()}" size="${puzzle?.startWord.size()}" style="font-size: 30px;"/><br/><br/>
			</g:each>
			<p style="font-size: 30px;">${puzzle?.endWord}</p><br/>
			
		</g:if>
		</div>
	</body>
</html>
