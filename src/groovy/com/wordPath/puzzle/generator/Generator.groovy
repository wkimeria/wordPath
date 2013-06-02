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
	List<String> unProcessedWords
	Generator(List<String> allowedWords, int wordLength, int depth){
		this.wordLength = wordLength
		this.depth = depth
		this.allowedWords = getWordsOfLength(wordLength, allowedWords)
		this.unProcessedWords = new ArrayList<>(this.allowedWords)			
	}
	
	/**
	 * Generate puzzles
	 * @return
	 */
	void generate(long runTimeMillis){
		long startTimeMillis = System.currentTimeMillis()		
		log.info "Words that match criteria = ${allowedWords.size()} in size"
		log.info "Started at ${new Date()} generating words of length ${depth} and depth ${depth}"
		int recordCount = 0
		while(System.currentTimeMillis() < (startTimeMillis + runTimeMillis)){
			while(unProcessedWords.size() > 0){
				int wordIdx = Math.random() * unProcessedWords.size()
				String randomWord = unProcessedWords.get(wordIdx)
				unProcessedWords.remove(wordIdx)
				log.info "Generating puzzle of wordLength = ${wordLength} depth = ${depth} and startWord = ${randomWord}"
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
		log.info "DONE PROCESSING!!!"
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
		
		log.debug "about to save ${startWord} ${endWord} ${pathInfo.get(0).size()}"
		
		def existingPuzzle =  Puzzle.find { startWord == startWord && endWord == endWord && depth == pathInfo.get(0).size() }		
				
		if(existingPuzzle){
			log.debug "record exists ${startWord} ${endWord} ${pathInfo.get(0).size()}"
			return	
		}
		
		def saveToDatabase = false
		//Make sure there is at least on path of required length
		pathInfo.each{ path ->
			if(path.size() == depth){
				saveToDatabase = true
			}
		}
		
		if(saveToDatabase == true){
			//def existingPuzzle = Puzzle.find(startWord:startWord,endWord:endWord,depth:pathInfo.get(0).size())
			//log.debug "saving for ${startWord} --> ${endWord} ${pathInfo}"
			def record = new Puzzle(startWord:startWord,
				paths:[],
				endWord:endWord,
				possiblePaths:possiblePaths,
				depth:depth,
				isActive: false)
			record.save(flush:false, failOnError:true)
						
			//Save paths for record
			pathInfo.each{ path ->
				if(path.size()== depth){
					Path p = new Path(puzzle:record, path:path.toString())
					record.paths.add(p)
				}				
			}
			log.info "saving ${startWord} ${endWord} ${pathInfo.get(0).size()}"
			log.debug "saving ${startWord} ${endWord} ${pathInfo.get(0).size()} ${pathInfo}"
			record.save(flush:true, failOnError:true)
			log.debug "Records in database = ${Puzzle.list().size()}"
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
