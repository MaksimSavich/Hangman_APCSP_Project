package com.backend;
import java.io.*;
import java.net.*;
import java.util.*;

/* TODO
    export all cli methods to a StartGame class
    integrate database and api functions
 */

public class HangmanGame {
    private static ArrayList<String> techList = new ArrayList<>();
    private static ArrayList<String> guessList = new ArrayList<>();
    private static Charcheck[] isCharFoundList;
    private static String wordToGuess;
    private static int mistakes;
    private static final String myListLink = "https://raw.githubusercontent.com/MaksimSavich/programming_terminology_list/main/programming_terms.txt";

    //constructs hangman game with default list
    public HangmanGame(ArrayList<String> list){
        this(list, myListLink);
    }

    //constructs hangman game with user inputted word list
    public HangmanGame(ArrayList<String> list, String link){
        getDict(list, link);
        techList = list;
        wordToGuess = randWord(techList);
        createCheckList();
        mistakes = 0;

    }

    //imports a word list from the internet and applies each word into an arraylist
    private void getDict(ArrayList<String> list, String link){
        try {
            URL url = new URL(link);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = in.readLine()) != null) {
                if(line.contains("/")){
                    line = line.substring(0, line.indexOf("/"));
                }

                list.add(line.toLowerCase());
            }
            in.close();

        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    //gets random word from "techList"
    private String randWord(ArrayList<String> list){
        int rand = (int) (Math.random() * list.size());
        return list.get(rand);
    }
    //creates a checklist containing each character of the word being guessed and if they are found
    private void createCheckList(){
        isCharFoundList = new Charcheck[wordToGuess.length()];

        for(int i = 0; i < isCharFoundList.length; i++){
            if((wordToGuess.substring(i, i + 1).matches("[^A-Za-z]")) || wordToGuess.charAt(i) == ' '){
                isCharFoundList[i] = new Charcheck(wordToGuess.substring(i, i + 1), true);
            } else{
                isCharFoundList[i] = new Charcheck(wordToGuess.substring(i, i + 1));
            }
        }
    }

    //gets the word to guess
    public String getWordToGuess(){
        return wordToGuess;
    }

    //checks if the character inputted by the user fits strict parameters
    public int charCheck(String c){
        //checks if the letter has already been used
        for (String s : guessList) {
            if (c.toLowerCase().equals(s)) {
                return 0;
            }
        }
        //adds letter to guess list if letter hasn't been used yet
        guessList.add(c.toLowerCase());

        //checks if the letter is incorrect
        if(!wordToGuess.contains(c.toLowerCase())){
            if(!(mistakes >= 8)){
                mistakes++;
            }
        }

        /*
        changes the isFound value of the Charcheck object created for the specific letter to true
        if that letter is found in the word to be guessed
        */
        for(int i = 0; i < wordToGuess.length(); i++){
            if(c.toLowerCase().equals(wordToGuess.substring(i, i + 1))){
                isCharFoundList[i].charFound();
            }
        }
        return 1;
    }

    //end game event that resets the value of the constructed object
    public void endGame(boolean x){

        if(x){
            System.exit(-100);
        }

        wordToGuess = randWord(techList);
        mistakes = 0;
        guessList = new ArrayList<>();
        createCheckList();
    }

    //gets mistakes
    public int getMistakes(){
        return mistakes;
    }

    //checks the mistakes and displays the corresponding drawing to the amount of mistakes
    public void drawing(){
        if(mistakes == 1){
            System.out.println(" _________\n |\n |\n |\n |\n |\n_|_\n");
        } else if(mistakes == 2){
            System.out.println(" _________\n |       |\n |\n |\n |\n |\n_|_\n");
        } else if(mistakes == 3){
            System.out.println(" _________\n |       |\n |       O\n |\n |\n |\n_|_\n");
        } else if(mistakes == 4){
            System.out.println(" _________\n |       |\n |       O\n |       |\n |\n |\n_|_\n");
        } else if(mistakes == 5){
            System.out.println(" _________\n |       |\n |       O\n |      (|\n |\n |\n_|_\n");
        } else if(mistakes == 6){
            System.out.println(" _________\n |       |\n |       O\n |      (|)\n |\n |\n_|_\n");
        } else if(mistakes == 7){
            System.out.println(" _________\n |       |\n |       O\n |      (|)\n |      /\n |\n_|_\n");
        } else if(mistakes == 8){
            System.out.println(" _________\n |       |\n |       O\n |      (|)\n |      / \\\n |\n_|_\n");
        }
    }

    //displays the spaces empty and filled when guessing the word
    public void displayCharSpaces(){
        for(int i = 0; i < isCharFoundList.length; i++){
            if(isCharFoundList[i].isCharFound()){
                System.out.print(wordToGuess.charAt(i) + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.print("\t" + "Incorrect Guesses: "+ getMistakes() + " / 8\n\nLetters Guessed: " + guessList + "\n");
        System.out.println();
    }

    //checks if all letters of the word have been found
    public boolean checkSuccess(){
        for (Charcheck charcheck : isCharFoundList) {
            if (!charcheck.isCharFound()) {
                return false;
            }
        }
        return true;
    }

    //returns game title - saves time
    public static void displayGameTitle(){
        System.out.println("|     Hangman     |\n\n");
    }

}