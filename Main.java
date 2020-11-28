
import java.io.*;

import java.util.*;

public class Main {

	// function to check that value x is at

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

// game will look good if missing values are between 20-50 that we can genrate using random number
// But right no I am using the variable K to set difficulty level if K will be more than difficulty will be more
		int k = sc.nextInt();
		Sudoku obj = new Sudoku(k);

		// fill the sudoku
		obj.fillValues();

		// To print the sudoku
		obj.printSudoku();

	}

}

class Sudoku {
	int[] mat[];

	int K; // No. Of missing digits

	// Constructor
	Sudoku(int K) {

		this.K = K;

		mat = new int[9][9];
	}

	// Sudoku Generator
	public void fillValues() {
		// Fill the diagonal of SRN x SRN matrices
		fillDiagonal();

		// Fill remaining blocks
		fillRemaining(0, 3);

		// Remove Randomly K digits to make game
		removeKDigits();
	}

	// Fill the diagonal 3 number of 3 x 3 matrices
	void fillDiagonal() {

		for (int i = 0; i < 9; i = i + 3)

			// for diagonal box, start coordinates->i==j
			fillBox(i, i);
	}

	// Returns false if given 3 x 3 block contains num.
	boolean unUsedInBox(int rowStart, int colStart, int num) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (mat[rowStart + i][colStart + j] == num)
					return false;

		return true;
	}

	// Fill a 3 x 3 matrix.
	void fillBox(int row, int col) {
		int num;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				do {
					num = randomGenerator(9);
				} while (!unUsedInBox(row, col, num));

				mat[row + i][col + j] = num;
			}
		}
	}

	// Random generator
	int randomGenerator(int num) {
		return (int) Math.floor((Math.random() * num + 1));
	}

	// Check if safe to put in cell i.e there should be no voilation of suduoku
	// property
	boolean CheckIfSafe(int i, int j, int num) {
		return (unUsedInRow(i, num) && unUsedInCol(j, num) && unUsedInBox(i - i % 3, j - j % 3, num));
	}

	// check in the row for existence
	boolean unUsedInRow(int i, int num) {
		for (int j = 0; j < 9; j++)
			if (mat[i][j] == num)
				return false;
		return true;
	}

	// check in the row for existence
	boolean unUsedInCol(int j, int num) {
		for (int i = 0; i < 9; i++)
			if (mat[i][j] == num)
				return false;
		return true;
	}

	// To fill remaining matrix

	boolean fillRemaining(int i, int j) {

		if (j >= 9 && i < 8) {
			i = i + 1;
			j = 0;
		}
		if (i >= 9 && j >= 9)
			return true;

		if (i < 3) {
			if (j < 3)
				j = 3;
		} else if (i < 6) {
			if (j == (int) (i / 3) * 3)
				j = j + 3;
		} else {
			if (j == 6) {
				i = i + 1;
				j = 0;
				if (i >= 9)
					return true;
			}
		}

		for (int num = 1; num <= 9; num++) {
			if (CheckIfSafe(i, j, num)) {
				mat[i][j] = num;
				if (fillRemaining(i, j + 1))
					return true;

				mat[i][j] = 0;
			}
		}
		return false;
	}

	// Remove the K no. of digits to genrate the game
	public void removeKDigits() {
		int count = K;
		while (count != 0) {
			int cellId = randomGenerator(81);

			int i = (cellId / 9);
			int j = cellId % 9;
			if (j != 0)
				j = j - 1;

			if (mat[i][j] != 0) {
				count--;
				mat[i][j] = 0;
			}
		}
	}

	// Print the sudoku
	public void printSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				System.out.print(mat[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
}
