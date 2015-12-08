package rede;

import jogo.Jogador;

// Implementa Jogada do NetGamesNRT

public class Lance implements br.ufsc.inf.leobr.cliente.Jogada{

	Jogador jogadorAcao, jogadorReceber;
	boolean acabar;

	public Lance(Jogador jogadorAcao, Jogador jogadorReceber, boolean acabar) {
		this.jogadorAcao = jogadorAcao;
		this.jogadorReceber = jogadorReceber;
		this.acabar = acabar;
	}

	public Jogador getJogador() {
		return jogadorAcao;
	}

	public Jogador getJogadorReceber() {
		return jogadorReceber;
	}
	public boolean getAcabar() {
		return acabar;
	}

}
