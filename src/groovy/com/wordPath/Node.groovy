package com.wordPath

class Node{
    def word
    def children =[]
    Node(){}
    Node(word, level){
        println "creating node for ${word} level is ${level}"
        this.word = word
        if(level<2){
            def allVariations = []
            for(int i =0;i<word.size();i++){
                allVariations.addAll(getVariations(word,i))
            }  
            allVariations.each{
               Node n = new Node(it,level+1)
            }  
        }       
    }
   
    def getVariations(word,idx){
        def letter = word[idx]
        def alphabet = getAlphabet()
        alphabet.remove(letter)
        def variations =[]    
        alphabet.each{        
            def variation = getWordVariation(word,idx,it) 
            variations.add(variation)
        }
        return variations
    }
    /*
    * For a given word and index, get all the word variations with the letter at the index position iterated from A-Z
    */
    def getWordVariation(word,idx,letter){
         if(idx==0){
             def s = letter + word.getAt([idx+1..word.size()-1])
        }else if(idx==word.size()-1){
            def s =  word.getAt([0..idx-1]) + letter 
        }else{
           def s =  word.getAt([0..idx-1]) + letter +  word.getAt([idx+1..word.size()-1]) 
        }
    }
    
    /*
    * Get all the letters in the alphabet
    */
    def getAlphabet(){
        def start = 'A'
        def letters =[start]
        while(letters.size()<26){
            start++
            letters.add(start)
        }
        return letters
    }
}
