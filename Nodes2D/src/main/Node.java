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
	// private static int id = 0;
	// private static final String CLASSNAME = "Node";
	protected List<NodePoint> inPointList = new ArrayList<NodePoint>();
	protected List<NodePoint> outPointList = new ArrayList<NodePoint>();

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node";
		idAddable = true;
	}

	///////////////
	public void generateStream() {

	}

	///////////////
	public NStream getStream(int id) {
		if (id < getOutPointNum()) {
			return outPointList.get(id).getStream();
		}else return null;//TODO Exception system
	}

	protected void addInPoint(int num, Node master, int mode) {
		NodePoint.nameId = inPointList.size();
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true));
		}

	}

	protected void addOutPoint(int num, Node master, int mode) {
		NodePoint.nameId = outPointList.size();
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

	NDouble data;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_SolidNumber";
		idAddable = true;
	}

	public Node_SolidNumber() {
		setOutputPoint(1, this, NData.NDOUBLE);
	}

	///////////////
	@Override
	public void generateStream() {
	}

	public void setData(double value) {
		data = new NDouble(value);
	}

}

class NodeDelayer extends Node {
	private double delayTime = 0;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeDelayer";
		idAddable = true;
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
	///////////////

	public void Build(int numOfIn) {
		if (numOfIn > 2) {
			addInPoint(numOfIn, this, NData.NDOUBLE);
			addOutPoint(1, this, NData.NDOUBLE);
		}

	}
}
