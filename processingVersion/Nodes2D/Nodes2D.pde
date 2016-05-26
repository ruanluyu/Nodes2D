NodesSystem nst;
boolean globalFlag = true;
boolean setup = false;
void setup(){
  
  size(1280,720);
  
  
}

void draw(){
  if(setup == false){
    nst = new NodesSystem();// 建立节点系统
    Node_SolidNumber n1 = new Node_SolidNumber(200);// 建立常数节点200
    Node_SolidNumber n2 = new Node_SolidNumber(580);// 建立常数节点580
    Node_SolidNumber n3 = new Node_SolidNumber(155);// 建立常数节点155
    Node_SolidNumber n4 = new Node_SolidNumber(176);// 建立常数节点5
    n4.setGenerating(true);
    Node_Pluser p1 = new Node_Pluser(3);// 建立三个输入点的加法计算器
    Node_Pluser p2 = new Node_Pluser();// 建立默认加法计算器
    Node_Printer np = new Node_Printer();// 建立打印输出节点
    NodeDelayer nd = new NodeDelayer();// 创建延时器
    Node_NDataToElse ntl = new Node_NDataToElse();// 创建NData适配器1
    Node_NDataToElse ntl2 = new Node_NDataToElse();// 创建NData适配器2
    Node_StreamMerge ns = new Node_StreamMerge(2);// 创建流合成器

    nst.addNode(n1);// 将节点加入节点系统
    nst.addNode(n2);// 将节点加入节点系统
    nst.addNode(n3);// 将节点加入节点系统
    nst.addNode(n4);// 将节点加入节点系统
    nst.addNode(p1);// 将节点加入节点系统
    nst.addNode(p2);// 将节点加入节点系统
    nst.addNode(np);// 将节点加入节点系统
    nst.addNode(nd);// 将节点加入节点系统
    nst.addNode(ntl);// 将节点加入节点系统
    nst.addNode(ntl2);// 将节点加入节点系统
    nst.addNode(ns);// 将节点加入节点系统

    nst.connect(n1.getOutpoint(0), p1.getInpoint(0));// 链接常数1和加法节点1
    nst.connect(n2.getOutpoint(0), p1.getInpoint(1));// 链接常数2和加法节点1
    nst.connect(n3.getOutpoint(0), p1.getInpoint(2));// 链接常数3和加法节点1
    nst.connect(n4.getOutpoint(0), p2.getInpoint(0));// 链接常数4和加法节点2
    nst.connect(p1.getOutpoint(0), ns.getInpoint(0));// 链接加法节点和流合成器
    nst.connect(ns.getOutpoint(0), nd.getInpoint(0));// 链接流合成器和延时器
    nst.connect(ns.getOutpoint(0), ntl.getInpoint(0));// 链接流合成器和适配器
    nst.connect(nd.getOutpoint(0), ntl2.getInpoint(0));// 链接延时器和适配器2
    nst.connect(ntl2.getOutpoint(0), p2.getInpoint(1));// 链接适配器2和加法节点2
    nst.connect(p2.getOutpoint(0), ns.getInpoint(1));// 链接加法节点2和流合成器
    nst.connect(ntl.getOutpoint(0), np.getInpoint(0));// 链接适配器和输出

    nst.startLoop();
  setup = true;
  }
  
  if(globalFlag){
    if(nst.loopCanRun()){
      nst.oneLoop();
    }else{
      nst.loopOver();
      globalFlag = false;
    }
  }
  nst.render();
}

  