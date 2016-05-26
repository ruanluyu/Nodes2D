
  void cleanArrayNull(ArrayList<?> list) {
    for (Object obj : list) {
      if (obj == null) {
        list.remove(obj);
      }
    }
  }

class NodesSystem extends NObject {
  private ArrayList<Node> nodeList = new ArrayList<Node>();
  private ArrayList<NodeLine> lineList = new ArrayList<NodeLine>();
  private ArrayList<NStream> streamList = new ArrayList<NStream>();

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
  private void masterCheck(Node node) {
    if (nodeList.indexOf(node) < 0) {
      nodeList.add(node);
      node.setMaster(this);
    }
  }

  public void addNode(Node node) {
    masterCheck(node);
  }

  public void addStream(NStream ns) {
    streamList.add(ns);
  }

  public void setMaxStep(int maxStep) {
    this.maxStep = maxStep;
  }

  // Node control:
  public int getStep() {
    return step;
  }

  public void removeNode(Node node) {
    nodeList.remove(node);
  }

  public void removeAllNode() {
    nodeList.clear();
  }

  public int getNodeNum() {
    return nodeList.size();
  }

  public int getLineNum() {
    return lineList.size();
  }
  

   public NodeLine connect(NodePoint outpoint, NodePoint inpoint) {
    masterCheck(outpoint.getMaster()); 
    masterCheck(inpoint.getMaster()); 
    NodeLine out = null;
    if (!theyAreboxAndInnerNode(outpoint.getMaster(), inpoint.getMaster())) {

      if (outpoint.isInput()) {
        NodePoint cur = outpoint;
        outpoint = inpoint;
        inpoint = cur;
      } 

      out = connectNormal(outpoint, inpoint);

    } else {
      if (inpoint.getMaster().getBoxMaster()==outpoint.getMaster()) {
        NodePoint curNp = outpoint;
        outpoint = inpoint;
        inpoint = curNp;
      }

      if (inpoint.isInput()) {
        out = connectInBox(inpoint, outpoint);
      } else if (inpoint.isOutput()) {
        out = connectOutBox(outpoint, inpoint);
      }
    }
    lineList.add(out);
    return out;
  }

  private NodeLine connectNormal(NodePoint outpoint, NodePoint inpoint) {

    if (!inTheSameBox(outpoint.getMaster(), inpoint.getMaster())) {
      println("Warning : connection between " + outpoint.getClassName() + " -and-" + inpoint.getClassName()
          + " failed.");
      return null;
    } // 检测是否在同一个作用域

    if ((outpoint.isInput() && inpoint.isInput()) || (outpoint.isOutput() && inpoint.isOutput())) {
      try {
        throw new NodeException(0, outpoint.getMaster().getTitle() + " : out " + outpoint.getTitle() + " and "
            + inpoint.getMaster().getTitle() + " : in " + inpoint.getTitle() + " .");
      } catch (NodeException e) {
        e.println();
      }
      return null;
    } // 检测输入输出点匹配问题

    if (!connectable(outpoint, inpoint)) {
      try {
        throw new NodeException(6, outpoint.getMaster().getTitle() + " : out " + outpoint.getTitle() + " and "
            + inpoint.getMaster().getTitle() + " : in " + inpoint.getTitle() + " .");
      } catch (NodeException e) {
        e.println();
      }
      return null;
    } // 检测点的数据类型是否匹配

    if ((inpoint.getNumOfLines() > 0)) {
      println("Warning:You override an input point when it connected.Occured on NodePoint " + inpoint.getTitle()
          + "(" + inpoint.getMaster().getTitle() + ")");
      disconnect(inpoint.getLine());
    } // 检测inpoint是否已存在连线，若存在，则断开存在的线并发出警告。

    NodeLine out = new NodeLine(outpoint, inpoint);
    outpoint.addLine(out);
    inpoint.addLine(out);
    if (outpoint.getMaster().getBoxMaster() != null) {
      outpoint.getMaster().getBoxMaster().addLine(out);
    }
    println(outpoint.getMaster().getTitle() + "(in " + outpoint.getTitle() + ") ----- "
        + inpoint.getMaster().getTitle() + "(out " + inpoint.getTitle() + ") connected");

    return out;
  }

  // 将线从NBox外部inpoint链接到NBox内部inpoint 的情况
  private NodeLine connectInBox(NodePoint boxpoint, NodePoint innerpoint) {

    if ((innerpoint.getNumOfLines() > 0)) {
      println("Warning:You overrided an input point when it connected.Occured on NodePoint "
          + innerpoint.getTitle() + "(" + innerpoint.getMaster().getTitle() + ")");
      disconnect(innerpoint.getLine());
    } // 检测innerpoint是否已存在连线，若存在，则断开存在的线并发出警告。

    NodeLine out = new NodeLine(boxpoint, innerpoint);
    boxpoint.addLine(out);
    innerpoint.addLine(out);
    innerpoint.getMaster().getBoxMaster().addLine(out);
    println(boxpoint.getMaster().getTitle() + "(in " + boxpoint.getTitle() + ") ----- "
        + innerpoint.getMaster().getTitle() + "(out " + innerpoint.getTitle() + ") connected");

    return out;
  }

