import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;


public class PegState extends AbstractGame implements State{
	private boolean[] isPeg;
	private static PegGraphL moves;
	private static final int BOARD_SIZE = 15;
	
	/**
	 * Creates a new PegGameState with the data from the specified file
	 * @param fileName
	 */
	public PegState() {
		super(BOARD_SIZE, 1);
		
		isPeg = new boolean[BOARD_SIZE];
		setStartState();
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
		PegState.setMoves("PegBoard.txt");
		PegState state = new PegState();
		System.out.println(state);
	}

	@Override
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

	@Override
	public void addChildren() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prettyPicture(State st) {
		System.out.println(this.toString());
	}
	
	private void setStartState() {
		for (int i = 0; i < isPeg.length-1; i++) {
			isPeg[i] = true;
		}
	}

	@Override
	public boolean goalState() {
		int count = 0; // how many trues are in the array
		for (int i = 0; i < isPeg.length; i++) {
			if (isPeg[i]) {
				count++;
			}
		}
		if (count == 0) {
			System.out.println("ERROR, ZERO PEGS");
		}
		else if (count == 1) {
			return true;
		}
		return false;
	}
	
	public int hashcode() {
		String concat = "";
		for (int i = 0; i < isPeg.length; i++) {
			if (isPeg[i]) {
				concat += i;
			}
		}
		return concat.hashCode();
	}

	@Override
	public State makeInitialState() {
		PegState ret = new PegState();
		return null;
	}
}
