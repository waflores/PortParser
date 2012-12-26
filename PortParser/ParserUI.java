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

import java.io.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;


/*******************************************************************************
* Purpose: Class definition for FileViewer editor
* 
* Author: Will Flores waflores@ncsu.edu
*******************************************************************************/
@SuppressWarnings("serial")
public class ParserUI implements ActionListener {
	/* Main File Opener GUI */
	private int defaultRows = 21; /* Rows for the TextArea */
	private int defaultColumns = 36; /* Columns for the TextArea */
	private JFrame mainWindow = new JFrame("FileViewer"); /* Main window frame where TextBox resides */
	private JPanel mainPanel = new JPanel();
	private JPanel btnPanel = new JPanel();
	private JTextArea dummyTextArea = new JTextArea(); /* Hidden from view for printing purposes */
	private JTextArea outTextArea = new JTextArea(defaultRows, defaultColumns) {
		public void setFont(Font f) {
			super.setFont(f);
			dummyTextArea.setFont(f);
		}
	};
	private String newLine = System.getProperty("line.separator"); /* Reactivate */
	private JScrollPane outScrollPane = new JScrollPane(outTextArea);
	private JScrollBar vsb = outScrollPane.getVerticalScrollBar(); /* Reactivate */
	
	/* Menu Bar */
	private JMenu fileOptions; // file
	private JMenuItem openFileItem;
	private JMenuItem newFileItem;
	private JMenuItem saveExisitingFileItem;
	private JMenuItem saveAsItem;
	private JMenuItem printPageItem;
	private JMenuItem exitProgramItem;
	
	private JMenu parseOptions; // parsing
	private JMenuItem parseItem; 
	
	private JMenu helpOptions; // view
	private JMenuItem aboutFileItem;
	private String currentFileName = null;
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
		dummyTextArea.setLineWrap(true); // this is for printing purposes
		dummyTextArea.setWrapStyleWord(true);
		
		/* Create the menuBar */
		createFileMenu();
		createParseMenu();
		createHelpMenu();
		
		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		menuBar.add(fileOptions);
		menuBar.add(parseOptions);
		menuBar.add(helpOptions);
		
		/* Use the native L & F - throughout */
		try {
            String cn = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(cn); 
        } catch (Exception cnf) {/* Don't do anything */}
		
		clearScreen();
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
		/* Register buttons with Java Interrupt Handler */
		newFileItem.addActionListener(this);
		openFileItem.addActionListener(this);
		saveExisitingFileItem.addActionListener(this);
		saveAsItem.addActionListener(this);
		printPageItem.addActionListener(this);
		exitProgramItem.addActionListener(this);
		
