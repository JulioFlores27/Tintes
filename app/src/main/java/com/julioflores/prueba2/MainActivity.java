package com.julioflores.prueba2;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity{
    SwipeRefreshLayout swipeRefreshLayout;
    List<String> myList;
    ArrayList<Tintes> lista123 = new ArrayList<Tintes>();
    ListAdapter adapter;
    ListView lvlista1;
    AsyncHttpClient cliente;
    TextView ett1;
    int acceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvlista1 = (ListView) findViewById(R.id.lista1);
        cliente = new AsyncHttpClient();
        ett1 = (TextView) findViewById(R.id.eet1);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        //Supervisor 1, entonador 2
        acceso=1;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ObtenerTintes();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (acceso == 1) {
            ObtenerTintes();
        }else  {
            ObtenerTintes2();
        }
    }
    private void ObtenerTintes(){
        String url = "https://appsionmovil.000webhostapp.com/pedidostintes.php";
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
            JSONArray jsonarreglo = new JSONArray(respuesta);
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
                    Tintes position2 = lista.get((int) id);
                    Tintes id2 = lista.get(position);
                    Tintes estePedido = (Tintes) parent.getItemAtPosition(position);
                    String CantidadDeEstePedido = String.valueOf(estePedido.getId());
                    intent.putExtra("ids", CantidadDeEstePedido);
                    Toast.makeText(MainActivity.this, CantidadDeEstePedido.toString(),Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }


}