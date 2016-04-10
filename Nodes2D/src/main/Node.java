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
class Node extends NObject{

	private static int id = 0;
	private static final String CLASSNAME = "Node";
	private List<NodePoint> inPointList = new ArrayList<NodePoint>();
	private List<NodePoint> outPointList = new ArrayList<NodePoint>();
	public Node() {
		this(CLASSNAME + " " + id);
	}

	public Node(String title) {
		super();
		thisId = id++;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
/*
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
	}*/
	
	public void add(NodePoint<?> point){
		if(point.isInput()){
			inPointList.add(point);
		}else{
			outPointList.add(point);
		}
	}
	/*
	private NodePoint<?> selectPoint(boolean input,int index){
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
	}*/
}

class NodeCalculator extends Node implements NodeComputable{

	@Override
	public float compute() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

interface NodeComputable {
	float compute();
}

interface NodeGenerator {
	float generate();
}
