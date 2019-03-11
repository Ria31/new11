package com.example.rajni.application1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{

    private EditText eid,ename,epass,empmail;
    private Button b1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        eid = (EditText)findViewById(R.id.eid);
        ename = (EditText)findViewById(R.id.ename);
        epass = (EditText)findViewById(R.id.epass);
        empmail = (EditText)findViewById(R.id.empmail);
        b1=(Button)findViewById(R.id.b1);

        progressDialog=new ProgressDialog(this);

        b1.setOnClickListener(this);
    }

    private void registerUser()
    {
        final String eid1=eid.getText().toString().trim();
        final String ename1=ename.getText().toString().trim();
        final String epass1=epass.getText().toString().trim();
        final String empmail1=empmail.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("EmployeeId",eid1);
                params.put("EmployeeName",ename1);
                params.put("Password",epass1);
                params.put("EmailId",empmail1);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v)
    {
        if(v==b1)
            registerUser();
    }
}
