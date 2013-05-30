package com.wordPath.puzzle.generator
import com.wordPath.utilities.Node
import com.wordpath.Puzzle
import com.wordpath.Path
import java.io.IOException;
import java.util.List;

class Generator{
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
	void generate(){
		println "Words that match criteria = ${allowedWords.size()} in size"
		println "Started at ${new Date()} generating words of length ${wordLength} and depth ${depth}"
		int recordCount = 0
		for(String randomWord:allowedWords){
			Map<String, List<List<String>>> consolidatedPaths = new HashMap<>()
			Node node = new Node(null, randomWord, allowedWords, 0, depth)
			List<List<String>> paths = node.getAllPaths()
			if(containsPuzzles(paths)){
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
				
				def pathsFound = consolidatedPaths.values()				
				for(List<List<String>> pathInfo:pathsFound){
					String startWord = pathInfo.get(0).first()
					String endWord = pathInfo.get(0).last()
					Integer possiblePaths = pathInfo.size()
					saveToDatabase(startWord, pathInfo, endWord, possiblePaths, false)					
				}
			}	
		}
	}
	
	/**
	 * Save specific row to database
	 * @param startWord
	 * @param pathInfo
	 * @param endWord
	 * @param possiblePaths
	 * @param isActive
	 * @return
	 */
	protected def saveToDatabase(startWord, pathInfo, endWord, possiblePaths, isActive){
		def existingPuzzle =  Puzzle.find { startWord == startWord && endWord == endWord && wordLength == pathInfo.get(0).size() }
		
		if(existingPuzzle){
			println "record exists ${startWord} ${endWord} ${pathInfo.get(0).size()}"
			return	
		}
		
		//def existingPuzzle = Puzzle.find(startWord:startWord,endWord:endWord,wordLength:pathInfo.get(0).size())
		println "saving for ${startWord} --> ${endWord} ${pathInfo}"
		def record = new Puzzle(startWord:startWord,
			paths:[],
			endWord:endWord,
			possiblePaths:possiblePaths,
			wordLength:pathInfo.get(0).size(),
			isActive: false)
			record.save(flush:false, failOnError:true)
						
			//Save paths for record
			pathInfo.each{ path ->
				println path.toString()
				Path p = new Path(puzzle:record, path:path.toString())
				println "adding path ${p}"
				record.paths.add(p)
			}
			record.save(flush:true, failOnError:true)
			println "Records in database = ${Puzzle.list().size()}"
			
					
		
		println record
		//recordCount++
		//println "recordcount = ${recordCount}"
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
	
	public static int MIN_PATH_SIZE = 3
	
	/**
	 * Determine whether the paths generated are of valid lengths
	 * @param allPaths
	 * @return
	 */
	def containsPuzzles(allPaths){
		boolean containsPuzzles = false
		int minPathSize = MIN_PATH_SIZE
		for(List<String>path:allPaths){
			if(path.size() > minPathSize){
				minPathSize = path.size()
			}
		}
		if(minPathSize > MIN_PATH_SIZE){
			containsPuzzles = true
		}
		return containsPuzzles
	}
}
