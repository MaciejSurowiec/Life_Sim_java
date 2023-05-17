package com.maciejsurowiec.lifesim;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import com.maciejsurowiec.lifesim.animals.*;
import com.maciejsurowiec.lifesim.plants.*;

import java.io.*;
import java.util.Scanner;

public class World implements ActionListener {

    private final String NEWLINE ="\n";
    private final int MAX_INITIATIVE = 8;
    protected final int RECT_WIDTH = 20;
    protected final int RECT_HEIGHT = RECT_WIDTH;
    protected final int RECT_X = RECT_WIDTH / 2;
    protected final int RECT_Y = RECT_X;

    protected JTextField textField, textField1;
    protected JTextArea textArea;
    protected JButton button, button1;
    protected JFrame frame;

    protected Organism[][] map;
    private ArrayList<Organism>[] organisms;
    private Vector size;
    public Commentator commentator = new Commentator();
    private Human human;
    private boolean isAlive;
    private int spawningRate;
    protected Random randomGenerator = new Random();
    private World.Listener listener;
    protected boolean hasMoved;

    protected Window window;
    protected int turns;

    public World() {
        initializeFrame();
        organisms = new ArrayList[MAX_INITIATIVE];

        for (int i = 0; i < MAX_INITIATIVE; i++) {
            organisms[i] = new ArrayList<>();
        }
    }

