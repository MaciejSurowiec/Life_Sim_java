package com.maciejsurowiec.lifesim.plants;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Plant;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Guarana extends Plant {
    private final int STRENGTH = 3;
    public static final String NAME = "a Guarana";
    public static final int ID = 10;
    public static final Color AVATAR = new Color(146, 34, 70);

    public Guarana(Vector vec, World world) {
        this.world = world;
        position = vec;
        initiative = 0;
        strength = 0;

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
        temp.addStrength(STRENGTH);
        world.setMapElement(position, null);
        remove();
        return VICTORY;
    }

    public void makeChild(Vector pos) {
        Guarana guarana = new Guarana(pos, world);
    }
}
