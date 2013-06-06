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
		<br/>
		<p>1 to 2 letters have been replaced with a _ in each subsequent word in the sequence. Replace the _with the correct letter while</p>
		<br/>
		<p>making sure that only one letter gets changed at each step. If you can complete the puzzle by changing more than the _ by all means do so.</p>		
		</div>
		<br/>
		<div align="center">
		<g:if test="${puzzle}">	
			<g:set var="firstWord" value="${puzzle?.get(0)}" />
			<g:set var="endWord" value="${puzzle?.get(puzzle.size()-1)}" />
			
			<g:set var="startWord" value="${puzzle.get(0)}" />						
			<g:each in="${(0..startWord.size()-1).toList()}" var="count2" >
				<input type="text" name="startWord_${count2}" placeholder="${startWord[count2]}" maxlength="1" size="1" style="font-size: 30px;"/>					
			</g:each>
			<br/>			
			
			<g:each in="${(1..puzzle.size()-2).toList()}" var="count" >
				<g:set var="currentWord" value="${puzzle.get(count)}" />			
				<g:each in="${(1..currentWord.size()-1).toList()}" var="count2" >
					<g:set var="currentLetter" value = "${currentWord[count2]}" />
					<g:if test="${currentLetter == '_'}">
						<input type="text" name="word_${count}_${count2}" maxlength="1" size="1" style="font-size: 30px;" required="true"/>
					</g:if>
					<g:if test="${currentLetter != '_'}">
						<input type="text" name="word_${count}_${count2}" maxlength="1" size="1" style="font-size: 30px;" value="${currentLetter}" required="true"/>
					</g:if>
											
				</g:each>
				<br/>
			</g:each>
			
			<g:set var="endWord" value="${puzzle.get(puzzle.size()-1)}" />					
			<g:each in="${(0..endWord.size()-1).toList()}" var="count2" >
				<input type="text" name="endWord_${count2}" placeholder="${endWord[count2]}" maxlength="1" size="1" style="font-size: 30px;"/>						
			</g:each>
			<br/>
			<br/>		
			<g:actionSubmit value="Submit solution" action="SubmitPuzzleSolution" />			
		</g:if>
		
		</div>
		</g:form>
	</body>
</html>
