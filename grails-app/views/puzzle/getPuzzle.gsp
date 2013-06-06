<%@ page import="com.wordpath.Puzzle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		
	</head>
	<body>
		<g:form controller="puzzle">
		<div id="description">
		<p>The goal of this game is to get from the start word to the end word only changing one letter in the preceding word at each step.</p>
		<p>1 to 2 letters have been replaced with a _ in each subsequent word in the sequence. Replace the _with the correct letter while</p>
		<p>making sure that only one letter gets changed at each step. If you can complete the puzzle by changing more than the _ by all means do so.</p>
		</div>
		<br/>
		<div align="center">
		<g:if test="${puzzle}">	
			<g:set var="firstWord" value="${puzzle?.get(0)}" />
			<g:set var="endWord" value="${puzzle?.get(puzzle.size()-1)}" />
			
			<input type="hidden" name="start_word" value="${firstWord}">
			<p style="font-size: 30px;">${firstWord}</p><br/>
			
			
			<g:each in="${(2..puzzle.size()-1).toList()}" var="count" >
			<input type="text" name="word_${count}" maxlength="${firstWord.size()+1}" size="${firstWord.size()+1}" style="font-size: 30px;" value="${puzzle.get(count-1)}"/><br/><br/>
			</g:each>
			<p style="font-size: 30px;">${endWord}</p><br/>
			<input type="hidden" name="end_word" value="${endWord}">
			
			<g:actionSubmit value="Submit solution" action="SubmitPuzzleSolution" />			
		</g:if>
		
		</div>
		</g:form>
	</body>
</html>
