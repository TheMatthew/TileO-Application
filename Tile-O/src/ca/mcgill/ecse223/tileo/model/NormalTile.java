/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.util.*;

// line 49 "../../../../../TileOPersistence.ump"
// line 220 "../../../../../TileO.ump"
public class NormalTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NormalTile(int aX, int aY, Game aGame)
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

  // line 224 "../../../../../TileO.ump"
   public void land(){
		  Game thisGame=this.getGame();
		  Player thisPlayer = thisGame.getCurrentPlayer();
		  thisPlayer.setCurrentTile(this);
		  thisGame.determineNextPlayer();
		  thisGame.updateTileStatus();
		  this.setHasBeenVisited(true);   
		  thisGame.setMode(Mode.GAME);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 51 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -129L ;

  
}