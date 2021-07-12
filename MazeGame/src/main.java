import java.util.Stack;

public class main {

    public static void main(String args[]) throws InterruptedException {


        long t1 = System.currentTimeMillis();

        MazeGUI gui =new MazeGUI();
        gui.setVisible(true);

        long t2 = System.currentTimeMillis();
        System.out.println("Done in: " + (t2-t1));

    }
}
