package main;

import java.util.ArrayList;
import java.util.List;

public class NBox extends Node {
	List<NodePoint> inPointList_s = new ArrayList<NodePoint>();
	List<NodePoint> outPointList_s = new ArrayList<NodePoint>();

	@Override
	protected void initializeObject() {
		CLASSNAME = "NBox";
		idAddable = true;
	}

	NBox() {
		addInPoint(1, NData.NDOUBLE);
		addOutPoint(1, NData.NDOUBLE);
	}

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

	
}
