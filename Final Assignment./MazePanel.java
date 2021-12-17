import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

public class MazePanel extends JPanel 
{
    private final List<Cell> grid = new ArrayList<Cell>();
    private List<Cell> currentCells = new ArrayList<Cell>();

    public MazePanel(int rows, int cols) 
    {
        for (int x = 0; x < rows; x++) 
        {
            for (int y = 0; y < cols; y++) 
            {
                grid.add(new Cell(x, y));
            }
        }
    }

    @Override
    public Dimension getPreferredSize() 
    {       
        return new Dimension(Maze.WIDTH + 1, Maze.HEIGHT+1);
    }

    public void generate(int index) 
    {
            new Prim(grid, this);          
    }
   
    public void solve(int index) 
    {
        switch (index) 
        {       
        case 0:
            new BFS(grid, this);
            break;
        case 2: 
            new DFS(grid, this);
            break;
        case 3:
            new Dijkstra(grid, this);
            break;
        default:
            new Dijkstra(grid, this);
            break;
        }
    }
        
    public void setCurrent(Cell current) 
    {
        if(currentCells.size() == 0) 
        {
            currentCells.add(current);
        } else 
        {
            currentCells.set(0, current);           
        }
    }
    
    public void setCurrentCells(List<Cell> currentCells) 
    {
        this.currentCells = currentCells;
    }
    
    @Override
    public void paint(Graphics a) 
    {
        super.paint(a);
        for (Cell x : grid) 
        {
            x.draw(a);
        }
        for (Cell x : currentCells) 
        {
            if(x != null) x.displayAsColor(a, Color.ORANGE);
        }
        
        grid.get(0).displayAsColor(a, Color.GREEN); //start 
        grid.get(grid.size() - 1).displayAsColor(a, Color.YELLOW); //end
    }
}