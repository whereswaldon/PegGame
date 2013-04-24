
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
