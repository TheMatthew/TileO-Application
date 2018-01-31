/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.List;

import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 98 "../../../../../TileOPersistence.ump"
// line 413 "../../../../../TileO.ump"
public class AdditionalMoveActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AdditionalMoveActionCard(String aInstructions, Deck aDeck)
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

  // line 417 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_ADDITIONALMOVEACTIONCARD;
  }

  // line 421 "../../../../../TileO.ump"
   public List<Tile> play(int numOfMoves){
    Game thisGame = this.getDeck().getGame();
		List<Tile> possibleDestinations = thisGame.takeTurn(numOfMoves);
		return possibleDestinations;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 100 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -138L ;

  
}