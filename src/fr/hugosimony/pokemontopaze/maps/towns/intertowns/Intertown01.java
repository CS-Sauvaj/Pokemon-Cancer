package fr.hugosimony.pokemontopaze.maps.towns.intertowns;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import fr.hugosimony.pokemontopaze.Const;
import fr.hugosimony.pokemontopaze.Game;
import fr.hugosimony.pokemontopaze.Main;
import fr.hugosimony.pokemontopaze.Variables;
import fr.hugosimony.pokemontopaze.listeners.DeplacementDispatcher;
import fr.hugosimony.pokemontopaze.listeners.InteractDispatcher;
import fr.hugosimony.pokemontopaze.listeners.MenuXDispatcher;
import fr.hugosimony.pokemontopaze.listeners.TextMenuDispatcher;
import fr.hugosimony.pokemontopaze.maps.Deplacement;
import fr.hugosimony.pokemontopaze.maps.Direction;
import fr.hugosimony.pokemontopaze.maps.Map;
import fr.hugosimony.pokemontopaze.maps.Places;
import fr.hugosimony.pokemontopaze.maps.pnj.Pnj;
import fr.hugosimony.pokemontopaze.maps.roads.Road01;
import fr.hugosimony.pokemontopaze.maps.towns.Selenia;
import fr.hugosimony.pokemontopaze.sounds.Sounds;
import fr.hugosimony.pokemontopaze.transitions.TransitionSimple;
import fr.hugosimony.pokemontopaze.utils.IntTuple;

public class Intertown01 extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private Clip clipSeleniaTheme;
	
	private IntTuple toRoad1;
	private IntTuple toSelenia;
	
	private Pnj intertownPnj01;
	
	public Intertown01(Game game, int locationX, int locationY, Direction direction, int mapLocationX, int mapLocationY) {
		this.game = game;
		game.intertown01 = this;
		
		setClickableTiles();
		setWalls();
		setPnjs();
		toRoad1 = new IntTuple(3264, 2794);
		toSelenia = new IntTuple(3264, 3178);
		
		setLayout(null);
		setBackground(new Color(0, 0, 0));
		
		//****************************************************************************************************
		// Music
		
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(Const.themeSelenia);
			clipSeleniaTheme = AudioSystem.getClip();
			clipSeleniaTheme.open(audioInput);
			Main.actualClip = clipSeleniaTheme;
			Main.refreshVolume();
			clipSeleniaTheme.start();
			clipSeleniaTheme.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
		
		//****************************************************************************************************
	    // Listener
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new DeplacementDispatcher(game, this));
		manager.addKeyEventDispatcher(new InteractDispatcher(game, this));
		manager.addKeyEventDispatcher(new MenuXDispatcher(game, this));
		manager.addKeyEventDispatcher(new TextMenuDispatcher(game, this));
		
		//****************************************************************************************************
	    // Map
		
		Variables.POSITION_Place = Places.SELENIA;
		game.map = new Map(3, 0);
		game.map.setLocation(mapLocationX, mapLocationY);
		game.map.setSize(10000,10000);
		
		add(game.map);
		
		game.actualPanel = this;
		game.firstMove = false;
		game.deplacement = new Deplacement(game, locationX, locationY, direction, mapLocationX, mapLocationY);
		
		game.deplacement.setSprites(game.deplacement.direction, game.deplacement.hero, false);
		
	}
	
	private void setClickableTiles() {
		game.clickableTiles = new ArrayList<IntTuple>();
		
		game.clickableTiles.add(new IntTuple(3200, 2922)); // HOST
		game.clickableTiles.add(new IntTuple(3328, 2858)); // SIGN
	}

	private void setWalls() {
		game.walls = new ArrayList<IntTuple>();
		
		game.walls.add(new IntTuple(3232, 3146));
		game.walls.add(new IntTuple(3200, 3114));
		game.walls.add(new IntTuple(3200, 3082));
		game.walls.add(new IntTuple(3200, 3050));
		game.walls.add(new IntTuple(3200, 3018));
		game.walls.add(new IntTuple(3200, 2986));
		game.walls.add(new IntTuple(3200, 2954));
		game.walls.add(new IntTuple(3200, 2922));
		game.walls.add(new IntTuple(3200, 2890));
		game.walls.add(new IntTuple(3200, 2858));
		game.walls.add(new IntTuple(3232, 2826));
		game.walls.add(new IntTuple(3296, 2826));
		game.walls.add(new IntTuple(3328, 2858));
		game.walls.add(new IntTuple(3360, 2890));
		game.walls.add(new IntTuple(3360, 2922));
		game.walls.add(new IntTuple(3360, 2954));
		game.walls.add(new IntTuple(3360, 2986));
		game.walls.add(new IntTuple(3360, 3018));
		game.walls.add(new IntTuple(3328, 3018));
		game.walls.add(new IntTuple(3328, 3050));
		game.walls.add(new IntTuple(3328, 3082));
		game.walls.add(new IntTuple(3328, 3114));
		game.walls.add(new IntTuple(3296, 3146));

	}
	
	private void setPnjs() {
		for(Pnj pnj : game.pnjs)
			pnj.clearIA();
		game.pnjs = new ArrayList<Pnj>();
		
		intertownPnj01 = new Pnj(game, "sellerboy", Direction.RIGHT, 0, 3168, 2922, false, false, null, null, false, false);
		intertownPnj01.setVisible(true);
		intertownPnj01.setSize(35, 50);
		intertownPnj01.setLocation(3168, 2922);
		game.pnjs.add(intertownPnj01);
	}
	
	public String getInteractMessage(IntTuple tuple) {
		 String text = "";
		 if(IntTuple.getPosition(game.clickableTiles, tuple) == 1) {
			 text = "Bonjour != Faites attention car des Pokémons peuvent vous attaquer\n"
			 		+ "sur la Route 01.= Bonne journée !";
		 }
		 else if(IntTuple.getPosition(game.clickableTiles, tuple) == 2) {
			 text = "Nord : Route 01.=\nSud : Sélénia.";
		 }
		 return text;
	}
	
	public boolean checkMapChange() {
		
		if(isVisible()){
			
			if(game.deplacement.getLookingTile().equals(toRoad1) || game.deplacement.getLookingTile().equals(toSelenia)) {
				 game.deplacement.hero.setVisible(false);
				 setVisible(false);
				 if(game.deplacement.getLookingTile().equals(toRoad1) || game.deplacement.getLookingTile().equals(toSelenia))
					 Sounds.playSound(Const.soundLeaveHouse);
				 Main.actualClip.close();
				 if(game.deplacement.getLookingTile().equals(toRoad1)){
					 new TransitionSimple(game, game.gamePanel, new Road01(game, 4064, 1802, Direction.UP, -3691, -1544));
				 }
				 else if(game.deplacement.getLookingTile().equals(toSelenia)){
					 new TransitionSimple(game, game.gamePanel, new Selenia(game, 2016, 1322, Direction.DOWN, -1643, -1064));
				 }
				 else {
					 
				 }
				 return true;
			}
		}
		return false;
	}
	
	
}
