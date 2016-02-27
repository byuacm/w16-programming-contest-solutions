import java.util.*;
import java.io.*;


public class Solution {

	static int MAX_SIZE = 10;
	
	static void floodfill(char pieceArray[][], int row, int col, int numLines) {
		if (row < 0 || row >= numLines)
			return;
		if (col < 0 || col >= pieceArray[row].length)
			return;
		if (pieceArray[row][col] != '#')
			return;
		pieceArray[row][col] = ' ';
		floodfill(pieceArray, row-1, col, numLines);
		floodfill(pieceArray, row, col+1, numLines);
		floodfill(pieceArray, row+1, col, numLines);
		floodfill(pieceArray, row, col-1, numLines);
	}
	public static void main(String[] args) {
		int numDatasets;
		String line;
		int numLines, numPieces;
		char pieceArray[][];
		
		Scanner scan = new Scanner(System.in);
		
		numDatasets = scan.nextInt();
		scan.nextLine();
		for(int i = 0; i < numDatasets; i++) {
			numLines = scan.nextInt();
      scan.nextLine();
			numPieces = 0;
			pieceArray = new char[numLines][MAX_SIZE];
			for(int j = 0; j < numLines; j++) {
				line = scan.nextLine();
				pieceArray[j] = line.toCharArray();
			}

			for (int j = 0; j < numLines; j++) {
				for (int k = 0; k < pieceArray[j].length; k++) {
					if (pieceArray[j][k] == '#') {
						numPieces++;
						floodfill(pieceArray, j, k, numLines);
					}
				}
			}
			System.out.println(numPieces);
		}
	}
}

// 0.1s per test case
