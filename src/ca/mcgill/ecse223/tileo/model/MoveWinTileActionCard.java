/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 93 "../../../../../TileOPersistence.ump"
// line 354 "../../../../../TileO.ump"
public class MoveWinTileActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MoveWinTileActionCard(String aInstructions, Deck aDeck)
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

  // line 358 "../../../../../TileO.ump"
   public Mode getActionCardGameMode(){
    return Mode.GAME_MOVEWINTILEACTIONCARD;
  }

  // line 362 "../../../../../TileO.ump"
   public void play(Tile tile){
    Game currentGame = this.getDeck().getGame();
	  
	   WinTile currentWinTile = currentGame.getWinTile();
	   NormalTile normalTile = new NormalTile(currentWinTile.getX(),currentWinTile.getY(),currentGame);
	   
	   for (int i=0;i<currentWinTile.getConnections().size();i++){
		   
		   
		   Connection aConnection = currentWinTile.getConnections().get(i);
		   Connection newConnection = new Connection(currentGame);
		   newConnection.addTile(normalTile);
		   int ind = aConnection.getTiles().indexOf(currentWinTile);
		   if (ind==0){
			   newConnection.addTile(aConnection.getTiles().get(1));
			   aConnection.getTiles().get(1).removeConnection(aConnection);
		   }
		   else{
			   newConnection.addTile(aConnection.getTiles().get(0));
			   aConnection.getTiles().get(0).removeConnection(aConnection);
		   }
	   }
	   
	   currentWinTile.delete();
	   
	   
	   WinTile newWinTile = new WinTile(tile.getX(),tile.getY(),currentGame);
	   
	   for (int i=0;i<tile.getConnections().size();i++){
		   Connection aConnection = tile.getConnections().get(i);
		   Connection newConnection = new Connection(currentGame);
		   newConnection.addTile(newWinTile);
		   int ind = aConnection.getTiles().indexOf(tile);
		   Tile test;
		   if (ind==0){
			   newConnection.addTile(aConnection.getTiles().get(1));
			   aConnection.getTiles().get(1).removeConnection(aConnection);
		   }
		   else{  
			   newConnection.addTile(aConnection.getTiles().get(0));
			   aConnection.getTiles().get(0).removeConnection(aConnection);
		   }
	   }
	   
	   tile.delete();
	   
	   currentGame.setWinTile(newWinTile);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 95 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -137L ;

  
}