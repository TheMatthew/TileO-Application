package ca.mcgill.ecse223.tileo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayModeController;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Tile;


public class PlayModePage extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JPanel topPanel,board,funcsPanel,playerPanel;
	
	int rowNum = 0;
	int colNum = 0;
	JButton[][] TilesandConnectionPieces;	
	
    private String[] dice = new String[]{"dice1.jpg","dice2.jpg","dice3.jpg","dice4.jpg","dice5.jpg","dice6.jpg",};
    private JLabel die;	
	
	private JButton drawButton;
	private JButton connectButton;
	private JButton removeConnectionButton;
	private JButton rollDieButton;
	private JButton teleportButton;
	private JButton moveWinTileButton;
	
	
	private JButton additionalMoveButton;
	private JComboBox additionalMoveToggleList;
	private int additionalMoveSelected = -1;
	private JComboBox playerNumToggleList;
	private int playerNumSelected = -1;
	private JButton moveOtherPlayerButton;
	private JButton swapPositionButton;
	
	private JButton saveButton;
	private JButton loadButton;
	
	private JButton addConnectionText;
	private JButton removeConnectionText;
	private ArrayList<Integer> newWinTileCoord = new ArrayList<Integer>();
	private ArrayList<Integer> revealTileCoord = new ArrayList<Integer>();
	
	private JButton buttonClicked;
	private JButton connectionClicked;
	
	private JLabel p1Label;
	private JLabel p2Label;
	private JLabel p3Label;
	private JLabel p4Label;
	private JLabel visitedTilesLegend;
	private JLabel possibleMovesLegend;
	
	
	private String mode = "";
	private String error= "";
	
	private PlayModeController pmc;
	
	/** Creates new form PlayMode Page */
	public PlayModePage(PlayModeController pmc) {
		this.pmc=pmc;
		initComponents();
	}
	
	private void refreshData(){
		additionalMoveSelected = -1;
		playerNumSelected = -1;
		
		
		mode = pmc.getGameMode();
		Border defaultBorder = new JButton().getBorder();
		Color defaultColor = new JButton().getBackground();
		
		// Clear all the border and color of each tile
		for (int y = 0; y < (rowNum+(rowNum-1)); y++){
			for (int x = 0; x < (colNum+(colNum-1)); x++){
				TilesandConnectionPieces[y][x].setBorder(defaultBorder);
				TilesandConnectionPieces[y][x].setBackground(defaultColor);
			}
		}
		
		// Update the position of each playing pieces on the board
		for(int i=0; i<pmc.getPlayers().size();i++){
			int xPosition = pmc.getPlayers().get(i).getCurrentTile().getX();
			int yPosition = pmc.getPlayers().get(i).getCurrentTile().getY();
			
			if (i==0){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.RED);
			}
			else if (i==1){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.BLUE);
			}
			else if (i==2){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.GREEN);
			}
			else if (i==3){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.YELLOW);
			}
		}
		
		// Change the border color of visited tiles
		for (Tile aTile: pmc.getTiles()){
			if (aTile.getHasBeenVisited()==true){
				int yOnBoard = aTile.getY()*2;
				int xOnBoard = aTile.getX()*2;
				TilesandConnectionPieces[yOnBoard][xOnBoard].setBorder(BorderFactory.createLineBorder(Color.MAGENTA.brighter(), 2));
			}
		}

		
		if (mode.equals("GAME")){
			int currentPlayerNum = pmc.getCurrentPlayerNum();
			JOptionPane.showMessageDialog(frame,"Player " + currentPlayerNum + "'s turn to roll the die.");
		}


		
		
		if (error.length()>0){
			JOptionPane.showMessageDialog(frame,error);
		}
	}
	
	public void initComponents(){

		int maxRow = 0;
		int maxCol = 0;
				
		for (int i=0; i<pmc.getTiles().size(); i++){
			if (maxRow<(pmc.getTiles().get(i).getY())){
				maxRow = pmc.getTiles().get(i).getY();
			}
			if (maxCol<(pmc.getTiles().get(i).getX())){
				maxCol = pmc.getTiles().get(i).getX();
			}
		}
		
		
		rowNum = maxRow+1;
		colNum = maxCol+1;

	//////////////////////////////////////////////////////////////////////////////////////
		TilesandConnectionPieces = new JButton[rowNum+(rowNum-1)][colNum+(colNum-1)];
	//////////////////////////////////////////////////////////////////////////////////////
				
		
		// Frame
		frame = new JFrame("PLAY MODE"); // Title for the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on Exit
//		frame.setResizable(false);// Execution window cannot be resized
		
		// Title
		topPanel = new JPanel (new GridBagLayout());
		topPanel.setBackground(Color.BLACK);
		
		GridBagConstraints topSpace = new GridBagConstraints();
		topSpace.insets = new Insets(1, 0, 1, 0);  // Button spacings (top,left,bottom,right)
		
		topSpace.gridx = 0;
		topSpace.gridy = 0;
		topPanel.add((new JLabel (new ImageIcon("playModeTitle.jpg"))),topSpace); //Add an image to the topPanel
		
		
		visitedTilesLegend = new JLabel("Magenta Border - Visited Tiles");
		visitedTilesLegend.setForeground(Color.MAGENTA.brighter());
		visitedTilesLegend.setFont(new Font("Georgia", Font.BOLD, 13));
		
		topSpace.gridx = 0;
		topSpace.gridy = 1;
		topPanel.add(visitedTilesLegend,topSpace);
		
		
		possibleMovesLegend = new JLabel("Cyan Border - Possible Destinations");
		possibleMovesLegend.setForeground(Color.CYAN.brighter());
		possibleMovesLegend.setFont(new Font("Georgia", Font.BOLD, 13));
		
		topSpace.gridx = 0;
		topSpace.gridy = 2;
		topPanel.add(possibleMovesLegend,topSpace);
		
		
		
		
		
		// Game board
		// Font size and style for the center panel
		Font boardFont = new Font("Elephant", Font.BOLD, 15);
		
		
		board = new JPanel(new GridBagLayout());
		board.setBackground(Color.BLACK);
		
		// Create a border with title for center panel
		board.setBorder(BorderFactory.createTitledBorder(null, "TILE-O", 0, 0, boardFont, Color.CYAN));
		
		GridBagConstraints boardSpace = new GridBagConstraints();
		boardSpace.insets = new Insets(1, 1, 1, 1);  // Button spacings (top,bottom,left,right)

		
		
		// Print out the tile and connecting piece
		
		for (int y = 0; y < (rowNum+(rowNum-1)); y++){
			for (int x = 0; x < (colNum+(colNum-1)); x++){
				
				TilesandConnectionPieces[y][x] = new JButton();

				if (x%2 == 1){
					TilesandConnectionPieces[y][x].setPreferredSize(new Dimension(14, 7));
					
					TilesandConnectionPieces[y][x].setText(Integer.toString(x)+" "+Integer.toString(y));
					TilesandConnectionPieces[y][x].setForeground(Color.WHITE.brighter());
					
					TilesandConnectionPieces[y][x].addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							connectionClicked = (JButton) evt.getSource();
							connectionPieceClickedActionPerformed(evt);
						}
					});
				}
				
				else if (y%2 == 1){
					TilesandConnectionPieces[y][x].setPreferredSize(new Dimension(7, 14));
					
					TilesandConnectionPieces[y][x].setText(Integer.toString(x)+" "+Integer.toString(y));
					TilesandConnectionPieces[y][x].setForeground(Color.WHITE.brighter());
					
					TilesandConnectionPieces[y][x].addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							connectionClicked = (JButton) evt.getSource();
							connectionPieceClickedActionPerformed(evt);
						}
					});
				}
				else {
					TilesandConnectionPieces[y][x].setPreferredSize(new Dimension(0, 0)); // Don't change it to setVisible
					
					TilesandConnectionPieces[y][x].addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							buttonClicked = (JButton) evt.getSource();
							tileClickedActionPerformed(evt);
						}
					});				
					
					for (int i = 0; i<pmc.getTiles().size(); i++){
						int actualXValue = pmc.getTiles().get(i).getX();
						int actualYValue = pmc.getTiles().get(i).getY();
						
						if (actualXValue==x/2 && actualYValue==y/2){
							TilesandConnectionPieces[y][x].setText(Integer.toString(actualXValue)+" "+Integer.toString(actualYValue));
							TilesandConnectionPieces[y][x].setForeground(Color.WHITE.brighter());
							TilesandConnectionPieces[y][x].setPreferredSize(new Dimension(18, 18));
						}
						
					}
					
				}
				
				
				if (x%2 == 1 && y%2 == 1){
					TilesandConnectionPieces[y][x].setVisible(false);  // Never used
				}
				
				
				boardSpace.gridx = x;
				boardSpace.gridy = y;
				board.add(TilesandConnectionPieces[y][x],boardSpace);
							
			}
		}		
		
		// Remove the connection piece of non-existing tile
		for (int y = 0; y < (rowNum+(rowNum-1)); y++){
			for (int x = 0; x < (colNum+(colNum-1)); x++){
				
				if (x%2==0 && y%2 ==0){
					if ((int)(TilesandConnectionPieces[y][x].getPreferredSize().getWidth())==0 && (int)(TilesandConnectionPieces[y][x].getPreferredSize().getHeight())==0){
						// has 2 connecting pieces (four vertices)   **************
						if (x==0 && y==0){ // upper left
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece							
							}
						else if (x == (colNum+(colNum-1))-1 && y==0){ // upper right
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece		
						}
						else if (x==0 && y == (rowNum+(rowNum-1))-1){ // bottom left
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece
						}
						else if (x == (colNum+(colNum-1)-1) && y==(rowNum+(rowNum-1)-1)){ // bottom right
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece							
						}
						
										
						// has 3 connecting pieces  *************
						else if (y==0 && x%2==0){ // top row
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // left horizontal 
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // right horizontal 
						}
						
						else if (y==(rowNum+(rowNum-1))-1 && x%2==0){ // bottom row
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // right horizontal 				
						}
						else if (x==0 && y%2==0){ // leftmost column
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical up
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical down
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal right
						}
						
						else if (x==(colNum+(colNum-1))-1 && y%2==0){ // rightmost column
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical up
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical down
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal left					
						}
						
						// has 4 connecting pieces
						else {
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal left
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal right
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical up
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical down
						}
					}
				}
			}
		}
		
		// Remove non-existing connection pieces
		String leftTileActualCoord="";
		String rightTileActualCoord="";
		String upTileActualCoord="";
		String downTileActualCoord="";
			
		String actualConnection="";
		String actualConnectionAlter="";
		
		for (int y = 0; y < (rowNum+(rowNum-1)); y++){
			for (int x = 0; x < (colNum+(colNum-1)); x++){
				if (x%2==1 || y%2 ==1){
					if ((int)(TilesandConnectionPieces[y][x].getPreferredSize().getWidth())!=0 && (int)(TilesandConnectionPieces[y][x].getPreferredSize().getHeight())!=0){
						int connectionBoardx =Integer.parseInt(TilesandConnectionPieces[y][x].getText().split("\\s")[0]);
						int connectionBoardy = Integer.parseInt(TilesandConnectionPieces[y][x].getText().split("\\s")[1]);
						// 4 Directions
						if (connectionBoardx%2 == 1){
							int leftActualX = (connectionBoardx-1)/2;
							int leftActualY = connectionBoardy/2;

							leftTileActualCoord = "("+leftActualX+","+leftActualY+")";
							
							int rightActualX = (connectionBoardx+1)/2;
							int rightActualY = connectionBoardy/2;
							
							 rightTileActualCoord = "("+rightActualX+","+rightActualY+")";
							
							 actualConnection = leftTileActualCoord + rightTileActualCoord;
							 actualConnectionAlter = rightTileActualCoord + leftTileActualCoord;					 
								 
						}

						if (connectionBoardy%2 == 1){
							int upActualX = connectionBoardx/2;
							int upActualY = (connectionBoardy-1)/2;
							
							upTileActualCoord = "("+upActualX+","+upActualY+")";
					
							int downActualX = connectionBoardx/2;
							int downActualY = (connectionBoardy+1)/2;
							
							 downTileActualCoord = "("+downActualX+","+downActualY+")";
							
							actualConnection = upTileActualCoord + downTileActualCoord;
							actualConnectionAlter = downTileActualCoord + upTileActualCoord;
						}
						
						 boolean exists = false;
						 for (int i=0; i<pmc.getConnections().size();i++){
							 String connectionInGame="";
							 String tileInGame="";
							 for (Tile aTile : pmc.getConnectedTiles(pmc.getConnections().get(i))){
								 tileInGame="("+aTile.getX()+","+aTile.getY()+")";
								 connectionInGame = connectionInGame+tileInGame;
							 }
							 if (connectionInGame.equals(actualConnection) || (connectionInGame.equals(actualConnectionAlter))){
								 exists=true;
							 }
						 }
						 
						 if (exists==false){
							 TilesandConnectionPieces[y][x].setVisible(false);
						 }
					}
				}
			}
		}
		
		
		////////////////////////////////////////////////////////
		
		// Panel for the function buttons
		funcsPanel = new JPanel(new GridBagLayout());
		funcsPanel.setBackground(Color.BLACK);
		
		GridBagConstraints menuSpace = new GridBagConstraints();
		
		
		// Roll die
		rollDieButton = new JButton();
		rollDieButton.setText("   Roll Die   ");
		rollDieButton.setForeground(Color.BLACK);
		rollDieButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rollDieButtonActionPerformed(evt);
			}
		});
				
		menuSpace.insets = new Insets(20, 10, 0, 10);  // Button spacings (top,bottom,left,right)
		menuSpace.gridx = 0;
		menuSpace.gridy = 0;
		funcsPanel.add(rollDieButton,menuSpace);
				
		// Draw Action Card
		drawButton = new JButton();
		drawButton.setText("Draw Card");
		drawButton.setForeground(Color.BLACK);
		drawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				drawButtonActionPerformed(evt);
			}
		});
		
		menuSpace.insets = new Insets(25, 10, 0, 10);  // Button spacings (top,left,buttom,right)
		menuSpace.gridx = 0;
		menuSpace.gridy = 1;
		funcsPanel.add(drawButton,menuSpace);
		
	
		
		// Connection piece
		connectButton = new JButton();
		connectButton.setText("   Connect Tiles   ");
		connectButton.setForeground(Color.BLACK);
		connectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectButtonActionPerformed(evt);
			}
		});
		
		menuSpace.insets = new Insets(25, 10, 0, 10);
		menuSpace.gridx = 0;
		menuSpace.gridy = 3;
		funcsPanel.add(connectButton,menuSpace);
		
		
		removeConnectionButton = new JButton();
		removeConnectionButton.setText("Remove Connection");
		removeConnectionButton.setForeground(Color.BLACK);
		removeConnectionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeConnectionButtonActionPerformed(evt);
			}
		});
				
		menuSpace.insets = new Insets(6,10,0,10);
		menuSpace.gridx = 0;
		menuSpace.gridy = 4;
		funcsPanel.add(removeConnectionButton,menuSpace);
				
	
		// Teleport
		teleportButton = new JButton();
		teleportButton.setText("   Teleport   ");
		teleportButton.setForeground(Color.BLACK);
		teleportButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				teleportButtonActionPerformed(evt);
			}
		});
		
		menuSpace.insets = new Insets(25, 10, 0, 0);
		menuSpace.gridx = 0;
		menuSpace.gridy = 6;
		funcsPanel.add(teleportButton,menuSpace);
		
		
		// Move Win Tile
		moveWinTileButton = new JButton();
		moveWinTileButton.setText("Move Win Tile");
		moveWinTileButton.setForeground(Color.BLACK);
		moveWinTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveWinTileButtonActionPerformed(evt);
			}
		});
		
		menuSpace.insets = new Insets(6,10,0,10);
		menuSpace.gridx = 0;
		menuSpace.gridy = 7;
		funcsPanel.add(moveWinTileButton,menuSpace);
		
		
		/////////////////////// Dice
        die = new JLabel(new ImageIcon(dice[0]));
		menuSpace.insets = new Insets(20, 10, 0, 10);  // Button spacings (top,bottom,left,right)
		menuSpace.gridx = 1;
		menuSpace.gridy = 0;
		funcsPanel.add(die,menuSpace);

		
		// Move Other Player
		playerNumToggleList = new JComboBox<String>(new String[0]);
		int numberOfPlayer = pmc.numberOfPlayer();
		
		for (int i = 1; i<numberOfPlayer+1; i++){
			playerNumToggleList.addItem("    " + i + "    ");
		}
		playerNumToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        playerNumSelected = cb.getSelectedIndex();
			}
		});
		
		menuSpace.gridx = 1;
		menuSpace.gridy = 1;
		funcsPanel.add(playerNumToggleList,menuSpace);
		playerNumToggleList.setBackground(Color.WHITE);
		playerNumToggleList.setForeground(Color.BLACK);
		
		
		
	
		moveOtherPlayerButton = new JButton();
		moveOtherPlayerButton.setText("Move Other Player");
		moveOtherPlayerButton.setForeground(Color.BLACK);
		moveOtherPlayerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveOtherPlayerButtonActionPerformed(evt);
			}
		});
		
		menuSpace.gridx = 1;
		menuSpace.gridy = 2;
		funcsPanel.add(moveOtherPlayerButton,menuSpace);
		
		
		
		// Swap Position
		menuSpace.insets = new Insets(6,10,0,10);
		swapPositionButton = new JButton();
		swapPositionButton.setText("Swap Position");
		swapPositionButton.setForeground(Color.BLACK);
		swapPositionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				swapPositionButtonActionPerformed(evt);
			}
		});
		
		menuSpace.gridx = 1;
		menuSpace.gridy = 3;
		funcsPanel.add(swapPositionButton,menuSpace);
		
		
		//Additional Move
		additionalMoveToggleList = new JComboBox<String>(new String[0]);
		for (int i = 1; i<7; i++){
			additionalMoveToggleList.addItem("    " + i + "    ");
		}
		
		additionalMoveToggleList.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				additionalMoveSelected = cb.getSelectedIndex();
			}
		});

		
		
		
		
		menuSpace.insets = new Insets(25, 10, 0, 10);
		menuSpace.gridx = 1;
		menuSpace.gridy = 4;
		funcsPanel.add(additionalMoveToggleList,menuSpace);
		
		
		additionalMoveButton = new JButton();
		additionalMoveButton.setText("Additional Move");
		additionalMoveButton.setForeground(Color.BLACK);
		additionalMoveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				additionalMoveButtonActionPerformed(evt);
			}
		});
		
		menuSpace.insets = new Insets(6,10,0,10);
		menuSpace.gridx = 1;
		menuSpace.gridy = 5;
		funcsPanel.add(additionalMoveButton,menuSpace);
		
		
		// Save and load
		saveButton = new JButton();
		saveButton.setText("    SAVE    ");
		saveButton.setForeground(Color.BLACK);
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});
		
		menuSpace.insets = new Insets(25, 10, 0, 10);
		menuSpace.gridx = 0;
		menuSpace.gridy = 13;
		funcsPanel.add(saveButton,menuSpace);
		
		
		
		loadButton = new JButton();
	  	loadButton.setText("    LOAD    ");
	  	loadButton.setForeground(Color.BLACK);
	  	loadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadButtonActionPerformed(evt);
			}
		});
	  	
	  	menuSpace.insets = new Insets(6,10,20,10);
	  	menuSpace.gridx = 0;
		menuSpace.gridy = 14;
		funcsPanel.add(loadButton,menuSpace);

		
		addConnectionText= new JButton();
		addConnectionText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addConnectionText.setBackground(Color.BLACK);
		addConnectionText.setForeground(Color.BLACK.darker());
		
		menuSpace.insets = new Insets(25, 10, 0, 10);
		menuSpace.gridx = 0;
		menuSpace.gridy = 15;
		funcsPanel.add(addConnectionText,menuSpace);
		
		
		
		removeConnectionText= new JButton();
		removeConnectionText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		removeConnectionText.setBackground(Color.BLACK);
		removeConnectionText.setForeground(Color.BLACK);
		
		menuSpace.insets = new Insets(25, 10, 0, 10);
		menuSpace.gridx = 0;
		menuSpace.gridy = 16;
		funcsPanel.add(removeConnectionText,menuSpace);
		
		
		
	  	// Playing pieces
		playerPanel = new JPanel(new GridBagLayout());
		playerPanel.setBackground(Color.BLACK);
		
		
		for(int i=0; i<pmc.getPlayers().size();i++){
			int xPosition = pmc.getPlayers().get(i).getCurrentTile().getX();
			int yPosition = pmc.getPlayers().get(i).getCurrentTile().getY();
			
			if (i==0){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.RED);
			}
			else if (i==1){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.BLUE);
			}
			else if (i==2){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.GREEN);
			}
			else if (i==3){
				TilesandConnectionPieces[yPosition*2][xPosition*2].setBackground(Color.YELLOW);
			}
		}
		
		
		GridBagConstraints playerSpace = new GridBagConstraints();
		playerSpace.insets = new Insets(0,0,0,20);
		
		for (int i = 0; i<pmc.getPlayers().size();i++){
	  	
			if (i==0){
				p1Label = new JLabel();
				p1Label.setText("[P1]");
				p1Label.setForeground(Color.RED);
				p1Label.setFont(new Font("Georgia", Font.BOLD, 13));
			
				playerSpace.gridx = 1;
				playerSpace.gridy = 0;
				playerPanel.add(p1Label,playerSpace);
			}
			
			if (i==1){
				p2Label = new JLabel();
				p2Label.setText("[P2]");
				p2Label.setForeground(Color.BLUE);
				p2Label.setFont(new Font("Georgia", Font.BOLD, 13));
				
				playerSpace.gridx = 2;
				playerSpace.gridy = 0;
				playerPanel.add(p2Label,playerSpace);
			}

			
			if (i==2){
				p3Label = new JLabel();
				p3Label.setText("[P3]");
				p3Label.setForeground(Color.GREEN);
				p3Label.setFont(new Font("Georgia", Font.BOLD, 13));
				
				playerSpace.gridx = 3;
				playerSpace.gridy = 0;
				playerPanel.add(p3Label,playerSpace);
			}
			
			if (i==3){
				p4Label = new JLabel();
				p4Label.setText("[P4]");
				p4Label.setForeground(Color.YELLOW);
				p4Label.setFont(new Font("Georgia", Font.BOLD, 13));
				
				playerSpace.gridx = 4;
				playerSpace.gridy = 0;
				playerPanel.add(p4Label,playerSpace);
			}
		}
		
	  	// Add the panels onto the frame
	  	frame.add(board, BorderLayout.CENTER); // Adds board into Frame
 		frame.add(funcsPanel, BorderLayout.EAST); // Adds function buttons into Frame
 		frame.add(playerPanel,BorderLayout.SOUTH);
 		frame.add(topPanel,BorderLayout.NORTH);
	  	
		frame.pack();
	  	frame.setSize(1050,730); ////////////////
	  	frame.setVisible(true);
	  	
	  	
	  	//////////////// Start Game /////////////////////
	  	onCreate();
	}
	
	
	private void onCreate(){
		
		int currentPlayerNum = pmc.getCurrentPlayerNum();
		
		mode = pmc.getGameMode();
		
		if (mode.equals("GAME")){
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(true);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);

			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					TilesandConnectionPieces[y][x].setEnabled(false);
				}
			}
		}
		
		else if (mode.equals("GAME_WON")){
			JOptionPane.showMessageDialog(frame,"Game is already finished."
					+ "\nPlease play another game or design a new game.");
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(false);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);

			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
				}
			}
		}
		else{
			if (mode.equals("GAME_ROLLDIEACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[ROLL DIE]"
						+ "\nPlayer "+ currentPlayerNum + " can roll the die for an extra turn.");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(true);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
				}
				
			}
			else if (mode.equals("GAME_CONNECTTILESACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[CONNECT TWO TILES]"
						+ "\nPlayer "+ currentPlayerNum + " can select two adjacent tiles and connect"
						+ "\nthem by pressing the \"Connect Tiles\" button");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(true);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}			
			}
			else if (mode.equals("GAME_REMOVECONNECTIONACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[REMOVE CONNECTION PIECE]"
						+ "\nPlayer "+ currentPlayerNum + " can select a connection piece from the board"
						+ "\nand remove it by clicking the \"Remove Connection\" button");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(true);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (y%2 == 1 || x%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
					}
				}
			}
			else if (mode.equals("GAME_TELEPORTACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[TELEPORT]"
						+ "\nPlayer "+ currentPlayerNum + " can select an arbitrary tile that is not your current"
						+ "\ntile and click the Teleport button");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(true);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			else if (mode.equals("GAME_LOSETURNACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[LOSE TURN]"
						+ "\n Player "+ currentPlayerNum + " will lose his/her next turn!");
				try{
					boolean isExecuted = pmc.playLoseTurnActionCard();
					
					if (isExecuted==true){
						drawButton.setEnabled(false);
						rollDieButton.setEnabled(true);
						connectButton.setEnabled(false);
						removeConnectionButton.setEnabled(false);
						teleportButton.setEnabled(false);
						moveWinTileButton.setEnabled(false);
						playerNumToggleList.setEnabled(false);
						moveOtherPlayerButton.setEnabled(false);
						swapPositionButton.setEnabled(false);
						additionalMoveToggleList.setEnabled(false);
						additionalMoveButton.setEnabled(false);
						
						
						for (int y = 0; y < (rowNum+(rowNum-1)); y++){
							for (int x = 0; x < (colNum+(colNum-1)); x++){
								TilesandConnectionPieces[y][x].setEnabled(false);
							}
						}
					}
					else{
						error="You cannot use LoseTurnActionCard!";
					}
				}
				catch (InvalidInputException e){
					error = e.getMessage();
				}
			}
			else if (mode.equals("GAME_MOVEWINTILEACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[Move Win Tile]"
						+ "\nPlayer "+ currentPlayerNum + " can select an arbitrary tile that is not your current tile\n"
						+ "and click the Move Win Tile button to set it as the new win tile");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(true);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			else if (mode.equals("GAME_DEACTIVATEACTIONTILEACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[DEACTIVATE ACTION TILE]"
						+ "\n All active action tiles will be inactive for\n"
						+ "their respective inactivity periods!");
					boolean isExecuted = pmc.playDeactivateActionTileActionCard();
					if (isExecuted==true){
						drawButton.setEnabled(false);
						rollDieButton.setEnabled(false);
						connectButton.setEnabled(false);
						removeConnectionButton.setEnabled(false);
						teleportButton.setEnabled(false);
						moveWinTileButton.setEnabled(false);
						playerNumToggleList.setEnabled(false);
						moveOtherPlayerButton.setEnabled(false);
						swapPositionButton.setEnabled(false);
						additionalMoveToggleList.setEnabled(false);
						additionalMoveButton.setEnabled(false);
						
						
						for (int y = 0; y < (rowNum+(rowNum-1)); y++){
							for (int x = 0; x < (colNum+(colNum-1)); x++){
								TilesandConnectionPieces[y][x].setEnabled(false);
							}
						}
						
						JOptionPane.showMessageDialog(frame,"All active action tiles are now inactive.");
						
					}
					else{
						error="You cannot use DeactivateActionTileActionCard!";
					}
			}
		 if (mode.equals("GAME_REVEALTILEACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[REVEAL TILE]"
						+ "\n Please select a tile to find out what kind of tile it is!");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			
			else if (mode.equals("GAME_MOVEOTHERPLAYERACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[MOVE OTHER PLAYER]"
						+ "\nPlease select a player number from the toggle list "
						+ "\nand click an abitrary tile you would like "
						+ "\nto move the player to before clicking the move other player button");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(true);
				moveOtherPlayerButton.setEnabled(true);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);////////////////////////
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			else if (mode.equals("GAME_SWAPPOSITIONACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[SWAP POSITION]"
						+ "\nPlease select a player number from the combo box above"
						+ "\nto switch position with that player.");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(true);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(true);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);/////////////////////////
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			else if (mode.equals("GAME_ADDITIONALMOVEACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[ADDITIONAL MOVE]"
						+ "Please enter an additional number of moves between 1 to 6.");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(true);
				additionalMoveButton.setEnabled(true);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			
			
			
			
			
		}
		refreshData();
	}
	
	
	
	private void rollDieButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		boolean isExecuted = false;
		
	
		
		
		if (pmc.getGameMode().equals("GAME")){
			isExecuted = pmc.rollDie();
		}
		
		else if (pmc.getGameMode().equals("GAME_ROLLDIEACTIONCARD")){
			try{
				isExecuted = pmc.playRollDieActionCard();
			}
			catch (InvalidInputException e){
				error=e.getMessage();
			}
		}
		
		if (isExecuted==true){

			final int number = pmc.getNumOfMoves();
			
			
			
            rollDieButton.setEnabled(false);
            Timer timer = new Timer(60, new ActionListener() {
                private int counter;
                private int lastRoll;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (counter < 20) {
                        counter++;
                        lastRoll = (int)(Math.random() * 6)+1;  
                        die.setIcon(new ImageIcon(dice[lastRoll-1]));
                    } 
                    else if (counter == 20){
                        lastRoll = number;
                        die.setIcon(new ImageIcon(dice[lastRoll-1]));
                        ((Timer)e.getSource()).stop();
                        JOptionPane.showMessageDialog(frame,"The number you have rolled is " + number + ".");
                    
            			for (Tile aTile: pmc.getPossibleMoves()){ 
            				int possibleMovesX = aTile.getX();
            				int possibleMovesY = aTile.getY();

            				int xOnBoard = possibleMovesX*2;
            				int yOnBoard = possibleMovesY*2;

            				TilesandConnectionPieces[yOnBoard][xOnBoard].setEnabled(true);
            				TilesandConnectionPieces[yOnBoard][xOnBoard].setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
            			}
                    
                    }
                }
            });
            timer.start();
			
			


			
			rollDieButton.setEnabled(false);

		}
		
		else{
			error = "Cannot roll die!";
		}
	}

	
	private void connectButtonActionPerformed(java.awt.event.ActionEvent evt){
		String text = addConnectionText.getText();
		
//		System.out.println("Whole text: "+text);

		text = text.trim();
		addConnectionText.setText("");
		
		boolean isExecuted=false;
		
		error = "";
		
		if (text.split(" ").length>=4){
			int tile1IndX = Integer.parseInt(text.split("\\s")[0]);
			int tile1IndY = Integer.parseInt(text.split("\\s")[1]);
			int tile2IndX = Integer.parseInt(text.split("\\s")[2]);
			int tile2IndY = Integer.parseInt(text.split("\\s")[3]);
			


			try{
				int index1 = 0;
				int index2 = 0;
				for (Tile aTile:pmc.getCurrentGame().getTiles()){
					if ((aTile.getX()==tile1IndX) && (aTile.getY()==tile1IndY)){
						index1 = pmc.getCurrentGame().getTiles().indexOf(aTile);
						break;
					}
				}

				for (Tile aTile:pmc.getCurrentGame().getTiles()){
					if ((aTile.getX()==tile2IndX) && (aTile.getY()==tile2IndY)){
						index2 = pmc.getCurrentGame().getTiles().indexOf(aTile);
						break;
					}
				}

				isExecuted = pmc.playConnectTilesActionCard(pmc.getCurrentGame().getTiles().get(index1), pmc.getCurrentGame().getTiles().get(index2));
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
			
			if (isExecuted == true) {

				int tile1BoardIndX = tile1IndX*2;
				int tile1BoardIndY = tile1IndY*2;
				int tile2BoardIndX= tile2IndX*2;
				int tile2BoardIndY = tile2IndY*2;

				int connectionX = 0;
				int connectionY = 0;

				if (tile1BoardIndX == tile2BoardIndX){
					connectionX = tile1BoardIndX;
					connectionY = (tile1BoardIndY+tile2BoardIndY)/2;
				}

				else if (tile1BoardIndY == tile2BoardIndY){
					connectionY = tile1BoardIndY;
					connectionX = (tile1BoardIndX + tile2BoardIndX)/2;
				}

				if (connectionY%2==1){		
					TilesandConnectionPieces[connectionY][connectionX].setVisible(true);
				}
				else if (connectionX%2==1){
					TilesandConnectionPieces[connectionY][connectionX].setVisible(true);
				}

			}
			
			else {
				error = "You cannot use ConnectTwoTilesActionCard!";
			}
		}
		else {
			try{
				pmc.playConnectTilesActionCard(null, null);
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}
		
		if (error.length()==0){
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(true);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);

			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					TilesandConnectionPieces[y][x].setEnabled(false);
				}
			}
		}
		
		refreshData();
	}
	
	private void removeConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String text = removeConnectionText.getText();
		
		text = text.trim();
		removeConnectionText.setText("");
		
		boolean isExecuted = false;
		
		error = "";
		
		String[] info = new String[4];
		info = text.split(" ");
		
		if (info.length>=4){
			int tile1IndX = Integer.parseInt(info[0]);
			int tile1IndY = Integer.parseInt(info[1]);
			int tile2IndX = Integer.parseInt(info[2]);
			int tile2IndY = Integer.parseInt(info[3]);
		
			try{
				int index1 = 0;
				int index2 = 0;
				for (Tile aTile:pmc.getCurrentGame().getTiles()){
					if ((aTile.getX()==tile1IndX) && (aTile.getY()==tile1IndY)){
						index1 = pmc.getCurrentGame().getTiles().indexOf(aTile);
						break;
					}
				}

				for (Tile aTile:pmc.getCurrentGame().getTiles()){
					if ((aTile.getX()==tile2IndX) && (aTile.getY()==tile2IndY)){
						index2 = pmc.getCurrentGame().getTiles().indexOf(aTile);
						break;
					}
				}
				
				Connection thisConn = pmc.getThisConnection(pmc.getCurrentGame().getTile(index1), pmc.getCurrentGame().getTile(index2));
				
				
				isExecuted = pmc.playRemoveConnectionActionCard(thisConn);
			
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}

			if (isExecuted == true){
				int tile1BoardIndX = tile1IndX*2;
				int tile1BoardIndY = tile1IndY*2;
				int tile2BoardIndX= tile2IndX*2;
				int tile2BoardIndY = tile2IndY*2;

				int connectionX = 0;				
				int connectionY = 0;

				
				if (tile1BoardIndX == tile2BoardIndX){
					connectionX = tile1BoardIndX;
					connectionY = (tile1BoardIndY+tile2BoardIndY)/2;
				}

				else if (tile1BoardIndY == tile2BoardIndY){
					connectionY = tile1BoardIndY;
					connectionX = (tile1BoardIndX + tile2BoardIndX)/2;
				}

				TilesandConnectionPieces[connectionY][connectionX].setVisible(false);

			}
			else{
				error = "You cannot use RemoveConnectionActionCard!";
			}
		}

		else {
			try{
				pmc.playRemoveConnectionActionCard(null);
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}
		
		if (error.length()==0){
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(true);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);

			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					TilesandConnectionPieces[y][x].setEnabled(false);
				}
			}
		}
		refreshData();
	}

	
	private void drawButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String mode = pmc.getGameMode();
		
		if (mode.equals("GAME_ROLLDIEACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[ROLL DIE]"
					+ "\nPlease roll the die for an extra turn");
			
			int currentPlayerNum = pmc.getCurrentPlayerNum();
			JOptionPane.showMessageDialog(frame,"Player " + currentPlayerNum + "'s turn to roll the die.");

			
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(true);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					TilesandConnectionPieces[y][x].setEnabled(false);
				}
			}
			
		}
		else if (mode.equals("GAME_CONNECTTILESACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[CONNECT TWO TILES]"
					+ "\nPlease select two adjacent tiles and connect"
					+ "\nthem by pressing the \"Connect Tiles\" button");
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(false);
			connectButton.setEnabled(true);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					if (x%2 == 1 || y%2 == 1){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
					else{
						TilesandConnectionPieces[y][x].setEnabled(true);
					}
				}
			}			
		}
		
		else if (mode.equals("GAME_REMOVECONNECTIONACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[REMOVE CONNECTION PIECE]"
					+ "\nPlease select a connection piece from the board"
					+ "\nand remove it by clicking the \"Remove Connection\" button");
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(false);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(true);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					if (y%2 == 1 || x%2 == 1){
						TilesandConnectionPieces[y][x].setEnabled(true);
					}
					else{
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
				}
			}
		}
		else if (mode.equals("GAME_TELEPORTACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[TELEPORT]"
					+ "\nPlease select an arbitrary tile that is not your current"
					+ "\ntile and click the Teleport button");
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(false);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(true);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					if (x%2 == 1 || y%2 == 1){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
					else{
						TilesandConnectionPieces[y][x].setEnabled(true);
					}
				}
			}
		}
		else if (mode.equals("GAME_LOSETURNACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[LOSE TURN]"
					+ "\n You will lose your next turn!");
			try{
				boolean isExecuted = pmc.playLoseTurnActionCard();
				if (isExecuted==true){
					drawButton.setEnabled(false);
					rollDieButton.setEnabled(true);
					connectButton.setEnabled(false);
					removeConnectionButton.setEnabled(false);
					teleportButton.setEnabled(false);
					moveWinTileButton.setEnabled(false);
					playerNumToggleList.setEnabled(false);
					moveOtherPlayerButton.setEnabled(false);
					swapPositionButton.setEnabled(false);
					additionalMoveToggleList.setEnabled(false);
					additionalMoveButton.setEnabled(false);
					
					
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
					}					
				}
				else{
					error="You cannot use LoseTurnActionCard!";
				}
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}
		
		else if (mode.equals("GAME_MOVEWINTILEACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[MOVE WIN TILE]"
					+ "\nPlease select an arbitrary tile that is not your current\n"
					+ "tile and click the Move Win Tile button to\n"
					+ "set it as the new win tile");
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(false);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(true);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					if (x%2 == 1 || y%2 == 1){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
					else{
						TilesandConnectionPieces[y][x].setEnabled(true);
					}
				}
			}
		}
		
		else if (mode.equals("GAME_DEACTIVATEACTIONTILEACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[DEACTIVATE ACTION TILE]"
					+ "\n All active action tiles will be inactive for\n"
					+ "their respective inactivity periods!");
			
				boolean isExecuted = pmc.playDeactivateActionTileActionCard();
				if (isExecuted==true){
					drawButton.setEnabled(false);
					rollDieButton.setEnabled(true);
					connectButton.setEnabled(false);
					removeConnectionButton.setEnabled(false);
					teleportButton.setEnabled(false);
					moveWinTileButton.setEnabled(false);
					playerNumToggleList.setEnabled(false);
					moveOtherPlayerButton.setEnabled(false);
					swapPositionButton.setEnabled(false);
					additionalMoveToggleList.setEnabled(false);
					additionalMoveButton.setEnabled(false);
					
					
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
					}
					
					JOptionPane.showMessageDialog(frame,"All active action tiles are now inactive.");
					
				}
				else{
					error="You cannot use DeactivateActionTileActionCard!";
				}
		}
		
		else if (mode.equals("GAME_REVEALTILEACTIONCARD")){
			JOptionPane.showMessageDialog(frame,"[REVEAL TILE]"
					+ "\n Please select a tile to find out what kind of tile it is!");
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(false);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					if (x%2 == 1 || y%2 == 1){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
					else{
						TilesandConnectionPieces[y][x].setEnabled(true);
					}
				}
			}
		}
			else if (mode.equals("GAME_MOVEOTHERPLAYERACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[MOVE OTHER PLAYER]"
						+ "\nPlease select a player from the toggle list"
						+ "\nand click an abitrary tile you would like "
						+ "\nto move the player to before clicking the"
						+ "\n Move Other Button");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(true);
				moveOtherPlayerButton.setEnabled(true);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			else if (mode.equals("GAME_SWAPPOSITIONACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[SWAP POSITION]"
						+ "\nPlease select a player number from the combo box above"
						+ "\nto switch position with that player.");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(true);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(true);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
			}
			
			else if (mode.equals("GAME_ADDITIONALMOVEACTIONCARD")){
				JOptionPane.showMessageDialog(frame,"[ADDITIONAL MOVE]"
						+ "Please enter an additional number of moves between 1 to 6");
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(true);
				additionalMoveButton.setEnabled(true);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (x%2 == 1 || y%2 == 1){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
						else{
							TilesandConnectionPieces[y][x].setEnabled(true);
						}
					}
				}
		}
		
		refreshData();
		
	}

	private void tileClickedActionPerformed(java.awt.event.ActionEvent evt){
		
		error = "";

//		JOptionPane.showMessageDialog(frame,"You clicked " + buttonClicked.getText());	
		int buttonClickedActualX = Integer.parseInt(buttonClicked.getText().split("\\s")[0]);
		int buttonClickedActualY = Integer.parseInt(buttonClicked.getText().split("\\s")[1]);

		boolean isExecuted = false;

		if (pmc.getGameMode() == "GAME_CONNECTTILESACTIONCARD"){
			String clickedFirst = addConnectionText.getText()+" "+buttonClicked.getText();
			addConnectionText.setText(clickedFirst);
			addConnectionText.setForeground(Color.BLACK);
		}
		else if(pmc.getGameMode() == "GAME_TELEPORTACTIONCARD"){
			addConnectionText.setText(buttonClicked.getText());
		}
		else if (pmc.getGameMode() == "GAME_MOVEWINTILEACTIONCARD"){
			newWinTileCoord.clear();
			
			newWinTileCoord.add(new Integer(buttonClickedActualX));
			newWinTileCoord.add(new Integer (buttonClickedActualY));
		}
		else if (pmc.getGameMode() == "GAME_REVEALTILEACTIONCARD"){
			revealTileCoord.clear();
			
			revealTileCoord.add(new Integer(buttonClickedActualX));
			revealTileCoord.add(new Integer (buttonClickedActualY));
			
			error = "";
			boolean revealExecuted = false;
			
				for (int i=0;i<pmc.getTiles().size();i++){
					Tile aTile = pmc.getTiles().get(i);
					if ((aTile.getX()==revealTileCoord.get(0).intValue()) && (aTile.getY()==revealTileCoord.get(1).intValue())){
						revealExecuted = pmc.playRevealTileActionCard(aTile);
						break;
					}
				}	
			
			
			
			if (revealExecuted == true){
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(true);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);
				
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
				}
				
				String tileHint = pmc.getTileHint();
				JOptionPane.showMessageDialog(frame,"This is a(n) " + tileHint + " Tile.");
			}
			
		}
		
		
		else if(pmc.getGameMode() == "GAME_MOVEOTHERPLAYERACTIONCARD"){
			addConnectionText.setText(buttonClicked.getText());
			}
		
		
		
		

		else{
			
			
			///////////     land method   //////////////////////
			int currentPlayerNum = pmc.getCurrentPlayerNum();

			for (Tile aTile : pmc.getTiles()){
				if ( (aTile.getX()==buttonClickedActualX) && (aTile.getY()==buttonClickedActualY) ){
					isExecuted = pmc.land(aTile);
					break;
				}
			}
			
			if (isExecuted==true){

				if (currentPlayerNum==1){
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==Color.RED){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[buttonClickedActualY*2][buttonClickedActualX*2].setBackground(Color.RED);
				}
				else if (currentPlayerNum==2){
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==Color.BLUE){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[buttonClickedActualY*2][buttonClickedActualX*2].setBackground(Color.BLUE);
				}
				else if (currentPlayerNum==3){
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==Color.GREEN){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[buttonClickedActualY*2][buttonClickedActualX*2].setBackground(Color.GREEN);
				}
				else if (currentPlayerNum==4){
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==Color.YELLOW){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[buttonClickedActualY*2][buttonClickedActualX*2].setBackground(Color.YELLOW);
				}


				////// Disabled different buttons based on different Game mode  ///////////////////////////
				mode = pmc.getGameMode();

				if (mode.equals("GAME")){
					drawButton.setEnabled(false);
					rollDieButton.setEnabled(true);
					connectButton.setEnabled(false);
					removeConnectionButton.setEnabled(false);
					teleportButton.setEnabled(false);
					moveWinTileButton.setEnabled(false);
					moveOtherPlayerButton.setEnabled(false);
					playerNumToggleList.setEnabled(false);
					swapPositionButton.setEnabled(false);
					additionalMoveToggleList.setEnabled(false);
					additionalMoveButton.setEnabled(false);


					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
					}
				}
				else if (mode.equals("GAME_WON")){
					drawButton.setEnabled(false);
					rollDieButton.setEnabled(false);
					connectButton.setEnabled(false);
					removeConnectionButton.setEnabled(false);
					teleportButton.setEnabled(false);
					moveWinTileButton.setEnabled(false);
					playerNumToggleList.setEnabled(false);
					moveOtherPlayerButton.setEnabled(false);
					swapPositionButton.setEnabled(false);
					additionalMoveToggleList.setEnabled(false);
					additionalMoveButton.setEnabled(false);

					JOptionPane.showMessageDialog(frame,"[GAME ENDED] Player " + currentPlayerNum+ " has win the game!");

					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
					}
				}
				
				

				else if (mode.equals("GAME_ROLLDIEACTIONCARD")||mode.equals("GAME_CONNECTTILESACTIONCARD")||mode.equals("GAME_REMOVECONNECTIONACTIONCARD")||mode.equals("GAME_TELEPORTACTIONCARD")||mode.equals("GAME_LOSETURNACTIONCARD")
						|| mode.equals("GAME_MOVEWINTILEACTIONCARD") || mode.equals("GAME_DEACTIVATEACTIONTILEACTIONCARD")|| mode.equals("GAME_REVEALTILEACTIONCARD")||mode.equals("GAME_ADDITIONALMOVEACTIONCARD")||mode.equals("GAME_MOVEOTHERPLAYERACTIONCARD")||mode.equals("GAME_SWAPPOSITIONACTIONCARD")){
					drawButton.setEnabled(true);
					rollDieButton.setEnabled(false);
					connectButton.setEnabled(false);
					removeConnectionButton.setEnabled(false);
					teleportButton.setEnabled(false);
					moveWinTileButton.setEnabled(false);
					playerNumToggleList.setEnabled(false);
					moveOtherPlayerButton.setEnabled(false);
					swapPositionButton.setEnabled(false);
					additionalMoveToggleList.setEnabled(false);
					additionalMoveButton.setEnabled(false);

					JOptionPane.showMessageDialog(frame,"You have landed on an Action Tile.\n"
							+ "Please draw an action card!");

					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							TilesandConnectionPieces[y][x].setEnabled(false);
						}
					}
				}
			}
			else{
				error = "You cannot land!";
			}
		}
		refreshData();
	}
	
	private void connectionPieceClickedActionPerformed(java.awt.event.ActionEvent evt){
		int connectionBoardX = Integer.parseInt(connectionClicked.getText().split("\\s")[0]);
		int connectionBoardY = Integer.parseInt(connectionClicked.getText().split("\\s")[1]);
		
		
		
		// 4 Directions
		if (connectionBoardX%2 == 1){
			int leftActualX = (connectionBoardX-1)/2;
			int leftActualY = connectionBoardY/2;

			int rightActualX = (connectionBoardX+1)/2;
			int rightActualY = connectionBoardY/2;		
			
//			JOptionPane.showMessageDialog(frame,"You clicked " + Integer.toString(leftActualX)+","+Integer.toString(leftActualY)+" and "+Integer.toString(rightActualX)+","+Integer.toString(rightActualY));
			connectionClicked = null;
			
			if (pmc.getGameMode() == "GAME_REMOVECONNECTIONACTIONCARD"){
				String removedConnection = removeConnectionText.getText()+" "+leftActualX+" "+leftActualY+" "+rightActualX+" "+rightActualY+" ";
				removeConnectionText.setText(removedConnection);
				removeConnectionText.setForeground(Color.BLACK.darker());
			}
			
		}

		if (connectionBoardY%2 == 1){
			int northActualX = connectionBoardX/2;
			int northActualY = (connectionBoardY-1)/2;
	
			int southActualX = connectionBoardX/2;
			int southActualY = (connectionBoardY+1)/2;
			
//			JOptionPane.showMessageDialog(frame,"You clicked " + Integer.toString(northActualX)+","+Integer.toString(northActualY)+" and "+Integer.toString(southActualX)+","+Integer.toString(southActualY));
			connectionClicked = null;
			
			if (pmc.getGameMode() == "GAME_REMOVECONNECTIONACTIONCARD"){
				String removedConnection = removeConnectionText.getText()+" "+northActualX+" "+northActualY+" "+southActualX+" "+southActualY+" ";
				removeConnectionText.setText(removedConnection);
			}
		}		
	}

	
	private void teleportButtonActionPerformed(java.awt.event.ActionEvent evt){

		error = "";

		boolean isExecuted = false;

		String text = (addConnectionText.getText()).trim();
		addConnectionText.setText("");

		int tileX = -1;
		int tileY = -1;



		if (text.length()<3){
			error = "You must select a tile! ";
		}

		if (error.length()<=0){

			tileX = Integer.parseInt(text.split("\\s")[0]);
			tileY = Integer.parseInt(text.split("\\s")[1]);




			try{
				for (Tile aTile : pmc.getTiles()){
					if ((aTile.getX()==tileX) && (aTile.getY()==tileY)){
						isExecuted = pmc.playTeleportActionCard(aTile);
						break;
					}
				}


			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}

		if (isExecuted==true){
			Color playerColor = new JButton().getBackground();
			int currentPlayerNum = pmc.getCurrentPlayerNum();

			if (currentPlayerNum==1){
				playerColor = Color.RED;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
			}
			else if (currentPlayerNum==2){
				playerColor = Color.BLUE;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
			}
			else if (currentPlayerNum==3){
				playerColor = Color.GREEN;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
			}
			else if (currentPlayerNum==4){
				playerColor = Color.YELLOW;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
			}

			mode = pmc.getGameMode();

			if (mode.equals("GAME")){
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(true);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);

				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
				}
			}
			else if (mode.equals("GAME_WON")){
				drawButton.setEnabled(false);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);

				JOptionPane.showMessageDialog(frame,"[GAME ENDED] Player " + currentPlayerNum+ " has win the game!");

				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
				}
			}

			else if (mode.equals("GAME_ROLLDIEACTIONCARD")||mode.equals("GAME_CONNECTTILESACTIONCARD")||mode.equals("GAME_REMOVECONNECTIONACTIONCARD")||mode.equals("GAME_TELEPORTACTIONCARD")||mode.equals("GAME_LOSETURNACTIONCARD")
					|| mode.equals("GAME_MOVEWINTILEACTIONCARD")|| mode.equals("GAME_DEACTIVATEACTIONTILEACTIONCARD")|| mode.equals("GAME_REVEALTILEACTIONCARD")||mode.equals("GAME_ADDITIONALMOVEACTIONCARD")||mode.equals("GAME_MOVEOTHERPLAYERACTIONCARD")||mode.equals("GAME_SWAPPOSITIONACTIONCARD")){
				drawButton.setEnabled(true);
				rollDieButton.setEnabled(false);
				connectButton.setEnabled(false);
				removeConnectionButton.setEnabled(false);
				teleportButton.setEnabled(false);
				moveWinTileButton.setEnabled(false);
				playerNumToggleList.setEnabled(false);
				moveOtherPlayerButton.setEnabled(false);
				swapPositionButton.setEnabled(false);
				additionalMoveToggleList.setEnabled(false);
				additionalMoveButton.setEnabled(false);

				JOptionPane.showMessageDialog(frame,"You have landed on an Action Tile.\n"
						+ "Please draw an action card!");

				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						TilesandConnectionPieces[y][x].setEnabled(false);
					}
				}
			}

		}

		else{
			error += "You cannot use TeleportActionCard!";
		}

		refreshData();

	}


	
	private void moveWinTileButtonActionPerformed(java.awt.event.ActionEvent evt){
		error = "";
		boolean isExecuted = false;
		try{
			
			if (newWinTileCoord.size()<2){
				isExecuted = pmc.playMoveWinTileActionCard(null);
			}
			else{


				for (int i=0;i<pmc.getTiles().size();i++){
					Tile aTile = pmc.getTiles().get(i);
					if ((aTile.getX()==newWinTileCoord.get(0).intValue()) && (aTile.getY()==newWinTileCoord.get(1).intValue())){
						isExecuted = pmc.playMoveWinTileActionCard(aTile);
						break;
					}
				}	
			}
		}
		
		catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		if (isExecuted == true){
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(true);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			playerNumToggleList.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			additionalMoveToggleList.setEnabled(false);
			additionalMoveButton.setEnabled(false);
			
			for (int y = 0; y < (rowNum+(rowNum-1)); y++){
				for (int x = 0; x < (colNum+(colNum-1)); x++){
					TilesandConnectionPieces[y][x].setEnabled(false);
				}
			}
			
			JOptionPane.showMessageDialog(frame,"The win tile has been moved.");
			
		}

		
		refreshData();
	}
	
	
