package corridor;

import javax.swing.*;
import java.awt.*;

public class CorridorEditor {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                CorridorFrame frame = new CorridorFrame();
                frame.setTitle("Corridor Editor");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
