/*You have a giv en, fixed amount of
coins and you can only pay the cashier with these coins, and the cashier accepts only the exact amount. If
can have the exact amount you have to use the maximum number of coins possible to pay the bill.
The coins distribution will be initialized to the original state after each paying process.
Explanation:
Let’s assume you have the following coins: { 1, 1, 2, 5, 25, 25, 25 } cents, and you have to pay 7 cents.
1 + 1 + 5 == 7, and
2 + 5 == 7.
The sequence { 1, 1, 5 } contains 3 coins and the sequence { 2, 5 } contains 2 coins. You hav e to use the
sequence { 1, 1, 5 } to pay.
Let’s assume you have the following coins: { 1, 1, 2, 5, 25, 25, 25 } cents, and you have to pay 11 cents.
This is not possible.
*/

/*
 * Coins.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

/**
 * This program determines if a sequence of coins exist adding up to a given value
 *
 * @author Ria Lulla
 * @author Chirayu Marathe
 */

public class Coins {
    static int[] coins = { 1, 1, 2, 5, 25, 25, 25 };
    static int[] toPay = { 0, 1, 4, 5, 7, 8 };
    //static int[] coins ={ 1, 5, 25, 25, 25 };
    //static int[] toPay ={ 0, 2, 30, 75 };
    static int index = 0;
    static int[][] combinationArray = new int[( int )( Math.pow( 2, coins.length )) ][coins.length ]; // number of combinations is 2 ^ n
    static int[] tempArray = new int[ coins.length ];
    static  int[] sumArray = new int[( int )( Math.pow( 2, coins.length )) ]; //stores sum of all combinations

    /**
     * Generates all combinations of the given array recursively
     *
     * @param inputArray  coins array
     * @param tempArray   stores each combination
     * @param counter     to iterate through the elements
     * @return
     */

    private static void generatecombinations( int[] inputArray, int[] tempArray, int counter ) {

        for ( int i = counter; i < inputArray.length; i++ ) {
            tempArray[i] = inputArray[i];   // add the element
            generatecombinations( inputArray, tempArray, i + 1 );
            tempArray[i] = 0;              // remove each element by replacing it with zero
        }

        printCombinations(tempArray);     // base case stores the combination in the combinationArray

    }

    /**
     * Storing the combinations in the combination Array
     * and calculating the sum of each combination
     *
     * @param tempArray  each combination passed from recursive call
     * @return
     */

    private static void printCombinations( int[] tempArray ) {
        for ( int j = index; j < index + 1; j++ ) {       // index value will always be the current combination number
            for ( int k = 0; k < coins.length; k++ ) {
                combinationArray[j][k] = tempArray[k];
                sumArray[j] = sumArray[j] + tempArray[k];
            }

        }
        index++;
    }

    /**
     * Finding the combination that matches the toPay array values
     *
     * @param sumArray  sum of all combinations
     * @param combinationArray  combinations stores in a 2D array
     * @return
     */

    private static void coinSequence( int[] sumArray, int[][] combinationArray ) {
        for( int i = 0; i < toPay.length; i++ ) {
            System.out.print( toPay[i] + " : " );
            if ( toPay[i] == 0 ) {                  // Do not include 0 as it has no value
                System.out.println( "can not be paid" );
            } else {
                for ( int j = 0; j < sumArray.length; j++ ) {
                    if ( toPay[i] == sumArray[j] ) {   // print combination if sum is equal to toPay element
                        System.out.print( "yes; used coins = " );
                        for ( int k = 0; k < coins.length; k++ ) {
                            if ( combinationArray[j][k] != 0 ) {    // exclude all zeros while printing
                                System.out.print( combinationArray[j][k] + " cents " );
                            }

                        }

                        System.out.println(" ");
                        break;

                    } else if ( j == sumArray.length - 1 ) {   // if toPay element is not equal to sum, check till the end of sum array
                        System.out.print( "no; can not be paid with the following sequence of coins: [ " );
                        for ( int n = 0; n < coins.length; n++ ) {
                            System.out.print( " " + coins[n] + ", " );
                        }
                        System.out.println( " ] " );
                    }
                }
            }
        }
    }
    /**
     * Main program call : generate combinations and check coin sequence
     *
     * @param args  command line arguments (ignored)
     */

    public static void main(String[] args) {

        generatecombinations( coins, tempArray, 0 );
        coinSequence( sumArray,combinationArray );

    }
}