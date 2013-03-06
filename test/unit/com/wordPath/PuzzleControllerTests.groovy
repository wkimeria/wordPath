package com.wordPath



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PuzzleController)
class PuzzleControllerTests {

    void testGenerate() {
       controller.generate()
	   assert response.text == '[["DOE","WOE","WON","WIN"],["DOE","SOE","SIE","WIN"]]'
    }
}
