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
