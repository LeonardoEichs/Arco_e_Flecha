package jogo;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
* 	Cria o muro do jogo
*/


public class Muro {

	int x;
	JLabel label;

	public Muro() {
		label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/muro.png")).getImage();
		label.setIcon(new ImageIcon(img));
	}

	public void setX(int posX) {
		this.x = posX;
		label.setBounds(x, 400, 50, 200);
	}

	public int getX() {
		return x;
	}

	public JLabel getLabel() {
		return label;
	}
}
