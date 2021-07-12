import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    JButton newMazeBtn;
    JTextField textField;
    JLabel errorLbl;
    JButton clearTextBtn;
    JButton solveMazeBtn;
    public ButtonPanel()
    {
        setLayout(new GridLayout(30,0));

        Label lbl=new Label("Enter maze size:");
        add(lbl);
        textField=new JTextField("");
        textField.setFocusable(false);
        add(textField);

        newMazeBtn = new JButton("New Maze");
        newMazeBtn.setFocusable(false);

        errorLbl=new JLabel();
        add(errorLbl);
        add(newMazeBtn);
        add(new JLabel());

        clearTextBtn=new JButton("Clear");
        clearTextBtn.setFocusable(false);
        add(clearTextBtn);

        errorLbl=new JLabel();
        add(errorLbl);
        add(newMazeBtn);
        add(new JLabel());

        solveMazeBtn=new JButton("Solve Maze");
        solveMazeBtn.setFocusable(false);
        add(solveMazeBtn);
    }

    public JLabel getErrorLbl() {
        return errorLbl;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setAction(ButtonAction action) //set action listener to the relevant components in the panel
    {
        newMazeBtn.addActionListener(action);
        clearTextBtn.addActionListener(action);
        solveMazeBtn.addActionListener(action);
    }
}
