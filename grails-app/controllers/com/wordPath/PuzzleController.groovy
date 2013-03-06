package com.wordPath

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class PuzzleController {

	def generate(){
		//For now, hardcode this
		def path1 = []
		path1.add("DOE")
		path1.add("WOE")
		path1.add("WON")
		path1.add("WIN")
		
		def path2 = []
		path2.add("DOE")
		path2.add("SOE")
		path2.add("SIE")
		path2.add("WIN")
		
		def puzzles =[]
		puzzles.add(path1)
		puzzles.add(path2)
		def resp = new JSONArray(puzzles)
		preventCache(response)
		render resp as JSON	
	}
	
	private static final String HEADER_PRAGMA = "Pragma";
	private static final String HEADER_EXPIRES = "Expires";
	private static final String HEADER_CACHE_CONTROL = "Cache-Control";
	
	protected preventCache (response) {
		response.setHeader(HEADER_PRAGMA, "no-cache");
		response.setDateHeader(HEADER_EXPIRES, 1L);
		response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
		response.addHeader(HEADER_CACHE_CONTROL, "no-store");
	}
}
