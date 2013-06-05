package wordpath

import com.wordpath.Puzzle

class PuzzleService {

    def getRandomPuzzle() {
		def records = Puzzle.findAllByIsActive(true)
		int randIdx = Math.random() * records.size()
		def randomPuzzle = records.get(randIdx)
		return randomPuzzle
    }
	
	def getPuzzle(String startWord, String endWord, int depth){
		def puzzle =  Puzzle.find { startWord == startWord && endWord == endWord && depth ==depth }
		return puzzle
	}
	
	def isPuzzleSolved(List<String> puzzle){
		
	}
}
