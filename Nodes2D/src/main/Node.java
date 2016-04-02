package main;

abstract class Node {
public static int id = 0;

}

class NodePoint{
	boolean input ,output;
	static int id = 0;
	private int thisId = 0;
	static final String defaultTitle = "Undefined";
	String title;
	NodePoint(){
		this(defaultTitle,true,true);
	}
	NodePoint(String title,boolean input,boolean output){
		try{
			if((!input)&&(!output)){
				thisId = id++;
				this.input = input;
				this.output = output;
				this.title = title;
			}else{
				throw new NodeException("Error:one of input or output should be true at least.This occured while building '"+title+"'(id:"+id+")");
			}
		}catch(NodeException error){
			System.out.println(error);
		}
		
		
	}
}