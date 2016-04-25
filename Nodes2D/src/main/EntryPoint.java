package main;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		this.setBounds(300, 300, 500, 500);
		this.setTitle(title);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		System.out.println("EntryPoint begin");
		// JFrame nodeWindow = new EntryPoint("Nodes2D","../icon/Icon_L.png");

		NodesSystem nst = new NodesSystem();//建立节点系统
		Node_SolidNumber n1 = new Node_SolidNumber(200);//建立常数节点200
		Node_SolidNumber n2 = new Node_SolidNumber(580);//建立常数节点580
		Node_SolidNumber n3 = new Node_SolidNumber(155);//建立常数节点155
		Node_Pluser p1 = new Node_Pluser(3);//建立三个输入点的加法计算器
		Node_Printer np = new Node_Printer();//建立打印输出节点
		nst.addNode(n1);//将节点加入节点系统
		nst.addNode(n2);//将节点加入节点系统
		nst.addNode(n3);//将节点加入节点系统
		nst.addNode(p1);//将节点加入节点系统
		nst.addNode(np);//将节点加入节点系统
		nst.connect(n1.getOutpoint(0), p1.getInpoint(0));//链接常数和加法节点
		nst.connect(n2.getOutpoint(0), p1.getInpoint(1));//链接常数和加法节点
		nst.connect(n3.getOutpoint(0), p1.getInpoint(2));//链接常数和加法节点
		nst.connect(p1.getOutpoint(0), np.getInpoint(0));//链接加法节点和输出
		nst.run();//节点系统启动
	}

}
