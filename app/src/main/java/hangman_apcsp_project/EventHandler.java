package hangman_apcsp_project;
import java.util.Scanner;
import java.net.*;

public class EventHandler {

    //refreshes the console after each input and displays the updated data
    public static void refreshConsole(HangmanGame game){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        HangmanGame.displayGameTitle();
        game.drawing();
        game.displayCharSpaces();
    }

    //Checks if the inputs are only letters
    public static String onlyLettersCheck(HangmanGame game, String charInput, Scanner scan){

        while(!charInput.matches("[A-Za-z]")){
            EventHandler.refreshConsole(game);

            if(charInput.length() != 1){
                System.out.println("Only input one letter!");
            }else{
                System.out.println("Only input letters!");
            }
            charInput = scan.nextLine();
        }
        return charInput;
    }

    //Checks if the letters are already used
    public static String alreadyUsedLetter(HangmanGame game, String charInput, Scanner scan){
        while(game.inputCharacterCheck(charInput) == 0){
            EventHandler.refreshConsole(game);
            System.out.println("That letter has already been used");
            charInput = scan.nextLine();
            charInput = onlyLettersCheck(game, charInput, scan);
        }
        return charInput;
    }

    //checks if the URL is in a valid format, is a txt file, and resolves to a real location
    public static int checkURL(String url){
        
        if(url.equals("")){
            return 0;
        } else if(EventHandler.isValidURL(url) == true && url.substring(url.length() - 4).equals(".txt")){
            try{
                URL urlToSearch = new URL(url);
                HttpURLConnection huc = (HttpURLConnection) urlToSearch.openConnection();
                huc.setRequestMethod("HEAD");
                int responseCode = huc.getResponseCode();
                if(responseCode == 200){
                return 1;
                }
            }
            catch (Exception e){
                return -1;
            }    
        }
      return -1;
    }
    
    //checks if the URL is in a vailid format
    public static boolean isValidURL(String url){
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    

}
