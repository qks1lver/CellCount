import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Color.*;

public class CountArea
    extends JPanel
{
    private Point center;
    private int radius;
    private int crossHair = 15;
    
    //This is to accommodate Phil's cheating advise, should not require in version 2
    private Point center2;
    private int radius2;
    private boolean[][] avgColony;
    
    //private ArrayList<Point> countArea;
    private int width;
    private int height;
    private boolean[][] isBounded;
    
    public CountArea()
    {
        super();
        center = new Point(0, 0);
        //isBounded = new boolean[800][600];
        radius = 0;
        
        center2 = new Point(0, 0);
        radius2 = 0;
        
        //countArea = new ArrayList<Point>();
        width = 0;
        height = 0;
    }
    
    public void setCenter(Point c)
    {
        center = c;
    }
    
    public Point getCenter()
    {
        Point p = new Point(center.x-130, center.y-40);
        return p;
    }
    
    public void setRadius(int r)
    {
        radius = r;
    }
    
    public int getRadius()
    {
        return radius;
    }
    
    //Phil's cheats start-
    public void setCenter2(Point c)
    {
        center2 = c;
    }
    
    public Point getCenter2()
    {
        Point p = new Point(center2.x-130, center2.y-40);
        return p;
    }
    
    public void setRadius2(int r)
    {
        radius2 = r;
    }
    
    public int getRadius2()
    {
        return radius2;
    }
    //-end-
    
    public void setWidth(int w)
    {
        width = w;
    }
    
    public void setHeight(int h)
    {
        height = h;
    }
    
    public void draw(Graphics g, int snap)
    {
        int x = center.x - radius;
        int y = center.y - radius;
        g.setColor(Color.red);
        g.drawLine( center.x-crossHair, center.y, center.x+crossHair, center.y );
        g.drawLine( center.x, center.y-crossHair, center.x, center.y+crossHair );
        g.drawOval( x, y, radius*2, radius*2);
        
        //----------------
        int x2 = center2.x - radius2;
        int y2 = center2.y - radius2;
        g.setColor(Color.blue);
        g.drawLine( center2.x-crossHair, center2.y, center2.x+crossHair, center2.y );
        g.drawLine( center2.x, center2.y-crossHair, center2.x, center2.y+crossHair );
        g.drawOval( x2, y2, radius2*2, radius2*2);
        //--------------------
        
        switch(snap)
        {
            case 1:
                g.setColor(Color.green);
                g.drawOval( center.x-5, center.y-5, 10, 10 );
                break;
            case 3:
                g.setColor(Color.green);
                g.drawOval( center2.x-5, center2.y-5, 10, 10 );
                break;
        }
    }
    
    public void setCountArea()
    {
        int y = 0;
        isBounded = new boolean[width][height];
        avgColony = new boolean[width][height];     //This one...
        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                isBounded[a][b] = false;
                avgColony[a][b] = false;    //This one, too
            }
        
        for (int x = 0; x <= radius; x++)
        {
            y = (int)Math.sqrt((radius*radius) - (x*x));
            for (int j = 0; j <= y; j++)
            {
                if (center.x-x >= 130 && center.x-x < width+130)
                {
                    if (center.y-j >= 40 && center.y-j < height+40 && !isBounded[center.x-x-130][center.y-j-40])
                    {
                        //countArea.add(new Point(center.x-x, center.y-j));
                        isBounded[center.x-x-130][center.y-j-40] = true;
                    }
                    if (center.y+j >= 40 && center.y+j < height+40 && !isBounded[center.x-x-130][center.y+j-40])
                    {
                        //countArea.add(new Point(center.x-x, center.y+j));
                        isBounded[center.x-x-130][center.y+j-40] = true;
                    }
                }
                if (center.x+x >= 130 && center.x+x < width+130)
                {
                    if (center.y-j >= 40 && center.y-j < height+40 && !isBounded[center.x+x-130][center.y-j-40])
                    {
                        //countArea.add(new Point(center.x+x, center.y-j));
                        isBounded[center.x+x-130][center.y-j-40] = true;
                    }
                    if (center.y+j >= 40 && center.y+j < height+40 && !isBounded[center.x+x-130][center.y+j-40])
                    {
                        //countArea.add(new Point(center.x+x, center.y+j));
                        isBounded[center.x+x-130][center.y+j-40] = true;
                    }
                }
            }
        }
        
        //And all of this non-sense--
        for (int x = 0; x <= radius2; x++)
        {
            y = (int)Math.sqrt((radius2*radius2) - (x*x));
            for (int j = 0; j <= y; j++)
            {
                if (center2.x-x >= 130 && center2.x-x < width+130)
                {
                    if (center2.y-j >= 40 && center2.y-j < height+40 && !avgColony[center2.x-x-130][center2.y-j-40])
                    {
                        avgColony[center2.x-x-130][center2.y-j-40] = true;
                    }
                    if (center2.y+j >= 40 && center2.y+j < height+40 && !avgColony[center2.x-x-130][center2.y+j-40])
                    {
                        avgColony[center2.x-x-130][center2.y+j-40] = true;
                    }
                }
                if (center2.x+x >= 130 && center2.x+x < width+130)
                {
                    if (center2.y-j >= 40 && center2.y-j < height+40 && !avgColony[center2.x+x-130][center2.y-j-40])
                    {
                        avgColony[center2.x+x-130][center2.y-j-40] = true;
                    }
                    if (center2.y+j >= 40 && center2.y+j < height+40 && !avgColony[center2.x+x-130][center2.y+j-40])
                    {
                        avgColony[center2.x+x-130][center2.y+j-40] = true;
                    }
                }
            }
        }
        //--End--
    }
    
    public boolean[][] getBoundArray()
    {
        return isBounded;
    }
    
    //Definitely this one
    public boolean[][] getAvgColony()
    {
        return avgColony;
    }
    
    /*public ArrayList<Point> getCountArea()
    {
        return countArea;
    }*/
}
