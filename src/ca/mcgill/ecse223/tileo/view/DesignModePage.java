package ca.mcgill.ecse223.tileo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayModeController;
import ca.mcgill.ecse223.tileo.controller.DesignModeController;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;

public class DesignModePage{

	private static final long serialVersionUID = 1L;

	//UI element
	private JFrame frame;
	private JPanel menuPanel;
	
	private JPanel boardPanel;
	private JPanel boardTop;
	private JPanel boardCenter = new JPanel(new GridBagLayout());
	private JPanel boardBottom = new JPanel(new GridBagLayout());
	
	private JLabel p1Label;
	private JLabel p2Label;
	private JLabel p3Label;
	private JLabel p4Label;
	private JLabel actionTilesLegend;
	private JLabel winTileLegend;
	
	private int rowMax = 13;
	private int colMax = 13;
	JButton[][] TilesandConnectionPieces = new JButton[rowMax+(rowMax-1)][colMax+(colMax-1)];
	private JButton buttonClicked;
	
	private JLabel errorMessage;

	//Player
	private JLabel numberOfPlayerLabel;
	private JComboBox<String> numberOfPlayerToggleList;
	private JButton addPlayerButton;

	//Tile
	private JLabel addTileLabel;
	private JLabel xCoordinateLabel;
	private JTextField xCoordinateTextField;
	private JLabel yCoordinateLabel;
	private JTextField yCoordinateTextField;
	private JButton addTileButton;

	
	//Connect Tile
	private JLabel connectTileLabel;
	private JLabel tile1Label;
	private JComboBox<String> tile1ToggleList;
	private JLabel tile2Label;
	private JComboBox<String> tile2ToggleList;
	private JLabel removeConnectionLabel;
	private JComboBox<String> removeConnectionToggleList;
	private JButton removeConnectionButton;
	private JButton addConnectionButton;

	//hidden tile
	private JLabel setTileLabel;
	private JComboBox<String> tileToggleList;
	private JButton setHiddenTileButton;
	private JButton actionTileButton;
	private JLabel inactivityPeriodLabel;
	private JTextField inactivityPeriodTextField;
	private JButton removeTileButton;

	//starting tile
	private JLabel startTileLabel;
	private JComboBox<String> startTileToggleList;
	private JButton player1StartButton;
	private JButton player2StartButton;
	private JButton player3StartButton;
	private JButton player4StartButton;

	

	//action card
	private JLabel actionCardLabel;
	private JLabel RDACLabel;
	private JTextField RDACTextField;
	private JLabel CTACLabel;
	private JTextField CTACTextField;
	private JLabel RCACLabel;
	private JTextField RCACTextField;
	private JLabel TACLabel;
	private JTextField TACTextField;
	private JLabel LTACLabel;
	private JTextField LTACTextField;
	
	private JLabel AMACLabel;
	private JTextField AMACTextField;
	private JLabel DATACLabel;
	private JTextField DATACTextField;
	private JLabel MOPACLabel;
	private JTextField MOPACTextField;
	private JLabel MWTACLabel;
	private JTextField MWTACTextField;
	private JLabel RTACLabel;
	private JTextField RTACTextField;
	private JLabel SPACLabel;
	private JTextField SPACTextField;
//	private JLabel WTHACLabel;
//	private JTextField WTHACTextField;
	
	private JButton actionCardButton;

	//save and load
	private JButton saveButton;
	private JButton loadButton;


	// data elements
	private String error = "";
	// toggle number of player
	private Integer selectedNumberOfPlayer = -1;
	// toggle tile
	private Integer selectedTile1 = -1;
	private HashMap<Integer, Tile> tile1;

	private Integer selectedTile2 = -1;
	private HashMap<Integer, Tile> tile2;
	// toggle connection
	private Integer selectedConnection = -1;
	private HashMap<Integer, Connection> connection;
	// toggle set tile
	private Integer selectedTile = -1;
	private HashMap<Integer, Tile> tile;
	// toggle start tile
	private Integer selectedStartTile = -1;
	private HashMap<Integer, Tile> startTile;

	PlayModeController pmc = new PlayModeController();

	//create new form DesignModePage
	public DesignModePage() {
		initComponents();
		refreshData();
	}

	

