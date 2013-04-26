import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

public abstract class AbstractGame<StateType extends State>
{
    public static final int BFS = 1;
    public static final int DFS = 2;
    public static final int A1S = 3;  // best first search
    private HashMap<StateType, StateType> htable;
    protected StateType currentState; 
    private int searchMethod;
    private Queue<StateType> open;
    int childrenAdded;
    int children;
    int pathLength;

    public AbstractGame (int size, int searchType)
    {
       if (size > 0)
       {
          htable = new HashMap<StateType,StateType>(size);
          searchMethod = searchType;
          if (searchType == DFS)
             open = new LinkedList<StateType>();
          else if (searchType == BFS)
             open = new LinkedList<StateType>();
          else if (searchType == A1S)
             open = new PriorityQueue<StateType>(size,makeStateComparator());

          childrenAdded = 0;
          children = 0;
          pathLength = 0;
       }
    }

    public Comparator<StateType> makeStateComparator()
    {
        System.out.println ("To use best first search, ");
        System.out.println ("override this method in your subclass.");
        return null;  
    }

    public void startGame(StateType startState)
    {
       currentState = startState;
       startGame();
    }

    public void startGame()
    {
       if (currentState == null)
          currentState = makeInitialState();
       open.add(currentState);
       htable.put(currentState, null);
    }

    public void printSolution()
    {
       printInReverse(currentState);
    }

   private void printInReverse(StateType someState)
   {
      StateType newState;

      if (someState != null)
      {
         pathLength++;
         newState = htable.get(someState);
         printInReverse(newState);
         prettyPicture(someState);
      }
   } 

   public boolean search()
   {

       while(!open.isEmpty())
       {
          currentState = open.remove();
          if (goalState())
          {
            return true;
          }
          addChildren();
       }
       return false;
    }

   @SuppressWarnings("unchecked")
   public boolean addNewState(StateType st)
   {
        children++;
        if (!htable.containsKey(st))
        {
           htable.put(st, currentState);
           if (searchMethod == DFS)
              ((LinkedList)open).push(st);
           else
              open.add(st);
           childrenAdded++;
           return true;
        }
        return false;
   }

   public void printStats()
   {
        System.out.println ("\n\n\n");
        System.out.println ("Path contained " + pathLength + " steps.");
        System.out.println ("Children generated " + children);
        System.out.println ("Unique children generated " + childrenAdded);
   }

   abstract public void addChildren();
 
   abstract public void prettyPicture(StateType st);

   abstract public boolean goalState();

   abstract public StateType makeInitialState();
}

