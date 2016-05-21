package main;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO elements of Nodes
 * 
 * @author ZzStarSound
 * @see main.Node
 */
class NodePoint extends NObject {
	private boolean input;
	private boolean inoutMode = false;
	private Node master;
	private List<NodeLine> outLinesList = new ArrayList<NodeLine>();
	private List<NStream> streamList = new ArrayList<NStream>();
	private NodeLine inLine;
	private int type = -1;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodePoint";
		idAddable = true;
	}

	///////////////

	public NodePoint(Node master, boolean input) {
		this("NodePoint" + " " + nameId, master, input);
	}

	public NodePoint(Node master, boolean input, int mode) {
		this("NodePoint" + " " + nameId, master, input, mode);
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
	public NodePoint(String title, Node master, boolean input) {
		this(title, master, input, NData.NDOUBLE);
	}

	public NodePoint(String title, Node master, boolean input, int mode) {
		super();
		this.input = input;
		setTitle(title);
		this.master = master;
		switch (mode) {
		case NData.NDATA:
			type = NData.NDATA;
			break;
		case NData.NDOUBLE:
			type = NData.NDOUBLE;
			break;
		case NData.NINT:
			type = NData.NINT;
			break;
		case NData.NSTRING:
			type = NData.NSTRING;
			break;
		case NData.NBOOLEAN:
			type = NData.NBOOLEAN;
			break;
		case NData.NVECTOR:
			type = NData.NVECTOR;
			break;
		case NData.NIMAGE:
			type = NData.NIMAGE;
		default:
			try {
				throw new NodeException(6, this.title);
			} catch (NodeException e) {
				e.println();
			}
			break;
		}
	}

	public static boolean connectable(NodePoint np1, NodePoint np2) {
		return NData.typeConnectable(np1.getType(), np2.getType());
	}

	public int getType() {
		return type;
	}

	public boolean isInput() {
		return input;
	}

	public boolean isOutput() {
		return !input;
	}

	public Node getMaster() {
		return master;
	}

	public void addLine(NodeLine target) {
		if (inoutMode) {
			if (input) {
				if (target.getOutPoint() == this) {
					if (outLinesList.size() > 0) {
						outLinesList.clear();
						println("Warning:You overrided an input point when it connected.Occured on NodePoint ");
					}
					outLinesList.add(target);
				} else if (target.getInPoint() == this) {
					if (inLine != null) {
						println("Warning:You overrided an input point when it connected.Occured on NodePoint ");
					}
					inLine = target;
				}
			} else {
				if (target.getOutPoint() == this) {
					outLinesList.add(target);
				} else if (target.getInPoint() == this) {
					if (inLine != null) {
						println("Warning:You overrided an input point when it connected.Occured on NodePoint ");
					}
					inLine = target;
				}
			}
			return;
		}
		if (input) {
			if (inLine != null)
				println("Warning:You overrided an input point when it connected.Occured on NodePoint " + title);
			inLine = target;
			return;
		} else
			outLinesList.add(target);
	}

	public void disconnect() {
		inLine = null;
	}

	public void disconnect(NodeLine line) {
		outLinesList.remove(line);
	}

	public void setInOutMode(boolean mode) {
		inoutMode = mode;
	}

	public boolean isInOutMode() {
		return inoutMode;
	}

	public int streamNum() {
		return streamList.size();
	}

	public void cleanStream() {
		if (streamList.size() > 0) {
			streamList.clear();
		}
	}

	public void removeStream(int index) {
		streamList.remove(index);
	}

	public void addStream(NStream nst) {
		if (!input) {
			streamList.add(nst);
		} else {
			if (streamList.size() > 0) {
				println("Warnning : the num of stream on inPoint > 1.\nThis occured while working on " + title + " "
						+ master.getTitle());
				nst.stop();
			} else
				streamList.add(nst);
		}
	}

	public NStream getStream(int id) {
		if (id < streamList.size())
			return streamList.get(id);
		return null;
	}

	public NStream getStream() {
		return getStream(0);
	}

	public boolean hasStream() {
		if (streamList.size() > 0) {
			return true;
		}
		return false;
	}

	public int getNumOfStream() {
		return streamList.size();
	}

	public int getNumOfLines() {

		if (input) {
			if (inLine == null) {
				return 0;
			} else {
				return 1;
			}
		}
		return outLinesList.size();
	}

	public int getNumOfLines(boolean input) {
		if (input) {
			if (inLine == null) {
				return 0;
			} else {
				return 1;
			}
		}
		return outLinesList.size();
	}

	public NodeLine getLine(int i) {
		return outLinesList.get(i);
	}

	public NodeLine getLine() {
		return inLine;
	}

	public void removeStream(NStream ns) {
		streamList.remove(ns);
	}

}
