import javax.swing.*;
import java.awt.*;
import java.util.Random;


import static java.lang.Math.*;
public class Plotter extends Component {
//    variables made to simplify scaling, parameterization
    static int padding = 50;
    static int width = 360;
    static int height = 360;

    static double rate = 0.01; //for 10Hz sampling rate, width / 10 and so on
    static double pi = Math.PI;
    static int scale = 60; // scale for amplitude of plot; default is 100

    int period = 1;

    public void randColor(Graphics g)
    {
        //lower and upper color bounds to prevent colors that are too dark or too light
        int lower = 50;
        int upper = 205;
        int r, gr, b;
        Random rand = new Random();
        r = rand.nextInt(lower)+rand.nextInt(upper);
        gr = rand.nextInt(lower)+rand.nextInt(upper);
        b = rand.nextInt(lower)+rand.nextInt(upper);
        g.setColor(new Color(r, gr, b));
    }

    public void plotSine(Graphics g, int period) {

        for (int i = 1; i <= period; i++) {

            randColor(g);

            for (double x = -width + padding; x < width + padding; x += rate)
            {
                double y = scale * sin(i * (x + width - padding) * (pi / width));

                int Y = (int) y;
                int X = (int) x;
                g.drawLine(width + X, height - Y, width + X, height - Y);
//uncomment to highlight 10Hz points of sample
//                if( ((int)x-padding) % (width/10) == 0)
//                {
//                    g.drawRect(width+X-5,height-Y-5,10,10);
//                }

            }
        }
        randColor(g);

        for (double x = -width + padding; x < width + padding; x += rate) {
            int Y = 0;
            int X = 0;
            double y = 0;
            for (int i = 1; i <= period; i++) {

                y += scale * sin(i * (x + width - padding) * (pi / width));
                Y = (int) y;
                X = (int) x;

            }
//            uncomment this for key points on the summed sine graph
//            if( ((int)x - padding) % (width/10) == 0)
//            {
//                g.drawRect(width+X-5,height-Y-5,10,10);
//
//            }
            g.drawLine(width + X, height - Y, width + X, height - Y);

        }
        g.setColor(Color.black);
    }

    public void plotQuant(Graphics g, int period)
    {
        randColor(g);
        int frequency = width/10; // a 10Hz frequency, relative to the width
        int bitrate = 4; // with 2 bits, 4 values can be represented

        int step = 2*scale / bitrate; //gives the step depending on the scale of the graph
//        System.out.println(step);
        int sign; //to save sign of value after absolution
        double quant;

        for (double x = -width + padding; x <= width + padding; x += frequency)
        {
            double y = scale * sin(period * (x + width - padding) * (pi / width));

            int X = (int) x;

            if(y < 0)
                sign = -1;
            else
                sign = 1;

            y = abs(y);

            if( y % step == 0 ) //need this as quant would not work if it's already a multiple of the scale
                quant = y;
            else {
                quant = y + step - y % step;
            }
//            System.out.println(quant);

            int val = (int)quant * sign;

            g.drawLine(width + X, height, width + X, height - val);
            g.drawRect(width+X-3,height-val-3,6,6);
//            System.out.println(val);
        }
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

//create a separate loop here for labeling. make an array of strings that you need (0, pi, etc)
//\u03c0 is unicode for pi. I hardcoded this as the assignment only requires up to 2*pi, though the y axis
//is scalable.
        String[] names = {"0", "\u03c0/2", "\u03c0", "3\u03c0/2", "2\u03c0"};
        int ind = 0;

        for(int x = padding; x <= (width+padding)*2; x += 180)
        {
            g.drawString(names[ind], x, height+15); //labeling the x axis
            g.drawLine(x,0,x,height * 2 + padding); // vertical grid creation

            ind++;
        }
            plotSine(g,period);
            plotQuant(g,period);
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