package po.project.zwierzeta;

import po.project.Vector;
import po.project.Zwierze;
import po.project.Swiat;
import po.project.Organizm;

import java.awt.Color;

public class Antylopa extends Zwierze {

    protected int ruch;
    protected int UCIECZKA = 50;
    public  static final String name="Antylopa";

    public Antylopa(Vector pos, Swiat sw)
    {
        sila = 4;
        inicjatywa = 4;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        avatar = new Color(255,255,153);
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public String dajGlos(){return name;};

    public void makeChild(Vector pos)
    {
        Antylopa org = new Antylopa(pos, swiat);
    }

    protected void move(int pos)
    {
        if (pos < Vector.DIRECTIONS * 2)
        {
            if (!swiat.checkPosition(Vector.Add(pozycja, Vector.Direction(pos)))) move(pos + 1);
            else
            {
                if (!swiat.freePosition(Vector.Add(pozycja, Vector.Direction(pos))))
                {
                    Organizm oponent = swiat.getMapElement(Vector.Add(pozycja, Vector.Direction(pos)));
                    swiat.setMapElement(pozycja, null);
                    int temp = oponent.kolizja(this);//robie kolizje w obiekcie zderzonym zeby sprawdzic czy to nie zolf czy cos
                    if (temp > 0)
                    {
                        if (temp == SMIERC) remove();
                        else
                        {
                            if (temp == ZWYCIESTWO)
                            {
                                pozycja = Vector.Add(pozycja, Vector.Direction(pos));
                                swiat.setMapElement(pozycja, this);
                                if (ruch < 2)
                                {
                                    ruch++;
                                    move(r_gen.nextInt(Vector.DIRECTIONS));
                                }
                            }
                        }
                    }
                    else
                    {
                        swiat.setMapElement(pozycja, this);
                        if (ruch < 2)
                        {
                            ruch++;
                            move(r_gen.nextInt(Vector.DIRECTIONS));
                        }
                    }
                }
                else
                {
                    swiat.setMapElement(pozycja, null);
                    pozycja = Vector.Add(pozycja, Vector.Direction(pos));
                    swiat.setMapElement(pozycja, this);

                    if (ruch < 2)
                    {
                        ruch++;
                        move(r_gen.nextInt(Vector.DIRECTIONS));
                    }
                }
            }
        }
        else
        {
            swiat.setMapElement(pozycja,null);
            swiat.komentator.brakwyboru(this);
            remove();// skazany na smierc przez niezdecydownie
        }
    }


    public void akcja()
    {
        if (toyoung) toyoung = false;
        else
        {
            ruch = 0;
            move(r_gen.nextInt(Vector.DIRECTIONS));
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
            if (sila > temp.getSila())
            {
                swiat.komentator.zabojstwo(this,temp);
                swiat.checkCzlowiek(this);
                return SMIERC;
            }
            else
            {
                if (r_gen.nextInt(PROCENTY) > UCIECZKA)
                {
                    swiat.komentator.zabojstwo(temp,this);
                    swiat.setMapElement(pozycja, null);
                    remove();
                    return ZWYCIESTWO;
                }
                else
                {
                    if (retreat(r_gen.nextInt(Vector.DIRECTIONS)))
                    {
                        swiat.komentator.ucieczka(this,temp);
                        return NARODZINY;
                    }
                    else
                    {
                        swiat.komentator.zabojstwo(temp,this);
                        swiat.setMapElement(pozycja, null);
                        remove();
                        return ZWYCIESTWO;
                    }
                }
            }
        }
    }

    public boolean retreat(int pos)
    {
        if (pos < Vector.DIRECTIONS * 2)
        {
            if (!swiat.checkPosition(Vector.Add(pozycja, Vector.Direction(pos))))
            {
                retreat(pos + 1);
            }
            else
            {
                if (!swiat.freePosition(Vector.Add(pozycja, Vector.Direction(pos))))
                {
                    retreat(pos + 1);
                }
                else
                {
                    swiat.setMapElement(pozycja, null);
                    pozycja = Vector.Add(pozycja, Vector.Direction(pos));
                    swiat.setMapElement(pozycja, this);
                    return true;
                }
            }
        }
       return false;
    }
}





