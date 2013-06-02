package com.wordpath

import java.io.IOException;
import java.util.concurrent.*
import com.wordpath.Puzzle
import com.wordPath.puzzle.generator.Generator

class GeneratePuzzlesJob {
	def executorService
  static triggers = {
    simple name: 'mySimpleTrigger', startDelay: 20000 
  }
  
  static final long RUNTIME = 1000 * 60 * 60 * 24 //1 day
  def group = "MyGroup"
  def execute(){
    List<String> allowedWords = getWords()
	
	def future1 = executorService.submit({	
		Generator generator = new Generator(allowedWords,3,5)
		generator.generate(RUNTIME)
	} as Callable)
	
	def future2 = executorService.submit({
		Generator generator = new Generator(allowedWords,4,5)
		generator.generate(RUNTIME)
	} as Callable)
	
	def future3 = executorService.submit({
		Generator generator = new Generator(allowedWords,5,5)
		generator.generate(RUNTIME)
	} as Callable)
	
	List<Future>futures = new ArrayList<Future>()
	futures.add(future1)
	futures.add(future2)
	futures.add(future3)
	
	futures.each{ future ->
		future.get(1, TimeUnit.DAYS)	
	}
			
	def hotels = Puzzle.list()
	println "Records in database = ${hotels.size()}"
	println "Completed at at ${new Date()}"
  }
  
  final static String FILE_NAME = "data/wordList.txt"
  
  def getWords()throws IOException {
	  List<String> allowedWords = new ArrayList<>()
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
	  return allowedWords
  }
}
