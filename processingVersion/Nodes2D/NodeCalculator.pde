
class NodeCalculator extends Node {
  /////////////// initialObject
  @Override
  protected void initializeObject() {
    CLASSNAME = "NodeCalculator";
    idAddable = true;
  }

  @Override
  public NodeCalculator clone() {
    return null;
  }
  ///////////////
}

class Node_Pluser extends NodeCalculator {

  @Override
  public Node_Pluser clone() {
    Node_Pluser cloned = new Node_Pluser();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Pluser";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = true;
    outPointAddable = false;
  }

  public Node_Pluser() {
    this(2);
  }

  public Node_Pluser(int numOfIn) {
    Build(numOfIn);
  }
  ///////////////

  public void Build(int numOfIn) {
    if (numOfIn >= 2) {
      addInPoint(numOfIn, this, NData.NDOUBLE);
      addOutPoint(1, this, NData.NDOUBLE);
    } else {
      println("Error : failed in building " + getTitle());
    }
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = 0;
    for (NodePoint np : inPointList) {
      if (!np.hasStream())
        continue;
      output += np.getStream().getData().getDoubleData();
    }
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Cutter extends NodeCalculator {

  @Override
  public Node_Cutter clone() {
    Node_Cutter cloned = new Node_Cutter();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Cutter";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = true;
    outPointAddable = false;
  }

  public Node_Cutter() {
    this(2);
  }

  public Node_Cutter(int numOfIn) {
    Build(numOfIn);
  }
  ///////////////

  public void Build(int numOfIn) {
    if (numOfIn >= 2) {
      addInPoint(numOfIn, this, NData.NDOUBLE);
      addOutPoint(1, this, NData.NDOUBLE);
    } else {
      println("Error : failed in building " + getTitle());
    }
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = inPointList.get(0).getStream().getData().getDoubleData() * 2;
    for (NodePoint np : inPointList) {
      if (!np.hasStream())
        continue;
      output -= np.getStream().getData().getDoubleData();
    }
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Multiplyer extends NodeCalculator {

  @Override
  public Node_Multiplyer clone() {
    Node_Multiplyer cloned = new Node_Multiplyer();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Multiplyer";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = true;
    outPointAddable = false;
  }

  public Node_Multiplyer() {
    this(2);
  }

  public Node_Multiplyer(int numOfIn) {
    Build(numOfIn);
  }
  ///////////////

  public void Build(int numOfIn) {
    if (numOfIn >= 2) {
      addInPoint(numOfIn, this, NData.NDOUBLE);
      addOutPoint(1, this, NData.NDOUBLE);
    } else {
      println("Error : failed in building " + getTitle());
    }
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = inPointList.get(0).getStream().getData().getDoubleData();
    boolean firstSkip = true;
    for (NodePoint np : inPointList) {
      if (firstSkip) {
        firstSkip = false;
        continue;
      }
      if (!np.hasStream())
        continue;
      output *= np.getStream().getData().getDoubleData();
    }
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Divider extends NodeCalculator {

  @Override
  public Node_Divider clone() {
    Node_Divider cloned = new Node_Divider();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Divider";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = true;
    outPointAddable = false;
  }

  public Node_Divider() {
    this(2);
  }

  public Node_Divider(int numOfIn) {
    Build(numOfIn);
  }
  ///////////////

  public void Build(int numOfIn) {
    if (numOfIn >= 2) {
      addInPoint(numOfIn, this, NData.NDOUBLE);
      addOutPoint(1, this, NData.NDOUBLE);
    } else {
      println("Error : failed in building " + getTitle());
    }
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = inPointList.get(0).getStream().getData().getDoubleData();
    boolean firstSkip = true;
    for (NodePoint np : inPointList) {
      if (firstSkip) {
        firstSkip = false;
        continue;
      }
      if (!np.hasStream())
        continue;
      output /= np.getStream().getData().getDoubleData();
    }
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Complementation extends NodeCalculator {

  @Override
  public Node_Complementation clone() {
    Node_Complementation cloned = new Node_Complementation();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Complementation";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_Complementation() {
    addInPoint(2, this, NData.NINT);
    addOutPoint(1, this, NData.NINT);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    int output = inPointList.get(0).getStream().getData().getIntData()
        % inPointList.get(1).getStream().getData().getIntData();
    cleanInPoint();
    addStreamToOutpoint(0, new NInt(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Pow extends NodeCalculator {

  @Override
  public Node_Pow clone() {
    Node_Pow cloned = new Node_Pow();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Pow";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_Pow() {
    addInPoint(2, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = Math.pow(inPointList.get(0).getStream().getData().getDoubleData(),
        inPointList.get(1).getStream().getData().getDoubleData());
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Sin extends NodeCalculator {

  @Override
  public Node_Sin clone() {
    Node_Sin cloned = new Node_Sin();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Sin";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_Sin() {
    addInPoint(1, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = Math.sin(inPointList.get(0).getStream().getData().getDoubleData());
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Cos extends NodeCalculator {

  @Override
  public Node_Cos clone() {
    Node_Cos cloned = new Node_Cos();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Cos";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_Cos() {
    addInPoint(1, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = Math.cos(inPointList.get(0).getStream().getData().getDoubleData());
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Tan extends NodeCalculator {

  @Override
  public Node_Tan clone() {
    Node_Tan cloned = new Node_Tan();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Tan";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_Tan() {
    addInPoint(1, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double output = Math.tan(inPointList.get(0).getStream().getData().getDoubleData());
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_Log extends NodeCalculator {

  @Override
  public Node_Log clone() {
    Node_Log cloned = new Node_Log();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_Log";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_Log() {
    addInPoint(2, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }
    double in0 = inPointList.get(0).getStream().getData().getDoubleData();
    double in1 = inPointList.get(1).getStream().getData().getDoubleData();
    if (in0 <= 0 || in0 == 1 || in1 <= 0) {
      cleanInPoint();
      return;
    }

    double output = Math.log(in1) / Math.log(in0);
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_DegreesToRadians extends NodeCalculator {

  @Override
  public Node_DegreesToRadians clone() {
    Node_DegreesToRadians cloned = new Node_DegreesToRadians();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_DegreesToRadians";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_DegreesToRadians() {
    addInPoint(1, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }

    double output = Math.toRadians(inPointList.get(0).getStream().getData().getDoubleData());
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

class Node_RadiansToDegrees extends NodeCalculator {

  @Override
  public Node_RadiansToDegrees clone() {
    Node_RadiansToDegrees cloned = new Node_RadiansToDegrees();
    clone(this, cloned);
    return cloned;
  }

  /////////////// initialObject

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_RadiansToDegrees";
    idAddable = true;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  public Node_RadiansToDegrees() {
    addInPoint(1, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NDOUBLE);
  }

  //////////// RUN
  @Override
  public void generateStream() {
    if (!inputIsAlready()) {
      return;
    }

    double output = Math.toDegrees(inPointList.get(0).getStream().getData().getDoubleData());
    cleanInPoint();
    addStreamToOutpoint(0, new NDouble(output));
    if (!stillCompute) {
      computeTimes--;
    }
  }
}

/**
 * in0: NData<BR/>
 * <BR/>
 * 
 * out0: NDouble<BR/>
 * out1: NInt<BR/>
 * out2: NBoolean<BR/>
 * out3: NString<BR/>
 * 
 * @author ZzStarSound
 *
 */
class Node_NDataToElse extends NodeCalculator {

  @Override
  public Node_NDataToElse clone() {
    Node_NDataToElse cloned = new Node_NDataToElse();
    clone(this, cloned);
    return cloned;
  }

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = false;
    outPointAddable = false;
  }

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_NDataToElse";
    idAddable = true;
  }

  public Node_NDataToElse() {
    addInPoint(1, this, NData.NDATA);

    addOutPoint(1, this, NData.NDOUBLE);
    addOutPoint(1, this, NData.NINT);
    addOutPoint(1, this, NData.NBOOLEAN);
    addOutPoint(1, this, NData.NSTRING);
  }

  @Override
  public void generateStream() {
    if (!inputIsAlready())
      return;

    NStream curStream = inPointList.get(0).getStream();
    addStreamToOutpoint(0, new NDouble(curStream.getData().getDoubleData()));
    addStreamToOutpoint(1, new NInt(curStream.getData().getIntData()));
    addStreamToOutpoint(2, new NBoolean(curStream.getData().getBooleanData()));
    addStreamToOutpoint(3, new NString(curStream.getData().getStringData()));
    cleanInPoint();
  }

}

class Node_StreamMerge extends NodeCalculator {

  @Override
  public Node_StreamMerge clone() {
    Node_StreamMerge cloned = new Node_StreamMerge();
    clone(this, cloned);
    return cloned;
  }

  protected int lastStep = 0;
  boolean generated = false;

  @Override
  protected void initializeNode() {
    stillCompute = true;
    computeTimes = 0;
    inPointAddable = true;
    outPointAddable = false;
  }

  @Override
  protected void initializeObject() {
    CLASSNAME = "Node_StreamMerge";
    idAddable = true;
  }

  public Node_StreamMerge() {
    addInPoint(2, this, NData.NDATA);
    addOutPoint(1, this, NData.NDATA);
  }

  public Node_StreamMerge(int numOfInput) {
    addInPoint(numOfInput, this, NData.NDATA);
    addOutPoint(1, this, NData.NDATA);
  }

  public void generateStream() {
    if (master.getStep() != lastStep) {
      lastStep = master.getStep();
      generated = false;
    }
    if (generated)
      return;

    inputCheck();
    cleanInPoint();
  }

  public void inputCheck() {

    for (NodePoint np : inPointList) {
      if (np.hasStream()) {
        generated = true;
        addStreamToOutpoint(0, np.getStream());
        break;
      }
    }

  }

}