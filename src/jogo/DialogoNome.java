package jogo;
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

import rede.AtorRede;

/*
*
*
*		Essa classe serve para perguntar ao jogador seu nome e servidor
* e passa essas informações ao AtorRede
*
*/

public class DialogoNome {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTextField txtNome;
	static String nome;
	static String servidor;
	private JTextField textServidor;
	JLabel lblIniciar;
	static Jogador jogador;
	static AtorRede atorRede;
	static DialogoNome window;


	/*
	*		Construtores
	*/
	public DialogoNome(AtorRede atorRede) {
		DialogoNome.atorRede = atorRede;
		System.out.println("Criou Dialogo");
		startWindow();
	}
	public DialogoNome() {
		initialize();
	}


	// Cria nova Thread para executar a janela
	public static void startWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new DialogoNome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Inicializa o conteudo da janela.
	 */
	public void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 442, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtNome = new JTextField();
		txtNome.setForeground(Color.DARK_GRAY);
		txtNome.setBackground(Color.WHITE);
		txtNome.setFont(new Font("L M Roman Caps10", Font.PLAIN, 12));
		txtNome.setHorizontalAlignment(SwingConstants.LEFT);
		txtNome.setBounds(22, 39, 396, 33);
		frame.getContentPane().add(txtNome);
		txtNome.setColumns(10);

		textServidor = new JTextField();
		textServidor.setHorizontalAlignment(SwingConstants.LEFT);
		textServidor.setForeground(Color.DARK_GRAY);
		textServidor.setFont(new Font("L M Roman Caps10", Font.PLAIN, 12));
		textServidor.setColumns(10);
		textServidor.setBackground(Color.WHITE);
		textServidor.setBounds(22, 119, 396, 33);
		frame.getContentPane().add(textServidor);

		JLabel lblNome = new JLabel(" Nome : ");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setFont(new Font("L M Roman Caps10", Font.BOLD, 12));
		lblNome.setBounds(15, 23, 70, 15);
		frame.getContentPane().add(lblNome);

		JLabel lblServidor = new JLabel("Servidor :");
		lblServidor.setHorizontalAlignment(SwingConstants.LEFT);
		lblServidor.setFont(new Font("L M Roman Caps10", Font.BOLD, 12));
		lblServidor.setBounds(15, 103, 90, 15);
		frame.getContentPane().add(lblServidor);

		lblIniciar = new JLabel("INICIAR");
		lblIniciar.setFont(new Font("L M Roman Caps10", Font.BOLD, 12));
		lblIniciar.setVisible(false);
		lblIniciar.setBounds(210, 196, 83, 15);
		lblIniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atorRede.iniciarPartidaRede();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblIniciar.setForeground(Color.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblIniciar.setForeground(Color.BLACK);
			}
		});

		frame.getContentPane().add(lblIniciar);

		final JLabel lblOk = new JLabel("OK");
		lblOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblIniciar.setVisible(true);
				nome = txtNome.getText();
				System.out.println(nome);
				servidor = textServidor.getText();
				System.out.println(servidor);
				jogador = new Jogador(nome);
				System.out.println("Criou Jogador " + jogador.getNome());
				atorRede.conectarRede(nome, servidor);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblOk.setForeground(Color.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblOk.setForeground(Color.BLACK);
			}
		});
		lblOk.setHorizontalAlignment(SwingConstants.LEFT);
		lblOk.setBounds(305, 195, 37, 15);
		frame.getContentPane().add(lblOk);

		final JLabel lblCancel = new JLabel("Cancel");
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCancel.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCancel.setForeground(Color.BLACK);
			}
		});
		lblCancel.setBounds(348, 195, 70, 15);
		frame.getContentPane().add(lblCancel);


	}

	/*
	*	Métodos get
	*/
	public String getNome() {
		return nome;
	}

	public String getServidor() {
		return servidor;
	}

	public Jogador getJogador() {
		return jogador;
	}

	//	Fecha esta janela
	public static void close() {
		window.frame.setVisible(false);
		window.frame.dispose();
	}

}
