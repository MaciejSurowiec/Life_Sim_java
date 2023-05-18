package com.maciejsurowiec.lifesim.animals;

import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.*;

public class Human extends Animal {
    private int power;
    private int move;
    public boolean life;
    private final int POWER_LENGTH = 5;
    public static final String NAME = "a Human";
    public static final int ID = 4;
    public static final Color AVATAR = new Color(6, 36, 127);

    public Human(Vector pos, World world) {
        strength = 5;
        initiative = 4;
        toYoung = true;
        this.world = world;
        position = pos;
        power = 0;
        move = 0;
        life = true;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String toString() {
        return ID + " " +
                strength + " " +
                position.toString() + " " +
                power;
    }

    public void setPower(int power) { this.power = power; }

    public void makeChild(Vector pos) { Human human = new Human(pos, world); }

    public String speak() { return NAME; }

    public Color getAvatar() { return AVATAR; }

    public int getId() { return ID; }

    public void action() {
        move(move);
         if (power > 0) killEverythingAround();

         if (power != 0) power++;

         if (power == POWER_LENGTH + 1) power = -1 * POWER_LENGTH;
    }

    private void killEverythingAround() {
        for (int i = 0; i < Vector.DIRECTIONS; i++) {
            if (world.checkPosition(Vector.add(position, Vector.direction(i)))) {
                if (!world.freePosition(Vector.add(position, Vector.direction(i)))) {
                    Organism temp = world.getMapElement(Vector.add(position, Vector.direction(i)));
                    world.setMapElement(Vector.add(position, Vector.direction(i)), null);
                    world.commentator.killingFromDistance(this,temp);
                    temp.remove();
                }
            }
        }
    }

    public void activatePower() {
        if(checkPower()) {
            power = 1;
            world.commentator.specialAction(POWER_LENGTH - (power - 1)) ;
        } else if (power < 0) world.commentator.powerUsed( POWER_LENGTH - (power - 1) );
            else world.commentator.powerAction(power * -1);
    }

    public boolean checkPosition(int pos) {
        return world.checkPosition(Vector.add(position, Vector.direction(pos)));
    }

    private boolean  checkPower() {
        return power == 0;
    }

    public boolean getLife() { return life; }

    public void getMove(int pos) { this.move = pos; }
}