	public void initComponents() {
		
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		
		menuPanel = new JPanel();
		menuPanel.setBackground(Color.BLACK);
		
		//top image
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		//player
		numberOfPlayerLabel = new JLabel();
		numberOfPlayerToggleList = new JComboBox<String>(new String[0]);
		numberOfPlayerToggleList.setBackground(Color.WHITE);
		numberOfPlayerToggleList.setForeground(Color.BLACK);
		numberOfPlayerToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		//numberOfPlayerToggleList.addItem("Please select");
		numberOfPlayerToggleList.addItem("2");
		numberOfPlayerToggleList.addItem("3");
		numberOfPlayerToggleList.addItem("4");
	
		numberOfPlayerToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedNumberOfPlayer = cb.getSelectedIndex();
			}
		});		

		addPlayerButton = new JButton();
				addPlayerButton.addActionListener(new java.awt.event.ActionListener(){		
			public void actionPerformed(java.awt.event.ActionEvent evt){
				addPlayerButtonActionPerformed(evt);
			}	
		});
		
		DesignModeController dmc = new DesignModeController();
		if (dmc.getPlayers().size()!=0){
			addPlayerButton.setEnabled(false);
		}

		//tile
		addTileLabel = new JLabel();
		xCoordinateLabel = new JLabel();
		xCoordinateTextField = new JTextField();
		xCoordinateTextField.setBackground(Color.WHITE);
		xCoordinateTextField.setForeground(Color.BLACK);
		xCoordinateTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));


		yCoordinateLabel = new JLabel();
		yCoordinateTextField = new JTextField();
		yCoordinateTextField.setBackground(Color.WHITE);
		yCoordinateTextField.setForeground(Color.BLACK);
		yCoordinateTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));



		

		//Add connection
		connectTileLabel = new JLabel();
		tile1Label = new JLabel();
		tile1ToggleList = new JComboBox<String>(new String[0]);
		tile1ToggleList.setBackground(Color.WHITE);
		tile1ToggleList.setForeground(Color.BLACK);
		tile1ToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		tile1ToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTile1 = cb.getSelectedIndex();
			}
		});		

		
		tile2Label = new JLabel();
		tile2ToggleList = new JComboBox<String>(new String[0]);
		tile2ToggleList.setBackground(Color.WHITE);
		tile2ToggleList.setForeground(Color.BLACK);
		tile2ToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		tile2ToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTile2 = cb.getSelectedIndex();
			}
		});	
	

		// Remove connection
		removeConnectionLabel = new JLabel();
		removeConnectionToggleList = new JComboBox<String>(new String[0]);
		removeConnectionToggleList.setBackground(Color.WHITE);
		removeConnectionToggleList.setForeground(Color.BLACK);
		removeConnectionToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		removeConnectionToggleList.addItem("Please select");
		removeConnectionToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedConnection = cb.getSelectedIndex();
			}
		});	
		removeConnectionButton = new JButton();
		addConnectionButton = new JButton();
		

		setHiddenTileButton = new JButton();
		setHiddenTileButton.setText("Set Hidden Tile");
		setHiddenTileButton.setForeground(Color.BLACK);
		setHiddenTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hiddenTileButtonActionPerformed(evt);
			}
		});
		
		
		
		//set tile
		setTileLabel = new JLabel();
		tileToggleList = new JComboBox<String>(new String[0]);
		tileToggleList.setBackground(Color.WHITE);
		tileToggleList.setForeground(Color.BLACK);
		tileToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		tileToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTile = cb.getSelectedIndex();
			}
		});	
		
		inactivityPeriodLabel = new JLabel();
		inactivityPeriodLabel.setText("Inactivity Period:");
		inactivityPeriodLabel.setForeground(Color.WHITE);
		
		inactivityPeriodTextField = new JTextField();
		inactivityPeriodTextField.setBackground(Color.WHITE);
		inactivityPeriodTextField.setForeground(Color.BLACK);
		inactivityPeriodTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		
		if (dmc.gameHasWinTile()==true){
			setHiddenTileButton.setEnabled(false);
		}

		//starting tile
		startTileLabel = new JLabel();
		startTileToggleList = new JComboBox<String>(new String[0]);
		startTileToggleList.setBackground(Color.WHITE);
		startTileToggleList.setForeground(Color.BLACK);
		startTileToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		startTileToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedStartTile = cb.getSelectedIndex();
			}
		});	
		
		player1StartButton = new JButton();
		player2StartButton = new JButton();
		player3StartButton = new JButton();
		player4StartButton = new JButton();


		//action card
		actionCardLabel = new JLabel();
		actionCardLabel.setText("Number of Action Cards");
		actionCardLabel.setForeground(Color.CYAN);
		
		RDACLabel = new JLabel();
		RDACLabel.setText("Roll Die:");
		RDACLabel.setForeground(Color.WHITE);

		RDACTextField = new JTextField();
		RDACTextField.setBackground(Color.WHITE);
		RDACTextField.setForeground(Color.BLACK);
		RDACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		CTACLabel = new JLabel();
		CTACTextField = new JTextField();
		CTACLabel.setText("Connect Tiles:");
		CTACLabel.setForeground(Color.WHITE);
	
		CTACTextField.setBackground(Color.WHITE);
		CTACTextField.setForeground(Color.BLACK);
		CTACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		RCACLabel = new JLabel();
		RCACTextField = new JTextField();
		RCACLabel.setText("Remove Connection:");
		RCACLabel.setForeground(Color.WHITE);

		RCACTextField.setBackground(Color.WHITE);
		RCACTextField.setForeground(Color.BLACK);
		RCACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		TACLabel = new JLabel();
		TACTextField = new JTextField();
		TACLabel.setText("Teleport:");
		TACLabel.setForeground(Color.WHITE);
		
		TACTextField.setBackground(Color.WHITE);
		TACTextField.setForeground(Color.BLACK);
		TACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		LTACLabel = new JLabel();
		LTACTextField = new JTextField();
		LTACLabel.setText("Lose Turn:");
		LTACLabel.setForeground(Color.WHITE);
				
		LTACTextField.setBackground(Color.WHITE);
		LTACTextField.setForeground(Color.BLACK);
		LTACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		AMACLabel = new JLabel();
		AMACTextField = new JTextField();
		AMACLabel.setText("Additional Move:");
		AMACLabel.setForeground(Color.WHITE);
				
		AMACTextField.setBackground(Color.WHITE);
		AMACTextField.setForeground(Color.BLACK);
		AMACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		
		DATACLabel = new JLabel();
		DATACTextField = new JTextField();
		DATACLabel.setText("Deactivate Action Tile:");
		DATACLabel.setForeground(Color.WHITE);
				
		DATACTextField.setBackground(Color.WHITE);
		DATACTextField.setForeground(Color.BLACK);
		DATACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		MOPACLabel = new JLabel();
		MOPACTextField = new JTextField();
		MOPACLabel.setText("Move Other Player:");
		MOPACLabel.setForeground(Color.WHITE);
				
		MOPACTextField.setBackground(Color.WHITE);
		MOPACTextField.setForeground(Color.BLACK);
		MOPACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		
		MWTACLabel = new JLabel();
		MWTACTextField = new JTextField();
		MWTACLabel.setText("Move Win Tile:");
		MWTACLabel.setForeground(Color.WHITE);
				
		MWTACTextField.setBackground(Color.WHITE);
		MWTACTextField.setForeground(Color.BLACK);
		MWTACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		RTACLabel = new JLabel();
		RTACTextField = new JTextField();
		RTACLabel.setText("Reveal Tile:");
		RTACLabel.setForeground(Color.WHITE);
				
		RTACTextField.setBackground(Color.WHITE);
		RTACTextField.setForeground(Color.BLACK);
		RTACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));

		SPACLabel = new JLabel();
		SPACTextField = new JTextField();
		SPACLabel.setText("Swap Position:");
		SPACLabel.setForeground(Color.WHITE);
				
		SPACTextField.setBackground(Color.WHITE);
		SPACTextField.setForeground(Color.BLACK);
		SPACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
