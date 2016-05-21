package main;

class NodeLine extends NObject {
	private NodePoint inP, outP;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodeLine";
		idAddable = false;
	}

	///////////////
	public NodeLine(NodePoint outpoint, NodePoint inpoint) {

		super();
		inP = inpoint;
		outP = outpoint;

		/*
		 * if (!NBox.theyAreboxAndInnerNode(outpoint.getMaster(),
		 * inpoint.getMaster())) { if (outpoint.isInput() && inpoint.isOutput())
		 * { inP = outpoint; outP = inpoint; } else if (outpoint.isOutput() &&
		 * inpoint.isInput()) { inP = inpoint; outP = outpoint; } else { try {
		 * throw new NodeException(0, "Connection between NodePoint" +
		 * outpoint.getTitle() + " and NodePoint " + outpoint.getTitle() + " ."
		 * ); } catch (NodeException e) { e.println(); } } } else if
		 * (!((outpoint.isInOutMode()) && (inpoint.isInOutMode()))) { if
		 * (inpoint.isInOutMode()) { NodePoint cur = inpoint; inpoint =
		 * outpoint; outpoint = cur; } if (outpoint.isInput() &&
		 * inpoint.isInput()) { outP = outpoint; inP = inpoint; } else if
		 * (outpoint.isOutput() && inpoint.isOutput()) { inP = outpoint; outP =
		 * inpoint; } else { println("Error : Failed in building line");// TODO
		 * Error } } else { println("Error : Failed in building line");// TODO
		 * Error }
		 */

	}

	public NodePoint getInPoint() {
		return inP;
	}

	public NodePoint getOutPoint() {
		return outP;
	}

	@Override
	public NodeLine clone() {
		return new NodeLine(this.outP, this.inP);
	}
}