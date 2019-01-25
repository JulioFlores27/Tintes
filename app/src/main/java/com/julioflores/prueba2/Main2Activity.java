package com.julioflores.prueba2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity {
    SwipeRefreshLayout swipere;
    Button etb1;
    Spinner etsp1, etsp2;
    TextView etid;
    TextView eetapa1;
    private AsyncHttpClient cliente;

    public String getIds() {
        String ids = getIntent().getStringExtra("ids");
        return ids;
    }
    public class contar extends CountDownTimer {
        public contar(long milienfuturo, long countdowninterval){
            super(milienfuturo,countdowninterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
        }
        public void onFinish(){
            ObtenerTintes();
            Toast.makeText(Main2Activity.this, "Actualizado",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etid = findViewById(R.id.id1);
        etsp2 = findViewById(R.id.sp2);
        etsp1 = findViewById(R.id.sp1);
        etb1 = findViewById(R.id.b1);
        eetapa1 = findViewById(R.id.contadoretapa1);
        swipere = findViewById(R.id.swiperefrescar);
        String contadores = getIntent().getExtras().getString("intentos");
        eetapa1.setText(contadores);
        etsp2.setSelection(4);
        cliente = new AsyncHttpClient();

        ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lanet = conectividad.getActiveNetworkInfo();
        if(lanet != null && lanet.isConnected()){
        }else{
            etb1.setVisibility(View.INVISIBLE);
            Intent intentar = new Intent(Main2Activity.this, Main2Activity.class);
            startActivity(intentar);
            finish();
        }
        final String[] tipos={"En Proceso","Por Verificar","Aprobado"};
        ArrayAdapter<String> listatipusu=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos);
        etsp1.setAdapter(listatipusu);
        final String[] nombres = {"Arnold","Marco","Geovannie","Julio","Miguel","Neri","Armando N","Gildardo"};
        ArrayAdapter<String> listanom=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nombres);
        etsp2.setAdapter(listanom);
        final String selnom = getIntent().getExtras().getString("nombres");
        final String seletp = getIntent().getExtras().getString("etaps");
        etsp2.setSelection(obtenerlaposicion(etsp2, selnom));
        etsp1.setSelection(obtenerlaposicion(etsp1, seletp));
        etb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                if(lanet != null && lanet.isConnected()){
                    Intent Menup = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(Menup);
                    ObtenerTintes();
                    finish();
                }else{
                    etb1.setVisibility(View.INVISIBLE);
                    Intent Menup = new Intent(Main2Activity.this, Main2Activity.class);
                    startActivity(Menup);
                    Toast.makeText(Main2Activity.this, "No se pudo guardar los datos debido a la falla de Internet",Toast.LENGTH_LONG).show();
                }
            }
        });
        swipere.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo lanets = conectividad1.getActiveNetworkInfo();
                if(lanets != null && lanets.isConnected()){
                    etb1.setVisibility(View.VISIBLE);
                    Toast.makeText(Main2Activity.this, "Conexión Reestablecido",Toast.LENGTH_LONG).show();
                    swipere.setRefreshing(false);
                }else{
                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                    etb1.setVisibility(View.INVISIBLE);
                    swipere.setRefreshing(false);
                }
            }
        });
    }
    public static int obtenerlaposicion(Spinner espin, String nom){
        int position = 0;
        for(int i=0; i<espin.getCount();i++){
            if(espin.getItemAtPosition(i).toString().equalsIgnoreCase(nom)){
                position = i;
            }
        }
        return position;
    }
    String asc = "En Proceso";
    private void ObtenerTintes(){
        Date fechahora = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dias = dateFormat.format(fechahora);
        int contador2 = Integer.parseInt(eetapa1.getText().toString());
        if (etsp1.getSelectedItem().toString().equals("Aprobado")) {
            final String url = "https://appsionmovil.000webhostapp.com/asignar.php?FechaAprobacion="+ dias.replaceAll(" ","%20") +
                    "&FechaAsignacion=" + dias.replaceAll(" ","%20") +
                    "&PersonaAsignada="+ etsp2.getSelectedItem().toString().replaceAll(" ","%20") +
                    "&Etapa1=" + etsp1.getSelectedItem().toString().replaceAll(" ","%20") +
                    "&NoIntentos=" + contador2 +
                    "&ID=" + getIds();
            cliente.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode == 200){
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            });
        }
        if (etsp1.getSelectedItem().toString().equals("Por Verificar")) {
            contador2 = contador2 +1;
            final String url = "https://appsionmovil.000webhostapp.com/asignar.php?FechaAprobacion=" + asc.replaceAll(" ", "%20") +
                    "&FechaAsignacion=" + dias.replaceAll(" ", "%20") +
                    "&PersonaAsignada=" + etsp2.getSelectedItem().toString().replaceAll(" ", "%20") +
                    "&Etapa1=" + etsp1.getSelectedItem().toString().replaceAll(" ", "%20") +
                    "&NoIntentos=" + contador2 +
                    "&ID=" + getIds();
            cliente.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            });
        }else{
            String url = "https://appsionmovil.000webhostapp.com/asignar.php?FechaAprobacion="+ asc.replaceAll(" ","%20") +
                    "&FechaAsignacion=" + dias.replaceAll(" ","%20") +
                    "&PersonaAsignada="+ etsp2.getSelectedItem().toString().replaceAll(" ","%20") +
                    "&Etapa1=" + etsp1.getSelectedItem().toString().replaceAll(" ","%20") +
                    "&NoIntentos=" + contador2 +
                    "&ID=" + getIds();
            cliente.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode == 200){
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            });
        }
    }
}