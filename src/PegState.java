import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


public class PegState implements State{
	private boolean[] isPeg;
	private static PegGraphL moves;
	/**
	 * @return the moves
	 */
	public static PegGraphL getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public static void setMoves(PegGraphL moves) {
		PegState.moves = moves;
	}

	private static final int BOARD_SIZE = 15;
	
	/**
	 * Creates a new PegGameState with the data from the specified file
	 * @param fileName
	 */
	public PegState() {
		isPeg = new boolean[BOARD_SIZE];
		setStartState();
	}
	
	public void setState(boolean[] pegs) {
		for (int i = 0; i < pegs.length; i++) {
			isPeg[i] = pegs[i];
		}
	}
	
	public boolean[] getState() {
		return isPeg;
	}
	
	public static void setMoves(String fileName) {
		moves = new PegGraphL(BOARD_SIZE);
		try {
			Scanner file = new Scanner(new FileReader(fileName));
			while (file.hasNext()) {
				moves.addEdge(file.nextInt(), file.nextInt(), file.nextInt());
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return moves.toString() + "\n\n" + Arrays.toString(isPeg);
	}
	
//	public static void main(String[] args) {
//		PegState.setMoves("PegBoard.txt");
//		PegState state = new PegState();
//		System.out.println(state);
//	}

	public void copy(State anotherState) {
		if (anotherState instanceof PegState) {
			this.setState( ((PegState) anotherState).getState());
		}
	}
	
	public PegState clone() {
		PegState copy = new PegState();
		copy.setState(this.getState());
		return copy;
	}

	private void setStartState() {
		for (int i = 0; i < isPeg.length-1; i++) {
			isPeg[i] = true;
		}
	}
	
	public boolean moveIsValid(int source, int destination, int between) {
		if (isPeg[source] && !isPeg[destination] && isPeg[between]) {
			Iterator<PegJumpNode> iter = moves.neighbors(source);
			PegJumpNode node;
			while (iter.hasNext()) {
				node = (PegJumpNode) iter.next();
				if (node.getJumped() == between && node.getJumpTo() == destination) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PegState) {
			PegState compareTo = (PegState) o;
			
			for (int i = 0; i < isPeg.length; i++) {
				if (isPeg[i] != compareTo.getState()[i]) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	
	}
	
	public int hashCode() {
		String concat = "";
		for (int i = 0; i < isPeg.length; i++) {
			if (isPeg[i]) {
				concat += i;
			}
		}
		return concat.hashCode();
	}
}
