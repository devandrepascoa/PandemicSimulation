package data_structure;

import models_controllers_others.MathUtils;

/**
 * Class that represents an object in the Canvas
 *
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class Point {
    //Position
    protected int x;
    protected int y;
    //Velocity
    protected int dx;
    protected int dy;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = MathUtils.randomGenerator(-2, 2);
        this.dy = MathUtils.randomGenerator(-2, 2);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }


    /**
     * Used for updating the velocities
     * and checks for border collisions
     *
     * @param city a datastructure containing a population,
     *             and other useful things
     */
    public void update(City city) {
        //Updates velocities
        this.x += this.dx;
        this.y += this.dy;
        //Retrieves city borders
        int width = city.getConstants().width;
        int height = city.getConstants().height;

        //Does Border collision on x axis
        //and inverts velocity if it hits wall
        if (x < 0) {
            this.dx *= -1;
        } else if (x >= width) {
            this.dx *= -1;
        }

        //Does Border collision on y axis
        //and inverts velocity if it hits wall
        if (y < 0) {
            this.dy *= -1;
        } else if (y >= height) {
            this.dy *= -1;
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean withinRadius(Point p2, int radius) {
        return getDistance(p2) <= radius;
    }

    public float getDistance(Point p2) {
        return (float) Math.sqrt((this.x - p2.getX()) * (this.x - p2.getX()) + (this.y - p2.getY()) * (this.y - p2.getY()));
    }
}
