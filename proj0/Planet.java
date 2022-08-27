/** 
 * class Planet
 */
public class Planet {
    /** Instance variables */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    /** Constructor of Planet */
    public Planet(double xP, double yP, double xV,
            double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    /** Copy constructor of Planet */
    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /** Caculate the distance from another planet */
    public double calcDistance(Planet p){
        double xDistance = this.xxPos - p.xxPos;
        double yDistance = this.yyPos - p.yyPos;
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

    /** Caculate the force between two planets */
    public double calcForceExertedBy(Planet p){
        double distance = this.calcDistance(p);
        return 6.67 * p.mass * this.mass / (distance * distance) / 100000000000.0;
    }

    /** Caculate the x direction force */
    public double calcForceExertedByX(Planet p){
        double distance = this.calcDistance(p);
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / distance;
    }

    /** Caculate the y direction force */
    public double calcForceExertedByY(Planet p){
        double distance = this.calcDistance(p);
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / distance;
    }

    /** Caculate the x direction force between this planet and a list of planet */
    public double calcNetForceExertedByX(Planet[] pList){
        double calcXResult = 0.0;
        for (Planet p : pList){
            if (p.equals(this)){
                continue;
            }
            calcXResult = calcXResult + this.calcForceExertedByX(p);
        }
        return calcXResult;
    }

    /** Caculate the y direction force between this planet and a list of planet */
    public double calcNetForceExertedByY(Planet[] pList){
        double calcYResult = 0.0;
        for (Planet p : pList){
            if (p.equals(this)){
                continue;
            }
            calcYResult = calcYResult + this.calcForceExertedByY(p);
        }
        return calcYResult;
    }

    /** Update the state of the planet between dt */
    public void update(double dt, double xForce, double yForce){
        double xAccelerate = xForce / this.mass;
        double yAccelerate = yForce / this.mass;
        this.xxVel = this.xxVel + xAccelerate * dt;
        this.yyVel = this.yyVel + yAccelerate * dt;
        this.xxPos = this.xxPos + this.xxVel * dt;
        this.yyPos = this.yyPos + this.yyVel * dt;
    }

    /** Draw itself */
    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}