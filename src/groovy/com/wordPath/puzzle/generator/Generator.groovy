package com.wordPath.puzzle.generator
import com.wordPath.utilities.Node
import com.wordpath.Puzzle
import java.io.IOException;
import java.util.List;

class Generator {
	int wordLength
	int depth	
	List<String> allowedWords
	Generator(List<String> allowedWords, int wordLength, int depth){
		this.wordLength = wordLength
		this.depth = depth
		this.allowedWords = getWordsOfLength(wordLength, allowedWords)		
	}
	
	/**
	 * Generate puzzles
	 * @return
	 */
	def generate(def persistFunc){
		println "Words = ${allowedWords.size()}"
		Double randomWordIndex = Math.random() * allowedWords.size()
		Map<String, List<List<String>>> consolidatedPaths = new HashMap<>()
		
		println "Started at ${new Date()}"
		int recordCount = 0
		for(String s:allowedWords){
			String randomWord = s
			Node node = new Node(null, randomWord, allowedWords, 0, depth)
			List<List<String>> paths = node.getAllPaths()
			
			for (List<String> path : paths) {
				String start = path.first()
				String last = path.last()
				String index = start + " - " + last + " - " + path.size()
				if(consolidatedPaths.containsKey(index)){
					List<List<String>> existingPaths = consolidatedPaths.get(index)
					existingPaths.add(path)
					consolidatedPaths.put(index,existingPaths)
				}else{
					consolidatedPaths.put(index,[path])
				}
			}
			for(List<List<String>> pathInfo:consolidatedPaths.values()){
				String startWord = pathInfo.get(0).first()
				String endWord = pathInfo.get(0).last()
				Integer possiblePaths = pathInfo.size()
				
				persistFunc(startWord, pathInfo, endWord, possiblePaths, false)				
			}			
		}
	}
	
	/**
	 * Get words of specified length from list
	 * @param length
	 * @param allowedWords
	 * @return
	 */
	def getWordsOfLength(int length, List<String> allowedWords){
		List<String> allowedWordsOfLengh = new ArrayList<List>()
		allowedWords.each{ word ->
			if(word.size() == length){
				allowedWordsOfLengh.add(word)
			}
		}
		return allowedWordsOfLengh
	}
}
