package gad.maze;

public class Walker {

	private boolean[][] maze;
	private int mazeWidth;
	private int mazeHeight;
	private Result result;
	private int posX;
	private int posY;
	private Direction direction;

	private enum Direction {
		north,
		east,
		south,
		west,
	}

	public Walker(boolean[][] maze, Result result) {
		this.maze = maze;
		this.result = result;

		mazeWidth = maze.length;
		mazeHeight = maze[0].length;
	}

	public boolean walk() {
		posX = 1;
		posY = 0;
		direction = Direction.south;
		doReport();

		while(true) {
			if(checkRight()) { //try go forward
				if(checkFront()) { //turn left
					int newDir = direction.ordinal()-1;
					direction = Direction.values()[newDir<0?3:newDir];
				}
				else { //go forward
					goForward();
				}
			}
			else { //turn right
				direction = Direction.values()[(direction.ordinal()+1)%4];
				goForward();
			}

			if(posX == mazeWidth-1 && posY == mazeHeight-2) return true;
			if(posX == 1 && posY == 0) return false;
		}
	}

	private void doReport() {
		result.addLocation(posX,posY);
	}

	//returns true if wall is on the right hand side of the walker
	private boolean checkRight() {
		int x = posX;
		int y = posY;

		switch (direction){
			case north -> x++;
			case east -> y++;
			case south -> x--;
			case west -> y--;
		}

		if(x<0||x>=mazeWidth||y<0||y>=mazeHeight) return true;

		return maze[x][y];
	}

	//returns true if wall is in front of walker
	private boolean checkFront() {
		int x = posX;
		int y = posY;

		switch (direction){
			case north -> y--;
			case east -> x++;
			case south -> y++;
			case west -> x--;
		}

		if(x<0||x>=mazeWidth||y<0||y>=mazeHeight) return true;

		return maze[x][y];
	}

	private void goForward() {
		switch (direction){
			case north -> posY--;
			case east -> posX++;
			case south -> posY++;
			case west -> posX--;
		}
		doReport();
	}

	public static void main(String[] args) {
		boolean[][] maze = Maze.generateStandardMaze(3, 5);
		StudentResult result = new StudentResult();
		Walker walker = new Walker(maze, result);
		System.out.println(walker.walk());
		Maze.draw(maze, result);
	}
}
