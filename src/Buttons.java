import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Buttons extends JButton
{
    public Buttons(String text, boolean enabled, boolean visible)
    {
        super(text);
        setMaximumSize(new Dimension(130, 30));
        setHorizontalAlignment(SwingConstants.LEFT);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setEnabled(enabled);
        setVisible(visible);
    }
}
