package main;


class NObject {
	private static int id = 0;
	private static final String CLASSNAME = "NodesSystem";
	protected String title;
	protected int thisId = 0;
	public NObject() {
		this(CLASSNAME + " " + id);
	}

	public NObject(String title) {
		thisId = id++;
		this.title = title;
	}
}
