/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
// By Veronica Taira (CS2)
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        Stack<MazeCell> solution = new Stack<MazeCell>();
        MazeCell current = maze.getEndCell();
        while (current != maze.getStartCell()){
            solution.push(current);
            current = current.getParent();
        }
        solution.push(current);
        int size = solution.size();
        ArrayList<MazeCell> orderedSolution = new ArrayList<>();
            for(int i = 0; i < size; i++){
                orderedSolution.add(solution.pop());
            }
        return orderedSolution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Creates a stack containing cells that have been explored, but not visited yet
        Stack<MazeCell> cellsToVisit = new Stack<MazeCell>();
       // Sets the starting cell as the current cell
        MazeCell current = maze.getStartCell();
       // Pushes the current cell into cellsToVisit, and sets it as explored
        cellsToVisit.push(current);
        current.setExplored(true);

        // While the current cell is not the end cell
        while(current != maze.getEndCell()){
            // For each neighbor that's been found by searchHelper
            for(MazeCell cell : searchHelper(current)){
               // Add the neighbor to cellsToVisit
               // Set the neighbor cell as explored, and set the parent as the current cell
                cellsToVisit.push(cell);
                cell.setExplored(true);
                cell.setParent(current);
            }
            // Changes current cell to next cell in Stack
            current = cellsToVisit.pop();
        }
        // Returns the DFS solution to the maze
        return getSolution();
    }

    // Helper function that finds all of a cell's neighbors
    public ArrayList<MazeCell> searchHelper (MazeCell current){
       // Stores all the valid neighbors of the cell
        ArrayList<MazeCell> surroundingCells = new ArrayList<>();

        // Checks if there's a valid cell north of the current cell.
        if(maze.isValidCell(current.getRow() -1, current.getCol())){
            // If it's valid, adds it to surroundingCells.
            surroundingCells.add(maze.getCell(current.getRow() -1, current.getCol()));
        }
       // Checks if the east neighbor is valid
        if(maze.isValidCell(current.getRow(), current.getCol() + 1)){
           // If it is valid, add it to surroundingCells
            surroundingCells.add(maze.getCell(current.getRow(), current.getCol() + 1));
        }
        // Checks if the south neighbor is valid
        if(maze.isValidCell(current.getRow() + 1, current.getCol())){
            // Adds it to surroundingCells
            surroundingCells.add(maze.getCell(current.getRow() + 1, current.getCol()));
        }
        // Checks if the west neighbor is valid
        if(maze.isValidCell(current.getRow(), current.getCol() - 1)){
            // Adds it to surroundingCells
            surroundingCells.add(maze.getCell(current.getRow(), current.getCol() - 1));
        }
        // Returns the ArrayList of valid neighbors
        return surroundingCells;
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> toVisit = new LinkedList<MazeCell>();
        MazeCell currentCell = maze.getStartCell();
        toVisit.add(currentCell);
        currentCell.setExplored(true);

        while(currentCell != maze.getEndCell()){
            for(MazeCell cell : searchHelper(currentCell)){
                toVisit.add(cell);
                cell.setParent(currentCell);
                cell.setExplored(true);
            }
            currentCell = toVisit.remove();
        }

        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
