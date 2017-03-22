package asteroids.model;
 
import java.util.Collection;
import java.util.Set;

import asteroids.util.ModelException;
//import asteroids.util.ModelException;
import be.kuleuven.cs.som.annotate.*;
 
/**
 * A class for dealing with ships in a costum game loosely based
 * on asteroids.
 *
 * @invar The velocity of each ship must be a valid velocity for a ship
 *        | isValidVelocity(getVelocity()[0]) && isValidVelocity(getVelocity()[1])
 *
 * @invar The position of each ship must be a valid position for a ship
 *        | isValidPosition(getPosition()[0]) && isValidPosition(getPosition()[1])
 *
 * @invar The radius of each ship must be a valid radius for a ship
 *        | isValidRadius(getRadius())
 *
 * @invar The orientation of each ship must be a valid orientation for a ship
 *        | isValidOrientation(getOrientation())
 *
 * @invar The maximum speed of each ship must be between 0 and 300 000
 *        | getMaxVelocity > 0 && getMaxVelocity < 300000
 *
 * @version 1.0
 * @author Brent De Bleser & Jesse Geens
 */
public  class Ship {
   
    //Variables
   
    /**
     * Variable containing the coordinates of the ship in the form of an array with length 2.
     */
    public double[] position = new double[2];
   
    /**
     * variable containing the velocity on the x and y axis as an array of length 2.
     */
    public double[] velocity = new double[2];
   
    /**
     * Variable containing the maximum velocity of the ship in km/s.
     */
    public double maxVelocity = 300000;
   
    /**
     * Variable registering the orientation of the ship in radients with right being 0 and left being pi.
     */
    public double orientation;
   
    /**
     * Variable registering the radius of the ship as a radius of a circle.
     */
    public double radius;
    
    public double direction;
    
    public double mass;
    
    public double acceleration;
   
    public boolean isTerminated;
    
    /**
     * Variable that determines whether or not debug output is enabled
     */
    public static boolean debug = false;
   
    //Initializers
   
    //Defensive
   /**
    * Initialize this new ship with a given x- and y-position, x- and y-velocity, radaius and
    * a given orientation
    *
    * @param  xPosition
    *         The x-position of the ship.
    * @param  yPosition
    *         The y-position of the ship.
    * @param  xVelocity
    *         The ship's velocity on the x-axis
    * @param  yVelocity
    *         The ship's velocity on the y-axis
    * @param  radius
    *         The radius (size) of the ship
    * @param  orientation
    *         The orientation of the ship in radians.
    * @effect The given x-position is set to the new x-position and
    *         the given y-position is set to the new y-position.
    *         | this.setShipPosition(xPosition, yPosition)
    * @effect The given x-velocity is set to the new x-velocity and
    *         the given y-velocity is equal to the new y-velocity.
    *         | this.setShipVelocity(xVelocity, yVelocity)
    * @post   The given radius is equal to the new radius.
    *         | new.getShipRadius() == radius
    * @effect The given orientation is set to the new orientation.
    *         | this.setShipOrientation(orientation)
    * @throws IllegalArgumentException
    *         Throws an IllegalArgumentException if any of the given parameters is invalid
    *         | if(!(isValidPosition(xPosition) && isValidPosition(yPosition) && isValidVelocity(xVelocity) && isValidVelocity(yVelocity) && isValidOrientation(orientation) && isValidRadius(radius)))
    *         |     then throw new IllegalArgumentException("Illegal argument given at CreateShip")
    */
    public Ship createShip(double xPosition, double yPosition, double xVelocity, double yVelocity, double radius, double direction, double mass) throws IllegalArgumentException {
        if(isValidPosition(xPosition) && isValidPosition(yPosition) && isValidVelocity(xVelocity) && isValidVelocity(yVelocity) && isValidDirection(direction) && isValidRadius(radius)){
            try{this.setShipPosition(xPosition, yPosition);} catch(IllegalArgumentException ex){throw new IllegalArgumentException(ex.getMessage());}
            this.setShipVelocity(xVelocity, yVelocity);
            this.setShipDirection(direction);
            this.mass = mass;
            this.radius = radius;
            return this;
        }
        else{
            log("posx: " + isValidPosition(xPosition) + "; " + xPosition);
            log("posy: " + isValidPosition(yPosition) + "; " + yPosition);
            log("velx: " + isValidVelocity(xPosition) + "; " + xPosition);
            log("vely: " + isValidVelocity(yPosition) + "; " + yPosition);
            //log("ori: " + isValidOrientation(orientation) + "; " + orientation);
            log("rad: " + isValidRadius(radius) + "; " + radius);
            throw new IllegalArgumentException("Illegal argument given at CreateShip");
        }
    }
 
   
    //Getters
   
