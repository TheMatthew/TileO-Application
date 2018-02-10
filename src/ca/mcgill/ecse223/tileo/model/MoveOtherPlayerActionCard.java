/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 123 "../../../../../TileOPersistence.ump"
// line 502 "../../../../../TileO.ump"
public class MoveOtherPlayerActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MoveOtherPlayerActionCard(String aInstructions, Deck aDeck)
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

  // line 506 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_MOVEOTHERPLAYERACTIONCARD;
  }

  // line 510 "../../../../../TileO.ump"
   public void play(Tile tile, Player player) throws RuntimeException{
    player.setCurrentTile(tile);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 125 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -143L ;

  
}