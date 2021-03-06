package com.devandrepascoa.data_structure;

import com.devandrepascoa.main.Utils;

/**
 * Class that represents an object in the Canvas
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
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
        this.dx = Utils.randomGenerator(-2, 2);
        this.dy = Utils.randomGenerator(-2, 2);
    }


    /**
     * Used for updating the velocities
     * and checks for border collisions
     *
     * @param city a datastructure containing a population,
     *             and other useful things
     * @param width
     * @param height
     */
    public void update(City city, int width, int height) {
        //Updates velocities
        this.x += this.dx;
        this.y += this.dy;

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

    /**
     * Checks if another point is within radius of this instance
     *
     * @param p2     another point
     * @param radius touching radius
     * @return yes if it's within the radius
     */
    public boolean withinRadius(Point p2, int radius) {
        return getDistance(p2) <= radius;
    }

    /**
     * Function to get the distance between two points
     *
     * @param p2 another point
     * @return distance in pixels between the two objects
     */
    public float getDistance(Point p2) {
        return (float) Math.sqrt((this.x - p2.getX()) * (this.x - p2.getX()) + (this.y - p2.getY()) * (this.y - p2.getY()));
    }

    //ACCESSORS

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
}
