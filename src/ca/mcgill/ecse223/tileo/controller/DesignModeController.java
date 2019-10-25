package ca.mcgill.ecse223.tileo.controller;

import java.util.List;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.AdditionalMoveActionCard;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.DeactivateActionTileActionCard;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.LoseTurnActionCard;
import ca.mcgill.ecse223.tileo.model.MoveOtherPlayerActionCard;
import ca.mcgill.ecse223.tileo.model.MoveWinTileActionCard;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RevealTileActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.SwapPositionActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;
import ca.mcgill.ecse223.tileo.model.WinTileHintActionCard;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.model.Player.Color;

public class DesignModeController {

	public DesignModeController(){

	}

	public boolean gameHasWinTile(){
		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();

		return currentGame.hasWinTile();
	}


	public void createGame(){
		TileO tileo = TileOApplication.getTileO();
		Game game = new Game(32);
		game.setMode(Mode.DESIGN);
		tileo.addGame(game);
		System.out.println("Game Mode: " + game.getMode().name());
		tileo.setCurrentGame(game);
	}

	public List<Player> getPlayers(){
		TileO tileo = TileOApplication.getTileO();
		List<Player> allPlayers = tileo.getCurrentGame().getPlayers();
		return allPlayers;
	}

	public List<Tile> getTiles(){
		return TileOApplication.getTileO().getCurrentGame().getTiles();
	}

	public List<Connection> getConnections(){
		return TileOApplication.getTileO().getCurrentGame().getConnections();
	}

	public List<Game> getGames(){
		TileO tileo = TileOApplication.getTileO();
		List<Game> allGames = tileo.getGames();
		return allGames;
	}

	public Game getCurrentGame(){
		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		return currentGame;
	}

	public void setCurrentGame(Game game){
		TileO tileo = TileOApplication.getTileO();
		tileo.setCurrentGame(game);
	}

	public void save(){
		TileOApplication.save();
	}

	public Game load(Game selectedGame) throws InvalidInputException{
		TileO tileo = TileOApplication.getTileO();
		if (selectedGame == null){
			throw new InvalidInputException("Please select a game to load!");
		}

		try{
			tileo.setCurrentGame(selectedGame);
			return tileo.getCurrentGame();
		}
		catch(RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}

	}

	public String getMode(){
		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		Mode currentMode = currentGame.getMode();
		String mode = currentMode.name();
		return mode;
	}


	public void createPlayer(int numOfPlayers) throws InvalidInputException{

		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();

		try{
            for (Player aPlayer : currentGame.getPlayers()){
                aPlayer.delete();
            }

			for (int i=0;i<numOfPlayers;i++){
				Player newPlayer = currentGame.addPlayer(i+1);
				if (i==1){
					newPlayer.setColor(Color.BLUE);
				}
				else if (i==2){
					newPlayer.setColor(Color.GREEN);
				}
				else if (i==3){
					newPlayer.setColor(Color.YELLOW);
				}
			}
		}

		catch (RuntimeException e){

			throw new InvalidInputException(e.getMessage());
		}

	}

	public void createTile(int x, int y) throws InvalidInputException{

	    if ( (x>=14) || (y>=14) ){
			throw new InvalidInputException("x/y coordinate cannot exceed 13!");
		}


		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		List <Tile> allAddedTiles = currentGame.getTiles();


		if (allAddedTiles.size()>0){
			for (int i=0;i<allAddedTiles.size();i++){
				if ( ( x == allAddedTiles.get(i).getX() ) && (y == allAddedTiles.get(i).getY() ) ){
					throw new InvalidInputException("The tile already exists!");
				}
			}
		}

		try{
			NormalTile newTile = new NormalTile(x,y,currentGame);
		}

		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}


