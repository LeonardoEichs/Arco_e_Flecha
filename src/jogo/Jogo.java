package jogo;
import java.awt.Color;
import java.awt.Window;

import javax.swing.JFrame;

import rede.AtorRede;

public class Jogo extends JFrame{
	

	Jogador jogador1 = null, jogador2 = null;
	Jogador jogador;
	Cenario cenario;
	boolean ehMinhaVez = false;
	AtorRede atorRede;

	public Jogo() {

	}

	public void iniciarJogo(boolean ehMinhaVez) {
		this.ehMinhaVez = ehMinhaVez;
		System.out.println("Iniciar Jogo");
		if (jogador1 != null) {
			jogador1.setX(-20);
			jogador2 = new Jogador(atorRede.obtemNomeAdversario());
			jogador2.setX(1033);
			this.jogador = jogador1;
		}
		else if(jogador2 != null) {
			jogador2.setX(1033);
			jogador1 = new Jogador(atorRede.obtemNomeAdversario());
			jogador1.setX(-20);
			this.jogador = jogador2;
		}
		cenario = new Cenario(this);
	}

	public void close() {
		cenario.frame.setVisible(false);
		cenario.frame.dispose();
	}


	public boolean trocaDeTurno() {
		if (ehMinhaVez == false) {
			jogador.desligarDefesa();
			Cenario.window.apagarD(this);
			jogador.setPontos(jogador.getPontos() + 5);
			if (getJogador() == getJogador1()) {
				receberDados(getJogador1(), getJogador2(), false);
			}
			else {
				receberDados(getJogador2(), getJogador1(), false);
			}
			atualizarPontos();
			ehMinhaVez = true;
			return true;
		}
		else {
			jogador.desligarDefesa();
			ehMinhaVez = false;
			return false;
		}
	}


	public Jogador getJogador() {
		return jogador;
	}

	public Jogador getJogador1() {
		return jogador1;
	}

	public Jogador getJogador2() {
		return jogador2;
	}

	public void setJogador1(Jogador j1) {
		this.jogador1 = j1;
	}

	public void setJogador2(Jogador j2) {
		this.jogador2 = j2;
	}

	public void atualizarPontos() {
		System.out.println("Pontos");
		Cenario.window.atualizarPontos(this);
	}
	public void atualizarCenario() {
		System.out.println("Atualizou o cenario");
		Cenario.window.atualizarCenario(this);
	}

	public void receberDados(Jogador jog, Jogador jogadorReceber, boolean acabar) {
		System.out.println("Na classe Jogo : " + jog.getNome());
		atorRede.enviarJogada(jog, jogadorReceber, acabar);

	}

	public void setAtorRede(AtorRede atorRede) {
		this.atorRede = atorRede;

	}

}
