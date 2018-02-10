/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 70 "../../../../../TileOPersistence.ump"
// line 326 "../../../../../TileO.ump"
public class TeleportActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TeleportActionCard(String aInstructions, Deck aDeck)
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

  // line 330 "../../../../../TileO.ump"
   public void play(Tile tile) throws InvalidInputException{
    tile.land();
  }

  // line 334 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_TELEPORTACTIONCARD;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 72 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -133L ;

  
}