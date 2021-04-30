public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static double G;

	public Planet(double xP, double yP, double xV, 
			double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
		G = 6.67e-11;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double r = java.lang.Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) + (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
		return r;
		}

	public double calcForceExertedBy(Planet p) {
		double F = G * this.mass * p.mass / this.calcDistance(p) / this.calcDistance(p);
		return F;
	}

	public double calcForceExertedByX(Planet p) {
		double Fx = this.calcForceExertedBy(p) *(p.xxPos-this.xxPos)/this.calcDistance(p);
		return Fx;
	}
	public double calcForceExertedByY(Planet p) {
		double Fy = this.calcForceExertedBy(p) *(p.yyPos-this.yyPos)/this.calcDistance(p);
		return Fy;
	}
	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double Fnetx = 0.0;
		for (int i = 0; i < allPlanets.length; i++) {
			if (this.equals(allPlanets[i])) {
				continue;
			}
			Fnetx += this.calcForceExertedByX(allPlanets[i]);
		}
		return Fnetx;
	}
	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double Fnety = 0.0;
		for (int i = 0; i < allPlanets.length; i++) {
			if (this.equals(allPlanets[i])) {
				continue;
			}
			Fnety += this.calcForceExertedByY(allPlanets[i]);
		}
		return Fnety;
	}
	public void update(double t, double fx, double fy) {
		double Anetx = fx/this.mass;
		double Anety = fy/this.mass;
		this.xxVel = this.xxVel + t * Anetx;
		this.yyVel = this.yyVel + t * Anety;
		this.xxPos = this.xxPos + t * this.xxVel;
		this.yyPos = this.yyPos + t * this.yyVel;
	}
	public void draw(){
		StdDraw.picture(this.xxPos,this.yyPos,"images/"+this.imgFileName);
	}

}

