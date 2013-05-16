package com.wordpath

class Puzzle {
	
	String startWord
	List<String> path
	String endWord
	Integer frequency
	Integer wordLength

    static constraints = {
		startWord blank: false
		path nullable: false
		endWord blank: false
		frequency nullable: false
		wordLength nullable: false
    }
}
