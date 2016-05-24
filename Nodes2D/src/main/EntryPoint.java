package main;

import java.awt.Graphics;

import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class EntryPoint extends JFrame {
	private static final long serialVersionUID = 1L;

	public EntryPoint(String title, String path) {
		try {
			Image img = ImageIO.read(this.getClass().getResource(path));
			this.setIconImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1280, 720);
		this.setTitle(title);
		this.setVisible(true);

	}

	public static void test1() {

		NodesSystem nst = new NodesSystem();// 建立节点系统
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

		nst.run();// 节点系统启动
	}

	public static void test2() {
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
		NBox nb2 = nb.clone();
		nst.connect(n3.getOutpoint(0), nb.getInpoint(0));
		nst.connect(n4.getOutpoint(0), nb2.getInpoint(0));
		nst.connect(nb.getOutpoint(0), np.getInpoint(0));
		nst.connect(nb2.getOutpoint(0), np2.getInpoint(0));
		nst.run();

	}

	public static void main(String[] args) {
		System.out.println("EntryPoint begin");
		// JFrame nodeWindow = new EntryPoint("Nodes2D", "../icon/Icon_T.png");
		test2();
		// test1();
	}

}
