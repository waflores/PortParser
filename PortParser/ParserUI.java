import java.awt.event.*;
import java.io.*;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ParserUI implements ActionListener {
	/* Main file IO */
	private BufferedReader diskFile;
	
	/* Main File Opener GUI */
	private JFrame mainWindow = new JFrame("FileViewer");
	private JPanel mainPanel = new JPanel();
	private JPanel btnPanel = new JPanel();
	private JTextArea outTextArea = new JTextArea(21,36);


	private String newLine = System.getProperty("line.separator");
	private JScrollPane outScrollPane = new JScrollPane(outTextArea);
	private JScrollBar vsb = outScrollPane.getVerticalScrollBar();
	
	/* Menu Bar */
	private JMenu fileOptions; // file
	private JMenuItem openNewFileItem;
	private JMenuItem saveExisitingFileItem;
	private JMenuItem exitProgramItem;
	private JMenu viewOptions; // view
	private JMenuItem viewFileItem; 
	
	@SuppressWarnings("unused")
	public static void main (String[] args) throws Exception {
		String filename = null;
		String identifiers = null;
		boolean cmdLineArgs = false;
		ParserUI fileViewer = null;
		
		/* Process cmdlineArgs */
		switch (args.length) {
			case 2: identifiers = args[1];
			case 1: filename = args[0];
					cmdLineArgs = true; // we have cmd line params
			case 0: // fall thru for default cases
			default:
				fileViewer = new ParserUI(cmdLineArgs);
			break;
		}
	}
	
	
	public ParserUI(boolean cmdLineArgs) {
		if (!cmdLineArgs) {
			buildMainGUI();
		}
	}
	
	
	
	public void printFile(String fileName) throws IOException {
		  BufferedReader diskFile = new BufferedReader(
		                            new FileReader(fileName));
		  mainWindow.setTitle("The contents of " + fileName + " is:");
		  while(true) { 
		    try {
		        printToConsole(diskFile.readLine().trim());
		        }
		    catch(IOException ioe) {
		        //System.out.println("Listing complete.");
		        diskFile.close();
		        break;
		    }
		  }
		}


	private void buildMainGUI() {
		/* Output gui */
		mainWindow.setSize(450, 450);
		mainWindow.setLocation(250, 250);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		mainWindow.getContentPane().add(mainPanel, "Center");
		mainWindow.getContentPane().add(btnPanel, "South");
		mainPanel.add(outScrollPane);

		outTextArea.setEditable(true); // Just make this a display text area
		
//		btnPanel.add(closeButton);
//		closeButton.addActionListener(this); // make sure button closes gui
//		btnPanel.add(saveButton);
//		saveButton.addActionListener(this);
		
		/* Create the menuBar */
		createFileMenu();
		createViewMenu();
		
		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		menuBar.add(fileOptions);
		menuBar.add(viewOptions);
		
		printToConsole("Open a file to parse by clicking File > Open File...");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}
	
	private void createFileMenu() {
		fileOptions = new JMenu("File");
		openNewFileItem = new JMenuItem("Open File...");
		saveExisitingFileItem = new JMenuItem("Save");
		exitProgramItem = new JMenuItem("Close Program");
		
		openNewFileItem.addActionListener(this);
		saveExisitingFileItem.addActionListener(this);
		exitProgramItem.addActionListener(this);
		
		fileOptions.add(openNewFileItem);
		fileOptions.add(saveExisitingFileItem);
		fileOptions.addSeparator();
		fileOptions.add(exitProgramItem);
	}
	
	private void createViewMenu() {
		viewOptions = new JMenu("View");
		viewFileItem = new JMenuItem("View File");
		
		viewFileItem.addActionListener(this);
		viewOptions.add(viewFileItem);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == openNewFileItem) {
			String filename = null;
			String identifiers = null;
			
			JFileChooser jfc = new JFileChooser();
			int retval = jfc.showOpenDialog(jfc);
			
			if (retval == JFileChooser.APPROVE_OPTION) {
				filename = jfc.getSelectedFile().getAbsolutePath();
			}
			else {
				JOptionPane.showMessageDialog(null, "You must choose a file name.");
			}
			
			while (identifiers == null || identifiers.length() == 0 ) {
				identifiers = JOptionPane.showInputDialog(null, 
						"Input tokens separated by semicolons, type \"Exit\" without a semicolon "
						+ "to exit program, or \"Cancel\" to cancel the process.", 
						"rxByteCount;txByteCount;rxHCOctets;txHCOctets");
			}
			/* figure out what tokens we have */
			if (identifiers.compareToIgnoreCase("exit") == 0) {
				JOptionPane.showMessageDialog(null, "Exiting the program.");
				System.exit(0);
			}
			else if (identifiers.compareToIgnoreCase("cancel") == 0); // just don't do anything
			else {	/* parse the file */
				outTextArea.setText("");
			}
		}
		if (ae.getSource() == exitProgramItem) {
			int retVal = JOptionPane.showConfirmDialog(mainWindow, "Do you want to save file before exiting?", "Closing program", JOptionPane.YES_NO_CANCEL_OPTION);
			try {
				switch (retVal) {
					case JOptionPane.YES_OPTION:
						 printToFile(); // Fall thru to close out
					case JOptionPane.NO_OPTION:
						System.exit(0); // terminate app
						break;
					default: // if they closed out or cancelled
						break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(mainWindow, "Couldn't save to file, try again...");
			} // print to file 
			
		}
		if (ae.getSource() == saveExisitingFileItem) {
			try {
				printToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(mainWindow, "Couldn't save to file, try again...");
			} // print to file 
		}
		if (ae.getSource() == viewFileItem) {
			try {
				String filename = null;
				JFileChooser jfc = new JFileChooser();
				int retval = jfc.showOpenDialog(jfc);
				
				if (retval == JFileChooser.APPROVE_OPTION) {
					filename = jfc.getSelectedFile().getAbsolutePath();
				}
				else {
					JOptionPane.showMessageDialog(null, "You must choose a file name.");
				}
				outTextArea.setText(""); // clear screen
				printFile(filename);
			} catch (NullPointerException npe) {} // reached eof 
			catch (IOException ioe) {
				JOptionPane.showMessageDialog(mainWindow, "Couldn't open the file, try again...");
			}
		}
	}
	
	private void printToConsole (String msg) {
		outTextArea.append(msg + newLine);
		vsb.setValue(vsb.getMaximum());
		outTextArea.setCaretPosition(outTextArea.getDocument().getLength());
	}
	
	private void printToFile() throws IOException {
		JFileChooser saveFile = new JFileChooser();
		int retval = saveFile.showSaveDialog(saveFile);
		
		String fileToSave = null;
		String fileData = null;
		if (retval == JFileChooser.APPROVE_OPTION) {
			fileToSave = saveFile.getSelectedFile().getAbsolutePath();
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
}
