package main;

class NodeLine extends NObject {
	private static final String CLASSNAME = "NodeLine";
	private static int id = 0;
	private Node inN, outN;
	private NodePoint inP, outP;

	NodeLine(NodePoint p1, NodePoint p2) {
		super();
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