package com.wordPath

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class PuzzleController {

	def generate(){
		
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
