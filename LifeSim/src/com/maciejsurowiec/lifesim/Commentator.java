package com.maciejsurowiec.lifesim;

import java.awt.*;

import com.maciejsurowiec.lifesim.animals.CyberSheep;
import com.maciejsurowiec.lifesim.plants.NightShade;
import com.maciejsurowiec.lifesim.plants.PineBorscht;

import javax.swing.*;

public class Commentator {
    private final String NEWLINE = "\n";
    protected CommentatorFrame commentatorFrame;
    protected int screenWidth = 600;
    protected int screenHeight = 400;

    public Commentator() {
        commentatorFrame = new CommentatorFrame();
    }

    private void speak(String s) {
        commentatorFrame.addText(s + NEWLINE);
        commentatorFrame.area.setCaretPosition(commentatorFrame.area.getDocument().getLength());
    }

    public void setVisible(boolean b) {
        commentatorFrame.setVisible(b);
    }

    public void clear() {
        commentatorFrame.removeAll();
        commentatorFrame = new CommentatorFrame();
    }

    public void attack(Organism attacking, Organism defending) {
        StringBuilder buff = new StringBuilder();
        buff.append(attacking.speak());

        if (defending.getInitiative() == 0) buff.append(" steps on ");
        else buff.append(" attacking ");

        buff.append(defending.speak());

        if (defending instanceof NightShade
        || (defending instanceof PineBorscht
        && !(attacking instanceof CyberSheep))) buff.append(" and dies from poisoning.");
        speak(buff.toString());
    }

    public void killing(Organism winner, Organism other) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(winner.speak());

        if (other.getInitiative() == 0) buffer.append(" destroys ");
        else buffer.append(" kills ");

        buffer.append(other.speak());
        speak(buffer.toString());
    }

    public void birth(Organism newborn) {
        if (newborn.getInitiative() != 0) speak(newborn.speak() + " birth");
        else speak("new seedling of " + newborn.speak());
    }

    public void escape(Organism org, Organism other) {
        speak(org.speak() + " escaping " + other.speak());
    }

    public void attackReflection(Organism org, Organism other) {
        speak(org.speak() + " reflect attack from " + other.speak());
    }

    public void killingFromDistance(Organism org, Organism other) {
        speak(org.speak() + " kills " + other.speak() + " from a distance");
    }

    public void specialAction(int i) {
        speak("Power activated, will last for: " + i + " turns");
    }

    public void powerAction(int i) {
        speak("Power will last for: " + i + "turns");
    }

    public void powerUsed(int i) {
        speak("Power can be used in: " + i + "turns");
    }

    public void cantMove(Organism org) {
        speak(org.speak() + "dies from hunger ");
    }

    public void readTurn(int i) {
        commentatorFrame.addRedText(NEWLINE + "Turn " + i + ":" + NEWLINE);
    }

    public void saved() {
        commentatorFrame.addRedText("Game saved" + NEWLINE);
    }

    
    protected class CommentatorFrame extends JFrame {
        protected JTextArea area;
        protected Font font;

        public CommentatorFrame() {
            area = new JTextArea();
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            area.setBounds(0,0, screenWidth, screenHeight);
            scrollPane.setBounds(0,0, screenWidth, screenHeight);
            scrollPane.getViewport().add(area);
            add(scrollPane);
            area.setEditable(false);
            area.setSize(screenWidth, screenHeight);
            font = new Font("Dialog", Font.PLAIN,15);
            area.setFont(font);
            area.setForeground(Color.WHITE);
            area.setBackground(Color.BLACK);
            area.setAutoscrolls(true);
            setSize(screenWidth, screenHeight);
            setResizable(false);
        }

        public void addText(String s) {
            area.setForeground(Color.WHITE);
            area.append(s);
        }

        public void addRedText(String s) {
            area.setForeground(Color.RED);
            area.append(s);
        }
    }
}
