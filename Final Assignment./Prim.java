import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.util.Collections;

public class Prim 
{       
    private final List<Cell> grid;
    private final List<Cell> frontier = new ArrayList<Cell>();
    private Cell current;

    public Prim(List<Cell> grid, MazePanel panel) 
    {
        this.grid = grid;
        current = grid.get(0);
        final Timer timer = new Timer(Maze.speed, null);
        
        timer.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (!grid.parallelStream().allMatch(x -> x.getVisited())) 
                {
                    carve();
                } else 
                {
                    current = null;
                    Maze.generated = true;
                    timer.stop();
                }
                panel.setCurrent(current);
                panel.repaint();
                timer.setDelay(Maze.speed);
            }
        });
        timer.start();
    }
    
    private void carve() 
    {
        current.setVisited(true);        
        List<Cell> neighbor = current.getUnvisitedList(grid);
        frontier.addAll(neighbor);
        Collections.shuffle(frontier);
        
        current = frontier.get(0);
        
        List<Cell> inNeighbor = current.getAllNeighbors(grid);
        inNeighbor.removeIf(x -> !x.getVisited());
        
        if (!inNeighbor.isEmpty()) 
        {
            Collections.shuffle(inNeighbor);
            current.removeWall(inNeighbor.get(0));
        }
        
        frontier.removeIf(x -> x.getVisited());
    }
}