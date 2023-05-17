package com.maciejsurowiec.lifesim;
import java.util.Random;
import java.awt.Color;

import com.maciejsurowiec.lifesim.animals.*;
import com.maciejsurowiec.lifesim.plants.*;

public abstract class Organism {

    protected final int BIRTH = 0;
    protected final int DEATH = 1;
    protected final int VICTORY = 2;

    protected static final String NAME = "org";
    protected int strength;
    protected Color avatar;
    protected int initiative;
    protected World world;
    protected boolean toYoung;
    protected Random randomGenerator = new Random();
    protected Vector position;

    protected int PERCENTAGE = 100;

    public Organism(){}

    public abstract void action();

    public abstract int collision(Organism org);

    public abstract String speak();

    public  int getStrength() { return strength; }

    public boolean isToYoung() { return toYoung; }

    public void addStrength(int str) { strength += str; }

    public Vector getPosition()
    {
        return position;
    }

    public boolean SameSpecies(Color temp) {
        return temp == avatar;
    }

    public int getInitiative() { return initiative; }

    public void remove() { world.deleteFromList(this); }

    public abstract void makeChild(Vector pos);

    public abstract int getId();

    public String toString() {
        return getId() +
                " " + strength +
                " " + position.toString();
    }

    public static Organism fromString(String s, World world) {
        char c;
        int i = 0;

        StringBuilder nameBuilder = new StringBuilder();
        c = s.charAt(i);
        while (c != ' ') {
            i++;
            nameBuilder.append(c);
            c = s.charAt(i);
        }

        System.out.print("\n" + c + "\n");
        i++;
        int tempStrength;
        StringBuilder strengthBuilder = new StringBuilder();
        c = s.charAt(i);

        while (c != ' ') {
            i++;
            strengthBuilder.append(c);
            c = s.charAt(i);
        }
        tempStrength = Integer.parseInt(strengthBuilder.toString());
        i++;

        StringBuilder poss = new StringBuilder();
        c = s.charAt(i);
        while (c != ' ' && i < s.length()) {
            i++;
            poss.append(c);
            if (s.length() > i) c = s.charAt(i);
        }

        Vector pos = Vector.fromString(poss.toString());

        Organism org;
        switch (Integer.parseInt(nameBuilder.toString())) {
            case Wolf.ID -> org = new Wolf(pos, world);
            case Turtle.ID -> org = new Turtle(pos, world);
            case Fox.ID -> org = new Fox(pos, world);
            case Human.ID -> {
                Human human = new Human(pos, world);
                int power;
                i++;
                StringBuilder powerBuilder = new StringBuilder();
                c = s.charAt(i);
                while (c != ' ' && i < s.length()) {
                    i++;
                    powerBuilder.append(c);
                    if (s.length() > i) c = s.charAt(i);
                }
                power = Integer.parseInt(String.valueOf(powerBuilder));
                human.setPower(power);
                org = human;
            }
            case CyberSheep.ID -> org = new CyberSheep(pos, world);
            case Antelope.ID -> org = new Antelope(pos, world);
            case NightShade.ID -> org = new NightShade(pos, world);
            case Grass.ID -> org = new Grass(pos, world);
            case Guarana.ID -> org = new Guarana(pos, world);
            case PineBorscht.ID -> org = new PineBorscht(pos, world);
            case Dandelion.ID -> org = new Dandelion(pos, world);
            default -> org = new Sheep(pos, world);
        }

        if (tempStrength - org.getStrength() > 0) {
            org.addStrength(tempStrength-org.getStrength());
        }

        return org;
    }
}
