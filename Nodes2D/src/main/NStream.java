package main;

public class NStream extends NObject {
	private static final String CLASSNAME = "NStream";
	private static int id = 0;
	private int delay = 0;
	private boolean stop = false;
	private boolean pause = false;
	private NData data;
	private NodePoint nowPoint;

	public NStream() {
		super();
		thisId = id;
		id++;
	}
	
	public NStream(NData data,NodePoint nowPoint){
		this.data = data;
		this.nowPoint = nowPoint;
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
