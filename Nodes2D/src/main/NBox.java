package main;

import java.util.ArrayList;
import java.util.List;

public class NBox extends Node {
	private List<Node> nodeList = new ArrayList<Node>();

	@Override
	protected void initializeObject() {
		CLASSNAME = "NBox";
		idAddable = true;
	}

	public NBox() {
		addInPoint(1, NData.NDOUBLE);
		addOutPoint(1, NData.NDOUBLE);
	}

	public void addNode(Node node) {
		nodeList.add(node);
		node.setMaster(this);
		if(node.getMaster() == null){
			node.setMaster(master);
			master.addNode(node);
		}
	}
	
	public void removeNode(Node node){
		nodeList.remove(node);
	};

	@Override
	protected void addInPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.setNameId(inPointList.size());
		for (int i = 0; i < num; i++) {
			NodePoint curA = new NodePoint(master, true, mode);
			curA.setInOutMode(true);
			inPointList.add(curA);
		}
		setNameId(cur);
	}

	@Override
	public void addInPoint(int num, int mode) {
		addInPoint(num, this, mode);
	}

	@Override
	protected void addOutPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.setNameId(outPointList.size());
		for (int i = 0; i < num; i++) {
			NodePoint curA = new NodePoint(master, false, mode);
			curA.setInOutMode(true);
			inPointList.add(curA);
		}
		setNameId(cur);
	}

	@Override
	public void addOutPoint(int num, int mode) {
		addOutPoint(num, this, mode);
	}

	@Override
	protected void initializeNode() {
		stillCompute = true;
		computeTimes = 0;
		inPointAddable = true;
		outPointAddable = true;
	}

	public static boolean inTheSameBox(Node a, Node b) {

		if (a.getMaster() == b.getMaster() && a.getBoxMaster() == b.getBoxMaster()) {
			return true;
		}
		if (a instanceof NBox) {
			if ((Object) a == (Object) b.getBoxMaster()) {
				return true;
			}
		}
		if (b instanceof NBox) {
			if ((Object) b == (Object) a.getBoxMaster()) {
				return true;
			}
		}
		return false;
	}

	public static boolean theyAreboxAndInnerNode(Node a, Node b) {
		if (a instanceof NBox) {
			if ((Object) a == (Object) b.getBoxMaster()) {
				return true;
			}
		}
		if (b instanceof NBox) {
			if ((Object) b == (Object) a.getBoxMaster()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void delete(){
		for(Node nd:nodeList){
			nd.delete();
		}
		deleteAllLines();
		if (master != null) {
			master.removeNode(this);
		}
		if (boxMaster != null) {
			boxMaster.removeNode(this);
		}
	}

}
