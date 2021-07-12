import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MazePanel extends JPanel {
    final int size=900;
    final int MARGIN=10;
    Maze maze;

    public MazePanel(Maze maze)
    {
        setSize(size,size);
        this.maze=maze;
    }

    @Override
    protected void paintComponent(Graphics g) // "Paints" the maze in the panel |N^2
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;//Cast to 2D graphics
        setBackground(Color.white);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));

        int n=maze.getHeight();
        int m=maze.getWidth();
        int width=size/m;//Cell width
        int height=size/n;//Cell height

        drawMaze(g2,n,m,height,width);


        if(!maze.isWin())// If game isn't set
        {
            //Draw player indicator vvv
            Point player = maze.getPlayer();

            drawCircle(g2, player.getX() * width + width / 2, player.getY() * height + height / 2, width / 4, Color.magenta);

        }
        else {
            Stack<Point> path=maze.getSolution();
            if(path.empty())
            {
                g2.setFont(new Font("Ariel", Font.PLAIN, size / 10));
                g2.drawString("YOU WIN!", size / 4, size / 2);
            }
            else
            {
                drawSolution(g2,width,height,maze.getSolution(),Color.green);
            }
        }

        g2.setColor(Color.white);
        g2.drawLine(MARGIN+1, 0, MARGIN+width-1, 0);//draw line to signal the start point
        //g2.drawLine(MARGIN+(size - width), size, MARGIN+size, size);//draw line to signal the ending point
        g2.drawLine(MARGIN+(m-1)*width+1,n*height,MARGIN+m*width-1,n*height);
    }

    private void drawCircle(Graphics2D g,int x,int y,int r,Color color)//draws a circle in the given X,Y (as the center cords)
    {
        if(r==0)
            r=1;
        g.setColor(color);
        x-=(r/2);
        y-=(r/2);
        g.fillOval(MARGIN+x,y,r,r);
    }


    private void drawMaze(Graphics2D g,int n,int m,int height,int width)// Draw a maze on component
    {

        for(int i=0;i<n;i++) {
            for (int j =0; j <m; j++)
            {
                Cell[][] grid=maze.getGrid();
                if(grid[i][j].getWall(Maze.UP))
                    g.drawLine(MARGIN+(j*width),i*height,MARGIN+((1+j)*width),i*height);//Draw upper wall

                if(grid[i][j].getWall(Maze.DOWN))
                    g.drawLine(MARGIN+j*width,(i+1)*height,MARGIN+(1+j)*width,(i+1)*height);//Draw bottom wall

                if(grid[i][j].getWall(Maze.LEFT))
                    g.drawLine(MARGIN+ (j * width), i * height,  MARGIN+(j * width), (i + 1) * height);//Draw left wall

                if(grid[i][j].getWall(Maze.RIGHT))
                    g.drawLine(MARGIN+((1 + j) * width), i * height, MARGIN+((1 + j) * width), (i + 1) * height);//Draw right wall
            }
        }
    }

    private void drawSolution(Graphics2D g,int width,int height,Stack<Point> path,Color c)
    {
        g.setColor(c);
        while(!path.empty())
        {
            Point p=path.pop();
            if(!path.empty())
            {
                g.drawLine(MARGIN+p.getX()*width+width/2,p.getY()*height+height/2,MARGIN+path.peek().getX()*width+width/2,path.peek().getY()*height+height/2);//draw line between two cells
            }
        }
    }
}
