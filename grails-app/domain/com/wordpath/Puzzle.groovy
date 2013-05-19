package com.wordpath

class Puzzle {
	
	String startWord
	List<List<String>> paths
	String endWord
	Integer possiblePaths
	Integer wordLength
	boolean isActive 

    static constraints = {
		id generator: 'identity', column: 'id'
		startWord blank: false
		paths nullable: false
		endWord blank: false
		possiblePaths nullable: false
		wordLength nullable: false
		isActive nullable: false
		startWord(unique: ['endWord','wordLength'])
    }
	
	String toString(){
		String recordInfo = "{$id} ${startWord} ${endWord} possible paths = ${possiblePaths} ${paths}"
		return recordInfo		
	}
}
