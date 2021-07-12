import javax.swing.*;
import java.awt.*;

public class MazeGUI extends JFrame {
    private Maze maze;
    public static final int DefaultMazeSize=5;
    private final int size=1100;
    public MazeGUI()
    {
        setBackground(Color.DARK_GRAY);
        this.maze=new Maze(DefaultMazeSize);
        setTitle("Maze");
        setPreferredSize(new Dimension(size,size));
        setSize(size,size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JLabel("Maze Game"),BorderLayout.NORTH);

        MazePanel mazePanel=new MazePanel(maze);

        ButtonPanel westPanel=new ButtonPanel();
        ButtonAction action=new ButtonAction(maze,mazePanel,westPanel);

        westPanel.setAction(action);
        add(westPanel,BorderLayout.WEST);

        KeyAction kAction = new KeyAction(maze,mazePanel,westPanel);
        addKeyListener(kAction);

        add(mazePanel,BorderLayout.CENTER);

        pack();
    }
}
