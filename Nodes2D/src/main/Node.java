package main;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO basic object of all Nodes
 * 
 * @version v1.0
 * @author ZzStarSound
 * @see main.NodePoint
 */
class Node extends NObject {
	protected List<NodePoint> inPointList = new ArrayList<NodePoint>();
	protected List<NodePoint> outPointList = new ArrayList<NodePoint>();
	protected boolean stillCompute = false;
	protected int computeTimes = 1;
	protected NodesSystem master = null;
	protected NBox boxMaster = null;
	protected boolean inPointAddable = false;
	protected boolean outPointAddable = false;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node";
		idAddable = true;
	}

	protected void initializeNode() {
		stillCompute = false;
		computeTimes = 0;
		inPointAddable = false;
		outPointAddable = false;
	}

	public Node() {
		super();
		initializeNode();
	}

	public void setMaster(NodesSystem ns) {
		master = ns;
	}

	public void setMaster(NBox nb) {
		boxMaster = nb;
	}

	public NodesSystem getMaster() {
		return master;
	}

	public NBox getBoxMaster() {
		return boxMaster;
	}

	///////////////
	public void generateStream() {

	}

	///////////////

	public NodePoint getOutpoint(int id) {

		return outPointList.get(id);
	}

	public NodePoint getInpoint(int id) {

		return inPointList.get(id);
	}

	protected void addInPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.setNameId(inPointList.size());
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true, mode));
		}
		setNameId(cur);
	}

	public void addInPoint(int num, int mode) {
		addInPoint(num, this, mode);
	}

	protected void addOutPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.setNameId(outPointList.size());
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false, mode));
		}
		setNameId(cur);
	}

	public void addOutPoint(int num, int mode) {
		addOutPoint(num, this, mode);
	}

	protected void setInputPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.resetNameId();
		if (inPointList.size() > 0)
			inPointList.clear();
		for (int i = 0; i < num; i++) {
			inPointList.add(new NodePoint(master, true, mode));
		}
		setNameId(cur);
	}

	protected void setOutputPoint(int num, Node master, int mode) {
		int cur = getNameId();
		NodePoint.resetNameId();
		if (outPointList.size() > 0)
			outPointList.clear();
		for (int i = 0; i < num; i++) {
			outPointList.add(new NodePoint(master, false));
		}
		setNameId(cur);
	}

	public boolean isComputing() {
		return stillCompute;
	}

	public void addPoint(NodePoint point) {
		int cur = getNameId();
		if (point.isInput()) {
			setNameId(inPointList.size());
			inPointList.add(point);
		} else {
			setNameId(outPointList.size());
			outPointList.add(point);
		}
		setNameId(cur);
	}

	public int getInPointNum() {
		return inPointList.size();
	}

	public int getOutPointNum() {
		return outPointList.size();
	}

	protected boolean inputIsAlready() {
		if (inPointList.size() > 0) {
			for (NodePoint np : inPointList) {
				if (!np.hasStream() || np.getStream().isStop()) {
					return false;
				}
			}
		}
		return true;
	}

	protected boolean outputIsAlready() {
		boolean flag = true;
		if (outPointList.size() > 0) {
			for (NodePoint np : outPointList) {
				if (!np.hasStream()) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public boolean computable() {
		if (!stillCompute) {
			if (computeTimes <= 0) {
				return false;
			}
		}
		return inputIsAlready();
	}

	public void cleanOutpointStream(int id) {
		outPointList.get(id).cleanStream();
	}

	public void cleanOutpointStream() {
		for (int i = 0; i < outPointList.size(); i++) {
			cleanOutpointStream(i);
		}
	}

	public void addStreamToOutpoint(int id, NData data) {
		if (id >= outPointList.size())
			return;
		NodePoint cur = outPointList.get(id);
		cur.cleanStream();
		for (int i = 0; i < cur.getNumOfLines(); i++) {
			cur.addStream(new NStream(NData.clone(data, data.getType()), cur, cur.getLine(i)));
		}
	}

	public void addStreamToOutpoint(int id, NStream stream) {
		if (id >= outPointList.size())
			return;
		NodePoint cur = outPointList.get(id);
		cur.cleanStream();
		for (int i = 0; i < cur.getNumOfLines(); i++) {
			NStream newStream = stream.clone();
			newStream.stop(false);
			newStream.pause(false);
			newStream.setPoint(cur);
			newStream.setLine(cur.getLine(i));
			cur.addStream(newStream);

		}

	}

	public void cleanInPoint() {
		for (NodePoint np : inPointList) {
			if ((!np.hasStream()))
				continue;
			if ((np.getStream().getPoint() == np))
				np.getStream().stop();
			np.cleanStream();
		}
	}

	protected void sendInToOut(int inId, int outId) {
		if (inId >= inPointList.size()) {
			return;// TODO Error
		}
		if (outId >= outPointList.size()) {
			return;// TODO Error
		}


		addStreamToOutpoint(outId, inPointList.get(inId).getStream());
		inPointList.get(inId).cleanStream();

	}

	public NStream getInpointStream(int id) {
		if (id >= inPointList.size()) {
			return null;
		}
		if (!(inPointList.get(id).hasStream())) {
			return null;
		}
		NStream curStream = inPointList.get(id).getStream();
		if (curStream.isStop()) {
			return null;
		}
		return curStream;
	}

	public NData getData(int id) {
		return inPointList.get(id).getStream().getData();
	}

	public void stopInputStream() {
		for (NodePoint np : inPointList) {
			if (!np.hasStream())
				continue;
			np.getStream().stop();
		}
	}

	public void pauseInputStream() {
		for (NodePoint np : inPointList) {
			if (!np.hasStream())
				continue;
			np.getStream().pause();
		}
	}

	public int getComputeTimes() {
		return computeTimes;
	}

	public Node clone() {
		return null;
	}

	/*
	 * private NodePoint selectPoint(boolean input,int index){ if(input){
	 * if(index<inPointList.size()&&index>=0){ return inPointList.get(index);
	 * }else{ try{ throw new NodeException("Node : "+title);
	 * }catch(NodeException e){ e.println(); } } }else{
	 * if(index<outPointList.size()&&index>=0){ return outPointList.get(index);
	 * }else{ try{ throw new NodeException("Node : "+title);
	 * }catch(NodeException e){ e.println(); } } } return null; }
	 */

}

/**
 * 
 * in0 : NDouble<BR/>
 * in1 : NInt<BR/>
 * in2 : NString<BR/>
 * in3 : NBoolean<BR/>
 * in4 : NVector<BR/>
 * in5 : NData<BR/>
 * 
 * @author ZzStarSound
 *
 */
class Node_Printer extends Node {

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "Node_Printer";
		idAddable = true;
	}

	@Override
	protected void initializeNode() {
		stillCompute = true;
		computeTimes = 0;
		inPointAddable = false;
		outPointAddable = false;
	}

	public Node_Printer() {
		addInPoint(1, this, NData.NDOUBLE);
		addInPoint(1, this, NData.NINT);
		addInPoint(1, this, NData.NSTRING);
		addInPoint(1, this, NData.NBOOLEAN);
		addInPoint(1, this, NData.NVECTOR);
		addInPoint(1, this, NData.NDATA);
	}

	@Override
	public void generateStream() {
		boolean flag = true;
		for (NodePoint np : inPointList) {
			if ((np.hasStream()) && (!(np.getStream().isStop()))) {
				flag = false;
			}
		}
		if (flag) {
			return;
		}
		String out = "Output message : \n";
		boolean haveMessage = false;
		for (NodePoint np : inPointList) {
			if (np.getNumOfStream() > 0) {
				haveMessage = true;
				NData cur = np.getStream().getData();
				switch (cur.getType()) {
				case NData.NDOUBLE:
					out += "NDouble ";
					break;
				case NData.NINT:
					out += "NInt ";
					break;
				case NData.NSTRING:
					out += "NString ";
					break;
				case NData.NBOOLEAN:
					out += "NBoolean ";
					break;
				case NData.NDATA:
					out += "NData ";
					break;
				}
				out += " : ";
				out += cur.getStringData() + " \n";
			}
		}
		if (haveMessage) {
			println(out);
			cleanInPoint();
		}
		cleanInPoint();
		if (!stillCompute) {
			computeTimes--;
		}
	}

	@Override
	public Node_Printer clone() {
		Node_Printer cloned = new Node_Printer();
		return cloned;
	}

}
