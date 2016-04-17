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
//////////////////////////
	public Node() {
		super(true);
		setRegularTitle("Node");
	}

	protected Node(boolean flag) {
		super(flag);
	}

	public Node(String title) {
		this();
		setTitle(title);
	}
//////////////////////
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
}

interface NStreamGenerator {
	NStream generateStream();
}

class NodeGenerator extends Node implements NStreamGenerator {
	protected boolean stillGenerating = false;
	protected int times = 1;

	public NodeGenerator() {
		super(true);
		setRegularTitle("NodeGenerator");
	}

	protected NodeGenerator(boolean flag) {
		super(flag);
	}

	public NodeGenerator(String title) {
		this();
		setTitle(title);
	}

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

	public Node_SolidNumber() {
		super(true);
		setRegularTitle("Node_SolidNumber");
	}

	protected Node_SolidNumber(boolean flag) {
		super(flag);
	}

	public Node_SolidNumber(String title) {
		this();
		setTitle(title);
	}

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

	public NodeDelayer() {
		super(true);
		setRegularTitle("NodeDelayer");
	}

	protected NodeDelayer(boolean flag) {
		super(flag);
	}

	public NodeDelayer(String title) {
		this();
		setTitle(title);
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
}

class NodeCalculator extends Node {

	public NodeCalculator() {
		super(true);
		setRegularTitle("NodeCalculator");
	}

	protected NodeCalculator(boolean flag) {
		super(flag);
	}

	public NodeCalculator(String title) {
		this();
		setTitle(title);
	}

}

class Node_Pluser extends NodeCalculator {
	public Node_Pluser() {
		super(true);
		setRegularTitle("Node_Pluser");
		Build(2);
	}

	protected Node_Pluser(boolean flag) {
		super(flag);
	}

	public Node_Pluser(String title) {
		this();
		setTitle(title);
	}

	public void Build(int numOfIn) {
		if (numOfIn > 2) {
			addInPoint(numOfIn, this);
			addOutPoint(1, this);
		}

	}
}
