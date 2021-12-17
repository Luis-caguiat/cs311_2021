import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.swing.Timer;

public class Dijkstra
{	
	private final Queue<Cell> queue;
	private Cell current;
	private final List<Cell> grid;

	public Dijkstra(List<Cell> grid, MazePanel panel) 
	{
		this.grid = grid;
		queue = new PriorityQueue<Cell>(new DistanceFromGoal());
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
	
	//Main differences in Djkstra and BFS is the priorty queue Djkstra
	private class DistanceFromGoal implements Comparator<Cell> 
	{
		Cell goal = grid.get(grid.size() - 1);
		
		@Override
		public int compare(Cell arg0, Cell arg1) 
		{
			if (getDistanceFromGoal(arg0) > getDistanceFromGoal(arg1)) 
			{
			    
				return 1;
			} else 
			{
				return getDistanceFromGoal(arg0) < getDistanceFromGoal(arg1) ? -1 : 0;
			}
		}
		
		private double getDistanceFromGoal(Cell c) 
		{
			return Math.hypot(c.getX() - goal.getX(), c.getY() - goal.getY());
		}
	}
}