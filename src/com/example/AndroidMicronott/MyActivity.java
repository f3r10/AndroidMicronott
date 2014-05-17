package com.example.AndroidMicronott;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity{
    /**
     * Called when the activity is first created.
     */
    private EditText txtPassword;
    private EditText txtUsername;
    private Button btnEnvio;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();


    // url to create new product

    private static String url_create_product = "http://192.168.0.107/micronott/mvc/index/android";

    //JSON Node names

    private static final String TAG_SUCCESS = "success";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txtPassword = (EditText)findViewById(R.id.password);
        txtUsername = (EditText)findViewById(R.id.userName);
        btnEnvio = (Button)findViewById(R.id.btnEnvio);

        btnEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CheckCredentials().execute();
            }
        });
    }

    class CheckCredentials extends AsyncTask<String,String,String>
    {

        protected  void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyActivity.this);
            pDialog.setMessage("check login");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",username));
            params.add(new BasicNameValuePair("password",password));

            JSONObject json = jParser.makeHttpRequest(url_create_product,"POST",params);
            Log.w("Create Response", json.toString());

            try
            {
                int success = json.getInt(TAG_SUCCESS);
                if(success == 1)
                {
                    Log.w("Ingreso", "Ingreso existos");
                    Log.w("Ingreso", Integer.toString(success));
                }
                else
                {
                    Log.w("Ingreso", "se produjo un error");
                    Log.w("Ingreso", Integer.toString(success));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}
