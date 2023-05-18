package com.maciejsurowiec.lifesim.animals;

import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Sheep extends Animal {
    public static final String NAME = "a Sheep";
    public static final int ID = 5;
    public static final Color AVATAR = new Color(255,255,255);

    public Sheep(Vector pos, World world) {
        strength = 4;
        initiative = 4;
        toYoung = true;
        this.world = world;
        position = pos;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String speak() { return NAME; }

    public Color getAvatar() { return AVATAR; }

    public int getId() { return ID; }

    public void makeChild(Vector pos) { Sheep sheep = new Sheep(pos, world); }
}
