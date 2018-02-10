package ca.mcgill.ecse223.tileo.application;

import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.tileo.view.DesignModeChoicePage;
import ca.mcgill.ecse223.tileo.view.DesignModePage;
import ca.mcgill.ecse223.tileo.view.PlayModePage;
import ca.mcgill.ecse223.tileo.view.StartMenu;

public class TileOApplication {
	private static TileO tileo;
	private static String filename = "data.tileo";	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new StartMenu();
	        }
	    });
	        
	}

	public static TileO getTileO() {
		if (tileo == null) {
			// load model
			tileo = load();
		}
		return tileo;
	}
	
	
	public static void save() {
		PersistenceObjectStream.serialize(tileo);
	}
	
	public static TileO load() {
		PersistenceObjectStream.setFilename(filename);
		tileo = (TileO) PersistenceObjectStream.deserialize();

		if (tileo == null) {
			tileo = new TileO();
		}
		else {
			for (int i=0;i<tileo.getGames().size();i++){
				Game currentGame = tileo.getGame(i);
			}
		}
		return tileo;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
		
}
