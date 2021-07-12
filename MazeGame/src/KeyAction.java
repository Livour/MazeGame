import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAction implements KeyListener {
    MazePanel panel;
    Maze maze;
    ButtonPanel bPanel;

    public KeyAction(Maze maze,MazePanel panel,ButtonPanel bPanel)
    {
        this.bPanel=bPanel;
        this.maze=maze;
        this.panel=panel;
    }
    @Override
    public void keyTyped(KeyEvent e) { /*not using this func*/}

    @Override
    public void keyPressed(KeyEvent e) {
        {
        switch (e.getKeyCode())//Get the pressed key code from keyboard input and act accordingly
        {

            case KeyEvent.VK_UP://if pressed key is upwards arrow
            {
                if(!maze.isWin()) {
                    maze.Move(Maze.UP);
                    panel.repaint();
                }
                break;
            }
            case KeyEvent.VK_DOWN://if pressed key is downwards arrow
            {
                if(!maze.isWin())

                {
                    maze.Move(Maze.DOWN);
                    panel.repaint();
                }
                break;
            }
            case KeyEvent.VK_LEFT://if pressed key is left arrow
            {
                if(!maze.isWin()) {
                    maze.Move(Maze.LEFT);
                    panel.repaint();
                }
                break;
            }
            case KeyEvent.VK_RIGHT://if pressed key is right arrow
            {
                if(!maze.isWin()) {
                    maze.Move(Maze.RIGHT);
                    panel.repaint();
                }
                break;
            }
            //if a number was pressed, add it to the bPanel's text field
            case KeyEvent.VK_0:
            case KeyEvent.VK_1:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_4:
            case KeyEvent.VK_5:
            case KeyEvent.VK_6:
            case KeyEvent.VK_7:
            case KeyEvent.VK_8:
            case KeyEvent.VK_9:
            {
                JTextField textField=this.bPanel.getTextField();
                int value=(e.getKeyCode()+2)%10;
                textField.setText(textField.getText()+value);
                break;
            }
            default:
            {

            }
        }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {/*not using this func*/}
}
