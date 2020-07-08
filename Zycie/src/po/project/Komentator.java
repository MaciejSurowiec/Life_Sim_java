package po.project;

import po.project.Organizm;

import java.awt.*;
import java.io.PrintStream;

import po.project.rosliny.BarszczSosnowskiego;
import po.project.rosliny.WilczeJagody;
import po.project.zwierzeta.CyberOwca;
import javax.swing.*;
import javax.swing.text.*;

public class Komentator {
    private final String newline="\n";
    protected Komentator.Kom kom;
    protected int screenX = 600;
    protected int screenY = 400;


    public Komentator()
    {
        kom = new Komentator.Kom();
    }


    private void mow(String s)
    {
        kom.addText(s+newline);
        kom.area.setCaretPosition(kom.area.getDocument().getLength());
    }

    public void setVisible(boolean b)
    {
        kom.setVisible(b);
    }

    public void clear()
    {
        kom.removeAll();
        kom=new Kom();
    }




public void atak(Organizm atak,Organizm obrona)
{
    StringBuilder buff =  new StringBuilder();
    buff.append(atak.dajGlos());

    if(obrona.getInic()==0)buff.append(" wchodzi na ");
    else buff.append(" atakuje ");

    buff.append(obrona.dajGlos());

    if(obrona instanceof WilczeJagody || (obrona instanceof BarszczSosnowskiego && !(atak instanceof CyberOwca))) buff.append(" i umiera z zatrucia");

    mow(buff.toString());
}

public void zabojstwo(Organizm zwyciezca, Organizm other)
{
    StringBuilder buff =  new StringBuilder();
    buff.append(zwyciezca.dajGlos());

    if(other.getInic()==0)buff.append(" niszczy ");
    else buff.append(" zabija ");

    buff.append(other.dajGlos());

    mow(buff.toString());
}



public void narodziny(Organizm nowy)
{
    if(nowy.getInic()!=0) mow(nowy.dajGlos()+" narodziny");
    else mow("nowa sadzonka "+nowy.dajGlos());
}

public void ucieczka(Organizm org,Organizm other)
{
    mow(org.dajGlos()+" ucieka "+other.dajGlos());
}

public void odbicieAtaku(Organizm org,Organizm other)
{
    mow(org.dajGlos()+" odbija atak "+other.dajGlos());
}

public void zabojstwoNaOdleglosc(Organizm org,Organizm other)
{
    mow(org.dajGlos()+" zabija na odleglosc "+other.dajGlos());
}

public void aktywacjaMocy(int i)
{
    mow("Moc aktywowana bedzie trwac: "+i);
}

public void mocAktywowana(int i)
{
    mow("Mocy bedzie trwac jeszcze "+i+"tur");
}

public void zuzytaMoc(int i)
{
    mow("Mocy bedzie mozna uzyc za " + i+ "tur");
}

public void brakwyboru(Organizm org)
{
    mow(org.dajGlos()+"umiera z glodu ");
}


public void czytajTure(int i)
{kom.addGText(newline+"Tura "+i+":"+newline);}


    protected class Kom extends JFrame
    {
        protected JTextArea area;
        protected Font font;


        public Kom()
        {
            area = new JTextArea();;

            JScrollPane sp = new JScrollPane();
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            area.setBounds(0,0,screenX,screenY);
            sp.setBounds(0,0,screenX,screenY);
            sp.getViewport().add(area);
            add(sp);
            area.setEditable(false);
            area.setSize(screenX,screenY);
            font = new Font("Dialog",Font.PLAIN,15);
            area.setFont(font);
            area.setForeground(Color.WHITE);
            area.setBackground(Color.BLACK);
            area.setAutoscrolls(true);
            setSize(screenX,screenY);
            setResizable(false);
        }

        public void addText(String s)
        {
            area.setForeground(Color.WHITE);
            area.append(s);
        }

        public void addGText(String s)
        {
            area.setForeground(Color.RED);
            area.append(s);

        }
    }



}
