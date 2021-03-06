package main;

/**
 * An exception class about Nodes.
 * 
 * @author ZzStarSound
 * @version v1.0
 * @see java.lang.Exception
 * @see Node.NodePoint
 * @see Node.Node
 */
public class NodeException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public NodeException() {
		this("Error(s) occured unexpectedly(NodeErrorId=-1)");
	}

	public NodeException(String msg) {
		message = msg;
	}

	/**
	 * Id constructor should be used when especial errors occurred.
	 * 
	 * @param errorId
	 *            <BR/>
	 *            case 0 : you can't connect NodePoints with the same in/output
	 *            type.<BR/>
	 *            case 1 : disconnection at NodeLine failed.<BR/>
	 *            case 2 : array boundary exceeded.<BR/>
	 *            case 3 : data type of NodePoint is not an instance of NData.<BR/>
	 *            case 4 : data overflowed.<BR/>
	 *            case 5 : types of tow points are different.<BR/>
	 *            case 6 : unknown point mode.<BR/>
	 *            .<BR/>
	 *            .<BR/>
	 *            .<BR/>
	 * @param actionName
	 *            source name
	 */
	public NodeException(int errorId, String actionName) {
		message = "";
		switch (errorId) {
		case 0:
			message = "NodeError(id:" + 0 + ") : ";
			message += "you can't connect NodePoints with the same in/output type.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		case 1:
			message = "NodeError(id:" + 1 + ") : ";
			message += "disconnection at NodeLine failed.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		case 2:
			message = "NodeError(id:" + 2 + ") : ";
			message += "array boundary exceeded.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		case 3:
			message = "NodeError(id:" + 3 + ") : ";
			message += "data type of NodePoint is not an instance of NData.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		case 4:
			message = "NodeError(id:" + 4 + ") : ";
			message += "data overflowed.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		case 5:
			message = "NodeError(id:" + 5 + ") : ";
			message += "types of tow points are different.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		case 6:
			message = "NodeError(id:" + 6 + ") : ";
			message += "unknown point mode.";
			message += "\nThis occured while working on " + actionName + ".";
			break;
		default:
			message = "Error(s) occured unexpectedly(NodeErrorId=-1)";
			break;
		}
		message += "\n";
	}

	/**
	 * System.out.println(message);
	 */
	void println() {
		System.out.println(message);
	}
}
