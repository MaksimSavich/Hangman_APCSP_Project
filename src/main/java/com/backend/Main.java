package com.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* TODO
    export cli game to a static cli class method called startGame()
 */
public class Main{
    public static void main (String[] str) throws IOException, InterruptedException {

        ArrayList<String> techList = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        //displays the game title
        HangmanGame.displayGameTitle();

        System.out.println("\nPress \"ENTER\" to begin the game!");
        String beginPhrase = scan.nextLine();
        String charInput;

        //constructs a HangmanGame object 'game'
        HangmanGame game = null;

        //asks user if they want to use predefined word list or their own
        if(beginPhrase.equals("")){
            System.out.println("Type \"YES\" in all capitals to use your own wordlist, otherwise press \"ENTER\" to continue");
            if(scan.nextLine().equals("YES")){
                System.out.println("Input a raw .txt file link containing words split line by line. If you would still rather use the predefined word list then press \"ENTER\"");
                String linkInput = scan.nextLine();
                    if(linkInput != ""){
                        game = new HangmanGame(techList, linkInput);
                    } else{
                        game = new HangmanGame(techList);
                    }
            } else{
                game = new HangmanGame(techList);
            }
        }

        //while loop that essentially runs the game using all the methods
        while(beginPhrase.equals("")){

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
                game.charCheck(charInput);

                EventHandler.refreshConsole(game);

            }

            EventHandler.refreshConsole(game);

            //once the game has ended, tell the user if the won or lost and what the word was
            if(game.checkSuccess()){
                System.out.println("\nYou Won! You found the word: " + game.getWordToGuess() + ".");
            } else {
                System.out.println("\nYou Lost! The word you were searching for was: " + game.getWordToGuess() + ".");
            }

            System.out.println("\nWould you like to play again? [Press ENTER to continue | Type \"QUIT\" in all capitals to end the game]");

            //allow the user to quit the game
            game.endGame(scan.nextLine().equals("QUIT"));
        }
        scan.close();

    }
}
