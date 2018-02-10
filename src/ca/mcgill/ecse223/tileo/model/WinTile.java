/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.util.*;

// line 88 "../../../../../TileOPersistence.ump"
// line 243 "../../../../../TileO.ump"
public class WinTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WinTile(int aX, int aY, Game aGame)
  {
    super(aX, aY, aGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 247 "../../../../../TileO.ump"
   public void land(){
    Game currentGame = this.getGame();
  	Player currentPlayer = currentGame.getCurrentPlayer();
  	currentPlayer.setCurrentTile(this);
  	
  	setHasBeenVisited(true);
  	currentGame.setMode(Mode.GAME_WON);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 90 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -136L ;

  
}