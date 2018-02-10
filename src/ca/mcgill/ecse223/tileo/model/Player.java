/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// line 54 "../../../../../TileOPersistence.ump"
// line 22 "../../../../../TileOState.ump"
// line 90 "../../../../../TileO.ump"
public class Player implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private int number;
  private int turnsUntilActive;

  //Player State Machines
  enum PlayerStatus { Active, Inactive }
  private PlayerStatus playerStatus;
  public enum Color { RED, BLUE, GREEN, YELLOW }
  private Color color;

  //Player Associations
  private Tile startingTile;
  private Tile currentTile;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(int aNumber, Game aGame)
  {
    number = aNumber;
    turnsUntilActive = 0;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
    setPlayerStatus(PlayerStatus.Active);
    setColor(Color.RED);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setTurnsUntilActive(int aTurnsUntilActive)
  {
    boolean wasSet = false;
    turnsUntilActive = aTurnsUntilActive;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }

  public int getTurnsUntilActive()
  {
    return turnsUntilActive;
  }

  public String getPlayerStatusFullName()
  {
    String answer = playerStatus.toString();
    return answer;
  }

  public String getColorFullName()
  {
    String answer = color.toString();
    return answer;
  }

  public PlayerStatus getPlayerStatus()
  {
    return playerStatus;
  }

  public Color getColor()
  {
    return color;
  }

  public boolean loseTurns(int n)
  {
    boolean wasEventProcessed = false;
    
    PlayerStatus aPlayerStatus = playerStatus;
    switch (aPlayerStatus)
    {
      case Active:
        if (n>0)
        {
        // line 25 "../../../../../TileOState.ump"
          setTurnsUntilActive(getTurnsUntilActive() + n);
          setPlayerStatus(PlayerStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        break;
      case Inactive:
        if (n>0)
        {
        // line 36 "../../../../../TileOState.ump"
          setTurnsUntilActive(getTurnsUntilActive() + n);
          setPlayerStatus(PlayerStatus.Inactive);
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
    
    PlayerStatus aPlayerStatus = playerStatus;
    switch (aPlayerStatus)
    {
      case Inactive:
        if (getTurnsUntilActive()>1)
        {
        // line 30 "../../../../../TileOState.ump"
          setTurnsUntilActive(getTurnsUntilActive() - 1);
          setPlayerStatus(PlayerStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        if (getTurnsUntilActive()<=1)
        {
        // line 33 "../../../../../TileOState.ump"
          setTurnsUntilActive(0);
          setPlayerStatus(PlayerStatus.Active);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayerStatus(PlayerStatus aPlayerStatus)
  {
    playerStatus = aPlayerStatus;
  }

  public boolean setColor(Color aColor)
  {
    color = aColor;
    return true;
  }

  public Tile getStartingTile()
  {
    return startingTile;
  }

  public boolean hasStartingTile()
  {
    boolean has = startingTile != null;
    return has;
  }

  public Tile getCurrentTile()
  {
    return currentTile;
  }

  public boolean hasCurrentTile()
  {
    boolean has = currentTile != null;
    return has;
  }

  public Game getGame()
  {
    return game;
  }

  public boolean setStartingTile(Tile aNewStartingTile)
  {
    boolean wasSet = false;
    startingTile = aNewStartingTile;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentTile(Tile aNewCurrentTile)
  {
    boolean wasSet = false;
    currentTile = aNewCurrentTile;
    wasSet = true;
    return wasSet;
  }

  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    startingTile = null;
    currentTile = null;
    Game placeholderGame = game;
    this.game = null;
    placeholderGame.removePlayer(this);
  }

  // line 98 "../../../../../TileO.ump"
   public List<Tile> getPossibleMoves(int numOfMoves){
    List<Tile> possibleDestinations = new ArrayList<Tile>();
		   possibleDestinations = currentTile.getNeighbors(numOfMoves);
		  
		   List<Tile> optimalDestinations = new ArrayList<Tile>();
		   
		   for (int i=0; i<possibleDestinations.size();i++){
			  if( ( Math.abs(possibleDestinations.get(i).getX() - this.currentTile.getX()) + Math.abs(possibleDestinations.get(i).getY()- this.currentTile.getY()) ) == numOfMoves ){
				  optimalDestinations.add(possibleDestinations.get(i));
			  }
		   }
		   
		   if (optimalDestinations.size()==0){
			   return possibleDestinations; 
		   }
		   
		   else{
			   return optimalDestinations;
		   }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "turnsUntilActive" + ":" + getTurnsUntilActive()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startingTile = "+(getStartingTile()!=null?Integer.toHexString(System.identityHashCode(getStartingTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentTile = "+(getCurrentTile()!=null?Integer.toHexString(System.identityHashCode(getCurrentTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null")
     + outputString;
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 57 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -130L ;

  
}