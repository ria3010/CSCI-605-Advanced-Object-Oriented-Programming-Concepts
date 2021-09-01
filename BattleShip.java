/*You program should present that a 3-dimensional ocean.
A visual representation could look like:
Play
Your program should print the battleship scenario and then prompt the user for column/row position.
Hits
If a boat is hit then the complete boat turns to water, in other word it sinks.
End State
The program ends when all parts of all boats are hit.
Board
Each boat has its uniq id. The range of an id is defined as: 1 ≤ id ≤ 128. In the image above you can see 3
boats: one is 1 squares long, one is 2 squares long, one is 3 squares long.
The ocean is defined with a simple language, where each is statement is one line, and each keyword is at
the beginning of the line. The keywords are width, height, row. It is guaranteed that the keywords width,
height are on line 1 and 2, but the order is not guaranteed. row can only be followed by:
w which stands for water
id indicate a square of boat id, You can assume that
all id’s are ≥ 0, and all id’s are different
It is guaranteed that the keyword row starts with line 3, and that there are height row lines and the are width
water or boat id’s following each row line.
Keyword Explanation
width so many squares in x direction
height so many squares in y direction
row next row starts
An example for the image above:
-3-
width 3
height 3
row b1 b2 b1
row w b2 w
row w b2 b3
It is guaranteed that the boats are only in horizontal or vertical directions. This would be an example of a
illegal input:
width 3
height 3
row b2 b2 b1 # boat 2 to is not horizontal or vertical
row w b2 # not enough entries
row w b2 b3
In other words you can assume the input is correct.*/
/*
 * BattleShip.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

/**
 * This program represents a battleship game using the Scanner class
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Boat{
    static int width = 0;
    static int height = 0;
    static final String rowKeyword = "row\\s*";
    static final char DOT = '.';
    static final char HIT = 'x';

    /**
     * This method checks if the boat is present at user specified coordinates
     *
     * @param xCoordinate               user's x-coordinate
     * @param yCoordinate               user's y-coordinate
     * @param battleFieldShownToUser    hidden battlefield shown to user
     * @param originalBattleField       battlefield from b1.txt

     * @return
     */

    protected void checkBoatPresent( int xCoordinate, int yCoordinate, char[][] battleFieldShownToUser, String[][] originalBattleField ) {

        if ( !originalBattleField[xCoordinate][yCoordinate].equals("w") ) {
            correctGuess( originalBattleField, xCoordinate, yCoordinate, battleFieldShownToUser );
            System.out.println( "HIT" );

        } else {
            battleFieldShownToUser[xCoordinate][yCoordinate] = 'w';
            displayBattleField( battleFieldShownToUser );
        }

    }

    /**
     * This method checks if the user entered correct guess
     *
     * @param xCoordinate               user's x-coordinate
     * @param yCoordinate               user's y-coordinate
     * @param battleFieldShownToUser    hidden battlefield shown to user
     * @param originalBattleField       battlefield from b1.txt

     * @return
     */

    private void correctGuess( String[][] originalBattleField ,int xCoordinate, int yCoordinate, char[][]battleFieldShownToUser ) {
        for( int i =0; i < originalBattleField.length; i++ ){
            for( int j=0; j < originalBattleField[i].length; j++ ){
                if( originalBattleField[xCoordinate][yCoordinate].equals( originalBattleField[i][j] ) ){
                    battleFieldShownToUser[i][j] = HIT;
                }
            }
        }
        displayBattleField( battleFieldShownToUser );
    }
    /**
     * This method displays the battlefield to the user
     * @param battleFieldShownToUser    hidden battlefield shown to user

     * @return
     */


    public void displayBattleField( char[][] battleFieldShownToUser ) {
        System.out.println("\n x indicates a hit \n w indicates a miss, but you know now there is water.\n\n");
        for(int col = 0 ; col < width; col++){
            System.out.print("   " + col );
        }

        for( int i = 0; i < height; i++ ){
            System.out.print("\n"+ i +": ");
            for( int j = 0; j < width; j++ ){
                System.out.print(" " + battleFieldShownToUser[i][j] + " ");
            }

            System.out.println();

        }
    }

    /**
     * This method checks if all boats have been guessed or not
     *
     * @param battleFieldShownToUser  hidden battlefield shown to user
     * @param originalBattleField     battlefield from b1.txt
     * @return boolean
     */

    protected static boolean notDone( char[][] battleFieldShownToUser, String[][] originalBattleField ) {
        for ( int i = 0; i < battleFieldShownToUser.length; i++ ) {
            for ( int j = 0; j < battleFieldShownToUser[i].length; j++ ) {
                if ( battleFieldShownToUser[i][j] == '.' && ( !originalBattleField[i][j].equals("w") ) ) {
                    return false;
                }

            }
        }
        return true;
    }



}



