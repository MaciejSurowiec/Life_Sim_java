package po.project;
import po.project.Swiat;
import java.util.Random;
import po.project.Vector;
import java.awt.Color;
import po.project.rosliny.*;
import po.project.zwierzeta.*;


public abstract class Organizm {

    protected final int NARODZINY = 0;
    protected final int SMIERC = 1;
    protected final int ZWYCIESTWO = 2;

    public static final String name="org";
    protected int sila;
    public Color avatar;
    protected int inicjatywa;
    protected Swiat swiat;
    protected boolean toyoung;
    protected Random r_gen = new Random();
    protected Vector pozycja;

    protected int PROCENTY = 100;

    public Organizm(){}

    public abstract void akcja();

    public abstract int kolizja(Organizm org);

    public abstract String dajGlos();

    public  int getSila() { return sila; }

    public boolean mature() { return toyoung; }

    public void addSila(int si) {sila+=si;}

    public Vector getPozycja()
    {
        return pozycja;
    }


    public boolean checkGatunek(Color temp)
    {
        if(temp == avatar)return true;
        else return false;
    }


    // zamiast chara moze byc classa na ktorej zapisuje jak wyswietlane jest zwierze
    //void makeChild(Vector pos); musze zrobic klase pomocnicza list i vector

     public int getInic() { return inicjatywa; }

     public void remove()
     {
        swiat.deleteFromList(this);
     }

    public abstract void makeChild(Vector pos);

    public String toString()
    {
        StringBuilder s =  new StringBuilder();
        s.append(dajGlos());
        s.append(" "+String.valueOf(sila));
        s.append(" "+pozycja.toString());

        return s.toString();
    }

    public static Organizm fromString(String s,Swiat sw)
    {
        char c=0;
        int i=0;

        StringBuilder name= new StringBuilder();
        c=s.charAt(i);
        while(c != ' ')
        {
            i++;
            name.append(c);
            c=s.charAt(i);
        }
        //System.out.print(name);


        System.out.print("\n"+c+"\n");
        i++;
        int sil = 0;
        StringBuilder silka= new StringBuilder();
        c=s.charAt(i);

        while(c != ' ')
        {
            i++;
            silka.append(c);
            c=s.charAt(i);
        }
        sil=Integer.parseInt(String.valueOf(silka.toString()));
        i++;

        StringBuilder poss= new StringBuilder();
        c=s.charAt(i);
        while(c != ' ' && i<s.length())
        {
            i++;
            poss.append(c);
            if (s.length()>i)c=s.charAt(i);
        }

        Vector pos = Vector.fromString(poss.toString());

        Organizm org;
        switch(name.toString())
        {
            case Wilk.name: org= new Wilk(pos,sw);
                break;
            case Zolw.name: org= new Zolw(pos,sw);
                break;
            case Owca.name: org = new Owca(pos,sw);
                break;
            case Lis.name:org = new Lis(pos,sw);
                break;
            case Czlowiek.name:Czlowiek cz = new Czlowiek(pos,sw);

                int pow = 0;
                i++;
                StringBuilder powe= new StringBuilder();
                c=s.charAt(i);
                while(c != ' ' && i < s.length())
                {
                    i++;
                    powe.append(c);
                    if (s.length()>i) c=s.charAt(i);
                }
                pow=Integer.parseInt(String.valueOf(powe));
                    cz.setPower(pow);
                    org=cz;
                break;
            case CyberOwca.name:org = new CyberOwca(pos,sw);
                break;
            case Antylopa.name:org = new Antylopa(pos,sw);
                break;
            case WilczeJagody.name:org = new WilczeJagody(pos,sw);
                break;
            case Trawa.name:org = new Trawa(pos,sw);
                break;
            case Guarana.name:org = new Guarana(pos,sw);
                break;
            case BarszczSosnowskiego.name:org = new BarszczSosnowskiego(pos,sw);
                break;
            case Mlecz.name:org=new Mlecz(pos,sw);
                break;
            default : org = new Owca(pos,sw);
                break;
        }

        if(sil-org.getSila()>0)
        {
            org.addSila(sil-org.getSila());
        }

        return org;
    }




}
