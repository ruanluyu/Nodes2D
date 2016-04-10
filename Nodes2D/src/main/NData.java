package main;


class NData extends NObject {
	protected static int id = 0;
	private int thisId = 0;

	NData() {
		thisId = id;
		id++;
	}
}

class NDouble extends NData {
	private static final String CLASSNAME = "Ndouble";
	private double data = 0;

	public void set(double value) {
		data = value;
	}

	public double get() {
		return data;
	}
}

class NInt extends NData {
	private static final String CLASSNAME = "NInt";
	private int data = 0;

	public void set(int value) {
		data = value;
	}

	public int get() {
		return data;
	}
}

class NString extends NData {
	private static final String CLASSNAME = "NString";
	private String data = "";

	public void set(String value) {
		data = value;
	}

	public String get() {
		return data;
	}
}

class NVector extends NData{
	private static final String CLASSNAME = "NVector";
	NData data[];
	NVector(int num){
		data = new NData[num];
	}
	public void set(int id,NData data){
		this.data[id] = data;
	}
	public NData[] get(){
		return data;
	}
	public NData get(int id){
		return get()[id];
	}
}

/*
 * class NBinary extends NData implements NData { private static final int
 * DEFAULT_LENGTH = 16; private static final String CLASSNAME = "NBinary";
 * private int length = DEFAULT_LENGTH; List<Boolean> list = new
 * ArrayList<Boolean>();
 * 
 * NBinary() { this(DEFAULT_LENGTH); }
 * 
 * NBinary(int length) { super(); this.length = length; for (int i = 0; i <
 * length; i++) { list.add(false); } }
 * 
 * static void invert(NBinary data) throws NodeException { int length =
 * data.getLength(); boolean cur[] = new boolean[length]; for (int i = 0; i <
 * length; i++) { cur[i] = data.get(i); } for (int i = 0; i < length; i++) {
 * data.set(i, cur[length - i - 1]); } }
 * 
 * public void set(int id, boolean bool) throws NodeException { try { if (id >=
 * length) throw new NodeException(2, CLASSNAME); } catch (NodeException e) {
 * e.println(); throw new NodeException(2, CLASSNAME); } list.set(id, bool); }
 * 
 * public boolean get(int id) throws NodeException { try { if (id >= length)
 * throw new NodeException(2, CLASSNAME); } catch (NodeException e) {
 * e.println(); throw new NodeException(2, CLASSNAME); } return list.get(id); }
 * 
 * protected static NData nPlus(NBinary data1, NBinary data2) throws
 * NodeException { int max = data1.getLength() > data2.getLength() ?
 * data1.getLength() : data2.getLength(); boolean out[] = new boolean[max]; for
 * (int i = 0; i < data1.getLength(); i++) { out[i] = data1.get(i); } for (int i
 * = 0; i < data2.getLength(); i++) { int pointer = i; if (out[i] = false) {
 * out[i] = data2.get(i); continue; } else { if (data2.get(i) == false) {
 * continue; } else { while (out[pointer]) { if (out[pointer]) { out[pointer] =
 * false; } else { out[pointer] = true; continue; } pointer++; if (pointer >=
 * max) throw new NodeException(4, CLASSNAME); } } } } NBinary outdata = new
 * NBinary(max); for (int i = 0; i < max; i++) { outdata.set(i, out[i]); }
 * return outdata; } }
 */