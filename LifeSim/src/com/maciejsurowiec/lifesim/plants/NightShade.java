package com.maciejsurowiec.lifesim.plants;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Plant;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class NightShade extends Plant {
    public static final String NAME = "a NightShade";
    public static final int ID = 11;
    public static final Color AVATAR = new Color(255,51,51);

    public NightShade(Vector vec, World world) {
        this.world = world;
        position = vec;
        initiative = 0;
        strength = 99;
        toYoung = true;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public String speak() { return NAME; }

    public Color getAvatar() { return AVATAR; }

    public int getId() { return ID; }

    public int collision(Organism temp) {
        world.commentator.attack(temp,this);
        world.commentator.killing(temp,this);
        world.setMapElement(position, null);
        world.checkHuman(temp);
        remove();
        return DEATH;
    }

    public void makeChild(Vector pos) {
        NightShade nightShade = new NightShade(pos, world);
    }
}
