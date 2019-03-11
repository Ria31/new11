package com.example.rajni.application1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class registration_emp2 extends AppCompatActivity {
    EditText employeeid,contactno,emailid,password2,cpassword2;
    Button submit,cancle2;
    String emailpattern="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //String pwdpattern= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,20}$";
    String contactpattern = "^(?=.*[0-9]).{9,11}";

    String URL = "http://192.168.43.150/Android11/index1.php";

    JSONParser jsonParser=new JSONParser();

    private static final String tag = "dob";
    private EditText dob, fname, mname, lname;
    Spinner gender;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_emp2);
        employeeid=(EditText)findViewById(R.id.eid);
        emailid=(EditText)findViewById(R.id.email);
        contactno=(EditText)findViewById(R.id.phnno);
        password2=(EditText)findViewById(R.id.password);
        cpassword2=(EditText)findViewById(R.id.cpassword);
        submit=(Button) findViewById(R.id.submit);
        cancle2=(Button) findViewById(R.id.cancle2);
        dob = (EditText) findViewById(R.id.dob);
        fname = (EditText) findViewById(R.id.fname);
        mname = (EditText) findViewById(R.id.mname);
        lname = (EditText) findViewById(R.id.lname);
        gender = (Spinner) findViewById(R.id.gender);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(registration_emp2.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                Log.d(tag, "onDataset:mm/dd/yyyy:" + i1 + "/" + i2 + "/" + i);
                String date = i1 + "/" + i2 + "/" + i;
                dob.setText(date);
            }
        };
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttemptLogin1 attemptLogin= new AttemptLogin1();
                attemptLogin.execute(fname.getText().toString(),mname.getText().toString(),lname.getText().toString()
                        ,employeeid.getText().toString(),emailid.getText().toString(),contactno.getText().toString()
                        ,password2.getText().toString());

                if(employeeid.getText().toString().trim().length()==0){
                    employeeid.setError("Enter your ID");
                    employeeid.requestFocus();
                }
                else if(emailid.getText().toString().trim().length()==0  ){
                    emailid.setError("Enter your EmailID");
                    emailid.requestFocus();
                }
               else if(!emailid.getText().toString().trim().matches(emailpattern)){
                    emailid.setError("InValid EmailID");
                    emailid.requestFocus();
                }
               else if(contactno.getText().toString().trim().length()==0){
                    contactno.setError("Enter your Contact No.");
                    contactno.requestFocus();
                }
                else if(!contactno.getText().toString().trim().matches(contactpattern)){
                    contactno.setError("InValid Contact No.");
                    contactno.requestFocus();
                }
               else if(password2.getText().toString().trim().length()==0){
                    password2.setError("Enter your Password");
                    password2.requestFocus();
                }
//                else if(!password2.getText().toString().trim().matches(pwdpattern)){
//                    password2.setError("InValid Password");
//                    password2.requestFocus();
//                }
//               else if(cpassword2.getText().toString().trim().length()==0){
//                    cpassword2.setError("Confirm your Password");
//                    cpassword2.requestFocus();
//                }
//                else if(!cpassword2.getText().toString().trim().matches(String.valueOf(password2)) ){
//                    cpassword2.setError("Password not matching");
//                    cpassword2.requestFocus();
//                }

                else {
                    Intent intent=new Intent(registration_emp2.this,profile_emp.class);
                    startActivity(intent);
                }
            }
        });
        cancle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailid.setText("");
                employeeid.setText("");
                contactno.setText("");
                password2.setText("");
                cpassword2.setText("");
            }
        });

    }
    private class AttemptLogin1 extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String fname = args[0];
            String mname = args[1];
            String lname= args[2];
            String eid = args[3];
            String email = args[4];
            String contact= args[5];
            String pass = args[6];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Firstname", fname));
            params.add(new BasicNameValuePair("Middlename", mname));
            params.add(new BasicNameValuePair("Lastname", lname));
            params.add(new BasicNameValuePair("EmployeeID", eid));
            params.add(new BasicNameValuePair("EmailID", email));
            params.add(new BasicNameValuePair("ContactNo", contact));
            params.add(new BasicNameValuePair("Password", pass));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
