/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.controller;
import java.util.*;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 3 "../../../../../PlayModeController.ump"
public class PlayModeController
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayModeController Attributes
  private int numOfMoves;
  private String tileHint;

  //PlayModeController State Machines
  enum ControllerMode { Ready, Roll, Move, ActionCard, Won }
  private ControllerMode controllerMode;

  //PlayModeController Associations
  private List<Tile> possibleMoves;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayModeController()
  {
    numOfMoves = 0;
    tileHint = "";
    possibleMoves = new ArrayList<Tile>();
    setControllerMode(ControllerMode.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumOfMoves(int aNumOfMoves)
  {
    boolean wasSet = false;
    numOfMoves = aNumOfMoves;
    wasSet = true;
    return wasSet;
  }

  public boolean setTileHint(String aTileHint)
  {
    boolean wasSet = false;
    tileHint = aTileHint;
    wasSet = true;
    return wasSet;
  }

  public int getNumOfMoves()
  {
    return numOfMoves;
  }

  public String getTileHint()
  {
    return tileHint;
  }

  public String getControllerModeFullName()
  {
    String answer = controllerMode.toString();
    return answer;
  }

  public ControllerMode getControllerMode()
  {
    return controllerMode;
  }

  public boolean startGame() throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case Ready:
        // line 6 "../../../../../PlayModeController.ump"
        doStartGame();
        setControllerMode(ControllerMode.Roll);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean load(Game selectedGame) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case Ready:
        if (isInGameMode(selectedGame))
        {
        // line 9 "../../../../../PlayModeController.ump"
          doLoad(selectedGame);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        if (isInWonMode(selectedGame))
        {
        // line 12 "../../../../../PlayModeController.ump"
          doLoad(selectedGame);
          setControllerMode(ControllerMode.Won);
          wasEventProcessed = true;
          break;
        }
        if (isNotInGameOrWonMode(selectedGame))
        {
        // line 15 "../../../../../PlayModeController.ump"
          doLoad(selectedGame);
          setControllerMode(ControllerMode.ActionCard);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean rollDie()
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case Roll:
        // line 20 "../../../../../PlayModeController.ump"
        possibleMoves = doRollDie();
        setControllerMode(ControllerMode.Move);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean land(Tile tile)
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case Move:
        if (isNormalTile(tile))
        {
        // line 25 "../../../../../PlayModeController.ump"
          doLand(tile);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        if (isWinTile(tile))
        {
        // line 28 "../../../../../PlayModeController.ump"
          doLand(tile);
          setControllerMode(ControllerMode.Won);
          wasEventProcessed = true;
          break;
        }
        if (isActionTile(tile))
        {
        // line 31 "../../../../../PlayModeController.ump"
          doLand(tile);
          setControllerMode(ControllerMode.ActionCard);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playRollDieActionCard() throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isRollDieActionCard())
        {
        // line 36 "../../../../../PlayModeController.ump"
          possibleMoves = doPlayRollDieActionCard();
          setControllerMode(ControllerMode.Move);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playConnectTilesActionCard(Tile tile1,Tile tile2) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isConnectTilesActionCard())
        {
        // line 40 "../../../../../PlayModeController.ump"
          doPlayConnectTilesActionCard(tile1, tile2);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playRemoveConnectionActionCard(Connection c) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isRemoveConnectionActionCard())
        {
        // line 44 "../../../../../PlayModeController.ump"
          doPlayRemoveConnectionActionCard(c);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playTeleportActionCard(Tile tile) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isTeleportAndNormalTile(tile))
        {
        // line 48 "../../../../../PlayModeController.ump"
          doPlayTeleportActionCard(tile);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        if (isTeleportAndWinTile(tile))
        {
        // line 51 "../../../../../PlayModeController.ump"
          doPlayTeleportActionCard(tile);
          setControllerMode(ControllerMode.Won);
          wasEventProcessed = true;
          break;
        }
        if (isTeleportAndActionTile(tile))
        {
        // line 54 "../../../../../PlayModeController.ump"
          doPlayTeleportActionCard(tile);
          setControllerMode(ControllerMode.ActionCard);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playLoseTurnActionCard() throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isLoseTurnActionCard())
        {
        // line 57 "../../../../../PlayModeController.ump"
          doPlayLoseTurnActionCard();
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playMoveWinTileActionCard(Tile tile) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isMoveWinTileActionCard())
        {
        // line 60 "../../../../../PlayModeController.ump"
          doPlayMoveWinTileActionCard(tile);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playMoveOtherPlayerActionCard(Tile tile,int playerNum) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isMoveOtherPlayerActionCard())
        {
        // line 63 "../../../../../PlayModeController.ump"
          doPlayMoveOtherPlayerActionCard(tile,  playerNum);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playSwapPositionActionCard(int num) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isSwapPositionActionCard())
        {
        // line 66 "../../../../../PlayModeController.ump"
          doPlaySwapPositionActionCard(num);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playAdditionalMoveActionCard(int numOfMoves) throws InvalidInputException
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isAdditionalMoveActionCard())
        {
        // line 69 "../../../../../PlayModeController.ump"
          doPlayAdditionalMoveActionCard(numOfMoves);
          setControllerMode(ControllerMode.Move);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

//  public boolean playWinTileHintActionCard(Tile tile)
//  {
//    boolean wasEventProcessed = false;
//    
//    ControllerMode aControllerMode = controllerMode;
//    switch (aControllerMode)
//    {
//      case ActionCard:
//        if (isWinTileHintActionCard())
//        {
//        // line 72 "../../../../../PlayModeController.ump"
//          doPlayWinTileHintActionCard(tile);
//          setControllerMode(ControllerMode.Roll);
//          wasEventProcessed = true;
//          break;
//        }
//        break;
//      default:
//        // Other states do respond to this event
//    }
//
//    return wasEventProcessed;
//  }

  public boolean playDeactivateActionTileActionCard()
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isDeactivateActionTileActionCard())
        {
        // line 75 "../../../../../PlayModeController.ump"
          doPlayDeactivateActionTileActionCard();
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playRevealTileActionCard(Tile tile)
  {
    boolean wasEventProcessed = false;
    
    ControllerMode aControllerMode = controllerMode;
    switch (aControllerMode)
    {
      case ActionCard:
        if (isRevealTileActionCard())
        {
        // line 78 "../../../../../PlayModeController.ump"
          doPlayRevealTileActionCard(tile);
          setControllerMode(ControllerMode.Roll);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setControllerMode(ControllerMode aControllerMode)
  {
    controllerMode = aControllerMode;
  }

  public Tile getPossibleMove(int index)
  {
    Tile aPossibleMove = possibleMoves.get(index);
    return aPossibleMove;
  }

  public List<Tile> getPossibleMoves()
  {
    List<Tile> newPossibleMoves = Collections.unmodifiableList(possibleMoves);
    return newPossibleMoves;
  }

  public int numberOfPossibleMoves()
  {
    int number = possibleMoves.size();
    return number;
  }

  public boolean hasPossibleMoves()
  {
    boolean has = possibleMoves.size() > 0;
    return has;
  }

  public int indexOfPossibleMove(Tile aPossibleMove)
  {
    int index = possibleMoves.indexOf(aPossibleMove);
    return index;
  }

  public static int minimumNumberOfPossibleMoves()
  {
    return 0;
  }

  public boolean addPossibleMove(Tile aPossibleMove)
  {
    boolean wasAdded = false;
    if (possibleMoves.contains(aPossibleMove)) { return false; }
    possibleMoves.add(aPossibleMove);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePossibleMove(Tile aPossibleMove)
  {
    boolean wasRemoved = false;
    if (possibleMoves.contains(aPossibleMove))
    {
      possibleMoves.remove(aPossibleMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addPossibleMoveAt(Tile aPossibleMove, int index)
  {  
    boolean wasAdded = false;
    if(addPossibleMove(aPossibleMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPossibleMoves()) { index = numberOfPossibleMoves() - 1; }
      possibleMoves.remove(aPossibleMove);
      possibleMoves.add(index, aPossibleMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePossibleMoveAt(Tile aPossibleMove, int index)
  {
    boolean wasAdded = false;
    if(possibleMoves.contains(aPossibleMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPossibleMoves()) { index = numberOfPossibleMoves() - 1; }
      possibleMoves.remove(aPossibleMove);
      possibleMoves.add(index, aPossibleMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPossibleMoveAt(aPossibleMove, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    possibleMoves.clear();
  }
  
  

  // line 90 "../../../../../PlayModeController.ump"
   private boolean isNormalTile(Tile aTile){
    if ( (aTile instanceof ActionTile) && (((ActionTile) aTile).getActionTileStatusFullName().equals("Inactive"))){
		return true;
	  }
	  return aTile instanceof NormalTile;
  }

  // line 97 "../../../../../PlayModeController.ump"
   private boolean isWinTile(Tile aTile){
    return aTile instanceof WinTile;
  }

  // line 101 "../../../../../PlayModeController.ump"
   private boolean isActionTile(Tile aTile){
    boolean isActive = false;
	  if (((ActionTile) aTile).getActionTileStatusFullName().equals("Active")){
		  isActive = true;
	  }
	  return isActive && (aTile instanceof ActionTile);
  }

  // line 109 "../../../../../PlayModeController.ump"
   private boolean isRollDieActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof RollDieActionCard;
  }

  // line 116 "../../../../../PlayModeController.ump"
   private boolean isLoseTurnActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof LoseTurnActionCard;
  }

  // line 123 "../../../../../PlayModeController.ump"
   private boolean isConnectTilesActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof ConnectTilesActionCard;
  }

  // line 130 "../../../../../PlayModeController.ump"
   private boolean isRemoveConnectionActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof RemoveConnectionActionCard;
  }

  // line 137 "../../../../../PlayModeController.ump"
   private boolean isTeleportAndNormalTile(Tile tile){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  boolean isTeleport = deck.getCurrentCard() instanceof TeleportActionCard;
	  boolean isNormalTile = isNormalTile(tile);
	  
	  return isTeleport&&isNormalTile;
  }

  // line 147 "../../../../../PlayModeController.ump"
   private boolean isTeleportAndActionTile(Tile tile){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  boolean isTeleport = deck.getCurrentCard() instanceof TeleportActionCard;
	  boolean isActionTile = isActionTile(tile);
	  
	  return isTeleport&&isActionTile;
  }

  // line 157 "../../../../../PlayModeController.ump"
   private boolean isTeleportAndWinTile(Tile tile){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  boolean isTeleport = deck.getCurrentCard() instanceof TeleportActionCard;
	  boolean isWinTile = isWinTile(tile);
	  
	  return isTeleport&&isWinTile;
  }

  // line 167 "../../../../../PlayModeController.ump"
   private boolean isMoveWinTileActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof MoveWinTileActionCard;
  }

  // line 174 "../../../../../PlayModeController.ump"
   private boolean isMoveOtherPlayerActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof MoveOtherPlayerActionCard;
  }

  // line 181 "../../../../../PlayModeController.ump"
   private boolean isSwapPositionActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof SwapPositionActionCard;
  }

  // line 188 "../../../../../PlayModeController.ump"
   private boolean isAdditionalMoveActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof AdditionalMoveActionCard;
  }

  // line 195 "../../../../../PlayModeController.ump"
   private boolean isWinTileHintActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof WinTileHintActionCard;
  }

  // line 202 "../../../../../PlayModeController.ump"
   private boolean isDeactivateActionTileActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof DeactivateActionTileActionCard;
  }

  // line 209 "../../../../../PlayModeController.ump"
   private boolean isRevealTileActionCard(){
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  return deck.getCurrentCard() instanceof RevealTileActionCard;
  }

  // line 217 "../../../../../PlayModeController.ump"
   public void setCurrentGame(Game game){
    TileO tileo = TileOApplication.getTileO();
		tileo.setCurrentGame(game);
  }

  // line 223 "../../../../../PlayModeController.ump"
   public List<Game> getGames(){
    TileO tileo = TileOApplication.getTileO();
		List<Game> allGames = tileo.getGames();
		return allGames;
  }

  // line 230 "../../../../../PlayModeController.ump"
   public List<Tile> getTiles(){
    return TileOApplication.getTileO().getCurrentGame().getTiles();
  }

  // line 235 "../../../../../PlayModeController.ump"
   public List<Connection> getConnections(){
    return TileOApplication.getTileO().getCurrentGame().getConnections();
  }

  // line 240 "../../../../../PlayModeController.ump"
   public List<Tile> getConnectedTiles(Connection connection){
    return connection.getTiles();
  }

  // line 245 "../../../../../PlayModeController.ump"
   public List<Player> getPlayers(){
    TileO tileo = TileOApplication.getTileO();
		List<Player> allPlayers = tileo.getCurrentGame().getPlayers();
		return allPlayers;
  }

  // line 252 "../../../../../PlayModeController.ump"
   public int getCurrentPlayerNum(){
    TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		return currentGame.getCurrentPlayer().getNumber();
  }

  // line 259 "../../../../../PlayModeController.ump"
   public Game getCurrentGame(){
    TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		return currentGame;
  }

  // line 266 "../../../../../PlayModeController.ump"
   public String getGameMode(){
    TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		String mode = currentGame.getMode().name();
		return mode;
  }

  // line 274 "../../../../../PlayModeController.ump"
   public void save(){
    TileOApplication.save();
  }
   
   private void cloneGame() {

	    TileO tileO = TileOApplication.getTileO();
	    Game cloned = tileO.getCurrentGame().clone();
	    tileO.addGame(cloned);
	    tileO.setCurrentGame(cloned);

	  }

  // line 279 "../../../../../PlayModeController.ump"
   private void doStartGame() throws InvalidInputException{
    String error = "";
		boolean allHaveStartingTiles = true;
		cloneGame();
		Game currentGame = TileOApplication.getTileO().getCurrentGame();
		
		
//		if (currentGame.getDeck().getCards().size()!=currentGame.NumberOfActionCards){
//			error = "Invalid number of action cards!";
//		}

		if (currentGame.hasTiles()==false){
			error = error + "This game does not have any tiles!";
		}

		else if (currentGame.hasWinTile()==false){
			error = error + "This game does not have a win tile!";

		}

		if (currentGame.hasConnections()==false){
			error = error + "This game does not have any connection pieces!";
		}

		if (currentGame.hasPlayers()==false){
			error = error + "The game does not have any players!";
		}

		else{
			for (Player player : currentGame.getPlayers()){
				if (player.hasStartingTile()==false){
					allHaveStartingTiles = false;
					error = error + "Every player must have a starting position!";
					break;
				}
			}
			
			if (allHaveStartingTiles == true){
				for (Player player : currentGame.getPlayers()){
					if (player.getStartingTile().hasConnections()==false){
						error = error + "There must be at least one connection piece connected to each player's starting position";
						break;
					}
				}


				for (Player player : currentGame.getPlayers()){
					if(  (currentGame.hasWinTile()==true) && ( player.getStartingTile().getX() == currentGame.getWinTile().getX() ) && ( player.getStartingTile().getY() == currentGame.getWinTile().getY() ) ){
						error = error + "The player's starting tile cannot be the win tile!";
						break;
					}
				}
			}
		}

		if (error.length()>0) {
			throw new InvalidInputException(error);
		}


		TileO tileo = TileOApplication.getTileO();
		tileo.setCurrentGame(currentGame);

		List<Player> allPlayers = currentGame.getPlayers();
		List<Tile> allTiles = currentGame.getTiles();

		for (Tile aTile : allTiles){
			aTile.setHasBeenVisited(false);
			if (aTile instanceof ActionTile){
				((ActionTile)aTile).activate();
			}
		}

		currentGame.setCurrentPlayer(allPlayers.get(0));

		for (Player aPlayer : allPlayers){
			aPlayer.getStartingTile().setHasBeenVisited(true);
			aPlayer.setCurrentTile(aPlayer.getStartingTile());
		}
		currentGame.setMode(Mode.GAME);
		currentGame.setCurrentConnectionPieces(32);

		Deck deck = currentGame.getDeck();
//		deck.shuffle();
		deck.setCurrentCard(deck.getCards().get(0));
		
  }

  // line 366 "../../../../../PlayModeController.ump"
   private List<Tile> doRollDie(){
    TileO tileo = TileOApplication.getTileO();
	   Game currentGame = tileo.getCurrentGame();
	   numOfMoves = currentGame.getDie().roll();
	   List<Tile> possibleDestinations = currentGame.takeTurn(numOfMoves);
		
	   return possibleDestinations;
		
		
		/*
		Die die = currentGame.getDie();
		int numOfMoves = die.roll();
		List<Tile> possibleDestinations = currentGame.takeTurn();
		return possibleDestinations;*/
  }

  // line 383 "../../../../../PlayModeController.ump"
   private int numberRolled(){
    return numOfMoves;
  }

  // line 388 "../../../../../PlayModeController.ump"
   private void doLand(Tile tile){
    TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();

		boolean contain = false;
		
		
		/* Which one???
		for ( Tile aTile: currentGame.getTiles() ) {
			if ( currentGame.getTiles().contains(tile)){
				contain = true;
			}
		}*/
		
		for ( Tile aTile: currentGame.getTiles() ) {
			if (aTile instanceof ActionTile){
				System.out.println(((ActionTile) aTile).getActionTileStatusFullName());
				System.out.println(((ActionTile) aTile).getTurnsUntilActive());
				
			}
			if ( (aTile.getX()==tile.getX()) && (aTile.getY()==tile.getY()) ){
				contain = true;
			}
		}
		
		System.out.println("-------------------------------");

		if (contain==false){
			throw new RuntimeException("Tile does not exist!");
		}

		tile.land();
  }

  // line 416 "../../../../../PlayModeController.ump"
   private List<Tile> doPlayRollDieActionCard() throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		Deck deck = currentGame.getDeck();
		ActionCard currentCard = deck.getCurrentCard();
		List<Tile> possibleDestinations = new ArrayList<Tile>();
		

		RollDieActionCard card = (RollDieActionCard) deck.getCurrentCard();
		numOfMoves = currentGame.getDie().roll();
		possibleDestinations = card.play(numOfMoves);
		
		
		int currentInd = deck.indexOfCard(currentCard);
		int totalCard = deck.getCards().size();
		
		if ( (currentInd+1) == totalCard){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard(deck.getCard(currentInd+1));
		}
		
		currentGame.setMode(Mode.GAME);
		
		return possibleDestinations;
  }

  // line 446 "../../../../../PlayModeController.ump"
   private void doPlayConnectTilesActionCard(Tile tile1, Tile tile2) throws InvalidInputException{
    TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		List<Tile> allTiles = currentGame.getTiles();
		
		int spareConnections = currentGame.getCurrentConnectionPieces();
		
		//Check if both tiles exist in this game
		boolean tile1Exists = false;
		boolean tile2Exists = false;
		
		if (tile1==null || tile2==null){
			throw new InvalidInputException("You must select two tiles before clicking the Connect Button!");
		}

		for ( Tile aTile: allTiles ) {
			if ( (aTile.getX()==tile1.getX()) && (aTile.getY()==tile1.getY()) ){
				tile1Exists = true;
				break;
			}
		}
		
		for ( Tile aTile: allTiles ) {
			if ( (aTile.getX()==tile2.getX()) && (aTile.getY()==tile2.getY()) ){
				tile2Exists = true;
				break;
			}
		}


	
		if(tile1Exists == false || tile2Exists == false){
			throw new InvalidInputException("You must choose (a) tile(s) that exist(s) in this game!");
		}
		
		
		
		if (spareConnections==0){
			throw new InvalidInputException("Unfortunately there are no connections pieces left");
		}

		//Check if the selected tiles are adjacent tiles
		boolean ifAdjacent = false;
		
		
		if( (tile1.getX()==tile2.getX()) && (Math.abs(tile1.getY()-tile2.getY()) == 1) ){
			ifAdjacent = true;
		}
		else if( (tile1.getY()==tile2.getY()) && (Math.abs(tile1.getX()-tile2.getX()) == 1) ){
			ifAdjacent = true;
		}

		if(ifAdjacent == false){
			throw new InvalidInputException(" must be adjacent!");
		}

		Deck deck = currentGame.getDeck();
		ActionCard currentCard = deck.getCurrentCard();
	
		boolean connected = false;
		
		if (tile1.hasConnections()){
			for (Connection aConnection: tile1.getConnections()){
				if ( (aConnection.getTiles().contains(tile1)) && (aConnection.getTiles().contains(tile2)) ){
					connected = true;
				}
			}
		}

		
		ConnectTilesActionCard card = (ConnectTilesActionCard) currentCard;
		if (connected==false){
			card.play(tile1, tile2);
		}

		currentGame.determineNextPlayer();
		currentGame.updateTileStatus();

		int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();
		
		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}

		
		currentGame.setCurrentConnectionPieces(spareConnections-1);
		currentGame.setMode(Mode.GAME);
  }

  // line 540 "../../../../../PlayModeController.ump"
   private void doPlayRemoveConnectionActionCard(Connection connection) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
		Game currentGame = tileo.getCurrentGame();
		
		if (connection==null){
			throw new InvalidInputException("Please select a connection to remove");
		}
		
		if ( currentGame.getConnections().contains(connection) == false ) {
			throw new InvalidInputException("The selected connection does not exist in this game!");
		}
		
		Deck deck = currentGame.getDeck();
		ActionCard currentCard = deck.getCurrentCard();
		
		RemoveConnectionActionCard card = (RemoveConnectionActionCard) deck.getCurrentCard();
		card.play(connection);

		currentGame.determineNextPlayer();
		currentGame.updateTileStatus();

		int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		
		int spareConnections = currentGame.getCurrentConnectionPieces();
		currentGame.setCurrentConnectionPieces(spareConnections+1);
		currentGame.setMode(Mode.GAME);
  }

  // line 577 "../../../../../PlayModeController.ump"
   private void doPlayTeleportActionCard(Tile tile) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
	    Game currentGame = tileo.getCurrentGame();
	    List<Tile> allTiles = currentGame.getTiles();
	    
	    if ( !(allTiles.contains(tile)) ){
	    	throw new InvalidInputException("This tile does not exist in this game!");
	    }
	    
	    Deck deck = currentGame.getDeck();
	    ActionCard currentCard = deck.getCurrentCard();
	    int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		
		TeleportActionCard card = (TeleportActionCard) currentCard;
		card.play(tile);
  }

  // line 603 "../../../../../PlayModeController.ump"
   private void doPlayLoseTurnActionCard() throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  Deck deck = currentGame.getDeck();
	  LoseTurnActionCard currentCard = (LoseTurnActionCard)deck.getCurrentCard();
	 
	  currentCard.play(); 
	  
	  currentGame.determineNextPlayer();
	  currentGame.updateTileStatus();
	  
	  
	  int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		
		currentGame.setMode(Mode.GAME);
  }

  // line 630 "../../../../../PlayModeController.ump"
   private void doLoad(Game selectedGame) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
		if (selectedGame == null){
			throw new InvalidInputException("Please select a game to load!");
		}
		
		if (selectedGame.getMode().name().equals("DESIGN")){
			throw new InvalidInputException("Design is not finished!\n"
					+ "If you have finished the design, please press the\n"
					+ "load buttonon Design Mode Page to start the game.");
		}
		
		try{
			tileo.setCurrentGame(selectedGame);
			
		}
		catch(RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
  }

  // line 653 "../../../../../PlayModeController.ump"
   public Connection getThisConnection(Tile tile1, Tile tile2){
    for (int i=0; i<tile1.getConnections().size();i++){
			List<Tile> connectedTiles = tile1.getConnections().get(i).getTiles();
			for (int n=0; n<connectedTiles.size();n++){
				if (connectedTiles.get(n).getX()==tile2.getX() && (connectedTiles.get(n).getX()==tile2.getX())){
					return tile1.getConnections().get(i);
				}
			}
		}
		
		return null;
  }

  // line 668 "../../../../../PlayModeController.ump"
   private void doPlayMoveWinTileActionCard(Tile tile) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
	   Game currentGame = tileo.getCurrentGame();
	   List<Tile> allTiles = currentGame.getTiles();
	    
	    Deck deck = currentGame.getDeck();
	    MoveWinTileActionCard currentCard = (MoveWinTileActionCard)deck.getCurrentCard();
	    
	    if (tile==null) {
	    	throw new InvalidInputException("You must select a tile");
	    }
	    if ( !(allTiles.contains(tile)) ){
	    	throw new InvalidInputException("This tile does not exist in this game!");
	    }

	    if (isWinTile(tile)==false){
	    	try{
	    	 currentCard.play(tile);
	    	}
	    	catch(RuntimeException e){
	    		throw new InvalidInputException(e.getMessage());
	    	}
    	}
	  

	    currentGame.determineNextPlayer();
		currentGame.updateTileStatus();

		
		int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		currentGame.setMode(Mode.GAME);
  }

  // line 711 "../../../../../PlayModeController.ump"
   private void doPlayDeactivateActionTileActionCard(){
    TileO tileo = TileOApplication.getTileO();
	   Game currentGame = tileo.getCurrentGame();
	   List<Tile> allTiles = currentGame.getTiles();
	    
	    Deck deck = currentGame.getDeck();
	    DeactivateActionTileActionCard currentCard = (DeactivateActionTileActionCard)deck.getCurrentCard();
	    currentCard.play();
	   
	    currentGame.determineNextPlayer();
	    currentGame.updateTileStatus();
	   
	    
	    int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		currentGame.setMode(Mode.GAME);
  }

  // line 737 "../../../../../PlayModeController.ump"
   private void doPlayRevealTileActionCard(Tile tile){
    TileO tileo = TileOApplication.getTileO();
	    Game currentGame = tileo.getCurrentGame();
	    List<Tile> allTiles = currentGame.getTiles();
	    
	    Deck deck = currentGame.getDeck();
	    RevealTileActionCard currentCard = (RevealTileActionCard)deck.getCurrentCard();
	    
	    String tileType = currentCard.play(tile);
	    this.setTileHint(tileType);
	    
	    currentGame.determineNextPlayer();
	    currentGame.updateTileStatus();
	    
	    int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		currentGame.setMode(Mode.GAME);
  }

  // line 765 "../../../../../PlayModeController.ump"
   private void doPlayMoveOtherPlayerActionCard(Tile tile, int playerNum) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
	    Game currentGame = tileo.getCurrentGame();
	    List<Tile> allTiles = currentGame.getTiles();
	    
	    Deck deck = currentGame.getDeck();
	    MoveOtherPlayerActionCard currentCard = (MoveOtherPlayerActionCard) deck.getCurrentCard();
		
		if (tile==null){
			throw new InvalidInputException("You must select a tile before clicking the Move Other Player Button!");
		}
		else if ( !(allTiles.contains(tile)) ){
	    	throw new InvalidInputException("This tile does not exist in this game!");
	    }
	    else if(playerNum > currentGame.getPlayers().size()){
	        throw new InvalidInputException("This player does not exist in this game!");
	    }
	    else if(playerNum  == 0){
	        throw new InvalidInputException("You must select a player before clicking the Move Other Player Button!");
	    }
	    else if(playerNum  == currentGame.getCurrentPlayer().getNumber()){
			throw new InvalidInputException("You Cannot Choose Yourself!");
		}
	    else{
	        Player chosenPlayer = currentGame.getPlayer(playerNum - 1);
	        currentCard.play(tile, chosenPlayer);
	    }
	    currentGame.determineNextPlayer();
		currentGame.updateTileStatus();

		int indexCard = deck.indexOfCard(currentCard);
		int numberOfCards = deck.numberOfCards();

		if( (indexCard+1) == numberOfCards ){
			deck.shuffle();
			deck.setCurrentCard(deck.getCard(0));
		}
		else{
			deck.setCurrentCard( deck.getCard(indexCard+1) );
		}
		currentGame.setMode(Mode.GAME);
  }

  // line 808 "../../../../../PlayModeController.ump"
   private void doPlaySwapPositionActionCard(int playerNum) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
			Game currentGame = tileo.getCurrentGame();
			Deck deck = currentGame.getDeck();
		  	SwapPositionActionCard currentCard = (SwapPositionActionCard)deck.getCurrentCard();
			
			if (playerNum > currentGame.getPlayers().size()) {
				throw new InvalidInputException("There are only"+""+currentGame.getPlayers().size()+"" +"players in the game!");
			}
			else if(playerNum == 0){
				throw new InvalidInputException("you must select a player");
			}
			else if(playerNum == currentGame.getCurrentPlayer().getNumber()){
				throw new InvalidInputException("you cannot choose yourself!");
			}
			else{
		 
		 	currentCard.play(playerNum); 
			}
			
		    currentGame.determineNextPlayer();

		    int indexCard = deck.indexOfCard(currentCard);
			int numberOfCards = deck.numberOfCards();

			if( (indexCard+1) == numberOfCards ){
				deck.shuffle();
				deck.setCurrentCard(deck.getCard(0));
			}
			else{
				deck.setCurrentCard( deck.getCard(indexCard+1) );
			}
			
		    currentGame.setMode(Mode.GAME);
  }

  // line 845 "../../../../../PlayModeController.ump"
   private List<Tile> doPlayAdditionalMoveActionCard(int numOfMoves) throws InvalidInputException{
    TileO tileo = TileOApplication.getTileO();
 		Game currentGame = tileo.getCurrentGame();
 		Deck deck = currentGame.getDeck();
 		ActionCard currentCard = deck.getCurrentCard();
 		List<Tile> possibleDestinations = new ArrayList<Tile>();
 		

 		AdditionalMoveActionCard card = (AdditionalMoveActionCard) deck.getCurrentCard();
 		possibleDestinations = card.play(numOfMoves);
 		
 		
 		int currentInd = deck.indexOfCard(currentCard);
 		int totalCard = deck.getCards().size();
 		
 		if ( (currentInd+1) == totalCard){
 			deck.shuffle();
 			deck.setCurrentCard(deck.getCard(0));
 		}
 		else{
 			deck.setCurrentCard(deck.getCard(currentInd+1));
 		}
 		
 		currentGame.setMode(Mode.GAME);
 		
 		return possibleDestinations;
  }

  // line 874 "../../../../../PlayModeController.ump"
   private boolean isNotInGameOrWonMode(Game selectedGame){
    if (isInWonMode(selectedGame)==false && isInGameMode(selectedGame)==false){
		  return true;
	  }
	  return false;
  }

  // line 881 "../../../../../PlayModeController.ump"
   private boolean isInWonMode(Game selectedGame){
    if (selectedGame.getModeFullName().equals("GAME_WON")){
		  return true;
	  }
	  return false;
  }

  // line 888 "../../../../../PlayModeController.ump"
   private boolean isInGameMode(Game selectedGame){
    if (selectedGame.getModeFullName().equals("GAME")){
		  return true;
	  }
	  return false;
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "numOfMoves" + ":" + getNumOfMoves()+ "," +
            "tileHint" + ":" + getTileHint()+ "]"
     + outputString;
  }

public int numberOfPlayer() {
	 TileO tileo = TileOApplication.getTileO();
	  Game currentGame = tileo.getCurrentGame();
	  int numberOfPlayer = currentGame.numberOfPlayers();
	return numberOfPlayer;
}
}