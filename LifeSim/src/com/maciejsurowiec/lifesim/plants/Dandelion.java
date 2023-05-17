package com.maciejsurowiec.lifesim.plants;
import com.maciejsurowiec.lifesim.Plant;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Dandelion extends Plant {

    final int ATTEMPTS = 3;
    public static final String NAME = "a Dandelion";
    public static final int ID = 8;

    public Dandelion(Vector vec, World world) {
        this.world = world;
        position = vec;
        initiative = 0;
        strength = 0;
        avatar = new Color(151, 168, 62);
        toYoung = true;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String speak() { return NAME; }

    public int getId() { return ID; }

    public void action() {
        if (toYoung) toYoung = false;
        else {
            for (int i = 0; i < ATTEMPTS; i++) {
                if (randomGenerator.nextInt(PERCENTAGE) < POLLINATION) {
                    pollination(randomGenerator.nextInt(Vector.DIRECTIONS));
                    break;
                }
            }
        }
    }

    public void makeChild(Vector pos) {
        Dandelion dandelion = new Dandelion(pos, world);
    }
}