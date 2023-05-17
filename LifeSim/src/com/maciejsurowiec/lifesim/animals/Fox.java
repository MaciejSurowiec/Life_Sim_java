package com.maciejsurowiec.lifesim.animals;

import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Fox extends Animal {

    public  static final String NAME = "a Fox";
    public static final int ID = 3;

    public Fox(Vector pos, World world) {
        strength = 3;
        initiative = 7;
        toYoung = true;
        this.world = world;
        position = pos;
        avatar = new Color(255,128,0);
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String speak() { return NAME; }

    public int getId() { return ID; }

    public void makeChild(Vector pos) { Fox fox = new Fox(pos, world); }

    public void move(int pos) {
        if (pos < Vector.DIRECTIONS * 2) {
            if (!world.checkPosition(Vector.add(position, Vector.direction(pos)))) {
                move(pos + 1);
            } else if (!world.freePosition(Vector.add(position, Vector.direction(pos)))) {
                Organism opponent = world.getMapElement(Vector.add(position, Vector.direction(pos)));
                if (strength < opponent.getStrength()) {
                    world.commentator.escape(this,opponent);
                    move(pos + 1);
                } else {
                    world.setMapElement(position, null);
                    int temp = opponent.collision(this);
                    if (temp > 0) {
                        if (temp == DEATH) remove();
                        else if (temp == VICTORY) {
                            position = Vector.add(position, Vector.direction(pos));
                            world.setMapElement(position, this);
                        }
                    } else world.setMapElement(position, this);
                }
            } else {
                world.setMapElement(position, null);
                world.setMapElement(Vector.add(position, Vector.direction(pos)), this);
                position = Vector.add(position, Vector.direction(pos));
            }
        } else {
            world.setMapElement(position,null);
            world.commentator.cantMove(this);
            remove();
        }
    }
}
