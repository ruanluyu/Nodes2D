package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO basic object of all Nodes
 * 
 * @version v1.0
 * @author ZzStarSound
 * @see main.NodePoint
 */
class Node extends NObject {
	protected List<NodePoint> inPointList = new ArrayList<NodePoint>();
	protected List<NodePoint> outPointList = new ArrayList<NodePoint>();
	protected boolean stillCompute = false;
	protected int computeTimes = 1;
	protected NodesSystem master = null;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node";
		idAddable = true;
	}

	protected void initializeNode() {
		stillCompute = false;
		computeTimes = 0;
	}

	public Node() {
		super();
		initializeNode();
	}

	public void setMaster(NodesSystem ns) {
		master = ns;
	}

	public NodesSystem getMaster() {
		return master;
	}

	///////////////
	public void generateStream() {

	}

	///////////////

	public NodePoint getOutpoint(int id) {

		return outPointList.get(id);
	}

	public NodePoint getInpoint(int id) {

		return inPointList.get(id);
	}

	protected void addInPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.setNameId(inPointList.size());
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true));
		}
		setNameId(cur);
	}

	protected void addOutPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.setNameId(outPointList.size());
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false, mode));
		}
		setNameId(cur);
	}

	protected void setInputPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.resetNameId();
		if (inPointList.size() > 0)
			inPointList.clear();
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true, mode));
		}
		setNameId(cur);
	}

	protected void setOutputPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.resetNameId();
		if (outPointList.size() > 0)
			outPointList.clear();
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false));
		}
		setNameId(cur);
	}

	protected void startGeneration() {

	}

	protected void endGeneration() {
		if (!stillCompute) {
			computeTimes--;
		}
	}

	/*
	 * public static void disconnect(NodeLine line) {
	 * line.getInPoint().disconnect(); line.getOutPoint().disconnect(line); }
	 * public static NodeLine connect(NodePoint a, NodePoint b) { if
	 * ((a.isInput() && b.isInput()) || (a.isOutput() && b.isOutput())) { try {
	 * throw new NodeException(0, "NodePoint : " + a.getTitle() +
	 * " and NodePoint : " + b.getTitle() + " ."); } catch (NodeException e) {
	 * e.println(); } return null; } else { return new NodeLine(a, b); } }
	 */
	public void addPoint(NodePoint point) {
		int cur = getNameId();
		if (point.isInput()) {
			setNameId(inPointList.size());
			inPointList.add(point);
		} else {
			setNameId(outPointList.size());
			outPointList.add(point);
		}
		setNameId(cur);
	}

	public int getInPointNum() {
		return inPointList.size();
	}

	public int getOutPointNum() {
		return outPointList.size();
	}

	protected boolean inputIsAlready() {
		boolean flag = true;
		if (inPointList.size() > 0) {
			for (NodePoint np : inPointList) {
				if (!np.haveStream()) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	protected boolean outputIsAlready() {
		boolean flag = true;
		if (outPointList.size() > 0) {
			for (NodePoint np : outPointList) {
				if (!np.haveStream()) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public boolean computable() {
		if (!stillCompute) {
			if (computeTimes <= 0)
				return false;
		}
		return inputIsAlready();
	}

	public void cleanOutpointStream(int id) {
		outPointList.get(id).cleanStream();
	}

	public void cleanOutpointStream() {
		for (int i = 0; i < outPointList.size(); i++) {
			cleanOutpointStream(i);
		}
	}

	public void addStreamToOutpoint(int id, NData data) {
		if (id >= outPointList.size())
			return;
		NodePoint cur = outPointList.get(id);
		for (int i = 0; i < cur.getNumOfLines(); i++) {
			cur.cleanStream();
			cur.addStream(new NStream(NData.copyData(data, data.getType()), cur, cur.getLine(i)));
		}
	}

	public void cleanInPoint() {
		for (NodePoint np : inPointList) {
			if (np.getStream() == null) {
				np.cleanStream();
				continue;
			}
			np.getStream().stop();
			np.cleanStream();
		}
	}

	public NData getData(int id) {
		return inPointList.get(id).getStream().getData();
	}

	/*
	 * private NodePoint selectPoint(boolean input,int index){ if(input){
	 * if(index<inPointList.size()&&index>=0){ return inPointList.get(index);
	 * }else{ try{ throw new NodeException("Node : "+title);
	 * }catch(NodeException e){ e.println(); } } }else{
	 * if(index<outPointList.size()&&index>=0){ return outPointList.get(index);
	 * }else{ try{ throw new NodeException("Node : "+title);
	 * }catch(NodeException e){ e.println(); } } } return null; }
	 */

}

class NodeGenerator extends Node {
	protected boolean stillGenerating = false;
	protected int times = 1;
	protected int waitTime = 1;
	protected int lastStep = -1;

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
	}

	public boolean generatable() {
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

	@Override
	protected void endGeneration() {
		if (!stillCompute) {
			computeTimes--;
		}
		lastStep = getMaster().getStep();
	}

	@Override
	public boolean computable() {
		if (!stillCompute) {
			if (computeTimes <= 0)
				return false;
		}

		if (getMaster().getStep() - lastStep < waitTime) {
			return false;
		}
		return inputIsAlready();
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

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_SolidNumber";
		idAddable = true;
	}

	@Override
	protected void initializeGenerator() {
		stillGenerating = false;
		times = 2;
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
		startGeneration();
		if (!computable())
			return;

		addStreamToOutpoint(0, data);
		endGeneration();
	}

	public void setData(double value) {
		data = new NDouble(value);
	}

}

class NodeDelayer extends Node {
	protected double delayTime = 0;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeDelayer";
		idAddable = true;
	}

	protected void initializeDelayer() {
		delayTime = 0;
	}

	///////////////
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
}

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
		startGeneration();
		if (!computable()) {
			if (inputIsAlready()) {
				cleanInPoint();
				return;
			}
		}
		double output = 0;
		for (NodePoint np : inPointList) {
			if (np.getStream() == null)
				continue;
			output += np.getStream().getData().getDoubleData();
		}
		cleanInPoint();
		addStreamToOutpoint(0, new NDouble(output));
		computeTimes--;
		endGeneration();
	}
}

class Node_Printer extends Node {

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_Printer";
		idAddable = true;
	}

	@Override
	protected void initializeNode() {
		stillCompute = true;
		computeTimes = 0;
	}

	public Node_Printer() {
		addInPoint(1, this, NData.NDOUBLE);
		addInPoint(1, this, NData.NINT);
		addInPoint(1, this, NData.NSTRING);
		addInPoint(1, this, NData.NBOOLEAN);
	}

	@Override
	public void generateStream() {
		boolean flag = true;
		for (NodePoint np : inPointList) {
			if (np.getStream() != null) {
				flag = false;
			}
		}
		if (flag) {
			return;
		}
		startGeneration();
		String out = "Output message : \n";
		boolean haveMessage = false;
		for (NodePoint np : inPointList) {
			if (np.getNumOfStream() > 0) {
				haveMessage = true;
				NData cur = np.getStream().getData();
				switch (cur.getType()) {
				case NData.NDOUBLE:
					out += "Double ";
					break;
				case NData.NINT:
					out += "Int ";
					break;
				case NData.NSTRING:
					out += "String ";
					break;
				case NData.NBOOLEAN:
					out += "Boolean ";
					break;
				}
				out += " : ";
				out += cur.getStringData() + " \n";
			}
		}
		if (haveMessage) {
			println(out);
			cleanInPoint();
		}
		cleanInPoint();
		endGeneration();
	}

}