	public void connectTile(Tile tile1, Tile tile2) throws InvalidInputException{


		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();

		// Check if two tiles are adjacent
		boolean ifAdjacent = false;

		if( (tile1.getX()-tile2.getX() == 0) && (Math.abs(tile1.getY()-tile2.getY()) == 1) ){
			ifAdjacent = true;
		}

		else if( (tile1.getY()-tile2.getY() == 0) && (Math.abs(tile1.getX()-tile2.getX()) == 1) ){
			ifAdjacent = true;
		}

		if(ifAdjacent == false){
			throw new InvalidInputException("Tiles must be adjacent!");
		}

		// Check if the same connection has been added before
		List<Connection> connections = tile1.getConnections();

		if (connections.size()>0){
			for (int i=0;i<connections.size();i++){
				List<Tile> connectedTiles = connections.get(i).getTiles();
				for (int n=0;n<connectedTiles.size();n++){
					if ( ( connectedTiles.get(n).getX()==tile2.getX() ) && ( connectedTiles.get(n).getY()==tile2.getY() ) ){
						throw new InvalidInputException("This connection has been added before");
					}
				}
			}
		}


		currentGame.addNewConnection(tile1, tile2);

	}

	public void disconnectTiles(Connection connection) throws InvalidInputException{

		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		try{
			currentGame.deleteConnection(connection);
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}

	public void setActionTile(Tile tile, int inactivityPeriod) throws InvalidInputException{
		try{
			/* Alternatively:
			   TileO tileo = TileOApplication.getTileO();
			   Game currentGame = tileo.getCurrentGame();
			   ActionTile newTile = new ActionTile(tile.getX(),tile.getY(),currentGame,0);
			   tile.delete(); */

			TileO tileo = TileOApplication.getTileO();
			Game currentGame = tileo.getCurrentGame();
			if ( (currentGame.hasWinTile()==true)&& (currentGame.getWinTile().getX()==tile.getX()) &&  (currentGame.getWinTile().getY()==tile.getY()) ){
				throw new InvalidInputException("This win tile cannot be set to an action tile");
			}

			ActionTile newTile = new ActionTile(tile.getX(),tile.getY(),currentGame,inactivityPeriod);
			tile.delete();


		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}


	public void setWinTile(Tile tile) throws InvalidInputException{

		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();

	    try{
	    	// The if-else statements below checking if the game has win tile can be deleted
	    	// As we now do not allow the user to click the set win tile button more than once in each design

	    	if ( currentGame.hasWinTile() == true ){

	    		if ( (tile.getX()==currentGame.getWinTile().getX()) && (tile.getY()==currentGame.getWinTile().getY())){
	    			throw new InvalidInputException("This tile has been set to win tile before!");
	    		}

	    		NormalTile normalTile = new NormalTile(currentGame.getWinTile().getX(),currentGame.getWinTile().getY(),currentGame);
	    		WinTile newWinTile = new WinTile(tile.getX(),tile.getY(),currentGame);
	    		currentGame.getWinTile().delete();
	    		tile.delete();
	    		currentGame.setWinTile(newWinTile);
	    	}

	    	else{
	    		WinTile winTile = new WinTile(tile.getX(),tile.getY(),currentGame);
	    		currentGame.setWinTile(winTile);
	    		tile.delete();
	    	}
	    }

	    catch (RuntimeException e){
	    	throw new InvalidInputException(e.getMessage());
	    }
	}



	public void setStartingPosition(Tile tile, int playerNum) throws InvalidInputException{

		TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();

		if(playerNum > currentGame.getPlayers().size()){
			throw new InvalidInputException("You cannot set the starting position for a player that doesn't exist!");
		}

		try{
			Player player = currentGame.getPlayer(playerNum-1);
			player.setStartingTile(tile);
		}

		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}

	}

	public void deleteTile(Tile tileToDelete) throws InvalidInputException{

		TileO tileo = TileOApplication.getTileO();

		Game currentGame = tileo.getCurrentGame();

		if (currentGame.hasWinTile()==true){
			if ( ( currentGame.getWinTile().getX() == tileToDelete.getX() )  && ( currentGame.getWinTile().getY() == tileToDelete.getY() ) ){
				throw new InvalidInputException( "This tile is a win tile. Cannot be deleted!" );
			}
		}

		try{
			tileToDelete.delete();
		}

		catch(RuntimeException e){

			throw new InvalidInputException(e.getMessage());

		}
	}

	public void initDeck(int rollDie, int connectTiles,int removeConnection,int teleport,int loseTurn,int additionalMove, int deactivateActionTile, int moveOtherPlayer, int moveWinTile, int revealTile, int swapPosition)throws InvalidInputException{

		 String error = "";

		 TileO tileo =TileOApplication.getTileO();
		 Game currentGame = tileo.getCurrentGame();
		 Deck deck = currentGame.getDeck();

		 if(rollDie+connectTiles+removeConnection+teleport+loseTurn+additionalMove+deactivateActionTile+moveOtherPlayer+moveWinTile+revealTile+swapPosition!=currentGame.NumberOfActionCards){
			 throw new InvalidInputException("The total number of all cards should be "+currentGame.NumberOfActionCards);
		 }


		 if (deck.getCards().size()==currentGame.NumberOfActionCards){
			for (int i=0;i<32;i++){
			  deck.getCards().get(0).delete();
			}
		 }



		 try{

			 for(int i=0; i<rollDie; i++){
				 RollDieActionCard rollDieActionCard = new RollDieActionCard("Take an extra turn", deck);
			 }

			 for(int i=0; i<connectTiles; i++){
				 ConnectTilesActionCard connectTilesActionCard = new ConnectTilesActionCard ("Use a connection piece to connect tiles",deck);
			 }

			 for(int i=0; i<removeConnection; i++){
				 RemoveConnectionActionCard removeConnectionActionCard = new  RemoveConnectionActionCard ("Remove a connection piece from the board and place it in the pile of spare connection piece",deck);
			 }

			 for(int i=0; i<teleport; i++){
				 TeleportActionCard teleportActionCard = new TeleportActionCard ("Move your playing piece to an arbitrary tile that is not your current ",deck);
			 }

			 for(int i=0; i<loseTurn; i++){
				 LoseTurnActionCard loseTurnACtionCard = new LoseTurnActionCard("Lose your next turn",deck);
			 }

			 for(int i=0; i<additionalMove; i++){
				 AdditionalMoveActionCard additionalMoveActionCard = new AdditionalMoveActionCard("Choose a number between 1 and 6 and move exactly that number of tiles on the board",deck);
			 }
			 for(int i=0; i<deactivateActionTile; i++){
				 DeactivateActionTileActionCard deactivateActionTileActionCard = new DeactivateActionTileActionCard("Set all active action tiles to inactive for their respective inactivity periods",deck);
			 }

			 for(int i=0; i<moveOtherPlayer; i++){
				 MoveOtherPlayerActionCard moveOtherPlayerActionCard = new MoveOtherPlayerActionCard("Move one of his opponent's piece to an arbitrary tile of his choice",deck);
			 }

			 for(int i=0; i<moveWinTile; i++){
				 MoveWinTileActionCard moveWinTileActionCard = new MoveWinTileActionCard("Move the win tile to a tile of his/her choice",deck);
			 }

			 for(int i=0; i<revealTile; i++){
				 RevealTileActionCard RevealTileActionCard = new RevealTileActionCard("Select a tile on the board and reveal its type to all the players in the game",deck);
			 }

			 for(int i=0; i<swapPosition; i++){
				 SwapPositionActionCard SwapPositionActionTile = new SwapPositionActionCard("Swap his position on the board with that of another player",deck);
			 }
		 }

		 catch (RuntimeException e){
			 throw new InvalidInputException(e.getMessage());
		 }

	}

	public void setGameMode(){
		TileO tileo =TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		currentGame.setMode(Mode.DESIGN);
	}




}
