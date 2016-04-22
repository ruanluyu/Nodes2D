package main;

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
		this.setBounds(300, 300, 500, 500);
		this.setTitle(title);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		System.out.println("EntryPoint begin");
		// JFrame nodeWindow = new EntryPoint("Nodes2D","../icon/Icon_L.png");
		
		NodesSystem nst = new NodesSystem();
		Node_SolidNumber n1 = new Node_SolidNumber(1);
		Node_SolidNumber n2 = new Node_SolidNumber(2);
		Node_Pluser p1 = new Node_Pluser();
		Node_Printer np = new Node_Printer();
		nst.addNode(n1);
		nst.addNode(n2);
		nst.addNode(p1);
		nst.addNode(np);
		nst.connect(n1.getOutpoint(0), p1.getInpoint(0));
		nst.connect(n2.getOutpoint(0), p1.getInpoint(1));
		nst.connect(p1.getOutpoint(0), np.getInpoint(0));
		nst.run();
		
		System.out.println("All done");
	}

}
