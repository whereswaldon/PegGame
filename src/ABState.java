/****************************************************************
 Author: Alice McRae
 Class implemented: ABState

 Purpose: Represents a current state of the puzzle.
  AUTHOR:  Alice McRae

              _____
             |     |
             |  0  |
        _____|_____|_____
       |     |     |     |
       |  1  |  2  | 3   |
       |_____|_____|_____|_____
       |     |     |     |     |
       |  4  |  5  |  6  |  7  |
       |_____|_____|_____|_____|
             |     |     |
             |  8  |  9  |
             |_____|_____|

  A state is represented as a 4-digit array: positions.
  positions[0] and positions[1] are the locations of the a's.
  positions[2] and positions[3] are the locations of the b's.
  Within the array, positions 0 and 1 are kept sorted, and
  positions 2 and 3 are kept sorted so that, for example,
  0179 and 1097 are the same.
 
********************************************************************/

// CONSTRUCTOR
//  ABState(int a1_pos=0, int a2_pos=1, int b1_pos=7, int b2_pos=9)
//

// MEMBER MODIFICATION FUNCTIONS
// void setState(int a1_pos, int a2_pos, int b1_pos, int b2_pos);
//     allows one to set a state to the four input parameter positions 

// void changeState(int piece, int value) 
//      allows one to change the value(location) of one of the four pieces

// void orderState();
//      sorts the state, so positions[0] and [1] are in sorted order
//                       and positions [2] and [3] are in sorted order

// int getState(int piece) 
//      returns the position of a given one of the four pieces

// int hashCode() const; 
//      hashes a board to an integer 

// ABState clone();
//      returns a copy of this ABState

// void copy(ABState  x);
//   copies ABState's values into this

// boolean equals (State  x);
//    checks to see if x is the same state as this;
       
public class ABState implements State
{
     private int positions[];

     public ABState()
     {  
        positions = new int[4];
        positions[0] = 0;  
        positions[1] = 1; 
        positions[2] = 7; 
        positions[3] = 9; 
     }

     public  ABState(int a1Pos, int a2Pos, int a3Pos, int a4Pos)
     {  
         positions = new int[4];
         setState (a1Pos, a2Pos, a3Pos, a4Pos);
         orderState();
     }
 
     public void setState (int a1Pos, int a2Pos, int a3Pos, int a4Pos)
     {
        positions[0] = a1Pos;  
        positions[1] = a2Pos; 
        positions[2] = a3Pos; 
        positions[3] = a4Pos; 
        orderState();
     }
          
     public void changeState(int piece, int value) 
     { 
         positions[piece] = value; 
         orderState();
     }

     public void orderState()
     {
        int temp;
        if (positions[0] > positions[1])
        {
              temp = positions[0];
              positions[0] = positions[1]; 
              positions[1] = temp;
        }
        if (positions[2] > positions[3])
        {
              temp = positions[3];
              positions[3] = positions[2]; 
              positions[2] = temp;
        }
     }        
 
     public int getState(int piece) 
     {
          return positions[piece];
     }

     public int hashCode() 
     {
          return (positions[0])*1000 + positions[1]*100 + 
              positions[2]*10 + positions[3];
     }

     public void copy(State otherAB)
     {
          ABState comp = (ABState) otherAB;
          for (int i=0; i < 4; i++)
             positions[i] = comp.positions[i];
     }

     public void printState()
     {
         System.out.print (hashCode());
     }

     public boolean equals(Object y) 
     {
         if (y instanceof ABState) {
           ABState x = (ABState) y;
           for (int i=0; i < 4; i++)
               if (positions[i] != x.positions[i])
                   return false;
            return true;
         }
         return false;
     }

     public ABState clone()
     {
         ABState newState = new ABState();
         newState.copy(this);
         return newState;
     } 
}
