package po.project.zwierzeta;

import po.project.Vector;
import po.project.Zwierze;
import po.project.Swiat;
import po.project.Organizm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;


public class Czlowiek  extends Zwierze
{
    private int  moc;
    private int ruch;
    public boolean life;
    private final int POWER_LENGTH=5;
    public static final String name="Czlowiek";


    public Czlowiek(Vector pos,Swiat sw)
    {
        sila = 5;
        inicjatywa = 4;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        moc=0;
        ruch=0;
        avatar=new Color(6, 36, 127);
        life=true;
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public String toString()
    {
        StringBuilder s =  new StringBuilder();
        s.append(dajGlos());
        s.append(" "+String.valueOf(sila));
        s.append(" "+pozycja.toString());
        s.append(" "+String.valueOf(moc));

        return s.toString();
    }

    public void setPower(int i)
    {
        moc=i;
    }



    public void makeChild(Vector pos)//nie bedzie uzywane ale niech juz jest moze kiedys bedzie multi czy cos
    {
        Czlowiek org = new Czlowiek(pos,swiat);
    }

    public String dajGlos(){return name;};

     public void akcja()//moc rosnie w kazdej turze a gdy osiagnie 5 przechodzi na -5 i wtedy znowu rosnie az bedzie 0 i czeka na przycisk
     {
             move(ruch);
             if (moc > 0) {
                 //komentaotr do mocy
                 zabijWokolo();
             }

             if (moc != 0) moc++;

             if (moc == POWER_LENGTH + 1) moc = -1 * POWER_LENGTH;

    }

    private void zabijWokolo()
    {
        for (int i = 0; i < Vector.DIRECTIONS; i++)
        {
            if (swiat.checkPosition(Vector.Add(pozycja , Vector.Direction(i))))
            {
                if (!swiat.freePosition(Vector.Add(pozycja , Vector.Direction(i))))
                {
                    Organizm temp = swiat.getMapElement(Vector.Add(pozycja , Vector.Direction(i)));

                    swiat.setMapElement(Vector.Add(pozycja , Vector.Direction(i)), null);

                    swiat.komentator.zabojstwoNaOdleglosc(this,temp);
                    temp.remove();
                }
            }
        }
    }

    public void activatePower()
    {
        if(checkPower())
        {
            moc = 1;
            swiat.komentator.aktywacjaMocy(POWER_LENGTH - (moc - 1)) ;
        }
        else
        {
            if (moc < 0) swiat.komentator.zuzytaMoc( POWER_LENGTH-(moc-1) );
            else swiat.komentator.mocAktywowana(moc * -1);
        }

    }

    public boolean checkPos(int pos)
    {
        if(swiat.checkPosition(Vector.Add(pozycja,Vector.Direction(pos))))  return true;
        else return false;
    }

    private boolean  checkPower()
    {
        if (moc == 0) return true;
        else return false;
    }

    public boolean getLife() { return life; }

    public void getMove(int pos) { this.ruch=pos; }

}
