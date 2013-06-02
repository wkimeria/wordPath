package com.wordpath

import org.springframework.dao.DataIntegrityViolationException

class PuzzleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
