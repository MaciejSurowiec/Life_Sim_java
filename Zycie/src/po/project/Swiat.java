package po.project;
import po.project.Organizm;
import po.project.Vector;
import po.project.Komentator;

import java.lang.reflect.Array;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import po.project.rosliny.*;
import po.project.zwierzeta.*;

import po.project.rosliny.BarszczSosnowskiego;

import java.io.PrintStream;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class Swiat  implements ActionListener {

    protected JTextField tf1,tf2;
    protected JTextArea tf3;
    protected JButton b1,b3;
    protected JFrame f;

    private final String newline="\n";
    private final int MAX_INIC = 8;
    protected Organizm[][] map;
    private ArrayList<Organizm>[]  organ;
    private Vector rozmiar;
    public Komentator komentator=new Komentator();
    private Czlowiek czlowiek;
    private boolean life;
    private int ilosc;
    protected Random r_gen = new Random();
    private Swiat.Listener listener;
    protected boolean ruszony;
    protected final int RECT_WIDTH = 20;
    protected final int RECT_HEIGHT = RECT_WIDTH;
    protected final int RECT_X = RECT_WIDTH/2;
    protected final int RECT_Y = RECT_X;
    protected int ILOSC = 10;
    protected Swiat.Okno okno;
    protected int tury;



    public Swiat()
    {
        pobierzRozmiar();
        organ = new ArrayList[MAX_INIC];

        for (int i = 0; i < MAX_INIC; i++) {
            organ[i] = new ArrayList<Organizm>();
        }
    }

    private void reszta()
    {
        map = new Organizm[rozmiar.x][rozmiar.y];
        ilosc = (rozmiar.x + rozmiar.y) / (10);
        life=true;
        for(int x=0;x<rozmiar.x;x++)
        {
            for(int y=0;y<rozmiar.y;y++)
            {
                map[x][y]=null;
            }
        }

        tury=0;
        listener = new Swiat.Listener();
        okno = new Swiat.Okno();
        komentator.setVisible(true);
        createWorld();
        okno.rysuj();
        ruszony=false;
    }


    public Vector findBarszcz(Vector org)
    {
        double dlugosc =  (rozmiar.x*rozmiar.x+rozmiar.y*rozmiar.y);
        Vector szukany = Vector.Zero;
        for(int i=0;i<organ[0].size();i++)
        {
            Organizm sprawdzany = organ[0].get(i);

            if (sprawdzany instanceof BarszczSosnowskiego)
            {
                double dlu=Vector.Dlugosc(Vector.Sub(sprawdzany.getPozycja(),org));
                if(dlu<dlugosc)
                {
                    dlugosc=dlu;
                    szukany=sprawdzany.getPozycja();
                }
            }
        }

        return szukany;
    }


    private void wykonajTure()
    {
        if(life) {
            if (czlowiek.checkPos(listener.ruch)) {
                czlowiek.getMove(listener.ruch);
                komentator.czytajTure(tury);
                tury++;
                for (int i = MAX_INIC - 1; i >= 0; i--) {
                    if (organ[i].size() > 0) {
                        for (int j = 0; j < organ[i].size(); j++) {
                            Organizm t = organ[i].get(j);
                            t.akcja();
                        }
                    }
                }
                if(!czlowiek.getLife())life=false;
                okno.rysujPonownie();
            }
            ruszony = false;
        }
        else
        {
            okno.schowaj();

            pobierzRozmiar();
        }
    }


    private Vector randomPosition()
    {
        int x = r_gen.nextInt(rozmiar.x);
        int y = r_gen.nextInt(rozmiar.y);
        Vector vec=new Vector(x, y);
        if (freePosition(vec)) return vec;
        else return randomPosition();
    }

    private void createWorld()
    {
        czlowiek = new Czlowiek(randomPosition(),this);

        life = true;

        for (int i = 0; i < ilosc; i++)
        {
            Wilk w = new Wilk(randomPosition(), this);
            Zolw z = new Zolw(randomPosition(), this);
            Owca o = new Owca(randomPosition(), this);
            Antylopa a = new Antylopa(randomPosition(), this);
            Lis l = new Lis(randomPosition(), this);
            Trawa t = new Trawa(randomPosition(), this);
            Mlecz m = new Mlecz(randomPosition(), this);
            Guarana g = new Guarana(randomPosition(), this);
            WilczeJagody wj = new WilczeJagody(randomPosition(), this);
            BarszczSosnowskiego bs = new BarszczSosnowskiego(randomPosition(), this);
            CyberOwca co = new CyberOwca(randomPosition(),this);
        }
    }

    public void save()
    {
        try
        {
            File save = new File("save.txt");
            if (!save.createNewFile())//jak instnieje to usuwam stary zapis
            {
                save.delete();
            }

            //moge komentatora ewentualnie dodac
            FileWriter myWriter = new FileWriter("save.txt");
            try
            {
                String roz=rozmiar.toString();

                myWriter.write(roz+newline);

                for(int i=MAX_INIC-1;i>=0;i--)
                {
                    for(int j=0;j<organ[i].size();j++)
                    {
                        myWriter.write(organ[i].get(j).toString()+newline);
                    }
                }
                myWriter.close();
            }
            catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void load()
    {
        try
        {
            File save = new File("save.txt");

            if (save.exists())
            {
                Scanner myReader = new Scanner(save);
                int i=0;
                while (myReader.hasNextLine()) {

                    String data = myReader.nextLine();
                    if (i == 0) {
                        System.out.print(data);
                        rozmiar = Vector.fromString(data);


                        i++;
                        map = new Organizm[rozmiar.x][rozmiar.y];
                        for(int x=0;x<rozmiar.x;x++)
                        {
                            for(int y=0;y<rozmiar.y;y++)
                            {
                                map[x][y]=null;
                            }
                        }
                        ilosc = (rozmiar.x + rozmiar.y) / (10);
                        life = true;
                    }
                    else
                    {
                        Organizm org=Organizm.fromString(data, this);
                        if(org instanceof Czlowiek)czlowiek = (Czlowiek)org;
                    }

                }
                myReader.close();
                f.dispose();
                tury=0;
                listener = new Swiat.Listener();
                okno = new Swiat.Okno();
                komentator.setVisible(true);
                okno.rysuj();
                ruszony=false;

            }
            else
            {
                tf3.setText("nie ma zapisu");
            }
        }
        catch (IOException ee)
        {
            System.out.println("An error occurred.");
            ee.printStackTrace();
        }

    }


    public boolean freePosition(Vector pos)
    {
        if (map[pos.x][pos.y] == null) return true;
        else return false;
    }

    public boolean checkPosition(Vector pos)
    {
        if(pos.x < 0 || pos.y < 0 || pos.x>=rozmiar.x || pos.y>=rozmiar.y ) return false;
        else return true;
    }

    public void checkCzlowiek(Organizm temp) { if (temp == czlowiek)life = false; }

    public void setMapElement(Vector pos,Organizm org)
    {
        if(checkPosition(pos)) map[pos.x][pos.y] = org;
    }

    public Organizm getMapElement(Vector pos) { return map[pos.x][pos.y]; }

    public void pushToWorld(Organizm org) { organ[org.getInic()].add(org); }

    public void deleteFromList(Organizm org) { organ[org.getInic()].remove(org); }


    private void pobierzRozmiar()
    {
        JLabel l1,l2;
        f = new JFrame();
        tf1=new JTextField();
        tf1.setBounds(200,50,100,20);
        tf2=new JTextField();
        tf2.setBounds(200,100,100,20);
        tf3=new JTextArea();
        tf3.setLineWrap(true);
        tf3.setBounds(125,150,250,80);
        tf3.setEditable(false);
        b1=new JButton("Stworz swiat");
        b3=new JButton("Wczytaj");
        l1=new JLabel("x: ");
        l1.setBounds(150,50,50,20);
        l2= new JLabel("y: ");
        l2.setBounds(150,100,50,20);
        b1.setBounds(175,240,150,50);
        b3.setBounds(175,350,150,50);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b1.addActionListener(this);
        b3.addActionListener(this);
        f.add(tf1);f.add(tf2);f.add(tf3);f.add(b1);f.add(l1);f.add(l2);f.add(b3);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        Scanner scanner= new Scanner(System.in);

        if(e.getSource()==b1)
        {
            String s1=tf1.getText();
            String s2=tf2.getText();
            if(!s1.isEmpty() && !s2.isEmpty())
            {
                int a=Integer.parseInt(s1);
                int b=Integer.parseInt(s2);

                if (a < 10 || b < 10)
                {
                    tf3.setText("Wielkosc musi byc wieksza od 9 w kazdym            wymiarze");
                }
                else
                {
                    tf3.setText("swiat o wymiarze (" + a + "," + b + " )  .");
                    rozmiar = new Vector(a,b);
                    f.dispose();
                    komentator.setVisible(false);
                    komentator.clear();
                    reszta();
                }
            }
            else
            {
                tf3.setText("Trzeba podac wymiar swiata o wymiarze            wiekszym ( 9 , 9 )");
            }
        }
        else
        {
            if(e.getSource()==b3)
            {
                 load();

            }
            else {
                    if(okno!=null)
                    {
                        if (e.getSource() == okno.but) komentator.setVisible(true);
                        else
                        {
                            if (e.getSource() == okno.butt) save();
                        }
                    }
            }
        }
    }

    protected class Listener extends JFrame  implements KeyListener
    {
        public int ruch;
        public Listener() {; }//addKeyListener(this); }

        public int getRuch(){return ruch;}

        @Override
        public void keyPressed(KeyEvent evt) {
            int c = evt.getKeyCode ();
            switch(c)
            {
                case KeyEvent.VK_UP: ruch=0;
                    ruszony=true;
                    break;
                case KeyEvent.VK_DOWN : ruch=4;
                    ruszony=true;
                    break;
                case KeyEvent.VK_LEFT : ruch=2;
                    ruszony=true;
                    break;
                case KeyEvent.VK_RIGHT : ruch=6;
                    ruszony=true;
                    break;
                case KeyEvent.VK_SPACE :
                    czlowiek.activatePower();
                    break;
            }

            if(ruszony)wykonajTure();
        }

        @Override
        public void keyReleased(KeyEvent evt) {}

        @Override
        public void keyTyped(KeyEvent evt) {}
    }


    public class Okno extends JPanel
    {
        protected Okno mainPanel;
        protected JFrame frame;
        protected JButton but;
        protected JButton butt;

        public void rysuj() {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() { createAndShowGui(); }});
        }

        public void schowaj()
        {
            frame.dispose();
        }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for(int i=0;i<rozmiar.y;i++)
            {
                for(int j=0;j<rozmiar.x;j++)
                {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.BLACK);
                    g2.drawRect(RECT_X + RECT_WIDTH * j, 50+RECT_Y + RECT_HEIGHT * i, RECT_WIDTH, RECT_HEIGHT);

                    if(map[j][i]!=null)
                    {
                        g2.setColor(map[j][i].avatar);
                    }
                    else
                    {
                        g2.setColor(Color.BLACK);
                    }
                    g2.fillRect(RECT_X+RECT_WIDTH*j,50+RECT_Y+RECT_HEIGHT*i,RECT_WIDTH,RECT_HEIGHT);
                }
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(RECT_WIDTH*(rozmiar.x+1), RECT_WIDTH*(rozmiar.y+1)+50);
        }

        public void rysujPonownie()
        {
            mainPanel.repaint();
        }

        private  void createAndShowGui()
        {
            int WIDTH=100;
            int HEIGHT=30;
            mainPanel = new Okno();
            frame = new JFrame("Zycie");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.getPreferredSize();
            frame.setVisible(true);
            but = new JButton("Komentator");
            butt = new JButton("zapisz");
            but.addActionListener(Swiat.this);
            butt.addActionListener(Swiat.this);
            but.setBounds(0,0,WIDTH,HEIGHT*2);
            butt.setBounds(100,0,WIDTH,HEIGHT*2);
            mainPanel.add(but);
            mainPanel.add(butt);
            frame.addKeyListener(listener);
            f.setLayout(null);
            but.setFocusable(false);
            butt.setFocusable(false);
            frame.setResizable(false);
        }
    }
}
