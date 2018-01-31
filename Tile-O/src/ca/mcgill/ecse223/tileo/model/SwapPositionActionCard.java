/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 118 "../../../../../TileOPersistence.ump"
// line 478 "../../../../../TileO.ump"
public class SwapPositionActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SwapPositionActionCard(String aInstructions, Deck aDeck)
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

  // line 482 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_SWAPPOSITIONACTIONCARD;
  }

  // line 486 "../../../../../TileO.ump"
   public void play(Tile tile, Player player) throws RuntimeException{
    player.setCurrentTile(tile);
  }

  // line 490 "../../../../../TileO.ump"
   public void play(int playerNum) throws RuntimeException{
    Game currentGame = this.getDeck().getGame();
	  Player currentPlayer = currentGame.getCurrentPlayer();
	  Player chosenPlayer = currentGame.getPlayer(playerNum - 1);
	  Tile currentTile = currentPlayer.getCurrentTile();
	  Tile chosenTile = chosenPlayer.getCurrentTile();
	  currentPlayer.setCurrentTile(chosenTile);
	     chosenPlayer.setCurrentTile(currentTile);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 120 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -142L ;

  
}