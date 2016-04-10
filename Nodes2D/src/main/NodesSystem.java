package main;

import java.util.ArrayList;
import java.util.List;

class NodesSystem extends NObject{
	private static int id = 0;
	private static final String CLASSNAME = "NodesSystem";
	private List<NodeLine> lineList = new ArrayList<NodeLine>();
	private List<Node> nodeList = new ArrayList<Node>();
	
	public NodesSystem() {
		this(CLASSNAME + " " + id);
	}

	public NodesSystem(String title) {
		super();
		thisId = id++;
		this.title = title;
	}
	
	public void println(String message){
		System.out.println(this.title+"(NodesSystem "+thisId+") : "+message);
	}
	
	public void disconnect(NodeLine line) {
		line.getInPoint().disconnect();
		line.getOutPoint().disconnect(line);
		println(line.getOutPoint().getTitle()+" - / - "+line.getInPoint().getTitle()+" (disconnected)");
	}
	
	public NodeLine connect(NodePoint a, NodePoint b) {
		if ((a.isInput() && b.isInput()) || (a.isOutput() && b.isOutput())) {
			try {
				throw new NodeException(0, "NodePoint : " + a.getTitle() + " and NodePoint : " + b.getTitle() + " .");
			} catch (NodeException e) {
				e.println();
			}
			return null;
		} else {
			println(a.getTitle()+" ----- "+b.getTitle()+" (connected)");
			return new NodeLine(a, b);
		}
	}
}
