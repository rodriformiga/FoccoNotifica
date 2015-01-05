package br.com.focco.focconotifica;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.focco.focconotifica.data.Mensagem;

public class MsgAdap extends BaseAdapter {
	private List<Mensagem> lista;
	private Context context;

	public MsgAdap(Context context, List<Mensagem> lista) {
		this.context = context;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return lista.get(position).id;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.lista_mensagem, null);

		TextView txtTit = (TextView) layout.findViewById(R.id.txtMsgTitulo);
		TextView txtSta = (TextView) layout.findViewById(R.id.txtMsgStatus);
		Mensagem msg = lista.get(position);
		txtTit.setText(msg.titulo.toString());
		txtSta.setText(msg.getStatus());
		switch (msg.status) {
		case 'P':
			txtSta.setTextColor(Color.YELLOW);
			break;
		case 'N':
			txtSta.setTextColor(Color.RED);
			break;
		case 'R':
			txtSta.setTextColor(Color.GREEN);
			break;
		}

		return layout;
	}

}
