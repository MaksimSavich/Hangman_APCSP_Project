package com.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main (String[] str) throws IOException, InterruptedException {

        //construct an arraylist calld wordlist that stores stringes
        ArrayList<String> wordList = new ArrayList<>();
        String charInput;

        //construct a scanner object called scan that looks for user input
        Scanner scan = new Scanner(System.in);

        //displays the game title
        HangmanGame.displayGameTitle();

        //asks the user to make an input to begin the game
        System.out.println("\nPress \"ENTER\" to begin the game or input \"QUIT\" in all capitals to end the game");
        String beginInput = scan.nextLine();

        if(beginInput.equals("QUIT")){
            System.exit(-100);
        }

        //constructs a HangmanGame object 'game'
        HangmanGame game = null;

        //asks user if they want to use predefined word list or their own
        if(beginInput.length() >= 0){
            System.out.println("Type \"YES\" in all capitals to use your own wordlist, otherwise press \"ENTER\" to continue");
            if(scan.nextLine().equals("YES")){

                System.out.println("Input a raw .txt file link containing words split line by line. If you would still rather use the predefined word list then press \"ENTER\"");
                boolean isLinkValid = EventHandler.checkURL(scan.nextLine());
              if(isLinkValid){
                
              }el
            } else{
                game = new HangmanGame(wordList);
            }
        }

        //while loop that essentially runs the game using all the methods
        while(beginInput.equals("")){

            //clears console at the start of the game
            EventHandler.refreshConsole(game);

            /*
             * if success or the user ran out of mistakes then exit the gameplay screen
             * within the while loop, the input is checked for the right kind of input
            */
            while(game.getMistakes() < 8 && !game.checkSuccess()){
                System.out.println("Input only a single character:");
                charInput = scan.nextLine();

                charInput = EventHandler.onlyLettersCheck(game, charInput, scan);
                charInput = EventHandler.alreadyUsedLetter(game, charInput, scan);
                game.inputCharCheck(charInput);

                EventHandler.refreshConsole(game);

            }

            EventHandler.refreshConsole(game);

            //once the game has ended, tell the user if they won or lost and what the word was
            if(game.checkSuccess()){
                System.out.println("\nYou Won! You found the word: " + game.getWordToGuess() + ".");
            } else {
                System.out.println("\nYou Lost! The word you were searching for was: " + game.getWordToGuess() + ".");
            }
            
            //ask the user if they would like to play again
            System.out.println("\nWould you like to play again? [Press ENTER to continue | Type \"QUIT\" in all capitals to end the game]");

            //allow the user to quit the game. If they choose to quit a true boolean value is passed to the endGame function to end the game contained within the object game.
            game.endGame(scan.nextLine().equals("QUIT"));
        }
        scan.close();

    }
}
