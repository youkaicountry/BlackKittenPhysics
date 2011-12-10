package com.youkaicountry.blackkitten;

//TODO: New time controls: Play, Stop, Pause, Reset, Update Init
//TODO: Group like globals/objects vars into a single getter/setter like: setGlobalConservationCollision_All(conserveon, iterations, ...)
//TODO: QUADS: Breaking
//TODO: Visible field for all objects
//TODO: separate max's for each type
//TODO: Add setParticleProperty(int id, int property, double value);, and a global one, and one for each object type
//TODO: Particle non-collision if on same bond
//TODO: Track Bonds attached to a particle
//TODO: Be able to get bond index by the 2 particles it connects

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.youkaicountry.blackkitten.IntQueue;
import com.youkaicountry.youkaigeometry.Distance;
import com.youkaicountry.youkaigeometry.DistanceFromLineReturn;

public class BlackKitten
{
    // PARTICLE PROPERTIES
    double oldx[], oldy[];
    double ancx[], ancy[];
    double oldxmod[], oldymod[];
    public double xpositioninit[], ypositioninit[];
    public double xposition[], yposition[];
    double xvelocityinit[], yvelocityinit[];
    public double xvelocity[], yvelocity[];
    double xaccelerationinit[], yaccelerationinit[];
    double xacceleration[], yacceleration[];
    double xforce[], yforce[];
    double xforceinit[], yforceinit[];
    public double mass[], massinit[];
    public double radius[], radiusinit[];
    public double efficiency[], efficiencyinit[];
    public double charge[], chargeinit[];
    public int ptag[], ptaginit[];
    // double plocx[], plocy[];
    public boolean pdisabled[];
    boolean pdisabledinit[];
    double ptx[], pty[];
    public int pred[];
    public int pgreen[];
    public int pblue[];
    // Color pcolor[];
    public boolean fixed[];
    boolean pselected[];
    boolean attachsolid[];
    int psprite[];
    
    // BOND PROPERTIES
    public int bparticle1[], bparticle1init[];
    public int bparticle2[], bparticle2init[];
    public double blength[], blengthinit[];
    public double bspringconstant[], bspringconstantinit[];
    public double bbreakforce[], bbreakforceinit[];
    public boolean bdisabled[];
    public boolean bdisabledinit[];
    boolean bcollide[], bcollideinit[];
    boolean bsolid[], bsolidinit[];
    public int btag[], btaginit[];
    double befficiency[], befficiencyinit[];
    public double bthickness[];
    double bthicknessinit[];
    double bfriction[], bfrictioninit[];
    double bstress[];
    public int bred[];
    public int bgreen[];
    public int bblue[];
    // Color bcolor[];
    boolean bselected[];
    public int bsprite[];
    
    // QUAD PROPERTIES
    public int qparticle1[];
    int qparticle1init[];
    public int qparticle2[];
    int qparticle2init[];
    public int qparticle3[];
    int qparticle3init[];
    public int qparticle4[];
    int qparticle4init[];
    public int qbond1[], qbond1init[];
    public int qbond2[], qbond2init[];
    public int qbond3[], qbond3init[];
    public int qbond4[], qbond4init[];
    int qbondstobreak[], qbondstobreakinit[];
    public int qsprite[];
    int qspriteinit[];
    public boolean qdisabled[];
    boolean qdisabledinit[];
    public int qred[];
    public int qgreen[];
    public int qblue[];
    public int qtag[];
    int qtaginit[];
    
    // JET PROPERTIES
    int jparticle1[], jparticle1init[];
    int jparticle2[], jparticle2init[];
    public double jetforce[], jetforceinit[];
    boolean jdisabled[], jdisabledinit[];
    int jtag[], jtaginit[];
    boolean jselected[];
    int jsprite[];
    
    // AIRFOIL PROPERTIES
    int aparticle1[], aparticle1init[];
    int aparticle2[], aparticle2init[];
    double airconstant[], airconstantinit[];
    boolean adisabled[], adisabledinit[];
    int atag[], ataginit[];
    boolean aselected[];
    int asprite[];
    
    // PROTOTYPE PARTICLE
    double xvelocityproto, yvelocityproto;
    double massproto;
    double radiusproto;
    boolean fixedproto;
    double efficiencyproto;
    int ptagproto;
    double chargeproto;
    int predproto, pgreenproto, pblueproto;
    // Color pcolorproto;
    int pspriteproto;
    
    //PROTOTYPE QUAD
    int qbondstobreakproto;
    int qspriteproto;
    int qredproto, qgreenproto, qblueproto;
    int qtagproto;
    
    // PROTOTYPE BOND
    double bspringconstantproto;
    double bbreakforceproto;
    int bredproto, bgreenproto, bblueproto;
    // Color bcolorproto;
    boolean bcollideproto;
    double befficiencyproto;
    double bfrictionproto;
    double bthicknessproto;
    boolean bsolidproto;
    int btagproto;
    int bspriteproto;
    
    // PROTOTYPE JET
    double jetforceproto;
    int jtagproto;
    int jspriteproto;
    
    // PROTOTYPE AIRFOIL
    double airconstantproto;
    int atagproto;
    int aspriteproto;
    
    // GLOBAL HIDDEN
    public int numparticles, numparticlesinit;
    public int numbonds;
    int numbondsinit;
    int numjets, numjetsinit;
    int numairfoils, numairfoilsinit;
    public int numquads;
    int numquadsinit;
    int maxnumparticles;
    
    double movemodleft, movemodright, movemodtop, movemodbottom;
    
    boolean isPlaying; // boolean that determines whether simulation is playing
    boolean isPaused; // boolean that determines whether the simulation is
    // paused
    public int step;// number of steps since
    public double simtime;// time in simulation time units
    
    // GLOBAL USER
    double gravityacc, gravityaccinit;
    double fluidfriction, fluidfrictioninit;
    boolean gravityon, gravityoninit;
    boolean repulsionon, repulsiononinit;
    boolean conserveon, conserveoninit;
    double repulsionconstant, repulsionconstantinit;
    boolean microgravityon, microgravityoninit;
    double microgravityconstant, microgravityconstantinit;
    double groundfriction, groundfrictioninit;
    Random rand = new Random();
    public double dt, dtinit;
    double dragScale;
    public boolean gridon, gridoninit;
    public boolean gridsnapon;
    // Color gridcolor;
    double gridscaleup, gridscaleupinit;
    double gridspacing, gridspacinginit;
    double collisiondistancetolerance;
    int collisioniterations;
    public double groundyposition, groundypositioninit;
    double groundhardness, groundhardnessinit;
    public boolean groundon, groundoninit;
    public boolean wateron, wateroninit;
    double waterfluidfriction, waterfluidfrictioninit;
    public double wateryposition, waterypositioninit;
    double waterdensityscale, waterdensityscaleinit;
    double waterdensity, waterdensityinit;
    boolean bondcollision, bondcollisioninit;
    boolean coulomb, coulombinit;
    double coulombconstant, coulombconstantinit;
    boolean com;
    double timescale, timescaleinit;
    boolean resetbonds;
    
    //event queues
    public LinkedList<Integer> bond_break_events;
    public LinkedList<Integer> quad_break_events;
    public boolean log_bond_death = false;
    public boolean log_quad_death = false;
    
    // VISUAL USER
    public double panelleft;
    public double panelright;
    public double paneltop;
    public double panelbottom;
    double panelleftinit, panelrightinit, paneltopinit, panelbottominit;
    public double camx, camy, camxinit, camyinit, camtheta, camthetainit;
    boolean bondstresscolor, bondstresscolorinit;
    
    
    //Temps
    IntQueue queue;
    
    DistanceFromLineReturn dflr;
    
    String version;
    
    boolean firstPaint;
    
    /**
     * Description of function here.
     * 
     * @param maxparticles
     *            - Description of parameter here.
     */
    public BlackKitten(int maxparticles)
    {
        
        // LineSegment ll = new LineSegment(new Coordinate(10,15),new
        // Coordinate(20,15));
        // System.out.println("lined: "+ll.distance(new Vector2(13,14.0)));
        // Vector2 vt = new Vector2(0,0);
        // ll.getClosestPoint(new Vector2(13,14.0),vt);
        // System.out.println(vt.toString());
         
        //initialize temps
        
        dflr = new DistanceFromLineReturn();
        
        queue = new IntQueue();
        
        firstPaint = true;
        
        resetbonds = true;
        
        isPaused = false;
        
        // jsscript = new StringBuffer();
        
        panelleft = -50;
        panelright = 50;
        panelbottom = 80;
        paneltop = -80;
        panelleftinit = -50;
        panelrightinit = 50;
        panelbottominit = 80;
        paneltopinit = -80;
        
        // blockref = false;
        
        collisiondistancetolerance = .01;
        collisioniterations = 8;
        
        version = "0.4.0"; // only change this when migrating to a new version
        
        camx = -20;
        camxinit = -20;
        camy = 0;
        camyinit = 0;
        camtheta = 0;
        camthetainit = 0;
        com = false;
        
        ptx = new double[maxparticles];
        pty = new double[maxparticles];
        attachsolid = new boolean[maxparticles];
        
        oldx = new double[maxparticles];
        oldy = new double[maxparticles];
        ancx = new double[maxparticles];
        ancy = new double[maxparticles];
        oldxmod = new double[maxparticles];
        oldymod = new double[maxparticles];
        for (int i = 0; i < maxparticles; i++)
        {
            oldxmod[i] = 0;
            oldymod[i] = 0;
        }
        
        qparticle1 = new int[maxparticles];
        qparticle1init = new int[maxparticles];
        qparticle2 = new int[maxparticles];
        qparticle2init = new int[maxparticles];
        qparticle3 = new int[maxparticles];
        qparticle3init = new int[maxparticles];
        qparticle4 = new int[maxparticles];
        qparticle4init = new int[maxparticles];
        qbond1 = new int[maxparticles];
        qbond1init = new int[maxparticles];
        qbond2 = new int[maxparticles];
        qbond2init = new int[maxparticles];
        qbond3 = new int[maxparticles];
        qbond3init = new int[maxparticles];
        qbond4 = new int[maxparticles];
        qbond4init = new int[maxparticles];
        qbondstobreak = new int[maxparticles];
        qbondstobreakinit = new int[maxparticles];
        qsprite = new int[maxparticles];
        qspriteinit = new int[maxparticles];
        qdisabled = new boolean[maxparticles];
        qdisabledinit = new boolean[maxparticles];
        qred = new int[maxparticles];
        qgreen = new int[maxparticles];
        qblue = new int[maxparticles];
        qtag = new int[maxparticles];
        qtaginit = new int[maxparticles];
        
        aparticle1 = new int[maxparticles];
        aparticle1init = new int[maxparticles];
        aparticle2 = new int[maxparticles];
        aparticle2init = new int[maxparticles];
        airconstant = new double[maxparticles];
        airconstantinit = new double[maxparticles];
        adisabled = new boolean[maxparticles];
        adisabledinit = new boolean[maxparticles];
        atag = new int[maxparticles];
        ataginit = new int[maxparticles];
        aselected = new boolean[maxparticles];
        asprite = new int[maxparticles];
        
        jparticle1 = new int[maxparticles];
        jparticle1init = new int[maxparticles];
        jparticle2 = new int[maxparticles];
        jparticle2init = new int[maxparticles];
        jetforce = new double[maxparticles];
        jetforceinit = new double[maxparticles];
        jdisabled = new boolean[maxparticles];
        jdisabledinit = new boolean[maxparticles];
        jtag = new int[maxparticles];
        jtaginit = new int[maxparticles];
        jselected = new boolean[maxparticles];
        jsprite = new int[maxparticles];
        
        bparticle1 = new int[maxparticles];
        bparticle1init = new int[maxparticles];
        bparticle2 = new int[maxparticles];
        bparticle2init = new int[maxparticles];
        blength = new double[maxparticles];
        blengthinit = new double[maxparticles];
        bspringconstant = new double[maxparticles];
        bspringconstantinit = new double[maxparticles];
        bred = new int[maxparticles];
        bgreen = new int[maxparticles];
        bblue = new int[maxparticles];
        bbreakforce = new double[maxparticles];
        bbreakforceinit = new double[maxparticles];
        bdisabled = new boolean[maxparticles];
        bdisabledinit = new boolean[maxparticles];
        bstress = new double[maxparticles];
        bcollide = new boolean[maxparticles];
        bcollideinit = new boolean[maxparticles];
        befficiency = new double[maxparticles];
        befficiencyinit = new double[maxparticles];
        bfriction = new double[maxparticles];
        bfrictioninit = new double[maxparticles];
        bthickness = new double[maxparticles];
        bthicknessinit = new double[maxparticles];
        bsolid = new boolean[maxparticles];
        bsolidinit = new boolean[maxparticles];
        btag = new int[maxparticles];
        btaginit = new int[maxparticles];
        bselected = new boolean[maxparticles];
        bsprite = new int[maxparticles];
        
        pselected = new boolean[maxparticles];
        ptag = new int[maxparticles];
        ptaginit = new int[maxparticles];
        charge = new double[maxparticles];
        chargeinit = new double[maxparticles];
        pdisabled = new boolean[maxparticles];
        pdisabledinit = new boolean[maxparticles];
        fixed = new boolean[maxparticles];
        xposition = new double[maxparticles];
        yposition = new double[maxparticles];
        xpositioninit = new double[maxparticles];
        ypositioninit = new double[maxparticles];
        xvelocity = new double[maxparticles];
        yvelocity = new double[maxparticles];
        xvelocityinit = new double[maxparticles];
        yvelocityinit = new double[maxparticles];
        xacceleration = new double[maxparticles];
        yacceleration = new double[maxparticles];
        xaccelerationinit = new double[maxparticles];
        yaccelerationinit = new double[maxparticles];
        xforce = new double[maxparticles];
        yforce = new double[maxparticles];
        xforceinit = new double[maxparticles];
        yforceinit = new double[maxparticles];
        mass = new double[maxparticles];
        massinit = new double[maxparticles];
        radius = new double[maxparticles];
        radiusinit = new double[maxparticles];
        efficiency = new double[maxparticles];
        efficiencyinit = new double[maxparticles];
        pred = new int[maxparticles];
        pgreen = new int[maxparticles];
        pblue = new int[maxparticles];
        // pcolor = new Color[maxparticles];
        // plocx = new double[maxparticles];
        // plocy = new double[maxparticles];
        psprite = new int[maxparticles];
        
        maxnumparticles = maxparticles;
        newExperiment();
        
        this.quad_break_events = new LinkedList<Integer>();
        this.bond_break_events = new LinkedList<Integer>();
        
        return;
    }
    
