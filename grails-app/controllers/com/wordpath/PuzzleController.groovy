package com.wordpath

import org.springframework.dao.DataIntegrityViolationException


class PuzzleController {
	
	def puzzleService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def getPuzzle(){
		def randomPuzzle = puzzleService.getRandomPuzzle()			
		log.info " randomPuzzle = ${randomPuzzle}"		
		[puzzle: randomPuzzle]
	}
	
	def submitPuzzleSolution(){
		//Get parameters
		java.util.Enumeration theFields=request.getParameterNames()
		def params =[:]
		theFields.each { parameter ->
			def fieldName = parameter
			def value = request.getParameter(fieldName)
			println "${fieldName} = ${value}"
			params.put(fieldName, value)
		}
		println params
		
		List<String> solution = new LinkedList<String>()
		solution.add(params.get("start_word"))
		for(int i = 2 ; i< params.size() ; i++){
			String key = "word_${i}"
			if(params.containsKey(key)){
				solution.add(params.get(key))
			}					
		}
		solution.add(params.get("end_word"))		
		println solution
	}

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [puzzleInstanceList: Puzzle.list(params), puzzleInstanceTotal: Puzzle.count()]
    }

    def create() {
        [puzzleInstance: new Puzzle(params)]
    }

    def save() {
        def puzzleInstance = new Puzzle(params)
        if (!puzzleInstance.save(flush: true)) {
            render(view: "create", model: [puzzleInstance: puzzleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), puzzleInstance.id])
        redirect(action: "show", id: puzzleInstance.id)
    }

    def show(Long id) {
        def puzzleInstance = Puzzle.get(id)
        if (!puzzleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), id])
            redirect(action: "list")
            return
        }

        [puzzleInstance: puzzleInstance]
    }

    def edit(Long id) {
        def puzzleInstance = Puzzle.get(id)
        if (!puzzleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), id])
            redirect(action: "list")
            return
        }

        [puzzleInstance: puzzleInstance]
    }

    def update(Long id, Long version) {
        def puzzleInstance = Puzzle.get(id)
        if (!puzzleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (puzzleInstance.version > version) {
                puzzleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'puzzle.label', default: 'Puzzle')] as Object[],
                          "Another user has updated this Puzzle while you were editing")
                render(view: "edit", model: [puzzleInstance: puzzleInstance])
                return
            }
        }

        puzzleInstance.properties = params

        if (!puzzleInstance.save(flush: true)) {
            render(view: "edit", model: [puzzleInstance: puzzleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), puzzleInstance.id])
        redirect(action: "show", id: puzzleInstance.id)
    }

    def delete(Long id) {
        def puzzleInstance = Puzzle.get(id)
        if (!puzzleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), id])
            redirect(action: "list")
            return
        }

        try {
            puzzleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'puzzle.label', default: 'Puzzle'), id])
            redirect(action: "show", id: id)
        }
    }
}
