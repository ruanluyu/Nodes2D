
class NodeLine extends NObject {
  private NodePoint inP, outP;
  boolean activated = false;

  void render(){
    PVector p1 = outP.position;
    PVector p2 = inP.position;
    float center = (p1.x+p2.x)/2;
    //float cen = (p1.x+p2.x)/2;
    if(activated){
      stroke(224, 67, 89);
      strokeWeight(3);
    }else{
      stroke(20);
      strokeWeight(2);
    }
    noFill();
    bezier(p1.x,p1.y,center,p1.y,center,p2.y,p2.x,p2.y);
  }

  /////////////// initialObject
  @Override
  protected void initializeObject() {
    CLASSNAME = "NodeLine";
    idAddable = false;
  }

  ///////////////
  public NodeLine(NodePoint outpoint, NodePoint inpoint) {
    super();
    inP = inpoint;
    outP = outpoint;
  }
  
  public void activate(boolean flag){
     activated = flag;
  }

  public NodePoint getInPoint() {
    return inP;
  }

  public NodePoint getOutPoint() {
    return outP;
  }

  @Override
  public NodeLine clone() {
    return new NodeLine(this.outP, this.inP);
  }

  public void delete() {
    if (inTheSameBox(inP.getMaster(), outP.getMaster()) && (inP.getMaster().getBoxMaster() != null)) {
      inP.getMaster().getBoxMaster().removeLine(this);
    } else if (theyAreboxAndInnerNode(inP.getMaster(), outP.getMaster())) {
      if (inP.getMaster().getBoxMaster() != null) {
        inP.getMaster().getBoxMaster().removeLine(this);
      }else{
        outP.getMaster().getBoxMaster().removeLine(this);
      }
    }
    inP.removeLine(this);
    outP.removeLine(this);
    inP = null;
    outP = null;

  }
}