package po.project.rosliny;

import po.project.Vector;
import po.project.Roslina;
import po.project.Swiat;
import java.awt.Color;

public class Trawa extends Roslina{


    public  static final String name="Trawa";

    public Trawa(Vector vec, Swiat sw)
    {
        swiat = sw;
        pozycja = vec;
        inicjatywa = 0;
        sila = 0;
        avatar=new Color(23, 199, 29);
        toyoung = true;
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }

    public String dajGlos(){return name;};

    public void makeChild(Vector pos)
    {
        Trawa org = new Trawa(pos,swiat);
    }


}