private void moveOtherPlayerButtonActionPerformed(java.awt.event.ActionEvent evt){
		
		error = "";
	    

		boolean isExecuted = false;

		String text = (addConnectionText.getText()).trim();
		addConnectionText.setText("");
		if (text.split(" ").length>=2){
			int tileX = Integer.parseInt(text.split("\\s")[0]);
			int tileY = Integer.parseInt(text.split("\\s")[1]);
		
		
		try{
			for (Tile aTile : pmc.getTiles()){
				if ((aTile.getX()==tileX) && (aTile.getY()==tileY)){
					isExecuted = pmc.playMoveOtherPlayerActionCard(aTile, playerNumSelected + 1);
					break;
				}
			}

			if (isExecuted==true){
				Color playerColor = new JButton().getBackground();

				if (playerNumSelected +1 ==1){
					playerColor = Color.RED;
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
				}
				else if (playerNumSelected + 1 ==2){
					playerColor = Color.BLUE;
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
				}
				else if (playerNumSelected + 1 ==3){
					playerColor = Color.GREEN;
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
				}
				else if (playerNumSelected + 1==4){
					playerColor = Color.YELLOW;
					for (int y = 0; y < (rowNum+(rowNum-1)); y++){
						for (int x = 0; x < (colNum+(colNum-1)); x++){
							if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
								TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
							}
						}
					}
					TilesandConnectionPieces[tileY*2][tileX*2].setBackground(playerColor);
				}
				
				mode = pmc.getGameMode();
				rollDieButton.setEnabled(true);
				
				int currentPlayerNum = pmc.getCurrentPlayerNum();
	
			}
			
			else{
				error = "You cannot use TeleportActionCard!";
			}
			
		}
		catch (InvalidInputException e){
			error = e.getMessage();
		}
	}
		else{
			try{
				pmc.playMoveOtherPlayerActionCard(null, playerNumSelected);
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
			
		}
	
		refreshData();
	}
	
	
