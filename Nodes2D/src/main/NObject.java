package main;

class NObject {
	protected static int nameId = 0;// Local this id.
	protected static int id = 0;// Global all ids' number.
	protected String CLASSNAME;
	protected String title;
	protected int thisId = 0;// Global this id.

	public NObject() {
		thisId = id++;
		setRegularTitle("NObject");
	}

	public NObject(boolean flag) {
		if(flag) thisId = id++;
		else thisId = -1;
	}

	public NObject(String title) {
		this();
		setTitle(title);
	}

	public void setRegularTitle(String classname) {
		CLASSNAME = classname;
		title = classname + " " + nameId;
		nameId++;
	}

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
		System.out.println(this.title + "( class : "+ CLASSNAME + "----global_id "+thisId+") : \n" + message);
	}
}
