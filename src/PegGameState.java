import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;


public class PegGameState {
	private boolean[] isPeg;
	private PegGraphL moves;
	private final int BOARD_SIZE = 15;
	
	/**
	 * Creates a new PegGameState with the data from the specified file
	 * @param fileName
	 */
	public PegGameState(String fileName) {
		isPeg = new boolean[BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE-1; i++)
			isPeg[i] = true;
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
		PegGameState state = new PegGameState("PegBoard.txt");
		System.out.println(state);
	}
}
