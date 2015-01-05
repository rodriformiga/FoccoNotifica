package br.com.focco.focconotifica;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.com.focco.focconotifica.data.Mensagem;

public class Principal extends Activity implements OnItemClickListener {

	List<Mensagem> msgs;
	private ListView listaMsgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		carrega();

		MsgAdap adap = new MsgAdap(this, msgs);

		listaMsgs = (ListView) findViewById(R.id.lstGeral);
		listaMsgs.setAdapter(adap);
		listaMsgs.setOnItemClickListener(this);
	}

	private void carrega() {
		if (msgs == null) {
			msgs = new ArrayList<Mensagem>();
		}
		msgs.clear();

		for (int i = 1; i < 10; i++) {
			Mensagem tmp = new Mensagem();
			tmp.id = i;
			tmp.titulo = "Mensagem " + i;
			tmp.mensagem = "Esta é a mensagem " + i;

			switch (i) {
			case 1:
			case 2:
			case 3:
				tmp.status = 'N';
				break;
			case 4:
			case 5:
			case 6:
				tmp.status = 'P';
				break;
			default:
				tmp.status = 'R';
				break;
			}

			// tmp.status = "0";
			tmp.resposta = "";
			msgs.add(tmp);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(getApplicationContext(), msgs.get(position).mensagem, Toast.LENGTH_LONG).show();
	}
}
