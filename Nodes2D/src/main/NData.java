package main;

import java.util.ArrayList;
import java.util.List;

public class NData {
	public static final int DEFAULT_LENGTH = 8;
	List<Boolean> list = new ArrayList<Boolean>();
	NData(){
		this(DEFAULT_LENGTH);
	}
	NData(int length){
		this.length = length;
		for(int i = 0;i<length;i++){
			list.add(false);
		}
	}
	int length = 0;
	int getLength(){
		return length;
	}
}

class NDouble extends NData{
	
}

class NInt extends NData{
	
}

class 