package main;

class NObject {
	protected static int nameId = 0;// Local this id.
	protected static int id = 0;// Global all ids' number.
	protected String CLASSNAME = "N_NULL";
	protected String title;
	protected int thisId = 0;// Global this id.
	protected boolean idAddable = true;
	///////////////

	public NObject() {
		initializeObject();
		if (idAddable) {
			thisId = id++;
			title = CLASSNAME + " " + nameId;
			nameId++;
		} else {
			thisId = -1;
			title = CLASSNAME;
		}

	}

	protected void initializeObject() {
		CLASSNAME = "NObject";
		idAddable = true;
	}

	//////////////

	public static void setNameId(int id) {
		nameId = id;
	}

	public static void resetNameId() {
		nameId = 0;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getClassName() {
		return CLASSNAME;
	}

	public void println(String message) {
		System.out.println(this.title + "( class : " + CLASSNAME + "----global_id " + thisId + ") : \n" + message);
	}

	public int getId() {
		return thisId;
	}
}
