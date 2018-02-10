/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.util.*;

// line 10 "../../../../../TileOPersistence.ump"
// line 3 "../../../../../TileOState.ump"
// line 180 "../../../../../TileO.ump"
public class ActionTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ActionTile Attributes
  private int inactivityPeriod;
  private int turnsUntilActive;

  //ActionTile State Machines
  enum ActionTileStatus { Active, Inactive }
  private ActionTileStatus actionTileStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ActionTile(int aX, int aY, Game aGame, int aInactivityPeriod)
  {
    super(aX, aY, aGame);
    inactivityPeriod = aInactivityPeriod;
    turnsUntilActive = 0;
    setActionTileStatus(ActionTileStatus.Active);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTurnsUntilActive(int aTurnsUntilActive)
  {
    boolean wasSet = false;
    turnsUntilActive = aTurnsUntilActive;
    wasSet = true;
    return wasSet;
  }

  public int getInactivityPeriod()
  {
    return inactivityPeriod;
  }

  public int getTurnsUntilActive()
  {
    return turnsUntilActive;
  }

  public String getActionTileStatusFullName()
  {
    String answer = actionTileStatus.toString();
    return answer;
  }

  public ActionTileStatus getActionTileStatus()
  {
    return actionTileStatus;
  }

  public boolean deactivate()
  {
    boolean wasEventProcessed = false;
    
    ActionTileStatus aActionTileStatus = actionTileStatus;
    switch (aActionTileStatus)
    {
      case Active:
        if (getInactivityPeriod()>0)
        {
        // line 6 "../../../../../TileOState.ump"
          setTurnsUntilActive(getInactivityPeriod() + 1);
          setActionTileStatus(ActionTileStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean takeTurn()
  {
    boolean wasEventProcessed = false;
    
    ActionTileStatus aActionTileStatus = actionTileStatus;
    switch (aActionTileStatus)
    {
      case Inactive:
        if (getTurnsUntilActive()>1)
        {
        // line 11 "../../../../../TileOState.ump"
          setTurnsUntilActive(getTurnsUntilActive() - 1);
          setActionTileStatus(ActionTileStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        if (getTurnsUntilActive()<=1)
        {
        // line 14 "../../../../../TileOState.ump"
          setTurnsUntilActive(0);
          setActionTileStatus(ActionTileStatus.Active);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setActionTileStatus(ActionTileStatus aActionTileStatus)
  {
    actionTileStatus = aActionTileStatus;
  }

  public void delete()
  {
    super.delete();
  }

  // line 187 "../../../../../TileO.ump"
   public void land(){
		  
		  Game thisGame = this.getGame();
		  Player currentPlayer = thisGame.getCurrentPlayer();
		  currentPlayer.setCurrentTile(this);
		  
		  this.setHasBeenVisited(true);   
		  
		  if (this.getActionTileStatus().name().equals("Active")){
			  
			  int index = thisGame.indexOfPlayer(currentPlayer);
			  int number = thisGame.numberOfPlayers();
			  
			  
			  Deck deck = thisGame.getDeck();
			  this.deactivate();
			  thisGame.setMode( deck.getCurrentCard().getActionCardGameMode());
			  System.out.println(deck.indexOfCard(deck.getCurrentCard()));
			 
		  }
		  
		  else{
			 
			  thisGame.determineNextPlayer();
			  thisGame.updateTileStatus();
			  thisGame.setMode(Mode.GAME);
		  }
  }

  // line 213 "../../../../../TileO.ump"
   public void activate(){
    this.setActionTileStatus(ActionTileStatus.Active);
	 this.setTurnsUntilActive(0);
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "inactivityPeriod" + ":" + getInactivityPeriod()+ "," +
            "turnsUntilActive" + ":" + getTurnsUntilActive()+ "]"
     + outputString;
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 12 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = 2315072607928790501L ;

  
}