    /**
     * Sets the values of the experiment to a rational beginning point.
     */
    public void newExperiment()
    {
        isPlaying = false;
        isPaused = false;
        // fixed[0] = false;
        // sel_index = 0;
        
        double nw = (100 * (panelrightinit - panelleftinit)) / (panelbottominit - paneltopinit);
        panelleftinit = -(nw / 2);
        panelrightinit = nw / 2;
        panelbottominit = 50;
        paneltopinit = -50;
        
        bondstresscolorinit = false;
        repulsionconstant = 20.0;
        repulsionconstantinit = 20.0;
        microgravityon = false;
        microgravityoninit = false;
        microgravityconstant = .1;
        microgravityconstantinit = .1;
        numparticles = 0;
        numparticlesinit = 0;
        numbonds = 0;
        numbondsinit = 0;
        repulsiononinit = true;
        repulsionon = true;
        conserveoninit = false;
        conserveon = false;
        bspringconstantproto = 3000;
        
        // bcolorproto = ColorFactory.getColor(30, 200, 200);
        
        bbreakforceproto = 3000;
        groundfriction = .7;
        groundfrictioninit = .7;
        bondcollision = true;
        bondcollisioninit = true;
        coulomb = false;
        coulombinit = false;
        coulombconstant = 5;
        coulombconstantinit = 5;
        
        timescale = 1;
        timescaleinit = 1;
        
        gridsnapon = false;
        
        bredproto = 30;
        bgreenproto = 200;
        bblueproto = 200;
        befficiencyproto = .4;
        bfrictionproto = .02;
        bthicknessproto = .8;
        bsolidproto = false;
        btagproto = 0;
        
        atagproto = 0;
        aspriteproto = 0;
        
        jtagproto = 0;
        jspriteproto = 0;
        
        for (int i = 0; i < numparticles; i++)
        {
            pselected[i] = false;
        }
        // pselected[0] = true;
        
        camx = -10;
        camxinit = -10;
        camy = 10;
        camyinit = 10;
        camtheta = 0;
        camthetainit = 0;
        
        xvelocityproto = 0;
        yvelocityproto = 0;
        massproto = 1.0;
        radiusproto = 1.0;
        chargeproto = 0;
        ptagproto = 0;
        efficiencyproto = 1.0;
        // pcolorproto = ColorFactory.getColor(255, 255, 255);
        predproto = 255;
        pgreenproto = 255;
        pblueproto = 255;
        bcollideproto = false;
        /*
         * pdisabled[0] = false; pdisabledinit[0] = false; xposition[0] = 0.0;
         * yposition[0] = 0.0; xpositioninit[0] = 0.0; ypositioninit[0] = 0.0;
         * mass[0] = 1.0; massinit[0] = 1.0; charge[0] = 0; ptag[0] = 0;
         * radius[0] = 1.0; radiusinit[0] = 1.0; efficiency[0] = .75;
         * efficiencyinit[0] = .75; // pcolor[0] = ColorFactory.getColor(255,
         * 255, 255); pred[0] = 255; pgreen[0] = 255; pblue[0] = 255;
         */
        gravityon = true;
        gravityoninit = true;
        gravityacc = 9.8;
        gravityaccinit = 9.8;
        fluidfriction = .91;
        fluidfrictioninit = .91;
        isPlaying = false;
        dragScale = 2;
        dt = .005;
        dtinit = .005;
        
        gridon = false;
        gridoninit = false;
        gridspacing = 10;
        gridspacinginit = 10;
        gridscaleup = 100;
        gridscaleupinit = 100;
        // gridcolor = ColorFactory.getColor(255, 255, 255);
        bondstresscolor = false;
        bondstresscolorinit = false;
        
        groundon = true;
        groundoninit = true;
        groundyposition = 30;
        groundypositioninit = 30;
        groundhardness = .75;
        groundhardnessinit = .75;
        wateron = false;
        wateroninit = false;
        waterfluidfriction = .6;
        waterfluidfrictioninit = .6;
        wateryposition = 40;
        waterypositioninit = 40;
        waterdensity = 1;
        waterdensityinit = 1;
        waterdensityscale = .01;
        waterdensityscaleinit = .01;
        
        numquads = 0;
        numquadsinit = 0;
        numjets = 0;
        numjetsinit = 0;
        numairfoils = 0;
        numairfoilsinit = 0;
        airconstantproto = .01;
        jetforceproto = 0;
        return;
    }
    
    /**
     * Resets the experiment back to init, and starts it playing.
     */
    public void resetAndPlay()
    {
        stopReset();
        startPlaying();
    }
    
    /**
     * Starts the experiment playing from wherever it is.
     */
    public void startPlaying()
    {
        isPlaying = true;
        isPaused = false;
    }
    
    /**
     * Pauses the simulation.
     */
    public void pausePlaying()
    {
        isPlaying = true;
        isPaused = true;
    }
    
    /**
     * Stops and resets the state of the simulation.
     */
    public void stopReset()
    {
        for (int i = 0; i < numbonds; i++)
        {
            attachsolid[i] = false;
        }
        isPlaying = false;
        isPaused = false;
        
        dt = dtinit;
        numparticles = numparticlesinit;
        numbonds = numbondsinit;
        numjets = numjetsinit;
        numairfoils = numairfoilsinit;
        repulsionon = repulsiononinit;
        conserveon = conserveoninit;
        repulsionconstant = repulsionconstantinit;
        microgravityon = microgravityoninit;
        microgravityconstant = microgravityconstantinit;
        step = 0;
        groundfriction = groundfrictioninit;
        gridspacing = gridspacinginit;
        gridon = gridoninit;
        gridscaleup = gridscaleupinit;
        groundon = groundoninit;
        groundyposition = groundypositioninit;
        groundhardness = groundhardnessinit;
        wateron = wateroninit;
        waterfluidfriction = waterfluidfrictioninit;
        wateryposition = waterypositioninit;
        waterdensity = waterdensityinit;
        waterdensityscale = waterdensityscaleinit;
        bondstresscolor = bondstresscolorinit;
        camx = camxinit;
        camy = camyinit;
        camtheta = camthetainit;
        bondcollision = bondcollisioninit;
        coulomb = coulombinit;
        coulombconstant = coulombconstantinit;
        timescale = timescaleinit;
        for (int i = 0; i < numparticles; i++)
        {
            xposition[i] = xpositioninit[i];
            yposition[i] = ypositioninit[i];
            xvelocity[i] = xvelocityinit[i];
            yvelocity[i] = yvelocityinit[i];
            xacceleration[i] = xaccelerationinit[i];
            yacceleration[i] = yaccelerationinit[i];
            xforce[i] = xforceinit[i];
            yforce[i] = yforceinit[i];
            mass[i] = massinit[i];
            radius[i] = radiusinit[i];
            efficiency[i] = efficiencyinit[i];
            pdisabled[i] = pdisabledinit[i];
            ptag[i] = ptaginit[i];
            charge[i] = chargeinit[i];
        }
        
        for (int i = 0; i < numbonds; i++)
        {
            bparticle1[i] = bparticle1init[i];
            bparticle2[i] = bparticle2init[i];
            blength[i] = blengthinit[i];
            bspringconstant[i] = bspringconstantinit[i];
            bbreakforce[i] = bbreakforceinit[i];
            bdisabled[i] = bdisabledinit[i];
            bcollide[i] = bcollideinit[i];
            befficiency[i] = befficiencyinit[i];
            bfriction[i] = bfrictioninit[i];
            bthickness[i] = bthicknessinit[i];
            bsolid[i] = bsolidinit[i];
            btag[i] = btaginit[i];
        }
        
        for (int i = 0; i < numjets; i++)
        {
            jparticle1[i] = jparticle1init[i];
            jparticle2[i] = jparticle2init[i];
            jetforce[i] = jetforceinit[i];
            jdisabled[i] = jdisabledinit[i];
            jtag[i] = jtaginit[i];
        }
        
        for (int i = 0; i < numairfoils; i++)
        {
            aparticle1[i] = aparticle1init[i];
            aparticle2[i] = aparticle2init[i];
            airconstant[i] = airconstantinit[i];
            adisabled[i] = adisabledinit[i];
            atag[i] = ataginit[i];
        }
        
        for (int i = 0; i < numquads; i++)
        {
            qparticle1[i] = qparticle1init[i];
            qparticle2[i] = qparticle2init[i];
            qparticle3[i] = qparticle3init[i];
            qparticle4[i] = qparticle4init[i];
            qbond1[i] = qbond1init[i];
            qbond2[i] = qbond2init[i];
            qbond3[i] = qbond3init[i];
            qbond4[i] = qbond4init[i];
            qbondstobreak[i] = qbondstobreakinit[i];
            qsprite[i] = qspriteinit[i];
            qdisabled[i] = qdisabledinit[i];
            qtag[i] = qtaginit[i];
        }
        
        gravityon = gravityoninit;
        gravityacc = gravityaccinit;
        fluidfriction = fluidfrictioninit;
        panelleft = panelleftinit;
        panelright = panelrightinit;
        paneltop = paneltopinit;
        panelbottom = panelbottominit;
        return;
    }
    
