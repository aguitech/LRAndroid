package com.aguitech.compartetuexperiencia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class BlogActivity extends AppCompatActivity {

    public String getNombreValue = "";
    public String getIDValue = "";

    private TextView blogTitulo1;
    private TextView blogTitulo2;
    //private TextView blogNombreImagen1;
    private TextView reseniaBlog1;
    private TextView reseniaBlog2;

    private ProgressDialog pDialog;
    private HashMap<String,String> data;
    private String url = "http://emocionganar.com/admin/panel/webservice_blog_android.php";

    ImageView SetImageViewHolder;
    ImageView SetImageViewHolder2;
    Bitmap Imagebitmap;
    Bitmap Imagebitmap2;
    String ImageUrl = "http://www.android-examples.com/wp-content/uploads/2015/11/logo-2015-new-2016.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            //String getNombreValue =(String) b.get("Nombre");
            getNombreValue =(String) b.get("Nombre");
            //Textv.setText(j);
            Toast.makeText(getApplicationContext(), getNombreValue, Toast.LENGTH_SHORT).show();
            //String getIDValue =(String) b.get("ID");
            getIDValue =(String) b.get("ID");

            Toast.makeText(getApplicationContext(), getIDValue, Toast.LENGTH_SHORT).show();
        }

        //blogNombreImagen1 = (TextView) findViewById(R.id.blogNombreImagen1);
        blogTitulo1 = (TextView) findViewById(R.id.blogTitulo1);
        blogTitulo2 = (TextView) findViewById(R.id.blogTitulo2);
        reseniaBlog1 = (TextView) findViewById(R.id.reseniaBlog1);
        reseniaBlog2 = (TextView) findViewById(R.id.reseniaBlog2);
        SetImageViewHolder = (ImageView) findViewById(R.id.blogImagen1);
        SetImageViewHolder2 = (ImageView) findViewById(R.id.blogImagen2);
        //SetImageViewHolder2 = (ImageView) findViewById(R.id.blogImagen2);


        new connectPhp().execute();

        //new ImageLoaderClass().execute(ImageUrl);

        /*
        connect.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                new connectPhp().execute();

            }
        });
        */

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent i = new Intent();
            i.putExtra("Nombre", getNombreValue);
            i.putExtra("ID", getIDValue);
            i.putExtra("Dios", "Mi nombre es Hector");
            //i.setClass(MainActivity.this, PantallaActivity.class);
            //i.setClass(MainActivity.this, RegistroActivity.class);
            i.setClass(BlogActivity.this, MenuPrincipalActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_eventos) {
            Intent i = new Intent();
            i.putExtra("Nombre", getNombreValue);
            i.putExtra("ID", getIDValue);
            i.putExtra("Dios", "Mi nombre es Hector");
            //i.setClass(MainActivity.this, PantallaActivity.class);
            //i.setClass(MainActivity.this, RegistroActivity.class);
            i.setClass(BlogActivity.this, EventosActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_trivias) {
            Intent i = new Intent();
            i.putExtra("Nombre", getNombreValue);
            i.putExtra("ID", getIDValue);
            i.putExtra("Dios", "Mi nombre es Hector");
            i.setClass(BlogActivity.this, TriviasActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_terminos_condiciones) {
            Intent i = new Intent();
            i.putExtra("Nombre", getNombreValue);
            i.putExtra("ID", getIDValue);
            i.putExtra("Dios", "Mi nombre es Hector");
            i.setClass(BlogActivity.this, TerminosCondicionesActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_aviso_privacidad) {
            Intent i = new Intent();
            i.putExtra("Nombre", getNombreValue);
            i.putExtra("ID", getIDValue);
            i.putExtra("Dios", "Mi nombre es Hector");
            i.setClass(BlogActivity.this, AvisoPrivacidadActivity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public class connectPhp extends AsyncTask<String, String, String> {

        //String getEdittextValue = name.getText().toString();

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(BlogActivity.this);
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
            data.put("name", "0");
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
                    JSONArray jsonArray = json.getJSONArray("blog");//get parent node

                    JSONObject child = jsonArray.getJSONObject(0);//get first child value
                    final String blogTitulo1value = child.optString("titulo");
                    final String blogResenia1value = child.optString("blog");
                    final String blogImagen1value = child.optString("imagen");


                    JSONObject child1 = jsonArray.getJSONObject(1);//get first child value
                    final String blogTitulo2value = child1.optString("titulo");
                    final String blogResenia2value = child1.optString("blog");
                    final String blogImagen2value = child1.optString("imagen");




                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //result.setText(getValue.toString());
                            Toast.makeText(getApplicationContext(), "Funciona XD", Toast.LENGTH_SHORT).show();
                            //result.setText(getValue.toString());
                            blogTitulo1.setText(blogTitulo1value.toString());
                            reseniaBlog1.setText(blogResenia1value.toString());
                            blogTitulo2.setText(blogTitulo2value.toString());
                            reseniaBlog2.setText(blogResenia2value.toString());
                            //blogNombreImagen1.setText(blogImagen2value.toString());

                            //String ImageUrl2 = "http://enobra.com.mx/images/Imagen2.jpg";
                            String ImageUrl1 = blogImagen1value.toString();
                            //new ImageLoaderClass().execute(ImageUrl);

                            /*
                            new ImageLoaderClass().execute(ImageUrl1);
                            */

                            //new ImageLoaderClass().execute(ImageUrl);


                            //String ImageUrl2 = blogImagen2value.toString();
                            //new ImageLoaderClass2().execute(ImageUrl2);

                            //estudiante1.setNombre("María");

                            //SetImageViewHolder2 = (ImageView) findViewById(R.id.blogImagen1);

                            //new ImageLoaderClass3().execute(ImageUrl3);

                            ImageLoaderClass nuevaImagen2 = new ImageLoaderClass();
                            nuevaImagen2.SetImageViewHolder3(SetImageViewHolder);
                            nuevaImagen2.SetImagebitmap3(Imagebitmap);
                            nuevaImagen2.execute(ImageUrl1);



                            SetImageViewHolder2 = (ImageView) findViewById(R.id.blogImagen2);
                            String ImageUrl3 = blogImagen2value.toString();
                            //new ImageLoaderClass3().execute(ImageUrl3);

                            ImageLoaderClass nuevaImagen = new ImageLoaderClass();
                            nuevaImagen.SetImageViewHolder3(SetImageViewHolder2);
                            nuevaImagen.SetImagebitmap3(Imagebitmap2);
                            nuevaImagen.execute(ImageUrl3);





                            //String ImageUrl2 = blogImagen2value.toString();
                            /*
                            Intent i = new Intent();
                            i.putExtra("Nombre", "Mi nombre es Hector");
                            //i.setClass(MainActivity.this, PantallaActivity.class);
                            //i.setClass(MainActivity.this, RegistroActivity.class);
                            i.setClass(BlogActivity.this, RegistroActivity.class);
                            startActivity(i);
                            */
                        }
                    });
                }
            }catch(Exception e){

            }



            return null;
        }

        @Override
        protected void onPostExecute(String a){
            super.onPostExecute(a);
            pDialog.cancel();
        }
    }

    public class ImageLoaderClass extends AsyncTask<String, String, Bitmap> {

        private ImageView SetImageViewHolder;
        private Bitmap Imagebitmap;

        public void SetImageViewHolder3(ImageView setImageViewHolder) {
            this.SetImageViewHolder = setImageViewHolder;
        }
        public void SetImagebitmap3(Bitmap imagebitmap) {
            this.Imagebitmap = imagebitmap;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                Imagebitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return Imagebitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                SetImageViewHolder.setImageBitmap(image);

            }
        }



    }

}
