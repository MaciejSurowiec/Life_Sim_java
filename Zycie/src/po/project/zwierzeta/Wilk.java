package po.project.zwierzeta;
import po.project.Zwierze;

import po.project.Vector;
import po.project.Swiat;
import java.awt.Color;

public class Wilk extends Zwierze {

    public static final String name="Wilk";

    public Wilk(Vector pos, Swiat sw) {
        sila = 9;
        inicjatywa = 5;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        avatar = new Color(96, 96, 96);
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public Wilk()
{;}


    public String dajGlos(){return name;};



    public void makeChild(Vector pos)
    {
        //dodaj komentatora
        Wilk org = new Wilk(pos,swiat);
    }


}
