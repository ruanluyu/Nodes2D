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
		NodePoint.setNameId(inPointList.size());
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true));
		}
	}

	protected void addOutPoint(int num, Node master, int mode) {
		NodePoint.setNameId(outPointList.size());
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false, mode));
		}
	}

	protected void setInputPoint(int num, Node master, int mode) {
		NodePoint.resetNameId();
		if (inPointList.size() > 0)
			inPointList.clear();
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true, mode));
		}
	}

	protected void setOutputPoint(int num, Node master, int mode) {
		NodePoint.resetNameId();
		if (outPointList.size() > 0)
			outPointList.clear();
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false));
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
		if (point.isInput()) {
			inPointList.add(point);
		} else {
			outPointList.add(point);
		}
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
		boolean flag = true;
		if (!stillCompute) {
			if (computeTimes <= 0)
				flag = false;
		}
		flag = inputIsAlready();
		return flag;
	}

	public boolean generatable() {
		return outputIsAlready();
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
		NodePoint cur = outPointList.get(id);
		for (int i = 0; i < cur.getNumOfLines(); i++) {
			cur.cleanStream();
			cur.addStream(new NStream(NData.copyData(data, data.getType()), cur, cur.getLine(i)));
		}
	}

	public void cleanInPoint() {
		for (NodePoint np : inPointList) {
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

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeGenerator";
		idAddable = true;
	}

	protected void initializeGenerator() {
		stillGenerating = false;
		times = 1;
	}

	public NodeGenerator() {
		super();
		initializeGenerator();
	}

	///////////////
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
		outPointList.get(0).addStream(new NStream(data, outPointList.get(0), null));
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
		stillCompute = false;
		computeTimes = 1;
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
		}
	}

	//////////// RUN
	@Override
	public void generateStream() {
		double output = 0;
		if (!computable()) {
			cleanInPoint();
			return;
		}
		if (inputIsAlready()) {
			for (NodePoint np : inPointList) {
				output += np.getStream().getData().getDoubleData();
			}
			cleanInPoint();
			addStreamToOutpoint(0, new NDouble(output));
			computeTimes--;
		}
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
		stillCompute = false;
		computeTimes = 1;
	}

	public Node_Printer() {
		addInPoint(1, this, NData.NDOUBLE);
		addInPoint(1, this, NData.NINT);
		addInPoint(1, this, NData.NSTRING);
		addInPoint(1, this, NData.NBOOLEAN);
	}

	@Override
	public void generateStream() {
		if (!computable()) {
			cleanInPoint();
			return;
		}
		String out = "";
		for (NodePoint np : inPointList) {
			if (np.getNumOfStream() > 0) {
				NData cur = np.getStream().getData();
				out += cur.getType() + " : ";
				out += cur.getStringData() + " \n";
			}
		}
		println(out);
		computeTimes--;
	}

}
