import java.util.*;

public class Maze {
    public static final int RIGHT = 0 ;
    public static final int DOWN = 1 ;
    public static final int LEFT = 2 ;
    public static final int UP = 3 ;
    private final int neigh[][]= {{0,1},{1,0},{0,-1},{-1,0}};//0=Right,1=Down,2=Left,3=Up

    private int height;
    private int width;
    private Cell[][] grid;//Maze's grid
    private Point player;//The player's current position
    private boolean win;//is win state
    private Stack<Point> solution;//Stack that contains the solution of the maze

    public Maze(int size) {//| O(n^2)
        this.width=size;
        this.height=size;
        generateMaze();
    }

    public void prepareMaze()//Initialize the maze grid | O(n^2)
    {
        solution=new Stack<Point>();
        win=false;
        player=new Point(0,0);
        grid =new Cell[height][width];
        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++)
                grid[i][j]=new Cell();
    }
    public void generateMaze()//generate a random maze - Using depth-first search | O(n^2)
    {
        prepareMaze();
        Stack<Point> pathStack=new Stack<Point>();
        Random rng=new Random();
        Point current;
        boolean checked [][]=new boolean[height][width];


        for(boolean[] row:checked)
            Arrays.fill(row,false);//initialize as not checked

        Point p=new Point(0,0);//Starting point 0,0
        checked[p.getY()][p.getX()]=true;//mark the starting cell as visited
        pathStack.push(p);//push current point to the stack

        while(!pathStack.empty())
        {
            current=pathStack.pop();//Make the top of the stack our "current" cell

            if(anyNonCheckedNeigh(checked,current))//if current has any unchecked neighbors
            {
                pathStack.push(current);
                int randNeigh[]=initNShuffleNeigh();//Init the array with the numbers 0-3 in a random order

                for(int side :randNeigh)//check any neighbor
                {
                    int indexY=current.getY()+neigh[side][0];
                    int indexX=current.getX()+neigh[side][1];
                    boolean inBounds = ((indexX >= 0) && (indexX < grid.length))&&((indexY >= 0) && (indexY < grid[0].length));//True if the random neighbor is inside the grid's bounds
                    if(inBounds&&!isCheckedNeighbor(checked,current,side))//if neighbor isn't checked and is in bounds
                    {
                        p=new Point(indexX,indexY);//recycle usage of p to push the new chosen cell to the stack
                        breakWall(current,side);
                        checked[indexY][indexX]=true;
                        pathStack.push(p);
                        break;
                    }
                }
            }

        }

    }

    public void solveMaze()//reaches the end of the maze and puts the solution path in the solution stack of the maze | O(n^2)
    {
      //  Stack<Point> path=new Stack<Point>();
        boolean checked [][]=new boolean[height][width];

        for(boolean[] row:checked)
            Arrays.fill(row,false);//initialize as not checked

       // path.push(new Point(0,0));//starting point
        solution.push(new Point(0,0));//starting point

        solveMaze(checked);

        settleGame();//signal that the game has ended
        //return path;
    }

    private  boolean solveMaze(boolean[][] checked)//recursive method that searches the end of the maze - Using recursive backtracker | O(n^2)
    {
        Point current=solution.peek();
        boolean isRightWay;
        checked[current.getY()][current.getX()]=true;


        if(current.getY()==height-1&&current.getX()==width-1)//If current reached the end point
        {
            return true;
        }
        if(anyNonCheckedNeigh(checked,current))//if current has any unchecked neighbors
        {
            for(int i=0;i<4;i++)
            {
                int indexY=current.getY()+neigh[i][0];
                int indexX=current.getX()+neigh[i][1];
              //  boolean inBounds = ((indexY >= 0) && (indexY < grid.length))&&((indexX >= 0) && (indexX < grid[0].length));//True if the random neighbor is inside the grid's bounds
                boolean inBounds = ((indexX >= 0) && (indexX < grid.length))&&((indexY >= 0) && (indexY < grid[0].length));
                if(inBounds)//If neighbor is in bounds of maze grid
                {
                    if (!grid[current.getY()][current.getX()].getWall(i) && !isCheckedNeighbor(checked, current, i))//If the Neighbor isn't checked and there isn't a wall separating the cells
                    {
                        solution.push(new Point(indexX, indexY));
                        isRightWay = solveMaze(checked);//recursion call to check if neighbor's method call reached the end of the maze

                        if (isRightWay)//if neighbor reached the end of the maze return true
                            return true;
                    }
                }
            }
        }
        solution.pop();//if couldn't find a neighbor that reached the end of the maze, pop the stack and return false
        return false;
    }


    private boolean isCheckedNeighbor(boolean checked [][], Point current, int side)//checks if a neighbor of a give cell is checked, returns true if checked,else false. 0=Right,1=Down,2=Left,3=Up - Not checking if neighbor exists | O(1).
    {
        return checked[current.getY()+neigh[side][0]][current.getX()+neigh[side][1]];
    }


    private boolean anyNonCheckedNeigh(boolean checked [][],Point current)//checks if the given cell has any unchecked neighbors(at least 1) | O(1)
    {

        boolean flag=false;
        for (int i=0;i<4;i++)
        {
            int indexY=current.getY()+neigh[i][0];
            int indexX=current.getX()+neigh[i][1];
            boolean inBounds = ((indexY >= 0) && (indexY < grid.length))&&((indexX >= 0) && (indexX < grid[0].length));
            if(inBounds)
              if(!isCheckedNeighbor(checked,current,i)) {
                 return true;
              }
        }
        return false;
    }


    private void breakWall(Point p,int side)//"Break" the wall between the given cell and a chosen neighbor. 0=Right,1=Down,2=Left,3=Up - Not checking if neighbor exists | O(1)
    {
        int currentY= p.getY(),currentX= p.getX(),nextX,nextY;
        grid[currentY][currentX].setWall(false,side);
        nextY=p.getY()+neigh[side][0];
        nextX= p.getX()+neigh[side][1];
        grid[nextY][nextX].setWall(false,(side+2)%4);// The evaluation of "(side+2)%4" gives me the wall of the neighbor which is facing to the current cell's direction
    }


    private int[] initNShuffleNeigh()//returns an array of all neighbors(0-3) in a random order - using "Fisher–Yates shuffle" - O(1)
    {
        Random rng=new Random();
        int arr[]={0,1,2,3};
        for(int i=arr.length;i>0;)
        {
            int j=rng.nextInt(i--);
            int temp=arr[j];
            arr[j]=arr[i];
            arr[i]=temp;
        }
        return arr;
    }

    public void Move(int side)//Perform a move - change the player's position according to the desirable side if possible
    {
        if(player.getX()+1==width&&player.getY()+1==height&&side==DOWN)
            settleGame();

        if(CanPerform(side))
        {
            player.setY(player.getY()+neigh[side][0]);
            player.setX(player.getX()+neigh[side][1]);
        }
    }
    public Boolean CanPerform(int side)//returns true if a move can be performed, if it cannot return false | O(1)
    {
        return !grid[player.getY()][player.getX()].getWall(side);
    }

    public int getHeight() {
        return height;
    } // | O(1)

    public int getWidth() {
        return width;
    } // | O(1)

    public Cell[][] getGrid() {
        return grid;
    }  // | O(1)

    public Point getPlayer() {
        return player;
    }// | O(1)

    private void settleGame()
    {
        win=true;
    }// | O(1)

    public boolean isWin() {
        return win;
    }// | O(1)

    public void SetSize(int size)// | O(n^2)
    {
        this.width=size;
        this.height=size;
        generateMaze();
    }

    public Stack<Point> getSolution() {
        return solution;
    }// | O(1)

    @Override
    public String toString() //| O(N^2)
    {
        String str="";
        for(Cell[] row : grid)
        {
            for(Cell single:row) {
                if (single.walls[0] && single.walls[1] && single.walls[2] && single.walls[3])
                    str+=".";
                else
                if (single.walls[0] && !single.walls[1] && !single.walls[2] && !single.walls[3])
                    str+="┤";
                else
                if (!single.walls[0] && single.walls[1] && !single.walls[2] && !single.walls[3])
                    str+="┴";
                else
                if (!single.walls[0] && !single.walls[1] && single.walls[2] && !single.walls[3])
                    str+="┝";
                else
                if (!single.walls[0] && !single.walls[1] && !single.walls[2] && single.walls[3])
                    str+="┬";
                else
                if (!single.walls[0] && !single.walls[1] && single.walls[2] && single.walls[3])
                    str+="┌";
                else
                if (single.walls[0] && !single.walls[1] && !single.walls[2] && single.walls[3])
                    str+="┐";
                else
                if (!single.walls[0] && single.walls[1] && single.walls[2] && !single.walls[3])
                    str+="└";
                else
                if (single.walls[0] && single.walls[1] && !single.walls[2] && !single.walls[3])
                    str+="┘";
                else
                if (single.walls[0] && !single.walls[1] && single.walls[2] && !single.walls[3])
                    str+="|";
                else
                if (!single.walls[0] && single.walls[1] && !single.walls[2] && single.walls[3])
                    str+="─";
                else
                if (single.walls[0] && single.walls[1] && !single.walls[2] && single.walls[3])
                    str+="─";
                else
                if (single.walls[0] && single.walls[1] && single.walls[2] && !single.walls[3])
                    str+="|";
                else
                if (!single.walls[0] && single.walls[1] && single.walls[2] && single.walls[3])
                    str+="─";
                else
                if (single.walls[0] && !single.walls[1] && single.walls[2] && single.walls[3])
                    str+="|";
                else
                    str+="┼";

            }
            str+="\n";
        }
        return str;
    }

}
