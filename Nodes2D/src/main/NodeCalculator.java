package main;

class NodeCalculator extends Node {
	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeCalculator";
		idAddable = true;
	}
	///////////////
}

class Node_Pluser extends NodeCalculator {
	/////////////// initialObject

	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_Pluser";
		idAddable = true;
	}

	@Override
	protected void initializeNode() {
		stillCompute = true;
		computeTimes = 0;
	}

	public Node_Pluser() {
		this(2);
	}

	public Node_Pluser(int numOfIn) {
		Build(numOfIn);
	}
	///////////////

	public void Build(int numOfIn) {
		if (numOfIn >= 2) {
			addInPoint(numOfIn, this, NData.NDOUBLE);
			addOutPoint(1, this, NData.NDOUBLE);
		} else {
			println("Error : failed in building " + getTitle());
		}
	}

	//////////// RUN
	@Override
	public void generateStream() {
		if (!computable()) {
			if (inputIsAlready()) {
				cleanInPoint();
				return;
			}
			pauseInputStream();
			return;
		}
		double output = 0;
		for (NodePoint np : inPointList) {
			if (np.getStream() == null)
				continue;
			output += np.getStream().getData().getDoubleData();
		}
		cleanInPoint();
		addStreamToOutpoint(0, new NDouble(output));
		if (!stillCompute) {
			computeTimes--;
		}
	}
}
