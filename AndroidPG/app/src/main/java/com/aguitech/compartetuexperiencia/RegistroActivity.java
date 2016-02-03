package com.aguitech.compartetuexperiencia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class RegistroActivity extends AppCompatActivity {
    private EditText valueNombre;
    private EditText valueCelular;
    private EditText valueTelefonoCasa;
    private EditText valueEmail;
    private EditText valueEmailConfirm;
    private EditText valueEdad;
    private EditText valueCodigoPostal;
    private EditText valueUsername;
    private EditText valuePassword;
    private EditText valuePasswordConfirm;
    private Button btnLogin;
    private Button btnRegistro;

    private ProgressDialog pDialog;
    private HashMap<String,String> data;
    private String url = "http://emocionganar.com/admin/panel/registro_android.php";
    /*
    private TextView result;
    private Button connect;
    private EditText name;
    private ProgressDialog pDialog;
    private HashMap<String,String> data;
    private String url = "http://emocionganar.com/json.php";
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        valueNombre = (EditText) findViewById(R.id.fieldNombreCompleto);
        valueCelular = (EditText) findViewById(R.id.fieldCelular);
        valueTelefonoCasa = (EditText) findViewById(R.id.fieldTelefonoCasa);
        valueEmail = (EditText) findViewById(R.id.fieldEmailLogin);
        valueEmailConfirm = (EditText) findViewById(R.id.fieldEmailConfirm);
        valueEdad = (EditText) findViewById(R.id.fieldEdad);
        valueCodigoPostal = (EditText) findViewById(R.id.fieldCodigoPostal);
        valueUsername = (EditText) findViewById(R.id.fieldUsername);
        valuePassword = (EditText) findViewById(R.id.fieldPasswordLogin);
        valuePasswordConfirm = (EditText) findViewById(R.id.fieldPasswordConfirm);
        btnLogin = (Button) findViewById(R.id.btnLoginLogin);
        btnRegistro = (Button) findViewById(R.id.buttonRegistro);


        //btnLogin.setOnClickListener();

        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent();
                i.putExtra("Nombre", "Mi nombre es Hector");
                //i.setClass(MainActivity.this, PantallaActivity.class);
                //i.setClass(MainActivity.this, RegistroActivity.class);
                //i.setClass(RegistroActivity.this, BlogActivity.class);
                i.setClass(RegistroActivity.this, LoginActivity.class);
                startActivity(i);
            }

        });


        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                new connectPhp().execute();

            }
        });


        /*
        result = (TextView) findViewById(R.id.result);
        connect = (Button) findViewById(R.id.connect);
        name = (EditText) findViewById(R.id.name);
        * */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public class connectPhp extends AsyncTask<String, String, String> {

        String getNombreValue = valueNombre.getText().toString();
        String getCelularValue = valueCelular.getText().toString();
        String getTelefonoCasaValue = valueTelefonoCasa.getText().toString();
        String getEmailValue = valueEmail.getText().toString();
        String getEmailConfirmValue = valueEmailConfirm.getText().toString();
        String getEdadValue = valueEdad.getText().toString();
        String getCodigoPostalValue = valueCodigoPostal.getText().toString();
        String getUsernameValue = valueUsername.getText().toString();
        String getPasswordValue = valuePassword.getText().toString();
        String getPasswordConfirmValue = valuePasswordConfirm.getText().toString();

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistroActivity.this);
            pDialog.setIndeterminate(false);
            pDialog.setMessage("Connecting...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            //List <NameValuePair> args = new ArrayList<NameValuePair>();
            //args.add(new BasicNameValuePair("name", getEdittextValue));//this is key and value to post data
            data = new HashMap<String, String>();
            //data.put("name", getEdittextValue);
            //data.put("nombre", getEdittextValue);
            data.put("nombre", getNombreValue);
            data.put("celular", getCelularValue);
            data.put("telefono", getTelefonoCasaValue);
            data.put("email", getEmailValue);
            data.put("email_confirm", getEmailConfirmValue);
            data.put("edad", getEdadValue);
            data.put("codigo_postal", getCodigoPostalValue);
            data.put("username", getUsernameValue);
            data.put("password", getPasswordValue);
            data.put("password_confirm", getPasswordConfirmValue);

            try{

                //JSONObject json = jsonParser.makeHttpRequest(url, "POST", args);//to pass url, method, and args
                //now connect using JSONParsr class
                JSONObject json = HttpUrlConnectionParser.makehttpUrlConnection(url,data);
                int succ = json.getInt("success");//get response from server
                if(succ == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(succ == 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Registro duplicado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(succ == 1){
                    JSONArray jsonArray = json.getJSONArray("result");//get parent node

                    JSONObject child = jsonArray.getJSONObject(0);//get first child value
                    final String getValue = child.optString("reply");
                    JSONObject child1 = jsonArray.getJSONObject(1);//get first child value
                    final String getIdValue = child1.optString("id");
                    JSONObject child2 = jsonArray.getJSONObject(2);//get first child value
                    final String getNombreValue = child2.optString("nombre");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //result.setText(getValue.toString());
                            Toast.makeText(getApplicationContext(), "Funciona XD", Toast.LENGTH_SHORT).show();
                            //result.setText(getValue.toString());

                            Intent i = new Intent();
                            i.putExtra("Nombre", getNombreValue);
                            i.putExtra("ID", getIdValue);
                            i.putExtra("Dios", "Mi nombre es Hector");
                            //i.setClass(MainActivity.this, PantallaActivity.class);
                            //i.setClass(MainActivity.this, RegistroActivity.class);
                            i.setClass(RegistroActivity.this, MenuPrincipalActivity.class);
                            startActivity(i);
                            /*
                            Intent i = new Intent();
                            i.putExtra("Nombre", "Mi nombre es Hector");
                            //i.setClass(MainActivity.this, PantallaActivity.class);
                            //i.setClass(MainActivity.this, RegistroActivity.class);
                            i.setClass(RegistroActivity.this, RegistroActivity.class);
                            startActivity(i);
                            */
                        }
                    });
                }
                /*
                int succ = json.getInt("success");//get response from server
                if(succ == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    JSONArray jsonArray = json.getJSONArray("result");//get parent node

                    JSONObject child = jsonArray.getJSONObject(0);//get first child value
                    final String getValue = child.optString("reply");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //result.setText(getValue.toString());
                            Toast.makeText(getApplicationContext(), "Funciona XD", Toast.LENGTH_SHORT).show();
                            result.setText(getValue.toString());

                            Intent i = new Intent();
                            i.putExtra("Nombre", "Mi nombre es Hector");
                            //i.setClass(MainActivity.this, PantallaActivity.class);
                            //i.setClass(MainActivity.this, RegistroActivity.class);
                            i.setClass(MainActivity.this, RegistroActivity.class);
                            startActivity(i);
                        }
                    });
                }
                */
            }catch(Exception e){

            }

            /**
             * private EditText valueNombre;
             private EditText valueCelular;
             private EditText valueTelefonoCasa;
             private EditText valueEmail;
             private EditText valueEmailConfirm;
             private EditText valueEdad;
             private EditText valueCodigoPostal;
             private EditText valueUsername;
             private EditText valuePassword;
             private EditText valuePasswordConfirm;
             *
            try{

                //JSONObject json = jsonParser.makeHttpRequest(url, "POST", args);//to pass url, method, and args
                //now connect using JSONParsr class
                JSONObject json = HttpUrlConnectionParser.makehttpUrlConnection(url,data);
                int succ = json.getInt("success");//get response from server
                if(succ == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    JSONArray jsonArray = json.getJSONArray("result");//get parent node

                    JSONObject child = jsonArray.getJSONObject(0);//get first child value
                    final String getValue = child.optString("reply");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //result.setText(getValue.toString());
                            Toast.makeText(getApplicationContext(), "Funciona XD", Toast.LENGTH_SHORT).show();
                            result.setText(getValue.toString());

                            Intent i = new Intent();
                            i.putExtra("Nombre", "Mi nombre es Hector");
                            //i.setClass(MainActivity.this, PantallaActivity.class);
                            //i.setClass(MainActivity.this, RegistroActivity.class);
                            i.setClass(MainActivity.this, RegistroActivity.class);
                            startActivity(i);
                        }
                    });
                }
            }catch(Exception e){

            }
            */


            return null;
        }

        @Override
        protected void onPostExecute(String a){
            super.onPostExecute(a);
            pDialog.cancel();
        }
    }

}
