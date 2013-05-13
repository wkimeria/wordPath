package com.wordPath.utilities;

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
class NodeTests {
	@Test
	void testCreate(){
		def allowedWords = ['pot','bot','lot','pet','pit','bet','pep','pin','tin','tan']
		Node node = new Node(null,'pot',allowedWords,0,5)
		
		assert node.toString().contains('pot 0 parent =')
		assert node.toString().contains('bot 1 parent = pot')
		assert node.toString().contains('lot 1 parent = pot')
		assert node.toString().contains('pet 1 parent = pot')
		assert node.toString().contains('pit 1 parent = pot')
		assert node.toString().contains('bet 2 parent = bot')
		assert node.toString().contains('bet 2 parent = pet')
		assert node.toString().contains('pep 2 parent = pet')
		assert node.toString().contains('pin 2 parent = pit')
		assert node.toString().contains('tin 3 parent = pin')
					
	}
	
	@Test
	void testGetAllPaths(){
		def allowedWords = ['pot','bot','lot','pet','pit','bet','pep','pin','tin','tan']
		Node node = new Node(null,'pot',allowedWords,0,5)
		def result = node.getAllPaths()
		assert result == [['pot', 'pit', 'pin', 'tin', 'tan'], ['pot', 'pet', 'pep'], ['pot', 'pet', 'bet'], ['pot', 'lot'], ['pot', 'bot', 'bet']]
		
	}
}
