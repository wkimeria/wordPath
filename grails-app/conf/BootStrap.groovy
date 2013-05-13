import com.wordpath.utilities.Node
class BootStrap {

    def init = { servletContext ->
		
		//For now, just a test of generating 4x3 word puzzles
		//TODO:Select database, create Puzzle Domain Objects and populate database. The code below should run
		//     as a background process
		int wordLength = 4;
		int puzzleLength = 3
		List<String> allowedWords = new ArrayList<>();
		getWordsOfLength(wordLength, allowedWords);
		System.out.println("Words = " + allowedWords.size());
		Double randomWordIndex = Math.random() * allowedWords.size();
		//String randomWord = allowedWords.get(randomWordIndex.intValue());
		
		for(String s:allowedWords){
			String randomWord = s;
			Node node = new Node(null, randomWord, allowedWords, 0, puzzleLength);
			println("--------------- Node built for ${s} ---------------------");
			List<List<String>> paths = node.getAllPaths();
			for (List<String> path : paths) {
				println(path);
			}
			System.out.println("Possible Paths = ${paths.size()}");
		}
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
