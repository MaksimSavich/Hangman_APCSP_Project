package hangman_apcsp_project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main (String[] str) throws IOException, InterruptedException {

        //construct an arraylist calld wordlist that is used to import a word list from the internet
        ArrayList<String> wordList = new ArrayList<>();

        //construct a scanner object called scan that looks for user input
        Scanner scan = new Scanner(System.in);

        //displays the game title
        HangmanGame.displayGameTitle();

        //asks the user to make an input to begin the game
        System.out.println("\nPress \"ENTER\" to begin the game.");
        String beginInput = scan.nextLine();
        
        //allows the user to quit the game inthe beginning 
        if(beginInput.equals("QUIT")){
            System.exit(-100);
        }

        //constructs a HangmanGame object called 'game'
        HangmanGame game = null;

        //asks the user if they want to use the default word list or their own
        if(beginInput.length() >= 0){
            System.out.println("Type \"YES\" in all capitals to use your own word list, otherwise press \"ENTER\" to use a predfined word list.");
            String userLink = scan.nextLine();
            if(userLink.equals("YES")){
                //if the user wants to use their own word list then they are asked to input one here and that link is checked for a correct format and that it resolves
                System.out.println("Input a raw .txt file link containing words split line by line. If you would still rather use the default word list then press \"ENTER\".");
                userLink = scan.nextLine();
                int isLinkGood = EventHandler.checkURL(userLink);

                //while the link is invalid, keep asking the user to input a valid link, allow them to use a default word list, or quit altogether
                while(isLinkGood == -1){
                    if(userLink.equals("QUIT")){System.exit(-100);} 
                    System.out.println("URL Invalid. Please input a new valid link or press \"ENTER\" to use the default word list.");
                    userLink = scan.nextLine();
                    isLinkGood = EventHandler.checkURL(userLink);
                }
                //occurs when user chooses the default word list
                if(isLinkGood == 0){
                    game = new HangmanGame(wordList);
                //occurs when the use inputs a valid link to a word list
                }else if(isLinkGood == 1){
                    game = new HangmanGame(wordList, userLink);
                }
            //occurs if the user chooses to quit the game
            } else if(userLink.equals("QUIT")){
                System.exit(-100);
            //occurs if the user chooses to use the default word list from the start
            }else{
                game = new HangmanGame(wordList);
            }
        }
    
        //initiates the charInput variable to store each char input by the user when playing the game
        String charInput;

        //while loop that runs the game using all of the methods
        while(true){

            //clears console at the start of the game
            EventHandler.refreshConsole(game);

            /*
             if success or the user ran out of mistakes then exit the gameplay screen
             within the while loop, the input is checked for the right kind of input
            */
            while(game.getMistakes() < 8 && !game.checkSuccess()){
                System.out.println("Input only a single character:");
                charInput = scan.nextLine();

                if(charInput.equals("QUIT")){game.endGame(true);}
                charInput = EventHandler.onlyLettersCheck(game, charInput, scan);
                charInput = EventHandler.alreadyUsedLetter(game, charInput, scan);
                game.inputCharacterCheck(charInput);

                EventHandler.refreshConsole(game);
            }

            EventHandler.refreshConsole(game);

            //once the game has ended, this tells the user if they won or lost and what the word was
            if(game.checkSuccess()){
                System.out.println("\nYou Won! You found the word: " + game.getWordToGuess() + ".");
            } else {
                System.out.println("\nYou Lost! The word you were searching for was: " + game.getWordToGuess() + ".");
            }
            
            //ask the user if they would like to play again
            System.out.println("\nWould you like to play again? [Press ENTER to continue | Type \"QUIT\" in all capitals to end the game]");

            //allow the user to quit the game. If they choose to quit a true boolean value is passed to the endGame function to end the game contained within the object game
            game.endGame(scan.nextLine().equals("QUIT"));
        }
    }
}