		fileOptions.add(newFileItem);
		fileOptions.add(openFileItem);
		fileOptions.add(saveExisitingFileItem);
		fileOptions.add(saveAsItem);
		fileOptions.add(printPageItem);
		fileOptions.addSeparator();
		fileOptions.add(exitProgramItem);
	}
	
	/*******************************************************************************
	* Purpose: Creates the Parsing Menu
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	private void createParseMenu() {
		parseOptions = new JMenu("Parse");
		parseOptions.setMnemonic(KeyEvent.VK_R);
		parseItem = new JMenuItem("Parse this file...");
		parseItem.addActionListener(this);
		parseOptions.add(parseItem);
	}
	
	/*******************************************************************************
	* Purpose: Creates the Help Menu
	* Passed: No arguments passed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	private void createHelpMenu() {
		helpOptions = new JMenu("Help");
		helpOptions.setMnemonic(KeyEvent.VK_H);
		aboutFileItem = new JMenuItem("About FileViewer");
		aboutFileItem.addActionListener(this);
		helpOptions.add(aboutFileItem);
	}

	@Override
	/*******************************************************************************
	* Purpose: Processes button presses on the App
	* Passed: ActionEvent ae - Variable from Java Interrupt Handler.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public void actionPerformed(ActionEvent ae) {
		/* Process through File Menu events */
		if (ae.getSource() == newFileItem) {
			int retval = JOptionPane.showConfirmDialog(mainWindow, "Do you want to save file before creating new file?", "Create a New File", JOptionPane.YES_NO_CANCEL_OPTION);
			try { 
				switch (retval) {
					case JOptionPane.YES_OPTION:
						saveFile(); // Fall thru to close out of program
					case JOptionPane.NO_OPTION:
						clearScreen(); // New Document
						break;
					default: break; // closed out or cancelled
				}
			} catch (IOException ioe) {
					// Handle saveFile Exception
					JOptionPane.showMessageDialog(mainWindow, "Couldn't save to file, try again.");
				}
		}
		if (ae.getSource() == openFileItem) {
			/* Open a file */
			String fileName = null;
			JFileChooser jfc = new JFileChooser();
			int retval = jfc.showOpenDialog(jfc);
			if (retval == JFileChooser.APPROVE_OPTION) fileName = jfc.getSelectedFile().getAbsolutePath();
			try {
				openFile(fileName);
			}
			catch (NullPointerException npe) { /* No filename */
				 JOptionPane.showMessageDialog(mainWindow,  "You must choose a file name.");
			} 
			catch (IOException e) {
				JOptionPane.showMessageDialog(mainWindow, "File error, please try again...");
			}
		}
		if (ae.getSource() == saveExisitingFileItem) {
			try {
				saveFile(this.currentFileName);
			}
			catch (NullPointerException npe) {
				try {
					saveFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(mainWindow, "Couldn't save to file, try again.");
				}
			}
			catch (IOException ioe) {
				JOptionPane.showMessageDialog(mainWindow, "Couldn't save current file, try again.");
			}
		}
		if (ae.getSource() == saveAsItem) {
			try {
				saveFile();
			}
			catch (IOException ioe) {
				JOptionPane.showMessageDialog(mainWindow, "Couldn't save to file, try again.");
			}
		}
		if (ae.getSource() == printPageItem) {
			this.print();
		}
		if (ae.getSource() == exitProgramItem) {
			int retval = JOptionPane.showConfirmDialog(mainWindow, "Do you want to save file before exiting?", "Closing Program", JOptionPane.YES_NO_CANCEL_OPTION);
			try {
				switch (retval){
				case JOptionPane.YES_OPTION:
					saveFile(); // Fall thru to close out of program
				case JOptionPane.NO_OPTION:
					System.exit(0); // terminate app
					break;
				default: break; // closed out or cancelled
				}
			}
				catch (IOException ioe) {
					// Handle saveFile Exception
					JOptionPane.showMessageDialog(mainWindow, "Couldn't save to file, try again.");
				}
		}
		
		/* Process through Parse Menu events */
		if (ae.getSource() == parseItem) {}
		
		/* Process through Help Menu events */
		if (ae.getSource() == aboutFileItem) {}
	}
	
	/***** Control the Display *****/
	/*******************************************************************************
	* Purpose: Display text on the editable text box
	* Passed: String msg - Text to be displayed.
	* Locals: No locals variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	private void printToConsole(String msg) {
		outTextArea.append(msg + newLine);
		vsb.setValue(vsb.getMaximum());
		outTextArea.setCaretPosition(outTextArea.getDocument().getLength());
	}
	
	private void clearScreen() {
		outTextArea.setText("");
	}
	
	private void openFile(String fileName) throws IOException {
		BufferedReader diskFile = new BufferedReader(new FileReader(fileName));
		mainWindow.setTitle("The contents of " + fileName + " is:");
		
		clearScreen();
		while (true) {
			try {
				printToConsole(diskFile.readLine().trim());
			}
			catch (NullPointerException npe) { /* reached EOF */
				diskFile.close();
				break;
			}
			catch(IOException ioe) { /* Something broke for real */
				JOptionPane.showMessageDialog(mainWindow, "Couldn't open the file, please try again...");
				diskFile.close();
				break;
			}
		}
	}
	
	private void saveFile () throws IOException {
		JFileChooser saveFile = new JFileChooser();
		int retval = saveFile.showSaveDialog(saveFile);
		
		String fileToSave = null;
		String fileData = null;
		
		if (retval == JFileChooser.APPROVE_OPTION) {
			fileToSave = saveFile.getSelectedFile().getAbsolutePath();
			this.currentFileName = fileToSave;
			fileData = outTextArea.getText();
			BufferedWriter diskFile = new BufferedWriter(new FileWriter(fileToSave));
			diskFile.write(fileData);
			diskFile.close();
			JOptionPane.showMessageDialog(mainWindow, "Done Saving to " + fileToSave);
		}
		else {
			JOptionPane.showMessageDialog(null, "Data not saved to file.");
		}
	}
	
	private void saveFile(String currentFileName) throws IOException {
		String fileData = outTextArea.getText();
		BufferedWriter diskFile = new BufferedWriter(new FileWriter(currentFileName));
		diskFile.write(fileData);
		diskFile.close();
	}

    // Printing text
    public void print() {
        if (outTextArea.getSelectedText() == null) {
            dummyTextArea.setText(outTextArea.getText());
        }
        else /* Print selected text*/ {
            dummyTextArea.setText(outTextArea.getSelectedText());
        }
        try {
            // This will show the print dialog.
            dummyTextArea.print();
        }
        catch (PrinterException e) {
            e.printStackTrace();  
        }
        // Set focus so that selected text is highlighted.
        outTextArea.requestFocusInWindow();
    } 
}
