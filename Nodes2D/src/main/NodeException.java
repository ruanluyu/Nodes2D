package main;

public class NodeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public NodeException(String msg){
		message = msg;
	}
	void print(){
		System.out.println(message);
	}
}
