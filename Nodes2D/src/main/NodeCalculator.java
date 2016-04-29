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
		inPointAddable = true;
		outPointAddable = false;
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
		if (!inputIsAlready()) {
			return;
		}
		double output = 0;
		for (NodePoint np : inPointList) {
			if (!np.hasStream())
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

/**
 * in0: NData<BR/>
 * <BR/>
 * 
 * out0: NDouble<BR/>
 * out1: NInt<BR/>
 * out2: NBoolean<BR/>
 * out3: NString<BR/>
 * 
 * @author ZzStarSound
 *
 */
class Node_NDataToElse extends NodeCalculator {
	@Override
	protected void initializeNode() {
		stillCompute = true;
		computeTimes = 0;
		inPointAddable = false;
		outPointAddable = false;
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_NDataToElse";
		idAddable = true;
	}

	public Node_NDataToElse() {
		addInPoint(1, this, NData.NDATA);

		addOutPoint(1, this, NData.NDOUBLE);
		addOutPoint(1, this, NData.NINT);
		addOutPoint(1, this, NData.NBOOLEAN);
		addOutPoint(1, this, NData.NSTRING);
	}

	@Override
	public void generateStream() {
		if (!inputIsAlready())
			return;

		NStream curStream = inPointList.get(0).getStream();
		addStreamToOutpoint(0, new NDouble(curStream.getData().getDoubleData()));
		addStreamToOutpoint(1, new NInt(curStream.getData().getIntData()));
		addStreamToOutpoint(2, new NBoolean(curStream.getData().getBooleanData()));
		addStreamToOutpoint(3, new NString(curStream.getData().getStringData()));
		cleanInPoint();
	}

}

class Node_StreamMerge extends NodeCalculator {
	protected int lastStep = 0;
	boolean generated = false;

	@Override
	protected void initializeNode() {
		stillCompute = true;
		computeTimes = 0;
		inPointAddable = true;
		outPointAddable = false;
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_StreamMerge";
		idAddable = true;
	}

	public Node_StreamMerge() {
		addInPoint(2, this, NData.NDATA);
		addOutPoint(1, this, NData.NDATA);
	}

	public Node_StreamMerge(int numOfInput) {
		addInPoint(numOfInput, this, NData.NDATA);
		addOutPoint(1, this, NData.NDATA);
	}

	public void generateStream() {
		if (master.getStep() != lastStep) {
			lastStep = master.getStep();
			generated = false;
		}
		if (generated)
			return;

		inputCheck();
		cleanInPoint();
	}

	public void inputCheck() {

		for (NodePoint np : inPointList) {
			if (np.hasStream()) {
				generated = true;
				addStreamToOutpoint(0, np.getStream());
				break;
			}
		}

	}

}
