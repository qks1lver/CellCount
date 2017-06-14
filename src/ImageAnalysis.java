import java.io.*;
import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color.*;

public class ImageAnalysis
{
    private BufferedImage image;
    private int pixX;
    private int pixY;
    private int tol = 40;
    private ColorLayers layers;
    
    private BufferedImage image2;
    private BufferedImage image3;
    
    private String message;
    
    private CountArea area;
    
    private int colonyPxl;
    
    
    public ImageAnalysis(File imageFile) throws IOException
    {
        image = ImageIO.read(imageFile);
        pixX = image.getWidth();
        pixY = image.getHeight();
        layers = new ColorLayers(tol, tol);
        
        area = new CountArea();
        
        colonyPxl = 0;
        
        image2 = new BufferedImage(pixX, pixY, java.awt.image.BufferedImage.TYPE_INT_RGB);
        image3 = new BufferedImage(pixX, pixY, java.awt.image.BufferedImage.TYPE_INT_RGB);
        
        message = "Image Analysis Initiated";
        //searchBound();
        analysis();
    }
    
    public int getWidth()
    {
        return pixX;
    }
    
    public int getHeight()
    {
        return pixY;
    }
    
    public int getR(int i, int j)
    {
        return (image.getRGB(i, j) & 0x00ff0000) >> 16;
    }
    
    public int getG(int i, int j)
    {
        return (image.getRGB(i, j) & 0x0000ff00) >> 8;
    }
    
