package jogo;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JSpinner;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Loja {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void loja(final Jogo jogo) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loja window = new Loja(jogo);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Loja(Jogo jogo) {
		initialize(jogo);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Jogo jogo) {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 144);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		final JLabel lblPooFraca = new JLabel("Poção Fraca");
		lblPooFraca.setToolTipText("2 Pontos");
		lblPooFraca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PocaoFraca pocao = new PocaoFraca();
				if(jogo.getJogador().getPontos() >= pocao.getPreco()) {
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - pocao.getPreco());
					jogo.getJogador().setHP(jogo.getJogador().getHP() + pocao.getCura());
					jogo.atualizarCenario();
					if (jogo.getJogador() == jogo.jogador1) {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador2(), false);						
					}
					else {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador1(), false);						
					}
					jogo.atualizarPontos();
				}
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblPooFraca.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblPooFraca.setForeground(Color.BLACK);
			}

		});
		lblPooFraca.setFont(new Font("LM Roman 12", Font.BOLD, 12));
		lblPooFraca.setBounds(23, 77, 78, 15);
		frame.getContentPane().add(lblPooFraca);
		
		final JLabel lblPooMdia = new JLabel("Poção Média");
		lblPooMdia.setToolTipText("5 Pontos");
		lblPooMdia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PocaoMedia pocao = new PocaoMedia();
				if(jogo.getJogador().getPontos() >= pocao.getPreco()) {
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - pocao.getPreco());
					jogo.getJogador().setHP(jogo.getJogador().getHP() + pocao.getCura());
					jogo.atualizarCenario();
					if (jogo.getJogador() == jogo.jogador1) {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador2(), false);						
					}
					else {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador1(), false);						
					}
					jogo.atualizarPontos();
				}
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblPooMdia.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblPooMdia.setForeground(Color.BLACK);
			}

		});
		lblPooMdia.setFont(new Font("LM Roman 12", Font.BOLD, 12));
		lblPooMdia.setBounds(184, 75, 78, 15);
		frame.getContentPane().add(lblPooMdia);
		
		final JLabel lblPooForte = new JLabel("Poção Forte");
		lblPooForte.setToolTipText("10 Pontos");
		lblPooForte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PocaoForte pocao = new PocaoForte();
				if(jogo.getJogador().getPontos() >= pocao.getPreco()) {
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - pocao.getPreco());
					jogo.getJogador().setHP(jogo.getJogador().getHP() + pocao.getCura());
					jogo.atualizarCenario();
					if (jogo.getJogador() == jogo.jogador1) {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador2(), false);						
					}
					else {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador1(), false);						
					}
					jogo.atualizarPontos();
				}
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblPooForte.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblPooForte.setForeground(Color.BLACK);
			}

		});
		lblPooForte.setFont(new Font("LM Roman 12", Font.BOLD, 12));
		lblPooForte.setBounds(336, 77, 78, 15);
		frame.getContentPane().add(lblPooForte);
		
		JLabel lblLoja = new JLabel("Loja");
		lblLoja.setFont(new Font("LM Roman Caps 10", Font.BOLD, 14));
		lblLoja.setBounds(12, 23, 70, 15);
		frame.getContentPane().add(lblLoja);
		
		final JLabel lblFlechaPedra = new JLabel("Flecha Pedra");
		lblFlechaPedra.setToolTipText("10 Pontos");
		lblFlechaPedra.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FlechaPedra flecha = new FlechaPedra();
				if(jogo.getJogador().getPontos() >= flecha.getPreco()) {
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - flecha.getPreco());
					jogo.getJogador().setFlecha(flecha);
					jogo.atualizarPontos();
					if (jogo.getJogador() == jogo.jogador1) {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador2(), false);						
					}
					else {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador1(), false);						
					}
				}
				Cenario.labelF('T');
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblFlechaPedra.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblFlechaPedra.setForeground(Color.BLACK);
			}

		});
		lblFlechaPedra.setFont(new Font("LM Roman 12", Font.BOLD, 12));
		lblFlechaPedra.setBounds(23, 50, 96, 15);
		frame.getContentPane().add(lblFlechaPedra);
		
		final JLabel lblFlechaFogo = new JLabel("Fogo");
		lblFlechaFogo.setToolTipText("15 Pontos");
		lblFlechaFogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FlechaFogo flecha = new FlechaFogo();
				if(jogo.getJogador().getPontos() >= flecha.getPreco()) {
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - flecha.getPreco());
					jogo.getJogador().setFlecha(flecha);
					jogo.atualizarPontos();
					if (jogo.getJogador() == jogo.jogador1) {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador2(), false);						
					}
					else {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador1(), false);						
					}
				}
				Cenario.labelF('F');
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblFlechaFogo.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblFlechaFogo.setForeground(Color.BLACK);
			}

		});
		lblFlechaFogo.setFont(new Font("LM Roman 12", Font.BOLD, 12));
		lblFlechaFogo.setBounds(184, 48, 78, 15);
		frame.getContentPane().add(lblFlechaFogo);
		
		final JLabel lblFlechaExplosiva = new JLabel("Explosiva");
		lblFlechaExplosiva.setToolTipText("25 Pontos");
		lblFlechaExplosiva.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FlechaExplosiva flecha = new FlechaExplosiva();
				if(jogo.getJogador().getPontos() >= flecha.getPreco()) {
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - flecha.getPreco());
					jogo.getJogador().setFlecha(flecha);
					jogo.atualizarPontos();
					if (jogo.getJogador() == jogo.jogador1) {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador2(), false);						
					}
					else {
						jogo.receberDados(jogo.getJogador(), jogo.getJogador1(), false);						
					}
				}
				Cenario.labelF('E');
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblFlechaExplosiva.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblFlechaExplosiva.setForeground(Color.BLACK);
			}

		});
		lblFlechaExplosiva.setFont(new Font("LM Roman 12", Font.BOLD, 12));
		lblFlechaExplosiva.setBounds(336, 48, 78, 15);
		frame.getContentPane().add(lblFlechaExplosiva);
		
	}
}
