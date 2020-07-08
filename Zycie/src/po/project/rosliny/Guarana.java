package po.project.rosliny;
import po.project.Vector;
import po.project.Roslina;
import po.project.Swiat;
import po.project.Organizm;
import java.awt.Color;
public class Guarana extends Roslina {
    private final int SILA = 3;
    public static final String name="Guarana";
    public Guarana(Vector vec, Swiat sw) {
        swiat = sw;
        pozycja = vec;
        inicjatywa = 0;
        sila = 0;
        avatar = new Color(146, 34, 70);
        toyoung = true;
        swiat.setMapElement(pozycja, this);
        swiat.pushToWorld(this);
    }



    public String dajGlos(){return name;};

    public int kolizja(Organizm temp)
    {
        swiat.komentator.atak(temp,this);
        swiat.komentator.zabojstwo(temp,this);
        temp.addSila(SILA);
        swiat.setMapElement(pozycja, null);
        remove();
        return ZWYCIESTWO;
    }


    public void makeChild(Vector pos)
    {
        Guarana org = new Guarana(pos,swiat);
    }
}
