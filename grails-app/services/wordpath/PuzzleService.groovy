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
			randomizedPath.add(currentWord)			
			
		}
		randomizedPath.set(0, randomPuzzle.startWord)
		randomizedPath.set(randomizedPath.size() - 1, randomPuzzle.endWord)
		return randomizedPath
    }
	
	def getPuzzle(String startWord, String endWord, int depth){
		def puzzle =  Puzzle.find { startWord == startWord && endWord == endWord && depth ==depth }
		
		
		return puzzle
	}
	
	def isPuzzleSolved(List<String> puzzle){
		
	}
}
