package main;

/**
 * An exception class about Nodes.
 * 
 * @author ZzStarSound
 * @version v1.0
 * @see java.lang.Exception
 * @see main.NodePoint
 * @see main.Node
 */
public class NodeException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public NodeException() {
		this("Error(s) occured unexpectedly(NodeErrorId:-1)");
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
	 *            .<BR/>
	 *            .<BR/>
	 *            .<BR/>
	 * @param actionName
	 *            source name
	 */
	public NodeException(int errorId, String actionName) {
		switch (errorId) {
		case 0:
			message = "NodeError(id:" + 0 + ") : ";
			message += "you can't connect NodePoints with the same in/output type.";
			message += "This occured while working on" + actionName + ".";
			break;
		case 1:
			message = "NodeError(id:" + 1 + ") : ";
			message += "disconnection at NodeLine failed.";
			message += "This occured while working on" + actionName + ".";
			break;
		case 2:
			message = "NodeError(id:" + 2 + ") : ";
			message += "array boundary exceeded.";
			message += "This occured while working on" + actionName + ".";
			break;
		default:
			message = "Error(s) occured unexpectedly(NodeErrorId:-1)";
			break;
		}
	}

	/**
	 * System.out.println(message);
	 */
	void println() {
		System.out.println(message);
	}
}
