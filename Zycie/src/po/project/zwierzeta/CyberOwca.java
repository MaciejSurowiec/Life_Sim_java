package po.project.zwierzeta;
import po.project.Organizm;
import po.project.Vector;
import po.project.Zwierze;
import po.project.Swiat;
import java.awt.Color;
public class CyberOwca extends Zwierze {

    public static final String name="Cyber_Owca";

    public CyberOwca(Vector pos, Swiat sw)
    {
        sila = 11;
        inicjatywa = 4;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        avatar=new Color(192,192,192);
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public void akcja()
    {
        if(toyoung)toyoung=false;
        else
        {
            if(!szukajBarszczu())
            {
                move(r_gen.nextInt(Vector.DIRECTIONS));
            }
        }
    }

    public String dajGlos(){return name;};

    private boolean szukajBarszczu()
    {
       Vector droga = swiat.findBarszcz(pozycja);//doddac vector zeby znajdowalo najblizszy

       if(Vector.Compare(droga,Vector.Zero))
       {
           return false;//nie znalazlo
       }
       else
       {
           Vector sciezka = Vector.Sub(droga,pozycja);
            //teraz trzeba przesunac o 1 ruch w strone vektora sciezka
           Vector pos=sciezka.doCelu();
           if(pos.x==0 && pos.y==0) return false;
           else return moveTo(pos);
       }
    }

    protected boolean moveTo(Vector pos)
    {
        if(swiat.checkPosition(Vector.Add(pozycja,pos)))
        {
            if(swiat.freePosition(Vector.Add(pozycja,pos)))
            {
                swiat.setMapElement(Vector.Add(pozycja,pos),this);
                swiat.setMapElement(pozycja,null);
                pozycja=Vector.Add(pozycja,pos);
            }
            else
            {
                Organizm oponent=swiat.getMapElement(Vector.Add(pozycja,pos));
                int temp=oponent.kolizja(this);
                swiat.setMapElement(pozycja, null);

                if(temp > NARODZINY)
                {
                    if (temp == SMIERC)
                    {
                        remove();
                    }
                    else
                    {
                        if (temp == ZWYCIESTWO)
                        {
                            pozycja = Vector.Add(pozycja, pos);
                            swiat.setMapElement(pozycja, this);
                        }
                    }
                }
                else swiat.setMapElement(pozycja, this);
            }

            return true;
        }
        else
        {
            return false;
        }
    }


    public void makeChild(Vector pos)
    {
        CyberOwca org = new CyberOwca(pos,swiat);
    }
}
