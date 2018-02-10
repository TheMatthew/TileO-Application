/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 60 "../../../../../TileOPersistence.ump"
// line 314 "../../../../../TileO.ump"
public class RemoveConnectionActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RemoveConnectionActionCard(String aInstructions, Deck aDeck)
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

  // line 318 "../../../../../TileO.ump"
   public void play(Connection connection){
    connection.delete();
  }

  // line 322 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_REMOVECONNECTIONACTIONCARD;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 62 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -131L ;

  
}