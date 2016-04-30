package main;

public class NStream extends NObject {
	private int delay = 0;
	private boolean stop = false;
	private boolean pause = false;
	private NData data;
	private NodePoint nowPoint;
	private NodeLine nowLine;

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NStream";
		idAddable = false;
	}

	///////////////

	public NStream(NData data, NodePoint nowPoint, NodeLine nowLine) {
		super();
		this.data = data;
		this.nowPoint = nowPoint;
		this.nowLine = nowLine;
	}

	public void setLine(NodeLine nl) {
		nowLine = nl;
	}

	public void setData(NData data) {
		this.data = data;
	}

	public NData getData() {
		return data;
	}

	public void stop() {
		stop = true;
	}

	public void stop(boolean flag) {
		stop = flag;
	}

	public void pause() {
		pause = true;
	}

	public void pause(boolean flag) {
		pause = flag;
	}

	public boolean isStop() {
		return stop;
	}

	public boolean isPause() {
		return pause;
	}

	public NodePoint getPoint() {
		return nowPoint;
	}

	public NodeLine getLine() {
		return nowLine;
	}

	public void goToInpoint() {
		/*
		 * println(nowPoint.getMaster().getTitle() + "+");
		 * println(nowPoint.getType() + "+"); println(nowPoint.isOutput() +
		 * "+"); println(nowLine.getOutPoint().getMaster(). getTitle() + "*");
		 * println(nowLine.getInPoint().getMaster(). getTitle() + "*");
		 */
		if (nowPoint.isOutput()) {
			if (!(nowPoint.getMaster() instanceof NodeGenerator))
				nowPoint.removeStream(this);
			nowPoint = nowLine.getInPoint();
			// println(nowPoint.getMaster().getTitle()+"-");
			nowPoint.addStream(this);
			if (nowPoint.getMaster().inputIsAlready() && (!nowPoint.getMaster().computable())) {
				stop();
			}

			if ((!nowPoint.getMaster().isComputing()) && nowPoint.getMaster().getComputeTimes() <= 0) {
				stop();
			}
			pause();
		} else {
			pause();
		}
	}

	public static NStream clone(NStream target){
		return new NStream(NData.clone(target.getData()), target.getPoint(), target.getLine());
	}
	
	public NStream clone() {
		return clone(this);
	}

	public void setPoint(NodePoint np) {
		nowPoint = np;
	}

}