//
//		WTHACLabel = new JLabel();
//		WTHACTextField = new JTextField();
//		WTHACLabel.setText("Win Tile Hint:");
//		WTHACLabel.setForeground(Color.WHITE);
//				
//		WTHACTextField.setBackground(Color.WHITE);
//		WTHACTextField.setForeground(Color.BLACK);
//		WTHACTextField.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));


		//player
		numberOfPlayerLabel.setText("Number of Players:");
		numberOfPlayerLabel.setForeground(Color.CYAN);
		addPlayerButton.setText("Add Players");
		addPlayerButton.setForeground(Color.BLACK);


		//tile
		addTileLabel.setText("Add Tile:");
		addTileLabel.setForeground(Color.CYAN);
		xCoordinateLabel.setText("x coordinate:");
		xCoordinateLabel.setForeground(Color.WHITE);
		yCoordinateLabel.setText("y coordinate:");
		yCoordinateLabel.setForeground(Color.WHITE);

		
		addTileButton = new JButton();
		addTileButton.setText("Add Tile");
		addTileButton.setForeground(Color.BLACK);
		addTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addTileButtonActionPerformed(evt);
			}
		});

		
		//connection
		connectTileLabel.setText("Add Connection:");
		connectTileLabel.setForeground(Color.CYAN);
		tile1Label.setText("Tile 1:");
		tile1Label.setForeground(Color.WHITE);
		tile2Label.setText("Tile 2:");
		tile2Label.setForeground(Color.WHITE);
		
		addConnectionButton.setText("Add Connection");
		addConnectionButton.setForeground(Color.BLACK);
		addConnectionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addConnectionButtonActionPerformed(evt);
			}

		});

				
		removeConnectionLabel.setText("Remove Connection:");
		removeConnectionLabel.setForeground(Color.CYAN);
		removeConnectionButton.setText("Remove Connection");
		removeConnectionButton.setForeground(Color.BLACK);
		removeConnectionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeConnectionButtonActionPerformed(evt);
			}
		});


		//set action tile
		setTileLabel.setText("Set and Remove Tile:");
		setTileLabel.setForeground(Color.CYAN);
		
		actionTileButton = new JButton();
		actionTileButton.setText("Set Action Tile");
		actionTileButton.setForeground(Color.BLACK);
		actionTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionTileButtonActionPerformed(evt);
			}
		});

		
        //set hidden tile
		//********************************************************************************************//
		
		removeTileButton = new JButton();
		removeTileButton.setText("Remove Tile");
		removeTileButton.setForeground(Color.BLACK);
		removeTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeTileButtonActionPerformed(evt);
			}
		});

		

		//starting tile
		startTileLabel.setText("Starting Position:");
		startTileLabel.setForeground(Color.CYAN);
		player1StartButton.setText("Player 1");
		player1StartButton.setForeground(Color.BLACK);
		player1StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				player1StartButtonActionPerformed(evt);
			}
		});

		
		player2StartButton.setText("Player 2");
		player2StartButton.setForeground(Color.BLACK);
		player2StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				player2StartButtonActionPerformed(evt);
			}
		});


		player3StartButton.setText("Player 3");
		player3StartButton.setForeground(Color.BLACK);
		player3StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				player3StartButtonActionPerformed(evt);
			}
		});

		

		player4StartButton.setText("Player 4");
		player4StartButton.setForeground(Color.BLACK);
		player4StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				player4StartButtonActionPerformed(evt);
			}
		});

		
		actionCardButton = new JButton();
		actionCardButton.setText("Set Action Card");
		actionCardButton.setForeground(Color.BLACK);
		actionCardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionCardButtonActionPerformed(evt);
			}
		});
	

		//save and load
		saveButton = new JButton();
		saveButton.setText("SAVE");
		saveButton.setForeground(Color.BLACK);
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

		loadButton = new JButton();
		loadButton.setText("LOAD");
		loadButton.setForeground(Color.BLACK);
		loadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadButtonActionPerformed(evt);
			}
		});

		

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("DESIGN MODE");
		frame.setResizable(false);

		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineMiddle1 = new JSeparator();
		JSeparator horizontalLineMiddle2 = new JSeparator();
		JSeparator horizontalLineMiddle3 = new JSeparator();
		JSeparator horizontalLineMiddle4 = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();

		GroupLayout layout = new GroupLayout(menuPanel);
		menuPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addComponent(horizontalLineTop)
				.addComponent(horizontalLineMiddle1)
				.addComponent(horizontalLineMiddle2)
				.addComponent(horizontalLineMiddle3)
				.addComponent(horizontalLineMiddle4)
				.addComponent(horizontalLineBottom)


				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(numberOfPlayerLabel)
								.addComponent(addTileLabel)
								.addComponent(xCoordinateLabel)
								.addComponent(setTileLabel)
								.addComponent(startTileLabel)
								.addComponent(player1StartButton)
								.addComponent(connectTileLabel)
								.addComponent(tile1Label)
								.addComponent(removeConnectionLabel)
								.addComponent(actionCardLabel)
								.addComponent(RDACLabel)
								.addComponent(RCACLabel)
								.addComponent(LTACLabel)
								.addComponent(LTACLabel)
								.addComponent(AMACLabel)
								.addComponent(MOPACLabel)
								.addComponent(SPACLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(numberOfPlayerToggleList)
								.addComponent(xCoordinateTextField,130,130,130)
								.addComponent(tileToggleList)
								.addComponent(actionTileButton)
								.addComponent(startTileToggleList)
								.addComponent(player2StartButton)
								.addComponent(tile1ToggleList)
								.addComponent(removeConnectionToggleList)
								.addComponent(RDACTextField,130,130,130)
								.addComponent(RCACTextField,130,130,130)
								.addComponent(LTACTextField,130,130,130)
								.addComponent(AMACTextField,130,130,130)
								.addComponent(MOPACTextField,130,130,130)
								.addComponent(SPACTextField,130,130,130))
						.addGroup(layout.createParallelGroup()
								.addComponent(yCoordinateLabel)
								.addComponent(inactivityPeriodLabel)
								.addComponent(setHiddenTileButton)
								.addComponent(player3StartButton)
								.addComponent(tile2Label)
								.addComponent(CTACLabel)
								.addComponent(TACLabel)
								.addComponent(MWTACLabel)
								.addComponent(DATACLabel)
								.addComponent(RTACLabel)
								.addComponent(saveButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(addPlayerButton)
								.addComponent(yCoordinateTextField,130,130,130)
								.addComponent(addTileButton)
								.addComponent(inactivityPeriodTextField,130,130,130)
								.addComponent(removeTileButton)
								.addComponent(player4StartButton)
								.addComponent(tile2ToggleList)
								.addComponent(addConnectionButton)
								.addComponent(removeConnectionButton)
								.addComponent(CTACTextField,130,130,130)
								.addComponent(TACTextField,130,130,130)
								.addComponent(MWTACTextField,130,130,130)
								.addComponent(DATACTextField,130,130,130)
								.addComponent(RTACTextField,130,130,130)
								.addComponent(actionCardButton)
								.addComponent(loadButton))
						)
				);		
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(numberOfPlayerLabel)
						.addComponent(numberOfPlayerToggleList)
						.addComponent(addPlayerButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(addTileLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(xCoordinateLabel)
						.addComponent(xCoordinateTextField,21,21,21)
						.addComponent(yCoordinateLabel)
						.addComponent(yCoordinateTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(addTileButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle1))
				.addGroup(layout.createParallelGroup()
						.addComponent(setTileLabel)
						.addComponent(tileToggleList)
						.addComponent(inactivityPeriodLabel)
						.addComponent(inactivityPeriodTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(actionTileButton)
						.addComponent(setHiddenTileButton)
						.addComponent(removeTileButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineMiddle4))
				.addGroup(layout.createParallelGroup()
						.addComponent(startTileLabel)
						.addComponent(startTileToggleList))
				.addGroup(layout.createParallelGroup()
						.addComponent(player1StartButton)
						.addComponent(player2StartButton)
						.addComponent(player3StartButton)
						.addComponent(player4StartButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addComponent(connectTileLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(tile1Label)
								.addComponent(tile1ToggleList)
								.addComponent(tile2Label)
								.addComponent(tile2ToggleList))
						.addGroup(layout.createParallelGroup()
								.addComponent(addConnectionButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineMiddle2))
						.addGroup(layout.createParallelGroup()
								.addComponent(removeConnectionLabel)
								.addComponent(removeConnectionToggleList)
								.addComponent(removeConnectionButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineMiddle3))
				.addGroup(layout.createParallelGroup()
						.addComponent(actionCardLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(RDACLabel)
						.addComponent(RDACTextField,21,21,21)
						.addComponent(CTACLabel)
						.addComponent(CTACTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(RCACLabel)
						.addComponent(RCACTextField,21,21,21)
						.addComponent(TACLabel)
						.addComponent(TACTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(LTACLabel)
						.addComponent(LTACTextField,21,21,21)
						.addComponent(MWTACLabel)
						.addComponent(MWTACTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(AMACLabel)
						.addComponent(AMACTextField,21,21,21)
						.addComponent(DATACLabel)
						.addComponent(DATACTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(MOPACLabel)
						.addComponent(MOPACTextField,21,21,21)
						.addComponent(RTACLabel)
						.addComponent(RTACTextField,21,21,21))
				.addGroup(layout.createParallelGroup()
						.addComponent(SPACLabel)
						.addComponent(SPACTextField,21,21,21))			
				.addGroup(layout.createParallelGroup()
						.addComponent(actionCardButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(saveButton)
						.addComponent(loadButton))
				);
		
		
		
		///////////////////////////////////// Game Board Preview
		boardPanel = new JPanel(new BorderLayout());
		boardPanel.setBackground(Color.BLACK);

		// Title
		boardTop = new JPanel (new GridBagLayout());
		boardTop.setBackground(Color.BLACK);
		
		GridBagConstraints topSpace = new GridBagConstraints();
		topSpace.insets = new Insets(5, 0, 0, 0);  // Button spacings (top,left,bottom,right)
		
		topSpace.gridx = 0;
		topSpace.gridy = 0;
		boardTop.add((new JLabel (new ImageIcon("designModeTitle.jpg"))),topSpace); //Add an image to the topPanel
		
		
		topSpace.insets = new Insets(1, 0, 1, 0);  // Button spacings (top,left,bottom,right)
		actionTilesLegend = new JLabel("Cyan Border - Action Tiles");
		actionTilesLegend.setForeground(Color.CYAN.brighter());
		actionTilesLegend.setFont(new Font("Georgia", Font.BOLD, 13));
		
		topSpace.gridx = 0;
		topSpace.gridy = 1;
		boardTop.add(actionTilesLegend,topSpace);
		
		
		winTileLegend = new JLabel("Orange Border - Win Tile");
		winTileLegend.setForeground(Color.ORANGE);
		winTileLegend.setFont(new Font("Georgia", Font.BOLD, 13));
		
		topSpace.gridx = 0;
		topSpace.gridy = 2;
		boardTop.add(winTileLegend,topSpace);
		
		
		///////////////// Board Center //////////////////
		Font boardFont = new Font("Elephant", Font.BOLD, 15);
		
		boardCenter.setBackground(Color.BLACK);
		
		// Create a border with title for center panel
		boardCenter.setBorder(BorderFactory.createTitledBorder(null, "TILE-O", 0, 0, boardFont, Color.WHITE));
				
		printBoard();
		
		
//	  	 Playing pieces
		boardBottom = new JPanel(new GridBagLayout());
		boardBottom.setBackground(Color.BLACK);
		
		boardPanel.add(boardTop,BorderLayout.NORTH);
		boardPanel.add(boardCenter,BorderLayout.CENTER);
		boardPanel.add(boardBottom,BorderLayout.SOUTH);
		
		
		/////////////////////////////////////////////////////////
		
		frame.add(menuPanel,BorderLayout.WEST);
		frame.add(boardPanel,BorderLayout.EAST);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		refreshBoard();
	}


	private void refreshData(){	
		// error
				DesignModeController dmc = new DesignModeController();		
				//errorMessage.setText(error);
				if (error.trim().length()>0){
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,error,"Error",JOptionPane.INFORMATION_MESSAGE);
				}
				if (dmc.getPlayers().size()!=0){
					addPlayerButton.setEnabled(false);
				}
				if (dmc.gameHasWinTile()==true){
					setHiddenTileButton.setEnabled(false);
				}
				else{
					setHiddenTileButton.setEnabled(true);
				}

				if (error == null || error.length() == 0) {
					// Add Tile textfield
					xCoordinateTextField.setText("");
					yCoordinateTextField.setText("");
					
					inactivityPeriodTextField.setText("");

					// Action card textfield
					RDACTextField.setText("");
					CTACTextField.setText("");
					RCACTextField.setText("");
					TACTextField.setText("");
					LTACTextField.setText("");
					
					
					AMACTextField.setText("");
					DATACTextField.setText("");
					MOPACTextField.setText("");
					MWTACTextField.setText("");
					RTACTextField.setText("");
					SPACTextField.setText("");
					
					
					// toggle number of players
					selectedNumberOfPlayer = -1;
					numberOfPlayerToggleList.setSelectedIndex(selectedNumberOfPlayer); // The default index selected is -1

					// toggle Tile 1
					tile1 = new HashMap<Integer, Tile>();
					tile1ToggleList.removeAllItems();
					
					Integer index = 0;						
						for (Tile tile : dmc.getTiles()) {  // get the tile
							tile1.put(index, tile);
							tile1ToggleList.addItem("(" + tile.getX() + "," + tile.getY() + ")"); // put in x and y coordinate in certain format
							index++;
						};

					selectedTile1 = -1;
					tile1ToggleList.setSelectedIndex(selectedTile1); // The default index selected is -1

					// toggle Tile 2
					tile2 = new HashMap<Integer, Tile>();
					tile2ToggleList.removeAllItems();
					
					index = 0;		
					//This if statement should be removed after the start menu is set up 
					for (Tile tile : dmc.getTiles()) {  // get the tile
						tile2.put(index, tile);
						tile2ToggleList.addItem("(" + tile.getX() + "," + tile.getY() + ")"); // put in x and y coordinate in certain format
						index++;
					};

					selectedTile2 = -1;
					tile2ToggleList.setSelectedIndex(selectedTile2); // The default index selected is -1

					
					// toggle Connection
					connection = new HashMap<Integer, Connection>();
					removeConnectionToggleList.removeAllItems();
					index = 0;
						for (Connection aConnection : dmc.getConnections()){
							connection.put(index, aConnection);
							removeConnectionToggleList.addItem("(" + aConnection.getTile(0).getX() + "," + aConnection.getTile(0).getY() + ")" + "-" + "(" + aConnection.getTile(1).getX() + "," + aConnection.getTile(1).getY() + ")" );
						};
						
					selectedConnection = -1;
					removeConnectionToggleList.setSelectedIndex(selectedConnection); // The default index selected is -1
				
					// toggle Set Tile
					tile = new HashMap<Integer, Tile>();
					tileToggleList.removeAllItems();
					
					index = 0;
						for (Tile aTile : dmc.getTiles()) {  // get the tile
							tile.put(index, aTile);
							tileToggleList.addItem("(" + aTile.getX() + "," + aTile.getY() + ")"); // put in x and y coordinate in certain format
							index++;
						};
				
					selectedTile = -1;
					tileToggleList.setSelectedIndex(selectedTile); // The default index selected is -1

					// toggle starting position
					startTile = new HashMap<Integer, Tile>();
					startTileToggleList.removeAllItems();
					
					index = 0;
						for (Tile aTile : dmc.getTiles()) {  // get the tile
							startTile.put(index, aTile);
							startTileToggleList.addItem("(" + aTile.getX() + "," + aTile.getY() + ")"); // put in x and y coordinate in certain format
							index++;
						};

					selectedStartTile = -1;
					startTileToggleList.setSelectedIndex(selectedStartTile); // The default index selected is -1
				}
				frame.pack();
	}
	

	private void addPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if ( selectedNumberOfPlayer<0 ){
			error = "Please select a number of players.";
		}
		
		if (error.length()==0){
			try{
				int numOfPlayers = Integer.parseInt((String) numberOfPlayerToggleList.getSelectedItem());
				DesignModeController dmc = new DesignModeController();
				dmc.createPlayer(numOfPlayers);	
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		refreshPlayerPanel();
	}


	private void addTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if (xCoordinateTextField.getText().equals("") || yCoordinateTextField.getText().equals("")){
			error = "Invalid tile coordinate(s)!";
		}
		else if ( !xCoordinateTextField.getText().matches("[0-9]+") || !yCoordinateTextField.getText().matches("[0-9]+") ){
			error = "You must enter a valid number.";
		}

		if (error.length()==0){
			try{
				DesignModeController dmc = new DesignModeController();
				int x = Integer.parseInt(xCoordinateTextField.getText());
				int y = Integer.parseInt(yCoordinateTextField.getText());
				dmc.createTile(x, y);
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		printBoard();
		refreshBoard();
	}


	private void addConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if ( selectedTile1<0 || selectedTile2<0 ){
			error = "You must select two tiles!";
		}
		if (error.length()==0){
			try{
				DesignModeController dmc = new DesignModeController();
			    dmc.connectTile(dmc.getTiles().get(tile1ToggleList.getSelectedIndex()) , dmc.getTiles().get(tile2ToggleList.getSelectedIndex()) );
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		printBoard();
		refreshBoard();
	}


	private void removeConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";

		if ( selectedConnection<0 ){
			error = "You must select a connection to remove!";
		}
		if (error.length()==0){
			DesignModeController dmc = new DesignModeController();
			try{
				dmc.disconnectTiles(dmc.getConnections().get(removeConnectionToggleList.getSelectedIndex()));
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		printBoard();
		refreshBoard();
	}
	

	private void actionTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if ( selectedTile < 0 ){
			error = "Please select a tile to set it to an action tile.";
		}		
		
		if (inactivityPeriodTextField.getText().equals("")){
			error = error + "You must enter a valid inactivity period for action tile.";
		}
		else if (!inactivityPeriodTextField.getText().matches("[0-9]+") ){
			error = error + "Invalid inactivity period for action tile.";
		}
		
		if ( error.length() == 0){
			try{
				DesignModeController dmc = new DesignModeController ();
				TileO tileo = TileOApplication.getTileO();
				Game currentGame  = tileo.getCurrentGame();
				Tile newTile = currentGame.getTile(selectedTile);
	
				int inactivityPeriod = Integer.parseInt(inactivityPeriodTextField.getText());
				dmc.setActionTile(newTile,inactivityPeriod);
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		printBoard();
		refreshBoard();
	}

	
	private void hiddenTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if (selectedTile < 0 ){
			error = "Please select a tile to set it to a win tile";
		}
		if (error.length()==0){
			try{	
				DesignModeController dmc = new DesignModeController ();
				Tile newTile = dmc.getCurrentGame().getTile(selectedTile);
			    dmc.setWinTile(newTile);
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		printBoard();
		refreshBoard();
	}

	
	private void removeTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if ( selectedTile < 0 ){
			error = "You must select a tile to remove.";
		}
		if ( error.length()==0 ){
			DesignModeController dmc = new DesignModeController();
			try{
				dmc.deleteTile(dmc.getTiles().get( tileToggleList.getSelectedIndex() ) );
			}
			catch (InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		printBoard();
		refreshBoard();
	}
	

	private void player1StartButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if (  selectedStartTile < 0 ){
			error = "You must select a tile to set it to a starting tile.";
		}
		if ( error.length()==0 ){
			DesignModeController dmc = new DesignModeController();
			try{
				dmc.setStartingPosition(dmc.getTiles().get(startTileToggleList.getSelectedIndex()), 1);
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		refreshBoard();
	}
	

	private void player2StartButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if ( selectedStartTile < 0 ){
			error = "You must select a tile to set it to a starting tile.";
		}
		if ( error.length()==0 ){
			DesignModeController dmc = new DesignModeController();
			try{
				dmc.setStartingPosition(dmc.getTiles().get(startTileToggleList.getSelectedIndex()),2 );
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		refreshBoard();
	}
	

	private void player3StartButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if ( selectedStartTile < 0 ){
			error = "You must select a tile to set it to a starting tile.";
		}
		
		if ( error.length()==0 ){
			DesignModeController dmc = new DesignModeController();
			try{
				dmc.setStartingPosition(dmc.getTiles().get(startTileToggleList.getSelectedIndex()),3 );
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		refreshBoard();
	}
	

	private void player4StartButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if ( selectedStartTile < 0 ){
			error = "You must select a tile to set it to a starting tile.";
		}
		if ( error.length()==0 ){
			DesignModeController dmc = new DesignModeController();
			try{
				dmc.setStartingPosition(dmc.getTiles().get(startTileToggleList.getSelectedIndex()),4 );
			}
			catch(InvalidInputException e){
				error = e.getMessage();
			}
		}
		refreshData();
		refreshBoard();
	}
	

	private void actionCardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		
		if(RDACTextField.getText().equals("") || CTACTextField.getText().equals("") || RCACTextField.getText().equals("") || TACTextField.getText().equals("") || LTACTextField.getText().equals("") ||
				AMACTextField.getText().equals("") || DATACTextField.getText().equals("") || MOPACTextField.getText().equals("") || MWTACTextField.getText().equals("") || RTACTextField.getText().equals("") ||
				SPACTextField.getText().equals("")){
			error= "You should fill in a number in each action card's textfield.";
		}
		
		else if ( !(RDACTextField.getText().matches("[0-9]+")) || !(CTACTextField.getText().matches("[0-9]+")) || !(RCACTextField.getText().matches("[0-9]+")) || !(TACTextField.getText().matches("[0-9]+")) || !(LTACTextField.getText().matches("[0-9]+")) ||
				!(AMACTextField.getText().matches("[0-9]+")) || !(DATACTextField.getText().matches("[0-9]+")) || !(MOPACTextField.getText().matches("[0-9]+")) || !(MWTACTextField.getText().matches("[0-9]+")) || !(RTACTextField.getText().matches("[0-9]+")) ||
				!(SPACTextField.getText().matches("[0-9]+"))){
			error = "Please enter a valid number of cards!";
		}

		
		if (error.length() == 0) {
			// call the controller
			DesignModeController dmc = new DesignModeController();
			try {
				dmc.initDeck( Integer.parseInt(RDACTextField.getText()),  Integer.parseInt(CTACTextField.getText()), Integer.parseInt(RCACTextField.getText()), Integer.parseInt(TACTextField.getText()), Integer.parseInt(LTACTextField.getText()), 
						Integer.parseInt(AMACTextField.getText()), Integer.parseInt(DATACTextField.getText()), Integer.parseInt(MOPACTextField.getText()), Integer.parseInt(MWTACTextField.getText()), Integer.parseInt(RTACTextField.getText()), Integer.parseInt(SPACTextField.getText()));
			} 
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		// update visuals
		refreshData();
	}
	

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		DesignModeController dmc = new DesignModeController();
		dmc.save();
		JOptionPane.showMessageDialog(frame,"Saved!");
		error = "";
	}


	private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";

        try {
        	   pmc.startGame(); 
        	   new PlayModePage(pmc);
        }
        catch (InvalidInputException e){
        	error = e.getMessage();
        }
        refreshData();
        if (error.trim().length()<=0){
        	frame.setVisible(false);
        }
	}
	
	
	public void printBoard(){
		
		boardCenter.removeAll();
		boardCenter.revalidate();
		boardCenter.repaint();
		
		// Clear all
		for (int y = 0; y < (rowMax+(rowMax-1)); y++){
			for (int x = 0; x < (colMax+(colMax-1)); x++){
				TilesandConnectionPieces[y][x] = new JButton();
			}
		}
		
		
		GridBagConstraints boardSpace = new GridBagConstraints();
		boardSpace.insets = new Insets(1, 1, 1, 1);  // Button spacings (top,bottom,left,right)
		
		// Print out the tile and connecting piece
		for (int y = 0; y < (rowMax+(rowMax-1)); y++){
			for (int x = 0; x < (colMax+(colMax-1)); x++){
				
				TilesandConnectionPieces[y][x] = new JButton();

				if (x%2 == 1){
					TilesandConnectionPieces[y][x].setPreferredSize(new Dimension(14, 7));
					
					TilesandConnectionPieces[y][x].setText(Integer.toString(x)+" "+Integer.toString(y));
					TilesandConnectionPieces[y][x].setForeground(Color.WHITE.brighter());
				}
				
				else if (y%2 == 1){
					TilesandConnectionPieces[y][x].setPreferredSize(new Dimension(7, 14));
					
					TilesandConnectionPieces[y][x].setText(Integer.toString(x)+" "+Integer.toString(y));
					TilesandConnectionPieces[y][x].setForeground(Color.WHITE.brighter());
					
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
				boardCenter.add(TilesandConnectionPieces[y][x],boardSpace);
							
			}
		}		
		
		
		// Remove the connection piece of non-existing tile
		for (int y = 0; y < (rowMax+(rowMax-1)); y++){
			for (int x = 0; x < (colMax+(colMax-1)); x++){
				
				if (x%2==0 && y%2 ==0){
					if ((int)(TilesandConnectionPieces[y][x].getPreferredSize().getWidth())==0 && (int)(TilesandConnectionPieces[y][x].getPreferredSize().getHeight())==0){
						// has 2 connecting pieces (four vertices)   **************
						if (x==0 && y==0){ // upper left
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece							
							}
						else if (x == (colMax+(colMax-1))-1 && y==0){ // upper right
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece		
						}
						else if (x==0 && y == (rowMax+(rowMax-1))-1){ // bottom left
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece
						}
						else if (x == (colMax+(colMax-1)-1) && y==(rowMax+(rowMax-1)-1)){ // bottom right
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece							
						}
						
										
						// has 3 connecting pieces  *************
						else if (y==0 && x%2==0){ // top row
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // left horizontal 
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // right horizontal 
						}
						
						else if (y==(rowMax+(rowMax-1))-1 && x%2==0){ // bottom row
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical connecting piece
							TilesandConnectionPieces[y][x-1].setPreferredSize(new Dimension(0,0)); // horizontal connecting piece
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // right horizontal 				
						}
						else if (x==0 && y%2==0){ // leftmost column
							TilesandConnectionPieces[y-1][x].setPreferredSize(new Dimension(0,0)); // vertical up
							TilesandConnectionPieces[y+1][x].setPreferredSize(new Dimension(0,0)); // vertical down
							TilesandConnectionPieces[y][x+1].setPreferredSize(new Dimension(0,0)); // horizontal right
						}
						
						else if (x==(colMax+(colMax-1))-1 && y%2==0){ // rightmost column
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
		
		for (int y = 0; y < (rowMax+(rowMax-1)); y++){
			for (int x = 0; x < (colMax+(colMax-1)); x++){
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
	}
	
	public void refreshBoard(){
		Border defaultBorder = new JButton().getBorder();
		Color defaultColor = new JButton().getBackground();
		
		// Clear all the border and color of each tile
		for (int y = 0; y < (rowMax+(rowMax-1)); y++){
			for (int x = 0; x < (colMax+(colMax-1)); x++){
				TilesandConnectionPieces[y][x].setBorder(defaultBorder);
				TilesandConnectionPieces[y][x].setBackground(defaultColor);
			}
		}
		
		printPlayingPiece();
		
		
		// Refresh Action tiles location
		
		for (Tile aTile: pmc.getTiles()){
			if (aTile instanceof ActionTile){
				int yOnBoard = aTile.getY()*2;
				int xOnBoard = aTile.getX()*2;
				TilesandConnectionPieces[yOnBoard][xOnBoard].setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
			}
			
			if ((aTile instanceof WinTile)){
				int yOnBoard = aTile.getY()*2;
				int xOnBoard = aTile.getX()*2;
				TilesandConnectionPieces[yOnBoard][xOnBoard].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
			}
		}
		
		
	}
	
	
	public void printPlayingPiece(){
		/////////// Print playing pieces
		if (pmc.getPlayers()!=null || pmc.getPlayers().size()!=0){
			for(int i=0; i<pmc.getPlayers().size();i++){
				if (pmc.getPlayers().get(i).getStartingTile()!=null){
					int xPosition = pmc.getPlayers().get(i).getStartingTile().getX();
					int yPosition = pmc.getPlayers().get(i).getStartingTile().getY();

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
			}
			refreshPlayerPanel();
		}

	}
	
	private void refreshPlayerPanel(){
		
		GridBagConstraints playerSpace = new GridBagConstraints();
		playerSpace.insets = new Insets(0,0,0,20);
		
		boardBottom.removeAll();
		boardBottom.revalidate();
		boardBottom.repaint();
		if (pmc.getPlayers()!=null || pmc.getPlayers().size()!=0){
			for (int i = 0; i<pmc.getPlayers().size();i++){
		  	
				if (i==0){
					p1Label = new JLabel();
					p1Label.setText("[P1]");
					p1Label.setForeground(Color.RED);
					p1Label.setFont(new Font("Georgia", Font.BOLD, 13));
				
					playerSpace.gridx = 1;
					playerSpace.gridy = 0;
					boardBottom.add(p1Label,playerSpace);
				}
				
				if (i==1){
					p2Label = new JLabel();
					p2Label.setText("[P2]");
					p2Label.setForeground(Color.BLUE);
					p2Label.setFont(new Font("Georgia", Font.BOLD, 13));
					
					playerSpace.gridx = 2;
					playerSpace.gridy = 0;
					boardBottom.add(p2Label,playerSpace);
				}
	
				
				if (i==2){
					p3Label = new JLabel();
					p3Label.setText("[P3]");
					p3Label.setForeground(Color.GREEN);
					p3Label.setFont(new Font("Georgia", Font.BOLD, 13));
					
					playerSpace.gridx = 3;
					playerSpace.gridy = 0;
					boardBottom.add(p3Label,playerSpace);
				}
				
				if (i==3){
					p4Label = new JLabel();
					p4Label.setText("[P4]");
					p4Label.setForeground(Color.YELLOW);
					p4Label.setFont(new Font("Georgia", Font.BOLD, 13));
					
					playerSpace.gridx = 4;
					playerSpace.gridy = 0;
					boardBottom.add(p4Label,playerSpace);
				}
			}
		}
	}
	
	private void tileClickedActionPerformed(java.awt.event.ActionEvent evt){
		
		int buttonClickedActualX = Integer.parseInt(buttonClicked.getText().split("\\s")[0]);
		int buttonClickedActualY = Integer.parseInt(buttonClicked.getText().split("\\s")[1]);
		
		JOptionPane.showMessageDialog(frame,"You clicked on Tile (" + buttonClickedActualX + "," + buttonClickedActualY + ")");
		
	}

}