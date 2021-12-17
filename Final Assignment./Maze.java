import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

//Main Driver
public class Maze 
{
    //Setup the size of the of our maze
    public static final int WIDTH = 2000;
    public static final int HEIGHT = WIDTH;
    public static final int W = 20;
    //required for our Swing timer so solver doesn't run with the generator during the same time
    public static int speed = 1;
    public static boolean generated, solved;
    private int columns, rows;
    
    private static final String[] Generator = {"Prim's"};
    private static final String[] Solvers = {" BFS", " DFS", " Dijkstra's"};
    

    public static void main(String[] args) 
    {
        new Maze();
    }

    public Maze() 
    {
        columns = Math.floorDiv(WIDTH, W);
        rows = columns;

        EventQueue.invokeLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } 
                    catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException ex) 
                    {
                        ex.printStackTrace();
                    }
                    GUI();
                }
            });
    }

    private void GUI() 
    {
        JFrame frame = new JFrame();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        frame.setContentPane(container);

        //Creates the top half of the JFrame Window
        MazePanel grid = new MazePanel(rows, columns);
        grid.setBackground(Color.BLACK);
        JPanel mazeBorder = new JPanel();
        final int BORDER_SIZE = 1;
        mazeBorder.setBounds(0, 0, WIDTH + BORDER_SIZE, HEIGHT + BORDER_SIZE);
        mazeBorder.setBackground(Color.BLACK);
        mazeBorder.add(grid);
        container.add(mazeBorder);

        CardLayout cardLayout = new CardLayout();
        JButton runButton = new JButton("Generate");
        JButton solveButton = new JButton("Solve");
        
        JComboBox<String> gen = new JComboBox<>(Generator);
        JComboBox<String> solveMethodsComboBox = new JComboBox<>(Solvers);
       
        // Create the card panels.
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel();
        card1.setLayout(new GridBagLayout());
        card2.setLayout(new GridBagLayout());

        GridBagConstraints x = new GridBagConstraints();;

        //The dropdown menu for the Methods and Prim
        x.insets = new Insets(5, 0, 5, 18);
        x.fill = GridBagConstraints.BOTH;
        x.weightx = 0.7;
        x.gridx = 0;
        x.gridy = 0;
        card2.add(solveMethodsComboBox, x);

        //For location of the run and solve button
        x.gridheight = 2;
        x.weightx = 0.3;
        x.gridx = 1;
        x.gridy = 0;
        card1.add(runButton, x);
        card2.add(solveButton, x);

        // Create the panel that contains the cards.
        JPanel cards = new JPanel(cardLayout);
        cards.setBorder(new EmptyBorder(0, 20, 0, 0));
        cards.setOpaque(false);
        cards.add(card1, "gen");
        cards.add(card2, "solve");

        container.add(cards);   
        
        //Triggers the start of the maze without no generation or solver
        runButton.addActionListener(event -> 
        {
                generated = false;
                solved = false;
                grid.generate(gen.getSelectedIndex());
                cardLayout.next(cards);
            });

        solveButton.addActionListener(event -> 
        {                                    
                 generated = true;
                 grid.solve(solveMethodsComboBox.getSelectedIndex());    
                 cardLayout.last(cards);               
            });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
