package com.maciejsurowiec.lifesim.animals;
import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Wolf extends Animal {
    public static final String NAME = "a Wolf";
    public static final int ID = 7;
    public static final Color AVATAR = new Color(96, 96, 96);

    public Wolf(Vector pos, World world) {
        strength = 9;
        initiative = 5;
        toYoung = true;
        this.world = world;
        position = pos;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String speak() { return NAME; }

    public Color getAvatar() { return AVATAR; }

    public int getId() { return ID; }

    public void makeChild(Vector pos) { Wolf org = new Wolf(pos, world); }
}
