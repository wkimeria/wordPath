package com.wordpath

class Puzzle {
	
	String startWord
	List<List<String>> paths
	String endWord
	Integer pathCount
	Integer wordLength
	boolean isActive 

    static constraints = {
		startWord blank: false
		paths nullable: false
		endWord blank: false
		pathCount nullable: false
		wordLength nullable: false
		isActive nullable: false
    }
}
