package main;

public class NStream extends NObject {
	private static final String CLASSNAME = "NStream";
	private static int id = 0;
	private int delay = 0;
	private boolean stop = false;
	private boolean pause = false;
	private NData data;

	NStream() {
		super();
		thisId = id;
		id++;
	}

	public void setData(NData data) {
		this.data = data;
	}

	public NData getData() {
		return data;
	}

	public boolean isStop() {
		return stop;
	}

	public boolean isPuase() {
		return pause;
	}

}
