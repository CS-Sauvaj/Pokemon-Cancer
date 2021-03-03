package fr.hugosimony.pokemoncancer.maps.items;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.hugosimony.pokemoncancer.Const;

public class GroundItem extends JPanel {
	private static final long serialVersionUID = 1L;

	private String type;
	
	public GroundItem(String type) {
		this.type = type;
		setOpaque(false);
		setLayout(null);
		setVisible(true);
		setSize(32, 50);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(type.equals("pokeball"))
			g.drawImage(Const.groundPokeBall.getImage(), 0, 0, null);
	}
}
