/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.List;

import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 65 "../../../../../TileOPersistence.ump"
// line 282 "../../../../../TileO.ump"
public class RollDieActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RollDieActionCard(String aInstructions, Deck aDeck)
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

  // line 286 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_ROLLDIEACTIONCARD;
  }

  // line 290 "../../../../../TileO.ump"
   public List<Tile> play(int numOfMoves){
    Game thisGame = this.getDeck().getGame();
	   Die thisDie = thisGame.getDie();
	   List<Tile> possibleDestinations = thisGame.takeTurn(numOfMoves);
	   return possibleDestinations;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 67 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -132L ;

  
}