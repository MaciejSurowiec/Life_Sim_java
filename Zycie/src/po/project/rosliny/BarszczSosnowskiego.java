package po.project.rosliny;
import po.project.Vector;
import po.project.Roslina;
import po.project.Swiat;
import po.project.Organizm;
import po.project.zwierzeta.CyberOwca;
import java.awt.Color;

public class BarszczSosnowskiego extends Roslina {

    public static final String name="Barszcz_Sosnowskiego";

    public BarszczSosnowskiego(Vector vec, Swiat sw)
    {
        swiat = sw;
        pozycja = vec;
        inicjatywa = 0;
        sila = 10;
        avatar = new Color(16, 73, 40);
        toyoung = true;
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public void akcja()
    {
        if (toyoung)toyoung = false;
        else
        {
            if (r_gen.nextInt(PROCENTY) < ZAPYLENIE)
            {
                zapylenie(r_gen.nextInt(Vector.DIRECTIONS));
            }
        }

        zabijWokolo();
    }

    public String dajGlos(){return name;};

    private void zabijWokolo()
    {
        for (int i = 0; i < Vector.DIRECTIONS; i++)
        {
            if (swiat.checkPosition(Vector.Add(pozycja,Vector.Direction(i))))
            {
                if (!swiat.freePosition(Vector.Add(pozycja , Vector.Direction(i))))
                {
                    Organizm temp = swiat.getMapElement(Vector.Add(pozycja,Vector.Direction(i)));
                    if(!(temp instanceof CyberOwca))
                    {
                        if (temp.getInic() > 0) {
                            swiat.setMapElement(Vector.Add(pozycja, Vector.Direction(i)), null);
                            swiat.checkCzlowiek(temp);
                            swiat.komentator.zabojstwoNaOdleglosc(this, temp);
                            temp.remove();
                        }
                    }
                }
            }
        }
    }

    public int kolizja(Organizm temp)
    {
        if(temp instanceof CyberOwca)
        {
            swiat.komentator.atak(temp,this);
            swiat.komentator.zabojstwo(temp,this);
            swiat.setMapElement(pozycja, null);

            remove();
            return ZWYCIESTWO;
        }
        else
        {
            swiat.komentator.atak(temp, this);
            swiat.komentator.zabojstwo(temp, this);
            swiat.setMapElement(pozycja, null);
            swiat.checkCzlowiek(temp);
            remove();
            return SMIERC;
        }
    }








    public void makeChild(Vector pos)
    {
        BarszczSosnowskiego org = new BarszczSosnowskiego(pos,swiat);
    }
}
