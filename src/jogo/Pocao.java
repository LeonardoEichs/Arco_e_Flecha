package jogo;

/*
*		Classe pai das Poções
*/

public class Pocao {
	int preco;
	int cura;

	public Pocao(int preco, int cura) {
		this.preco = preco;
		this.cura = cura;
	}

	public int getPreco() {
		return preco;
	}

	public int getCura() {
		return cura;
	}

}
