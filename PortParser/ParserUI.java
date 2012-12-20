/******************************************************************************* 
* File:  ParserUI.java
* Author: Will Flores waflores@ncsu.edu
* Usage: ParserUI [cmd args]...
* Description: This file contains the FileViewer editor GUI.
* Environment: Windows 7, x64 build
*              Built in Eclipse Juno
* Notes:       NONE
* Revisions:   0.0, Initial Release
*
* Created on December 20, 2012
*******************************************************************************/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/*******************************************************************************
* Purpose: Class definition for FileViewer editor
* 
* Author: Will Flores waflores@ncsu.edu
*******************************************************************************/
public class ParserUI implements ActionListener{
	/* Main File Opener GUI */
	private int defaultRows = 21; /* Rows for the TextArea */
	private int defaultColumns = 36; /* Columns for the TextArea */
	private JFrame mainWindow = new JFrame("FileViewer"); /* Main window frame where TextBox resides */
	private JPanel mainPanel = new JPanel();
	private JPanel btnPanel = new JPanel();
	private JTextArea outTextArea = new JTextArea(defaultRows, defaultColumns);

	//private String newLine = System.getProperty("line.separator"); /* Reactivate */
	private JScrollPane outScrollPane = new JScrollPane(outTextArea);
	//private JScrollBar vsb = outScrollPane.getVerticalScrollBar(); /* Reactivate */
	
	/* Menu Bar */
	private JMenu fileOptions; // file
	private JMenuItem openFileItem;
	private JMenuItem newFileItem;
	private JMenuItem saveExisitingFileItem;
	private JMenuItem saveAsItem;
//	private JMenuItem pageSetupItem;
	private JMenuItem printPageItem;
	private JMenuItem exitProgramItem;
	
	private JMenu viewOptions; // view
	private JMenuItem viewFileItem; 
	
	
	/*******************************************************************************
	* Purpose: main function to be activated with command line paramters
	* Passed: The file name of the file to be edited.
	* Locals: 
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	//@SuppressWarnings("unused")
	public static void main (String[] args) throws Exception {
/* Process cmdlineArgs - don't have them */
		switch (args.length) {
			/*case 2: identifiers = args[1];
			case 1: filename = args[0];
					cmdLineArgs = true; // we have cmd line params
			case 0: // fall thru for default cases */
			default:
				new ParserUI();
			break;
		}
	}
	
	/*******************************************************************************
	* Purpose: Default constructor for FileViewer 
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: FileViewer GUI object
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public ParserUI() {
		buildMainGUI();
	}
	
	/*******************************************************************************
	* Purpose: Builds the main GUI
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	private void buildMainGUI() {
		/* Output gui */
		mainWindow.setSize(450, 450);
		mainWindow.setLocation(250, 250);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		mainWindow.getContentPane().add(mainPanel, "Center");
		mainWindow.getContentPane().add(btnPanel, "South");
		mainPanel.add(outScrollPane);

		outTextArea.setEditable(true); // Just make this a display text area
		
		/* Create the menuBar */
		createFileMenu();
		createViewMenu();
		
		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		menuBar.add(fileOptions);
		menuBar.add(viewOptions);
		
		//printToConsole("Open a file to parse by clicking File > Open File..."); /* Reactivate */
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}
	
	/*******************************************************************************
	* Purpose: Creates the file menu
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	private void createFileMenu() {
		fileOptions = new JMenu("File");
		fileOptions.setMnemonic(KeyEvent.VK_F); /* Alt+F will activate this menu */
		newFileItem = new JMenuItem("New");
		
		openFileItem = new JMenuItem("Open File...");
		saveExisitingFileItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As...");
		printPageItem = new JMenuItem("Print...");
		exitProgramItem = new JMenuItem("Exit Program");
		
		//openNewFileItem.addActionListener(this); /* Reactivate */
		//saveExisitingFileItem.addActionListener(this); /* Reactivate */
		//exitProgramItem.addActionListener(this); /* Reactivate */
		
		fileOptions.add(newFileItem);
		fileOptions.add(openFileItem);
		fileOptions.add(saveExisitingFileItem);
		fileOptions.add(saveAsItem);
		fileOptions.add(printPageItem);
		fileOptions.addSeparator();
		fileOptions.add(exitProgramItem);
	}
	
	/*******************************************************************************
	* Purpose: Creates the View Menu
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	private void createViewMenu() {
		viewOptions = new JMenu("View");
		viewFileItem = new JMenuItem("View File");
		//viewFileItem.addActionListener(this); /* Reactivate */
		viewOptions.add(viewFileItem);
	}

	@Override
	/*******************************************************************************
	* Purpose: Processes button p
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
	}
	
}
