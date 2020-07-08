package po.project;

public abstract class Roslina extends Organizm {

    protected final int ZAPYLENIE = 10;

    public void akcja()
    {
        if (r_gen.nextInt(PROCENTY) < ZAPYLENIE)
        {

            zapylenie(r_gen.nextInt(Vector.DIRECTIONS));
        }
    }


    public int kolizja(Organizm org)
    {
        swiat.komentator.atak(org,this);
        swiat.komentator.zabojstwo(org,this);
        swiat.setMapElement(pozycja, null);
        remove();
        return ZWYCIESTWO;
    }

    protected void zapylenie(int pos)
    {
        if (pos < Vector.DIRECTIONS * 2)
        {
            if (swiat.checkPosition(Vector.Add(pozycja , Vector.Direction(pos))))
            {
                if (swiat.freePosition(Vector.Add(pozycja , Vector.Direction(pos))))
                {
                    swiat.komentator.narodziny(this);
                    makeChild(Vector.Add(pozycja , Vector.Direction(pos)));
                }
                else zapylenie(pos + 1);
            }
            else zapylenie(pos + 1);
        }
    }







}
