import java.io.*;
import java.util.*;

/*******************************************************************************
* IdTagObjs: Serves as the parsing algorithm and data structure for the type of
* 	input file used by Switches.
* 
* Author: Will Flores waflores@ncsu.edu
*******************************************************************************/
public class IdTagObjs {
	private ParserUI pui;
	private String commaDelimeter = ",";
	private String identifierTag = null;
	private String idVal = "0";

	/*******************************************************************************
	* Purpose: Constructor that is invoked by the fileviewer.
	* Passed: Parser pui - saves Fileviewer pointer to link object.
	* Locals: No local variables used.
	* Returned: IdTagObjs object.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public IdTagObjs(ParserUI pui) {
		this.pui = pui;
	}
	
	/*******************************************************************************
	* Purpose: Constructor that is invoked by this object for parsing.
	* Passed: String indentifierTag - token processed.
	* Locals: No local variables used.
	* Returned: IdTagObjs object.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public IdTagObjs(String identifierTag) {
		if (identifierTag == null || identifierTag.length() == 0) {
			throw new IllegalArgumentException("Need an identifier.");
		}
		else setIdentifierTag(identifierTag);
	}

	/*******************************************************************************
	* Purpose: Clears token count.
	* Passed: No values passed.
	* Locals: No local variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public void resetIdValues() {
		setIdVal(null);
	}
	
	/*******************************************************************************
	* Purpose: Retrieves token.
	* Passed: No values passed.
	* Locals: No local variables used.
	* Returned: The token from this object.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public String getIdentifierTag() {
		return identifierTag;
	}

	/*******************************************************************************
	* Purpose: Sets token.
	* Passed: String indentifierTag - token processed.
	* Locals: No local variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public void setIdentifierTag(String identifierTag) {
		this.identifierTag = identifierTag;
	}

	/*******************************************************************************
	* Purpose: Retrieves token count.
	* Passed: No values passed.
	* Locals: No local variables used.
	* Returned: The token count from this object.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public String getIdVal() {
		return idVal;
	}

	/*******************************************************************************
	* Purpose: Sets token count.
	* Passed: String idVal - token count.
	* Locals: No local variables used.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public void setIdVal(String idVal) {
		this.idVal = idVal;
	}
	
	/*******************************************************************************
	* Purpose: Parsing algorithm for switch input.
	* Passed: String identifiers - identifiers from user to find.
	* 		  String regex - delimiters for identifiers from user.
	* 		  String fileName - file to parse.
	* Locals: Help save the parse file and displays results.
	* Returned: No values returned.
	* Author: Will Flores waflores@ncsu.edu
	*******************************************************************************/
	public void parser (String identifiers, String regex, String fileName) throws IllegalArgumentException {
		// Open the file in question
		if (fileName == null || identifiers == null) throw new IllegalArgumentException("Can't have null args.");
		
		ArrayList <IdTagObjs> idTags = new ArrayList<IdTagObjs>(); 
		idTags.add(new IdTagObjs("Port")); // add the port identifiers
		
		BufferedReader diskFile; /* File to parse */
		try {
			diskFile = new BufferedReader(new FileReader(fileName));
			String[] inputid = identifiers.split(regex);
			ArrayList<String> intermediateids = new ArrayList<String>();
			// add the identifiers in the identifier data structure
			for (String id : inputid) {
				idTags.add(new IdTagObjs(id.trim()));
				intermediateids.add(id.trim());
			}
			Object[] idobjArray = intermediateids.toArray();
			// ids only contain what was input from the command line
			String[] ids = Arrays.copyOf(idobjArray, idobjArray.length, String[].class);
	
			boolean gotFirstPortTag = false;
			String line = null;
	
			// Print header
			String header = null;
			for (IdTagObjs a : idTags) {
				if (header == null) header = a.getIdentifierTag();
				else header += commaDelimeter + a.getIdentifierTag();
			}
			pui.printToConsole(header);
	
			// Parsing loop
			while (!gotFirstPortTag) {
				line = diskFile.readLine().trim();
				// Get the opening port tag
				if (!gotFirstPortTag && line.contains("Port") || line.contains("port")) {
					line = line.substring(line.indexOf(" "), line.length());
					idTags.get(0).setIdVal(line);
					gotFirstPortTag = true;
				}
			}
			while (true) {
				line = diskFile.readLine().trim();
	
				// Check to see if we have the requisite identifiers
				// while the line doesn't contain a reference to a port
				while(!(line.contains("Port ") || line.contains("port "))) {
					// Compare the identifiers to the line
					String searchTerm = null;
					for (int index = 0; index < ids.length; index++) {
						searchTerm = ids[index];
						if (line.contains(searchTerm)) {
							line = line.substring(line.indexOf("=")+1, line.length());
							// find the searchTerm
							idTags.get(index+1).setIdVal(line);
							//printToConsole(searchTerm + " "+ line);
							break; // move on to the next line
						}
					}
	
					line = diskFile.readLine().trim();
				}
				// Print out the port 
				if (line.contains("Port ") || line.contains("port ")) {
					String output = null;
					for (IdTagObjs a : idTags) {
						if (output == null) output = a.getIdVal();
						else output += commaDelimeter + a.getIdVal();
					}
					pui.printToConsole(output);
					// Set up for the next port
					line = line.substring(line.indexOf(" "), line.length());
					idTags.get(0).setIdVal(line);
				}
			}
		} 
		catch (NullPointerException npe) { // Flush all the stuff to screen if EOF is reached
				String output = null;
				for (IdTagObjs a : idTags) {
					if (output == null) output = a.getIdVal();
					else output += commaDelimeter + a.getIdVal();
				}
				pui.printToConsole(output); // Be done with the parsing
		}
		catch (IOException fnfe) {
			pui.printToConsole("Can't open the file, make sure it's there.");
		} 
	}
} /* End IdTagObjs */
