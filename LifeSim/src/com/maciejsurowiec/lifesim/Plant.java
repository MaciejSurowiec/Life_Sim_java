package com.maciejsurowiec.lifesim;

public abstract class Plant extends Organism {

    protected final int POLLINATION = 10;

    public void action() {
        if (randomGenerator.nextInt(PERCENTAGE) < POLLINATION) {
            pollination(randomGenerator.nextInt(Vector.DIRECTIONS));
        }
    }

    public int collision(Organism org) {
        world.commentator.attack(org,this);
        world.commentator.killing(org,this);
        world.setMapElement(position, null);
        remove();
        return VICTORY;
    }

    protected void pollination(int pos) {
        if (pos < Vector.DIRECTIONS * 2) {
            if (world.checkPosition(Vector.add(position, Vector.direction(pos)))) {
                if (world.freePosition(Vector.add(position, Vector.direction(pos)))) {
                    world.commentator.birth(this);
                    makeChild(Vector.add(position, Vector.direction(pos)));
                } else pollination(pos + 1);
            } else pollination(pos + 1);
        }
    }
}