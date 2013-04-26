import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;


public class PegGameState implements State{
	private boolean[] isPeg;
	private static PegGraphL moves;
	private static final int BOARD_SIZE = 15;
	
	/**
	 * Creates a new PegGameState with the data from the specified file
	 * @param fileName
	 */
	public PegGameState() {
		isPeg = new boolean[BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE-1; i++)
			isPeg[i] = true;
	}
	
	public void setState(boolean[] pegs) {
		isPeg = pegs;
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
	public static void main(String[] args) {
		PegGameState.setMoves("PegBoard.txt");
		PegGameState state = new PegGameState();
		System.out.println(state);
	}

	@Override
	public void copy(State anotherState) {
		if (anotherState instanceof PegGameState) {
			this.setState( ((PegGameState) anotherState).getState());
		}
	}
	
	public PegGameState clone() {
		PegGameState copy = new PegGameState();
		copy.setState(this.getState());
		return copy;
	}
}
