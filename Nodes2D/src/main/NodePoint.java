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
class NodePoint<E> extends NObject {
	public static int nameId = 0;
	private static int id = 0;
	private static final String CLASSNAME = "NodePoint";
	private boolean input;
	private Node master;
	private List<NodeLine> outLinesList = new ArrayList<NodeLine>();
	private NodeLine inLine;
	private E data = null;

	NodePoint(Node master, boolean input) {
		this(CLASSNAME + " " + nameId, master, input);
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
		nameId++;
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