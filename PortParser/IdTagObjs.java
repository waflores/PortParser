import java.util.*;


public class IdTagObjs {
	private ParserUI pui; 
	
	private String identifierTag = null;
	private String idVal = "0";
	
	public IdTagObjs (String identifierTag, ParserUI pui) {
		this.pui = pui;
		if (identifierTag == null || identifierTag.length() == 0) {
			throw new IllegalArgumentException("Need an identifier.");
		}
		else setIdentifierTag(identifierTag);
	}

	public void resetIdValues() {
		setIdVal(null);
	}
	
	public String getIdentifierTag() {
		return identifierTag;
	}

	public void setIdentifierTag(String identifierTag) {
		this.identifierTag = identifierTag;
	}

	public String getIdVal() {
		return idVal;
	}

	public void setIdVal(String idVal) {
		this.idVal = idVal;
	}
	
	public void parser () {
		// Open the file in question
				if (fileName == null || identifiers == null) throw new IllegalArgumentException("Can't have null args.");
				ArrayList <IdTagObjs> idTags = new ArrayList<IdTagObjs>(); 
				idTags.add(new IdTagObjs("Port")); // add the port identifiers

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
//					boolean gotNextPortTag = false;
					String line = null;
					// TODO Rem this for csv printout
					serverWindow.setTitle("Looking for the contents of: " + fileName);

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
				// Flush all the stuff to screen if EOF is reached
				catch (NullPointerException npe) {
						String output = null;
						for (IdTagObjs a : idTags) {
							if (output == null) output = a.getIdVal();
							else output += commaDelimeter + a.getIdVal();
						}
						pui.printToConsole(output);
						// Be done with the parsing
				}
				catch (IOException fnfe) {
					pui.printToConsole("Can't open the file, make sure it's there.");
				} 

	}
}
