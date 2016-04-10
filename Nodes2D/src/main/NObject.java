package main;


class NObject {
	public static int nameId = 0;//Local this id.
	private static int id = 0;//Global all ids' number.
	private static final String CLASSNAME = "NodesSystem";
	protected String title;
	protected int thisId = 0;//Global this id.
	public NObject() {
		this(CLASSNAME + " " + nameId);
	}

	public NObject(String title) {
		thisId = id++;
		this.title = title;
		nameId++;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
}