    /**
     * Returns the position of the ship.
     */
    @Basic
    public double[] getShipPosition(){
        return this.position;
    }
   
    /**
     *Returns the velocity of the ship.
     */
    @Basic
    public double[] getShipVelocity(){
        return this.velocity;
    }
   
    /**
     * Returns the maximum velocity of the ship.
     */
    @Basic
    public double getMaxVelocity(){
        return this.maxVelocity;
    }
   
    /**
     * Return the orientation of ship (in radians).
     */
    @Basic
    public double getShipOrientation(){
        return this.orientation;
    }
   
    /**
     * Return the radius of ship.
     */
    @Immutable
    @Basic
    public double getShipRadius(){
        return this.radius;
    }
    
	/**
	 * Check whether <code>ship</code> is terminated.
	 */
	public boolean isTerminatedShip() {
		return this.isTerminated;
	}
	/**
	 * Return the total mass of <code>ship</code> (i.e., including bullets
	 * loaded onto the ship).
	 */
	public double getShipMass() {
		return this.mass;
	}

	/**
	 * Return the world of <code>ship</code>.
	 */
	public World getShipWorld() {
		//return ship.getShipWorld();
		return null;
	}
	
	/**
	 * Return the acceleration of <code>ship</code>.
	 */
	public double getShipAcceleration() {
		return this.acceleration;
	}
	
	/**
	 * Return whether <code>ship</code>'s thruster is active.
	 */
	public boolean isShipThrusterActive() {
		//return ship.isShipThrusterActive();
		return false;
	}
   
    ///Setters
   
    //Defensive
    /**
     * Set the position on the X-axis to xPosition.
     * Set the position on the Y-axis to yPosition.
     *
     * @param  xPosition
     *         The x coordinate of the new position.
     * @param  yPosition
     *         The y coordinate of the new position;
     * @post   The arguments xPosition and yPosition become the X- and Y-coordinates respectively.
     *         | new.position[0] = xPosition;
     *         | new.position[1] = yPosition;
     * @throws IllegalArgumentException
     *         xPosition isn't valid.
     *         | (!isValidPosition(xPosition))
     * @throws IllegalArgumentException
     *         yPosition isn't valid.
     *         | (!isValidPosition(yPosition)
     */
    public void setShipPosition(double xPosition, double yPosition) throws IllegalArgumentException {
        if ( (!isValidPosition(xPosition)) || (!isValidPosition(yPosition))) throw new IllegalArgumentException("Invalid position");
        else {
            this.position[0] = xPosition;
            this.position[1] = yPosition;
        }
    }
   
    //Total
    /**
     * Set the velocity on the X-axis to xVelocity and
     * set the velocity on the Y_axis to yVelocity..
     *
     * @param xVelocity
     *        The new velocity on the X-axis.
     * @param yVelocity
     *        The new velocity on the Y-axis.
     * @post  If the given velocities for the X- and Y-axis are of type double and aren't infinite
     *        the new velocities on the X- and Y-axis are equal the given ones.
     *        | if (!(isValidVelocity(yVelocity)) || (isValidVelocity(xVelocity))
     *        |      then new.getShipVelocity()[0] == xVelocity
     *        |           new.getShipVelocity()[1] == yVelocity
     * @post  If the given velocity on the X- or Y-axis isn't a double or is infinity
     *        the new velocities on the X- and Y-axis will be equal to 0.
     *        | if ((isValidVelocity(yVelocity)) || (isValidVelocity(xVelocity))
     *        |      then new.getShipvelocity()[0] == 0
     *        |           new.getShipVelocity()[1] == 0
     *
     */
    public void setShipVelocity(double xVelocity, double yVelocity){
        if ( (!isValidVelocity(xVelocity)) || (!isValidVelocity(yVelocity))){
            this.velocity[0] = 0;
            this.velocity[1] = 0;
        }
        else {
            this.velocity[0] = xVelocity;
            this.velocity[1] = yVelocity;
        }
    }
   
