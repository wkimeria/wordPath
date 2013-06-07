package wordpath

import com.wordpath.Puzzle

class PuzzleService {

    def getRandomPuzzle() {
		def records = Puzzle.findAllByIsActive(true)
		int randIdx = Math.random() * records.size()
		def randomPuzzle = records.get(randIdx)		
		List<String> randomizedPath = new LinkedList<String>()
		def randomPath = randomPuzzle.getRandomPath().getPathElements()
		randomPath.each{ String word ->
			int charToRemove = Math.random() * word.size()
			int charToRemove2 = Math.random() * word.size()
			charToRemove = charToRemove == 0?1:charToRemove
			charToRemove2 = charToRemove2 == 0?1:charToRemove2
			StringBuilder currentWord = new StringBuilder(word)
			currentWord.setCharAt(charToRemove, '_'.toCharacter())
			currentWord.setCharAt(charToRemove2, '_'.toCharacter())
			
			randomizedPath.add(currentWord.toString().toUpperCase().toCharArray())			
			
		}
		randomizedPath.set(0, randomPuzzle.startWord.toUpperCase().toCharArray())
		randomizedPath.set(randomizedPath.size() - 1, randomPuzzle.endWord.toUpperCase().toCharArray())
		//return randomizedPath
		return ["originalPuzzle" : randomPath, "randomizedPuzzle" : randomizedPath]
    }
	
	def getPuzzle(String startWord, String endWord, int depth){
		def puzzle =  Puzzle.find { startWord == startWord && endWord == endWord && depth ==depth }
		return puzzle
		
		
	}
	
	def validateSolution(request){	
		boolean isValid = false	
		java.util.Enumeration theFields=request.getParameterNames()
		def params =[:]
		theFields.each { parameter ->
			def fieldName = parameter
			def value = request.getParameter(fieldName)
			println "${fieldName} = ${value}"
			params.put(fieldName, value)
		}
		
		def wordLength = Integer.parseInt(request.getParameter("wordLength"))
		def puzzleDepth = Integer.parseInt(request.getParameter("puzzleDepth"))
						
		List<String> solution = new LinkedList<String>()
		for(int i = 0 ; i < puzzleDepth ; i++){
			def currentWord = []
			for(int x = 0; x < wordLength ; x++){
				String key = "word_${i}_${x}"
				String currentLetter = request.getParameter(key)
				println key
				println currentLetter
				currentWord.add(currentLetter)
			}			
			solution.add(currentWord.join("").toLowerCase())
		}
		//return solution
		def puzzle = Puzzle.find { startWord == solution.first() && endWord == solution.last() && depth == puzzleDepth}	
		println "solution = ${solution}"
		
		if(puzzle){
			puzzle.paths.each{ path ->
				println path.getPathElements()
				if(path.toString() == solution.toString()){isValid = true}
			}
		}		
		return isValid
	}
}