    public int getB(int i, int j)
    {
        return image.getRGB(i, j) & 0x000000ff;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    private void searchBound()
    {
        int cut = 10;
        int tableColorUpper;
        int tableUR;
        int tableUG;
        int tableUB;
        int tableColorLower;
        int tableLR;
        int tableLG;
        int tableLB;
        boolean boundFound = false;
        //ColorLayers bound = new ColorLayers(80, 20);
        
        //From left
        for (int j = 0; j < pixY; j++)
        {
            tableColorUpper = image.getRGB( 0, j );
            tableUR = (tableColorUpper & 0x00ff0000) >> 16;
            tableUG = (tableColorUpper & 0x0000ff00) >> 8;
            tableUB = tableColorUpper & 0x000000ff;
            tableColorLower = image.getRGB( 0, j );
            tableLR = (tableColorLower & 0x00ff0000) >> 16;
            tableLG = (tableColorLower & 0x0000ff00) >> 8;
            tableLB = tableColorLower & 0x000000ff;
            for (int i = 0; i < pixX/2+pixX%2; i++)
            {
                if (boundFound || getR(i, j) > tableUR+cut && getG(i, j) > tableUG+cut && getB(i, j) > tableUB+cut ||
                    getR(i, j) < tableLR-cut && getG(i, j) < tableLG-cut && getB(i, j) < tableLB-cut)
                {
                    boundFound = true;
                    image2.setRGB( i, j, Color.red.getRGB() );
                }
                else
                {
                    if (getR(i, j) > tableUR && getG(i, j) > tableUG && getB(i, j) > tableUB)
                    {
                        tableColorUpper = image.getRGB( i, j );
                        tableUR = (tableColorUpper & 0x00ff0000) >> 16;
                        tableUG = (tableColorUpper & 0x0000ff00) >> 8;
                        tableUB = tableColorUpper & 0x000000ff;
                    }
                    else if (getR(i, j) < tableLR && getG(i, j) < tableLG && getB(i, j) < tableLB)
                    {
                        tableColorLower = image.getRGB( i, j );
                        tableLR = (tableColorLower & 0x00ff0000) >> 16;
                        tableLG = (tableColorLower & 0x0000ff00) >> 8;
                        tableLB = tableColorLower & 0x000000ff;
                    }
                    image2.setRGB( i, j, image.getRGB(i, j) );
                }
                System.out.print(".");
            }
            boundFound = false;
            System.out.print("\n");
        }
        //From right
        for (int j = 0; j < pixY; j++)
        {
            tableColorUpper = image.getRGB( pixX-1, j );
            tableUR = (tableColorUpper & 0x00ff0000) >> 16;
            tableUG = (tableColorUpper & 0x0000ff00) >> 8;
            tableUB = tableColorUpper & 0x000000ff;
            tableColorLower = image.getRGB( pixX-1, j );
            tableLR = (tableColorLower & 0x00ff0000) >> 16;
            tableLG = (tableColorLower & 0x0000ff00) >> 8;
            tableLB = tableColorLower & 0x000000ff;
            for (int i = pixX-1; i >= (pixX-1)/2-(pixX-1)%2; i--)
            {
                if (boundFound || getR(i, j) > tableUR+cut && getG(i, j) > tableUG+cut && getB(i, j) > tableUB+cut ||
                    getR(i, j) < tableLR-cut && getG(i, j) < tableLG-cut && getB(i, j) < tableLB-cut)
                {
                    boundFound = true;
                    image2.setRGB( i, j, Color.red.getRGB() );
                }
                else
                {
                    if (getR(i, j) > tableUR && getG(i, j) > tableUG && getB(i, j) > tableUB)
                    {
                        tableColorUpper = image.getRGB( i, j );
                        tableUR = (tableColorUpper & 0x00ff0000) >> 16;
                        tableUG = (tableColorUpper & 0x0000ff00) >> 8;
                        tableUB = tableColorUpper & 0x000000ff;
                    }
                    else if (getR(i, j) < tableLR && getG(i, j) < tableLG && getB(i, j) < tableLB)
                    {
                        tableColorLower = image.getRGB( i, j );
                        tableLR = (tableColorLower & 0x00ff0000) >> 16;
                        tableLG = (tableColorLower & 0x0000ff00) >> 8;
                        tableLB = tableColorLower & 0x000000ff;
                    }
                    image2.setRGB( i, j, image.getRGB(i, j) );
                }
                System.out.print(".");
            }
            boundFound = false;
            System.out.print("\n");
        }
        //From top
        for (int i = 0; i < pixX; i++)
        {
            tableColorUpper = image.getRGB( i, 0 );
            tableUR = (tableColorUpper & 0x00ff0000) >> 16;
            tableUG = (tableColorUpper & 0x0000ff00) >> 8;
            tableUB = tableColorUpper & 0x000000ff;
            tableColorLower = image.getRGB( i, 0 );
            tableLR = (tableColorLower & 0x00ff0000) >> 16;
            tableLG = (tableColorLower & 0x0000ff00) >> 8;
            tableLB = tableColorLower & 0x000000ff;
            for (int j = 0; j < (pixY-1)/2+(pixY-1)%2; j++)
            {
                if (boundFound || getR(i, j) > tableUR+cut && getG(i, j) > tableUG+cut && getB(i, j) > tableUB+cut ||
                    getR(i, j) < tableLR-cut && getG(i, j) < tableLG-cut && getB(i, j) < tableLB-cut)
                {
                    boundFound = true;
                    image2.setRGB( i, j, image2.getRGB(i, j) );
                }
                else
                {
                    if (getR(i, j) > tableUR && getG(i, j) > tableUG && getB(i, j) > tableUB)
                    {
                        tableColorUpper = image.getRGB( i, j );
                        tableUR = (tableColorUpper & 0x00ff0000) >> 16;
                        tableUG = (tableColorUpper & 0x0000ff00) >> 8;
                        tableUB = tableColorUpper & 0x000000ff;
                    }
                    else if (getR(i, j) < tableLR && getG(i, j) < tableLG && getB(i, j) < tableLB)
                    {
                        tableColorLower = image.getRGB( i, j );
                        tableLR = (tableColorLower & 0x00ff0000) >> 16;
                        tableLG = (tableColorLower & 0x0000ff00) >> 8;
                        tableLB = tableColorLower & 0x000000ff;
                    }
                    image2.setRGB( i, j, image.getRGB(i, j) );
                }
                System.out.print(".");
            }
            boundFound = false;
            System.out.print("\n");
        }
        //From bottom
        for (int i = 0; i < pixX; i++)
        {
            tableColorUpper = image.getRGB( i, pixY-1 );
            tableUR = (tableColorUpper & 0x00ff0000) >> 16;
            tableUG = (tableColorUpper & 0x0000ff00) >> 8;
            tableUB = tableColorUpper & 0x000000ff;
            tableColorLower = image.getRGB( i, pixY-1 );
            tableLR = (tableColorLower & 0x00ff0000) >> 16;
            tableLG = (tableColorLower & 0x0000ff00) >> 8;
            tableLB = tableColorLower & 0x000000ff;
            for (int j = pixY-1; j >= (pixY-1)/2-(pixY-1)%2; j--)
            {
                if (boundFound || getR(i, j) > tableUR+cut && getG(i, j) > tableUG+cut && getB(i, j) > tableUB+cut ||
                    getR(i, j) < tableLR-cut && getG(i, j) < tableLG-cut && getB(i, j) < tableLB-cut)
                {
                    boundFound = true;
                    image2.setRGB( i, j, image2.getRGB(i, j) );
                }
                else
                {
                    if (getR(i, j) > tableUR && getG(i, j) > tableUG && getB(i, j) > tableUB)
                    {
                        tableColorUpper = image.getRGB( i, j );
                        tableUR = (tableColorUpper & 0x00ff0000) >> 16;
                        tableUG = (tableColorUpper & 0x0000ff00) >> 8;
                        tableUB = tableColorUpper & 0x000000ff;
                    }
                    else if (getR(i, j) < tableLR && getG(i, j) < tableLG && getB(i, j) < tableLB)
                    {
                        tableColorLower = image.getRGB( i, j );
                        tableLR = (tableColorLower & 0x00ff0000) >> 16;
                        tableLG = (tableColorLower & 0x0000ff00) >> 8;
                        tableLB = tableColorLower & 0x000000ff;
                    }
                    image2.setRGB( i, j, image.getRGB(i, j) );
                }
                System.out.print(".");
            }
            boundFound = false;
            System.out.print("\n");
        }
    }
    
    public void analysis()
    {
        int run = 0;
        int layer = 0;
        int[] x = new int[100];
        int[] y = new int[100];
        
        int[][] boundary = new int[pixX][pixY];
        for (int j = 0; j < pixY; j++)
        {
            for (int i = 0; i < pixX; i++)
            {
                boundary[i][j] = 0;
            }
        }
        
        //From upper left
        for (int j = 0; j < pixY; j++)
        {
            for (int i = 0; i < pixX; i++)
            {
                layer = layers.getLayer( getR(i, j), getG(i, j), getB(i, j) );
                if (layer > -1)
                {
                    boundary[i][j] = layer;
                }
                else
                {
                    layers.addLayer( getR(i, j), getG(i, j), getB(i, j) );
                    layer = layers.getLayer( getR(i, j), getG(i, j), getB(i, j) );
                    boundary[i][j] = layer;
                    x[layer] = i;
                    y[layer] = j;
                }
                image2.setRGB( i, j, colorRGB(layer) );
                //image2.setRGB( i, j,  image.getRGB(x[layer], y[layer]));
                //System.out.println("pix:{" + image.getRGB( i, j ) + "} R:{" + getR(i, j) + "} G:{" + getG(i, j) + "} B:{" + getB(i, j) + "}");
            }
        }
        
        image3 = image2;
    }
    
    public void analysis2(CountArea ar)
    {
        //image2 = image
        area = ar;
        area.setCountArea();
        
        /*for (Point p : area.getCountArea())
        {
            //Image area begins at (130, 40)
            image2.setRGB( p.x-130, p.y-40, image.getRGB( p.x-130, p.y-40 ) );
        }*/
        analysis4();
        //analysis3();
    }
    
    public void analysis4()
    {
        boolean[][] aColony = area.getAvgColony();
        int colonySize = 0;
        int cX2 = area.getCenter2().x;
        int cY2 = area.getCenter2().y;
        int cR2 = area.getRadius2();
        
        int trues = 0;
        
        //System.out.println(cX + ":" + cY);
        
        for (int j = cY2-cR2; j < cY2+cR2; j++)
        {
            for (int i = cX2-cR2; i < cX2+cR2; i++)
            {
                //System.out.println(i + ":" + j);
                if (aColony[i][j])
                {
                    trues++;
                    //System.out.println("true");
                    
                    if (compareRGB(i, j, cX2, cY2) == 0)
                        colonySize++;
                    /*if (compareRGB(i, j, cX, cY) == 0)
                        colonySize++;*/
                }
            }
        }
        
        System.out.println("\nPixel in a colony: " + colonySize + " out of: " + trues);
        
        boolean[][] plate = area.getBoundArray();
        int cX = area.getCenter().x;
        int cY = area.getCenter().y;
        int cR = area.getRadius();
        
        int forJ;
        int maxJ;
        int forI;
        int maxI;
        int pxColonies = 0;
        
        if (cY-cR < 0)
            forJ = 0;
        else
            forJ = cY-cR;
        
        if (cY+cR >= pixY)
            maxJ = pixY;
        else
            maxJ = cY+cR;
        
        if (cX-cR < 0)
            forI = 0;
        else
            forI = cX-cR;
        
        if (cX+cR >= pixX)
            maxI = pixX;
        else
            maxI = cX+cR;
        
        trues = 0;
        for (int j = forJ; j < maxJ; j++)
        {
            for (int i = forI; i < maxI; i++)
            {
                //System.out.println(i + ":" + j);
                if (plate[i][j])
                {
                    trues++;
                    //System.out.println("true");
                    
                    if (compareRGB(i, j, cX2, cY2) == 0)
                        pxColonies++;
                    /*if (compareRGB(i, j, cX, cY) == 0)
                        colonySize++;*/
                }
            }
        }
        
        int numColonies = pxColonies / colonySize;
        if (pxColonies-(colonySize*numColonies) <= numColonies/2)
            numColonies++;
        
        System.out.println("Pixels of colonies: " + pxColonies + " out of " + trues + " Number of colonies: " + numColonies);
    }
    
    /*public void analysis3()
    {
        boolean[][] bound = new boolean[pixX][pixY];
        bound = area.getBoundArray();
        int diam;
        ArrayList<Point> colonies = new ArrayList<Point>();
        BufferedImage tmp = image;
        image = image3;
        
        boolean[][] counted = new boolean[pixX][pixY];
        for (int a = 0; a < pixX; a++)
            for (int b = 0; b < pixY; b++)
                counted[a][b] = false;
        
        for (int j = 0; j < pixY; j++)
        {
            for (int i = 0; i < pixX-1; i++)
            {
                if (bound[i][j] && bound[i+1][j] && !counted[i+1][j] && compareRGB(i, j, i+1, j) == 1)
                {
                    System.out.println("" + i + ":" + j);
                    diam = 0;
                    while(compareRGB(i+1, j+diam, i+1, j+diam+1) >= 0)
                    {
                        diam++;
                    }
                    System.out.println(diam);
                    colonies.add( new Point(i+1, j+(diam/2)) );
                    counted = expandSearch(i+1, j, i, j, counted);
                }
            }
        }
        image = tmp;
    }*/
    
    /**
     * Find pixels taken up by colonies
     * @return pixels
     */
    /*public boolean[][] expandSearch(int x, int y, int x0, int y0, boolean[][] counted)
    {
        if (!counted[x+1][y] && compareRGB(x, y, x+1, y) == 0)
        {
            counted[x+1][y] = true;
            counted = expandSearch(x+1, y, x, y, counted);
            System.out.print(1);
        }
        if (!counted[x-1][y] && compareRGB(x, y, x-1, y) == 0)
        {
            counted[x-1][y] = true;
            counted = expandSearch(x-1, y, x, y, counted);
            System.out.print(2);
        }
        if (!counted[x][y+1] && compareRGB(x, y, x, y+1) == 0)
        {
            counted[x][y+1] = true;
            counted = expandSearch(x, y+1, x, y, counted);
            System.out.print(3);
        }
        if (!counted[x][y-1] && compareRGB(x, y, x, y-1) == 0)
        {
            counted[x][y-1] = true;
            counted = expandSearch(x, y-1, x, y, counted);
            System.out.print(4);
        }
        System.out.print( 0 );
        return counted;
    }*/
    
    /**
     * Count the pixels in each colony.
     * @return pixels
     *//*
    public int expandSearch(int x, int y, int x0, int y0, boolean[][] counted)
    {
        int count = 0;
        if (!counted[x+1][y] && compareRGB(x0, y0, x+1, y) >= 0)
        {
            count = count + expandSearch(x+1, y, x0, y0, counted) + 1;
            counted[x+1][y] = true;
        }
        if (!counted[x-1][y] && compareRGB(x0, y0, x-1, y) >= 0)
        {
            count = count + expandSearch(x+1, y, x0, y0, counted) + 1;
            counted[x-1][y] = true;
        }
        if (!counted[x][y+1] && compareRGB(x0, y0, x, y+1) >= 0)
        {
            count = count + expandSearch(x, y+1, x0, y0, counted) + 1;
            counted[x][y+1] = true;
        }
        if (!counted[x][y-1] && compareRGB(x0, y0, x, y-1) >= 0)
        {
            count = count + expandSearch(x, y-1, x0, y0, counted) + 1;
            counted[x][y-1] = true;
        }
        return count;
    }*/
    
    /**
     * Compare RGB of Point1 with Point2
     * @return 1 if P2 is brighter, -1 if P2 is darker, 0 if P2 is within range around P1
     */
    private int compareRGB(int x1, int y1, int x2, int y2)
    {
        if (getR(x2, y2) > getR(x1, y1)+tol && getG(x2, y2) > getG(x1, y1)+tol && getB(x2, y2) > getB(x1, y1)+tol)
        {
            return 1;
        }
        else if (getR(x2, y2) < getR(x1, y1)-tol && getG(x2, y2) < getG(x1, y1)-tol && getB(x2, y2) < getB(x1, y1)-tol)
        {
            return -1;
        }
        return 0;
    }
    
    public Image getNewImage()
    {
        return image2;
    }
    
    public void setTolerance(int tolerance)
    {
        tol = tolerance;
        layers = new ColorLayers(tol, tol);
    }
    
    private int colorRGB(int lay)
    {
        switch(lay)
        {
            case 0:
                return Color.BLACK.getRGB();
            case 1:
                return Color.blue.getRGB();
            case 2:
                return Color.gray.getRGB();
            case 3:
                return Color.white.getRGB();
            case 4:
                return Color.cyan.getRGB();
            case 5:
                return Color.pink.getRGB();
            case 6:
                return Color.yellow.getRGB();
            case 7:
                return Color.green.getRGB();
            default:
                return Color.orange.getRGB();
        }
    }
}