private void swapPositionButtonActionPerformed(java.awt.event.ActionEvent evt){
	error = "";
	boolean isExecuted = false;
	int currentPlayerNum = pmc.getCurrentPlayerNum();
	Tile currentTile = pmc.getCurrentGame().getCurrentPlayer().getCurrentTile();
	int currentTileX = currentTile.getX();
	int currentTileY = currentTile.getY();
	
	if(playerNumSelected < pmc.getPlayers().size() && playerNumSelected >= 0){
	
	try{
	
	if(pmc.getGameMode() == "GAME_SWAPPOSITIONACTIONCARD"){
		
		
		Tile chosenTile = pmc.getCurrentGame().getPlayer(playerNumSelected).getCurrentTile();
		int chosenTileX = chosenTile.getX();
		int chosenTileY = chosenTile.getY();
		
		isExecuted = pmc.playSwapPositionActionCard(playerNumSelected + 1);
		
		if (isExecuted==true){
			Color playerColor = new JButton().getBackground();

			if (currentPlayerNum==1){
				playerColor = Color.RED;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor)
						{
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[chosenTileY*2][chosenTileX*2].setBackground(playerColor);
			}
			else if (currentPlayerNum==2){
				playerColor = Color.BLUE;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[chosenTileY*2][chosenTileX*2].setBackground(playerColor);
			}
			else if (currentPlayerNum==3){
				playerColor = Color.GREEN;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[chosenTileY*2][chosenTileX*2].setBackground(playerColor);
			}
			else if (currentPlayerNum==4){
				playerColor = Color.YELLOW;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[chosenTileY*2][chosenTileX*2].setBackground(playerColor);
			}
			
			
			if (playerNumSelected + 1==1){
				playerColor = Color.RED;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor)
						{
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[currentTileY*2][currentTileX*2].setBackground(playerColor);
			}
			else if (playerNumSelected +1 ==2){
				playerColor = Color.BLUE;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[currentTileY*2][currentTileX*2].setBackground(playerColor);
			}
			else if (playerNumSelected +1 ==3){
				playerColor = Color.GREEN;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[currentTileY*2][currentTileX*2].setBackground(playerColor);
			}
			else if (playerNumSelected +1==4){
				playerColor = Color.YELLOW;
				for (int y = 0; y < (rowNum+(rowNum-1)); y++){
					for (int x = 0; x < (colNum+(colNum-1)); x++){
						if (TilesandConnectionPieces[y][x].getBackground()==playerColor){
							TilesandConnectionPieces[y][x].setBackground(new JButton().getBackground());
						}
					}
				}
				TilesandConnectionPieces[currentTileY*2][currentTileX*2].setBackground(playerColor);
			}
			
			drawButton.setEnabled(false);
			rollDieButton.setEnabled(true);
			connectButton.setEnabled(false);
			removeConnectionButton.setEnabled(false);
			teleportButton.setEnabled(false);
			moveWinTileButton.setEnabled(false);
			swapPositionButton.setEnabled(false);
			moveOtherPlayerButton.setEnabled(false);
			
			}
		}
	}
	catch(InvalidInputException e){
		error = e.getMessage();
	}
	}
	else{
		try{
			pmc.playSwapPositionActionCard(playerNumSelected + 1);
		}
		catch(InvalidInputException e){
			error = e.getMessage();
		}
	}
	refreshData();
}
	
