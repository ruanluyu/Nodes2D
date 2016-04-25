package main;

class NodeDelayer extends Node {
	protected int delayTime = 0;
	protected int signedTime = -1;

	NData data = null;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeDelayer";
		idAddable = true;
	}

	protected void initializeDelayer() {
		delayTime = 1;
	}

	///////////////
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
}
