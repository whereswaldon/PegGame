
public class PegGameState {
	private boolean[] isPeg;
	private PegGraphL moves;
	
	public PegGameState() {
		isPeg = new boolean[15];
		for (int i = 0; i < 14; i++)
			isPeg[i] = true;
	}

}
