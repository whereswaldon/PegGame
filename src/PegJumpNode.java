/**
 * A data type to represent both the destination of a jump and the location jumped in a PegGraphL
 * @author christopherwaldon
 *
 */
public class PegJumpNode {
	private int jumpTo;
	private int jumped;
	
	public PegJumpNode(int jumpTarget, int jumpedOver) {
		jumpTo = jumpTarget;
		jumped = jumpedOver;
	}
	
	public int getJumpTo() {
		return jumpTo;
	}
	
	public int getJumped() {
		return jumped;
	}
}