    //Nominaal
    /**
     * A method to set to orientation of a ship
     * to a given value.
     * @param orientation
     *        The new orientation of the ship
     * @pre   The given orientation must be a double.
     *        | assert isValidDouble(orientation)
     * @post  The new orientation of the ship is the given orientation
     *        in radians notation with orientation equal or bigger than 0
     *        and smaller or equal to 2*Pi.
     *        | orientation = surplusRadians(orientation)
     *        | new.getShipOrientation == orientation
     */
    public void setShipDirection(double direction) {
        assert isValidDouble(direction);
        this.direction = direction;
    }
    
	/**
	 * Terminate ship.
	 */
	public void terminateShip() throws ModelException {
		
	}


	/**
	 * Enables or disables <code>ship</code>'s thruster depending on the value
	 * of the parameter <code>active</code>.
	 */
	public void setThrusterActive(boolean active) throws ModelException {
		//Ship.setThrusterActive(active);
	}

    //Methods
	
    //defensive
    /**
     * Moves the ship to a new position during a timespan of dt.
     *
     * @param   dt
     *          The time over which the ship is moving.
     * @throws  IllegalArgumentException
     *          Throws an IllegalArgumentException if dt is infinity or is smaller then zero..
     *         |  ((dt < 0.0) && ( Double.isInfinite(dt)))
     * @effect  Sets the ship position with the current position and velocity times dt.
     *         | this.setShipPosition(this.position[0] + (this.velocity[0] * dt), this.position[1] + (this.velocity[1] * dt))
     */
    public void move(double dt) throws IllegalArgumentException{
        if ((dt < 0.0) && ( Double.isInfinite(dt)))
            throw new IllegalArgumentException("Invalid time");
        else{
            try {
                   this.setShipPosition(this.position[0] + (this.velocity[0] * dt), this.position[1] + (this.velocity[1] * dt));
            }
            catch (IllegalArgumentException ex){
                throw new IllegalArgumentException(ex);
                }
            }    
    }
   
 
    //Total
    /**
     * A method to increase the velocity of a ship in the direction
     *  of its orientation.
     *  
     * @param amount
     *        The amount of velocity added in the current orientation.
     * @post  If amount is smaller then 0 or isn't a double it is set to 0.
     *        | if(!(amount >= 0) && (isValidDouble(amount)))
     *        |     then amount = 0
     * @effect te velocity of the ship is set to the sum of the new amount which is
     *         calculated upon the X- and Y-axis and the previous velocities on the X- and Y-axis.
     *        | double alpha = orientation
     *        | double vx = this.getShipVelocity()[0] + amount * Math.cos(alpha)
     *        | double vy = this.getShipVelocity()[1] + amount * Math.sin(alpha)
     *        | int velocity = (int) Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2))
     *        | if(!(velocity > this.getMaxVelocity()))
     *        |     then this.setShipVelocity(vx, vy)
     *        | else
     *        |     double vxl, vyl; //vx limited, vy limited
     *        |     vxl = Math.cos(alpha) * this.getMaxVelocity();
     *        |     vyl = Math.sin(alpha) * this.getMaxVelocity();
     *        | this.setShipVelocity(vxl, vyl);
     */
    public void thrust(double amount){
        if(!(amount >= 0) && (isValidDouble(amount)))
            amount = 0;
        double alpha = orientation; //Math.atan(this.getShipVelocity()[1] / this.getShipVelocity()[0]);
        double vx = this.getShipVelocity()[0] + amount * Math.cos(alpha);
        double vy = this.getShipVelocity()[1] + amount * Math.sin(alpha);
        int velocity = (int) Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
        if(!(velocity > this.getMaxVelocity()))
            this.setShipVelocity(vx, vy);
        else{
            double vxl, vyl; //vx limited, vy limited
            log("velocities: " + vx + "; " + vy);
            log("alpha: " + alpha);
            vxl = Math.cos(alpha) * this.getMaxVelocity();
            vyl = Math.sin(alpha) * this.getMaxVelocity();
            this.setShipVelocity(vxl, vyl);
            }
    }
   
