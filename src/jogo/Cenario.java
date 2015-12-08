package jogo;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import com.sun.xml.internal.ws.Closeable;

import rede.Lance;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


/*
*														Jogo Arco e Flecha
*
*							(feito para disciplina de Engenharia de Software)
*
*	Um jogador de cada lado. O objetivo é atirar flechas para matar o outro
*
*	COMANDOS :
*
*		Para atirar posicione o mouse de forma a ter o angulo de tiro desejado, clique
* e segure o mouse para carregar força. Quando o mouse for solto a flecha será
* lançada.
*
*	  Direcional Direito -> Move o personagem para Direita. Custo : para 10 passos 1 ponto
*		Direcional Esquerdo -> Move o personagem para Esquerda. Custo : para 10 passos 1 ponto
*		S -> Abre uma loja onde pode ser comprado poções e flechas, utilizand pontos
*		D -> Ativa Modo de Defesa, reduz dano. Custo : 5 pontos
*		T -> Terminar turno, passa o turno para o oponente. Quando recebe turno há ganho de 5 pontos.
*
*
*
*
*
*
*
*/



public class Cenario{

	// Frame da aplicação
	static JFrame frame;
	// Instância do jogo
	static Jogo jogo;
	// Nova thread que executara cenario é acessivel por esta variavel
	static Cenario window;
	// JLabels utilizadas no código
	static JLabel labelPontos, labelPontos2;
	static JLabel lblD1, lblD2; // Indica defesa ligada
	static JLabel lblF1, lblF2; // Indica tipo de flecha
	JLabel labelAngulo;
	JLabel labelForca;
	JLabel lblDistncia;
	JLabel lblNomeJ1, lblNomeJ2;
	// Criação da classe Muro
	private Muro muro = new Muro();
	// Variáveis que armazenam posição do mouse
	int xMouse, yMouse;
	// boolean que diz se pode andar
	boolean walkable = true;
	boolean morte;
	// utilizado como base para calcualar força
	int time = 0;
	// contagem de força
	int strCounter;
	// utilizado método de andar
	int contador = 0;
	int forca = 0;
	// utilizadas para calculo do angulo
	double angulo, oposto, hip, sine;
	// AtorRede
	DialogoNome atorRede;
	// Barras de Vida
	JProgressBar progressBarJ1;
	JProgressBar progressBarJ2;
	// Labels que representam o jogador
	JLabel labelJ1;
	JLabel labelJ2;
	//Label para mostrar o turno
	JLabel JTurno;

