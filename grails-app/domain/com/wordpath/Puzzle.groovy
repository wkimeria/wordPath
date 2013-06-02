package com.wordpath
import com.wordpath.Path

class Puzzle {
	
	String startWord
	List<Path> paths
	String endWord
	Integer possiblePaths
	Integer depth
	boolean isActive 
	
	static hasMany = [paths: Path]

    static constraints = {
		id generator: 'identity', column: 'id'
		startWord blank: false
		paths nullable: true
		endWord blank: false
		possiblePaths nullable: false
		depth nullable: false
		isActive nullable: false
		startWord(unique: ['endWord','depth'])
    }
	
	String toString(){
		String recordInfo = "{$id} ${startWord} ${endWord} possible paths = ${possiblePaths} ${paths}"
		return recordInfo		
	}
}
