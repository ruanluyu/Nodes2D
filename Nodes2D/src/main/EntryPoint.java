package main;

import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class EntryPoint extends JFrame {
	private static final long serialVersionUID = 1L;
	public EntryPoint(String title,String path){
		try{
			Image img = ImageIO.read(this.getClass().getResource(path));
			this.setIconImage(img);
		}catch(IOException e){
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(300,300,500,500);
		this.setTitle(title); 
		this.setVisible(true);
		
	}
	public static void main(String[] args){
		System.out.println("EntryPoint begin");
		//JFrame nodeWindow = new EntryPoint("Nodes2D","../icon/Icon_L.png");
		System.out.println("All done");
		
	}
}