package main;

public class NStream extends NObject {
	private int delay = 0;
	private boolean stop = false;
	private boolean pause = false;
	private NData data;
	private NodePoint nowPoint;

	public NStream() {
		super(false);
		setRegularTitle("NStream");
	}

	protected NStream(boolean flag) {
		super(flag);
	}
	
	public NStream(NData data,NodePoint nowPoint){
		this();
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
