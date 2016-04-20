package main;

public class NStream extends NObject {
	private int delay = 0;
	private boolean stop = false;
	private boolean pause = false;
	private NData data;
	private NodePoint nowPoint;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NStream";
		idAddable = false;
	}

	///////////////
	
	public NStream(NData data,NodePoint nowPoint){
		super();
		this.data = data;
		this.nowPoint = nowPoint;
	}

	public void setData(NData data) {
		this.data = data;
	}

	public NData getData() {
		return data;
	}

	public void stop(){
		stop = true;
	}
	
	public void stop(boolean flag){
		stop = flag;
	}
	
	public void pause(){
		pause = true;
	}
	
	public void pause(boolean flag){
		pause = flag;
	}
	
	public boolean isStop() {
		return stop;
	}

	public boolean isPuase() {
		return pause;
	}

}