private void additionalMoveButtonActionPerformed(java.awt.event.ActionEvent evt){
	
	error = "";
	boolean isExecuted = false;
	

   if (pmc.getGameMode().equals("GAME_ADDITIONALMOVEACTIONCARD")){
		try{
			isExecuted = pmc.playAdditionalMoveActionCard(additionalMoveSelected + 1);
		}
		catch (InvalidInputException e){
			error=e.getMessage();
		}
	}
	
	if (isExecuted==true){

		for (Tile aTile: pmc.getPossibleMoves()){ //Change!!!!!!!!!!!!!!!*******************************************/
			int possibleMovesX = aTile.getX();
			int possibleMovesY = aTile.getY();

			int xOnBoard = possibleMovesX*2;
			int yOnBoard = possibleMovesY*2;

			TilesandConnectionPieces[yOnBoard][xOnBoard].setEnabled(true);
			TilesandConnectionPieces[yOnBoard][xOnBoard].setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
			
		}

		JOptionPane.showMessageDialog(frame,"The number you entered is " + (additionalMoveSelected + 1) + ".");

		rollDieButton.setEnabled(false);
}

}

	
	
		
	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt){
		JOptionPane.showMessageDialog(PlayModePage.this,"Saved!");
		pmc.save();
	}
	
	private void loadButtonActionPerformed(java.awt.event.ActionEvent evt){
		new StartMenu();
		frame.setVisible(false);
	}

}
