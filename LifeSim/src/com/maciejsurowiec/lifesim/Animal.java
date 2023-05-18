package com.maciejsurowiec.lifesim;

public abstract class Animal extends Organism {

    protected void move(int i) {
        if (i < Vector.DIRECTIONS * 2) {
            if (world.checkPosition(Vector.add(position, Vector.direction(i)))) {
                if (world.freePosition(Vector.add(position, Vector.direction(i)))) {
                    world.setMapElement(Vector.add(position, Vector.direction(i)), this);
                    world.setMapElement(position, null);
                    position = Vector.add(position, Vector.direction(i));
                } else {
                    Organism opponent = world.getMapElement(Vector.add(position, Vector.direction(i)));
                    int collisionResult = opponent.collision(this);
                    world.setMapElement(position, null);

                    if (collisionResult > BIRTH) {
                        if (collisionResult == DEATH) {
                            remove();
                        } else {
                            if (collisionResult == VICTORY) {
                                position = Vector.add(position, Vector.direction(i));
                                world.setMapElement(position, this);
                            }
                        }
                    } else world.setMapElement(position, this);
                }
            } else {
                move(i + 1);
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
            move(randomGenerator.nextInt(Vector.DIRECTIONS));
        }
    }

    public void breeding() {
        for (int i = 0; i < Vector.DIRECTIONS; i++) {
            if (world.checkPosition(Vector.add(position, Vector.direction(i)))) {
                if (world.freePosition(Vector.add(position, Vector.direction(i)))) {
                    makeChild(Vector.add(position,Vector.direction(i)));
                    break;
                }
            }
        }
    }

    public abstract void makeChild(Vector pos);

    public int collision(Organism temp) {
        if (temp.SameSpecies(AVATAR)) {
            if (!toYoung && !temp.isToYoung()) {
                world.commentator.birth(this);
                breeding();
            }
            return BIRTH;
        } else {
            world.commentator.attack(temp,this);
            if (strength > temp.getStrength()) {
                world.commentator.killing(this, temp);
                world.checkHuman(temp);
                return DEATH;
            } else {
                world.commentator.killing(temp,this);
                world.checkHuman(this);
                world.setMapElement(position, null);
                remove();
                return VICTORY;
            }
        }
    }
}