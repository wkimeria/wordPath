import com.wordPath.utilities.Node
import com.wordpath.Puzzle
class BootStrap {

    def init = { servletContext ->

		int wordLength = 4;
		int puzzleLength = 5
		List<String> allowedWords = new ArrayList<>();
		getWordsOfLength(wordLength, allowedWords);
		System.out.println("Words = " + allowedWords.size());
		Double randomWordIndex = Math.random() * allowedWords.size();
		//String randomWord = allowedWords.get(randomWordIndex.intValue());
		
		println "Started at ${new Date()}"
		int recordCount = 0
		
		for(String s:allowedWords){
			String randomWord = s;
			Node node = new Node(null, randomWord, allowedWords, 0, puzzleLength)			
			List<List<String>> paths = node.getAllPaths()
			String start = paths.get(0).first()
			String last = paths.get(0).last()
			def record = new Puzzle(startWord:start, 'paths':paths, endWord:last, pathCount:paths.size(), wordLength: wordLength, isActive: false)
			record.save(flush:true)
			recordCount++
			println "records = ${recordCount} path = ${start} -> ${last} number of paths = ${paths.size()}"			
		}
		def hotels = Puzzle.list()
		println "Records in database = ${hotels.size()}"
		println "Completed at at ${new Date()}"
    }
    def destroy = {
    }
	
	final static String FILE_NAME = "data/wordList.txt";
	
	def getWordsOfLength(int length, List<String> allowedWords)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
		try {
			String line = br.readLine();
			while (line != null) {
				if (line.trim().length() == length) {
					allowedWords.add(line.trim().toLowerCase());
				}
				line = br.readLine();
			}
		} catch (Exception e) {

		} finally {
			br.close();
		}

	}
}