    /**
     * Stops and sets the initial state of the simulation to the current state.
     */
    public void stopUpdate()
    {
        isPlaying = false;
        isPaused = false;
        dtinit = dt;
        numparticlesinit = numparticles;
        numbondsinit = numbonds;
        numjetsinit = numjets;
        numairfoilsinit = numairfoils;
        repulsiononinit = repulsionon;
        conserveoninit = conserveon;
        repulsionconstantinit = repulsionconstant;
        microgravityoninit = microgravityon;
        microgravityconstantinit = microgravityconstant;
        step = 0;
        groundfrictioninit = groundfriction;
        gridspacinginit = gridspacing;
        gridoninit = gridon;
        gridscaleupinit = gridscaleup;
        groundoninit = groundon;
        groundypositioninit = groundyposition;
        groundhardnessinit = groundhardness;
        wateroninit = wateron;
        waterfluidfrictioninit = waterfluidfriction;
        waterypositioninit = wateryposition;
        waterdensityinit = waterdensity;
        waterdensityscaleinit = waterdensityscale;
        bondstresscolorinit = bondstresscolor;
        camxinit = camx;
        camyinit = camy;
        camthetainit = camtheta;
        bondcollisioninit = bondcollision;
        coulombinit = coulomb;
        coulombconstantinit = coulombconstant;
        timescaleinit = timescale;
        for (int i = 0; i < numparticles; i++)
        {
            xpositioninit[i] = xposition[i];
            ypositioninit[i] = yposition[i];
            xvelocityinit[i] = xvelocity[i];
            yvelocityinit[i] = yvelocity[i];
            xaccelerationinit[i] = xacceleration[i];
            yaccelerationinit[i] = yacceleration[i];
            xforceinit[i] = xforce[i];
            yforceinit[i] = yforce[i];
            massinit[i] = mass[i];
            radiusinit[i] = radius[i];
            efficiencyinit[i] = efficiency[i];
            pdisabledinit[i] = pdisabled[i];
            ptaginit[i] = ptag[i];
            chargeinit[i] = charge[i];
        }
        
        for (int i = 0; i < numbonds; i++)
        {
            bparticle1init[i] = bparticle1[i];
            bparticle2init[i] = bparticle2[i];
            blengthinit[i] = blength[i];
            bspringconstantinit[i] = bspringconstant[i];
            bbreakforceinit[i] = bbreakforce[i];
            bdisabledinit[i] = bdisabled[i];
            bcollideinit[i] = bcollide[i];
            befficiencyinit[i] = befficiency[i];
            bfrictioninit[i] = bfriction[i];
            bthicknessinit[i] = bthickness[i];
            bsolidinit[i] = bsolid[i];
            btaginit[i] = btag[i];
        }
        
        for (int i = 0; i < numjets; i++)
        {
            jparticle1init[i] = jparticle1[i];
            jparticle2init[i] = jparticle2[i];
            jetforceinit[i] = jetforce[i];
            jdisabledinit[i] = jdisabled[i];
            jtaginit[i] = jtag[i];
        }
        
        for (int i = 0; i < numairfoils; i++)
        {
            aparticle1init[i] = aparticle1[i];
            aparticle2init[i] = aparticle2[i];
            airconstantinit[i] = airconstant[i];
            adisabledinit[i] = adisabled[i];
            ataginit[i] = atag[i];
        }
        
        for (int i = 0; i < numquads; i++)
        {
            qparticle1init[i] = qparticle1[i];
            qparticle2init[i] = qparticle2[i];
            qparticle3init[i] = qparticle3[i];
            qparticle4init[i] = qparticle4[i];
            qbond1init[i] = qbond1[i];
            qbond2init[i] = qbond2[i];
            qbond3init[i] = qbond3[i];
            qbond4init[i] = qbond4[i];
            qbondstobreakinit[i] = qbondstobreak[i];
            qspriteinit[i] = qsprite[i];
            qdisabledinit[i] = qdisabled[i];
            qtaginit[i] = qtag[i];
        }
        
        gravityoninit = gravityon;
        gravityaccinit = gravityacc;
        fluidfrictioninit = fluidfriction;
        panelleftinit = panelleft;
        panelrightinit = panelright;
        paneltopinit = paneltop;
        panelbottominit = panelbottom;
        return;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** PARTICLE EDITING METHODS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    // TODO: Have copy from proto use this, and copy to proto
    // TODO: Have newExperiment use this
    // TODO: Have addParticleFromPrototype, setParticleFromPrototype,
    // copyPrototypeFromParticle
    // ADDS A NEW PARTICLE AT THE END OF THE STACK WITH THE GIVEN PROPERTIES
    public int addParticle(double x, double y, double xvel, double yvel, double xacc, double yacc, double xforce, double yforce, double mass, double radius, double efficiency, boolean disabled, int tag, double charge, int red, int green, int blue, boolean fixed)
    {
        int n = this.numparticles;
        this.setParticle(n, x, y, xvel, yvel, xacc, yacc, xforce, yforce, mass, radius, efficiency, disabled, tag, charge, red, green, blue, fixed);
        this.numparticles++;
        if (!isPlaying && !isPaused)
        {
            this.numparticlesinit++;
        }
        return n;
    }
    
    public int addParticle(double x, double y)
    {
        int n = this.numparticles;
        this.clearParticle(n);
        this.numparticles++;
        this.xposition[n] = x;
        this.yposition[n] = y;
        if (!isPlaying && !isPaused)
        {
            this.numparticlesinit++;
            this.xpositioninit[n] = x;
            this.ypositioninit[n] = y;
        }
        return n;
    }
    
    // SETS ALL OF THE VALUES OF THE GIVEN PARTICLE ID
    public void setParticle(int id, double x, double y, double xvel, double yvel, double xacc, double yacc, double xforce, double yforce, double mass, double radius, double efficiency, boolean disabled, int tag, double charge, int red, int green, int blue, boolean fixed)
    {
        if (!isPlaying && !isPaused)
        {
            this.xpositioninit[id] = x;
            this.ypositioninit[id] = y;
            this.xvelocityinit[id] = xvel;
            this.yvelocityinit[id] = yvel;
            this.xaccelerationinit[id] = xacc;
            this.yaccelerationinit[id] = yacc;
            this.xforceinit[id] = xforce;
            this.yforceinit[id] = yforce;
            this.massinit[id] = mass;
            this.radiusinit[id] = radius;
            this.efficiencyinit[id] = efficiency;
            this.pdisabledinit[id] = disabled;
            this.ptaginit[id] = tag;
            this.chargeinit[id] = charge;
        }
        this.xposition[id] = x;
        this.yposition[id] = y;
        this.xvelocity[id] = xvel;
        this.yvelocity[id] = yvel;
        this.xacceleration[id] = xacc;
        this.yacceleration[id] = yacc;
        this.xforce[id] = xforce;
        this.yforce[id] = yforce;
        this.mass[id] = mass;
        this.radius[id] = radius;
        this.efficiency[id] = efficiency;
        this.pdisabled[id] = disabled;
        this.ptag[id] = tag;
        this.charge[id] = charge;
        this.pred[id] = red;
        this.pgreen[id] = green;
        this.pblue[id] = blue;
        this.fixed[id] = fixed;
    }
    
    public void setParticlePrototype(double xvel, double yvel, double mass, double radius, boolean fixed, double efficiency, int tag, double charge, int red, int green, int blue)
    {
        this.xvelocityproto = xvel;
        this.yvelocityproto = yvel;
        this.massproto = mass;
        this.radiusproto = radius;
        this.fixedproto = fixed;
        this.efficiencyproto = efficiency;
        this.ptagproto = tag;
        this.chargeproto = charge;
        this.predproto = red;
        this.pgreenproto = green;
        this.pblueproto = blue;
    }
    
    // SETS THE GIVEN PARTICLE'S VALUES TO THOSE OF THE PROTOTYPE PARTICLE
    public void setParticleFromPrototype(int id)
    {
        this.setParticle(id, xposition[id], yposition[id], xvelocityproto, yvelocityproto, xacceleration[id], yacceleration[id], xforce[id], yforce[id], massproto, radiusproto, efficiencyproto, pdisabled[id], ptagproto, chargeproto, predproto, pgreenproto, pblueproto, fixedproto);
    }
    
    /**
     * Description.
     * 
     * @param x
     *            - blah
     * @param y
     *            - bloop
     * @return - The id of the new particle.
     */
    public int addParticleFromPrototype(double x, double y)
    {
        int n = allocateParticles(1);
        if (!isPaused && !isPlaying)
        {
            xpositioninit[n] = x;
            ypositioninit[n] = y;
        }
        xposition[n] = x;
        yposition[n] = y;
        this.setParticleFromPrototype(n);
        return n;
    }
    
    public void setPrototypeFromParticle(int id)
    {
        this.setParticlePrototype(xvelocity[id], yvelocity[id], mass[id], radius[id], fixed[id], efficiency[id], ptag[id], charge[id], pred[id], pgreen[id], pblue[id]);
    }
    
    public void clearParticle(int i)
    {
        
        if (!isPlaying && !isPaused)
        {
            xpositioninit[i] = 0.0;
            ypositioninit[i] = 0.0;
            xvelocityinit[i] = 0.0;
            yvelocityinit[i] = 0.0;
            massinit[i] = 1.0;
            chargeinit[i] = 0;
            ptaginit[i] = 0;
            radiusinit[i] = 1.0;
            efficiencyinit[i] = .75;
            pdisabledinit[i] = false;
        }
        xposition[i] = 0.0;
        yposition[i] = 0.0;
        xvelocity[i] = 0.0;
        yvelocity[i] = 0.0;
        mass[i] = 1.0;
        charge[i] = 0;
        ptag[i] = 0;
        radius[i] = 1.0;
        efficiency[i] = .75;
        pred[i] = 255;
        pgreen[i] = 255;
        pblue[i] = 255;
        pdisabled[i] = false;
        fixed[i] = false;
        pselected[i] = false;
    }
    
    public int allocateParticles(int num) // adds num new particles to the top,
    // and returns the first new particle
    // num
    {
        int a = numparticles;
        if (!isPlaying && !isPaused)
        {
            numparticlesinit += num;
        }
        numparticles += num;
        for (int i = a; i < numparticles; i++)
        {
            clearParticle(i);
        }
        return a;
    }
    
    public void applyForceToParticle(double forcex, double forcey, int particleid)
    {
        xforce[particleid] += forcex;
        yforce[particleid] += forcey;
        return;
    }
    
    /**
     * Applies the given acceleration to all particles that have a tag matching
     * those in the tags objects. If tags is null, then it will apply to every
     * particle.
     * 
     * @param forcex
     * @param forcey
     * @param tags
     */
    public void applyForceToAllParticles(double forcex, double forcey, HashSet tags)
    {
        boolean nulltags = tags == null;
        for (int i = 0; i < numparticles; i++)
        {
            if (!nulltags && !tags.contains(ptag[i]))
            {
                continue;
            }
            if (!pdisabled[i] && !fixed[i])
            {
                xforce[i] += forcex;
                yforce[i] += forcey;
            }
        }
        return;
    }
    
    /**
     * Applies the given acceleration to all particles that have a tag matching
     * those in the tags objects. If tags is null, then it will apply to every
     * particle.
     * 
     * @param forcex
     * @param forcey
     * @param tags
     */
    public void applyAccelerationToAllParticles(double accx, double accy, HashSet tags)
    {
        boolean nulltags = tags == null;
        for (int i = 0; i < numparticles; i++)
        {
            if (!nulltags && !tags.contains(ptag[i]))
            {
                continue;
            }
            if (!pdisabled[i] && !fixed[i])
            {
                xforce[i] += accx * mass[i];
                yforce[i] += accy * mass[i];
            }
        }
        return;
    }
    
    public void deleteStuffOnParticle(int i)
    {
        for (int a = 0; a < numbonds; a++)
        {
            if (bparticle1[a] == i || bparticle2[a] == i)
            {
                if (isPlaying || isPaused)
                {
                    bdisabled[a] = true;
                }
                else
                {
                    bdisabled[a] = true;
                    bdisabledinit[a] = true;
                }
            }
        }
        for (int a = 0; a < numjets; a++)
        {
            if (jparticle1[a] == i || jparticle2[a] == i)
            {
                if (isPlaying || isPaused)
                {
                    jdisabled[a] = true;
                }
                else
                {
                    jdisabled[a] = true;
                    jdisabledinit[a] = true;
                }
            }
        }
        for (int a = 0; a < numairfoils; a++)
        {
            if (aparticle1[a] == i || aparticle2[a] == i)
            {
                if (isPlaying || isPaused)
                {
                    adisabled[a] = true;
                }
                else
                {
                    adisabled[a] = true;
                    adisabledinit[a] = true;
                }
            }
        }
        return;
    }
    
    public double getParticleSpeed(int index)
    {
        double xvel = xvelocity[index];
        double yvel = yvelocity[index];
        return Math.sqrt(xvel*xvel + yvel*yvel);
    }
    
    //TODO: Fix by also correcting ancx/y?
    public void setPX(int index, double value)
    {
        if (!this.isPlaying && !this.isPaused)
        {
            this.xposition[index] = value; this.xpositioninit[index] = value;
        }
        else
        {
            this.xposition[index] = value;
            this.oldxmod[index] += oldx[index]-value;
        }
        return;
    }
    
    public void setPY(int index, double value)
    {
        if (!this.isPlaying && !this.isPaused)
        {
            this.yposition[index] = value; this.ypositioninit[index] = value;
        }
        else
        {
            this.yposition[index] = value;
            this.oldymod[index] += oldy[index]-value;
        }
        return;
    }
    
    public void setParticleRadius(int index, double value)
    {
    	if (!this.isPlaying && !this.isPaused)
    	{
    		this.radius[index] = value; this.radiusinit[index] = value;
    	}
    	else
    	{
    		this.radius[index] = value;
    	}
    	return;
    }
    
    public void moveSelectedParticles(double hx, double hy)
    {
        double xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;
        // Double.POSITIVE_INFINITY;
        for (int i = 0; i < numparticles; i++)
        {
            if (pselected[i] == true && !pdisabled[i])
            {
                if (xpositioninit[i] < xmin)
                {
                    xmin = xpositioninit[i];
                }
                if (xpositioninit[i] > xmax)
                {
                    xmax = xpositioninit[i];
                }
                if (ypositioninit[i] < ymin)
                {
                    ymin = ypositioninit[i];
                }
                if (ypositioninit[i] > ymax)
                {
                    ymax = ypositioninit[i];
                }
            }
        }
        double xc = ((xmax - xmin) / 2) + xmin;
        double yc = ((ymax - ymin) / 2) + ymin;
        double gridx = 0;
        double gridy = 0;
        
        // if (gridsnapon == true) {gridx = gridSnap(hx);gridy = gridSnap(hy);}
        // else {gridx = hx; gridy = hy;}
        // INSTEAD DO THIS:
        gridx = hx;
        gridy = hy;
        
        for (int i = 0; i < numparticles; i++)
        {
            if (pselected[i] == true)
            {
                // System.out.println("ex: "+ex+"ey: "+ey);
                // System.out.println("xc: "+xc+" yc: "+yc);
                // System.out.println("xpos: "+(ex + (sim.xposition[i] - xc)));
                xposition[i] = gridx + (xposition[i] - xc);
                xpositioninit[i] = xposition[i];
                yposition[i] = gridy + (yposition[i] - yc);
                ypositioninit[i] = yposition[i];
                resetBondsOnParticle(i);
            }
        }
        return;
    }
    
    public int findNearestParticle(double xloc, double yloc)
    {
        double d_closest, dx, dy, d;
        int i_closest = -1;
                 d_closest = Double.POSITIVE_INFINITY;
                 for(int i=0;i<numparticles;i++)
                 {
                    //sim.pselected[i] = false;
                    dx = xposition[i]-xloc;
                    dy = yposition[i]-yloc;
                    d = dx*dx+dy*dy;
                    if ((d < d_closest) && !pdisabled[i])
                    {
                       d_closest = d;
                       i_closest = i;
                    }
                 }
                 return i_closest;
        }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** QUAD EDITING METHODS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public int addQuad(int particle1, int particle2, int particle3, int particle4, int bond1, int bond2, int bond3, int bond4, int bondstobreak, int sprite, boolean disabled, int tag, int red, int green, int blue)
    {
        int n = this.numquads;
        this.setQuad(n, particle1, particle2, particle3, particle4, bond1, bond2, bond3, bond4, bondstobreak, sprite, disabled, tag, red, green, blue);
        this.numquads++;
        if (!isPlaying && !isPaused)
        {
            this.numquadsinit++;
        }
        return n;
    }
    
    public int addQuad(int particle1, int particle2, int particle3, int particle4, int bond1, int bond2, int bond3, int bond4)
    {
        int n = this.numquads;
        this.clearQuad(n);
        this.numquads++;
        this.qparticle1[n] = particle1;
        this.qparticle2[n] = particle2;
        this.qparticle3[n] = particle3;
        this.qparticle4[n] = particle4;
        this.qbond1[n] = bond1;
        this.qbond2[n] = bond2;
        this.qbond3[n] = bond3;
        this.qbond4[n] = bond4;
        if (!isPlaying && !isPaused)
        {
            this.qparticle1[n] = particle1;
            this.qparticle2[n] = particle2;
            this.qparticle3[n] = particle3;
            this.qparticle4[n] = particle4;
            this.qbond1[n] = bond1;
            this.qbond2[n] = bond2;
            this.qbond3[n] = bond3;
            this.qbond4[n] = bond4;
            this.numquadsinit++;
        }
        return n;
    }
    
    public int addQuadFromPrototype(int particle1, int particle2, int particle3, int particle4, int bond1, int bond2, int bond3, int bond4)
    {
        int n = allocateQuads(1);
        this.qparticle1[n] = particle1;
        this.qparticle2[n] = particle2;
        this.qparticle3[n] = particle3;
        this.qparticle4[n] = particle4;
        this.qbond1[n] = bond1;
        this.qbond2[n] = bond2;
        this.qbond3[n] = bond3;
        this.qbond4[n] = bond4;
        if (!isPlaying && !isPaused)
        {
            this.qparticle1init[n] = particle1;
            this.qparticle2init[n] = particle2;
            this.qparticle3init[n] = particle3;
            this.qparticle4init[n] = particle4;
            this.qbond1init[n] = bond1;
            this.qbond2init[n] = bond2;
            this.qbond3init[n] = bond3;
            this.qbond4init[n] = bond4;
            this.numquadsinit++;
        }
        this.setQuadFromPrototype(n);
        return n;
    }
    
    /**
     * Sets the values of bond ID
     * 
     * @param id
     * @param particle1
     * @param particle2
     * @param length
     * @param lengthcalc
     *            - If true, length is ignored and calculated from actual
     *            length.
     * @param springconstant
     * @param red
     * @param green
     * @param blue
     * @param breakforce
     * @param disabled
     * @param stress
     * @param collide
     * @param efficiency
     * @param friction
     * @param thickness
     * @param solid
     * @param tag
     */
    public void setQuad(int id, int particle1, int particle2, int particle3, int particle4, int bond1, int bond2, int bond3, int bond4, int bondstobreak, int sprite, boolean disabled, int tag, int red, int green, int blue)
    {
        if (!isPlaying && !isPaused)
        {
            this.qparticle1init[id] = particle1;
            this.qparticle2init[id] = particle2;
            this.qparticle3init[id] = particle3;
            this.qparticle4init[id] = particle4;
            this.qbond1init[id] = bond1;
            this.qbond2init[id] = bond2;
            this.qbond3init[id] = bond3;
            this.qbond4init[id] = bond4;
            this.qbondstobreakinit[id] = bondstobreak;
            this.qspriteinit[id] = sprite;
            this.qdisabledinit[id] = disabled;
            this.qtaginit[id] = tag;
        }
        this.qred[id] = red;
        this.qgreen[id] = green;
        this.qblue[id] = blue;
        this.qparticle1[id] = particle1;
        this.qparticle2[id] = particle2;
        this.qparticle3[id] = particle3;
        this.qparticle4[id] = particle4;
        this.qbond1[id] = bond1;
        this.qbond2[id] = bond2;
        this.qbond3[id] = bond3;
        this.qbond4[id] = bond4;
        this.qbondstobreak[id] = bondstobreak;
        this.qdisabled[id] = disabled;
        this.qtag[id] = tag;
        this.qsprite[id] = sprite;
        return;
    }
    
    public void setQuadPrototype(int bondstobreak, int sprite, int tag, int red, int green, int blue)
    {
      //PROTOTYPE QUAD
        qbondstobreakproto = bondstobreak;
        qspriteproto = sprite;
        qtagproto = tag;
        qredproto = red;
        qgreenproto = green;
        qblueproto = blue;
    }
    
    public void clearQuad(int id)
    {
        if (!isPlaying && !isPaused)
        {
            this.qparticle1init[id] = 0;
            this.qparticle2init[id] = 0;
            this.qparticle3init[id] = 0;
            this.qparticle4init[id] = 0;
            this.qbond1init[id] = 0;
            this.qbond2init[id] = 0;
            this.qbond3init[id] = 0;
            this.qbond4init[id] = 0;
            this.qbondstobreakinit[id] = 1;
            this.qspriteinit[id] = 0;
            this.qdisabled[id] = false;
            this.qtaginit[id] = 0;
        }
        this.qred[id] = 0;
        this.qgreen[id] = 0;
        this.qblue[id] = 0;
        this.qparticle1[id] = 0;
        this.qparticle2[id] = 0;
        this.qparticle3[id] = 0;
        this.qparticle4[id] = 0;
        this.qbond1[id] = 0;
        this.qbond2[id] = 0;
        this.qbond3[id] = 0;
        this.qbond4[id] = 0;
        this.qbondstobreak[id] = 1;
        this.qdisabled[id] = false;
        this.qtag[id] = 0;
        this.qsprite[id] = 0;
    }
    
    public void setPrototypeFromQuad(int index)
    {
        qbondstobreakproto = qbondstobreak[index];
        qspriteproto = qsprite[index];
        qtagproto = qtag[index];
        qredproto = qred[index];
        qgreenproto = qgreen[index];
        qblueproto = qblue[index];
        return;
    }
    
    public void setQuadFromPrototype(int o)
    {
        this.setQuad(o, qparticle1[o], qparticle2[o], qparticle3[o], qparticle4[o], qbond1[o], qbond2[o], qbond3[o], qbond4[o], qbondstobreakproto, qspriteproto, qdisabled[o], qtagproto, qredproto, qgreenproto, qblueproto);
        return;
    }
    
    public int allocateQuads(int num) // adds num new particles to the top, and
    // returns the first new particle num
    {
        int a = numquads;
        if (!isPlaying && !isPaused)
        {
            numquadsinit += num;
        }
        numquads += num;
        for (int i = a; i < numquads; i++)
        {
            clearQuad(i);
        }
        return a;
    }
    
    // public void recalcBond(int i)
    // {
    // int p1 = bparticle1[i];
    // int p2 = bparticle2[i];
    // double tempa = (xposition[p2] - xposition[p1]);
    // double tempb = (yposition[p2] - yposition[p1]);
    // blength[i] = Math.sqrt((tempa * tempa) + (tempb * tempb));
    // blengthinit[i] = blength[i];
    // return;
    // }
    
 // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** BOND EDITING METHODS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public int addBond(int particle1, int particle2, double length, boolean lengthcalc, double springconstant, int red, int green, int blue, double breakforce, boolean disabled, double stress, boolean collide, double efficiency, double friction, double thickness, boolean solid, int tag, int sprite)
    {
        int n = this.numbonds;
        this.setBond(n, particle1, particle2, length, lengthcalc, springconstant, red, green, blue, breakforce, disabled, stress, collide, efficiency, friction, thickness, solid, tag, sprite);
        this.numbonds++;
        if (!isPlaying && !isPaused)
        {
            this.numbondsinit++;
        }
        return n;
    }
    
    public int addBond(int particle1, int particle2)
    {
        int n = this.numbonds;
        this.clearBond(n);
        this.numbonds++;
        this.bparticle1[n] = particle1;
        this.bparticle2[n] = particle2;
        if (!isPlaying && !isPaused)
        {
            this.bparticle1init[n] = particle1;
            this.bparticle2init[n] = particle2;
            this.numbondsinit++;
        }
        resetBondLength(n);
        return n;
    }
    
    public int addBondFromPrototype(int particle1, int particle2)
    {
        int n = allocateBonds(1);
        if (!isPaused && !isPlaying)
        {
            bparticle1init[n] = particle1;
            bparticle2init[n] = particle2;
        }
        bparticle1[n] = particle1;
        bparticle2[n] = particle2;
        this.setBondFromPrototype(n);
        this.resetBondLength(n);
        return n;
    }
    
    /**
     * Sets the values of bond ID
     * 
     * @param id
     * @param particle1
     * @param particle2
     * @param length
     * @param lengthcalc
     *            - If true, length is ignored and calculated from actual
     *            length.
     * @param springconstant
     * @param red
     * @param green
     * @param blue
     * @param breakforce
     * @param disabled
     * @param stress
     * @param collide
     * @param efficiency
     * @param friction
     * @param thickness
     * @param solid
     * @param tag
     */
    public void setBond(int id, int particle1, int particle2, double length, boolean lengthcalc, double springconstant, int red, int green, int blue, double breakforce, boolean disabled, double stress, boolean collide, double efficiency, double friction, double thickness, boolean solid, int tag, int sprite)
    {
        if (!isPlaying && !isPaused)
        {
            this.bparticle1init[id] = particle1;
            this.bparticle2init[id] = particle2;
            this.blengthinit[id] = length;
            this.bspringconstantinit[id] = springconstant;
            this.bbreakforceinit[id] = breakforce;
            this.bdisabledinit[id] = disabled;
            this.bcollideinit[id] = collide;
            this.befficiencyinit[id] = efficiency;
            this.bfrictioninit[id] = friction;
            this.bthicknessinit[id] = thickness;
            this.bsolidinit[id] = solid;
            this.btaginit[id] = tag;
        }
        this.bsprite[id] = sprite;
        this.bred[id] = red;
        this.bgreen[id] = green;
        this.bblue[id] = blue;
        this.bparticle1[id] = particle1;
        this.bparticle2[id] = particle2;
        this.blength[id] = length;
        this.bspringconstant[id] = springconstant;
        this.bbreakforce[id] = breakforce;
        this.bdisabled[id] = disabled;
        this.bcollide[id] = collide;
        this.befficiency[id] = efficiency;
        this.bfriction[id] = friction;
        this.bthickness[id] = thickness;
        this.bsolid[id] = solid;
        this.btag[id] = tag;
        this.bstress[id] = stress;
        if (lengthcalc)
        {
            resetBondLength(id);
        }
        return;
    }
    
    public void setBondPrototype(double springconstant, int red, int green, int blue, double breakforce, boolean collide, double efficiency, double friction, double thickness, boolean solid, int tag, int sprite)
    {
        bspringconstantproto = springconstant;
        bredproto = red;
        bgreenproto = green;
        bblueproto = blue;
        bbreakforceproto = breakforce;
        bcollideproto = collide;
        befficiencyproto = efficiency;
        bfrictionproto = friction;
        bthicknessproto = thickness;
        bsolidproto = solid;
        btagproto = tag;
        bspriteproto = sprite;
    }
    
    public void resetBondLength(int num)
    {
        double tempa = (xposition[bparticle1[num]] - xposition[bparticle2[num]]);
        double tempb = (yposition[bparticle1[num]] - yposition[bparticle2[num]]);
        blength[num] = Math.sqrt((tempa * tempa) + (tempb * tempb));
        if (!isPlaying && !isPaused)
        {
            blengthinit[num] = blength[num];
        }
        return;
    }
    
    public void resetBondsOnParticle(int i)
    {
        if (!resetbonds) { return; }
        for (int a = 0; a < numbonds; a++)
        {
            if (bparticle1[a] == i || bparticle2[a] == i)
            {
                resetBondLength(a);
            }
        }
    }
    
    public void clearBond(int i)
    {
        if (!isPlaying && !isPaused)
        {
            bparticle1init[i] = 0;
            bparticle2init[i] = 0;
            blengthinit[i] = 0;
            bspringconstantinit[i] = 1000.0;
            bbreakforceinit[i] = 8000;
            bdisabledinit[i] = false;
            bstress[i] = 0;
            bcollideinit[i] = false;
            befficiencyinit[i] = 1;
            bfrictioninit[i] = 0;
            bthicknessinit[i] = 1;
            bsolidinit[i] = false;
            btaginit[i] = 0;
        }
        bparticle1[i] = 0;
        bparticle2[i] = 0;
        blength[i] = 0;
        bspringconstant[i] = 1000.0;
        bred[i] = 255;
        bgreen[i] = 255;
        bblue[i] = 255;
        bbreakforce[i] = 8000;
        bdisabled[i] = false;
        bstress[i] = 0;
        bcollide[i] = false;
        befficiency[i] = 1;
        bfriction[i] = 0;
        bthickness[i] = 1;
        bsolid[i] = false;
        btag[i] = 0;
    }
    
    public void setPrototypeFromBond(int index)
    {
        bspringconstantproto = bspringconstant[index];
        bredproto = bred[index];
        bgreenproto = bgreen[index];
        bblueproto = bblue[index];
        bbreakforceproto = bbreakforce[index];
        bcollideproto = bcollide[index];
        befficiencyproto = befficiency[index];
        bfrictionproto = bfriction[index];
        bthicknessproto = bthickness[index];
        bsolidproto = bsolid[index];
        btagproto = btag[index];
        bspriteproto = bsprite[index];
        return;
    }
    
    public void setBondFromPrototype(int o)
    {
        this.setBond(o, bparticle1[o], bparticle2[o], blength[o], false, bspringconstantproto, bredproto, bgreenproto, bblueproto, bbreakforceproto, bdisabled[o], bstress[o], bcollideproto, befficiencyproto, bfrictionproto, bthicknessproto, bsolidproto, btagproto, bspriteproto);
        return;
    }
    
    public int allocateBonds(int num) // adds num new particles to the top, and
    // returns the first new particle num
    {
        int a = numbonds;
        if (!isPlaying && !isPaused)
        {
            numbondsinit += num;
        }
        numbonds += num;
        for (int i = a; i < numbonds; i++)
        {
            clearBond(i);
        }
        return a;
    }
    
    //gets the bond that connects the selected particles. SLOW.
    //returns -1 if none match
    public int getBondByParticles(int p1, int p2)
    {
        for (int i = 0; i < numbonds; i++)
        {
            if ((p1 == bparticle1[i] && p2 == bparticle2[i]) || (p1 == bparticle2[i] && p2 == bparticle1[i]))
            {
                return i;
            }
        }
        return -1;
    }
    
    public int findNearestBond(double xloc, double yloc)
    {
        double d_closest;
        //double px, py, coldist;
        int i_closest = -1;
                 d_closest = Double.POSITIVE_INFINITY;
                 for(int i=0;i<numbonds;i++)
                 {
                    //sim.bselected[i] = false;
                     Distance.DistanceFromLine(xloc, yloc, xposition[bparticle1[i]], yposition[bparticle1[i]], xposition[bparticle2[i]], yposition[bparticle2[i]], dflr);
                     
                    
                    //Coordinate closes = ll.closestPoint(new Coordinate(mousex, mousey));
                    
                    if (dflr.sd < d_closest && !bdisabled[i])
                    {
                       d_closest = dflr.sd;
                       i_closest = i;
                    }
                 }
                 return i_closest;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** JET EDITING METHODS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public void clearJet(int i)
    {
        jparticle1[i] = 0;
        jparticle2[i] = 0;
        jetforce[i] = 0;
        jdisabled[i] = false;
        jtag[i] = 0;
    }
    
    public void copyJetToProto(int index)
    {
        jetforceproto = jetforce[index];
        jtagproto = jtag[index];
        return;
    }
    
    public void copyJetFromProto(int o)
    {
        jetforce[o] = jetforceproto;
        jetforceinit[o] = jetforceproto;
        jtag[o] = jtagproto;
        jtaginit[o] = jtagproto;
        return;
    }
    
    public int allocateJets(int num) // adds num new particles to the top, and
    // returns the first new particle num
    {
        int a = numjets;
        numjets += num;
        for (int i = a; i < numjets; i++)
        {
            clearJet(i);
        }
        return a;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** AIRFOIL EDITING METHODS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public void clearAirfoil(int i)
    {
        aparticle1[i] = 0;
        aparticle2[i] = 0;
        airconstant[i] = 0;
        adisabled[i] = false;
        atag[i] = 0;
    }
    
    public void copyAirfoilToProto(int index)
    {
        airconstantproto = airconstant[index];
        atagproto = atag[index];
    }
    
    public void copyAirfoilFromProto(int o)
    {
        airconstant[o] = airconstantproto;
        airconstantinit[o] = airconstantproto;
        atag[o] = atagproto;
        ataginit[o] = atagproto;
        return;
    }
    
    public int allocateAirfoils(int num) // adds num new particles to the top,
    // and returns the first new particle
    // num
    {
        int a = numairfoils;
        numairfoils += num;
        for (int i = a; i < numairfoils; i++)
        {
            aparticle1[i] = 0;
            aparticle1init[i] = 0;
            aparticle2[i] = 0;
            aparticle2init[i] = 0;
            airconstant[i] = 0;
            airconstantinit[i] = 0;
            adisabled[i] = false;
            adisabledinit[i] = false;
            atag[i] = 0;
            ataginit[i] = 0;
        }
        return a;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** SIMULATION
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public void stepSimulation(int steps)
    {
        //TODO: Maybe have a boolean
        //this.bond_break_events.clear();
        //this.quad_break_events.clear();
        
        if (step == 0)
        {
            for (int l = 0; l < numbonds; l++)
            {
                if ((bsolid[l] == true) && (bdisabled[l] == false))
                {
                    attachsolid[bparticle1[l]] = true;
                    attachsolid[bparticle2[l]] = true;
                }
            }
            for (int m = 0; m < numparticles; m++)
            {
                oldx[m] = xposition[m] - xvelocity[m] * dt;
                oldy[m] = yposition[m] - yvelocity[m] * dt;
                oldxmod[m] = 0;
                oldymod[m] = 0;
            }
        }
        simtime = step * dt;
        int p1, p2;
        double bdistance;
        double tempa, tempb, tempc;// , tempd;
        
        if (isPlaying == false) return;
        
        step++; // increase step by 1
        for (int i = 0; i < steps; i++)
        {
            
            // VERLET APPROXIMATE VELOCITY HERE
            for (int k = 0; k < numparticles; k++)
            {
                if (!pdisabled[k])
                {
                    xvelocity[k] = xposition[k] - oldx[k];
                    yvelocity[k] = yposition[k] - oldy[k];
                }
            }
            
            // Macro Gravity
            for (int j = 0; j < numparticles; j++)
            {
                if (gravityon == true)
                {
                    if (!fixed[j] && !pdisabled[j])
                    {
                        yforce[j] += gravityacc * mass[j];
                    }
                }
            }
            
            // MICRO GRAVITY
            if (microgravityon == true)
            {
                double nx, ny;
                double aa, bb, cc, muli, mulx, muly;
                for (int ii = 0; ii < numparticles; ii++)
                {
                    if (!pdisabled[ii])
                    {
                        for (int jj = ii + 1; jj < numparticles; jj++)
                        {
                            if (!pdisabled[jj])
                            {
                                // System.out.println("MICROGRAV");
                                aa = xposition[ii] - xposition[jj];
                                bb = yposition[ii] - yposition[jj];
                                cc = Math.sqrt(aa * aa + bb * bb);
                                nx = (aa) / cc;
                                ny = (bb) / cc;
                                muli = microgravityconstant * ((mass[ii] * mass[jj]) / (cc * cc));
                                mulx = muli * nx;
                                muly = muli * ny;
                                xforce[ii] -= mulx;
                                yforce[ii] -= muly;
                                xforce[jj] += mulx;
                                yforce[jj] += muly;
                            }
                        }
                    }
                }
            }
            
            // coulomb force
            if (coulomb == true)
            {
                double nx, ny;
                double aa, bb, cc, muli, mulx, muly;
                for (int ii = 0; ii < numparticles; ii++)
                {
                    if (!pdisabled[ii])
                    {
                        for (int jj = ii + 1; jj < numparticles; jj++)
                        {
                            // System.out.println("COULOMB");
                            if (((charge[ii] != 0) && (charge[jj] != 0)))
                            {
                                if (!pdisabled[ii])
                                {
                                    aa = xposition[ii] - xposition[jj];
                                    bb = yposition[ii] - yposition[jj];
                                    cc = Math.sqrt(aa * aa + bb * bb);
                                    if (cc == 0)
                                    {
                                        cc = .000001;
                                    }
                                    nx = (aa) / cc;
                                    ny = (bb) / cc;
                                    muli = coulombconstant * ((charge[ii] * charge[jj]) / (cc * cc));
                                    mulx = muli * nx;
                                    muly = muli * ny;
                                    xforce[ii] += mulx;
                                    yforce[ii] += muly;
                                    xforce[jj] -= mulx;
                                    yforce[jj] -= muly;
                                }
                            }
                        }
                    }
                }
            }
            
            // COLLISION
            // marking collisions
            double coldist;// , coloverlapdist;
            //IntQueue queue = new IntQueue();
            queue.clear();
            int markiteration = 0;
            int collisionswitch = 0;
            double kk;
            double aa, bb;// ,cc;
            
            if (conserveon == true)
            {
                for (int k = 0; k < collisioniterations; k++)
                {
                    collisionswitch = 0;
                    for (i = 0; i < numparticles; i++)
                    {
                        if (!pdisabled[i])
                        {
                            for (int j = i + 1; j < numparticles; j++)
                            {
                                if (!pdisabled[j])
                                {
                                    aa = xposition[i] - xposition[j];
                                    bb = yposition[i] - yposition[j];
                                    coldist = Math.sqrt(aa * aa + bb * bb);
                                    if ((radius[i] + radius[j]) - coldist > 0.0)
                                    {// then there is a collision
                                        if (markiteration == 1)
                                        {
                                            queue.write(i);
                                            queue.write(j);
                                        }
                                        if (((radius[i] + radius[j]) - coldist) / (radius[i] + radius[j]) > collisiondistancetolerance)
                                        {
                                            collisionswitch = 1;
                                            if (fixed[i] == false && fixed[j] == true)
                                            {
                                                xposition[i] += ((xposition[i] - xposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                                yposition[i] += ((yposition[i] - yposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                            }
                                            else if (fixed[i] == true && fixed[j] == false)
                                            {
                                                xposition[j] -= ((xposition[i] - xposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                                yposition[j] -= ((yposition[i] - yposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                            }
                                            else if (fixed[i] == true && fixed[j] == true)
                                            {
                                            }
                                            else
                                            {
                                                kk = ((xposition[i] - xposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                                xposition[i] += (mass[j] / (mass[i] + mass[j])) * kk;
                                                xposition[j] -= (mass[i] / (mass[i] + mass[j])) * kk;
                                                kk = ((yposition[i] - yposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                                yposition[i] += (mass[j] / (mass[i] + mass[j])) * kk;
                                                yposition[j] -= (mass[i] / (mass[i] + mass[j])) * kk;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    markiteration = 1;
                    if (collisionswitch == 0)
                    {
                        break;
                    }
                }
                // parse the collision table
                double nx, ny;
                // double ut;
                // double un, sq;
                // double vn, wn;
                double r, e;
                double unx, uny;
                double vcx, vcy;
                double utx, uty;
                double vnx, vny;
                double wnx, wny;
                double ux, uy;
                double mag;
                double vtemp, tempt;// ,tea, teb, tec, ted;
                double ov2x, ov2y;
                double alpha, theta;
                int j;
                while (!queue.isEmpty())
                {
                    i = queue.read();
                    j = queue.read();
                    nx = xposition[i] - xposition[j];
                    ny = yposition[i] - yposition[j];
                    ux = xvelocity[i] - xvelocity[j];
                    uy = yvelocity[i] - yvelocity[j];
                    r = mass[i] / mass[j]; // N
                    // TEMPORARILY OFF
                    // e = (efficiency[i] + efficiency[j]) / 2;
                    e = 1;
                    // component vector
                    vtemp = Math.sqrt(nx * nx + ny * ny);
                    vcx = nx / vtemp;
                    vcy = ny / vtemp;
                    alpha = Math.atan2(ny, nx);
                    theta = Math.atan2(uy, ux);
                    mag = Math.sqrt(ux * ux + uy * uy);
                    // un = mag*Math.cos(theta-alpha);
                    // tea = un * nx; teb = un * ny; tec = ux - tea; ted = uy -
                    // teb;
                    // ut = Math.sqrt(tec*tec+ted*ted);
                    // sq = r*r*un*un-(r+1)*((r-e)*un*un+(1-e)*ut*ut);
                    // vnx = nx*(Math.sqrt(sq)-r*un)/(r+1); vny =
                    // ny*(Math.sqrt(sq)-r*un)/(r+1);
                    // wnx = r*(nx*un-vnx);wny = r*(ny*un-vny);
                    tempt = mag * Math.cos(theta - alpha);
                    vcx *= tempt;
                    vcy *= tempt;
                    unx = vcx;
                    uny = vcy;
                    // end component vector
                    utx = ux - unx;
                    uty = uy - uny;
                    vnx = unx * (r - 1) / (r + 1);
                    vny = uny * (r - 1) / (r + 1);
                    wnx = unx * 2 * r / (r + 1);
                    wny = uny * 2 * r / (r + 1);
                    ov2x = xvelocity[j];
                    ov2y = yvelocity[j];
                    xvelocity[i] = (utx + vnx + ov2x) * e;
                    yvelocity[i] = (uty + vny + ov2y) * e;
                    xvelocity[j] = (wnx + ov2x) * e;
                    yvelocity[j] = (wny + ov2y) * e;
                }
            }
            
            // REPULSION COLLISION
            if (repulsionon == true)
            {
                double biggest = -1;
                for (int ia = 0; ia < numparticles; ia++)
                {
                    if (radius[ia] > biggest)
                    {
                        biggest = radius[ia];
                    }
                }
                biggest *= 2;
                // for (int ia = 0;ia<numparticles;ia++)
                // {
                // plocx[ia] = (xposition[ia] / biggest);
                // plocy[ia] = (yposition[ia] / biggest);
                // }
                // System.out.println(biggest);
                
                double nx, ny;
                for (i = 0; i < numparticles; i++)
                {
                    if (!pdisabled[i])
                    {
                        for (int j = i + 1; j < numparticles; j++)
                        {
                            if (!pdisabled[j])
                            {
                                // double ta = plocx[i] - plocx[j];
                                // double tb = plocy[i] - plocy[j];
                                double ta = xposition[i] - xposition[j];
                                double tb = yposition[i] - yposition[j];
                                if ((ta <= biggest && ta >= -biggest) && tb <= biggest && tb >= -biggest)
                                {
                                    aa = xposition[i] - xposition[j];
                                    bb = yposition[i] - yposition[j];
                                    coldist = Math.sqrt(aa * aa + bb * bb);
                                    aa = (radius[i] + radius[j]) - coldist;
                                    if ((radius[i] + radius[j]) - coldist > 0.0)
                                    {
                                        nx = (xposition[i] - xposition[j]) / coldist;
                                        ny = (yposition[i] - yposition[j]) / coldist;
                                        bb = efficiency[i] * efficiency[j];
                                        
                                        // experimental moving
                                        kk = ((xposition[i] - xposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                        if (fixed[i] == false)
                                        {
                                            xposition[i] += (1 - bb) * (mass[j] / (mass[i] + mass[j])) * kk;
                                        }
                                        if (fixed[j] == false)
                                        {
                                            xposition[j] -= (1 - bb) * (mass[i] / (mass[i] + mass[j])) * kk;
                                        }
                                        kk = ((yposition[i] - yposition[j]) / coldist) * ((radius[i] + radius[j]) - coldist);
                                        if (fixed[i] == false)
                                        {
                                            yposition[i] += (1 - bb) * (mass[j] / (mass[i] + mass[j])) * kk;
                                        }
                                        if (fixed[j] == false)
                                        {
                                            yposition[j] -= (1 - bb) * (mass[i] / (mass[i] + mass[j])) * kk;
                                        }
                                        
                                        /*
                                         * xforce[i] += nx * aa *
                                         * repulsionconstant * bb; yforce[i] +=
                                         * ny * aa * repulsionconstant * bb;
                                         * xforce[j] -= nx * aa *
                                         * repulsionconstant * bb; yforce[j] -=
                                         * ny * aa * repulsionconstant * bb;
                                         */

                                        aa *= 1 / dt;
                                        
                                        // add bb back in if things aren't
                                        // working right
                                        double biga = 2 * aa * repulsionconstant; // 2*overlap*efficiency*constant
                                        double masssum = mass[i] + mass[j];
                                        
                                        xforce[i] += ((nx * biga) * (mass[j] / masssum)) * mass[i];
                                        yforce[i] += ((ny * biga) * (mass[j] / masssum)) * mass[i];
                                        xforce[j] -= ((nx * biga) * (mass[i] / masssum)) * mass[j];
                                        yforce[j] -= ((ny * biga) * (mass[i] / masssum)) * mass[j];
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            double tv, tv2;
            // P-B COLLISIONS
            if (bondcollision == true)
            {
                double nx = 0;
                double ny = 0;
                double c = 0;
                double tdist = 0;
                double bl = 0;
                double bt1 = 0;
                double bt2 = 0;
                double velal = 0;
                for (i = 0; i < numbonds; i++)
                {
                    if (bcollide[i] == true && bdisabled[i] == false && !pdisabled[bparticle1[i]] && !pdisabled[bparticle2[i]])
                    {
                        for (int j = 0; j < numparticles; j++)
                        {
                            if (!pdisabled[j])
                            {
                                // aa = xposition[i] - xposition[j];
                                // bb = yposition[i] - yposition[j];
                                // coldist = Math.sqrt(aa*aa+bb*bb);
                                
                                if (j != bparticle1[i] && j != bparticle2[i])
                                {
                                    //      LineSegment ll;
                                    //             Coordinate closes;
                                    //             Coordinate linep1, linep2;
                                          //linep1.x = xposition[bparticle1[i]];
                                          //linep1.y = yposition[bparticle1[i]];
                                          //linep2.x = xposition[bparticle2[i]];
                                          //linep2.y = yposition[bparticle2[i]];
                                          //ll.p0 = linep1;
                                          //ll.p1 = linep2;
                                          //ppoint.x = xposition[j];
                                          //ppoint.y = yposition[j];
                                          //closes = ll.closestPoint(ppoint);
                                          
                                          Distance.DistanceFromLine(xposition[j], yposition[j], xposition[bparticle1[i]], yposition[bparticle1[i]], xposition[bparticle2[i]], yposition[bparticle2[i]], dflr);
                                          
                                    double t1 = 0;
                                    double t2 = 0;
                                    t1 = dflr.sx - xposition[j];
                                    t2 = dflr.sy - yposition[j];
                                    coldist = dflr.sd;
                                    //coldist = Math.sqrt((t1*t1) + (t2 * t2));
                                    //coldist = ll.distAndPoint(new Vector2(xposition[j], yposition[j]), closes);
                                    aa = (radius[j] + bthickness[i]) - coldist;   //the distance penetrated.

                                    tdist = radius[j] + bthickness[i] - coldist;
                                    //System.out.println("dist: "+coldist);
                                    if (tdist > 0.0)   //then there is a collision
                                    {
                                       //let's do friction here!

                                       bt1 = xposition[bparticle2[i]] - xposition[bparticle1[i]];
                                       bt2 = yposition[bparticle2[i]] - yposition[bparticle1[i]];
                                       bl = Math.sqrt((bt1*bt1) + (bt2*bt2));
                                       bt1 = bt1/bl; //is normalized x of bond vector
                                       bt2 = bt2/bl; //is normalized y of bond vector

                                       velal = xvelocity[j] * bt1 + yvelocity[j] * bt2; //velocity ALONG the bond!
                                       
                                       if (velal != 0.0)
                                       {
                                          //friction
                                          tv = -(velal / Math.abs(velal));
                                          tv2 = tdist*bfriction[i]*tv;
                                          if (Math.abs(tv2) > Math.abs(velal))
                                          {
                                             tv2 = -velal;
                                          }
                                          xposition[j] += tv2 * bt1;
                                          yposition[j] += tv2 * bt2;
                                       }

                                      
                                      
                                      

                                       if (xposition[bparticle1[i]] == xposition[bparticle2[i]])   //then use Y's
                                       {
                                          if (yposition[bparticle1[i]] > yposition[bparticle2[i]])  //then case1 for c find
                                          {
                                             c = 1 - ((dflr.sy - yposition[bparticle2[i]]) / (yposition[bparticle1[i]] - yposition[bparticle2[i]]));
                                             //System.out.println("case 1 c = " + c);
                                          }
                                          else
                                          {
                                             //System.out.println("cpoint x = "+closes.getX());
                                             c = ((dflr.sy - yposition[bparticle1[i]]) / (yposition[bparticle2[i]] - yposition[bparticle1[i]]));
                                             //System.out.println("case 2 c = " + c);
                                          }
                                       }
                                       else     //use X's
                                       {
                                          if (xposition[bparticle1[i]] > xposition[bparticle2[i]])  //then case1 for c find
                                          {
                                             c = 1 - ((dflr.sx - xposition[bparticle2[i]]) / (xposition[bparticle1[i]] - xposition[bparticle2[i]]));
                                             //System.out.println("case 1 c = " + c);
                                          }
                                          else
                                          {
                                             //System.out.println("cpoint x = "+closes.getX());
                                             c = ((dflr.sx - xposition[bparticle1[i]]) / (xposition[bparticle2[i]] - xposition[bparticle1[i]]));
                                             //System.out.println("case 2 c = " + c);
                                          }
                                       }


                                       //System.out.println("coldetect");
                                       nx = (xposition[j] - dflr.sx) / coldist;
                                       ny = (yposition[j] - dflr.sy) / coldist;

                                       bb = efficiency[j] * befficiency[i];

                                       double virtualmass = ((1-c)*mass[bparticle1[i]]) + (c * mass[bparticle2[i]]);

                                       double masssum = mass[j] + virtualmass;

                                       //experimental moving
                                       //kk = ((xposition[j] - dflr.sx)/coldist)*((radius[i]+radius[j])-coldist);
                                       //if (fixed[j] == false) {xposition[j] += (1-bb)*(mass[j]/(masssum))*kk;}
                                       //if (fixed[bparticle1[i]] == false) {xposition[bparticle1[i]] -= (1-bb)*(virtualmass/(masssum))*kk;}
                                       //if (fixed[bparticle2[i]] == false) {xposition[bparticle2[i]] -= (1-bb)*(virtualmass/(masssum))*kk;}
                                       //kk = ((yposition[j] - dflr.sy)/coldist)*((radius[i]+radius[j])-coldist);
                                       //if (fixed[j] == false) {yposition[j] += (1-bb)*(mass[j]/(masssum))*kk;}
                                       //if (fixed[bparticle1[i]] == false) {yposition[j] -= (1-bb)*(virtualmass/(masssum))*kk;}
                                       //if (fixed[bparticle1[i]] == false) {yposition[j] -= (1-bb)*(virtualmass/(masssum))*kk;}

                                       aa *= 1/dt;

                                       //add bb back in if things aren't working right
                                       double biga = 2 * aa *bb* repulsionconstant; //2*overlap*efficiency*constant

                                       //double masssum = mass[i] + mass[j];



                                       //real particle
                                       xforce[j] += ((nx * biga) * (virtualmass / masssum))*mass[j];
                                       yforce[j] += ((ny * biga) * (virtualmass / masssum))*mass[j];

                                       //bonded particles
                                       double virtualforcex = ((nx * biga) * (mass[j] / masssum))*virtualmass;
                                       double virtualforcey = ((ny * biga) * (mass[j] / masssum))*virtualmass;

                                       xforce[bparticle1[i]] -= (1-c)*virtualforcex;
                                       yforce[bparticle1[i]] -= (1-c)*virtualforcey;

                                       xforce[bparticle2[i]] -= c*virtualforcex;
                                       yforce[bparticle2[i]] -= c*virtualforcey;

                                       //xforce[bparticle1[j]] -=
                                       //yforce[j] -= ((ny * biga) * (mass[i] / masssum))*mass[j];
                                    }
                                 }
                              }
                              }
                           }
                        }
                     }
            
            // GROUND
            if (groundon == true)
            {
                for (i = 0; i < numparticles; i++)
                {
                    if (!pdisabled[i])
                    {
                        tempa = radius[i] + yposition[i];
                        if ((tempa + (.1 * radius[i])) > groundyposition)
                        {
                            xvelocity[i] += ((xvelocity[i] * groundfriction) - xvelocity[i]) * dt;
                        }
                        if (tempa > groundyposition && fixed[i] == false && attachsolid[i] == false)
                        {
                            tempa -= groundyposition;
                            
                            if (xvelocity[i] != 0.0)
                            {
                                // friction
                                tv = -(xvelocity[i] / Math.abs(xvelocity[i]));
                                tv2 = tempa * groundfriction * tv;
                                if (Math.abs(tv2) > Math.abs(xvelocity[i]))
                                {
                                    tv2 = -xvelocity[i];
                                }
                                xposition[i] += tv2;
                            }
                            
                            tempa = (radius[i] + yposition[i]) - groundyposition;
                            
                            // step 1: put up to ground level for free
                            yposition[i] -= tempa;
                            oldymod[i] = 0;
                            oldy[i] = yposition[i];
                            // /yposition[i] -= 2 * yvelocity[i];
                            
                            if (yvelocity[i] > 0)
                            {
                                tempb = yvelocity[i] - ((oldy[i] + radius[i]) - groundyposition);
                                yposition[i] -= tempb;
                                oldymod[i] -= tempb;
                                oldymod[i] += yvelocity[i] * efficiency[i] * groundhardness;
                            }
                            
                            // tempb = (2*tempa) - ((oldy[i] + radius[i]) -
                            // groundyposition);
                            // yposition[i] -= (tempb * efficiency[i] *
                            // groundhardness);
                            // oldymod[i] -= ((oldy[i] + radius[i]) -
                            // groundyposition) * efficiency[i] *
                            // groundhardness;
                            
                            // System.out.println("Yo");
                            
                        }
                    }
                }
            }
            
            // WATER
            if (wateron == true)
            {
                for (i = 0; i < numparticles; i++)
                {
                    if (!pdisabled[i])
                    {
                        // tempa = particlebottom
                        // tempc = h
                        // tempd = area
                        double particlebottom = radius[i] + yposition[i];
                        if (particlebottom > wateryposition && fixed[i] == false)
                        {
                            double area = 0;
                            double h = particlebottom - wateryposition;
                            if (h > 2 * radius[i])
                            {
                                h = 2 * radius[i];
                            }
                            if (h <= radius[i])
                            {
                                double thet = 2 * Math.acos((radius[i] - h) / radius[i]);
                                area = .5 * radius[i] * radius[i] * (thet - Math.sin(thet));
                            }
                            if (h > radius[i])
                            {
                                double aaa = Math.PI * radius[i] * radius[i];
                                double hh = (2 * radius[i]) - h;
                                double thet = 2 * Math.acos((radius[i] - hh) / radius[i]);
                                area = aaa - (.5 * radius[i] * radius[i] * (thet - Math.sin(thet)));
                            }
                            // double area = (2*radius[i])*h * .78;
                            // yforce[i] -=
                            // mass[i]*gravityacc*waterdensity*tempd;
                            
                            // tempa = density
                            double density = (waterdensityscale * ((yposition[i] + radius[i]) - wateryposition)) + waterdensity; // tempd
                            // =
                            // adjusted
                            // density
                            // System.out.println(""+tempa);
                            if (gravityon == false)
                            {
                                yforce[i] -= density * area * 0;
                            }
                            else
                            {
                                yforce[i] -= density * area * gravityacc;
                            }
                        }
                    }
                }
            }
            
            // JET
            double nx, ny;
            for (i = 0; i < numjets; i++)
            {
                if (!jdisabled[i] && !pdisabled[jparticle1[i]] && !pdisabled[jparticle2[i]])
                {
                    p1 = jparticle1[i];
                    p2 = jparticle2[i];
                    tempa = (xposition[p2] - xposition[p1]);
                    tempb = (yposition[p2] - yposition[p1]);
                    bdistance = Math.sqrt((tempa * tempa) + (tempb * tempb));
                    nx = tempa / bdistance;
                    ny = tempb / bdistance;
                    xforce[p1] += nx * jetforce[i];
                    yforce[p1] += ny * jetforce[i];
                }
            }
            
            // AIRFOILS
            double snx, sny, windx, windy;
            for (i = 0; i < numairfoils; i++)
            {
                if (!adisabled[i] && !pdisabled[aparticle1[i]] && !pdisabled[aparticle2[i]])
                {
                    // System.out.println("AIRF");
                    p1 = aparticle1[i];
                    p2 = aparticle2[i];
                    // if (xposition[p2] > xposition[p1]) {tempa =
                    // (xposition[p2]-xposition[p1]);}
                    // else {tempa = (xposition[p1]-xposition[p2]);}
                    tempa = (xposition[p2] - xposition[p1]);
                    tempb = (yposition[p2] - yposition[p1]);
                    // if (yposition[p2] > yposition[p1]) {tempb =
                    // (yposition[p2]-yposition[p1]);}
                    // else {tempb = (yposition[p1]-yposition[p2]);}
                    // tempb = (yposition[p2]-yposition[p1]);
                    bdistance = Math.sqrt((tempa * tempa) + (tempb * tempb));
                    // nx = tempa / bdistance;
                    // ny = tempb / bdistance;
                    // nx = Math.abs(nx);
                    // ny = Math.abs(ny);
                    // snx = -ny;
                    // sny = nx;
                    snx = -tempb;
                    sny = tempa;
                    windx = ((xvelocity[p1] + xvelocity[p2]) / 2);
                    windy = ((yvelocity[p1] + yvelocity[p2]) / 2);
                    tempc = airconstant[i] * ((snx * windx) + (sny * windy));
                    // tempb = airconstant[i] * sny * windy;
                    
                    xforce[p1] -= snx * tempc;
                    yforce[p1] -= sny * tempc;
                    xforce[p2] -= snx * tempc;
                    yforce[p2] -= sny * tempc;
                }
            }
            
            int notsoliders = 0;
            
            // BONDS
            int j;
            for (j = 0; j < numbonds; j++)
            {
                if (!bdisabled[j] && !pdisabled[bparticle1[j]] && !pdisabled[bparticle2[j]])
                {
                    if ((fixed[bparticle1[j]] == true && fixed[bparticle2[j]] == true) || bsolid[j] == true)
                    {
                    	//handle solid bonds...
                    	
                    }
                    else
                    {
                        notsoliders++;
                        if (bdisabled[j] == false)
                        {
                            p1 = bparticle1[j];
                            p2 = bparticle2[j];
                            tempa = (xposition[p2] - xposition[p1]);
                            tempb = (yposition[p2] - yposition[p1]);
                            bdistance = Math.sqrt((tempa * tempa) + (tempb * tempb)); // now
                            // this
                            // is
                            // the
                            // distance
                            // of
                            // the
                            // particles
                            // in
                            // question
                            tempc = blength[j] - bdistance; // now tempc is the
                            // difference in
                            // length;
                            tempc = -(tempc / bdistance) * bspringconstant[j];
                            
                            //tempa = (xposition[p2] - xposition[p1]) * tempc;
                            //tempb = (yposition[p2] - yposition[p1]) * tempc;
                            tempa *= tempc;
                            tempb *= tempc;
                            bstress[j] = Math.sqrt(tempa * tempa + tempb * tempb); // for
                            // coloration
                            if (bstress[j] > bbreakforce[j])
                            {
                                bdisabled[j] = true;
                                if (this.log_bond_death)
                                {
                                	bond_break_events.add(j);
                                }
                            }
                            else
                            {
                                xforce[p1] += tempa;
                                //xforce[p2] += (xposition[p1] - xposition[p2]) * tempc;
                                xforce[p2] -= tempa;
                                yforce[p1] += tempb;
                                //yforce[p2] += (yposition[p1] - yposition[p2]) * tempc;
                                yforce[p2] -= tempb;
                            }
                            bstress[j] /= bbreakforce[j]; // also for coloration
                        }
                    }
                }
            }
            
            
            
            //QUAD BREAK
            for (j = 0; j < numquads; j++)
            {
                if (!qdisabled[j])
                {
                    int broken = 0;
                    if (qbond1[j] >= 0 && bdisabled[qbond1[j]]) {broken++;}
                    if (qbond2[j] >= 0 && bdisabled[qbond2[j]]) {broken++;}
                    if (qbond3[j] >= 0 && bdisabled[qbond3[j]]) {broken++;}
                    if (qbond4[j] >= 0 && bdisabled[qbond4[j]]) {broken++;}
                    if (broken >= qbondstobreak[j])
                    {
                        qdisabled[j] = true;
                        if (this.log_quad_death)
                        {
                        	this.quad_break_events.add(j);
                        }
                    }
                }
            }
            
            // MODIFY OLDX and OLDY
            for (j = 0; j < numparticles; j++)
            {
                if (!pdisabled[j])
                {
                    oldx[j] += oldxmod[j];
                    oldxmod[j] = 0;
                    oldy[j] += oldymod[j];
                    oldymod[j] = 0;
                }
            }
            
            // ZERO FIXED AND REMOVED
            for (j = 0; j < numparticles; j++)
            {
                if (fixed[j] == true && !pdisabled[j])
                {
                    xforce[j] = 0;
                    yforce[j] = 0;
                    xvelocity[j] = 0;
                    yvelocity[j] = 0;
                    xacceleration[j] = 0;
                    yacceleration[j] = 0;
                }
            }
            
            // Increase Time
            // for(j=0;j<numparticles;j++)
            // {
            // xforce[j] *= dt;
            // yforce[j] *= dt;
            // }
            
            // Force to acceleration
            for (j = 0; j < numparticles; j++)
            {
                if (mass[j] != 0 && !pdisabled[j])
                {
                    xacceleration[j] = xforce[j] / mass[j];
                    yacceleration[j] = yforce[j] / mass[j];
                }
            }
            
            // VERLET POINTER SWITCHES
            double atx[];
            atx = ancx;
            ancx = oldx;
            oldx = xposition;
            xposition = atx;
            
            double aty[];
            aty = ancy;
            ancy = oldy;
            oldy = yposition;
            yposition = aty;
            
            // VERLET INTEGRATION
            double mfdefault = Math.pow(fluidfriction, dt);
            double mff = 0;
            // System.out.println(mff + "\n");
            for (int v = 0; v < numparticles; v++)
            {
                // xposition[v] = 2*oldx[v] - ancx[v] +
                // (xacceleration[v]*dt*dt);
                // yposition[v] = 2*oldy[v] - ancy[v] +
                // (yacceleration[v]*dt*dt);
                
                // xposition[v] = (1 + fluidfriction)*oldx[v] -
                // fluidfriction*ancx[v] + (xacceleration[v]*dt*dt);
                // yposition[v] = (1 + fluidfriction)*oldy[v] -
                // fluidfriction*ancy[v] + (yacceleration[v]*dt*dt);
                
                // water friction
                if (!pdisabled[v])
                {
                    
                    if (wateron == true && yposition[v] + radius[v] > wateryposition)
                    {
                        tempa = ((yposition[v] + radius[v]) - wateryposition) / (2 * radius[v]);
                        if (tempa > 1)
                        {
                            tempa = 1;
                        }
                        tempb = (tempa * (waterfluidfriction - fluidfriction)) + fluidfriction;
                        mff = Math.pow(tempb, dt);
                    }
                    else
                    {
                        mff = mfdefault;
                    }
                    
                    xposition[v] = (1 + mff) * oldx[v] - mff * ancx[v] + (xacceleration[v] * dt * dt);
                    yposition[v] = (1 + mff) * oldy[v] - mff * ancy[v] + (yacceleration[v] * dt * dt);
                }
            }
            
          //Iteratively handle solid bonds
            for (j = 0; j < numbonds; j++)
            {
            	if ((fixed[bparticle1[j]] == true && fixed[bparticle2[j]] == true) || bsolid[j] == true)
                {
                	//handle solid bonds...
            		double dp1 = 0;
            		double dp2 = 0;
            		p1 = bparticle1[j];
                    p2 = bparticle2[j];
                    tempa = (xposition[p2] - xposition[p1]);
                    tempb = (yposition[p2] - yposition[p1]);
                    bdistance = Math.sqrt((tempa * tempa) + (tempb * tempb)); // now
                    tempc = blength[j] - bdistance; // now tempc is the difference in length;
                    tempc = -(tempc / bdistance);
                    tempa *= tempc;
                    tempb *= tempc;
                    
                    if (fixed[p1] && !fixed[p2])
                    {
                    	xposition[p2] -= tempa;
                    	oldx[p2] -= tempa;
                    	yposition[p2] -= tempb;
                    	//oldy[p2] -= tempb;
                    	
                    	
                    }
                    
                    if (fixed[p2] && !fixed[p1])
                    {
                    	xposition[p1] += tempa;
                    	oldx[p1] += tempa;
                    	yposition[p1] += tempb;
                    	//oldy[p1] += tempb;
                    }
                }
            }
            
            // move FORCE CHECK to right here... and add virtual forces
            // Reset forces
            for (j = 0; j < numparticles; j++)
            {
                if (!pdisabled[j])
                {
                    xforce[j] = 0.0;
                    yforce[j] = 0.0;
                }
            }
        }
        return;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** CAMERA METHODS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public double getWorldX(double screenx, double screenwidth)
    { 
        return ((screenx/screenwidth)*(this.panelright-this.panelleft))+this.panelleft;
    }
    
    public double getWorldY(double screeny, double screenheight)
    {
        return ((screeny/screenheight)*(this.panelbottom-this.paneltop))+this.paneltop;
    }
    
    public void setCameraBounds(double left, double right, double top, double bottom)
    {
        if (!isPlaying && !isPaused)
        {
            this.panelleftinit = left;
            this.panelrightinit = right;
            this.paneltopinit = top;
            this.panelbottominit = bottom;
        }
        this.panelleft = left;
        this.panelright = right;
        this.paneltop = top;
        this.panelbottom = bottom;
        return;
    }
    
    public void moveCamera(double x, double y)
    {
        panelleft += x;
        panelright += x;
        paneltop += y;
        panelbottom += y;
        zoomCheck();
        return;
    }
    
    public void centerCamera(double x, double y)
    {
        double oldcx = (panelright + panelleft) / 2.0;
        double oldcy = (panelbottom + paneltop) / 2.0;
        this.moveCamera(x - oldcx, y - oldcy);
        return;
    }
    
    public void cameraExecute()
    {
        this.panelleft += movemodleft;
        this.panelright += movemodright;
        this.paneltop += movemodtop;
        this.panelbottom += movemodbottom;
        zoomCheck();
        movemodleft = 0;
        movemodright = 0;
        movemodtop = 0;
        movemodbottom = 0;
    }
    
    public void scroll(double dx, double dy)
    {
        double wdx = (panelright - panelleft) * dx, hdy = (panelbottom - paneltop) * dy;
        // System.out.println("DX:"+dx+"  DY:"+dy);
        // System.out.println("WDX:"+wdx+"  HDY:"+hdy);
        panelleft += wdx;
        panelright += wdx;
        paneltop += hdy;
        panelbottom += hdy;
        zoomCheck();
        return;
    }
    
    public void zoomCheck()
    {
        if (!isPlaying)
        {
            panelleftinit = panelleft;
            panelrightinit = panelright;
            paneltopinit = paneltop;
            panelbottominit = panelbottom;
        }
        return;
    }
    
    public void centerOn(double x, double y)
    {
        double cx = 0.5 * (panelleft + panelright), cy = 0.5 * (paneltop + panelbottom);
        double dx = x - cx, dy = y - cy;
        panelleft += dx;
        paneltop += dy;
        panelright += dx;
        panelbottom += dy;
        zoomCheck();
        return;
    }
    
    public void zoomFit()
    {
        // Center is unchanged, zoom to fit!
        double cx = 0.5 * (panelleft + panelright), cy = 0.5 * (paneltop + panelbottom);
        double maxd = 0.0;
        double aspect = (panelright - panelleft) / (paneltop - panelbottom);
        
        // Amount needed to scale
        for (int i = 0; i < numparticles; i++)
        {
            maxd = Math.max(maxd, Math.abs(xposition[i] + radius[i] - cx));
            maxd = Math.max(maxd, Math.abs(xposition[i] - radius[i] - cx));
            maxd = Math.max(maxd, Math.abs(yposition[i] + radius[i] - cy));
            maxd = Math.max(maxd, Math.abs(yposition[i] - radius[i] - cy));
        }
        if (aspect < 1.0)
        {
            panelleft = cx - maxd;
            panelright = cx + maxd;
            paneltop = cy + maxd / aspect;
            panelbottom = cy - maxd / aspect;
        }
        else
        {
            paneltop = cy + maxd;
            panelbottom = cy - maxd;
            panelleft = cx - maxd * aspect;
            panelright = cx + maxd * aspect;
        }
        return;
    }
    
    /**
     * Scales the current frame by a factor of s.
     */
    
    public void scale(double s)
    {
        double cx = 0.5 * (panelleft + panelright), cy = 0.5 * (paneltop + panelbottom);
        double sdx = s * (cx - panelleft), sdy = s * (cy - paneltop);
        
        panelleft = cx - sdx;
        paneltop = cy - sdy;
        panelright = cx + sdx;
        panelbottom = cy + sdy;
        
        zoomCheck();
        return;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** GLOBAL ACCESS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
     
    public void setGlobalConservationCollisionDistanceTolerance(double value)
    {
        this.collisiondistancetolerance = value;
    }
    
    public double getGlobalConservationCollisionDistanceTolerance()
    {
        return this.collisiondistancetolerance;
    }
    
    public void setGlobalConservationCollisionIterations(int value)
    {
        this.collisioniterations = value;
    }
    
    public int getGlobalConservationCollisionIterationst()
    {
        return this.collisioniterations;
    }
    
    public void setGlobalRepulsionCollisionConstant(double value)
    {
        if (!isPlaying && !isPaused)
        {
            this.repulsionconstantinit = value;
        }
        this.repulsionconstant = value;
    }
    
    public double getGlobalRepulsionCollisionConstant()
    {
        return this.repulsionconstant;
    }
    
    public void setGlobalRepulsionCollisions(boolean value)
    {
        if (!isPlaying && !isPaused)
        {
            this.repulsiononinit = value;
        }
        this.repulsionon = value;
    }
    
    public boolean getGlobalRepulsionCollisions()
    {
        return this.repulsionon;
    }
    
    public void setGlobalConservationCollisions(boolean value)
    {
        if (!isPlaying && !isPaused)
        {
            this.conserveoninit = value;
        }
        this.conserveon = value;
    }
    
    public boolean getGlobalConservationCollisions()
    {
        return this.conserveon;
    }
    
    public void setGlobalFluidFriction(double value)
    {
        if (!isPlaying && !isPaused)
        {
            this.fluidfrictioninit = value;
        }
        this.fluidfriction = value;
    }
    
    public double getGlobalFluidFriction()
    {
        return this.fluidfriction;
    }
    
    public void setGlobalGroundOn(boolean value)
    {
        if (!isPlaying && !isPaused)
        {
            this.groundoninit = value;
        }
        this.groundon = value;
    }
    
    public boolean getGlobalGroundOn()
    {
        return this.groundon;
    }
    
    public void setGlobalGroundYPosition(double value)
    {
        if (!isPlaying && !isPaused)
        {
            this.groundypositioninit = value;
        }
        this.groundyposition = value;
    }
    
    public double getGlobalGroundYPosition()
    {
        return this.groundyposition;
    }
    
    public void setGlobalGravityAcceleration(double value)
    {
        if (!isPlaying && !isPaused)
        {
            this.gravityaccinit = value;
        }
        this.gravityacc = value;
    }
    
    public double getGlobalGravityAcceleration()
    {
        return this.gravityacc;
    }
    
    public void setGlobalDT(double value)
    {
    	if (!isPlaying && !isPaused)
        {
            this.dtinit = value;
        }
        this.dt = value;
    }
    
    public double getGlobalDT()
    {
    	return this.dt;
    }
    
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** PARTICLE ACCESS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public void setParticleFixed(int id, boolean value)
    {
        this.fixed[id] = value;
        return;
    }
    
    public boolean getParticleFixed(int id)
    {
        return fixed[id];
    }
    
    public void setParticleMass(int id, double value)
    {
        if (!isPlaying && !isPaused)
        {
            this.massinit[id] = value;
        }
        this.mass[id] = value;
        return;
    }
    
    public double getParticleMass(int id)
    {
        return mass[id];
    }
    
 // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    // ** QUAD ACCESS
    // *******************************************************************************************************************************
    // *******************************************************************************************************************************
    
    public void setQuadSprite(int id, int value)
    {
        if (!isPlaying && !isPaused)
        {
            this.qspriteinit[id] = value;
        }
        this.qsprite[id] = value;
        return;
    }
    
    public int getQuadSprite(int id)
    {
        return qsprite[id];
    }
}