  // 将线从NBox内部outpoint链接到NBox外部outpoint 的情况
  private NodeLine connectOutBox(NodePoint innerpoint, NodePoint boxpoint) {
    if ((boxpoint.getNumOfLines(true) > 0)) {
      println("Warning:You overrided an input point when it connected.Occured on NodePoint " + boxpoint.getTitle()
          + "(" + boxpoint.getMaster().getTitle() + ")");
      disconnect(boxpoint.getLine());
    } // 检测inpoint是否已存在连线，若存在，则断开存在的线并发出警告。

    NodeLine out = new NodeLine(innerpoint, boxpoint);
    innerpoint.addLine(out);
    boxpoint.addLine(out);
    innerpoint.getMaster().getBoxMaster().addLine(out);
    println(innerpoint.getMaster().getTitle() + "(in " + innerpoint.getTitle() + ") ----- "
        + boxpoint.getMaster().getTitle() + "(out " + boxpoint.getTitle() + ") connected");

    return out;
  }

  public void disconnect(NodeLine line) {
    println(line.getOutPoint().getMaster().getTitle() + "(in " + line.getOutPoint().getTitle() + ") - / - "
        + line.getInPoint().getMaster().getTitle() + "(out " + line.getInPoint().getTitle() + ") disconnected");
    line.delete();
    lineList.remove(line);

  }

 

  //////////////////
  ///// RUN//////
  ////////////////

  private void cleanStopStream() {
    cleanArrayNull(streamList);
    for (int i = streamList.size()-1;i>=0;i--) {
      NStream ns = streamList.get(i);
      if (ns == null)
        continue;
      if (ns.isStop()) {
        ns.delete();
        streamList.remove(i);
      }
    }
  }

  private void cleanAllStream() {
    for (NStream ns : streamList) {
      ns.delete();
    }
    streamList.clear();
  }
  
  private void resetAllLinesActivate(){
    for(NodeLine nl:lineList){
      nl.activate(false);
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
    for (Node nd : nodeList) {
      nd.generateStream();
      if (nd.getOutPointNum() <= 0) {
        continue;
      }
      if (nd instanceof NodeGenerator) {
        NodeGenerator ng = (NodeGenerator) nd;
        if (!ng.generatable()) {
          continue;
        }
        boolean generatorGenerated = false;
        for (int i = 0; i < nd.getOutPointNum(); i++) {
          NodePoint np = nd.getOutpoint(i);
          if (!np.hasStream())
            continue;
          generatorGenerated = true;
          for (int j = 0; j < np.getNumOfStream(); j++) {
            streamList.add(np.getStream(j).clone());
          }
        }
        if ((nd instanceof NodeGenerator) && generatorGenerated)
          ((NodeGenerator) nd).generated();
        continue;
      }

      for (int i = 0; i < nd.getOutPointNum(); i++) {
        NodePoint np = nd.getOutpoint(i);
        if (np.getNumOfStream() <= 0)
          continue;
        for (int j = 0; j < np.getNumOfStream(); j++) {
          streamList.add(np.getStream(j));
        }
        np.cleanStream();
      }
    }
    cleanArrayNull(streamList);
  }

  private boolean transportStream() {
    boolean transported = false;
    int pt = 0;
    while (pt < streamList.size()) {
      NStream ns = streamList.get(pt++);
      if (ns.isPause() || ns.isStop()) {
        continue;
      }
      ns.goToInpoint();
      ns.pause();
      transported = true;
    }

    for (NStream ns : streamList) {
      if (ns.isPause() || ns.isStop())
        continue;
      ns.goToInpoint();
      ns.pause();
      transported = true;
    }
    return transported;
  }

  private boolean generatorCheck() {
    for (Node nd : nodeList) {
      if (nd instanceof NodeGenerator) {
        if (((NodeGenerator) nd).getTimes() > 0) {
          return true;
        }
        if (((NodeGenerator) nd).isGenerating()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean delayerCheck() {
    for (Node nd : nodeList) {
      if (nd instanceof NodeDelayer) {
        if (((NodeDelayer) nd).hasStream()) {
          return true;
        }
      }
    }
    return false;
  }

  private void setStreamToInpoint() {
    for (NStream ns : streamList) {
      if ((!(ns.isStop())) && (!(ns.isPause()))) {
        NodePoint cur = ns.getPoint();
        if (cur.isInput()) {
          cur.cleanStream();
          cur.addStream(ns);
          ns.pause();
        }
      }
    }
  }
  
  public void render(){
    for(Node nd:nodeList){
      nd.render();
    }
    for(NodeLine nl:lineList){
      nl.render();
    }
  }

  private void oneComputeStep() {
    generateStream();
    while (transportStream()) {
      setStreamToInpoint();
      cleanStopStream();
      generateStream();
    }
  }
  
  void startLoop(){
    println("************************\n RUN.\n\n************************");
    generateStream();
  }
  
  boolean loopCanRun(){
    return ((delayerCheck() || generatorCheck() || (streamList.size() > 0)) && stepBelowMaxStep());
  }
  
  void oneLoop(){
      oneComputeStep();
      cleanAllStream();
      resetAllLinesActivate();
      step++;
      
      println("************************\n Step " + step + " : Completed.\n\n************************");
      if (sleepTime > 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
  }
  void loopOver(){
    println("************************\n ALL DONE.\n\n************************");
  }

  public void run() {
    println("************************\n RUN.\n\n************************");

    generateStream();
    while ((delayerCheck() || generatorCheck() || (streamList.size() > 0)) && stepBelowMaxStep()) {

      oneComputeStep();
      cleanAllStream();
      resetAllLinesActivate();
      step++;

      println("************************\n Step " + step + " : Completed.\n\n************************");
      if (sleepTime > 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    println("************************\n ALL DONE.\n\n************************");
  }
}