package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class NodesSystem extends NObject {
	private List<Node> nodeList = new ArrayList<Node>();
	private List<NodeLine> lineList = new ArrayList<NodeLine>();
	private List<NStream> streamList = new ArrayList<NStream>();

	private int sleepTime = 100;
	private int step = 0;
	private int maxStep = 20;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NodesSystem";
		idAddable = true;
	}

	///////////////
	public void addNode(Node node) {
		nodeList.add(node);
		node.setMaster(this);
	}

	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}

	// Node control:
	public int getStep() {
		return step;
	}

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
				NodeLine out = new NodeLine(a, b);
				String cur1, cur2;
				if (a.isInput()) {
					cur1 = "in";
					cur2 = "out";
				} else {
					cur1 = "out";
					cur2 = "in";
				}
				println(a.getMaster().getTitle() + "(" + cur1 + " " + a.getTitle() + ") ----- "
						+ b.getMaster().getTitle() + "(" + cur2 + " " + b.getTitle() + ") connected");
				a.addLine(out);
				b.addLine(out);
				lineList.add(out);
				return out;
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

	public static void cleanArrayNull(List<?> list) {
		for (Object obj : list) {
			if (obj == null) {
				list.remove(obj);
			}
		}
	}

	//////////////////
	///// RUN//////
	////////////////

	private void cleanStopStream() {
		cleanArrayNull(streamList);
		for (Iterator<NStream> i = streamList.iterator(); i.hasNext();) {
			NStream ns = i.next();
			if (ns == null)
				continue;
			if (ns.isStop()) {
				i.remove();
			}
		}
	}

	private boolean stepBelowMaxStep() {
		if (maxStep < 0)
			return true;
		if (step < maxStep)
			return true;
		return false;
	}

	private void generateStream() {
		for (Node np : nodeList) {
			np.generateStream();
			if (np.getOutPointNum() > 0) {
				for (int i = 0; i < np.getOutPointNum(); i++) {
					NodePoint cur = np.getOutpoint(i);
					if (cur.getNumOfStream() <= 0)
						continue;
					if ((np instanceof NodeGenerator) && ((NodeGenerator) np).generatable()) {
						for (int j = 0; j < cur.getNumOfStream(); j++) {
							streamList.add(cur.getStream(j).copyStream());
						}
						((NodeGenerator) np).generated();
					} else if (!(np instanceof NodeGenerator)) {
						for (int j = 0; j < cur.getNumOfStream(); j++) {
							streamList.add(cur.getStream(j));
							cur.cleanStream();
						}

					}
				}
			}
		}
		cleanArrayNull(streamList);
	}

	private boolean transportStream() {
		boolean transported = false;
		for (NStream ns : streamList) {
			if (ns.isPuase() || ns.isStop())
				continue;
			ns.goToInpoint();
			ns.pause();
			transported = true;
		}
		return transported;
	}

	private void setStreamToInpoint() {
		for (NStream ns : streamList) {
			NodePoint cur = ns.getPoint();
			if (cur.isInput()) {
				cur.cleanStream();
				cur.addStream(ns);
				ns.pause();
			}
		}
	}

	private void oneComputeStep() {
		while (transportStream()) {
			setStreamToInpoint();
			cleanStopStream();
			generateStream();
			
			println(streamList.size()+"");
			println(streamList.get(0).getPoint().getMaster().getTitle());
			println(streamList.get(1).getPoint().getMaster().getTitle());
		}

	}

	public void run() {
		println("************************\n RUN.\n\n************************");
		generateStream();

		while (streamList.size() > 0 && stepBelowMaxStep()) {
			
			oneComputeStep();
			cleanStopStream();
			step++;
			println("************************\n step : " + step + " completed.\n\n************************");
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		println("All done");
	}
}
