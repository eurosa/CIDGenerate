package com.rnjn.cidgenerate;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MatrixMatcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Matrix data
        int[] digitsCID = {1, 2, 3, 4};
        int[][] matrixCenterId = {

                /* 4th*/ {80, 55, 1234, 1111, 2200, 2499, 3333, 3999, 4444, 6565},
                /* 3rd*/  {29, 333, 123, 432, 20, 80, 55, 1234, 1111, 2200},
                /* 2nd*/   {1234, 1111, 2200, 2499, 3333, 3999, 4444, 6565, 7799, 8811},
                /* 1st*/   {2222, 1111, 4444, 3333, 6565, 2299, 3399, 7799, 8811, 4545},

        };


        int[][] matrixSerialNo = {
                {7799, 8811, 4545, 12, 31, 21, 29, 333, 123, 432},
                {432, 20, 80, 55, 1234, 1111, 2200, 2499, 3333, 3999},
                {55, 1234, 1111, 2200, 2499, 3333, 3999, 4444, 6565, 7799},
                {3333, 6565, 2299, 3399, 7799, 8811, 4545, 12, 31, 21},
                {12, 31, 21, 29, 333, 123, 432, 20, 80, 55},

        };

        // Column index you want to access (0-based index)
        int columnIndex = 1; // Example: get the value from column index 2
        int columnIndex2 = 2; // Example: get the value from column index 2
        int columnIndex3 = 3; // Example: get the value from column index 2
        int columnIndex4 = 4; // Example: get the value from column index 2

        // Get value from the 4th row
        int value1 = getValueFromRow(matrixCenterId, 0, columnIndex); // Row 0 represents the 4th row
        int value2 = getValueFromRow(matrixCenterId, 1, columnIndex2); // Row 1 represents the 3rd row
        int value3 = getValueFromRow(matrixCenterId, 2, columnIndex3); // Row 2 represents the 2nd row
        int value4 = getValueFromRow(matrixCenterId, 3, columnIndex4); // Row 3 represents the 1st row
        Log.d(TAG, "Value from 4th row at column index " + columnIndex + ": " + value1);
        Log.d(TAG, "Value from 4th row at column index " + columnIndex + ": " + value2);
        Log.d(TAG, "Value from 4th row at column index " + columnIndex + ": " + value3);
        Log.d(TAG, "Value from 4th row at column index " + columnIndex + ": " + value4);
    }

    /**
     * Method to get a value from a specific row and column index.
     *
     * @param matrix The 2D matrix array.
     * @param rowIndex The index of the row.
     * @param columnIndex The index of the column.
     * @return The value at the specified row and column.
     */
    private int getValueFromRow(int[][] matrix, int rowIndex, int columnIndex) {
        if (rowIndex < matrix.length && columnIndex < matrix[rowIndex].length) {
            return matrix[rowIndex][columnIndex];
        } else {
            Log.e(TAG, "Invalid row or column index.");
            return -1; // Return a sentinel value to indicate an error
        }
    }

 /*
        int[] digitsCID = {1, 2, 3, 4};
        int[] digitsCenterId = {5, 6, 7, 8, 0};

        // Matching digits with matrix data and retrieving values
        // Loop through the matrix and match with digits
        for (int i = 0; i < matrixCenterId.length; i++) {
            Log.d(TAG, "Row " + (4 - i) + ":"); // Adjusted for row labels (4th, 3rd, 2nd, 1st)
            for (int j = 0; j < digitsCID.length; j++) {
                int index = digitsCID[j];
                // Check if the index is within the bounds of the row
                if (index < matrixCenterId[i].length) {
                    int value = matrixCenterId[i][index];
                    Log.d(TAG, "  Column " + (index + 1) + ": Value = " + value);
                } else {
                    Log.d(TAG, "  Column " + (index + 1) + ": Index " + index + " is out of bounds for this row.");
                }
            }
        }

        // Loop through the matrix and match with digits
        for (int i = 0; i < matrixSerialNo.length; i++) {
            Log.d(TAG, "Row " + (5 - i) + ":"); // Adjusted for row labels (5th, 4th, 3rd, 2nd, 1st)
            for (int j = 0; j < digitsCenterId.length; j++) {
                int index = digitsCenterId[j];
                // Check if the index is within the bounds of the row
                if (index < matrixSerialNo[i].length) {
                    int value = matrixSerialNo[i][index];
                    Log.d(TAG, "  Column " + j + ": Value = " + value);
                } else {
                    Log.d(TAG, "  Column " + j + ": Index " + index + " is out of bounds for this row.");
                }
            }
        }
*/
    }
