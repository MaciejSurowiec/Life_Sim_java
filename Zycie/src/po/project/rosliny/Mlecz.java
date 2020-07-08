package po.project.rosliny;
import po.project.Vector;
import po.project.Roslina;
import po.project.Swiat;
import java.awt.Color;

public class Mlecz extends Roslina {

    final int PROBY = 3;
    public static final String name="Mlecz";
    public Mlecz(Vector vec, Swiat sw)
    {
        swiat = sw;
        pozycja = vec;
        inicjatywa = 0;
        sila = 0;
        avatar = new Color(151, 168, 62);
        toyoung = true;
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }


    public String dajGlos(){return name;};

    public void akcja()
    {
        if (toyoung)toyoung = false;
        else
        {
            for (int i = 0; i < PROBY; i++)
            {
                if (r_gen.nextInt(PROCENTY) < ZAPYLENIE)
                {
                    zapylenie(r_gen.nextInt(Vector.DIRECTIONS));
                    break;
                }
            }
        }
    }



    public void makeChild(Vector pos)
    {
        Mlecz org = new Mlecz(pos,swiat);
    }
}
