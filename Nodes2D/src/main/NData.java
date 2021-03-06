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
	public static final int NIMAGE = 5;

	protected double doubleData = 0;
	protected int intData = 0;
	protected String stringData = "";
	protected boolean booleanData = false;

	public static boolean typeConnectable(int outpoint, int inpoint) {
		if (inpoint == NDATA || outpoint == NDATA) {
			return true;
		}

		if ((outpoint == inpoint) || ((outpoint == NINT) && (inpoint == NDOUBLE)))
			return true;
		else
			return false;
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

	public int getType() {
		return TYPE;
	}
	///////////////

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
	private String structure = "";
	private List<Double> data = new ArrayList<Double>();

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
			data.add(0d);
		}
	}

	public NVector(double r, double g, double b, double a) {
		data.add(lockInColorBoundary(r));
		data.add(lockInColorBoundary(g));
		data.add(lockInColorBoundary(b));
		data.add(lockInColorBoundary(a));
	}

	public NVector(double r, double g, double b) {
		this(r, g, b, 1);
	}

	private double lockInColorBoundary(double input) {
		if (input <= 0d) {
			return 0d;
		} else if (input >= 1d) {
			return 1d;
		} else {
			return input;
		}
	}

	@Override
	protected void initializeObject() {
		CLASSNAME = "NVector";
		idAddable = false;
	}

	///////////////
	public String getStructure() {
		structure = "";
		structure += "{";
		for (int i = 0; i < getSize(); i++) {
			structure += " " + get(i) + " ";
			if (i + 1 < getSize())
				structure += ",";
		}
		structure += "}";
		return structure;
	}

	public void set(int id, double data) {
		this.data.set(id, data);
	}

	public void add(int num, double data) {
		for (int i = 0; i < num; i++) {
			this.data.add(data);
		}
	}

	public void add(double data) {
		add(1, data);
	}

	public List<Double> get() {
		return data;
	}

	public double get(int id) {
		return data.get(id);
	}

	public int getSize() {
		return data.size();
	}
}

class NImage extends NData {
	private List<NVector> data = new ArrayList<NVector>();
	private int width, height;
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int ALPHA = 3;

	public NImage(int width, int height) {
		this.width = width;
		this.height = height;
		for (int i = 0; i < width * height; i++) {
			data.add(new NVector(0d, 0d, 0d, 1d));
		}
	}

	/**
	 * 
	 * @param x
	 *            >=1 && <=width
	 * @param y
	 *            >=1 && <= height
	 * @return result
	 */
	private boolean outOfBoundary(int x, int y) {
		if (x < 1 || x > width) {
			// TODO X_PixelsBoundaryException
			return true;
		}
		if (y < 1 || y > height) {
			// TODO Y_PixelsBoundaryException
			return true;
		}
		return false;
	}

	private int computePosition(int x, int y) {
		return y * width + x;
	}

	/**
	 * 
	 * @param x
	 *            >=1 && <=width
	 * @param y
	 *            >=1 && <= height
	 * @return NVector[4] ( R , G , B , A )
	 */
	public NVector getPixel(int x, int y) {
		if (outOfBoundary(x, y)) {
			return null;
		}
		x--;
		y--;
		return data.get(computePosition(x, y));
	}

	/**
	 * 
	 * 
	 * @param x
	 *            >=1 && <=width
	 * @param y
	 *            >=1 && <= height
	 * @param rgba
	 */
	public void setPixel(int x, int y, NVector rgba) {
		if (outOfBoundary(x, y)) {
			return;
		}
		data.set(computePosition(x, y), rgba);
	}

	public void setPixel(int x, int y, double r, double g, double b, double a) {
		setPixel(x, y, new NVector(r, g, b, a));
	}

	public void setPixel(int x, int y, double r, double g, double b) {
		setPixel(x, y, new NVector(r, g, b, 1));
	}

	public void resetImage(int width, int height) {
		this.width = width;
		this.height = height;
		data.clear();
		for (int i = 0; i < width * height; i++) {
			data.add(new NVector(0d, 0d, 0d, 1d));
		}
	}

}
