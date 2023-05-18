package com.maciejsurowiec.lifesim.plants;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Plant;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;
import com.maciejsurowiec.lifesim.animals.CyberSheep;
import java.awt.Color;

public class PineBorscht extends Plant {
    public static final String NAME = "a Pine Borscht";
    public static final Color AVATAR = new Color(16, 73, 40);
    public static final int ID = 12;

    public PineBorscht(Vector vec, World world) {
        this.world = world;
        position = vec;
        initiative = 0;
        strength = 10;

        toYoung = true;
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }


    public void action() {
        if (toYoung) toYoung = false;
        else if (randomGenerator.nextInt(PERCENTAGE) < POLLINATION) {
                pollination(randomGenerator.nextInt(Vector.DIRECTIONS));
        }

        killEverythingAround();
    }

    public String speak() { return NAME; }

    public Color getAvatar() { return AVATAR; }

    public int getId() { return ID; }

    private void killEverythingAround() {
        for (int i = 0; i < Vector.DIRECTIONS; i++) {
            if (world.checkPosition(Vector.add(position, Vector.direction(i)))) {
                if (!world.freePosition(Vector.add(position, Vector.direction(i)))) {
                    Organism temp = world.getMapElement(Vector.add(position,Vector.direction(i)));
                    if (!(temp instanceof CyberSheep)) {
                        if (temp.getInitiative() > 0) {
                            world.setMapElement(Vector.add(position, Vector.direction(i)), null);
                            world.checkHuman(temp);
                            world.commentator.killingFromDistance(this, temp);
                            temp.remove();
                        }
                    }
                }
            }
        }
    }

    public int collision(Organism temp) {
        if (temp instanceof CyberSheep) {
            world.commentator.attack(temp,this);
            world.commentator.killing(temp,this);
            world.setMapElement(position, null);

            remove();
            return VICTORY;
        } else {
            world.commentator.attack(temp, this);
            world.commentator.killing(temp, this);
            world.setMapElement(position, null);
            world.checkHuman(temp);
            remove();
            return DEATH;
        }
    }

    public void makeChild(Vector pos) {
        PineBorscht pineBorscht = new PineBorscht(pos, world);
    }
}