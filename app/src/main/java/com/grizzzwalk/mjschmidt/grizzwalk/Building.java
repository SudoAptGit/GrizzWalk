package com.grizzzwalk.mjschmidt.grizzwalk;

/**
 * Created by austindanaj on 9/30/16.
 */

public class Building {

    //==============================================================================
            //Variable for singleton
        private static Building sharedInstance = new Building();
        private Building(){ }
        public static Building getInstance(){return sharedInstance;}

    //==============================================================================
                        //{rows,columns}
    private int [] ERROR    = {-1,-1}; //Error
    private int [] HHB      = {0,0}; // Human Health Building
    private int [] GHC      = {1,1}; // Graham Heath Center
    private int [] VBH      = {1,3}; // Vanderberg Hall
    private int [] WH       = {2,1}; // Wilson Hall
    private int [] POND1    = {2,2}; // First Pond
    private int [] POND2    = {2,3}; // Second Pond
    private int [] FTZ      = {2,4}; // Fitzgerald House
    private int [] ANI      = {2,5}; // Anibal House
    private int [] PRY      = {2,6}; // Pryale House
    private int [] NFH      = {3,1}; // North Foundation Hall
    private int [] OC       = {3,2}; // Oakland Center
    private int [] ODH      = {3,4}; // O'Dowd Hall
    private int [] ORENA    = {3,6}; // O'Rena
    private int [] SFH      = {4,1}; // South Foundation Hall
    private int [] EMPTY7   = {4,2}; // Empty Space
    private int [] CLK      = {4,3}; // Clock Tower
    private int [] EMPTY2   = {4,4}; // Empty Space
    private int [] EMPTY3   = {4,5}; // Empty Space
    private int [] REC      = {4,6}; // Rec Center
    private int [] EMPTY1   = {5,1}; // Empty Space
    private int [] FNT      = {5,2}; // Fountain
    private int [] LIB      = {5,3}; // Library
    private int [] EMPTY4   = {5,4}; // Empty Space
    private int [] EMPTY5   = {5,5}; // Empty Space
    private int [] EMPTY6   = {5,6}; // Empty Space
    private int [] HH       = {6,1}; // Hannah Hall
    private int [] DH       = {6,2}; // Dodge Hall
    private int [] EC       = {6,4}; // Engineering Center
    private int [] EH       = {6,5}; // Elliott Hall
    private int [] VAR      = {6,6}; // Varner Hall
    private int [] MSC      = {7,1}; // Mathematics and Science Center
    private int [] PH       = {7,7}; // Pawley Hall
    private int [] CAS      = {8,1}; // College of Arts and Sciences Annex

    //==============================================================================

    private int [] startLocation;
    private int [] endLocation;
    private int [][] blocked = {{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},{8,0},{1,2},{1,4},{1,5},{1,6},{1,7},
                                {1,8},{2,7},{2,8},{3,3},{3,5},{3,7},{3,8},{4,7},{4,8},{5,7},{5,8},{6,3},{6,7},{6,8},{7,2},{7,3},{7,4},{7,5},{7,6},{7,8},{8,2},
                                {8,3},{8,4},{8,5},{8,6},{8,7},{8,8}};
    private int [][] list = {HHB, GHC, VBH, WH, POND1, POND2, FTZ, ANI, PRY, NFH, OC, ODH, ORENA, SFH, EMPTY7, CLK, EMPTY2,
                             EMPTY3, REC, EMPTY1, FNT, LIB, EMPTY4, EMPTY5, EMPTY6, HH, DH, EC, EH, VAR, MSC, PH, CAS};

    private String [] names = {"Human Health Building","Graham Health Center", "Vandenberg Hall", "Wilson Hall", "Pond", "Pond", "Fitzgerald House",
            "Anibal House", "Pryale House", "North Foundation Hall", "Oakland Center", "O'Dowd Hall", "O'Rena", "South Foundation Hall", "Walking", "Clock Tower",
            "Walking", "Walking", "REC Center", "Walking", "The Fountain", "Library", "Walking", "Walking", "Walking", "Hannah Hall", "Dodge Hall", "Engineering Center", "Elliott Hall", "Varner Hall", "Mathematics and Science Center",
            "Pawley Hall", "College of Arts and Sciences Annex"};

    //==============================================================================

    public void setStartLocation(String newStartLoc){
        switch(newStartLoc){
            case "HHB":
                this.startLocation = HHB;
                break;
            case "GHC":
                this.startLocation = GHC;
                break;
            case "WH":
                this.startLocation = WH;
                break;
            case "ANI":
                this.startLocation = ANI;
                break;
            case "PRY":
                this.startLocation = PRY;
                break;
            case "NFH":
                this.startLocation = NFH;
                break;
            case "OC":
                this.startLocation = OC;
                break;
            case "ODH":
                this.startLocation = ODH;
                break;
            case "EH":
                this.startLocation = EH;
                break;
            case "VAR":
                this.startLocation = VAR;
                break;
            case "PH":
                this.startLocation = PH;
                break;
            case "SFH":
                this.startLocation = SFH;
                break;
            case "CLK":
                this.startLocation = CLK;
                break;
            case "LIB":
                this.startLocation = LIB;
                break;
            case "HH":
                this.startLocation = HH;
                break;
            case "DH":
                this.startLocation = DH;
                break;
            case "EC":
                this.startLocation = EC;
                break;
            case "MSC":
                this.startLocation = MSC;
                break;
            case "CAS":
                this.startLocation = CAS;
                break;
            default:
                this.startLocation = ERROR;
                break;
        }
    }
    public void setEndLocation(String newEndLoc){
        switch(newEndLoc){
            case "HHB":
                this.endLocation = HHB;
                break;
            case "GHC":
                this.endLocation = GHC;
                break;
            case "WH":
                this.endLocation = WH;
                break;
            case "ANI":
                this.endLocation = ANI;
                break;
            case "PRY":
                this.endLocation = PRY;
                break;
            case "NFH":
                this.endLocation = NFH;
                break;
            case "OC":
                this.endLocation = OC;
                break;
            case "ODH":
                this.endLocation = ODH;
                break;
            case "EH":
                this.endLocation = EH;
                break;
            case "VAR":
                this.endLocation = VAR;
                break;
            case "PH":
                this.endLocation = PH;
                break;
            case "SFH":
                this.endLocation = SFH;
                break;
            case "CLK":
                this.endLocation = CLK;
                break;
            case "LIB":
                this.endLocation = LIB;
                break;
            case "HH":
                this.endLocation = HH;
                break;
            case "DH":
                this.endLocation = DH;
                break;
            case "EC":
                this.endLocation = EC;
                break;
            case "MSC":
                this.endLocation = MSC;
                break;
            case "CAS":
                this.endLocation = CAS;
                break;
            default:
                this.endLocation = ERROR;
                break;

        }
    }
    public int [] getStartLocation(){return startLocation;}
    public int [] getEndLocation(){return endLocation;}
    public int [][] getList(){return list;}
    public String [] getNames(){ return names;}
    public int [][] getBlocked(){return blocked; }

}