    //Nominal
    /**
     * A method to turn a ship with a given angle.
     *
     * @param  angle
     *         The angle to be turned by the ship.
     * @effect The orientation of the ship is set to the current orientation added with angle.
     *         | new.getShipOrientation == this.orientation + angle
     */
    public void turn(double angle){
        assert isValidAngle(angle);
        this.setShipDirection(this.direction + angle);
    }
   
    //Defensive
    /**
     * A method to get the distance between two ships.
     *
     * @param ship
     *         The ship between which and the ship to which the method is invoked
     *         the distance is measured.
     * @return Returns the distance between ship and the ship on which the method is invoced.
     *         | double diffx = Math.abs(this.position[0] - ship.getShipPosition()[0])
     *         | double diffy = Math.abs(this.position[1] - ship.getShipPosition()[1])
     *         | double diff = Math.sqrt(Math.abs(square(diffx) - square(diffy)))
     *         | return == diff
     * @throws IllegalArgumentException
     *         Throws an IllegalArgumentException if the given ship is the same ship
     *         to which the method is invoced.
     *         | this == ship
     */
    public double getDistanceBetween(Ship ship) throws IllegalArgumentException{
        if(this == ship) throw new IllegalArgumentException("this == ship");
        else{
            double diffx = Math.abs(this.position[0] - ship.getShipPosition()[0]);
            double diffy = Math.abs(this.position[1] - ship.getShipPosition()[1]);
            double diff = Math.sqrt(Math.abs(square(diffx) - square(diffy)));
            return diff;
        }
    }
   
    //Defensive
    /**
     * A method to check whether two ships overlap.
     *
     * @param  ship
     *         The ship to check whether it and the ship on which the method is invoced
     *         are overlapping.
     * @return Returns true if the ship on which the method is invoced is the
     *         the same ship as ship.
     *         | if(this == ship)
     *         |    then return == true
     * @return Returns false if the ships don't overlap.
     *         |if(this.getDistanceBetween(ship) >= this.getShipRadius() + ship.getShipRadius())
     *         |    then return == false
     * @return Returns true if the ships overlap.
     *         |if(this.getDistanceBetween(ship) < this.getShipRadius() + ship.getShipRadius())
     *         |    then return == true
     */
    public boolean overlap(Ship ship){
        if(this == ship){
            return true;
        }
        else{
            double radius = this.getShipRadius() + ship.getShipRadius();
            if(this.getDistanceBetween(ship) >= radius)
                return false;
            else
                return true;
        }
    }
   
    //Defensive
    /**
     * A method to get the time it wil take to get two ships colliding
     * if they wil collide.
     * @param  ship
     *         The ship to get the time of collision from with itself and
     *         the ship on which the method is invoced.
     * @post   If the ships keep moving in the same way without an one of them getting changed
     *         in any way (velocity, position and radius must remain the same)
     *         The ships will collide with each other after the returned amount of time.
     *         The ships won't collide before the given amount of time given if not changed in anyway
     *         (velocity, position and radius must remain the same)
     *         | this.overlap(ship) == true
     *         |    This is true after the following code:
     *         |        this.move(this.getTimeToCollision(ship));
     *         |        ship.move(this.getTimeToCollision(ship));
     * @post   The ships will collide with each other after the returned amount of time.
     *         The ships won't collide before the given amount of time given if not changed in anyway
     *         (velocity, position and radius must remain the same)
     *         | this.overlap(ship) == False
     *         |    This is true after the following code:
     *         |        If (duration < this.getTimeToCollision(ship)){
     *         |            this.move(duration);
     *         |            ship.move(duration);
     *         |        }
     * @throws IllegalArgumentException
     *         Throws an IllegalArgumentException if the given ship is already overlapping
     *         with the ship to which the method is invoced.
     *         | this.overlap(ship)
     */
   
