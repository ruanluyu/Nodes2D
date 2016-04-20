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
	private List<NodePoint> inPointList = new ArrayList<NodePoint>();
	private List<NodePoint> outPointList = new ArrayList<NodePoint>();

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node";
		idAddable = true;
	}
	///////////////

	protected void addInPoint(int num, Node master) {
		NodePoint.nameId = inPointList.size();
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true));
		}

	}

	protected void addOutPoint(int num, Node master) {
		NodePoint.nameId = outPointList.size();
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false));
		}
	}

	protected void setInputPoint(int num, Node master) {
		NodePoint.nameId = 0;
		if (inPointList.size() > 0)
			inPointList.clear();
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true));
		}
	}

	protected void setOutputPoint(int num, Node master) {
		NodePoint.nameId = 0;
		if (outPointList.size() > 0)
			outPointList.clear();
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false));
		}
	}

	public void setTitle(String title) {
		this.title = title;
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

	public void add(NodePoint point) {
		if (point.isInput()) {
			inPointList.add(point);
		} else {
			outPointList.add(point);
		}
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

	public int getId() {
		return thisId;
	}
}

interface NStreamGenerator {
	NStream generateStream();
}

class NodeGenerator extends Node implements NStreamGenerator {
	protected boolean stillGenerating = false;
	protected int times = 1;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeGenerator";
		idAddable = true;
	}

	///////////////
	@Override
	public NStream generateStream() {
		return null;
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

	NDouble data;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_SolidNumber";
		idAddable = true;
	}

	///////////////
	@Override
	public NStream generateStream() {
		return new NStream();
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
			addInPoint(numOfIn, this);
			addOutPoint(1, this);
		}

	}
}
