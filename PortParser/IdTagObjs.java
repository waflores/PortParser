
public class IdTagObjs {

	private String identifierTag = null;
	private String idVal = "0";
	
	public IdTagObjs (String identifierTag) {
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
}
