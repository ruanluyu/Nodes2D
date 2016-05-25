
class NodeLine extends NObject {
  private NodePoint inP, outP;
  private boolean activated = false;

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