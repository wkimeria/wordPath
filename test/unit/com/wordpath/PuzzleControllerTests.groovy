package com.wordpath



import org.junit.*
import grails.test.mixin.*

@TestFor(PuzzleController)
@Mock(Puzzle)
class PuzzleControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/puzzle/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.puzzleInstanceList.size() == 0
        assert model.puzzleInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.puzzleInstance != null
    }

    void testSave() {
        controller.save()

        assert model.puzzleInstance != null
        assert view == '/puzzle/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/puzzle/show/1'
        assert controller.flash.message != null
        assert Puzzle.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/puzzle/list'

        populateValidParams(params)
        def puzzle = new Puzzle(params)

        assert puzzle.save() != null

        params.id = puzzle.id

        def model = controller.show()

        assert model.puzzleInstance == puzzle
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/puzzle/list'

        populateValidParams(params)
        def puzzle = new Puzzle(params)

        assert puzzle.save() != null

        params.id = puzzle.id

        def model = controller.edit()

        assert model.puzzleInstance == puzzle
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/puzzle/list'

        response.reset()

        populateValidParams(params)
        def puzzle = new Puzzle(params)

        assert puzzle.save() != null

        // test invalid parameters in update
        params.id = puzzle.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/puzzle/edit"
        assert model.puzzleInstance != null

        puzzle.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/puzzle/show/$puzzle.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        puzzle.clearErrors()

        populateValidParams(params)
        params.id = puzzle.id
        params.version = -1
        controller.update()

        assert view == "/puzzle/edit"
        assert model.puzzleInstance != null
        assert model.puzzleInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/puzzle/list'

        response.reset()

        populateValidParams(params)
        def puzzle = new Puzzle(params)

        assert puzzle.save() != null
        assert Puzzle.count() == 1

        params.id = puzzle.id

        controller.delete()

        assert Puzzle.count() == 0
        assert Puzzle.get(puzzle.id) == null
        assert response.redirectedUrl == '/puzzle/list'
    }
}
