package main;

class NodeLine extends NObject {
	private NodePoint inP, outP;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeLine";
		idAddable = true;
	}

	///////////////
	NodeLine(NodePoint p1, NodePoint p2) {
		super();
		if (!NBox.theyAreboxAndInnerNode(p1.getMaster(), p2.getMaster())) {
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
		} else if (!((p1.isInOutMode()) && (p2.isInOutMode()))) {
			if (p2.isInOutMode()) {
				NodePoint cur = p2;
				p2 = p1;
				p1 = cur;
			}
			if (p1.isInput() && p2.isInput()) {
				outP = p1;
				inP = p2;
			} else if (p1.isOutput() && p2.isOutput()) {
				inP = p1;
				outP = p2;
			} else {
				println("Error : Failed in building line");// TODO Error
			}
		} else {
			println("Error : Failed in building line");// TODO Error
		}

	}

	NodePoint getInPoint() {
		return inP;
	}

	NodePoint getOutPoint() {
		return outP;
	}
}