    public double getTimeToCollision(Ship ship) throws IllegalArgumentException{
           
        double a1 = ship.getShipVelocity()[0] - this.velocity[0];
        double a2 = ship.getShipVelocity()[1] - this.velocity[1];
        double a = square(a1) + square(a2);
        //exception 1:
        //The two ships follow an identical path so they don't collide.
        if (a == 0.0) {
            return Double.POSITIVE_INFINITY;
        }
       
        double b1 = ship.getShipPosition()[0] - this.position[0];
        double b2 = ship.getShipPosition()[1] - this.position[1];
        double b = 2*((b1 * a1) + (b2 *a2));
       
        double s = this.getShipRadius() + ship.radius;
        double c = square(b1) + square(b2) - square(s);
     
        //exception 2:
        //If the quadratic equation doesn't give an answer the ships' paths cross but
        //don't the ships don't touch each other.
        if (quadraticSolver(a, b, c)[2] == 1.0){
            return Double.POSITIVE_INFINITY;
        }
        else{
            double dt1 = quadraticSolver(a, b, c)[0];
            double dt2 = quadraticSolver(a, b, c)[1];
           
          //dt exceptions:
           
            //  exception 3:
            //  if both answers are negative the ships are moving away from each other.
            if (dt1 < 0 && dt2 <0){
                return Double.POSITIVE_INFINITY;
            }
            if ((dt1 <= 0 && dt2 >= 0 ) || (dt1 > 0 && dt2 < 0)){
                throw new IllegalArgumentException("Already overlaps while calling timeToCollision");
            }
            if (dt1 < dt2) return dt1;
            else return dt2 ;
        }
       
}
 
    //Defensive
    /**
     *A method to get the position where two ships collide.
     *
     * @param ship
     *        The ship between which and the ship to which the method invoced
     *        the collision position is searched.
     * @return Returns null if the ships don't collide.
     *         | if(this.getTimeToCollision(ship) == Double.POSITIVE_INFINITY)
     *         |    then return == null
     * @return Returns the collision position
     *         | double[] pos = new double[2]
     *         | double[] cp1 = this.getDistanceTraveled(this.getTimeToCollision(ship))
     *         | double[] cp2 = ship.getDistanceTraveled(this.getTimeToCollision(ship))
     *         | double s = this.getShipRadius() + ship.getShipRadius()
     *         | double diffx = Math.abs(cp2[0] - cp1[0])
     *         | double diffy = Math.abs(cp2[1] - cp1[1])
     *         | double cosinus, sinus
     *         |
     *         |if(diffx != 0)
     *         |     then cosinus = (square(diffx) + square(s) - square(diffy))/(2*diffx*s)
     *         |          sinus = Math.sin(Math.acos(cosinus))
     *         |          pos[0] = cp1[0] + this.getShipRadius() * cosinus
     *         |          pos[1] = cp1[1] + this.getShipRadius() * sinus
     *         | else
     *         |     then pos[0] = this.getDistanceTraveled(this.getTimeToCollision(ship))[0]
     *         |          pos[1] = this.getDistanceTraveled(this.getTimeToCollision(ship))[1] + this.getShipRadius()
     *         | return == pos
     * @throws IllegalArgumentException
     *         Throws IllegalArgumentException if the ships already overlap.
     *         | this.overlap(ship)
     */
    public double[] getCollisionPosition(Ship ship) throws IllegalArgumentException{
        if(this.overlap(ship)){
            log("Ships overlap");
            throw new IllegalArgumentException("Ships overlap");
        }
        else{
            if(this.getTimeToCollision(ship) == Double.POSITIVE_INFINITY){
                log("Ships will never collide");
                return null;
            }
            else{
                if(debug) System.out.println("Calculating collision position");
               
                double[] pos = new double[2];
                double[] cp1 = this.getDistanceTraveled(this.getTimeToCollision(ship));
                double[] cp2 = ship.getDistanceTraveled(this.getTimeToCollision(ship));
                double s = this.getShipRadius() + ship.getShipRadius();
                double diffx = Math.abs(cp2[0] - cp1[0]);
                double diffy = Math.abs(cp2[1] - cp1[1]);
                double cosinus, sinus;
               
                if(diffx != 0){
                    cosinus = (square(diffx) + square(s) - square(diffy))/(2*diffx*s);
                    sinus = Math.sin(Math.acos(cosinus));
                    log("sinus: " + sinus + "; cosinus: " + cosinus);
                    pos[0] = cp1[0] + this.getShipRadius() * cosinus;
                    pos[1] = cp1[1] + this.getShipRadius() * sinus;
                }
                else{
                    log("diffx is 0");
                    pos[0] = this.getDistanceTraveled(this.getTimeToCollision(ship))[0];
                    pos[1] = this.getDistanceTraveled(this.getTimeToCollision(ship))[1] + this.getShipRadius();
                }
               
                return pos;
            }
        }
    }
   
