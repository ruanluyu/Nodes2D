void setup(){
  size(1280,720);
  frameRate(60);
  test2();
}

void draw(){
  
}

  void test2() {
    NodesSystem nst = new NodesSystem();// 建立节点系统
    NBox nb = new NBox(1, 1);// 建立盒子

    Node_SolidNumber n1 = new Node_SolidNumber(200);// 建立常数节点200
    Node_SolidNumber n2 = new Node_SolidNumber(6);// 建立常数节点6

    Node_SolidNumber n3 = new Node_SolidNumber(5);// 建立常数节点5
    Node_SolidNumber n4 = new Node_SolidNumber(3);// 建立常数节点3

    Node_Cutter p1 = new Node_Cutter();// 建立默认减法计算器
    Node_Multiplyer p2 = new Node_Multiplyer();// 建立默认乘法计算器
    Node_Printer np = new Node_Printer();// 建立输出
    Node_Printer np2 = new Node_Printer();// 建立输出2

    nst.addNode(nb);
    nst.addNode(np);
    nst.addNode(np2);
    nst.addNode(n3);
    nst.addNode(n4);
    nb.addNode(p1);
    nb.addNode(p2);
    nb.addNode(n1);
    nb.addNode(n2);
    nst.connect(nb.getInpoint(0), p1.getInpoint(1));// 链接盒子输入点和减法计算器
    nst.connect(n1.getOutpoint(0), p1.getInpoint(0));// 链接常数和减法计算器
    nst.connect(p1.getOutpoint(0), p2.getInpoint(0));// 链接减法计算器和乘法计算器
    nst.connect(n2.getOutpoint(0), p2.getInpoint(1));// 链接常数和乘法计算器
    nst.connect(p2.getOutpoint(0), nb.getOutpoint(0));// 链接乘法计算器和盒子输出点
    NBox nbi = nb.clone();
    nb.addNode(nbi);
    nst.connect(p2.getOutpoint(0), nbi.getInpoint(0));// 链接乘法计算器和盒子输入点
    nst.connect(nbi.getOutpoint(0), nb.getOutpoint(0));// 链接盒子输出点和盒子输出点
    NBox nb2 = nb.clone();
    nst.connect(n3.getOutpoint(0), nb.getInpoint(0));
    nst.connect(n4.getOutpoint(0), nb2.getInpoint(0));
    nst.connect(nb.getOutpoint(0), np.getInpoint(0));
    nst.connect(nb2.getOutpoint(0), np2.getInpoint(0));
    nst.run();

  }