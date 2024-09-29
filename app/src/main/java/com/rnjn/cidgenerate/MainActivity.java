package com.rnjn.cidgenerate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MatrixMatcher";
    private Button passwordGenerator;
    private EditText cidNo, serialNo;
    private TextView passView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidNo = findViewById(R.id.cidNo);
        serialNo =  findViewById(R.id.seNo);
        passView = findViewById(R.id.genPass);

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

        passwordGenerator = findViewById(R.id.generatePass);
        passwordGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cidNoInput = formatTo4Digits(cidNo.getText().toString());
                String[] cidNo = splitDigits(cidNoInput);
                String serialNoInput = formatTo5Digits(serialNo.getText().toString());
                String[] serialNo = splitDigits(serialNoInput);

                // Print the formatted string and digits
                Log.d("Formatted Input", cidNoInput); // Output: "7   "
                Log.d("Formatted Digits", Arrays.toString(cidNo)); // Output: "[7,  ,  ,  ]"

                Log.d("Formatted Input", serialNoInput); // Output: "7   "
                Log.d("Formatted Digits", Arrays.toString(serialNo)); // Output: "[7,  ,  ,  ]"

                int value1 = getValueFromRow(matrixCenterId, 0, Integer.parseInt(cidNo[0])); // Row 0 represents the 4th row
                int value2 = getValueFromRow(matrixCenterId, 1, Integer.parseInt(cidNo[1])); // Row 1 represents the 3rd row
                int value3 = getValueFromRow(matrixCenterId, 2, Integer.parseInt(cidNo[2])); // Row 2 represents the 2nd row
                int value4 = getValueFromRow(matrixCenterId, 3, Integer.parseInt(cidNo[3])); // Row 3 represents the 1st row

                int seValue1 = getValueFromRow(matrixSerialNo, 0, Integer.parseInt(serialNo[0])); // Row 0 represents the 4th row
                int seValue2 = getValueFromRow(matrixSerialNo, 1, Integer.parseInt(serialNo[1])); // Row 1 represents the 3rd row
                int seValue3 = getValueFromRow(matrixSerialNo, 2, Integer.parseInt(serialNo[2])); // Row 2 represents the 2nd row
                int seValue4 = getValueFromRow(matrixSerialNo, 3, Integer.parseInt(serialNo[3])); // Row 3 represents the 1st row
                int seValue5 = getValueFromRow(matrixSerialNo, 4, Integer.parseInt(serialNo[4])); // Row 3 represents the 1st row
                Log.d("Formatted cid No", value1+" "+value2+" "+value3+" "+value4 ); // Output: "[7,  ,  ,  ]"
                Log.d("Formatted serial No", seValue1+" "+seValue2+" "+seValue3+" "+seValue4+" "+seValue5 ); // Output: "[7,  ,  ,  ]"
                int totalCidNo = value1+value2+value3+value4;
                int totalSerialNo = seValue1+seValue2+seValue3+seValue4+seValue5;
                passView.setText(""+(totalCidNo+totalSerialNo));
            }

        });




    }

    /**
     * Method to get a value from a specific row and column index.
     *
     * @param matrix The 2D matrix array.
     * @param rowIndex The index of the row.
     * @param columnIndex The index of the column.
     * @return The value at the specified row and column.
     */
    // Method to format a string to 4 digits, filling with spaces if needed
    private String formatTo4Digits(String input) {
        return String.format("%-4s", input);  // Left-align and pad with spaces
    }
    private String formatTo5Digits(String input) {
        return String.format("%-5s", input);  // Left-align and pad with spaces
    }
    // Method to split the formatted string into individual digits
    private String[] splitDigits(String formattedInput) {
        return formattedInput.split("");  // Split into individual characters
    }
    private int getValueFromRow(int[][] matrix, int rowIndex, int columnIndex) {
        if (rowIndex < matrix.length && columnIndex < matrix[rowIndex].length) {
            return matrix[rowIndex][columnIndex];
        } else {
            Log.e(TAG, "Invalid row or column index.");
            return -1; // Return a sentinel value to indicate an error
        }
    }


    }
