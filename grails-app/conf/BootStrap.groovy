import com.wordPath.utilities.Node
import com.wordpath.Puzzle
class BootStrap {

    def init = { servletContext ->

		int wordLength = 5
		int puzzleLength = 5
		List<String> allowedWords = new ArrayList<>()
		getWordsOfLength(wordLength, allowedWords)
		println "Words = ${allowedWords.size()}"
		Double randomWordIndex = Math.random() * allowedWords.size()
		Map<String, List<List<String>>> consolidatedPaths = new HashMap<>()
		
		println "Started at ${new Date()}"
		int recordCount = 0		
		for(String s:allowedWords){
			String randomWord = s
			Node node = new Node(null, randomWord, allowedWords, 0, puzzleLength)			
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
				def record = new Puzzle(startWord:startWord, 
										paths:pathInfo, 
										endWord:endWord, 
										possiblePaths:possiblePaths, 
										wordLength:pathInfo.get(0).size(), 
										isActive: false)
				record.save(flush:true)
				println record
				recordCount++				
				println "${recordCount}"
				
			}
			
		}
		def hotels = Puzzle.list()
		println "Records in database = ${hotels.size()}"
		println "Completed at at ${new Date()}"
    }
    def destroy = {
    }
	
	final static String FILE_NAME = "data/wordList.txt"
	
	def getWordsOfLength(int length, List<String> allowedWords)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))
		try {
			String line = br.readLine()
			while (line != null) {
				if (line.trim().length() == length) {
					allowedWords.add(line.trim().toLowerCase())
				}
				line = br.readLine()
			}
		} catch (Exception e) {

		} finally {
			br.close()
		}

	}
}
