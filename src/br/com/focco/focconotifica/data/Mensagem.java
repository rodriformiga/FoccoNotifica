package br.com.focco.focconotifica.data;

public class Mensagem {
	public int id;
	public String titulo;
	public String mensagem;
	public char status;
	public String resposta;

	public String getStatus() {

		switch (status) {
		case 'P':
			return "Pendente";
		case 'N':
			return "Nova";
		case 'R':
			return "Respondida";
		default:
			return "Indefinido";
		}
	}
}
