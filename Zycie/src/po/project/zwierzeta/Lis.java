package po.project.zwierzeta;

import po.project.Vector;
import po.project.Zwierze;
import po.project.Swiat;
import po.project.Organizm;

import java.awt.Color;

public class Lis extends Zwierze{
    public  static final String name="Lis";

    public Lis(Vector pos, Swiat sw)
    {
        sila = 3;
        inicjatywa = 7;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        avatar = new Color(255,128,0);
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public String dajGlos(){return name;};
    public void makeChild(Vector pos)
    {
            Lis org = new Lis(pos,swiat);
    }

    public void move(int pos)
    {
        if (pos < Vector.DIRECTIONS * 2)//zeby nie sprawdzal w kolko ale napewno sprawdzil wszystkie mozliwosci
        {
            if (!swiat.checkPosition(Vector.Add(pozycja , Vector.Direction(pos))))//jezeli cos stoi obok sciany to vector jest obracany
            {
                move(pos + 1);
            }
            else
            {
                if (!swiat.freePosition(Vector.Add(pozycja , Vector.Direction(pos))))
                {
                    Organizm oponent = swiat.getMapElement(Vector.Add(pozycja ,Vector.Direction(pos)));
                    if (sila < oponent.getSila())
                    {
                        swiat.komentator.ucieczka(this,oponent);
                        move(pos + 1);
                    }
                    else
                    {
                        swiat.setMapElement(pozycja, null);
                        int temp = oponent.kolizja(this);
                        if (temp > 0)
                        {
                            if (temp == SMIERC) remove();
						else
                            {
                                if (temp == ZWYCIESTWO)
                                {
                                    pozycja = Vector.Add(pozycja, Vector.Direction(pos));
                                    swiat.setMapElement(pozycja, this);
                                }
                            }
                        }
                        else swiat.setMapElement(pozycja, this);
                    }
                }
                else
                {
                    swiat.setMapElement(pozycja, null);
                    swiat.setMapElement(Vector.Add(pozycja , Vector.Direction(pos)), this);
                    pozycja = Vector.Add(pozycja, Vector.Direction(pos));
                }
            }
        }
        else
        {
            swiat.setMapElement(pozycja,null);
            swiat.komentator.brakwyboru(this);
            remove();
        }
    }


}
