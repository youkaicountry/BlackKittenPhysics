package com.youkaicountry.youkaigeometry;

public class DistanceFromLineReturn
{
    
    //point on line
    public double lx, ly;
    
    //distance to line
    public double ld;
    
    //point on segment
    public double sx, sy;
    
    //distance to segment
    public double sd;
    
    public DistanceFromLineReturn()
    {
        lx = 0;
        ly = 0;
        ld = 0;
        sx = 0;
        sy = 0;
        sd = 0;
        return;
    }
    
    public void calculateSegmentDistance(double ox, double oy)
    {
        double x = ox-sx;
        double y = oy-sy;
        sd = Math.sqrt(x*x + y*y);
    }
}
