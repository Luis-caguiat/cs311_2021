 
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS
{
	
	private final Queue<Cell> queue = new LinkedList<Cell>();
	private Cell current;
	private final List<Cell> grid;

	public BFS(List<Cell> grid, MazePanel panel) 
	{
		this.grid = grid;
		current = grid.get(0);
		current.setDistance(0);
		queue.offer(current);
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (!current.equals(grid.get(grid.size() - 1))) 
				{
					flood();
				} else {
					drawPath();
					Maze.solved = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();
				timer.setDelay(Maze.speed);
			}
		});
		timer.start();
	}
	
	private void flood() 
	{
		current.setDE(true);
		current = queue.poll();
		List<Cell> adjacentCells = current.getValidMoveNeighbors(grid);
		for (Cell c : adjacentCells) 
		{
			if (c.getDistance() == -1) 
			{
				c.setDistance(current.getDistance() + 1);
				c.setParent(current);
				queue.offer(c);
			}
		}
	}
	
	private void drawPath() 
	{
		while (current != grid.get(0)) 
		{
			current.setPath(true);
			current = current.getParent();
		}
	}
}