import javax.swing.*;
import java.awt.*;

public class CellCountMain
{

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        JFrame frame = new JFrame();
        frame.add(new CellCountPanel());
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
