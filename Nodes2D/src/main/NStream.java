package main;

public class NStream extends NObject implements Cloneable {
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
		
		if (nowPoint.isOutput()) {
			if (!(nowPoint.getMaster() instanceof NodeGenerator))
				nowPoint.removeStream(this);
			nowPoint = nowLine.getInPoint();
			nowPoint.addStream(this);
			println(nowPoint.getMaster().getTitle()+"");
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

	public NStream clone() {
		return new NStream(NData.copyData(data, data.getType()), nowPoint, nowLine);
	}

	public NStream copyStream() {
		NStream cloned = null;
		try {
			cloned = (NStream) this.clone();
			cloned.setData(NData.copyData(data, data.getType()));
		} catch (Exception e) {
			System.out.println("Error : CloneNotSupportException");
		}
		return cloned;
	}

}
