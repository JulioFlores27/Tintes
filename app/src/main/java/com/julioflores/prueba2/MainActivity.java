package com.julioflores.prueba2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.loopj.android.http.*;
import org.json.JSONArray;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity{
    SwipeRefreshLayout swipeRefreshLayout;
    ListAdapter adapter;
    SwipeMenuListView lvlista1;
    AsyncHttpClient cliente;
    int acceso;
    Calendar calendariocompleto;
    android.os.Handler customHandler;

    public class contar extends CountDownTimer{
        public contar(long milienfuturo, long countdowninterval){
            super(milienfuturo,countdowninterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            ObtenerTintes();
        }
        public void onFinish(){
            ObtenerTintes();
            //Toast.makeText(MainActivity.this, "Actualizado",Toast.LENGTH_SHORT).show();
        }
    }
    private Runnable actualizartimer = new Runnable() {
        @Override
        public void run() {
            contar tiempo = new contar(45000, 45000);
            tiempo.start();
            customHandler.postDelayed(this, 45000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customHandler = new android.os.Handler();
        customHandler.postDelayed(actualizartimer, 15000);
        lvlista1 = findViewById(R.id.lista1);
        cliente = new AsyncHttpClient();
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lanet = conectividad.getActiveNetworkInfo();
        if(lanet != null && lanet.isConnected()){
            ObtenerTintes();
        }else{
            lvlista1.setVisibility(View.INVISIBLE);
            Intent intentar = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intentar);
        }
        //Supervisor 1, entonador 2
        acceso=0;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                if(lanet != null && lanet.isConnected()){
                    ObtenerTintes();
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    Intent intentar = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intentar);
                    Toast.makeText(MainActivity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                    lvlista1.setVisibility(View.INVISIBLE);
                }
            }
        });
        if (acceso == 1) {
            ObtenerTintes();
        }else  {
            ObtenerTintes2();
        }

    }
    private void ObtenerTintes(){
        String url = "https://appsionmovil.000webhostapp.com/pedidostintes.php?";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listartintes(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    private void ObtenerTintes2(){
        String url = "https://appsionmovil.000webhostapp.com/pedidostintes2.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listartintes(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    public void listartintes(String respuesta){
        final ArrayList<Tintes> lista = new ArrayList<Tintes>();

        try{
            final JSONArray jsonarreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonarreglo.length(); i++){
                Tintes t = new Tintes();
                t.setId(jsonarreglo.getJSONObject(i).getInt("ID"));
                t.setNopedido(jsonarreglo.getJSONObject(i).getInt("NoPedido"));
                t.setCantidad(jsonarreglo.getJSONObject(i).getInt("Cantidad"));
                t.setProducto(jsonarreglo.getJSONObject(i).getString("Producto"));
                t.setObservaciones(jsonarreglo.getJSONObject(i).getString("Observaciones"));
                t.setTipoenvase(jsonarreglo.getJSONObject(i).getString("TipoEnvase"));
                t.setFechacaptura(jsonarreglo.getJSONObject(i).getString("FechaCaptura"));
                t.setFechaasigacion(jsonarreglo.getJSONObject(i).getString("FechaAsignacion"));
                t.setFechaaprobacion(jsonarreglo.getJSONObject(i).getString("FechaAprobacion"));
                t.setPersonaasignada(jsonarreglo.getJSONObject(i).getString("PersonaAsignada"));
                t.setEtapa1(jsonarreglo.getJSONObject(i).getString("Etapa1"));
                t.setNointentos(jsonarreglo.getJSONObject(i).getInt("NoIntentos"));
                t.setLote(jsonarreglo.getJSONObject(i).getInt("Lote"));
                lista.add(t);
            }
            adapter = new ArrayAdapter<Tintes>(this, android.R.layout.simple_list_item_1, lista);
            lvlista1.setAdapter(adapter);
            final Main3Activity entinteadapter = new Main3Activity(this, lista);
            //ArrayAdapter<Tintes> a = new ArrayAdapter<Tintes>(this,android.R.layout.simple_list_item_1, lista);
            lvlista1.setAdapter(entinteadapter);
            lvlista1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    final Tintes estePedido = (Tintes) parent.getItemAtPosition(position);
                    String CantidadDeEstePedido = String.valueOf(estePedido.getId());
                    String CantidadIntentos = String.valueOf(estePedido.getNointentos());
                    final String Nombreasig = estePedido.getPersonaasignada();
                    final String etapas = estePedido.getEtapa1();
                    intent.putExtra("ids", CantidadDeEstePedido);
                    intent.putExtra("intentos", CantidadIntentos);
                    intent.putExtra("nombres", Nombreasig);
                    intent.putExtra("etaps", etapas);
                    Toast.makeText(MainActivity.this, CantidadDeEstePedido,Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            });
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.GRAY));
                    // set item width
                    openItem.setWidth(170);
                    // set item title
                    openItem.setTitle("Exportar a Envases");
                    // set item title fontsize
                    openItem.setTitleSize(18);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);
                }
            };
            lvlista1.setMenuCreator(creator);
            lvlista1.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            final Tintes estePedido = (Tintes) lvlista1.getItemAtPosition(position);
                            String fechacap = estePedido.getFechacaptura();
                            String fechaapr = estePedido.getFechaaprobacion();
                            String fechaasi = estePedido.getFechaasigacion();
                            String nopedido = String.valueOf(estePedido.getNopedido());
                            String prods = String.valueOf(estePedido.getProducto());
                            String cantidad = String.valueOf(estePedido.getCantidad());
                            String tipoenva = estePedido.getTipoenvase();
                            String nombreas = estePedido.getPersonaasignada();
                            Date fechahora = Calendar.getInstance().getTime();
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                            String dias = dateFormat.format(fechahora);
                            String url = "https://appsionmovil.000webhostapp.com/pedidoenvasar.php?FechaCaptura="+ fechacap.replaceAll(" ","%20") +
                                    "&FechaAprobacion="+ fechaapr.replaceAll(" ","%20") +
                                    "&FechaAsignacion="+ fechaasi.replaceAll(" ","%20") +
                                    "&FechaEnvase="+ dias.replaceAll(" ","%20") +
                                    "&NoPedidos="+ nopedido.replaceAll(" ","%20") +
                                    "&Producto="+ prods.replaceAll(" ","%20") +
                                    "&Cantidad="+ cantidad.replaceAll(" ","%20") +
                                    "&TipoEnvase="+ tipoenva.replaceAll(" ","%20") +
                                    "&PersonaAsignada="+ nombreas.replaceAll(" ","%20");
                            Toast.makeText(MainActivity.this, "Se exportaron los datos", Toast.LENGTH_LONG).show();
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
                            break;
                        case 1:
                            Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_LONG).show();
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }
}