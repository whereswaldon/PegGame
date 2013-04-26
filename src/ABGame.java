/*********************************************************************

 Author: Alice McRae
 Class ABGame: 

 *********************************************************************/

// CONSTRUCTOR
//    ABGame();

// void readGame(String fileName);
//    reads the game in from a file.

//  void addChildren();      
//     determines the children of a current state

//  void prettyPicture(ABState someState); 
//     prints out a state of the puzzle
       
//  boolean goalState();
//     returns if the current state is the goal state
  
//  void init(ABState startState);
//     initializes the start state to this state

//  void init();
//     initializes using the default value of the start state

//  void printSolution();
//           prints a solution to the game  

//  boolean search();
//           searches for a solution

import java.io.*;
import java.util.*;

public class ABGame extends AbstractGame<ABState>
{
      public class Square 
      {
          int x_coord;     // coordinates of a square on the board
          int y_coord;     
      };

      Square pos[];          // the coordinates of all squares
      
      ABState finalState; // the goal state  
      GraphL moves;       // adjacency lists tell what squares are
                           // adjacent to a particular square 
      char let[];
      public static int NUM_SQUARES = 10;
      Scanner keyIn; 
       
 
// CONSTRUCTOR: 
//    first tells the class we want a hash table of size 9000
//    and to do a queue for a breadth first search.  Sets up 
//    the adjacency lists for each of the squares. 


public ABGame(int sType)
{
    super(16384, sType); 
    pos = new Square[NUM_SQUARES];
    moves = new GraphL(10);
    let = new char[10];
    finalState = new ABState();
    keyIn = new Scanner(System.in);
}

// THE INPUT FILE IS IN THIS FORMAT
// First ten lines:
//   coordinates of the ten squares of the board.
// Next few lines:
//   a list of which squares are adjacent to which others
//   a -1 to mark the end of this list
// What squares to put the a's and b's on initially
// What squares the a's and b's should end up on
//
// The method reads the file into the data structures and
//   sets the current and final states 
 
public void readGame(String filename)
{
    Scanner fileIn;

    int v1, v2;
    int a1, a2, b1, b2;
    int x, y;
   
    ABState inputState = makeInitialState();
    try {
        fileIn = new Scanner (new FileReader(filename));

        for (int i = 0; i < NUM_SQUARES; i++)
        {
           x = fileIn.nextInt();
           y = fileIn.nextInt();
           pos[i] = new Square();
           pos[i].x_coord = x; 
           pos[i].y_coord = y; 
        }
        v1 = fileIn.nextInt();
        while (v1 >= 0)
        {
           v2 = fileIn.nextInt();
           moves.addEdge(v1,v2);
           v1 = fileIn.nextInt();
        }

        a1 = fileIn.nextInt();
        a2 = fileIn.nextInt();
        b1 = fileIn.nextInt();
        b2 = fileIn.nextInt();
        inputState.setState(a1, a2, b1, b2);

        a1 = fileIn.nextInt();
        a2 = fileIn.nextInt();
        b1 = fileIn.nextInt();
        b2 = fileIn.nextInt();
        finalState.setState(a1, a2, b1, b2);

        fileIn.close();
    }catch (IOException ioe)
    {
        System.out.println (ioe.getMessage());
        System.exit(1);
    }
    System.out.println ("Read the file to completion");
    startGame(inputState);
}

// Determines if the current state is a goal state

public boolean goalState()
{
     return (currentState.equals(finalState));
}
    
// Determines where we can get to from a current state.
//    for each piece: try to move it to an adjacent square
//       and if it is possible and we haven't been there
//       before, add it to the queue and the hash table.  

public void addChildren()
{
    ABState newState = new ABState();  // where we hope to move to
    ListIterator<Integer> itr;

    int curSquare=0, parSquare=0, op1Square=0, op2Square=0, 
               futureSquare=0;
       // locations of the current piece we are considering moving, 
       // its partner, and the positions of the opponent squares
       // and the square we wish to move to
    
    for (int i = 0; i < 4; i++)
    {
       switch (i) {
        case 0 : curSquare = currentState.getState(0);
               parSquare =   currentState.getState(1);
               op1Square =   currentState.getState(2);
               op2Square =   currentState.getState(3);
               break;
        case 1 : curSquare = currentState.getState(1);
               parSquare =   currentState.getState(0);
               op1Square =   currentState.getState(2);
               op2Square =   currentState.getState(3);  
               break;
        case 2 : curSquare = currentState.getState(2);
               parSquare =   currentState.getState(3);
               op1Square =   currentState.getState(0);
               op2Square =   currentState.getState(1);  
               break;
        case 3 : curSquare = currentState.getState(3);
               parSquare =   currentState.getState(2);
               op1Square =   currentState.getState(0);
               op2Square =   currentState.getState(1);  
               break;
        }
        for (itr = moves.neighbor(curSquare); itr.hasNext(); )
        {
             futureSquare = itr.next();
             if (OK_move(futureSquare,curSquare, parSquare,
                        op1Square,op2Square))
             {
                 newState.copy(currentState);
                 newState.changeState(i,futureSquare);
                  
                 if (addNewState(newState)) {
                    newState = new ABState();
                 }
             }
        }
     }
}


// checks to see if a move is valid.

private boolean OK_move ( int futureSquare, int currentSquare, 
           int partnerSquare, int opp1, int opp2)
{
   /* check to see if the new square is on an opponents row or column */

   if (pos[futureSquare].x_coord == pos[opp1].x_coord)
      return false;
   else if (pos[futureSquare].x_coord == pos[opp2].x_coord)
      return false;
   else if (pos[futureSquare].y_coord == pos[opp1].y_coord)
      return false;
   else if (pos[futureSquare].y_coord == pos[opp2].y_coord)
      return false;

   /* you cannot move to a partner's square                     */

   else if (futureSquare == partnerSquare)
      return false;

   /* you cannot jump over the partner's square */

   else if (pos[futureSquare].x_coord == pos[currentSquare].x_coord
    && pos[futureSquare].x_coord == pos[partnerSquare].x_coord &&
       (( pos[currentSquare].y_coord < pos[partnerSquare].y_coord) ==
       (pos[partnerSquare].y_coord < pos [futureSquare].y_coord)))
       return false;
   else if (pos[futureSquare].y_coord == pos[currentSquare].y_coord
     && pos[futureSquare].y_coord == pos[partnerSquare].y_coord &&
       (( pos[currentSquare].x_coord < pos[partnerSquare].x_coord) ==
       ( pos[partnerSquare].x_coord < pos[futureSquare].x_coord)))
       return false;

   /* the move is OK, */

   else
       return true;
}

// print a pretty picture of the board
 
public void prettyPicture (ABState someState)
{
     int moveOn;

     for (int i=0; i<10; i++)
        let[i] = ' ';

     let [someState.getState(0)] = 'A';
     let [someState.getState(1)] = 'A';
     let [someState.getState(2)] = 'B';
     let [someState.getState(3)] = 'B';

     System.out.println( "       _____      ");
     System.out.println( "      |     |     ");
     System.out.println( "      |  " + let[0] + "  |     ");
     System.out.println( " _____|_____|_____     ");
     System.out.println( "|     |     |     |    ");
     System.out.println( "|  "  + let[1] + "  |  " + let[2] + 
                 "  |  "  + let[3] +  "  |");
     System.out.println( "|_____|_____|_____|_____  ");
     System.out.println( "|     |     |     |     | ");
     System.out.println( "|  "  + let[4] + "  |  " + let[5] + 
                 "  |  "  + let[6] + "  |  "  + let[7] + "  |");
     System.out.println("|_____|_____|_____|_____|");
     System.out.println( "      |     |     |      ");
     System.out.println( "      |  " + let[8] + "  |  " + let[9] + "  | ");
     System.out.println( "      |_____|_____|      \n\n");
     System.out.println( "****** press  0 and return to continue ****");
     moveOn = keyIn.nextInt();
}

public ABState makeInitialState()
{
   return new ABState();
}


public static void main(String args[])
{
       ABGame abPuzzle;
       int searchMethod = BFS;
  
       if (args.length < 1)
       {
          System.out.println ("Usage: executable inputfilename");
          System.exit(0);
       }
       else if (args.length < 2)
          searchMethod = BFS;
       else
          searchMethod = new Integer(args[1]);
       abPuzzle = new ABGame(searchMethod);

       abPuzzle.readGame(args[0]);
       if (abPuzzle.search())             
         abPuzzle.printSolution();        
       else
         System.out.println( "No solution found");       
       abPuzzle.printStats();
}


}
