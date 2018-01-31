/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 113 "../../../../../TileOPersistence.ump"
// line 470 "../../../../../TileO.ump"
public class WinTileHintActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WinTileHintActionCard(String aInstructions, Deck aDeck)
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

  // line 474 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_WINTILEHINTACTIONCARD;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 115 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -141L ;

  
}