    //Helper methods
 
    //Total
    /**
    * A helper method to solve quadratic equations.
    *
    * @param a
    *        The factor of the second power term.
    * @param b
    *        The factor of the first power term.
    * @param c
    *        The factor of the zero power term.
    * @post  If a x^2 + b x +c doesn't have a solution
    *        return an array of 3 elements with the first
    *        two being 0.0 and the last being 1.0.
    *        | if (4 * a *c > Math.pow(b, 2))
    *        |      then x[0] = 0.0
    *        |           x[1] = 0.0
    *        |           x[2] = 1.0
    *        |           result == x
    * @post  If a x^2 + b x + c has a solution terurn
    *        an array with the first two elemnts being the two solutions
    *        and the last elemnt being 1.0.
    *        | if (4 * a *c > Math.pow(b, 2))
    *        |       then double d = Math.pow(b, 2) + (4 * a *c)
    *        |            x[0] = (-b + Math.sqrt(d)) / (2 * a)
    *        |            x[1] = (-b - Math.sqrt(d)) / (2 * a)
    *        |            x[2] = 0.0
    *        |            result == x
    */
    public double[] quadraticSolver(double a, double b, double c){
        double d = square(b) - (4 * a *c);
        double[] x = new double[3];
        if (d < 0){
            x[0] = 0.0;
            x[1] = 0.0;
            x[2] = 1.0;
            return x;
        }
        else{
            x[0] = ((-b + Math.sqrt(d)) / (2 * a));
            x[1] = ((-b - Math.sqrt(d)) / (2 * a));
            x[2] = 0.0;
            return x;
        }
    }
   
    //Nominal
    /**
     * A helper method to replace Math.pow(x, 2) with square(x).
     *
     * @param  x
     *         The number to square.
     * @pre    x must be a valid double.
     *         | isValidDouble(x)
     * @return x is squared.
     *         | result == Math.pow(x, 2)
     */
    public double square(double x){
        assert isValidDouble(x);
        return Math.pow(x, 2);
    }
   
    //Nominal
    /**
     * A helper method to get the centre of a ship when it traveled over a time.
     *
     * @param  time
     *         The time over which the ship moves.
     * @pre    time must be bigger then zero and musn't be infinty.
     *         | (time > 0) && isValidDouble(time)
     * @return Returns an array of length two, which contains the coordinates of the center of the ship when it
     *         has moved over time.
     *         | double pos[] = new double[2]
     *         |pos[0] = this.position[0] + this.velocity[0] * time
     *         |pos[1] = this.position[1] + this.velocity[1] * time
     *         |return == pos
     */
    public double[] getDistanceTraveled(double time){
        assert ((time > 0) && isValidDouble(time));
        double pos[] = new double[2];
        pos[0] = this.position[0] + this.velocity[0] * time;
        pos[1] = this.position[1] + this.velocity[1] * time;
        return pos;
    }
   
    //Total
    /**
     * A helper method to convort radian notation bigger or equal to 2*Pi
     * to a notation smaller or equal to 2*Pi.
     *
     * @param  radians
     *         The radians notation to be converted.
     * @return Return an equivalent notation for radians between or equal to 0 and 2*Pi.
     *         | while(radians > 2*Math.PI)
     *         |        radians -= 2*Math.PI
     *        | while(radians < 0)
     *         |    radians += 2*Math.PI
     *         | return == radians
     *        
     *
     */
    public double surplusRadians(double radians){
       while(radians > 2*Math.PI){
            radians -= 2*Math.PI;
        }
        while(radians < 0){
            radians += 2*Math.PI;
            }
        return radians;
    }
   
