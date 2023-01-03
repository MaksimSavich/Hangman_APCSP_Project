package com.backend;

public class Charcheck {

    String character;
    boolean isFound;

    //constructs charcheck for any character
    public Charcheck(String c){
        this(c, false);
    }

    //constructs charcheck for any character - used for non-letters
    public Charcheck(String c, boolean x){
        character = c;
        isFound = x;
    }

    //returns the value of isFound
    public boolean isCharFound(){
        return isFound;
    }

    //sets isFound to true
    public void charFound(){
        isFound = true;
    }

    //returns the character of a word and if it is found
    public String toString(){
        return character + " - " + isFound;
    }
}
