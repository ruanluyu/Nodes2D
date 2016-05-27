
  void cleanArrayNull(ArrayList<?> list) {
    for (Object obj : list) {
      if (obj == null) {
        list.remove(obj);
      }
    }
  }

  boolean pointInsideBox(PVector point,PVector upLeft,PVector downRight){
    boolean flag = false;
    if((point.x>=upLeft.x&&point.x<=downRight.x)&&(point.y>=upLeft.y&&point.y<=downRight.y)){
      flag = true;
    }
    return flag;
  }
  
  boolean boxInterSection(PVector ALU,PVector ARD,PVector BLU,PVector BRD){
    float a1,a2,a3,a4,b1,b2,b3,b4;
    
    a1 = ALU.x;
    a2 = ALU.y;
    a3 = ARD.x;
    a4 = ARD.y;
    b1 = BLU.x;
    b2 = BLU.y;
    b3 = BRD.x;
    b4 = BRD.y;
    if(a1>a3){
      float cur = a1;
      a1 = a3;
      a3 = cur;
    }
    
    if(a2>a4){
      float cur = a2;
      a2 = a4;
      a4 = cur;
    }
    if(b1>b3){
      float cur = b1;
      b1 = b3;
      b3 = cur;
    }
    
    if(b2>b4){
      float cur = b2;
      b2 = b4;
      b4 = cur;
    }
    
    boolean flag = true;
    if((a3<b1)||(a1>b3)||(a4<b2)||(a2>b4)){
      flag = false;
    }
    return flag;
  }

class NodesSystem extends NObject {
  private ArrayList<Node> nodeList = new ArrayList<Node>();
  private ArrayList<NodeLine> lineList = new ArrayList<NodeLine>();
  private ArrayList<NStream> streamList = new ArrayList<NStream>();

  private int sleepTime = 100;
  private int step = 0;
  private int maxStep = 20;
  private ArrayList<Node> activatedNode = new ArrayList<Node>();
  boolean mouseDown = false;
  boolean clickBit = false;
  boolean chooseSq = false;
  PVector chooseSqStartPoint = new PVector(0,0);
  PVector moveStartPoint = new PVector(0,0);
  void itemMove(){
    if(mouseDown&&(!clickBit)){
      for(Node nd:activatedNode){
        nd.position.set(nd.position.x+mouseX-moveStartPoint.x,nd.position.y+mouseY-moveStartPoint.y);
      }
      moveStartPoint.set(mouseX,mouseY);
    }
  }
  
  void itemChoose(){
    if(clickBit){
      chooseSq = true;
      Node cur = null;
      for(Node nd : nodeList){
        if(pointInsideBox(new PVector(mouseX,mouseY),nd.position,new PVector(nd.position.x+nd.size.x,nd.position.y+nd.size.y))){
          cur = nd;
          break;
        }
      }
      
      if(cur != null){
        chooseSq = false;
        if(activatedNode.size()>0){
          if(activatedNode.indexOf(cur)<0){
            for(Node nd2:activatedNode){
              nd2.activated = false;
            }
            activatedNode.clear();
            cur.activated = true;
            activatedNode.add(cur);
          }
        }else{
          activatedNode.add(cur);
          cur.activated = true;
        }
      }else{
        chooseSq = true;
        for(Node nd2:activatedNode){
              nd2.activated = false;
            }
        activatedNode.clear();
      }
      chooseSqStartPoint.set(mouseX,mouseY);
      moveStartPoint.set(mouseX,mouseY);
      clickBit = false;
    }
  }
  void renderChooseSq(){
    if(chooseSq){
      stroke(235,235,23,200);
      fill(155,142,23,25);
      rect(chooseSqStartPoint.x,chooseSqStartPoint.y,mouseX-chooseSqStartPoint.x,mouseY-chooseSqStartPoint.y);
    }
  }
  
  void mousePress(){
    mouseDown = true;
    clickBit = true;
  }
  void mouseRelease(){
    mouseDown = false;
    if(chooseSq){
      activatedNode.clear();
      for(Node nd:nodeList){
        nd.activated = false;
        if(boxInterSection(chooseSqStartPoint,new PVector(mouseX,mouseY),nd.position,new PVector(nd.position.x+nd.size.x,nd.position.y+nd.size.y))){
          activatedNode.add(nd);
          nd.activated = true;
        }
      }
      chooseSq = false;
    }
  }
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
    nodeList.add(node);
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
      /*println("Warning : connection between " + outpoint.getClassName() + " -and-" + inpoint.getClassName()
          + " failed.");*/
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
      /*println("Warning:You override an input point when it connected.Occured on NodePoint " + inpoint.getTitle()
          + "(" + inpoint.getMaster().getTitle() + ")");*/
      disconnect(inpoint.getLine());
    } // 检测inpoint是否已存在连线，若存在，则断开存在的线并发出警告。

    NodeLine out = new NodeLine(outpoint, inpoint);
    outpoint.addLine(out);
    inpoint.addLine(out);
    if (outpoint.getMaster().getBoxMaster() != null) {
      outpoint.getMaster().getBoxMaster().addLine(out);
    }
    /*println(outpoint.getMaster().getTitle() + "(in " + outpoint.getTitle() + ") ----- "
        + inpoint.getMaster().getTitle() + "(out " + inpoint.getTitle() + ") connected");*/

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
    /*println(boxpoint.getMaster().getTitle() + "(in " + boxpoint.getTitle() + ") ----- "
        + innerpoint.getMaster().getTitle() + "(out " + innerpoint.getTitle() + ") connected");*/

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
    /*println(innerpoint.getMaster().getTitle() + "(in " + innerpoint.getTitle() + ") ----- "
        + boxpoint.getMaster().getTitle() + "(out " + boxpoint.getTitle() + ") connected");*/

    return out;
  }

  public void disconnect(NodeLine line) {
   /* println(line.getOutPoint().getMaster().getTitle() + "(in " + line.getOutPoint().getTitle() + ") - / - "
        + line.getInPoint().getMaster().getTitle() + "(out " + line.getInPoint().getTitle() + ") disconnected");*/
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
    itemChoose();
    itemMove();
    for(int i = nodeList.size()-1;i>=0;i--){
      nodeList.get(i).render();
    }
    for(NodeLine nl:lineList){
      nl.render();
    }
    renderChooseSq();
    resetAllLinesActivate();
    
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