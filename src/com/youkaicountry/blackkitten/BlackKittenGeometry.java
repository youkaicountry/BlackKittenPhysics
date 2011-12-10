package com.youkaicountry.blackkitten;


public class BlackKittenGeometry
{
    public static int makeLine(BlackKitten bc, double x1, double y1, double x2, double y2, int numparticles, boolean bonds)
    {
        double dx = (x2-x1)/(numparticles-1);
        double dy = (y2-y1)/(numparticles-1);
        int startpart = bc.numparticles;
        
        for (int i = 0; i < numparticles; i++)
        {
            double xp = (i * dx) + x1;
            double yp = (i * dy) + y1;
            bc.addParticleFromPrototype(xp, yp);
        }
        
        if (bonds)
        {
            for (int i = 0; i < numparticles-1; i++)
            {
                bc.addBondFromPrototype(startpart+i, startpart+i+1);
            }
        }
        return startpart;
    }
    
    //TODO: Add bonds to quads (gonna be chotto muzukashi)
    public static int makeGrid(BlackKitten bc, double top, double left, double width, double height, int xres, int yres, boolean horizboxes, boolean vertboxes, boolean lrxes, boolean rlxes, boolean quads)
    {
        double dx = (xres-1) > 1 ? width / (double)(xres-1) : 0;
        double dy = (yres-1) > 1 ? height / (double)(yres-1): 0;
        
        //int startpart = bc.allocateParticles(xres*yres);
        //int currpart = startpart;
        int startpart = bc.numparticles;
        for (int y = 0; y < yres; y++)
        {
            for (int x = 0; x < xres; x++)
            {
                bc.addParticleFromPrototype((dx*x)+left, (dy*y)+top);
                //currpart++;
            }
        }
        if (horizboxes)
        {
            for (int y = 0; y < (yres); y++)
            {
                for (int x = 0; x < (xres-1); x++)
                {
                    int p = (y*xres + x) + startpart;
                    bc.addBondFromPrototype(p, p+1);
                }
            }
        }
        
        if (vertboxes)
        {
            for (int x = 0; x < (xres); x++)
            {
                for (int y = 0; y < (yres-1); y++)
                {
                    int p = (y*xres + x) + startpart;
                    bc.addBondFromPrototype(p, p+xres);
                }
            }
        }
        
        if (lrxes || rlxes || quads)
        {
            for (int y = 0; y < (yres-1); y++)
            {
                for (int x = 0; x < (xres-1); x++)
                {
                    int p0 = (y*(xres-1) + x) + startpart + y;
                    if (lrxes) {bc.addBondFromPrototype(p0, p0+xres+1);}
                    if (rlxes) {bc.addBondFromPrototype(p0+1, p0+xres);}
                    if (quads) 
                    {
                        int qp0 = p0; int qp1 = p0+1; int qp2 = p0+xres; int qp3 = p0+xres+1;
                        int qb0 = bc.getBondByParticles(qp0, qp1); int qb1 = bc.getBondByParticles(qp1, qp3);
                        int qb2 = bc.getBondByParticles(qp2, qp3); int qb3 = bc.getBondByParticles(qp0, qp2);
                        //int qb0 = bc.getBondByParticles(qp0, qp1); int qb1 = bc.getBondByParticles(qp1, qp2);
                        //int qb2 = bc.getBondByParticles(qp2, qp3); int qb3 = bc.getBondByParticles(qp0, qp3);
                        bc.addQuadFromPrototype(qp0, qp1, qp2, qp3, qb0, qb1, qb2, qb3);
                        
                    }
                }
            }
        }
        return startpart;
    }
}
