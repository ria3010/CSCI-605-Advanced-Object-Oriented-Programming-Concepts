/*The objective is to hav e the least amount
of coins in your wallet after the transaction is completed. The modification of the problem is that the
cashier can also give you coins back, in other words you do not need the exact amount.
Explanation:
Assuming you have to pay 8 cents, and the coins in your wallet are:
{ 1, 1, 1, 1, 1, 5 }
and the cashier has the following coin:
{2}
If you pay the cashier { 1, 1, 1, 5 } you will have { 1, 1 }; 2 coins in your wallet.
If you pay the cashier { 1, 1, 1, 1, 1, 5 } you will have { 2 };1coin in your wallet, because the cashier gives
you one coin with the value of 2 cents back.
Your Work:
Your work is to write a program to achieve the objective. The objective is to hav e the least amount of coins
in your wallet after the transaction is completed. The modification of the problem is that the cashier can
also give you coins back, in other words you do not need the exact amount.*/
/*
 * Coins_v2.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

/**
 * This program determines the least amount of coins in your wallet after the transaction is completed
 *
 * @author Ria Lulla
 */

public class Coins_v2 {


    static int[] coins = { 1, 1, 1, 1, 5, 5, 10, 10, 10 };
    static int[] cashierPay = { 2 };
    static int[] toPay = { 7 };
    static int index = 0;
    // number of coins combinations is 2 ^ n
    static int[][] combinationArray = new int[(int) (Math.pow(2, coins.length))][coins.length];
    static int[] tempArray = new int[coins.length];
    //stores sum of all coins combinations
    static int[] sumArray = new int[(int) (Math.pow(2, coins.length))];
    static int tempSizeOfSequenceArray = 0;
    //stores cashier combinations
    static int[][] cashiercombinationArray = new int[(int) (Math.pow(2, cashierPay.length))][cashierPay.length];
    static int indexForCashier = 0;
    //stores sum of all cashier combinations
    static int[] cashiersumArray = new int[(int) (Math.pow(2, cashierPay.length))];

    /**
     * Generates all combinations of the coins array recursively
     *
     * @param inputArray coins array
     * @param tempArray  stores each combination
     * @param counter    to iterate through the elements
     * @return
     */
    private static void generatecombinations( int[] inputArray, int[] tempArray, int counter ) {

        for ( int i = counter; i < inputArray.length; i++ ) {
            tempArray[i] = inputArray[i];   // add the element
            generatecombinations( inputArray, tempArray, i + 1 );
            tempArray[i] = 0;              // remove each element by replacing it with zero
        }

        storeCombinations(tempArray);     // base case stores the combination in the combinationArray

    }

    /**
     * Storing the combinations in the coins combination Array
     * and calculating the sum of each coins combination
     *
     * @param tempArray each combination passed from recursive call
     * @return
     */

    private static void storeCombinations( int[] tempArray ) {
        // index value will always be the current combination number
        for ( int j = index; j < index + 1; j++ ) {
            for ( int k = 0; k < coins.length; k++ ) {
                combinationArray[j][k] = tempArray[k];
                sumArray[j] = sumArray[j] + tempArray[k]; //calculates sum of all combinations
            }

        }
        index++;
    }

    /**
     * Storing the combinations in the cashier combination Array
     * and calculating the sum of each cashier combination
     *
     * @param tempArray each combination passed from recursive call
     * @return
     */

    private static void generateCashierCombinations( int[] inputArray, int[] tempArray, int counter ) {

        for ( int i = counter; i < inputArray.length; i++ ) {
            tempArray[i] = inputArray[i];   // add the element
            generateCashierCombinations( inputArray, tempArray, i + 1 );
            tempArray[i] = 0;              // remove each element by replacing it with zero
        }

        for ( int j = indexForCashier; j < indexForCashier + 1; j++ ) {
            for ( int k = 0; k < cashierPay.length; k++ ) {
                cashiercombinationArray[j][k] = tempArray[k];
                cashiersumArray[j] = cashiersumArray[j] + tempArray[k]; //calculates sum of all cashier combinations
            }

        }
        indexForCashier++;
    }

