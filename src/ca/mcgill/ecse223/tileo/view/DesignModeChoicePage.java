package ca.mcgill.ecse223.tileo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.DesignModeController;
import ca.mcgill.ecse223.tileo.model.Game;

public class DesignModeChoicePage {
	
	private JFrame frame;
	private JPanel topPanel,optionsPanel;
	
	private HashMap <Integer,Game> existingGames;
	private JComboBox<String> existingGamesToggleList;
	private JButton editButton;
	private JButton cancelButton;
	private String error = "";
	private JLabel errorMessage;

	private Integer selectedExistingGames = -1;
	
	
	public DesignModeChoicePage() {
		initComponents();
		refreshData();
	}
	
	
	public void initComponents(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);// Execution window cannot be resized
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.BLACK);
		
		topPanel = new JPanel (new GridLayout(0,1));
		topPanel.setBackground(Color.BLACK);
		topPanel.add(new JLabel (new ImageIcon("designModeTitle.jpg")));		
		
		optionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		optionsPanel.setBackground(Color.black);
		
		layout.insets = new Insets(10, 0, 0, 0);  // Button spacings (top,bottom,left,right)
		
		existingGamesToggleList = new JComboBox<String>(new String[0]);
		existingGamesToggleList.setBackground(Color.WHITE);
		existingGamesToggleList.setForeground(Color.BLACK);
		existingGamesToggleList.setBorder(BorderFactory.createLineBorder(Color.CYAN.brighter()));
		
		existingGamesToggleList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedExistingGames = cb.getSelectedIndex();
			}
		});	
		layout.gridx = 0;
		layout.gridy = 0;
		optionsPanel.add(existingGamesToggleList,layout);
		
		
		
		editButton = new JButton("     Edit     ");
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
			}
		});
		layout.gridx = 0;
		layout.gridy = 1;
		optionsPanel.add(editButton,layout);
		
		
		
		cancelButton = new JButton("   Cancel  ");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new StartMenu();
				frame.setVisible(false);
			}
		});
		layout.insets = new Insets(10, 0, 20, 0);  // Button spacings (top,left,bottom,right)
		
		layout.gridx = 0;
		layout.gridy = 2;
		optionsPanel.add(cancelButton,layout);
		
		layout.gridx = 0;
		layout.gridy = 3;
		optionsPanel.add(errorMessage,layout);
		
				
		
		// Add the panels onto the frame
		frame.add(topPanel, BorderLayout.NORTH); // Adds Panels into Frame
		frame.add(optionsPanel,BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.pack();
		
	}
	
	private void refreshData(){
		DesignModeController dmc = new DesignModeController();
		
		if (error.length()>0){
			JOptionPane.showMessageDialog(frame,error);
		}
		
		Integer index = 0;
		existingGames = new HashMap<Integer,Game>();
		existingGamesToggleList.removeAllItems();
		
		if ( dmc.getGames().size()!=0 ){
			for (Game aGame : dmc.getGames()){
				existingGames.put(index, aGame);
				existingGamesToggleList.addItem("Game"+dmc.getGames().indexOf(aGame)+" - "+aGame.getModeFullName() + " MODE");
			};
		}
		selectedExistingGames = -1;
		existingGamesToggleList.setSelectedIndex(selectedExistingGames);
	}
	
	
	
	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		
		if (selectedExistingGames == -1){
			error = "You have not selected a game to edit";
		}
		
		if (error.length()==0){
			DesignModeController dmc = new DesignModeController();
			dmc.setCurrentGame(dmc.getGames().get(existingGamesToggleList.getSelectedIndex()));
			dmc.setGameMode();
			
			System.out.println("Game Mode: " + (dmc.getGames().get(existingGamesToggleList.getSelectedIndex())).getMode().name());
			
			new DesignModePage();
			frame.setVisible(false);
		}
		
		refreshData();
	}
	
	
		

	
	
	
	
	
}
