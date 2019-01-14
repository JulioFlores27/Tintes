package com.julioflores.prueba2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity {
    Button etb1;
    EditText ett1, ett2;
    Spinner etsp1, etsp2;
    TextView etid;
    private AsyncHttpClient cliente;
    Calendar calendariocompleto;
    public String getIds() {
        String ids = getIntent().getStringExtra("ids");
        return ids;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etid = (TextView) findViewById(R.id.id1);
        ett1 = (EditText) findViewById(R.id.t1);
        ett2 = (EditText) findViewById(R.id.t2);
        etsp2 = (Spinner) findViewById(R.id.sp2);
        etsp1 = (Spinner) findViewById(R.id.sp1);
        etb1 = (Button) findViewById(R.id.b1);
        cliente = new AsyncHttpClient();
        final String[] tipos={"En Proceso","Por Verificar","Aprobado"};
        ArrayAdapter<String> listatipusu=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos);
        etsp1.setAdapter(listatipusu);
        etb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etsp1.getSelectedItem().toString().equals("Aprobado")){
                    ObtenerTintes();
                }else {
                    ObtenerTintes1();
                }
                Intent Menup = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(Menup);
                finish();
            }
        });
    }

    private void ObtenerTintes(){
        Date fechahora = calendariocompleto.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dias = dateFormat.format(fechahora);
        String url = "https://appsionmovil.000webhostapp.com/asignar.php?FechaAprobacion="+ dias.replaceAll(" ","%20") +
                "&FechaAsignacion=" + dias.replaceAll(" ","%20") +
                "&PersonaAsignada="+ etsp2.getSelectedItem().toString().replaceAll(" ","%20") +
                "&Etapa1=" + etsp1.getSelectedItem().toString().replaceAll(" ","%20") +
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
String asc = "En Proceso";
    private void ObtenerTintes1(){
        Date fechahora = calendariocompleto.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dias = dateFormat.format(fechahora);
        String url = "https://appsionmovil.000webhostapp.com/asignar.php?FechaAprobacion="+ asc.replaceAll(" ","%20") +
                "&FechaAsignacion=" + dias.replaceAll(" ","%20") +
                "&PersonaAsignada="+ etsp2.getSelectedItem().toString().replaceAll(" ","%20") +
                "&Etapa1=" + etsp1.getSelectedItem().toString().replaceAll(" ","%20") +
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