package jogo;

/*
*
*		Classe Flecha equivale a flecha padrão
*
*/

public class Flecha implements br.ufsc.inf.leobr.cliente.Jogada{
	int preco;
	int dano;

	public Flecha() {
		preco = 0;
		dano = 25;
	}

	public Flecha(int preco, int dano) {
		this.preco = preco;
		this.dano = dano;
	}

	public int getPreco() {
		return preco;
	}

	public int getDano() {
		return dano;
	}

}
