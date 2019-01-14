package com.julioflores.prueba2;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Main3Activity extends ArrayAdapter <Tintes> {

    public Main3Activity(Activity context, ArrayList<Tintes> pedido_entinte) {
        super(context, 0, pedido_entinte);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main3, parent, false);
        }
        Tintes currentPedidoEntinte = getItem(position);

        TextView NoPedidoTextView = (TextView) listItemView.findViewById(R.id.NoPedido_entinte);
        NoPedidoTextView.setText(String.valueOf(currentPedidoEntinte.getNopedido()));

        TextView ProductoEntinteTextView = (TextView) listItemView.findViewById(R.id.Producto_entinte);
        ProductoEntinteTextView.setText(currentPedidoEntinte.getProducto());

        TextView ObservacionesTextView = (TextView) listItemView.findViewById(R.id.Observaciones_entinte);
        ObservacionesTextView.setText(currentPedidoEntinte.getObservaciones());

        TextView Espiner = (TextView) listItemView.findViewById(R.id.Etapa1_entinte);
        Espiner.setText(currentPedidoEntinte.getEtapa1());

        TextView TipoEnvaseTextView = (TextView) listItemView.findViewById(R.id.TipoEnvase_entinte);
        TipoEnvaseTextView.setText(currentPedidoEntinte.getCantidad()+" "+currentPedidoEntinte.getTipoenvase());

        TextView PersonaAsignadaTextView = (TextView) listItemView.findViewById(R.id.PersonaAsignada_entinte);
        PersonaAsignadaTextView.setText((currentPedidoEntinte.getPersonaasignada()));

        TextView FechaAsignacionTextView = (TextView) listItemView.findViewById(R.id.FechaAsignacion_entinte);
        FechaAsignacionTextView.setText(currentPedidoEntinte.getFechaasigacion());

        TextView FechaCapturaTextView = (TextView) listItemView.findViewById(R.id.FechaCaptura_entinte);
        FechaCapturaTextView.setText(currentPedidoEntinte.getFechacaptura());
        return listItemView;
    }
}