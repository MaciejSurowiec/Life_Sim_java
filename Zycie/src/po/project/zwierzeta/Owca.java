package po.project.zwierzeta;

import po.project.Vector;
import po.project.Zwierze;
import po.project.Swiat;
import java.awt.Color;

public class Owca extends Zwierze {

    public static final String name="Owca";
    public Owca(Vector pos, Swiat sw)
    {
        sila = 4;
        inicjatywa = 4;
        toyoung = true;
        swiat = sw;
        pozycja = pos;
        avatar = new Color(255,255,255);

        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }
    public String dajGlos(){return name;};

    public void makeChild(Vector pos)
    {
        Owca org = new Owca(pos,swiat);
    }
}
