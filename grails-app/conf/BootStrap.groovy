import com.wordpath.Puzzle
import com.wordPath.puzzle.generator.Generator
class BootStrap {

    def init = { servletContext ->

		int wordLength = 5
		int puzzleLength = 5
		List<String> allowedWords = new ArrayList<>()
		getWords(wordLength, allowedWords)
		def recordCount = 0;
		
		def saveToDatabase = {startWord, pathInfo, endWord, possiblePaths, isActive ->
			def record = new Puzzle(startWord:startWord,
				paths:pathInfo,
				endWord:endWord,
				possiblePaths:possiblePaths,
				wordLength:pathInfo.get(0).size(),
				isActive: false)
				record.save(flush:true)
			println record
			recordCount++
			println "recordcount = ${recordCount}"
		}		
		Generator generator = new Generator(allowedWords,wordLength,puzzleLength)
		generator.generate(saveToDatabase)
		
		def hotels = Puzzle.list()
		println "Records in database = ${hotels.size()}"
		println "Completed at at ${new Date()}"
		
    }
    def destroy = {
    }
	
	final static String FILE_NAME = "data/wordList.txt"
	
	def getWords(int length, List<String> allowedWords)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))
		try {
			String line = br.readLine()
			while (line != null) {
				allowedWords.add(line.trim().toLowerCase())
				line = br.readLine()
			}
		} catch (Exception e) {

		} finally {
			br.close()
		}

	}
}
