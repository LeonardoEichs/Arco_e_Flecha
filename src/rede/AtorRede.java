package rede;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import jogo.Cenario;
import jogo.DialogoNome;
import jogo.Jogador;
import jogo.Jogo;
import jogo.MenuInicial;

public class AtorRede implements OuvidorProxy {

	/**
	 *  Variáveis
	 */
	private static final long serialVersionUID = 1L;
	Proxy proxy;
	Jogo atorJogador;
	String nome, servidor;
	DialogoNome dNome;
	boolean ehMinhaVez;
	Jogador jogador;
	Jogo jogo;

	public AtorRede(Jogo jogo) {
		super();
		System.out.println("Criou AtorRede");
		this.atorJogador = jogo;
		jogo.setAtorRede(this);
		proxy = Proxy.getInstance();
		// Abre janela que pede nome e servidor
		dNome = new DialogoNome(this);
	}


	/**
	 * Conecta o jogo em rede.
	 * @param nome
	 * @param ipServidor
	 */
	public void conectarRede(String nome, String ipServidor) {
		try {
			Proxy.getInstance().conectar(dNome.getServidor(), dNome.getNome());
			Proxy.getInstance().addOuvinte(this);
		} catch (JahConectadoException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(atorJogador, e.getMessage());
		} catch (NaoPossivelConectarException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(atorJogador, "Erro: "
					+ e.getMessage());
		} catch (ArquivoMultiplayerException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(atorJogador, "Erro: "
					+ e.getMessage());
		}
	}

	/**
		 * Inicia uma partida de rede.
		 * Esse método é chamado por um jogador que está conectado e deseja jogar.
		 * No servidor, um outro jogador, também conectado e que ainda não está jogando será sorteado para inciar uma partida.
		 * O usuário quando chama esse método ainda não iniciou uma partida, mas solicitou seu inicio.
		 */

	public void iniciarPartidaRede() {
		System.out.println("Iniciar Partida Rede");
		try {
			Proxy.getInstance().iniciarPartida(2);
		} catch (NaoConectadoException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(atorJogador, e.getMessage()); // // falta ator jogador
		}
	}


	/*
	 * Esse método efetivamente inicia a partida.
	 * Ele é chamado pelo servidor em todos os jogadores que fizerem parte da partida,
	 * inclusive pelo usuário que fez a solicitação do início da partida.
	 * (non-Javadoc)
	 * @see br.ufsc.inf.leobr.cliente.OuvidorProxy#iniciarNovaPartida(java.lang.Integer)
	 */
	@Override
	public void iniciarNovaPartida(Integer posicao) {
		System.out.println("iniciarNovaPartida");
		if (posicao == 1) {
			JOptionPane.showMessageDialog(atorJogador,
					"Partida Iniciada, você começa jogando!");
			System.out.println("Testando GetNome do dNome : " + dNome.getJogador().getNome());
			ehMinhaVez = true;
			atorJogador.setJogador1(dNome.getJogador());
			atorJogador.iniciarJogo(ehMinhaVez);

		}
		else if (posicao == 2){
			JOptionPane.showMessageDialog(atorJogador,
					"Partida Iniciada, aguarde uma jogada");
			System.out.println("Testando GetNome do dNome : " + dNome.getJogador().getNome());
			ehMinhaVez = false;
			atorJogador.setJogador2(dNome.getJogador());
			atorJogador.iniciarJogo(ehMinhaVez);
		}
		MenuInicial.close();
		dNome.close();

	}


	// Envia a Jogada para outros jogadores
	public void enviarJogada(Jogador jogador, Jogador jogadorReceber, boolean acabar) {
		System.out.println("Nome Jogador que fez ação : " + jogador.getNome());
		System.out.println("Tenta criar lance");
		Jogada lance = new Lance(jogador, jogadorReceber, acabar);
		System.out.println("Lance criado");
		try {
			System.out.println("Tentou enviar jogada");
			Proxy.getInstance().enviaJogada(lance);;
			System.out.println("Enviou jogada");
		} catch (NaoJogandoException e) {
			e.printStackTrace();
		}
	}

	/* Método chamado pelo servidor para enviar a jogada do jogo da velha
 * para os participantes de uma partida.
 * O único jogador no qual o método não é invocado é aquele que enviou a jogada.
 * (non-Javadoc)
 * @see br.ufsc.inf.leobr.cliente.OuvidorProxy#receberJogada(br.ufsc.inf.leobr.cliente.Jogada)
 */
	@Override
	public void receberJogada(Jogada lance) {
		System.out.println("Recebeu jogada: " + atorJogador.getJogador().getNome());
		if (atorJogador.getJogador() == atorJogador.getJogador1()) {
			atorJogador.setJogador2(((Lance)lance).getJogador());
			atorJogador.getJogador1().setHP((((Lance)lance).getJogadorReceber().getHP()));
			if (atorJogador.getJogador1().getHP() == 0) {
				//JOptionPane.showMessageDialog(null, "Você perdeu");
				atorJogador.close();
			}
			
		}
		else if (atorJogador.getJogador() == atorJogador.getJogador2()){
			atorJogador.setJogador1(((Lance)lance).getJogador());
			atorJogador.getJogador2().setHP((((Lance)lance).getJogadorReceber().getHP()));
			if (atorJogador.getJogador2().getHP() == 0) {
				JOptionPane.showMessageDialog(null, "Você perdeu");
				atorJogador.close();
			}
		}

		if (((Lance)lance).getAcabar()) {
			atorJogador.trocaDeTurno();
		}
		atorJogador.atualizarPontos();
		atorJogador.atualizarCenario();
	}

	@Override
	public void finalizarPartidaComErro(String message) {
		JOptionPane.showMessageDialog(atorJogador, message);
		System.exit(0);
	}

	@Override
	public void receberMensagem(String msg) {
		JOptionPane.showMessageDialog(atorJogador,
				"Mensagem recebida do servidor:" + msg);

	}

	@Override
	public void tratarConexaoPerdida() {
		JOptionPane
		.showMessageDialog(atorJogador,
				"A conexão com o servidor foi perdida, por favor tente novamente mais tarde.");
		System.exit(0);

	}

	@Override
	public void tratarPartidaNaoIniciada(String message) {
		JOptionPane.showMessageDialog(atorJogador,
				"A partida não pode ser iniciada devido ao seguinte erro: "
						+ message);
		System.exit(0);
	}

	public String obtemNomeAdversario(){
		return Proxy.getInstance().obterNomeAdversarios().get(0);
	}


}
