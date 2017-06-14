import java.util.*;

public class ColorLayers
{
    private ArrayList<String> layers;
    private int up;
    private int low;
    
    public ColorLayers(int upper, int lower)
    {
        layers = new ArrayList<String>();
        up = upper;
        low = lower;
    }
    
    public int getLayer(int r, int g, int b)
    {
        String[] RGB = new String[3];
        int[] intRGB = new int[6];
        ArrayList<Integer> match = new ArrayList<Integer>();
        for (String s : layers)
        {
            RGB = s.split(",");
            intRGB[0] = Integer.parseInt(RGB[0]);
            intRGB[1] = Integer.parseInt(RGB[1]);
            intRGB[2] = Integer.parseInt(RGB[2]);
            if (intRGB[0] <= r+up && intRGB[0] >= r-low &&
                intRGB[1] <= g+up && intRGB[1] >= g-low &&
                intRGB[2] <= b+up && intRGB[2] >= b-low)
            {
                match.add(layers.indexOf(s));
            }
        }
        Integer nearest = -1;
        long ofNearest = 0;
        long ofI = 0;
        for (Integer i : match)
        {
            if (match.indexOf(i) == 0)
            {
                nearest = i;
            }
            else
            {
                RGB = layers.get(nearest).split(",");
                intRGB[0] = Integer.parseInt(RGB[0]);
                intRGB[1] = Integer.parseInt(RGB[1]);
                intRGB[2] = Integer.parseInt(RGB[2]);
                ofNearest = intRGB[0] + 256*intRGB[1] + 256*256*intRGB[2];
                RGB = layers.get(i).split(",");
                intRGB[3] = Integer.parseInt(RGB[0]);
                intRGB[4] = Integer.parseInt(RGB[1]);
                intRGB[5] = Integer.parseInt(RGB[2]);
                ofI = intRGB[3] + 256*intRGB[4] + 256*256*intRGB[5];
                if (ofI < ofNearest)
                {
                    nearest = i;
                }
            }
        }
        return nearest;
    }
    
    public void addLayer(int r, int g, int b)
    {
        layers.add("" + r + "," + g +"," + b);
    }
}
