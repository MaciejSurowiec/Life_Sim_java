package com.maciejsurowiec.lifesim.plants;

import com.maciejsurowiec.lifesim.Plant;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;
import java.awt.Color;

public class Grass extends Plant {

    public  static final String NAME = "a Grass";
    public static final int ID = 9;

    public Grass(Vector vec, World world) {
        this.world = world;
        position = vec;
        initiative = 0;
        strength = 0;
        avatar = new Color(23, 199, 29);
        toYoung = true;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String speak() { return NAME; }

    public int getId() { return ID; }

    public void makeChild(Vector pos) { Grass grass = new Grass(pos, world); }
}
