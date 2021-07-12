import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonAction implements ActionListener {
    private Maze maze;
    JPanel panel;
    ButtonPanel bPanel;
    public ButtonAction(Maze maze,MazePanel panel,ButtonPanel bPanel)
    {
        this.bPanel=bPanel;
        this.panel=panel;
        this.maze=maze;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JTextField text=bPanel.getTextField();
        if(e.getActionCommand().equals("New Maze"))
        {
            JLabel error=bPanel.getErrorLbl();
            if(!text.getText().isEmpty())
            {
                try {
                    int size = Integer.parseInt(bPanel.getTextField().getText());
                    if(size<=100)
                    {
                        if(size!=0)
                        {
                            maze.SetSize(size);
                            error.setText("");
                            panel.repaint();
                        }
                        else
                            error.setText("Size must be higher than 0");
                    }
                    else
                        error.setText("Size may not exceed 100 ");

                } catch (NumberFormatException exe) {
                    bPanel.getErrorLbl().setText("Size must be a number");
                }
                text.setText("");
            }
            else
                {
                maze.SetSize(MazeGUI.DefaultMazeSize);
                panel.repaint();
            }
        }
        else
            if(e.getActionCommand().equals("Clear"))
            {
                text.setText("");
            }
            else
                if(e.getActionCommand().equals("Solve Maze"))
                {
                    maze.solveMaze();
                    panel.repaint();
                }

    }
}
