package main;

import java.util.ArrayList;
import java.util.List;

class NodesSystem extends NObject {
	private List<Node> nodeList = new ArrayList<Node>();
	private List<NodeLine> lineList = new ArrayList<NodeLine>();
	private List<NStream> streamList = new ArrayList<NStream>();

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodesSystem";
		idAddable = true;
	}

	///////////////
	public void addNode(Node node) {
		nodeList.add(node);
	}

	// Node control:
	public void removeNode() {
		removeNode(0);
	}

	public void removeNode(int index) {
		nodeList.remove(index);
	}

	public void removeAllNode() {
		nodeList.clear();
	}

	public int getNodeNum() {
		return nodeList.size();
	}

	protected Node selectNode(int id) {
		for (Node cur : nodeList) {
			if (cur.getId() == id) {
				return cur;
			}
		}
		println("Error : nothing selected.");
		return null;
	}

	public void disconnect(NodeLine line) {
		line.getInPoint().disconnect();
		line.getOutPoint().disconnect(line);
		println(line.getOutPoint().getTitle() + " - / - " + line.getInPoint().getTitle() + " (disconnected)");
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
			if (NodePoint.connectable(a, b)) {
				println(a.getTitle() + " ----- " + b.getTitle() + " (connected)");
				return new NodeLine(a, b);
			} else {
				try {
					throw new NodeException(6,
							"NodePoint : " + a.getTitle() + " and NodePoint : " + b.getTitle() + " .");
				} catch (NodeException e) {
					e.println();
				}
				return null;
			}
		}
	}

	/////////////////
	///// RUN//////
	////////////////

}
