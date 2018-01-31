/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 108 "../../../../../TileOPersistence.ump"
// line 446 "../../../../../TileO.ump"
public class RevealTileActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RevealTileActionCard(String aInstructions, Deck aDeck)
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

  // line 450 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_REVEALTILEACTIONCARD;
  }

  // line 455 "../../../../../TileO.ump"
   public String play(Tile tile){
    if (tile instanceof NormalTile){
	    	return "Normal";
	    }
	    else if (tile instanceof ActionTile){
	    	return "Action";
	    }
	    else if (tile instanceof WinTile){
	    	return "Win";
	    }
	    
	    return null;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 110 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -140L ;

  
}