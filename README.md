The application solves the 8 - puzzle. and provides a choice of three algorithms for the same. The user can select between the Uniform Cost Search, A* using Manhattan distance heuristic and A* using misplaced tile heuristic. 
The application was developed using Java (version 7) and has an interactive interface that works through the standard output, the input from the user is taken through the standard input.
Design
The application consists of three classes described below:
State.class: Implements the Comparable interface. Consists of the puzzleState, gVal (g(n)) and hVal (h(n)) as fields. The class consists of getters and setters for the same. It implements the compareTo method of the Comparable interface. It takes a State object as argument and compares the cost (hVal + gVal) of itself to that of the State in the parameter, if the cost of the parameter’s state is equal to its cost it returns 0, if lesser the method returns 1, else it returns -1. 
NPuzzle.class: It consists of the fields for goalState, rootNode (initial state), a priority queue of the states (used to store the tree structure), an enum structure for the movements of the tiles and attributes for puzzle size. The class also contains the methods for solving the puzzle by Uniform Cost Search (solveByMethod) which takes the algorithmChoice of the user as parameter and runs the appropriate method (default being the uniform cost). The methods, solveByMisplacedTile() and solveBymanhattan(), compute the h(n) value using the respective methods. The move method takes the currentState and direction to move the tile in that direction. The goalStateReached() method checks if the goal state is reached and prints the trace on the standard output. The notRepeatedState() method checks if the current state is not repeated, only then it is expanded. The class also consists of an “isValid()” method which checks the validity of the puzzle input.
PuzzleSolver.class: This class consists of the main() method which handles the interactive interface of the application and has methods to take inputs and run the NPuzzle methods to solve the puzzle.
Adaptability
The code was written as generally as possible, with all the loops and other identifiers being created with variable properties. The size of the puzzle (8, 15 – puzzle, etc.) can be changed by altering the PUZZLE_SIZE field in the PuzzleSolver class and the application adapts to the change by taking the appropriate input, creating a goal state and runs the algorithms on the new puzzle size. The default input provided in the PuzzleSolver class is however, hardcoded for the 8 – puzzle only. The application was run once with a 15-puzzle and works accurately.
Algorithms
The initial state is first expanded in all its possible subordinate states by checking if the blank space can be moved left, right, up and down. Out of all the valid moves new states are formed. Of these new states, the best state to expand is chosen using the cost calculated by the algorithm chosen by the user. In each iteration, the state is checked for a match with the goal state and also for repeated states.


Uniform Cost Search
This method searches the branches with the same cost. It is basically the A* algorithm with h(n) set to zero. The cost of expanded node is g(n) which is one.
Misplaced Tile Heuristic
In this method, each non-blank element in the current state is compared to the goal state and if, it is different. the ‘h’ value is incremented. After traversing through the entire grid, the final ‘h’ value is set to that node. For example,
Goal State:	1	2	3	Puzzle State:		1	2	4
4	5	6				3	0	6
7	8	0				7	8	5
The underlined elements are the non-matching ones between puzzle state and goal state. The blank element in the puzzle state is ignored. Therefore, the misplaced tile heuristic is 3. The h(n) value for this puzzle state is set as 3.
Manhattan Distance Heuristic
This heuristic is calculated by finding the number of places the initial state of the element is away from its place in the goal state. This value is calculated for each element and then the sum of those values is set as the h(n) value for that state.
Goal State:	1	2	3	Puzzle State:		1	2	4
4	5	6				3	0	6
7	8	0				7	8	5
h4= 1 + 2 = 3, h3 = 1 + 2 = 3, h5 = 1 + 1 = 2
Therefore h(n) for this state = 3 + 3 + 2 = 8
