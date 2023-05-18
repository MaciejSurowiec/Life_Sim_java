package com.maciejsurowiec.lifesim;

import com.maciejsurowiec.lifesim.animals.*;
import com.maciejsurowiec.lifesim.plants.*;

import javax.swing.*;
import java.awt.*;

public class Legend {

    protected LegendFrame legendWindow;
    protected int screenWidth = 120;
    protected int screenHeight = 400;

    public Legend() {
        legendWindow = new LegendFrame();
    }

    protected Color getAvatar(int i) {
        return switch(i) {
            case 0 -> Antelope.AVATAR;
            case 1 -> CyberSheep.AVATAR;
            case 2 -> Fox.AVATAR;
            case 3 -> Human.AVATAR;
            case 4 -> Sheep.AVATAR;
            case 5 -> Turtle.AVATAR;
            case 6 -> Wolf.AVATAR;
            case 7 -> Dandelion.AVATAR;
            case 8 -> Grass.AVATAR;
            case 9 -> Guarana.AVATAR;
            case 10 -> NightShade.AVATAR;
            default -> PineBorscht.AVATAR;
        };
    }

    protected static String getName(int i) {
        return switch(i) {
            case 0 -> Antelope.NAME;
            case 1 -> CyberSheep.NAME;
            case 2 -> Fox.NAME;
            case 3 -> Human.NAME;
            case 4 -> Sheep.NAME;
            case 5 -> Turtle.NAME;
            case 6 -> Wolf.NAME;
            case 7 -> Dandelion.NAME;
            case 8 -> Grass.NAME;
            case 9 -> Guarana.NAME;
            case 10 -> NightShade.NAME;
            default -> PineBorscht.NAME;
        };
    }

    public void setVisible(boolean b) {
        legendWindow.setVisible(b);
    }


    protected class LegendFrame extends JFrame {
        public LegendFrame() {
            setSize(screenWidth, screenHeight);
            setResizable(false);
            JSplitPane splitPane = new JSplitPane();
            JPanel one = new LegendPanel();
            JPanel two = new JPanel(new GridLayout(12,0));
            splitPane.setDividerSize(0);
            splitPane.setDividerLocation(World.RECT_SIZE * 2);
            splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

            for (int i = 0; i < 12; i++) {
                JTextField textField = new JTextField();
                textField.setBounds(World.RECT_SIZE + 20,
                        (World.RECT_SIZE + 10) * i + 10, 50, 20);
                textField.setEditable(false);
                textField.setText(Legend.getName(i));
                textField.setFocusable(false);
                textField.setBorder(BorderFactory.createEmptyBorder());
                two.add(textField);
            }

            splitPane.setLeftComponent(one);
            splitPane.setRightComponent(two);
            add(splitPane);
        }


        public class LegendPanel extends JPanel {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < 12; i++) {
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setColor(getAvatar(i));
                    g2.fillRect(World.RECT_SIZE / 2,
                            (World.RECT_SIZE + 10) * i + 5,
                            World.RECT_SIZE,
                            World.RECT_SIZE);
                }
            }
        }
    }
}
