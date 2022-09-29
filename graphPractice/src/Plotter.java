import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import static java.lang.Math.*;
public class Plotter extends Component {
    static int padding = 50;
    static int width = 360;
    static int height = 180;

    int period = 1;
    public void plotSine(Graphics g, int period)
    {
        g.setColor(Color.red);

        for(double x=-width+padding;x<width+padding;x=x+0.01)
        {
            double y = 100 * sin(period*(x+width-padding)*(3.1415926/360));

            int Y = (int)y;
            int X = (int)x;
            g.drawLine(width+X,height-Y,width+X,height-Y);

        }
        g.setColor(Color.black);
    }

    public void paint(Graphics g)
    {

        g.drawLine(padding,height,width * 2 + padding,height); // x-axis
        g.drawLine(padding,0,padding,height * 2); // y-axis

//create a separate loop here for labeling. make an array of chars that you need (0, pi, etc)
//and bump the x every time you need it. repeat for y, in a different loop. Use DrawString

        String[] names = {"0", "\u03c0/2", "\u03c0", "3\u03c0/2", "2\u03c0"};
        int ind = 0;
        String[] maxmin = {"1","-1"};

//        creating horizontal grid
        g.drawLine(padding,height-100,width*2+padding,height-100);
        g.drawString(maxmin[0],padding-25,height-100); //labeling y axis
        g.drawLine(padding,height+100,width*2+padding,height+100);
        g.drawString(maxmin[1],padding-25,height+100); //labeling y axis

        for(int x = padding; x <= (width+padding)*2; x += 180)
        {
            g.drawString(names[ind], x, height+15); //labeling the x axis
            g.drawLine(x,0,x,height * 2 + padding); // vertical grid creation

            ind++;
        }

        plotSine(g,period);

    }

    public static void main(String[] args)
    {

        JFrame frame = new JFrame();
        frame.setSize((width+padding)*2, (height)*2);
        frame.setTitle("Sine Function");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(Color.WHITE);

        Plotter function = new Plotter();

        String input; //getting user input for period of sine function
        //introduce invalid input handling, even though it's not necessary?
        input = JOptionPane.showInputDialog("Please enter a value from 1-10:");

        int repeat = Integer.parseInt(input);

        for(int i = 1; i <= repeat; i++)
        {
            function.period = repeat;
            frame.getContentPane().add(function);

        }

        frame.setVisible(true);


    }

}