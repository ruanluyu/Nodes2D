package main;

class NodeGenerator extends Node {
	protected boolean stillGenerating = false;
	protected int times = 1;
	protected int waitTime = 1;
	private int lastStep = -1;

	@Override
	public NodeGenerator clone() {
		return null;
	}

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeGenerator";
		idAddable = true;
	}

	@Override
	protected void initializeNode() {
		stillCompute = false;
		computeTimes = 1;
		inPointAddable = false;
		outPointAddable = false;
	}

	protected void initializeGenerator() {
		stillGenerating = false;
		times = 1;
		waitTime = 1;
	}

	public NodeGenerator() {
		super();
		initializeGenerator();
	}

	///////////////
	public void generated() {
		if (!stillGenerating) {
			times--;
		}
		lastStep = getMaster().getStep();
	}

	public boolean generatable() {
		if (getMaster().getStep() - lastStep < waitTime) {
			return false;
		}

		if (stillGenerating) {
			return true;
		}
		if (times > 0) {
			return true;
		}

		return false;
	}

	protected void sendReadyPointToOutPoint() {

	}

	public boolean isGenerating() {
		return stillGenerating;
	}

	public void setGenerating(boolean flag) {
		stillGenerating = flag;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public void addTimes(int times) {
		this.times += times;
	}

}

class Node_SolidNumber extends NodeGenerator {

	NData data;

	@Override
	public Node_SolidNumber clone() {
		Node_SolidNumber cloned = new Node_SolidNumber(this.data.getDoubleData());
		clone(this, cloned);
		return cloned;
	}

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_SolidNumber";
		idAddable = true;
	}

	@Override
	protected void initializeGenerator() {
		stillGenerating = false;
		times = 1;
		waitTime = 1;
	}

	public Node_SolidNumber() {
		this(0);
	}

	public Node_SolidNumber(double value) {
		setOutputPoint(1, this, NData.NDOUBLE);
		setData(value);
	}

	///////////////
	@Override
	public void generateStream() {
		if (!computable())
			return;

		addStreamToOutpoint(0, data);
		if (!stillCompute) {
			computeTimes--;
		}
	}

	public void setData(double value) {
		data = new NDouble(value);
	}
	

}
