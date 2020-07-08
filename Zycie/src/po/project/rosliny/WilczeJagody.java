package po.project.rosliny;
import po.project.Vector;
import po.project.Roslina;
import po.project.Swiat;
import po.project.Organizm;

import java.awt.Color;

public class WilczeJagody extends Roslina{
    public static final String name= "Wilcze_Jagody";
    public WilczeJagody(Vector vec, Swiat sw)
    {
        swiat = sw;
        pozycja = vec;
        inicjatywa = 0;
        sila = 99;
        avatar = new Color(255,51,51);
        toyoung = true;
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }



    public String dajGlos(){return name;};

    public int kolizja(Organizm temp)
    {
        swiat.komentator.atak(temp,this);
        swiat.komentator.zabojstwo(temp,this);
        swiat.setMapElement(pozycja, null);
        swiat.checkCzlowiek(temp);
        remove();
        return SMIERC;
    }



    public void makeChild(Vector pos)
    {
        WilczeJagody org = new WilczeJagody(pos,swiat);
    }
}