    private void initialization() {
        map = new Organism[size.x][size.y];
        spawningRate = (size.x + size.y) / 10;
        isAlive = true;
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                map[x][y] = null;
            }
        }

        turns = 0;
        listener = new World.Listener();
        window = new Window();
        commentator.setVisible(true);
        createWorld();
        window.draw();
        hasMoved = false;
    }

    public Vector vectorToPineBorscht(Vector org) {
        double length = size.x * size.x + size.y * size.y;
        Vector closestPath = Vector.ZERO;
        for (int i = 0; i < organisms[0].size(); i++) {
            Organism tempOrganism = organisms[0].get(i);

            if (tempOrganism instanceof PineBorscht) {
                double tempLength = Vector.length(Vector.sub(tempOrganism.getPosition(),org));
                if (tempLength < length) {
                    length = tempLength;
                    closestPath = tempOrganism.getPosition();
                }
            }
        }

        return closestPath;
    }

    private void nextTurn() {
        if(isAlive) {
            if (human.checkPosition(listener.direction)) {
                human.getMove(listener.direction);
                commentator.readTurn(turns);
                turns++;
                for (int i = MAX_INITIATIVE - 1; i >= 0; i--) {
                    if (organisms[i].size() > 0) {
                        for (int j = 0; j < organisms[i].size(); j++) {
                            Organism t = organisms[i].get(j);
                            t.action();
                        }
                    }
                }
                if(!human.getLife()) isAlive = false;
                window.redraw();
            }
            hasMoved = false;
        } else {
            window.hideWindow();
            initializeFrame();
        }
    }

    private Vector randomPosition() {
        int x = randomGenerator.nextInt(size.x);
        int y = randomGenerator.nextInt(size.y);
        Vector vec = new Vector(x, y);
        if (freePosition(vec)) return vec;
        else return randomPosition();
    }

    private void createWorld() {
        human = new Human(randomPosition(),this);
        isAlive = true;

        for (int i = 0; i < spawningRate; i++) {
            Wolf wolf = new Wolf(randomPosition(), this);
            Turtle turtle = new Turtle(randomPosition(), this);
            Sheep sheep = new Sheep(randomPosition(), this);
            Antelope antelope = new Antelope(randomPosition(), this);
            Fox fox = new Fox(randomPosition(), this);
            Grass grass = new Grass(randomPosition(), this);
            Dandelion dandelion = new Dandelion(randomPosition(), this);
            Guarana guarana = new Guarana(randomPosition(), this);
            NightShade nightShade = new NightShade(randomPosition(), this);
            PineBorscht pineBorscht = new PineBorscht(randomPosition(), this);
            CyberSheep cyberSheep = new CyberSheep(randomPosition(),this);
        }
    }

    public void save() {
        try {
            File save = new File("save.txt");

            FileWriter myWriter = new FileWriter("save.txt");
            try {
                String roz = size.toString();
                myWriter.write(roz + NEWLINE);

                for (int i = MAX_INITIATIVE - 1; i >= 0; i--) {
                    for (int j = 0; j < organisms[i].size(); j++) {
                        myWriter.write(organisms[i].get(j).toString() + NEWLINE);
                    }
                }
                myWriter.close();
                commentator.saved();
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            File save = new File("save.txt");

            if (save.exists()) {
                Scanner myReader = new Scanner(save);
                int i = 0;
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (i == 0) {
                        System.out.print(data);
                        size = Vector.fromString(data);

                        i++;
                        map = new Organism[size.x][size.y];
                        for (int x = 0; x < size.x; x++) {
                            for (int y = 0; y < size.y; y++) {
                                map[x][y] = null;
                            }
                        }
                        spawningRate = (size.x + size.y) / 10;
                        isAlive = true;
                    } else {
                        Organism org = Organism.fromString(data, this);
                        if (org instanceof Human) human = (Human)org;
                    }
                }
                myReader.close();
                frame.dispose();
                turns = 0;
                listener = new World.Listener();
                window = new Window();
                commentator.setVisible(true);
                window.draw();
                hasMoved = false;
            } else {
                textArea.setText("no save found");
            }
        }
        catch (IOException ee) {
            System.out.println("An error occurred.");
            ee.printStackTrace();
        }
    }

    public boolean freePosition(Vector pos) {
        return map[pos.x][pos.y] == null;
    }

    public boolean checkPosition(Vector pos) {
        return !(pos.x < 0 || pos.y < 0 || pos.x >= size.x || pos.y >= size.y);
    }

    public void checkHuman(Organism temp) { if (temp == human) isAlive = false; }

    public void setMapElement(Vector pos, Organism org) {
        if(checkPosition(pos)) map[pos.x][pos.y] = org;
    }

    public Organism getMapElement(Vector pos) { return map[pos.x][pos.y]; }

    public void pushToWorld(Organism org) {
        organisms[org.getInitiative()].add(org);
    }

    public void deleteFromList(Organism org) {
        organisms[org.getInitiative()].remove(org);
    }

    private void initializeFrame() {
        JLabel label,label1;
        frame = new JFrame();
        textField1 = new JTextField();
        textField1.setBounds(200,50,100,20);
        textField = new JTextField();
        textField.setBounds(200,100,100,20);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBounds(125,150,250,80);
        textArea.setEditable(false);
        button1 = new JButton("Create world");
        button = new JButton("Load");
        label = new JLabel("x: ");
        label.setBounds(150,50,50,20);
        label1 = new JLabel("y: ");
        label1.setBounds(150,100,50,20);
        button1.setBounds(175,240,150,50);
        button.setBounds(175,350,150,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button1.addActionListener(this);
        button.addActionListener(this);
        frame.add(textField1);
        frame.add(textField);
        frame.add(textArea);
        frame.add(button1);
        frame.add(label);
        frame.add(label1);
        frame.add(button);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            String s1 = textField1.getText();
            String s2 = textField.getText();
            if (!s1.isEmpty() && !s2.isEmpty()) {
                int a = Integer.parseInt(s1);
                int b = Integer.parseInt(s2);

                if (a < 10 || b < 10) {
                    textArea.setText("Each dimension have to be bigger then 9");
                } else {
                    textArea.setText("The world of size (" + a + "," + b + " )  .");
                    size = new Vector(a,b);
                    frame.dispose();
                    commentator.setVisible(false);
                    commentator.clear();
                    initialization();
                }
            } else {
                textArea.setText("You need to choose world size");
            }
        } else if (e.getSource() == button) {
            load();
        }
        else if(window != null) {
            if (e.getSource() == window.button2) commentator.setVisible(true);
            else if (e.getSource() == window.button3) save();
        }
    }


    protected class Listener extends JFrame implements KeyListener {
        private int direction;
        public Listener() {}

        public int getDirection() { return direction; }

        @Override
        public void keyPressed(KeyEvent evt) {
            int c = evt.getKeyCode ();
            switch (c) {
                case KeyEvent.VK_UP -> {
                    direction = 0;
                    hasMoved = true;
                }
                case KeyEvent.VK_DOWN -> {
                    direction = 4;
                    hasMoved = true;
                }
                case KeyEvent.VK_LEFT -> {
                    direction = 2;
                    hasMoved = true;
                }
                case KeyEvent.VK_RIGHT -> {
                    direction = 6;
                    hasMoved = true;
                }
                case KeyEvent.VK_SPACE -> human.activatePower();
            }

            if(hasMoved) nextTurn();
        }

        @Override
        public void keyReleased(KeyEvent evt) {}

        @Override
        public void keyTyped(KeyEvent evt) {}
    }


    public class Window extends JPanel {
        protected Window mainPanel;
        protected JFrame frame;
        protected JButton button2, button3;

        public void draw() {
            SwingUtilities.invokeLater(this::createAndShowGui);
        }

        public void hideWindow() { frame.dispose(); }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < size.y; i++) {
                for (int j = 0; j < size.x; j++) {
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setColor(Color.BLACK);
                    g2.drawRect(RECT_X + RECT_WIDTH * j, 50 + RECT_Y + RECT_HEIGHT * i, RECT_WIDTH, RECT_HEIGHT);

                    if (map[j][i] != null) {
                        g2.setColor(map[j][i].avatar);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                    g2.fillRect(RECT_X+RECT_WIDTH * j,50 + RECT_Y+RECT_HEIGHT * i, RECT_WIDTH, RECT_HEIGHT);
                }
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(RECT_WIDTH * (size.x + 1), RECT_WIDTH * (size.y + 1) + 50);
        }

        public void redraw() {
            mainPanel.repaint();
        }

        private  void createAndShowGui() {
            int width = 100;
            int height = 30;
            mainPanel = new Window();
            frame = new JFrame("LifeSim");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.getPreferredSize();
            frame.setVisible(true);
            button2 = new JButton("Commentator");
            button3 = new JButton("Save");
            button2.addActionListener(World.this);
            button3.addActionListener(World.this);
            button2.setBounds(0,0, width,height * 2);
            button3.setBounds(100,0, width,height * 2);
            mainPanel.add(button2);
            mainPanel.add(button3);
            frame.addKeyListener(listener);
            World.this.frame.setLayout(null);
            button2.setFocusable(false);
            button3.setFocusable(false);
            frame.setResizable(false);
        }
    }
}