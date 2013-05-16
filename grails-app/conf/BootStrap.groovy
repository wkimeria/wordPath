import com.wordPath.utilities.Node
import com.wordpath.Puzzle
class BootStrap {

    def init = { servletContext ->

		int wordLength = 4;
		int puzzleLength = 3
		List<String> allowedWords = new ArrayList<>();
		getWordsOfLength(wordLength, allowedWords);
		System.out.println("Words = " + allowedWords.size());
		Double randomWordIndex = Math.random() * allowedWords.size();
		//String randomWord = allowedWords.get(randomWordIndex.intValue());
		
		println new Date()
		
		for(String s:allowedWords){
			String randomWord = s;
			Node node = new Node(null, randomWord, allowedWords, 0, puzzleLength)			
			List<List<String>> paths = node.getAllPaths()			
			for (List<String> path : paths) {
				//println "Saving ${path}"
				String start = path.first()
				String last = path.last()				
				def record = new Puzzle(startWord:start, 'path':path, endWord:last, frequency:0, wordLength:path.size())				
				record.save(flush:true)			
							
			}			
		}
		def hotels = Puzzle.list()
		println "Records in database = ${hotels.size()}"
		println new Date()
		println "Possible Paths = ${paths.size()}"
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
