package com.rnjn.cidgenerate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends Activity {
    private String get_report_url = "https://timxn.com/ecom/licencepermission/get_track_link.php";
    private String send_data_url = "https://timxn.com/ecom/licencepermission/deviceidadd.php";
    private static final String TAG = "MatrixMatcher";
    private Button passwordGenerator;
    private EditText cidNo, serialNo;
    private TextView passView;
    private DatabaseManager databaseManager;
    String androidID;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidNo = findViewById(R.id.cidNo);
        serialNo =  findViewById(R.id.seNo);
        passView = findViewById(R.id.genPass);

        try{
            // Get Android ID (which is unique for each device)
            androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            // Log or use the Android ID
            Log.d(TAG, "Device Android ID: " + androidID);
        }catch (Exception ex){

        }

        databaseManager = new DatabaseManager(this, androidID);

        try {
            sendDeviceId(androidID);
        }catch (Exception ex){}

        Cursor cursor = databaseManager.getData();

        if (cursor != null && cursor.moveToFirst()) {  // Check if the cursor is not null and has data
            do {
                // Use getColumnIndexOrThrow for better error reporting
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String cid_no = cursor.getString(cursor.getColumnIndexOrThrow("cid_no"));
                String cid_permission_code = cursor.getString(cursor.getColumnIndexOrThrow("cid_permission_code"));
                cidNo.setText(cid_no);


            } while (cursor.moveToNext());
        }

        // Close cursor to avoid memory leaks
        if (cursor != null) {
            cursor.close();
        }

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

                getIotData(androidID);
                if (cidNo.getText() == null || cidNo.getText().toString().trim().isEmpty()) {
                    // Return immediately if the input is null, empty, or only contains whitespaces
                    return;
                }

                if (serialNo.getText() == null || serialNo.getText().toString().trim().isEmpty()) {
                    // Return immediately if the input is null, empty, or only contains whitespaces
                    return;
                }


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

                Cursor cursor = databaseManager.getData();

                if (cursor != null && cursor.moveToFirst()) {  // Check if the cursor is not null and has data
                    do {
                        // Use getColumnIndexOrThrow for better error reporting
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        String cid_no = cursor.getString(cursor.getColumnIndexOrThrow("cid_no"));
                        String totalCid = cursor.getString(cursor.getColumnIndexOrThrow("total_cid"));
                        String cid_permission_code = cursor.getString(cursor.getColumnIndexOrThrow("cid_permission_code"));
                        String android_Id = cursor.getString(cursor.getColumnIndexOrThrow("androidID"));

                        if (cid_no == null || cid_no.isEmpty() || cid_no.trim().isEmpty() || totalCid.equals(Integer.toString(totalCidNo))) {
                            // The string is either null, empty, only contains whitespaces, or equals "someValue"
                            int rowsAffected = databaseManager.updateData(1, cidNoInput,Integer.toString(totalCidNo));
                            //Toast.makeText(getApplicationContext(), "Rows Affected: " + rowsAffected, Toast.LENGTH_SHORT).show();
                            Log.d("my_data",""+cid_permission_code+" "+android_Id+" "+androidID);
                            if(cid_permission_code.equals("1827") && Objects.equals(androidID, android_Id)) {
                                int totalSerialNo = seValue1 + seValue2 + seValue3 + seValue4 + seValue5;
                                int password = (totalCidNo + totalSerialNo);
                                passView.setText(getString(R.string.score_text, password).substring(1));
                                passView.setTextColor(Color.GREEN);
                            }else{

                                passView.setText("");
                                //passView.setTextColor(Color.RED);
                            }
                        } else {
                            // The string is valid
                            // Update an existing record
                            Toast.makeText(getApplicationContext(), "Not valid cid no", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getApplicationContext(), "ID: " + id + ", cid_no: " + cid_no + ", cid_permission_code: " + cid_permission_code, Toast.LENGTH_SHORT).show();
                    } while (cursor.moveToNext());
                }

                // Close cursor to avoid memory leaks
                if (cursor != null) {
                    cursor.close();
                }



            }

        });




    }


    // Method to format a string to 4 digits, filling with spaces if needed
    private String formatTo4Digits(String input) {
        return String.format(Locale.getDefault(),"%04d", Integer.parseInt(input));  // Left-align and pad with spaces
    }
    private String formatTo5Digits(String input) {
        return String.format(Locale.getDefault(),"%05d", Integer.parseInt(input));  // Left-align and pad with spaces
    }
    /*private String formatTo4Digits(String input) {
        return String.format("%-4s", input);  // Left-align and pad with spaces
    }
    private String formatTo5Digits(String input) {
        return String.format("%-5s", input);  // Left-align and pad with spaces
    }*/
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
    public void getIotData(String androidID) {


        Thread sendThread = new Thread() {

            private TennisAdapter tennisAdapter;

            @SuppressLint("ResourceType")
            public void run() {

                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();
                //Populate the request parameters
                param.put("androidID", androidID);
                //param.put("to_date", toDate);
                String result=rh.sendPostRequest(get_report_url, param);
                //pDialog.dismiss();
                runOnUiThread(() -> {

                });



                try {

                    // Parse the JSON string
                    JSONObject jsonObject = new JSONObject(result);

                    // Access values
                   // String status = jsonObject.getString("status");




                    if (jsonObject.getString("status").equals("true")) {

                        String cidgenerate = jsonObject.getString("cidgenerate");
                        String androidID = jsonObject.getString("androidID");


                        int rowsAffected = databaseManager.updateCIDPermissionData(1,cidgenerate, androidID);

                        Log.d("cidgenerate","cidgenerate "+cidgenerate+" androidID "+androidID+" "+rowsAffected);
                        runOnUiThread(() -> {
                            // scrollTextViewLayout.addView(netTotlaTitleTextView);
                            //  scrolLayout.addView(netVolTextView);
                        });


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        };
        Log.d("ajflkasj","idhf");
        sendThread.start();

    }

    public void sendDeviceId(String androidID) {


        Thread sendThread = new Thread() {

            private TennisAdapter tennisAdapter;

            @SuppressLint("ResourceType")
            public void run() {

                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();
                //Populate the request parameters
                param.put("androidID", androidID);
                //param.put("to_date", toDate);
                String result=rh.sendPostRequest(send_data_url, param);
                //pDialog.dismiss();

            }
        };
        Log.d("ajflkasj","idhf");
        sendThread.start();

    }

    }
