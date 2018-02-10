/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 21 "../../../../../TileOPersistence.ump"
// line 297 "../../../../../TileO.ump"
public class ConnectTilesActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ConnectTilesActionCard(String aInstructions, Deck aDeck)
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

  // line 301 "../../../../../TileO.ump"
   public void play(Tile tile1, Tile tile2) throws InvalidInputException{
    Connection connection = new Connection(tile1.getGame());
  	connection.addTile(tile1);
  	connection.addTile(tile2);
  	
  	tile1.getGame().addConnection(connection);
  }

  // line 309 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_CONNECTTILESACTIONCARD;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 23 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = 386717977557499839L ;

  
}