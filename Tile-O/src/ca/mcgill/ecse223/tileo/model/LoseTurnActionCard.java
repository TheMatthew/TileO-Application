/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 44 "../../../../../TileOPersistence.ump"
// line 339 "../../../../../TileO.ump"
public class LoseTurnActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LoseTurnActionCard(String aInstructions, Deck aDeck)
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

  // line 343 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_LOSETURNACTIONCARD;
  }

  // line 347 "../../../../../TileO.ump"
   public void play(){
    Game currentGame = this.getDeck().getGame();
	   Player currentPlayer = currentGame.getCurrentPlayer();
	   currentPlayer.loseTurns(1); //turn num = 1
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 46 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -128L ;

  
}