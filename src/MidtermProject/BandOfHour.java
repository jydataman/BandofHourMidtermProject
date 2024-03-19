package MidtermProject;
import java.util.Scanner;

/**
 BandOfHour class represents a program for managing a band arrangement.
 It allows adding, removing, and displaying musicians in the band.
 The band arrangement is stored in a 2D array where rows represent positions in the band.
 Musicians' weights are tracked to ensure the band doesn't exceed weight limits.
 @author Joshua Yepes
 */

public class BandOfHour {
    private static final Scanner keyboard = new Scanner(System.in);
    /**
     Maximum number of rows allowed in the band arrangement.
     */
    public static final int MAX_ROWS = 10;

    /**
     Maximum number of columns allowed in the band arrangement.
     */
    public static final int MAX_COLUMNS = 8;

    /**
     Minimum weight allowed for a musician in the band.
     */
    public static final double MIN_WEIGHT = 45.0;

    /**
     * Maximum weight allowed for a musician in the band.
     */
    public static final double MAX_WEIGHT = 200.0;

    /**
     Maximum total weight allowed per row in the band arrangement.
     This weight represents the total weight limit for all musicians in a single row.
     */
    public static final double MAX_WEIGHT_PER_ROW = 100.0;

    /**
     * The main method of the program.
     * It prompts the user to input the number of rows and positions,
     * initializes the band arrangement data, and s the menu for managing musicians.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        int rows;
        int numPositions;

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Welcome to the band of the hour");
        System.out.println("----------------");

        do {
            System.out.print("Enter the number of rows: ");
            rows = keyboard.nextInt();

            if (rows < 1 || rows > MAX_ROWS) {
                System.out.println("ERROR: Out of range, try again: ");

            }
        } while (rows < 1 || rows > MAX_ROWS);

        double[][] bandArrangementData = new double[rows][];

        for (int positionIndex = 0; positionIndex < rows; positionIndex++) {
            do {
                System.out.print("Please enter number of positions in row " + (char)('A' + positionIndex) + ": ");
                numPositions = keyboard.nextInt();
                if (numPositions < 1 || numPositions > MAX_COLUMNS) {
                    System.out.println("ERROR: Out of range, try again:  ");
                }
            } while (numPositions < 1 || numPositions > MAX_COLUMNS);

            bandArrangementData[positionIndex] = new double[numPositions];
        }

        displayMenu(bandArrangementData);

    } // end of main method

    /**
     * Displays the menu for managing musicians in the band.
     * Allows adding, removing, printing musicians, or exiting the program.
     *
     * @param bandArrangementData The 2D array representing the band arrangement.
     */

    public static void displayMenu(double[][] bandArrangementData) {
        Scanner keyboard = new Scanner(System.in);
        char userChoice;

        do {
            System.out.print("(A)dd, (R)emove, (P)rint, e(X)it : ");
            userChoice = keyboard.next().charAt(0);

            switch (Character.toUpperCase(userChoice)) {
                case 'A':
                    addMusician(bandArrangementData);
                    System.out.println(" ");
                    break;
                case 'P':
                    printArray(bandArrangementData);
                    System.out.println(" ");
                    break;
                case 'R':
                    removeMusician(bandArrangementData);
                    System.out.println(" ");
                    break;
                case 'X':
                    System.out.println("Exiting the program");
                    break;
                default:
                    System.out.println("ERROR: Invalid option, try again");
            }

        } while (Character.toUpperCase(userChoice) != 'X');

    } // end of menu method

    /**
     * Prints the current band arrangement.
     * Includes the weights of musicians in each row and their total and average weights.
     *
     * @param bandArrangementData The 2D array representing the band arrangement.
     */

    public static void printArray(double[][] bandArrangementData) {
        int spacesBeforeBracket;
        char rowLetter;
        double totalWeight;
        double averageWeight;


        for (int rowIndex = 0; rowIndex < bandArrangementData.length; rowIndex++) {
            rowLetter = (char) ('A' + rowIndex);
            System.out.print(rowLetter + ": ");

            totalWeight = calculateTotalWeight(bandArrangementData[rowIndex]);
            averageWeight = calculateAverageWeight(bandArrangementData[rowIndex]);

            for (int positionIndex = 0; positionIndex < bandArrangementData[rowIndex].length; positionIndex++) {
                System.out.printf("%6.1f ", bandArrangementData[rowIndex][positionIndex]);
            }

            spacesBeforeBracket = 40 - bandArrangementData[rowIndex].length * 7;

            System.out.printf("%" + spacesBeforeBracket + "s", "");
            System.out.printf("%13s", "[");
            System.out.printf("%4.1f, ", totalWeight);
            System.out.printf("%8.1f", averageWeight);
            System.out.println("]");
        }

    } // end of print bandArrangementData

    /**
     * Calculates the total weight of musicians in a row.
     *
     * @param rowPositions The array representing the weights of musicians in a row.
     * @return The total weight of musicians in the row.
     */

    public static double calculateTotalWeight(double[] rowPositions) {
        double totalWeight = 0;

        for (double weight : rowPositions) {
            totalWeight = totalWeight + weight;
        }

        return totalWeight;

    } // end of total weight method

