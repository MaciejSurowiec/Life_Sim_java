package po.project.zwierzeta;

import po.project.Vector;
import po.project.Zwierze;
import po.project.Swiat;
import po.project.Organizm;
import java.awt.Color;
public class Zolw extends Zwierze{

    public static final String name="Zolw";
    private final int SZANSA_RUCHU = 75;

    public Zolw(Vector pos,Swiat sw)
    {
        sila = 2;
        inicjatywa = 1;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        avatar= new Color(51,102,0);

        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public String dajGlos(){return name;};

    public void makeChild(Vector pos)
    {
        Zolw org = new Zolw(pos,swiat);
    }

    public void akcja()
    {
        if (toyoung) toyoung = false;
        else
        {

            if (r_gen.nextInt(PROCENTY) > SZANSA_RUCHU)
            {
                move(r_gen.nextInt(Vector.DIRECTIONS));
            }
        }
    }


    public int kolizja(Organizm temp)
    {
        if (temp.checkGatunek(avatar))
        {
            if (!toyoung && !temp.mature())
            {
                swiat.komentator.narodziny(this);
                rozmnazanie();
            }

            return NARODZINY;
        }
        else
        {
           swiat.komentator.atak(temp,this);
            if (sila < temp.getSila())
            {
                if (temp.getSila() >= 5)
                {
                    swiat.komentator.zabojstwo(temp,this);
                    swiat.setMapElement(pozycja, null);
                    remove();
                    return ZWYCIESTWO;//aktualny organizm przegrywa
                }
                else
                {
                    swiat.komentator.odbicieAtaku(this,temp);
                    return NARODZINY;//organizm atakujacy nie przesuwa sie
                }
            }
            else
            {
                swiat.komentator.zabojstwo(this,temp);
                swiat.checkCzlowiek(temp);
                return SMIERC;//aktualny organizm wygrywa
            }
        }
    }
}


