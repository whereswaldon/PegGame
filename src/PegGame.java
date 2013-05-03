import java.util.Iterator;

/**
 * Plugs the Cracker Barrel Peg game into AbstractGame to search for a solution
 * @author christopher waldon AND sina tashakkori
 *
 */


public class PegGame extends AbstractGame<PegState>{

	public PegGame(int searchType) {
		super(9000, searchType);
	}

	@Override
	public void addChildren() {
		//System.out.println("called");
		//create state we want to move to
		PegState goalstate = new PegState();
		
		//create integers to hold the values of other holes on the board
		int start, end, jumpOver;
		
		PegJumpNode node;
		//iterates through all nodes
		for (int i = 0; i < 15; i++) {
			if (currentState.getState()[i]) {
				Iterator<PegJumpNode> iter = currentState.getMoves().neighbors(i);
				while (iter.hasNext()) {
					node = iter.next();
					goalstate = currentState.clone();
					//checks if the move is possible
					if (((PegState)currentState).moveIsValid(i,node.getJumpTo(),node.getJumped())) {
						start = i;
						end = node.getJumpTo();
						jumpOver = node.getJumped();
						
						//creates a new state that includes that move
						goalstate = ((PegState) currentState).clone();
						boolean[] temp = goalstate.getState();
						temp [start] = false;
						temp [end] = true;
						temp [jumpOver] = false;
						goalstate.setState(temp);
						
						//System.out.println("Child:");
						//prettyPicture(goalstate);
						//adds the new state
						if (addNewState(goalstate)){
							goalstate = new PegState();
						}
					}
				}
			}
		}
	}


	@Override
	public boolean goalState() {
		int count = 0; // how many trues are in the array
		for (int i = 0; i < currentState.getState().length; i++) {
			if (currentState.getState()[i]) {
				count++;
			}
		}
		if (count == 0) {
			System.out.println("ERROR, ZERO PEGS");
		}
		else if (count == 1) {
			System.out.println("Path to Goal:");
			return true;
		}
		return false;
	}

	@Override
	public PegState makeInitialState() {
		PegState ret = new PegState();
		return null;
	}

	@Override
	public void prettyPicture(PegState st) {
        PegState ourState = (PegState)st;
        boolean[] checking = ourState.getState();
        
        for(int i = 0; i < checking.length; i++){
                if(i == 0) System.out.print("    ");
                if(i == 1) System.out.print("   ");
                if(i == 3) System.out.print("  ");
                if(i == 6) System.out.print(" ");
                if(checking[i]) System.out.print("O ");
                else System.out.print(". ");
                if(i == 0 || i == 2 || i == 5 || i == 9) System.out.println();
            }
        System.out.println("\n");
        }

	public static void main(String[] args) {
		PegGame game = new PegGame(BFS);
		PegState.setMoves("PegBoard.txt");
		game.startGame(new PegState());
		if (game.search())
			game.printSolution();
		else {
			System.out.println("No solution found");
		}
		game.printStats();
	}
}
