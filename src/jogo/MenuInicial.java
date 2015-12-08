package jogo;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import rede.AtorRede;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuInicial {

	public static JFrame frame;
	static MenuInicial window;
	/**
	 * Lança a aplicação.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MenuInicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria aplicação.
	 */
	public MenuInicial() {
		iniciarJanela();
	}

	/**
	 * Inicializa conteudo da janela.
	 */
	private void iniciarJanela() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblArcoEFlecha = new JLabel("Arco e Flecha");
		lblArcoEFlecha.setFont(new Font("LM Roman Caps 10", Font.BOLD, 23));
		lblArcoEFlecha.setHorizontalAlignment(SwingConstants.LEFT);
		lblArcoEFlecha.setBounds(12, 102, 214, 43);
		frame.getContentPane().add(lblArcoEFlecha);

		final JLabel lblJogar = new JLabel("Jogar");
		lblJogar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicou Menu Inicial");
				Jogo jogo = new Jogo();
				AtorRede atorRede = new AtorRede(jogo);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblJogar.setForeground(Color.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblJogar.setForeground(Color.BLACK);
			}
		});
		lblJogar.setFont(new Font("L M Roman Caps10", Font.BOLD, 19));
		lblJogar.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogar.setBounds(282, 102, 70, 30);
		frame.getContentPane().add(lblJogar);

		final JLabel lblSair = new JLabel("Sair");
		lblSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSair.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSair.setForeground(Color.BLACK);
			}
		});
		lblSair.setFont(new Font("L M Roman Caps10", Font.BOLD, 12));
		lblSair.setHorizontalAlignment(SwingConstants.CENTER);
		lblSair.setBounds(282, 159, 70, 15);
		frame.getContentPane().add(lblSair);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(11, 139, 177, 1);
		frame.getContentPane().add(separator);
	}

	// Fecha aplicação
	public static void close() {
		window.frame.setVisible(false);
		window.frame.dispose();
	}
}