    /**
     * Calculates the average weight of musicians in a row.
     *
     * @param rowPositions The array representing the weights of musicians in a row.
     * @return The average weight of musicians in the row.
     */

    public static double calculateAverageWeight(double[] rowPositions) {
        double totalWeight;
        int numPositions;
        double averageWeight;

        totalWeight = calculateTotalWeight(rowPositions);
        numPositions = rowPositions.length;
        averageWeight = totalWeight / numPositions;

        return averageWeight;

    } // end of average weight method

    /**
     * Adds a musician to the band arrangement.
     * Prompts the user to input row, position, and weight of the musician.
     *
     * @param bandArrangementData The 2D array representing the band arrangement.
     */

    public static void addMusician(double[][] bandArrangementData) {
        char row;
        int positionIndex;
        int rowIndex;
        double weight;
        double totalWeight;
        double maxWeightPerRow;


        do {
            System.out.print("Enter the row letter: ");
            row = Character.toUpperCase(keyboard.next().charAt(0));
            rowIndex = row - 'A';
            if (validateRow(row, bandArrangementData)) {
                System.out.println("ERROR: Invalid row letter, try again.");
            }
        } while (validateRow(row, bandArrangementData));


        do {
            System.out.print("Enter the position number (1 to " + bandArrangementData[rowIndex].length + "):  ");
            positionIndex = keyboard.nextInt();

            if (validatePosition(positionIndex, bandArrangementData[rowIndex])) {
                System.out.println("ERROR: Invalid positionIndex number, try again.");
            }
            else if (bandArrangementData[rowIndex][positionIndex - 1] != 0) {
                System.out.println("ERROR: There is already a musician there.");
                System.out.println(" ");
                return;
            }
        } while (validatePosition(positionIndex, bandArrangementData[rowIndex]));

        do {
            System.out.print("Enter the weight of the musician (" + MIN_WEIGHT + " and " + MAX_WEIGHT + "):  ");
            weight = keyboard.nextDouble();

            totalWeight = 0;
            for (double musicianWeight : bandArrangementData[rowIndex]) {
                totalWeight = totalWeight + musicianWeight;
            }

            maxWeightPerRow = bandArrangementData[rowIndex].length * MAX_WEIGHT_PER_ROW;

            if (totalWeight + weight > maxWeightPerRow) {
                System.out.println("ERROR: That would exceed the average weight limit");
                System.out.println(" ");
                return;
               

            } else if (validateWeight(weight)) {
                System.out.println("Invalid weight. Please enter a weight between " + MIN_WEIGHT + " and " + MAX_WEIGHT);
            } else {
                break;
            }
        } while (validateWeight(weight));

        bandArrangementData[rowIndex][positionIndex - 1] = weight;
        System.out.println("****** Musician added.");

    } // add method

    /**
     * Validates if the row letter provided by the user is valid.
     *
     * @param row               The row letter provided by the user.
     * @param bandArrangementData The 2D array representing the band arrangement.
     * @return True if the row letter is invalid, false otherwise.
     */

    public static boolean validateRow(char row, double[][] bandArrangementData) {
        int rowIndex;

        rowIndex = row - 'A';

        return rowIndex < 0 || rowIndex >= bandArrangementData.length;

    } // end of validateRow

    /**
     * Validates if the position index provided by the user is valid.
     *
     * @param positionIndex     The position index provided by the user.
     * @param row               The array representing a row in the band arrangement.
     * @return True if the position index is invalid, false otherwise.
     */

    public static boolean validatePosition(int positionIndex, double[] row) {

        return positionIndex < 1 || positionIndex > row.length;

    } // end of validatePosition

    /**
     * Validates if the weight provided by the user is valid.
     *
     * @param weight The weight provided by the user.
     * @return True if the weight is invalid, false otherwise.
     */

    public static boolean validateWeight(double weight) {

        return !(weight >= MIN_WEIGHT) || !(weight <= MAX_WEIGHT);

    } // end of validate weight

    /**
     * Removes a musician from the band arrangement.
     * Prompts the user to input row and position of the musician to be removed.
     *
     * @param bandArrangementData The 2D array representing the band arrangement.
     */

    public static void removeMusician(double[][] bandArrangementData) {
        char row;
        int positionIndex;
        int rowIndex;
        boolean validInput = false;


        do {
            System.out.print("Enter the row letter: ");
            row = keyboard.next().toUpperCase().charAt(0);
            rowIndex = row - 'A';

            if (validateRow(row, bandArrangementData)) {
                System.out.println("ERROR: Invalid row letter, try again.");
            } else {
                break;
            }
        } while (validateRow(row,bandArrangementData));

        do {
            System.out.print("Enter the position number (1 to " + bandArrangementData[rowIndex].length + "):  ");
            positionIndex = keyboard.nextInt();

            if (validatePosition(positionIndex, bandArrangementData[rowIndex])) {
                System.out.println("ERROR: Invalid position number, try again.");
            } else if (bandArrangementData[rowIndex][positionIndex - 1] == 0) {
                System.out.println("ERROR: That position is vacant.");
                System.out.println(" ");
                return;
            } else {
                validInput = true;
            }
        } while (!validInput);

        bandArrangementData[rowIndex][positionIndex - 1] = 0;
        System.out.println("****** Musician removed.");

    } // end of remove method

} // end of class