    //Validity checkers
   
    //Nominal
    /**
     * Check whether the given orientation is a valid orientation for
     * a ship.
     *
     * @param   orientation
     *          The orienation to check.
     * @return  True if and only if the given orientation is bigger or equal to zero and smaller or equal to 2*pi.
     *         | result == ((orientation >= 0) && (orientation <= (2*Math.PI))
     */
    public boolean isValidDirection(double direction){
        //return ((direction >= 0) && (direction <= 2*Math.PI));
    	return isValidDouble(direction);
    }
    
    /**************
	 * SHIP: Methods related to loaded bullets
	 *************/

	/**
	 * Return the set of all bullets loaded on <code>ship</code>.
	 * 
	 * For students working alone, this method may return null.
	 */
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException {
		return null;
	}

	/**
	 * Return the number of bullets loaded on <code>ship</code>.
	 */
	public int getNbBulletsOnShip(Ship ship) throws ModelException {
		return 0;
	}

	/**
	 * Load <code>bullet</code> on <code>ship</code>.
	 */
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {}

	/**
	 * Load <code>bullet</code> on <code>ship</code>.
	 * 
	 * For students working alone, this method must not do anything.
	 */
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {}

	/**
	 * Remove <code>ship</code> from <code>ship</code>.
	 */
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {}

	/**
	 * <code>ship</code> fires a bullet.
	 */
	public void fireBullet(Ship ship) throws ModelException {}
     
     //Total
     /**
     * Check whether the given velocity is a valid velocity for
     * a ship.
     *
     * @param   velocity
     *          The velocity to check.
     * @return  True if and only if the given velocity is a double isn't negative and isn't faster then maxVelocity.
     *         | result == ((velocity >= -1*this.maxVelocity) && (velocity <= this.maxVelocity) && isValidDouble(velocity))
     */
    public boolean isValidVelocity(double velocity){
        return ((velocity >= -1*this.maxVelocity) && (velocity <= this.maxVelocity) && isValidDouble(velocity));
    }
   
     //defensive
    /**
     * Check whether the given position is a valid position for
     * a ship.
     *  
     * @param   position
     *          The position to check.
     * @return  True if and only if the given position is a double.
     *          | result == isValidDouble(position)
     */
    public boolean isValidPosition(double position){
        return isValidDouble(position);
    }
   
    //Total
    /**
     * Check whether the given angle is a valid angle.
     *  
     * @param   angel
     *          The angle to check.
     * @return  True if and only if the given angle is a double.
     *          | result == isValidDouble(double number)
     */
    public boolean isValidAngle(double angle){
        return isValidDouble(angle);
    }
   
     //defensive
    /**
     * Check whether the given radius is a valid radius for
     * a ship.
     *  
     * @param   radius
     *          The radius to check.
     * @return  True if and only if the given radius is a double and is bigger or equal to 10.
     *          | result == ( radius >= 10 && isValidDouble(radius))
     */
    public boolean isValidRadius(double radius){
        return ( radius >= 10 && isValidDouble(radius));
    }
   
    //Total
    /**
     * Check whether the given number is a double.
     *
     * @param  number
     *         The double to check.
     * @return True if and only if the given double is a finite number
     *         | result == (!(Double.isNaN(number) || (Double.isInfinite(number))))
     */
    @Model
    public boolean isValidDouble(double number){
        if(Double.isNaN(number) || Double.isInfinite(number) || number < Double.MAX_VALUE)
            return false;
        return true;
    }
   
    //Total
    /**
     * A helper method to make a small errorMessage
     *
     * @param errorMessage
     *        The error message that needs to be shown
     * @post  If debug equals true the errorMessage is shown.
     *        | if debug == true
     *        |     then System.out.println(errorMessage)
     * @post  If debug equals false the errorMessage isn't shown.
     *        | if debug == true
     *        |     then nothing
     */
    public void log(String errorMessage){
        if(debug) System.out.println(errorMessage);
    }
}

