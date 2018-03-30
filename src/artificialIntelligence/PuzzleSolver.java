package artificialIntelligence;

import java.util.Scanner;

public class PuzzleSolver {

	private static final int PUZZLE_SIZE = 8;	//Change value to solve N-puzzle
	public static int[][] initialState;

	public static void main(String[] args) {
		
		int GRID_SIZE = (int) Math.sqrt(PUZZLE_SIZE + 1);
		initialState = new int[GRID_SIZE][GRID_SIZE];
		System.out.println("Welcome to the "+ PUZZLE_SIZE +"-Puzzle Solver");
		System.out.println("Type '1' to use a default puzzle, or '2' to enter your own puzzle.");
		Scanner scanner = new Scanner(System.in);
		int defaultOrCustom = scanner.nextInt();
		
		switch(defaultOrCustom) {
		case 1:inputDefaultPuzzle();
				break;
		case 2:inputCustomPuzzle(scanner, GRID_SIZE);
				break;
		default:System.out.println("Invalid input. Exiting...");
				scanner.close();
				return;
		}
		boolean gameON = true;
		State startState = new State();
		startState.setPuzzleState(initialState);
		NPuzzle puzzle = new NPuzzle(startState, PUZZLE_SIZE + 1, GRID_SIZE);

		if(puzzle.isValid() && puzzle.hasSolution()) {
			while(gameON) {
				puzzle = new NPuzzle(startState, PUZZLE_SIZE, GRID_SIZE);
				System.out.println("Enter your choice of algorithm\n" + 
						"1.	Uniform Cost Search\n" + 
						"2.	A* with the Misplaced Tile heuristic.\n" + 
						"3.	A* with the Manhattan distance heuristic\n");
				int algorithmChoice = scanner.nextInt();
				puzzle.solveByMethod(algorithmChoice);
				System.out.println("\nEnter 1 to use different algo OR 0 to exit.");
				if(scanner.nextInt() == 0)
					gameON = false;
			}
		}
		else {
			System.out.println("Puzzle invalid or unsolvable. Exiting...");
			scanner.close();
			return;
		}
		scanner.close();
	}

	private static void inputDefaultPuzzle() {//hardcoded a default input.
		initialState[0][0] = 1;
		initialState[0][1] = 2;
		initialState[0][2] = 3;
		initialState[1][0] = 4;
		initialState[1][1] = 8;
		initialState[1][2] = 0;
		initialState[2][0] = 7;
		initialState[2][1] = 6;
		initialState[2][2] = 5;
		System.out.println("Using default puzzle \n1 2 3\n4 8 0\n7 6 5\n");
	}

	private static void inputCustomPuzzle(Scanner scanner, int GRID_SIZE) {
		System.out.println("Enter your puzzle, use a zero to represent the blank");
		for(int i = 0; i<GRID_SIZE; i++) {
			System.out.println("Enter row "+ (i+1) +", use space or tabs between numbers");
			for(int j=0; j<GRID_SIZE; j++) 
				initialState[i][j] = scanner.nextInt();
		}
	}
}
