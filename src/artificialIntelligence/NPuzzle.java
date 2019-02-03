package artificialIntelligence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class NPuzzle {

	private static int GRID_SIZE;
	private static int N;
	private static int[][] goalState;
	private State rootNode = new State();
	private PriorityQueue<State> states = new PriorityQueue<State>();
	private static enum SHIFT {
		LEFT, RIGHT, UP, DOWN
	}
	
	public NPuzzle(State startState, int puzzleSize, int gridSize) {
		GRID_SIZE = gridSize;
		N = puzzleSize;
		
		goalState =  new int[GRID_SIZE][GRID_SIZE];
		setGoalState();
		
		rootNode = startState;
		rootNode.setHVal(0);
		rootNode.setGVal(0);
		
		states.offer(rootNode);
	}
	
	private void setGoalState() {
		int digit = 1;
		for(int i=0; i<GRID_SIZE; i++)
			for(int j=0; j<GRID_SIZE; j++) {
				goalState[i][j] = digit;
				digit++;
			}
		goalState[GRID_SIZE-1][GRID_SIZE-1] = 0;
	}
	public boolean isValid() {
		int[][] startState = rootNode.getPuzzleState();
		int[] digitFrequency = new int[N];
		int digit = 0;
		int i,j;
		for(i=0; i<GRID_SIZE; i++)
			for(j=0; j<GRID_SIZE; j++){
				digit = startState[i][j];
				if(digit <= N && digit >= 0)
					digitFrequency[digit]++;
			}
		for(i=0; i<9; i++)
			if(digitFrequency[i] != 1)
				return false;
		return true;
	}
	
	public boolean hasSolution() {
		int[][] startState = rootNode.getPuzzleState();
		int[] sequence = new int[N];
		int count = 0, diff = 0;
		int i, j;
		for(i=0; i<GRID_SIZE; i++)
            for(j=0; j<GRID_SIZE; j++) {
            		if(startState[i][j] != 0){
            			sequence[count] = startState[i][j];
            			count++;
            		}
            		else
            			diff = (GRID_SIZE - i - 1) % 2;
            }
		count = 0;
		for(i=0; i<N; i++)
			for(j=i+1; j<N; j++)
				if(sequence[i] > sequence[j])
                    count++;
		if(GRID_SIZE % 2 == 1) {
			if(count % 2 == 0)
				return true;
		}
		else {
			if((diff == 0 && count % 2 == 1) || (diff == 1 && count % 2 ==0))
				return true;
		}
		return false;
	}

	public void solveByMethod(int algorithmChoice) {//Default Method(1) is the A* with h(n) = 0.

		int statesCount = 0;
		int maxQueueSize = 0;
		List<int[][]> visitedStates = new ArrayList<int[][]>();
		
		while (states.peek() != null) {
			maxQueueSize = Math.max(maxQueueSize, states.size());
			State currentState = states.peek();
			states.remove();
			
			if(goalStateReached(statesCount, maxQueueSize, currentState)) //breaks the loop iterating the tree if goal is reached
				break; 
			
			if(notRepeatedState(currentState, visitedStates)){
				System.out.println("The best state to expand with a g(n)=" + currentState.getGVal() + 
						" and a h(n)=" + currentState.getHVal() + " is:");
				for(int i=0; i<GRID_SIZE; i++) {
					for(int j=0; j<GRID_SIZE; j++) 
						System.out.print(currentState.getPuzzleState()[i][j] + " ");
					System.out.println();
				}
				statesCount++;
				State left = move(currentState, SHIFT.LEFT);
				if(left.getGVal() != 0){
					if(algorithmChoice == 2)
						solveByMisplacedTile(left);
					else if (algorithmChoice == 3)
						solveByManhattanDistance(left);
					states.offer(left);
				}
				
				State right = move(currentState, SHIFT.RIGHT);
				if(right.getGVal() != 0){
					if(algorithmChoice == 2)
						solveByMisplacedTile(right);
					else if (algorithmChoice == 3)
						solveByManhattanDistance(right);
					states.offer(right);
				}
			
				State up = move(currentState, SHIFT.UP);
				if(up.getGVal() != 0){
					if(algorithmChoice == 2)
						solveByMisplacedTile(up);
					else if (algorithmChoice == 3)
						solveByManhattanDistance(up);
					states.offer(up);
				}
				
				State down = move(currentState, SHIFT.DOWN);
				if(down.getGVal() != 0){
					if(algorithmChoice == 2)
						solveByMisplacedTile(down);
					else if (algorithmChoice == 3)
						solveByManhattanDistance(down);
					states.offer(down);
				}
				visitedStates.add(currentState.getPuzzleState());
			}
		}
		return;	
	}

	private void solveByMisplacedTile(State node) {
		int h = 0;
		for(int i=0; i<GRID_SIZE; i++)
			for(int j=0; j<GRID_SIZE; j++)
				if((node.getPuzzleState()[i][j] != goalState[i][j]) && node.getPuzzleState()[i][j] != 0)
					h++;
		node.setHVal(h);
	}

	private void solveByManhattanDistance(State node) {
		int h = 0;
		for(int i=0; i<GRID_SIZE; i++)
			for(int j=0; j<GRID_SIZE; j++)
				if((node.getPuzzleState()[i][j] != goalState[i][j]) && node.getPuzzleState()[i][j] != 0)
					for(int x=0; x<GRID_SIZE; x++)
						for(int y=0; y<GRID_SIZE; y++)
							if(node.getPuzzleState()[i][j] == goalState[x][y])
								h = h + Math.abs(i-x)+ Math.abs(j-y);
		node.setHVal(h);
	}

	private State move(State currentState, SHIFT direction) {
		State next = new State();
		int row = 0, column = 0;
		int newState[][] = new int[GRID_SIZE][GRID_SIZE];
		for(int i=0; i<GRID_SIZE; i++)
			for(int j=0; j<GRID_SIZE; j++){
				newState[i][j] = currentState.getPuzzleState()[i][j];
				if(currentState.getPuzzleState()[i][j] == 0){
					row = i;
					column = j;
				}
			}
		next.setGVal((currentState.getGVal()) + 1);
		int rowShift = row, columnShift = column;
		switch(direction) {
		case LEFT: if(column != 0)
			columnShift = column - 1;
		else
			next.setGVal(0);
		break;
		case RIGHT: if(column != (GRID_SIZE-1))
			columnShift = column + 1;
		else
			next.setGVal(0);
		break;
		case UP: if(row != 0)
			rowShift = row - 1;
		else
			next.setGVal(0);
		break;
		case DOWN: if(row != (GRID_SIZE-1))
			rowShift = row + 1;
		else
			next.setGVal(0);
		break;
		}
		shiftTile(newState, rowShift, columnShift, row, column);
		next.setPuzzleState(newState);
		
		return next;
	}

	private boolean goalStateReached(int statesCount, int maxQueueSize, State currentState) {
		if(Arrays.deepEquals(goalState, currentState.getPuzzleState())){
			System.out.println("Goal!!");
			System.out.println("To solve this problem search algorithm expanded a total of "+ statesCount +" states.");
			System.out.println("The maximum number of nodes in the queue at any one time was "+ maxQueueSize);
			System.out.println("Depth of the goal:" +currentState.getGVal());
			return true;
		}
		return false;
	}

	private boolean notRepeatedState(State currentState, List<int[][]> visitedStates) {
		int count = 0;
		for(int[][] state :visitedStates )
			if(Arrays.deepEquals(state, currentState.getPuzzleState()))
				count++;
		if(count == 0)
			return true;
		else
			return false;
	}
	
	private static void shiftTile(int[][] state, int rowShift ,int columnShift, int row, int column) {
		int temp = state[rowShift][columnShift];
		state[rowShift][columnShift] = state[row][column];
		state[row][column] = temp;
	}
}
