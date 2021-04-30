public class NBody{
	public static double readRadius(String Path){
		In in = new In(Path);
		int N = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String Path){
		In in = new In(Path);
		int N = in.readInt();
		Planet[]  PlanetArray = new Planet[N];
		double radius = in.readDouble();
		for (int i=0; i < N; i ++) {
			/* Each line has the rank of a country, then its
			 * name, then its production in metric tons, and
			 * finally the fraction of world salt output it produces. */
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			PlanetArray[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
		}
		return PlanetArray;
	}
	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dT = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = NBody.readRadius(filename);
		Planet[] PlanetArray= NBody.readPlanets(filename);

		StdDraw.enableDoubleBuffering();
		double t = 0;
		while (t < T) {
			double[] xForces = new double[PlanetArray.length];
			double[] yForces = new double[PlanetArray.length];
			for (int count = 0; count < PlanetArray.length;  count++){
				xForces[count] = PlanetArray[count].calcNetForceExertedByX(PlanetArray);
				yForces[count] = PlanetArray[count].calcNetForceExertedByY(PlanetArray);
			}
			for (int n = 0; n < PlanetArray.length; n++){
				PlanetArray[n].update(dT, xForces[n],yForces[n]);
			}

			StdDraw.setScale(-radius, radius);
			StdDraw.picture(0,0,"images/starfield.jpg");
			for (Planet p : PlanetArray){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			t = t + dT;
		}
		StdOut.printf("%d\n", PlanetArray.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < PlanetArray.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					PlanetArray[i].xxPos, PlanetArray[i].yyPos, PlanetArray[i].xxVel,
					PlanetArray[i].yyVel, PlanetArray[i].mass, PlanetArray[i].imgFileName);
		}
	}
}