	/*
	 *
	 * Construtores
	 *
	 */
	public Cenario(Jogo jogo) {
		Cenario.jogo = jogo;
		cenario();
	}
	public Cenario() {
		initialize();
	}
	// Apesar de cenario() não ser construtor, ele é usado para
	//fazer uma nova  threas e construir um cenario
	public static void cenario() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Cenario();
					Cenario.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Método que constrói os componentes do cenario em si
	private void initialize() {

		// Seleciona um background
			// Para mudar basta mudar o nome da imagem
		JLabel background = new JLabel();
		Image imgB = new ImageIcon(this.getClass().getResource("/cloud-example.jpg")).getImage();
		background.setIcon(new ImageIcon(imgB));

		/*
		 * 	Criação do Frame
		 */
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(200, 100, 1100, 600);
		frame.setContentPane(background);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Criação do Panel
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(-27, 500, 1500, 103);
		frame.getContentPane().add(panel);

		// labelAngulo mostra o angulo entre personagem e mouse
		labelAngulo = new JLabel("");
		labelAngulo.setFont(new Font("L M Roman Caps10", Font.BOLD, 12));
		frame.getContentPane().add(labelAngulo);

		// labelForca mostra a forca que a flecha será lançada
			// baseada no tempo entre pressionar e soltar o mouse
		labelForca = new JLabel("");
		labelForca.setFont(new Font("L M Roman Caps10", Font.BOLD, 12));
		labelForca.setVisible(false);
		frame.getContentPane().add(labelForca);




		// Cria barra de vida para jogador1
		progressBarJ1 = new JProgressBar();
		progressBarJ1.setBackground(Color.GRAY);
		progressBarJ1.setForeground(Color.GREEN);
		progressBarJ1.setOrientation(SwingConstants.VERTICAL);
		progressBarJ1.setBounds(jogo.getJogador1().getX() + 75, 465, 5, 25);
		progressBarJ1.setValue(100);

		// Cria barra de vida para jogador2
		progressBarJ2 = new JProgressBar();
		progressBarJ2.setBackground(Color.GRAY);
		progressBarJ2.setForeground(Color.RED);
		progressBarJ2.setOrientation(SwingConstants.VERTICAL);
		progressBarJ2.setBounds(jogo.getJogador2().getX() + 10, 465, 5, 25);
		progressBarJ2.setValue(100);


		frame.getContentPane().add(progressBarJ1);
		frame.getContentPane().add(progressBarJ2);



		// Label que representa jogador1
		labelJ1 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/player1.png")).getImage();
		labelJ1.setIcon(new ImageIcon(img));
		labelJ1.setBounds(jogo.getJogador1().getX() + 20, 450, 50, 50);

		// Label que representa jogador2
		labelJ2 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/player2.png")).getImage();
		labelJ2.setIcon(new ImageIcon(img1));
		labelJ2.setBounds(jogo.getJogador2().getX() + 20, 450, 50, 50);


		frame.getContentPane().add(labelJ1);
		frame.getContentPane().add(labelJ2);


		// Cria label que indica defesa dependendo do jogador que possui o jogo
		if (jogo.getJogador() == jogo.getJogador1()) {
			lblD1 = new JLabel("D");
			lblD1.setForeground(Color.BLACK);
			lblD1.setFont(new Font("Dialog", Font.BOLD, 14));
			lblD1.setBounds(30, 78, 70, 15);
			lblD1.setVisible(false);
			frame.getContentPane().add(lblD1);
		}
		else {
			lblD2 = new JLabel("D");
			lblD2.setForeground(Color.BLACK);
			lblD2.setFont(new Font("Dialog", Font.BOLD, 14));
			lblD2.setBounds(1054, 78, 70, 15);
			lblD2.setVisible(false);
			frame.getContentPane().add(lblD2);
		}

		// Cria label que indica tipo de flecha dependendo do jogador que possui o jogo
		if (jogo.getJogador() == jogo.getJogador1()){
			lblF1 = new JLabel("F");
			lblF1.setBounds(30, 112, 70, 15);
			frame.getContentPane().add(lblF1);
		}
		else {
			lblF2 = new JLabel("F");
			lblF2.setBounds(1054, 112, 70, 15);
			frame.getContentPane().add(lblF2);
		}

		// Cria muro
		muro.setX(510);
		frame.getContentPane().add(muro.getLabel());

		//Label que mostra o turno
		JTurno = new JLabel("T");
		JTurno.setFont(new Font("L M Roman Caps10", Font.BOLD, 24));
		JTurno.setBounds(510, 12, 297, 36);
		if (jogo.ehMinhaVez) {
			JTurno.setVisible(true);
		}
		else {
			JTurno.setVisible(false);
		}
		frame.getContentPane().add(JTurno);
		
		// Label que mostra nome do jogador1
		lblNomeJ1 = new JLabel("");
		lblNomeJ1.setFont(new Font("L M Roman Caps10", Font.BOLD, 16));
		lblNomeJ1.setBounds(30, 12, 297, 36);
		lblNomeJ1.setText(jogo.getJogador1().getNome());
		frame.getContentPane().add(lblNomeJ1);

		// Label que mostra nome do jogador2
		lblNomeJ2 = new JLabel("");
		lblNomeJ2.setFont(new Font("L M Roman Caps10", Font.BOLD, 16));
		lblNomeJ2.setBounds(950, 12, 297, 36);
		lblNomeJ2.setText(jogo.getJogador2().getNome());
		frame.getContentPane().add(lblNomeJ2);


		// Label que mostra pontos do jogador1
		labelPontos = new JLabel("");
		labelPontos.setFont(new Font("L M Roman Caps10", Font.PLAIN, 12));
		labelPontos.setBounds(30, 40, 297, 36);
		labelPontos.setText("Pontos : " + Integer.toString(jogo.getJogador1().getPontos()));
		frame.getContentPane().add(labelPontos);


		// Label que mostra pontos do jogador2
		labelPontos2 = new JLabel("");
		labelPontos2.setFont(new Font("L M Roman Caps10", Font.PLAIN, 12));
		labelPontos2.setBounds(950, 40, 297, 36);
		labelPontos2.setText("Pontos : " + Integer.toString(jogo.getJogador2().getPontos()));
		frame.getContentPane().add(labelPontos2);

		// lblDistncia mostra onde a flecha caiu
		lblDistncia = new JLabel("");
		lblDistncia.setVisible(false);
		lblDistncia.setBounds(336, 416, 120, 15);
		/*
		*	Caso queira usar imagem como indicador
		*/
		//Image imgA = new ImageIcon(this.getClass().getResource("/arrow.png")).getImage();
		//lblDistncia.setIcon(new ImageIcon(imgA));
		frame.getContentPane().add(lblDistncia);


		/*
		 * 	Ouvidor para motion do mouse
		 */
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// Esse método seta a posição x e y do mouse
					// O ângulo é calculado com base nesses dados
						// Seta p/ invisível labels que possam ter
						// sido ativadas em outros métodos
				xMouse = e.getX();
				yMouse = e.getY();
				angulo = getAngulo();
				labelForca.setVisible(false);
				lblDistncia.setVisible(false);
			}

		});

		/*
		 * 	Ouvidor para clicar e soltar mouse
		 */
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Confere se é a vez e se possui pontos suficientes
				if (jogo.getJogador().getPontos() >= 5 && jogo.ehMinhaVez) {
					walkable = false; // não pode andar enquanto estiver atirando
					labelForca.setVisible(false);
					lblDistncia.setVisible(false);
					// time é o tempo do sistema
					time = (int) System.currentTimeMillis();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (jogo.getJogador().getPontos() >= 5 && jogo.ehMinhaVez) {
					// retira os pontos do jogador
					jogo.getJogador().setPontos(jogo.getJogador().getPontos() - 5);
					// atualiza painel de pontos
					atualizarPontos(jogo);
					if (jogo.getJogador() == jogo.getJogador1()) {
						jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
					}
					else {
						jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
					}
					// conseguimos a força por meio fa subtação entre o tempo atual e
					// o tempo em que o mouse foi clicado
						// podemos trocar o /20 por outro numero
						// depende de quão rápido queremos que a força seja carregada
					forca = (((int) System.currentTimeMillis() - time)/20) + 1;
					if (forca > 100) {
						// se força for maior que 100, nós arredondamos
						forca = 100;
						// método que calcula a distância com base no ângulo e força
						calcularDistancia();
						// labelForcao mostra forca do lance
						labelForca.setBounds(xMouse, yMouse, 100, 50);
						labelForca.setText("Força: " + Integer.toString(forca));
						labelForca.setVisible(true);
						// fazemos as atualizações para os membros do jogo
						if (jogo.getJogador() == jogo.getJogador1()) {
							jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
						}
						else {
							jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
						}
					}
					else {
						calcularDistancia();
						//
						labelForca.setBounds(xMouse, yMouse, 100, 50);
						labelForca.setText("Força: " + Integer.toString(forca));
						labelForca.setVisible(true);
						//
						if (jogo.getJogador() == jogo.getJogador1()) {
							jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
						}
						else {
							jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
						}
					}

					// caso estivesse usando uma flecha especial, retornamos para a normal
					if (jogo.getJogador() == jogo.getJogador1()){
						lblF1.setForeground(Color.BLACK);
					}
					else {
						lblF2.setForeground(Color.BLACK);

					}
					jogo.getJogador().setFlecha(new Flecha());
				}
				// permite personagem andar novamente
				walkable = true;
			}
		});

		/*
		* Adiciona ouvidor às teclas
		*/
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				// Direcional Direito
					// Andar Para Direita
				if (code == KeyEvent.VK_RIGHT) {
					if (jogo.ehMinhaVez) {
						// Para jogador1
						if (jogo.getJogador() == jogo.getJogador1()) {
							// Trata colisão com o muro
							if (walkable == true && jogo.getJogador().getPontos() > 0 && jogo.getJogador1().getX() < muro.getX() - 60) {
								labelForca.setVisible(false);
								lblDistncia.setVisible(false);
								// Método que move p/ esquerda em si
								right();
								getAngulo();
								// Atualizações para membros da partida
								if (jogo.getJogador() == jogo.getJogador1()) {
									jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
								}
								else {
									jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
								}
							}
						}
						// Para jogador2
						else if (jogo.getJogador() == jogo.getJogador2()) {
							if (walkable == true && jogo.getJogador().getPontos() > 0 && jogo.getJogador2().getX() < 1033) {
								labelForca.setVisible(false);
								lblDistncia.setVisible(false);
								right();
								getAngulo();
								if (jogo.getJogador() == jogo.getJogador1()) {
									jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
								}
								else {
									jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
								}
							}
						}
					}
				}
				// Direcional esquerdo
					// Andar para esquerda
				if (code == KeyEvent.VK_LEFT) {
					if (jogo.ehMinhaVez) {
						// Para jogador1
						if (jogo.getJogador() == jogo.getJogador1()) {
							if (walkable == true && jogo.getJogador().getPontos() > 0 && jogo.getJogador1().getX() > -20) {
								labelForca.setVisible(false);
								lblDistncia.setVisible(false);
								left();
								getAngulo();
								if (jogo.getJogador() == jogo.getJogador1()) {
									jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
								}
								else {
									jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
								}
							}
						}
						// Para jogador2
						else if (jogo.getJogador() == jogo.getJogador2()) {
							if (walkable == true && jogo.getJogador().getPontos() > 0 && jogo.getJogador2().getX() > muro.getX() + 20) {
								labelForca.setVisible(false);
								lblDistncia.setVisible(false);
								left();
								getAngulo();
								if (jogo.getJogador() == jogo.getJogador1()) {
									jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
								}
								else {
									jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
								}
							}
						}
					}
				}

				// Tecla S
					//  Abre loja
				if (code == KeyEvent.VK_S) {
					if (jogo.ehMinhaVez) {
						Loja.loja(jogo);
					}
				}

				// Tecla D
					// Ativa modo defesa
				if (code == KeyEvent.VK_D) {
					if(jogo.ehMinhaVez) {
						// 	Para jogador1
						if (jogo.getJogador() == jogo.getJogador1()) {
							if (jogo.getJogador().getPontos() >= 5 && jogo.getJogador().getDefesa() == false) {
								lblD1.setVisible(true);
								jogo.getJogador().defender();
								atualizarPontos(jogo);
								if (jogo.getJogador() == jogo.getJogador1()) {
									jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
								}
								else {
									jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
								}
							}
						}
						if (jogo.getJogador() == jogo.getJogador2()) {
							if (jogo.getJogador().getPontos() >= 5 && jogo.getJogador().getDefesa() == false) {
								lblD2.setVisible(true);
								jogo.getJogador().defender();
								atualizarPontos(jogo);
								if (jogo.getJogador() == jogo.getJogador1()) {
									jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
								}
								else {
									jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
								}
							}
						}
					}
				}
				if (code == KeyEvent.VK_T) {
					if (jogo.ehMinhaVez) {
						if (jogo.getJogador() == jogo.getJogador1()) {
							jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), true);
						}
						else {
							jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), true);
						}
						jogo.trocaDeTurno();
						atualizarCenario(jogo);
					}
				}
			}
		});

	}

	// Atualiza Placar de Pontos
	public void atualizarPontos(Jogo jogo) {
			labelPontos.setText("Pontos : " + Integer.toString(jogo.getJogador1().getPontos()));
			labelPontos2.setText("Pontos : " + Integer.toString(jogo.getJogador2().getPontos()));
	}

	// Atualiza Cenario
	public void atualizarCenario(Jogo jogo) {

		labelJ1.setBounds(jogo.getJogador1().getX() + 20, 450, 50, 50);
		labelJ2.setBounds(jogo.getJogador2().getX() + 20, 450, 50, 50);


		progressBarJ1.setValue(jogo.getJogador1().getHP());
		progressBarJ2.setValue(jogo.getJogador2().getHP());


		progressBarJ1.setBounds(jogo.getJogador1().getX() + + 75, 465, 5, 25);
		progressBarJ2.setBounds(jogo.getJogador2().getX() + 10, 465, 5, 25);
		
		if (jogo.ehMinhaVez) {
			JTurno.setVisible(true);
		}
		else {
			JTurno.setVisible(false);
		}


	}


	// Metodo que faz personagem andar para Direita
	public void right() {
		int newX = jogo.getJogador().getX() + 5; // Numero controla qtd movimento
		contador += 1;
		if (contador == 10) {
			jogo.getJogador().setPontos(jogo.getJogador().getPontos() - 1);
			atualizarPontos(jogo);
			contador = 0;
		}
		jogo.getJogador().setX(newX);
		jogo.atualizarCenario();
	}

	// Metodo que faz personagem andar para Esquerda
	public void left() {
		int newX = jogo.getJogador().getX() - 5; // Numero controla qtd movimento
		contador += 1;
		if (contador == 10) {
			jogo.getJogador().setPontos(jogo.getJogador().getPontos() - 1);
			atualizarPontos(jogo);
			contador = 0;
		}
		jogo.getJogador().setX(newX);
		jogo.atualizarCenario();
	}

	// Pega o angulo ente mouse e personagem
	public double getAngulo() {
		// calcula a OPOSTA
		oposto = 515 - yMouse;
		// calcula HIPOTENUSA
		if (jogo.getJogador() == jogo.getJogador1()) {
			hip = Math.sqrt(
					Math.pow(xMouse - jogo.getJogador().getX() - 56, 2) +
					Math.pow(yMouse - 515, 2) );
		}
		else {
			hip = Math.sqrt(
					Math.pow(xMouse - jogo.getJogador().getX() - 12, 2) +
					Math.pow(yMouse - 515, 2) );
		}
		// Seno = Op/Hip
		sine = oposto/hip;
		// Organiza label
		labelAngulo.setBounds(xMouse, yMouse - 60, 50, 50);
		labelAngulo.setText(Double.toString(Math.ceil(Math.toDegrees(Math.asin(sine)))) + "°");
		// Transformação
		return Math.ceil(Math.toDegrees(Math.asin(sine)));

	}

	public int calcularDistancia() {

		// Componentes independentes do vetor
		int vox = (int) Math.ceil(forca * Math.cos(Math.toRadians(angulo)));
		int voy = (int) Math.ceil(forca * Math.sin(Math.toRadians(angulo)));

		if (jogo.getJogador() == jogo.getJogador1()) {
			vox = vox;
		}
		else {
			vox = -vox; // Inverter componente horizontal
		}

		double posX = 0.0, posY = 10000000.0;
		double t = 0.0;
		// Enquanto não bater no chão
		while (posY > 500) {
			// componente horizontal pelo tempo
			posX = vox * t;
			// relação para componente vertical
			posY = 515 + voy*t - 0.5*(9.8)*t*t;

			/*
			*	Colisão com muro
			*		Caso a flecha passe pelas coordenadas do muro
			*/
			if (jogo.getJogador() == jogo.getJogador1() &&
					posY <= 600 && posX + jogo.getJogador1().getX() <= muro.getX() + 12
					&& posX + jogo.getJogador1().getX() >= muro.getX() - 12) {
				break;
			}
			if (jogo.getJogador() == jogo.getJogador2() &&
					posY <= 600 && jogo.getJogador2().getX() + posX <= muro.getX() + 12
					&& jogo.getJogador2().getX() + posX >= muro.getX() - 12) {
				break;
			}


			/*
			* Caso acerte o oponente
			*		Caso flecha passe pelas coordenadas do oponente
			*/
			if (	jogo.getJogador() == jogo.getJogador1() &&
					posY <= 500 && posX + jogo.getJogador1().getX() >= jogo.getJogador2().getX() - 50 &&
					posX + jogo.getJogador1().getX() <= jogo.getJogador2().getX() + 50) {
				if (jogo.getJogador2().getDefesa()) {
					morte = jogo.getJogador2().setHP(jogo.getJogador2().getHP() - jogo.getJogador().getFlecha().getDano() + 10);
					if (jogo.getJogador() == jogo.getJogador1()) {
						jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
					}
					else {
						jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
					}
				}
				else {
					morte = jogo.getJogador2().setHP(jogo.getJogador2().getHP() - jogo.getJogador().getFlecha().getDano());
					if (jogo.getJogador() == jogo.getJogador1()) {
						jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
					}
					else {
						jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
					}
				}
				//jogo.getJogador2().setHP(jogo.getJogador2().getHP() - jogo.getJogador1().getFlecha().getDano());
				progressBarJ2.setValue(jogo.getJogador2().getHP());
				atualizarCenario(jogo);
				if (morte) {
					JOptionPane.showMessageDialog(null, "Você venceu");
					jogo.close();
				}
				break;
			}
			if (jogo.getJogador() == jogo.getJogador2() &&
					posY <= 500 && jogo.getJogador2().getX() + posX <= jogo.getJogador1().getX() + 50
					&& jogo.getJogador2().getX() + posX >= jogo.getJogador1().getX() - 50) {
				if (jogo.getJogador1().getDefesa()) {
					morte = jogo.getJogador1().setHP(jogo.getJogador1().getHP() - jogo.getJogador().getFlecha().getDano() + 10);
					if (jogo.getJogador() == jogo.getJogador1()) {
						jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
					}
					else {
						jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
					}
				}
				else {
					morte = jogo.getJogador1().setHP(jogo.getJogador1().getHP() - jogo.getJogador().getFlecha().getDano());
					if (jogo.getJogador() == jogo.getJogador1()) {
						jogo.receberDados(jogo.getJogador1(), jogo.getJogador2(), false);
					}
					else {
						jogo.receberDados(jogo.getJogador2(), jogo.getJogador1(), false);
					}
				}
				//jogo.getJogador1().setHP(jogo.getJogador1().getHP() - jogo.getJogador2().getFlecha().getDano());
				progressBarJ1.setValue(jogo.getJogador1().getHP());
				atualizarCenario(jogo);
				break;
			}
			t += 0.005;
		}
		if (jogo.getJogador() == jogo.getJogador1()) {
			lblDistncia.setText("Distância: " + Math.ceil(posX));
		}
		else {
			lblDistncia.setText("Distância: " + Math.ceil(-posX));
		}
		lblDistncia.setBounds((int) (jogo.getJogador().getX() + posX), 416, 120, 45);
		// Caso queira usar a imagem
		//lblDistncia.setBounds((int) (jogo.getJogador().getX() + posX), 416, 50, 50);
		lblDistncia.setVisible(true);
		return 0;
	}

	// Na compra de uma flecha, atualiza labelF
	public static void labelF(char c) {
		switch(c) {
			case ('E') :
				if (jogo.getJogador() == jogo.getJogador1()){
					lblF1.setForeground(Color.RED);
				}
				else {
					lblF2.setForeground(Color.RED);
				}
				break;
			case ('F') :
				if (jogo.getJogador() == jogo.getJogador1()){
					lblF1.setForeground(Color.ORANGE);
				}
				else {
					lblF2.setForeground(Color.ORANGE);
				}
				break;
			case ('T') :
				if (jogo.getJogador() == jogo.getJogador1()){
					lblF1.setForeground(Color.GREEN);
				}
				else {
					lblF2.setForeground(Color.GREEN);
				}
		}
	}

	// Torna labelD invisivel
	public void apagarD(Jogo jogo2) {
		if (jogo2.getJogador() == jogo.getJogador1()) {
			lblD1.setVisible(false);
		}
		else {
			lblD2.setVisible(false);
		}

	}
}
