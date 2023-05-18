package com.maciejsurowiec.lifesim.animals;

import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Turtle extends Animal {
    public static final String NAME = "a Turtle";
    public static final int ID = 6;
    private final int CHANCE_TO_MOVE = 75;
    protected final int KILLING_STRENGTH = 5;
    public static final Color AVATAR = new Color(51,102,0);

    public Turtle(Vector pos, World world) {
        strength = 2;
        initiative = 1;
        toYoung = true;
        this.world = world;
        position = pos;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }


    public String speak() { return NAME; }

    public Color getAvatar() { return AVATAR; }

    public int getId() { return ID; }

    public void makeChild(Vector pos) { Turtle t = new Turtle(pos, world); }

    public void action() {
        if (toYoung) toYoung = false;
        else {
            if (randomGenerator.nextInt(PERCENTAGE) > CHANCE_TO_MOVE) {
                move(randomGenerator.nextInt(Vector.DIRECTIONS));
            }
        }
    }

    public int collision(Organism temp) {
        if (temp.SameSpecies(AVATAR)) {
            if (!toYoung && !temp.isToYoung()) {
                world.commentator.birth(this);
                breeding();
            }

            return BIRTH;
        } else {
           world.commentator.attack(temp,this);
            if (strength < temp.getStrength()) {
                if (temp.getStrength() >= KILLING_STRENGTH) {
                    world.commentator.killing(temp,this);
                    world.setMapElement(position, null);
                    remove();
                    return VICTORY;
                } else {
                    world.commentator.attackReflection(this,temp);
                    return BIRTH;
                }
            } else {
                world.commentator.killing(this,temp);
                world.checkHuman(temp);
                return DEATH;
            }
        }
    }
}