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
	int size(){
		return path.split(",").size()
	}
	
	List<String> getPathElements(){
		return Arrays.asList(path.split(","))
	}
}