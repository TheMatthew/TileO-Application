/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

// line 32 "../../../../../TileOPersistence.ump"
// line 518 "../../../../../TileO.ump"
public class Die implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Die Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Die(Game aGame)
  {
    if (aGame == null || aGame.getDie() != null)
    {
      throw new RuntimeException("Unable to create Die due to aGame");
    }
    game = aGame;
  }

  public Die(int aCurrentConnectionPiecesForGame, Deck aDeckForGame)
  {
    game = new Game(aCurrentConnectionPiecesForGame, aDeckForGame, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Game getGame()
  {
    return game;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

  // line 520 "../../../../../TileO.ump"
   public int roll(){
    int move = ThreadLocalRandom.current().nextInt(1,7);
		  return move;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 35 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -7403802774454467836L ;

  
}