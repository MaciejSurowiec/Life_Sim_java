package com.maciejsurowiec.lifesim.animals;
import com.maciejsurowiec.lifesim.Animal;
import com.maciejsurowiec.lifesim.Organism;
import com.maciejsurowiec.lifesim.Vector;
import com.maciejsurowiec.lifesim.World;

import java.awt.Color;
public class CyberSheep extends Animal {

    public static final String NAME = "a Cyber Sheep";
    public static final int ID = 2;

    public CyberSheep(Vector pos, World world) {
        strength = 11;
        initiative = 4;
        toYoung = true;
        this.world = world;
        position = pos;
        avatar = new Color(192,192,192);
        this.world.setMapElement(position, this);
        this.world.pushToWorld(this);
    }

    public void action() {
        if(toYoung) toYoung = false;
        else if(!findPineBorscht()) {
            move(randomGenerator.nextInt(Vector.DIRECTIONS));
        }
    }

    public String speak() { return NAME; }

    public int getId() { return ID; }

    private boolean findPineBorscht() {
       Vector route = world.vectorToPineBorscht(position);

       if(Vector.compare(route,Vector.ZERO)) {
           return false;
       } else {
           Vector path = Vector.sub(route, position);
           Vector pos = path.goToTarget();
           if(pos.x == 0 && pos.y == 0) return false;
           else return moveTo(pos);
       }
    }

    protected boolean moveTo(Vector pos) {
        if(world.checkPosition(Vector.add(position, pos))) {
            if(world.freePosition(Vector.add(position, pos))) {
                world.setMapElement(Vector.add(position, pos),this);
                world.setMapElement(position,null);
                position = Vector.add(position, pos);
            } else {
                Organism opponent = world.getMapElement(Vector.add(position,pos));
                int temp = opponent.collision(this);
                world.setMapElement(position, null);

                if(temp > BIRTH) {
                    if (temp == DEATH) {
                        remove();
                    } else if (temp == VICTORY) {
                        position = Vector.add(position, pos);
                        world.setMapElement(position, this);
                    }
                } else world.setMapElement(position, this);
            }
            return true;
        }
        else return false;
    }

    public void makeChild(Vector pos) {
        CyberSheep org = new CyberSheep(pos, world);
    }
}