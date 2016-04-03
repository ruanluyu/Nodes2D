package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class NodesSystem {
	public static int id = 0;
	public static final String className = "NodeSystem";
	private String title;
	private int thisId;
	private List<NodeLine> lineList = new ArrayList<NodeLine>();
	private List<Node> nodeList = new ArrayList<Node>();

	NodesSystem() {
		this(className + " " + id);
	}

	NodesSystem(String title) {
		this.title = title;
		thisId = id++;
	}
}

/**
 * TODO basic object of all Nodes
 * 
 * @version v1.0
 * @author ZzStarSound
 * @see main.NodePoint
 */
class Node {

	public static int id = 0;
	public static final String className = "Node";
	private int thisId;
	private String title;
	private List<NodePoint> inPointList = new ArrayList<NodePoint>();
	private List<NodePoint> outPointList = new ArrayList<NodePoint>();
	public Node() {
		this(className + " " + id);
	}

	public Node(String title) {
		thisId = id++;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static void disconnect(NodeLine line) {
		line.getInPoint().disconnect();
		line.getOutPoint().disconnect(line);
	}

	public static NodeLine connect(NodePoint a, NodePoint b) {

		if ((a.isInput() && b.isInput()) || (a.isOutput() && b.isOutput())) {
			try {
				throw new NodeException(0, "NodePoint : " + a.getTitle() + " and NodePoint : " + b.getTitle() + " .");
			} catch (NodeException e) {
				e.println();
			}
			return null;
		} else {
			return new NodeLine(a, b);
		}
	}
	
	public void add(NodePoint point){
		if(point.isInput()){
			inPointList.add(point);
		}else{
			outPointList.add(point);
		}
	}
	
	private NodePoint selectPoint(boolean input,int index){
		if(input){
			if(index<inPointList.size()&&index>=0){
				return inPointList.get(index);
			}else{
				try{
					throw new NodeException("Node : "+title);
				}catch(NodeException e){
					e.println();
				}
			}
		}else{
			if(index<outPointList.size()&&index>=0){
				return outPointList.get(index);
			}else{
				try{
					throw new NodeException("Node : "+title);
				}catch(NodeException e){
					e.println();
				}
			}
		}
		return null;
	}
}

interface NodeComputable {
	float compute();
}

interface NodeGenerator {
	float generate();
}

class NodeCalculator extends Node implements NodeComputable{

	@Override
	public float compute() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

/**
 * TODO elements of Nodes
 * 
 * @author ZzStarSound
 * @see main.Node
 */
class NodePoint<E> {
	public static int id = 0;
	public static final String className = "NodePoint";
	private int thisId;
	private boolean input;
	private String title;
	private Node master;
	private List<NodeLine> outLinesList = new ArrayList<NodeLine>();
	private NodeLine inLine;
	private E data = null;

	NodePoint(Node master, boolean input) {
		this(className + " " + id, master, input);
	}

	/**
	 * @param title
	 *            name of point.
	 * @param master
	 *            the Node where NodePoint is.
	 * @param input
	 *            it can be installed in input area while it is true.
	 * @see main.NodePoint
	 */
	NodePoint(String title, Node master, boolean input) {
		thisId = id++;
		this.input = input;
		this.title = title;
		this.master = master;
	}
	
	void setData(E data){
		if(data!=null) this.data = data;
	}
	public E getData(){
		if(data != null)return data;
		else return null;
	}
	
	boolean isInput() {
		return input;
	}

	boolean isOutput() {
		return !input;
	}

	String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	Node getMaster() {
		return master;
	}

	void setLine(NodeLine target) {
		if (input) {
			if (inLine != null) {
				System.out
						.println("Warning:You override an input point when it connected.Occured on NodePoint " + title);
				inLine = target;
			}
		} else outLinesList.add(target);
	}

	void disconnect() {
		inLine = null;
	}

	void disconnect(NodeLine line) {
		boolean flag = false;
		for (Iterator<NodeLine> i = outLinesList.iterator(); i.hasNext();) {
			if (i.next() == line) {
				i.remove();
				flag = true;
				break;
			}
		}
		if (flag == false) {
			try {
				throw new NodeException(1, "NodePoint : " + title);
			} catch (NodeException e) {
				e.println();
			}
		}
	}
}

class NodeLine {
	public static int id = 0;
	private int thisId;
	private Node inN, outN;
	private NodePoint inP, outP;

	NodeLine(NodePoint p1, NodePoint p2) {
		thisId = id++;
		if (p1.isInput() && p2.isOutput()) {
			inP = p1;
			outP = p2;
		} else if (p1.isOutput() && p2.isInput()) {
			inP = p2;
			outP = p1;
		} else {
			try {
				throw new NodeException(0,
						"Connection between NodePoint" + p1.getTitle() + " and NodePoint " + p1.getTitle() + " .");
			} catch (NodeException e) {
				e.println();
			}
		}
		inN = inP.getMaster();
		outN = outP.getMaster();
	}

	NodePoint getInPoint() {
		return inP;
	}

	NodePoint getOutPoint() {
		return outP;
	}

	Node getInNode() {
		return inN;
	}

	Node getOutNode() {
		return outN;
	}
}