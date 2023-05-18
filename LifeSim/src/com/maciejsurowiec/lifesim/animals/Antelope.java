package com.maciejsurowiec.lifesim.animals;

import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;

public class Antelope extends Animal {
    protected int move;
    protected int ESCAPE = 50;
    public static final int ID = 1;
    public static final String NAME = "an Antelope";
    public static final Color AVATAR = new Color(255,255,153);

    public Antelope(Vector pos, World world) {
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

    public void makeChild(Vector pos) {
        Antelope antelope = new Antelope(pos, world);
    }

    public int getId() { return ID; }

    protected void move(int pos) {
        if (pos < Vector.DIRECTIONS * 2) {
            if (!world.checkPosition(Vector.add(position, Vector.direction(pos)))) move(pos + 1);
            else if (!world.freePosition(Vector.add(position, Vector.direction(pos)))) {
                Organism opponent = world.getMapElement(Vector.add(position, Vector.direction(pos)));
                world.setMapElement(position, null);
                int collisionResult = opponent.collision(this);
                if (collisionResult > 0) {
                    if (collisionResult == DEATH) remove();
                    else if (collisionResult == VICTORY) {
                        position = Vector.add(position, Vector.direction(pos));
                        world.setMapElement(position, this);
                        if (move < 2) {
                            move++;
                            move(randomGenerator.nextInt(Vector.DIRECTIONS));
                        }
                    }
                } else {
                    world.setMapElement(position, this);
                    if (move < 2) {
                        move++;
                        move(randomGenerator.nextInt(Vector.DIRECTIONS));
                    }
                }
            } else {
                world.setMapElement(position, null);
                position = Vector.add(position, Vector.direction(pos));
                world.setMapElement(position, this);

                if (move < 2) {
                    move++;
                    move(randomGenerator.nextInt(Vector.DIRECTIONS));
                }
            }
        } else {
            world.setMapElement(position,null);
            world.commentator.cantMove(this);
            remove();
        }
    }

    public void action() {
        if (toYoung) toYoung = false;
        else {
            move = 0;
            move(randomGenerator.nextInt(Vector.DIRECTIONS));
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
            if (strength > temp.getStrength()) {
                world.commentator.killing(this,temp);
                world.checkHuman(this);
                return DEATH;
            }
            else if (randomGenerator.nextInt(PERCENTAGE) > ESCAPE) {
                world.commentator.killing(temp,this);
                world.setMapElement(position, null);
                remove();
                return VICTORY;
            } else if (retreat(randomGenerator.nextInt(Vector.DIRECTIONS))) {
                world.commentator.escape(this,temp);
                return BIRTH;
            } else {
                world.commentator.killing(temp,this);
                world.setMapElement(position, null);
                remove();
                return VICTORY;
            }
        }
    }

    public boolean retreat(int pos) {
        if (pos < Vector.DIRECTIONS * 2) {
            if (!world.checkPosition(Vector.add(position, Vector.direction(pos)))) {
                retreat(pos + 1);
            } else if (!world.freePosition(Vector.add(position, Vector.direction(pos)))) {
                retreat(pos + 1);
            } else {
                world.setMapElement(position, null);
                position = Vector.add(position, Vector.direction(pos));
                world.setMapElement(position, this);
                return true;
            }
        }
        return false;
    }
}