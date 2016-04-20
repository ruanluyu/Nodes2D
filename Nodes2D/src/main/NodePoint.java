package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO elements of Nodes
 * 
 * @author ZzStarSound
 * @see main.Node
 */
class NodePoint extends NObject {
	private boolean input;
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

	NodePoint(Node master, boolean input) {
		this("NodePoint" + " " + nameId, master, input);
	}

	NodePoint(Node master, boolean input, int mode) {
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
	NodePoint(String title, Node master, boolean input) {
		this(title, master, input, NData.NDOUBLE);
	}

	NodePoint(String title, Node master, boolean input, int mode) {
		super();
		this.input = input;
		setTitle(title);
		this.master = master;
		switch (mode) {
		case NData.NDOUBLE:
			type = NData.NDOUBLE;
			break;
		case NData.NINT:
			type = NData.NINT;
			break;
		case NData.NSTRING:
			type = NData.NSTRING;
			break;
		case NData.NVECTOR:
			type = NData.NVECTOR;
			break;
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

	boolean isInput() {
		return input;
	}

	boolean isOutput() {
		return !input;
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
		} else
			outLinesList.add(target);
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

	public int streamNum() {
		return streamList.size();
	}

	public void cleanStream() {
		streamList.clear();
	}

	public void removeStream(int index) {
		streamList.remove(index);
	}

	public void setStream(NStream nst) {
		streamList.add(nst);
	}

	public NStream getStream(int id) {
		return streamList.get(id);
	}
	
	public boolean haveStream(){
		if(streamList.size() > 0){
			return true;
		}
		return false;
	}
}