class Ocean extends Boat{
    /**
     * This method takes in input coordinates from user
     *
     * @param battleFieldShownToUser  hidden battlefield shown to user
     * @param originalBattleField     battlefield from b1.txt
     * @return
     */

    public void receiveCoordinatesFromUser(char[][] battleFieldShownToUser, String[][] originalBattleField) {
        do {
            Scanner scanCoordinates = new Scanner(System.in);
            System.out.print("\nrow coordinate (0 " + "<= row < " + height + "): ");
            int x = scanCoordinates.nextInt();
            System.out.print("column coordinate (0 " + "<= column < " + width + "): ");
            int y = scanCoordinates.nextInt();
            checkBoatPresent( x, y, battleFieldShownToUser, originalBattleField );
        } while( !notDone( battleFieldShownToUser, originalBattleField ));

    }



}
public class BattleShip extends Ocean{

    /**
     * This method locates the keyword width and height in the first two lines
     * and finds the associated length
     *
     * @param scanner scanner from b1.txt
     * @return
     */

    private static void findLengthOfKeyword( Scanner scanner ) {

        int lines = 2;
        while ( scanner.hasNextLine() && lines!=0 ) {
            String input = scanner.nextLine();
            Matcher widthMatcher = Pattern.compile( "\\bwidth\\b" ).matcher( input );
            Matcher heightMatcher = Pattern.compile( "\\bheight\\b" ).matcher( input );

            if ( widthMatcher.find() ) {
                //split by any number of whitespaces
                String[] widthKeyword = input.split( "[ \t]+" );
                width = Integer.parseInt( widthKeyword[ widthKeyword.length - 1 ] );
                System.out.println( "Width: " + width );
            }else if( heightMatcher.find() ){
                String[] heightKeyword = input.split("[ \t]+");
                height = Integer.parseInt( heightKeyword[ heightKeyword.length - 1 ] );
                System.out.println( "Height: " + height );
            }
            lines--;
        }
    }
    /**
     * This method locates the keyword row in the remaining file
     * and stores the battlefield in the string array
     *
     * @param scanner scanner from b1.txt
     * @return
     */

    private static void readTheRows( Scanner scanner ) {
        while ( scanner.hasNext() ){
            scanner.useDelimiter( rowKeyword );
            String[][] originalBattleField = new String[height] [width];
            for( int i = 0; i < height; i++ ){
                String input = scanner.next();
                String[] eachInput = input.split("\\s+");
                for(int j = 0; j < width; j++){
                    originalBattleField[i][j] = eachInput[j];
                }
            }
            initializeGame( originalBattleField );

        }
    }
    /**
     * This method displays the initial battlefield
     *
     * @param battleFieldShownToUser char array shown to user
     * @return
     */

    private static void playBattleShip( char[][] battleFieldShownToUser ) {
        for( int i = 0; i < height; i++ ){
            for( int j=0; j < width; j++ ){
                battleFieldShownToUser[i][j] = DOT;
            }

        }
    }
    /**
     * This method initializes the game
     *
     * @param originalBattleField battlefield from b1.txt
     * @return
     */

    private static void initializeGame(String [][] originalBattleField) {
        char [][] battleFieldShownToUser = new char[height][width];
        Ocean ocean = new Ocean();
        Boat boat = new Boat();
        playBattleShip( battleFieldShownToUser );
        boat.displayBattleField( battleFieldShownToUser );
        ocean.receiveCoordinatesFromUser( battleFieldShownToUser, originalBattleField );

    }
    /**
     * Main program call
     *
     * @param args      
     */
    public static void main( String[] args ) {
        try{
            //C:\Users\Acer\Desktop\b1.txt
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the entire file location : ");
            String fileName = scanner.next();
            File file = new File(fileName);
            scanner = new Scanner(file);
            findLengthOfKeyword( scanner );
            readTheRows( scanner );
            scanner.close();
        }
        catch (FileNotFoundException e ) {
            e.printStackTrace();
        }

    }





}