    /**
     * Iterates through all coins combinations to find the least coins
     *
     * @param toPayElement each toPay element in the array
     * @return
     */

    private static void findLeastCoins( int toPayElement ) {
        int[] sequenceArray = new int[coins.length];
        int[] newTempArray = new int[cashierPay.length];
        // call to generate cashier combinations
        generateCashierCombinations(cashierPay, newTempArray, 0);
        for ( int j = 0; j < sumArray.length; j++ ) {
            if (sumArray[j] > toPayElement) {

                for ( int k = 0; k < coins.length; k++ ) {
                    sequenceArray[k] = combinationArray[j][k];
                }
                doesCombinationSatisfyCondition( sequenceArray, sumArray[j], toPayElement, cashiersumArray );


            }

        }
    }

    /**
     * finding the transaction with least number of coins
     *
     * @param sequenceArray    sequence array from the coins combinations
     * @param sumOfCombination sum of the coins combinations
     * @param eachToPayElement to Pay element
     * @param cashiersumArray  sum of the cashier array
     * @return
     */

    private static void doesCombinationSatisfyCondition( int[] sequenceArray, int sumOfCombination, int eachToPayElement, int[] cashiersumArray ) {


        for ( int i = 0; i < cashiersumArray.length; i++ ) {

            //subtract the total sum of combination from each cashier's sum combination
            if ( ( sumOfCombination - cashiersumArray[i] ) == eachToPayElement ) {
                int originalSizeOfSequenceArray = 0;
                for ( int j = 0; j < sequenceArray.length; j++ ) {
                    if ( sequenceArray[j] != 0 ) {
                        originalSizeOfSequenceArray = originalSizeOfSequenceArray + 1;
                    }
                }

                if ( tempSizeOfSequenceArray < originalSizeOfSequenceArray ) {
                    tempSizeOfSequenceArray = originalSizeOfSequenceArray;
                    System.out.print( " " + eachToPayElement + " : " );
                    System.out.print( " I gave the cashier the following coins " );

                    for ( int k = sequenceArray.length - 1; k >=0 ; k-- ) {
                        if ( sequenceArray[k] != 0 ) {
                            System.out.print( " " + sequenceArray[k] + " cents" + " " );
                        }
                    }

                    System.out.print( "and the cashier gave me  " + cashiersumArray[i] + " cents" );
                }

                System.out.println( " " );
            }

        }

    }

    /**
     * if the toPay amount is the exact amount as that in the coins wallet, we would give exact amount,
     * no cashier wallet will be considered in this case
     *
     * @param toPayEl each toPay element in the array
     * @return
     */

    private static void checkIfCoinsWalletHasExactAmountForEachToPay( int toPayEl ) {
        int sum = 0;
        for ( int i = 0; i < coins.length; i++ ) {
            sum = sum + coins[i];
        }
        if ( sum == toPayEl ) {
            System.out.print( toPayEl + " : " );
            System.out.print( "I gave the cashier the following coins " );
            for ( int i = coins.length -1 ; i >=0; i-- ) {
                System.out.print( +coins[i] + " cents " );

            }
            System.out.println( "and the cashier gave me 0 cents " );
        }
    }


    /**
     * Main program call : generate combinations and check findLeastCoins
     *
     * @param args command line arguments (ignored)
     */

    public static void main(String[] args) {
        generatecombinations( coins, tempArray, 0 );
        for ( int p = 0; p < toPay.length; p++ ) {
            cashiersumArray = new int[(int) (Math.pow(2, cashierPay.length))];
            cashiercombinationArray = new int[(int) (Math.pow(2, cashierPay.length))][cashierPay.length];
            indexForCashier = 0;
            tempSizeOfSequenceArray = 0;
            checkIfCoinsWalletHasExactAmountForEachToPay( toPay[p] );
            findLeastCoins( toPay[p] );
        }

    }
}