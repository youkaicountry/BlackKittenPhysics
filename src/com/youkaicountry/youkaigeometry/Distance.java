// Copyright (c) <2011> <Nathaniel Caldwell>
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.youkaicountry.youkaigeometry;

public abstract class Distance
{
    public static double distance2D(double x, double y)
    {
        return Math.sqrt(x*x + y*y);
    }
    
    //assuming (ax, ay) to be the top of the line and (bx, by) to be the bottom,
    //the point is to the left if the number is positive, and the point is right if
    //the number is negative. If the number is 0, then the 
    public static double sideOfLine(double px, double py, double ax, double ay, double bx, double by)
    {
        //(Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)
        return (bx-ax)*(py-ay)-(by-ay)*(px-ax);
    }
    
    public static void DistanceFromLine(double px, double py, double ax, double ay, double bx, double by, DistanceFromLineReturn ret)
    {
        
        //
        // find the distance from the point (cx,cy) to the line
        // determined by the points (ax,ay) and (bx,by)
        //
        // distanceSegment = distance from the point to the line segment
        // distanceLine = distance from the point to the line (assuming
        // infinite extent in both directions
        //
        
        /*
         * 
         * Subject 1.02: How do I find the distance from a point to a line?
         * 
         * 
         * Let the point be C (Cx,Cy) and the line be AB (Ax,Ay) to (Bx,By). Let
         * P be the point of perpendicular projection of C on AB. The parameter
         * r, which indicates P's position along AB, is computed by the dot
         * product of AC and AB divided by the square of the length of AB:
         * 
         * (1) AC dot AB r = --------- ||AB||^2
         * 
         * r has the following meaning:
         * 
         * r=0 P = A r=1 P = B r<0 P is on the backward extension of AB r>1 P is
         * on the forward extension of AB 0<r<1 P is interior to AB
         * 
         * The length of a line segment in d dimensions, AB is computed by:
         * 
         * L = sqrt( (Bx-Ax)^2 + (By-Ay)^2 + ... + (Bd-Ad)^2)
         * 
         * so in 2D:
         * 
         * L = sqrt( (Bx-Ax)^2 + (By-Ay)^2 )
         * 
         * and the dot product of two vectors in d dimensions, U dot V is
         * computed:
         * 
         * D = (Ux * Vx) + (Uy * Vy) + ... + (Ud * Vd)
         * 
         * so in 2D:
         * 
         * D = (Ux * Vx) + (Uy * Vy)
         * 
         * So (1) expands to:
         * 
         * (Cx-Ax)(Bx-Ax) + (Cy-Ay)(By-Ay) r = -------------------------------
         * L^2
         * 
         * The point P can then be found:
         * 
         * Px = Ax + r(Bx-Ax) Py = Ay + r(By-Ay)
         * 
         * And the distance from A to P = r*L.
         * 
         * Use another parameter s to indicate the location along PC, with the
         * following meaning: s<0 C is left of AB s>0 C is right of AB s=0 C is
         * on AB
         * 
         * Compute s as follows:
         * 
         * (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay) s = ----------------------------- L^2
         * 
         * 
         * Then the distance from C to P = |s|*L.
         */

        double r_numerator = (px - ax) * (bx - ax) + (py - ay) * (by - ay);
        double r_denomenator = (bx - ax) * (bx - ax) + (by - ay) * (by - ay);
        double r = r_numerator / r_denomenator;
        //
        double lpx = ax + r * (bx - ax);
        double lpy = ay + r * (by - ay);
        //
        double s = ((ay - py) * (bx - ax) - (ax - px) * (by - ay)) / r_denomenator;
        
        ret.ld = Math.abs(s) * Math.sqrt(r_denomenator);
        
        //
        // (xx,yy) is the point on the lineSegment closest to (cx,cy)
        //
        double xx = lpx;
        double yy = lpy;
        
        
        if ((r >= 0) && (r <= 1))
        {
            ret.sd = ret.ld;
        }
        else
        {
            
            double dist1 = (px - ax) * (px - ax) + (py - ay) * (py - ay);
            double dist2 = (px - bx) * (px - bx) + (py - by) * (py - by);
            if (dist1 < dist2)
            {
                xx = ax;
                yy = ay;
                ret.sd = Math.sqrt(dist1);
            }
            else
            {
                xx = bx;
                yy = by;
                ret.sd = Math.sqrt(dist2);
            }
            
        }
        ret.sx = xx;
        ret.sy = yy;
        ret.lx = lpx;
        ret.ly = lpy;
        
        return;
    }
}
