package ca.mcgill.ecse223.tileo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.mcgill.ecse223.tileo.controller.DesignModeController;

public class StartMenu{
	private JFrame frame;
	private JPanel topPanel,optionsPanel;
	
	private JButton designModeButton;
	private JButton playModeButton;
	private JButton exitButton;
	
	public StartMenu() {
		initComponents();
	}
	
	
	public void initComponents(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on Exit
		frame.setResizable(false);// Execution window cannot be resized
		
		
		topPanel = new JPanel (new GridLayout(0,1));
		topPanel.setBackground(Color.BLACK);
		topPanel.add(new JLabel (new ImageIcon("StartMenutitle.jpg")));
		
		optionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		optionsPanel.setBackground(Color.black);
		
		layout.insets = new Insets(10, 0, 0, 10);  // Button spacings (top,bottom,left,right)
		
		designModeButton = new JButton("  Design Mode  ");
		designModeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				designModeButtonActionPerformed(evt);
			}
		});
		layout.gridx = 0;
		layout.gridy = 0;
		optionsPanel.add(designModeButton,layout);
		
		playModeButton = new JButton("   Play Mode   ");
		playModeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playModeButtonActionPerformed(evt);
			}
		});
		layout.gridx = 0;
		layout.gridy = 1;
		optionsPanel.add(playModeButton,layout);
		
		
		exitButton = new JButton("       Exit       ");
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitButtonActionPerformed(evt);
			}
		});
		
		layout.insets = new Insets(10, 0, 30, 10);  // Button spacings (top,bottom,left,right)
		layout.gridx = 0;
		layout.gridy = 2;
		optionsPanel.add(exitButton,layout);
		
		// Add the panels onto the frame
		frame.add(topPanel, BorderLayout.NORTH); // Adds Panels into Frame
		frame.add(optionsPanel,BorderLayout.CENTER);

		frame.setVisible(true);
		frame.pack();
		
	}
	
	
	
	
	private void designModeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		DesignModeController dmc = new DesignModeController();
		int selectedOption = JOptionPane.showConfirmDialog(null, 
                "Do you want to edit existing game design?", 
                "Choose", 
                JOptionPane.YES_NO_CANCEL_OPTION); 
		if (selectedOption == JOptionPane.YES_OPTION) {
			new DesignModeChoicePage();
			frame.setVisible(false);
		}
		else if (selectedOption == JOptionPane.NO_OPTION){
			dmc.createGame();
			new DesignModePage();
			frame.setVisible(false);
		}
	}
	
	private void playModeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		new PlayModeChoicePage();
		frame.setVisible(false);
	}
	
	private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}
	

	
}
