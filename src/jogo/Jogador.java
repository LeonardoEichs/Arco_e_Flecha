package jogo;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class Jogador implements br.ufsc.inf.leobr.cliente.Jogada{

	String nome;
	int pontos;
	int x;
	int velX;
	int hp;
	boolean vivo;
	boolean defesa = false;
	Flecha flecha = new Flecha(0,25);

	public Jogador(String nome) {
		this.nome = nome;
		vivo = true;
		hp = 100;
		pontos = 10;
	}

	// Métodos get e set para Nome

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	//

	// Métodos get e set para X

	public void setX(int posX) {
		this.x = posX;
	}

	public int getX() {
		return x;
	}

	//


	// Métodos get e set para HP

	public boolean setHP(int hp) {
		if (hp > 100) {
			this.hp = 100;
		}
		else if  (hp <= 0) {
			this.hp = 0;
			vivo = false;
			return true;
			//JOptionPane.showMessageDialog(null, "Você venceu");
			//jogo.close();
			//TelaFim.startWindow(nome);
		}
		else {
			this.hp = hp;
		}
		return false;
	}

	public int getHP() {
		return hp;
	}

	//

	// Métodos get e set para Pontos

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getPontos() {
		return pontos;
	}

	//

	// Indica se está defendendo

	public boolean getDefesa() {
		return defesa;
	}

	//	Ativa modo de defesa

	public void defender() {
		pontos -= 5;
		defesa = true;
	}

	//	Desliga modo de defesa

	public void desligarDefesa() {
		defesa = false;
	}

	//

	// Métodos get e set para flecha

	public Flecha getFlecha() {
		return flecha;
	}

	public void setFlecha(Flecha flechaP) {
		flecha = flechaP;
	}

		//

}
