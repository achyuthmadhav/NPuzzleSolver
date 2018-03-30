package artificialIntelligence;

public class State implements Comparable<State>{
	private int[][] puzzleState;
	private int hVal;
	private int gVal;
	
	public int[][] getPuzzleState() {
		return puzzleState;
	}

	public void setPuzzleState(int[][] puzzleState) {
		this.puzzleState = puzzleState;
	}
	
	public int getHVal() {
		return hVal;
	}
	public void setHVal(int hVal) {
		this.hVal = hVal;
	}
	public int getGVal() {
		return gVal;
	}
	public void setGVal(int gVal) {
		this.gVal = gVal;
	}
	
	public int compareTo(State node) {
		int h = node.getHVal();
		int g = node.getGVal();
	    
	    int costOfNode = h+g;
	    int costOfThis = this.getGVal() + this.getHVal();
	    
	    if (costOfThis <= costOfNode) 
	      return -1;
	    if (costOfThis > costOfNode) 
	      return 1;
	    return 0;
	}
}
