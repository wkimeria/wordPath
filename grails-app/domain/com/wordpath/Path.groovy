package com.wordpath
import com.wordpath.Puzzle

class Path {
	String path
	Puzzle puzzle
	
	static belongsTo = [puzzle: Puzzle]
	
	String toString(){
		String recordInfo = "${path}"
		return recordInfo
	}
}