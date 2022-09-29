import javax.swing.JFrame;

public class Main {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(900, 700);
        frame.setTitle("Sin(x) Graph ~ RadixCode.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new Plotter());
        frame.setVisible(true);
    }
}