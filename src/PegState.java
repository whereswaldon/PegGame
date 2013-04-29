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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean goalState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public State makeInitialState() {
		// TODO Auto-generated method stub
		return null;
	}
}
