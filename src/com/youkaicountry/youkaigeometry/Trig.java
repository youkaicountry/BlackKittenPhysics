package com.youkaicountry.youkaigeometry;

public abstract class Trig
{
    public static final double PI_TIMES_2 = Math.PI * 2.0;
    public static final double PI_OVER_2 = Math.PI / 2.0;
    
    public static double atan2angle(double y, double x)
    {
        double a = Math.atan2(y, x);
        if (a < 0)
        {
            a += PI_TIMES_2;
        }
        return a;
    }
}
