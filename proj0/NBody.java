/**
 * class NBody, the simulator
 */
public class NBody {
    private static String backgroud = "images/starfield.jpg";

    /** read the radius of universe from the file */
    public static double readRadius(String path) {
        In in = new In(path);
        in.readInt();
        return in.readDouble();
    }

    /** read the planet data from the file */
    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int N = in.readInt();
        in.readDouble();
        Planet[] planetArray = new Planet[N];
        for (int i = 0; i < N; i++) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String image = in.readString();
            planetArray[i] = new Planet(xPos, yPos, xVel, yVel, mass, image);
        }
        return planetArray;
    }

    /** the entry point */
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        double time = 0.0;
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-1.0 * radius, radius);
        double[] xForceArray = new double[planets.length];
        double[] yForceArray = new double[planets.length];
        while (time < T) {
            for (int i = 0; i < planets.length; i++) {
                xForceArray[i] = planets[i].calcNetForceExertedByX(planets);
                yForceArray[i] = planets[i].calcNetForceExertedByY(planets);
            }
            StdDraw.picture(0, 0, backgroud);
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForceArray[i], yForceArray[i]);
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }

}
