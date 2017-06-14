import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class CellCountPanel
    extends JPanel
{
    private ImageIcon image0;
    private ImageIcon image;
    private ImageIcon imageToDraw;
    private String message;
    private ImageAnalysis img;
    private int currentTol;
    
    private JButton load;
    private JButton viewImage;
    private JButton viewImage0;
    private JButton auto;
    private JButton manual;
    private JButton refresh;
    private JButton analyze;
    
    private JTextField addressBox;
    private JTextField toleranceBox;
    
    private JLabel imageBox;
    private JLabel messageBox;
    private JLabel centerPxlBox;
    private JLabel radiusBox;
    private JLabel cursorBox;
    
    private JPanel northPanel;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel manualPanel;
    private JPanel southPanel;
    private JPanel analysisPanel;
    
    private Point center;
    private Point rim;
    private int radius;
    private int snap;
    private CountArea area;
    
    private Point center2;
    private Point rim2;
    private int radius2;
    
    public CellCountPanel()
    {
        super();
        setLayout(new BorderLayout());
        setAlignmentX(LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(800, 640));
        
        img = null;
        currentTol = 40;
        center = new Point(465, 300);
        rim = new Point(0, 0);
        radius = 200;
        snap = 0;
        area = new CountArea();
        
        center2 = new Point(465, 300);
        rim2 = new Point(0, 0);
        radius2 = 30;
        
        image = null;
        image0 = null;
        imageToDraw = new ImageIcon();
        
        message = "Stable Operation.";
        load = new JButton("<=");
        load.addActionListener(new Action());
        viewImage = new Buttons("Image Rendered", false, true);
        viewImage.addActionListener(new Action());
        viewImage0 = new Buttons("Image Original", false, true);
        viewImage0.addActionListener(new Action());
        auto = new Buttons("Automatic", false, true);
        auto.addActionListener(new Action());
        //manual = new Buttons("Manual", false, true);
        manual = new Buttons("Adjust Tolerance", false, true);
        manual.addActionListener(new Action());
        refresh = new JButton("<");
        refresh.addActionListener(new Action());
        analyze = new Buttons("Analyze", false, true);
        analyze.addActionListener(new Action());
        
        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        addressBox = new JTextField(40);
        imageBox = new JLabel("Please Load Image");
        /*imageBox.setHorizontalAlignment(SwingConstants.CENTER);
        imageBox.setVerticalAlignment(SwingConstants.CENTER);
        imageBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageBox.setAlignmentY(Component.CENTER_ALIGNMENT);*/
        messageBox = new JLabel(message);
        messageBox.setMaximumSize( new Dimension(440, 30) );
        toleranceBox = new JTextField(""+currentTol, 3);
        centerPxlBox = new JLabel("Center: " + (center.x-130) + ", " + (center.y-40));
        centerPxlBox.setMaximumSize( new Dimension(120, 30) );
        radiusBox = new JLabel("Radius: " + 200);
        radiusBox.setMaximumSize( new Dimension(120, 30) );
        cursorBox = new JLabel("Cursor: ");
        cursorBox.setMaximumSize( new Dimension(120, 30) );
        
        northPanel = new JPanel();
        northPanel.setPreferredSize( new Dimension(800, 40) );
        westPanel = new JPanel();
        westPanel.setPreferredSize( new Dimension(130, 600) );
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        centerPanel = new JPanel();
        //centerPanel.setPreferredSize(new Dimension(400, 400));
        centerPanel.setBackground(Color.white);
        
        northPanel.add(new JLabel("Image Address: "));
        northPanel.add(addressBox);
        northPanel.add(load);
        
        //Things in westPanel-----------------
        westPanel.add(viewImage0);
        westPanel.add(viewImage);
        //westPanel.add(auto);
        westPanel.add(manual);
        manualPanel = new JPanel();
        manualPanel.setMaximumSize(new Dimension(130, 40));
        manualPanel.setVisible(false);
        manualPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        manualPanel.add(new JLabel("Tol:"));
        manualPanel.add(toleranceBox);
        manualPanel.add(refresh);
        analysisPanel = new JPanel();
        
        westPanel.add(manualPanel);
        westPanel.add(analyze);
        //---------------------------------------
        
        centerPanel.add(imageBox);
        
        southPanel = new JPanel();
        southPanel.setPreferredSize( new Dimension(800, 30) );
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(messageBox);
        southPanel.add(centerPxlBox);
        southPanel.add(radiusBox);
        southPanel.add(cursorBox);
        
        add(northPanel, BorderLayout.NORTH);
        //add(centerPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try
        {
            g.drawImage(imageToDraw.getImage(), 130, 40, this);
        }
        catch(java.lang.NullPointerException e){}
        area.setCenter( center );
        area.setRadius(radius);
        area.setCenter2( center2 );
        area.setRadius2(radius2);
        area.draw(g, snap);
    }
    
    private class Action
        implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == load)
            {
                if (addressBox.getText() != null && !addressBox.getText().equals(""))
                {
                    image0 = new ImageIcon(addressBox.getText());
                    imageBox.setIcon(image0);
                    imageToDraw = image0;
                    repaint();
                    if (image0 == null || image0.getIconHeight() < 5)
                    {
                        setEnVisible(0);
                        imageBox.setText( "No Image - Please Load Image" );
                        message = "Error -- Image Address - code 02.";
                    }
                    else
                    {
                        message = "Image Loading Complete.";
                        
                        try
                        {
                            img = new ImageAnalysis(new File(addressBox.getText()));
                        }
                        catch(IOException e) {/*Nothing*/}
                        image = new ImageIcon( img.getNewImage() );
                        currentTol = 40;
                        toleranceBox.setText(""+currentTol);
                        imageBox.setText("");
                        imageBox.setIcon(image);
                        imageToDraw = image;
                        setEnVisible(1);
                    }
                }
                else
                {
                    setEnVisible(0);
                    imageBox.setIcon(new ImageIcon(""));
                    imageBox.setText("No Image - Please Load Image");
                    message = "Error -- Image Address - code 01.";
                }
            }
            else if (event.getSource() == viewImage)
            {
                imageBox.setIcon(image);
                
                imageToDraw = image;
            }
            else if (event.getSource() == viewImage0)
            {
                imageBox.setIcon(image0);
                
                imageToDraw = image0;
            }
            else if (event.getSource() == auto)
            {
                setEnVisible(4);
            }
            else if (event.getSource() == manual)
            {
                setEnVisible(3);
            }
            else if (event.getSource() == refresh)
            {
                try
                {
                    int tolerance = Integer.parseInt(toleranceBox.getText());
                    if (currentTol == tolerance)
                    {
                        message = "Same Tolerance - Image maintained.";
                    }
                    else
                    {
                        img.setTolerance(tolerance);
                        currentTol = tolerance;
                        message = "Tolerance updated.";
                        updateUI();
                        img.analysis();
                        image = new ImageIcon( img.getNewImage() );
                        imageBox.setIcon(image);
                        imageToDraw = image;
                        message = "Analysis complete.";
                    }
                }
                catch(java.lang.NumberFormatException n)
                {
                    message = "Error - Only accepts integer.";
                }
            }
            else if (event.getSource() == analyze)
            {
                area.setWidth( image.getIconWidth() );
                area.setHeight( image.getIconHeight() );
                img.analysis2(area);
                image = new ImageIcon( img.getNewImage() );
                imageBox.setIcon(image);
                imageToDraw = image;
            }
            
            messageBox.setText(message);
            repaint();
        }
    }
    
    private void setEnVisible(int code)
    {
        switch(code)
        {
            case 0:
                viewImage0.setEnabled(false);
                viewImage.setEnabled(false);
                auto.setEnabled(false);
                manual.setEnabled(false);
                manualPanel.setVisible(false);
                break;
            case 1:
                viewImage0.setEnabled(true);
                viewImage.setEnabled(true);
                analyze.setEnabled(true);
                auto.setEnabled(true);
                manual.setEnabled(true);
                manualPanel.setVisible(false);
                break;
            case 3:
                auto.setEnabled(true);
                manual.setEnabled(false);
                manualPanel.setVisible(true);
                break;
            case 4:
                auto.setEnabled(false);
                manual.setEnabled(true);
                manualPanel.setVisible(false);
                break;
        }
    }
    
    private class Mouse
        implements MouseListener, MouseMotionListener
    {
        private Point center0;
        private Point center1;
        private int cenRange = 15;
        private boolean setCenter;
        private boolean setRim;
        private int rad;
        
        /*private Point center20;
        private Point center21;*/
        private boolean setCenter2;
        private boolean setRim2;
        private int rad2;
        
        public Mouse()
        {
            center0 = new Point(0, 0);
            center1 = new Point(0, 0);
            setCenter = false;
            setRim = false;
            rad = 0;
            
            /*center20 = new Point(0, 0);
            center21 = new Point(0, 0);*/
            setCenter2 = false;
            setRim2 = false;
            rad2 = 0;
        }

        public void mouseClicked( MouseEvent event )
        {
            //TODO
        }

        public void mouseEntered( MouseEvent event )
        {
            //TODO
        }

        public void mouseExited( MouseEvent event )
        {
            //TODO
        }

        public void mousePressed( MouseEvent event )
        {
            center1 = event.getPoint();
            center0 = center;
            
            if (snap == 1)
            {
                if (center1.x >= 130 && center1.y >= 40)
                    center = center1;
                //setCenter = true;
            }
            else if (snap == 2)
            {
                //setRim = true;
            }
            else if (snap == 3)
            {
                if (center1.x >= 130 && center1.y >= 40)
                    center2 = center1;
                setCenter2 = true;
            }
            else if (snap == 4)
            {
                setCenter2 = true;
            }
            centerPxlBox.setText("Center: " + (center.x-130) + ", " + (center.y-40));
            radiusBox.setText("Radius: " + radius);
            cursorBox.setText("Cursor: " + (center1.x-130) + ", " + (center1.y-40));
            repaint();
        }

        public void mouseReleased( MouseEvent event )
        {
            /*setCenter = false;
            setRim = false;
            setCenter2 = false;
            setRim2 = false;*/
            repaint();
        }

        public void mouseDragged( MouseEvent event )
        {
            Point current = event.getPoint();
            
            if (snap == 1)
            {
                if (current.x >= 130 && current.y >= 40)
                    center = current;
            }
            else if (snap == 2)
            {
                rim = current;
                radius = (int)Math.sqrt((double)((rim.x - center.x)
                         * (rim.x - center.x)) + (double)((rim.y - center.y)
                         * (rim.y - center.y)));
            }
            else if (snap == 3)
            {
                if (current.x >= 130 && current.y >= 40)
                    center2 = current;
            }
            else if (snap == 4)
            {
                rim2 = current;
                radius2 = (int)Math.sqrt((double)((rim2.x - center2.x)
                         * (rim2.x - center2.x)) + (double)((rim2.y - center2.y)
                         * (rim2.y - center2.y)));
            }
            centerPxlBox.setText("Center: " + (center.x-130) + ", " + (center.y-40));
            radiusBox.setText("Radius: " + radius);
            cursorBox.setText("Cursor: " + (current.x-130) + ", " + (current.y-40));
            repaint();
        }

        public void mouseMoved( MouseEvent event )
        {
            Point current = event.getPoint();
            rad = (int)Math.sqrt((double)((current.x - center.x)
                   * (current.x - center.x)) + (double)((current.y - center.y)
                   * (current.y - center.y)));
            rad2 = (int)Math.sqrt((double)((current.x - center2.x)
                    * (current.x - center2.x)) + (double)((current.y - center2.y)
                    * (current.y - center2.y)));
            
            if (snap != 2 && current.x < center.x+cenRange && current.x > center.x-cenRange
                && current.y < center.y+cenRange && current.y > center.y-cenRange)
            {
                snap = 1;
            }
            else if (snap != 3 && rad <= radius + cenRange && rad >= radius - cenRange)
            {
                snap = 2;
            }
            else if (snap != 4 && current.x < center2.x+cenRange && current.x > center2.x-cenRange
                     && current.y < center2.y+cenRange && current.y > center2.y-cenRange)
            {
                snap = 3;
            }
            else if (rad2 <= radius2 + cenRange && rad2 >= radius2 - cenRange)
            {
                snap = 4;
            }
            else
            {
                snap = 0;
            }
            cursorBox.setText("Cursor: " + (current.x-130) + ", " + (current.y-40));
            repaint();
        }
    }
}
