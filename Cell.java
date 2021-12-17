import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cell 
{
    private int x, y, distance, id;    
    private Cell parent;    
    private boolean visited = false;
    private boolean path = false;
    private boolean DE = false;
    
    private boolean[] walls = {true, true, true, true};

    public boolean[] getWall() 
    {
        return walls;
    }
    public void setWall(boolean[] walls) 
    {
        this.walls = walls;
    }
    public Cell(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.distance = -1;
    }    
    public int getX() 
    {
        return x;
    }    
    public int getY() 
    {
        return y;
    }    
    public int getId() 
    {
        return id;
    }
    public void setId(int id) 
    {
        this.id = id;
    }    
    public boolean getVisited() 
    {
        return visited;
    }    
    public void setVisited(boolean visited) 
    {
        this.visited = visited;
    }    
    public boolean getDE() 
    {
        return DE;
    }    
    public void setDE(boolean DE) 
    {
        this.DE = DE;
    }
    public boolean isPath() 
    {
        return path;
    }
    public void setPath(boolean path) 
    {
        this.path = path;
    }    
    public int getDistance() 
    {
        return distance;
    }
    public void setDistance(int distance) 
    {
        this.distance = distance;
    }
    public Cell getParent() 
    {
        return parent;
    }    
    public void setParent(Cell parent) 
    {
        this.parent = parent;
    }
    
    public void draw(Graphics g) 
    {
        int v = x * Maze.W;
        int w = y * Maze.W;
        
        //colors the maze when visited and also highlights path and deadend
        if (visited) 
        {
            g.setColor(Color.GRAY);
            g.fillRect(v, w, Maze.W, Maze.W);
        }       
        if (path) 
        {
            g.setColor(Color.BLUE);
            g.fillRect(v, w, Maze.W, Maze.W);
        } else if (DE) 
        {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(v, w, Maze.W, Maze.W);
        }
        
        //Creates the walls of our maze
        g.setColor(Color.WHITE);
        if (walls[0]) 
        {
            g.drawLine(v, w, v+Maze.W, w);
        }
        if (walls[1]) 
        {
            g.drawLine(v+Maze.W, w, v+Maze.W, w+Maze.W);
        }
        if (walls[2]) 
        {
            g.drawLine(v+Maze.W, w+Maze.W, v, w+Maze.W);
        }
        if (walls[3]) 
        {
            g.drawLine(v, w+Maze.W, v, w);
        } 
    }
    
    public void displayAsColor(Graphics g, Color color) 
    {
        int v = x * Maze.W;
        int w = y * Maze.W;
        g.setColor(color);
        g.fillRect(v, w, Maze.W, Maze.W);
    }
    
    //Removes the walls in the cells
    public void removeWall(Cell next) 
    {
        // top 0, right 1, bottom 2, left 3  
        int x = this.x - next.x;               
        if(x==1) 
        {
            walls[3] = false;
            next.walls[1] = false;
        } else if (x == -1) 
        {
            walls[1] = false;
            next.walls[3] = false;
        }        

        int y = this.y - next.y;                
        if(y==1)
        {            
            walls[0] = false;
            next.walls[2] = false;
        } 
        else if (y == -1) 
        {
            walls[2] = false;
            next.walls[0] = false;
        }
    }
   
    //Generates next cell to move to
    private Cell randomNeighbor(List<Cell> neighbor) 
    {
        if (neighbor.size() > 0) 
        {
            return neighbor.get(new Random().nextInt(neighbor.size()));
        } else {
            return null;
        }
    }   
    //checks if next neighbor is in grid and is able to move
    private Cell checkInGridBounds(List<Cell> grid, Cell neighbour) 
    {
        if (grid.contains(neighbour)) 
        {
            return grid.get(grid.indexOf(neighbour));
        } else {
            return null;
        }
    }
    
    public Cell getTop(List<Cell> grid) 
    {
        return checkInGridBounds(grid, new Cell(x, y - 1));
    }    
    public Cell getRight(List<Cell> grid) 
    {
        return checkInGridBounds(grid, new Cell(x + 1, y));
    }    
    public Cell getBottom (List<Cell> grid) 
    {
        return checkInGridBounds(grid, new Cell(x, y + 1));
    }    
    public Cell getLeft(List<Cell> grid) 
    {
        return checkInGridBounds(grid, new Cell(x - 1, y));
    }
   
    //Creates list for all the unvisited neighbor
    public Cell getUnvisitedNeighbor(List<Cell> grid) 
    {        
        List<Cell> neighbor = getUnvisitedList(grid);        
        if (neighbor.size() ==  1) 
        {
            return neighbor.get(0);
        }
        return randomNeighbor(neighbor);
    }
    
    public List<Cell> getUnvisitedList(List<Cell> grid) 
    {
        
        List<Cell> neighbor = new ArrayList<Cell>(4);        
        Cell top = checkInGridBounds(grid, new Cell(x, y - 1));
        Cell right = checkInGridBounds(grid, new Cell(x + 1, y));
        Cell bottom = checkInGridBounds(grid, new Cell(x, y + 1));
        Cell left = checkInGridBounds(grid, new Cell(x - 1, y));
        
        if (top != null) if(!top.visited) neighbor.add(top);
        if (right != null) if(!right.visited) neighbor.add(right);
        if (bottom != null)if(!bottom.visited) neighbor.add(bottom);
        if (left != null) if(!left.visited)neighbor.add(left);
        
        return neighbor;
    }
    
    // no walls between
    public List<Cell> getValidMoveNeighbors(List<Cell> grid) 
    {
        List<Cell> neighbor = new ArrayList<Cell>(4);
        
        Cell top = checkInGridBounds(grid, new Cell(x, y - 1));
        Cell right = checkInGridBounds(grid, new Cell(x + 1, y));
        Cell bottom = checkInGridBounds(grid, new Cell(x, y + 1));
        Cell left = checkInGridBounds(grid, new Cell(x - 1, y));
        
        if (top != null) 
        {
            if (!walls[0]) neighbor.add(top);
        }           
        if (right != null) 
        {
            if (!walls[1]) neighbor.add(right);
        }        
        if (bottom != null) 
        {
            if (!walls[2]) neighbor.add(bottom);
        }        
        if (left != null) 
        {
            if (!walls[3]) neighbor.add(left);
        }
        
        return neighbor;
    }
    
    //For DFS to find possible path
    public Cell getPathNeighbor(List<Cell> grid) 
    {
        List<Cell> neighbor = new ArrayList<Cell>();
        
        Cell top = checkInGridBounds(grid, new Cell(x, y - 1));
        Cell right = checkInGridBounds(grid, new Cell(x + 1, y));
        Cell bottom = checkInGridBounds(grid, new Cell(x, y + 1));
        Cell left = checkInGridBounds(grid, new Cell(x - 1, y));
        
        if (top != null) 
        {
            if (!top.DE) 
            {
                if (!walls[0]) neighbor.add(top);
            }
        }        
        if (right != null) 
        {
            if (!right.DE) 
            {
                if (!walls[1]) neighbor.add(right);
            }
        }        
        if (bottom != null) 
        {
            if (!bottom.DE) 
            {
                if (!walls[2]) neighbor.add(bottom);
            }
        }        
        if (left != null) 
        {
            if (!left.DE) {
                if (!walls[3]) neighbor.add(left);
            }
        }        
        if (neighbor.size() ==  1) 
        {
            return neighbor.get(0);
        }
        
        return randomNeighbor(neighbor);
    }
    
    public List<Cell> getAllNeighbors(List<Cell> grid) 
    {
        List<Cell> neighbor = new ArrayList<Cell>();
        
        Cell top = checkInGridBounds(grid, new Cell(x, y - 1));
        Cell right = checkInGridBounds(grid, new Cell(x + 1, y));
        Cell bottom = checkInGridBounds(grid, new Cell(x, y + 1));
        Cell left = checkInGridBounds(grid, new Cell(x - 1, y));
        
        if (top != null) neighbor.add(top);
        if (right != null) neighbor.add(right);
        if (bottom != null) neighbor.add(bottom);
        if (left != null) neighbor.add(left);
        
        return neighbor;
    }
        
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}