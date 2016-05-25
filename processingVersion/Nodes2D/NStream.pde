
public class NStream extends NObject {
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

  ///////////////

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
    if (!(nowPoint.getMaster() instanceof NodeGenerator))
      nowPoint.removeStream(this);// 因为Generator的流的注册方式是clone()所以NodePoint上不会登记clone()过的NStream
    nowPoint = nowLine.getInPoint();
    if (nowPoint.isInOutMode()) {
      if (nowPoint.getNumOfLines(false) == 0) {
        pause();
        stop();
        return;
      }
      pause = false;
      stop = false;
      NodesSystem nst = nowPoint.getMaster().getMaster();
      for (int i = 0; i < nowPoint.getNumOfLines(false); i++) {
        nowLine = nowPoint.getLine(i);
        nst.addStream(clone());
      }
      pause();
      stop();
      return;
    }
    nowPoint.addStream(this);
    pause();
  }


  public NStream clone() {
    return new NStream(data.clone(), this.getPoint(), this.getLine());
  }

  public void setPoint(NodePoint np) {
    nowPoint = np;
  }

  public void delete() {
    data = null;
    nowPoint.removeStream(this);
    nowPoint = null;
    nowLine = null;
  }

}