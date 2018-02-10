/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 103 "../../../../../TileOPersistence.ump"
// line 427 "../../../../../TileO.ump"
public class DeactivateActionTileActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DeactivateActionTileActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 431 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_DEACTIVATEACTIONTILEACTIONCARD;
  }

  // line 436 "../../../../../TileO.ump"
   public void play(){
    Game currentGame = this.getDeck().getGame();
	   for (Tile aTile: currentGame.getTiles()){
		   if ( (aTile instanceof ActionTile) && (((ActionTile) aTile).getActionTileStatusFullName().equals("Active"))) {
			   ((ActionTile) aTile).deactivate();
		   }
	   }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 105 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -139L ;

  
}