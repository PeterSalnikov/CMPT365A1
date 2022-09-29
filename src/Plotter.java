import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Math.*;
public class Plotter extends Component {
    static int padding = 50;
    static int width = 360;
    static int height = 360;

    static int rate = width/width; //for 10Hz sampling rate, width / 10 and so on
    static double pi = Math.PI;
    static int scale = 200; // scale for amplitude of plot

    int period = 1;
    public void plotSine(Graphics g, int period) {

        int r, gr, b;

        Random rand = null;
        for (int i = 1; i <= period; i++) {
            rand = new Random();
            r = rand.nextInt(255);
            gr = rand.nextInt(255);
            b = rand.nextInt(255);
            g.setColor(new Color(r, gr, b));

            for (double x = -width + padding; x < width + padding; x += rate) {
                double y = scale * sin(i * (x + width - padding) * (pi / width));

                int Y = (int) y;
                int X = (int) x;
                g.drawLine(width + X, height - Y, width + X, height - Y);

            }
        }
        r = rand.nextInt(255);
        gr = rand.nextInt(255);
        b = rand.nextInt(255);
        g.setColor(new Color(r, gr, b));

        for (double x = -width + padding; x < width + padding; x += rate) {
            int Y = 0;
            int X = 0;
            double y = 0;
            for (int i = 1; i <= period; i++) {

                y += scale * sin(i * (x + width - padding) * (pi / width));
                Y = (int) y;
                X = (int) x;
            }
            g.drawLine(width + X, height - Y, width + X, height - Y);

        }
        g.setColor(Color.black);
    }

    public void paint(Graphics g)
    {
        //two for loops to draw horizontal grid and label the y-axis
        for(int i = height; i < height*2; i += scale)
        {
            g.drawLine(padding,i,width*2+padding,i);
            if(i != height) //ignore double zero plotting
                g.drawString("-"+(i - height)/scale,20,i);
        }
        for(int i = height; i > 0; i -= scale)
        {
            g.drawLine(padding, i, width * 2 + padding, i);
            g.drawString(""+-1*(i - height)/scale,30,i);
        }

        g.drawLine(padding,height,width * 2 + padding,height); // x-axis
        g.drawLine(padding,0,padding,height * 2); // y-axis

//create a separate loop here for labeling. make an array of chars that you need (0, pi, etc)
//and bump the x every time you need it. repeat for y, in a different loop. Use DrawString

        String[] names = {"0", "\u03c0/2", "\u03c0", "3\u03c0/2", "2\u03c0"};
        int ind = 0;


        for(int x = padding; x <= (width+padding)*2; x += 180)
        {
            g.drawString(names[ind], x, height+15); //labeling the x axis
            g.drawLine(x,0,x,height * 2 + padding); // vertical grid creation

            ind++;
        }

//        for(int i = 1; i <= period; i++)
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