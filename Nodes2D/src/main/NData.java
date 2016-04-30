package main;

import java.util.ArrayList;
import java.util.List;

class NData extends NObject {
	protected int TYPE = -1;
	public static final int NDATA = -1;
	public static final int NDOUBLE = 0;
	public static final int NINT = 1;
	public static final int NSTRING = 2;
	public static final int NBOOLEAN = 3;
	public static final int NVECTOR = 4;

	protected double doubleData = 0;
	protected int intData = 0;
	protected String stringData = "";
	protected boolean booleanData = false;

	public void convertType(int forcus) {
		switch (forcus) {
		case NDOUBLE:
			intData = Math.round((float) doubleData);
			if (doubleData != 0)
				booleanData = true;
			else
				booleanData = false;
			stringData = "" + doubleData;
			break;
		case NINT:
			doubleData = intData;
			if (intData != 0)
				booleanData = true;
			else
				booleanData = false;
			stringData = "" + intData;
			break;
		case NSTRING:
			if (stringData != "") {
				doubleData = Double.parseDouble(stringData);
				intData = Math.round((float) doubleData);
				if (intData != 0)
					booleanData = true;
				else
					booleanData = false;
			} else {
				doubleData = 0;
				intData = 0;
				booleanData = false;
			}
			break;
		case NBOOLEAN:
			if (booleanData) {
				doubleData = 1;
				intData = 1;
				stringData = "true";
			} else {
				doubleData = 0;
				intData = 0;
				stringData = "false";
			}
			break;
		default:
			println("Unknow data type");
		}
	}

	public static NData clone(NData data, int type) {
		switch (type) {
		case NDOUBLE:
			return new NDouble(data.getDoubleData());
		case NINT:
			return new NInt(data.getIntData());
		case NSTRING:
			return new NString(data.getStringData());
		case NBOOLEAN:
			return new NBoolean(data.getBooleanData());
		}
		return null;
	}

	public static NData clone(NData data) {
		return clone(data, data.getType());
	}

	public NData clone() {
		return clone(this);
	}

	/////////////// initialObject
	@Override
	protected void initializeObject() {
		CLASSNAME = "NData";
		idAddable = false;
	}

	protected void initializeData() {
		TYPE = -1;
	}

	public NData() {
		super();
		initializeData();
	}
	///////////////

	public static boolean typeConnectable(int outpoint, int inpoint) {
		if (inpoint == NDATA) {
			return true;
		}

		if ((outpoint == inpoint) || (outpoint == NINT && inpoint == NDOUBLE)

		)
			return true;
		else
			return false;
	}

	public int getType() {
		return TYPE;
	}

	public double getDoubleData() {
		convertType(TYPE);
		return doubleData;
	}

	public int getIntData() {
		convertType(TYPE);
		return intData;
	}

	public boolean getBooleanData() {
		convertType(TYPE);
		return booleanData;
	}

	public String getStringData() {
		convertType(TYPE);
		return stringData;
	}

}

class NDouble extends NData {

	/////////////// initialObject
	@Override
	protected void initializeData() {
		TYPE = NDOUBLE;
	}

	public NDouble() {
		this(0);
	}

	public NDouble(double value) {
		doubleData = value;
		convertType(TYPE);
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "NDouble";
		idAddable = false;
	}

	///////////////
	public void set(double value) {
		doubleData = value;
	}

	public double get() {
		return doubleData;
	}
}

class NInt extends NData {

	/////////////// initialObject
	@Override
	protected void initializeData() {
		TYPE = NINT;
	}

	public NInt() {
		this(0);
	}

	public NInt(int value) {
		intData = value;
		convertType(TYPE);
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "NInt";
		idAddable = false;
	}

	///////////////
	public void set(int value) {
		intData = value;
	}

	public int get() {
		return intData;
	}
}

class NString extends NData {
	/////////////// initialObject
	@Override
	protected void initializeData() {
		TYPE = NSTRING;
	}

	public NString() {
		this("");
	}

	public NString(String value) {
		stringData = value;
		convertType(TYPE);
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "NString";
		idAddable = false;
	}

	///////////////
	public void set(String value) {
		stringData = value;
	}

	public String get() {
		return stringData;
	}
}

class NBoolean extends NData {
	/////////////// initialObject
	@Override
	protected void initializeData() {
		TYPE = NSTRING;
	}

	public NBoolean() {
		this(false);
	}

	public NBoolean(boolean value) {
		booleanData = value;
		convertType(TYPE);
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "NBoolean";
		idAddable = false;
	}

	///////////////
	public void set(boolean value) {
		booleanData = value;
	}

	public boolean get() {
		return booleanData;
	}
}

class NVector extends NData {
	private String type = "";
	List<NData> data = new ArrayList<NData>();

	/////////////// initialObject
	@Override
	protected void initializeData() {
		TYPE = NVECTOR;
	}

	public NVector() {
		this(2);
	}

	public NVector(int num) {
		for (int i = 0; i < num; i++) {
			data.add(new NDouble(0));
		}
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "NVector";
		idAddable = false;
	}

	///////////////
	public void computeType() {
		type = "";
		type += CLASSNAME + "[" + data.size() + "]";
		NData cur = data.get(0);
		if (cur instanceof NVector) {

			while (cur instanceof NVector) {
				type += "{" + cur.getClassName() + "}~" + cur.getClassName() + "[" + ((NVector) cur).getNum() + "]";
				cur = ((NVector) cur).get(0);
			}
			type += "{" + cur.getClassName() + "}";
		} else {
			type += "{" + cur.getClassName() + "}";
		}
	}

	public int getNum() {
		return data.size();
	}

	public void set(int id, NData data) {
		this.data.set(id, data);
	}

	public void add(int num, NData data) {
		for (int i = 0; i < num; i++) {
			this.data.add(data.clone());
		}
	}

	public void add(NData data) {
		add(1, data);
	}

	public List<NData> get() {
		return data;
	}

	public NData get(int id) {
		return data.get(id);
	}

	public int getSize() {
		return data.size();
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