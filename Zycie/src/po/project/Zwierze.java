package po.project;
import po.project.Vector;


public abstract class Zwierze  extends Organizm {

    protected void move(int i)
    {
        if(i< Vector.DIRECTIONS*2) {
            if (swiat.checkPosition(Vector.Add(pozycja, Vector.Direction(i)))) {
                if (swiat.freePosition(Vector.Add(pozycja, Vector.Direction(i)))) {
                    swiat.setMapElement(Vector.Add(pozycja, Vector.Direction(i)), this);
                    swiat.setMapElement(pozycja, null);
                    pozycja = Vector.Add(pozycja, Vector.Direction(i));
                } else {
                    Organizm oponent = swiat.getMapElement(Vector.Add(pozycja, Vector.Direction(i)));
                    int temp = oponent.kolizja(this);
                    swiat.setMapElement(pozycja, null);

                    if (temp > NARODZINY) {
                        if (temp == SMIERC) {
                            remove();
                        } else {
                            if (temp == ZWYCIESTWO) {
                                pozycja = Vector.Add(pozycja, Vector.Direction(i));
                                swiat.setMapElement(pozycja, this);
                            }
                        }
                    } else swiat.setMapElement(pozycja, this);
                }
            } else {
                move(i + 1);
            }
        }
        else
        {
            swiat.setMapElement(pozycja,null);
            swiat.komentator.brakwyboru(this);
            remove();// skazany na smierc przez niezdecydownie
        }
    }

    public void akcja()
    {
        if(toyoung)toyoung=false;
        else {
            move(r_gen.nextInt(Vector.DIRECTIONS));
        }
    }

    public void rozmnazanie()
    {
        for (int i = 0; i < Vector.DIRECTIONS; i++)
        {
            if (swiat.checkPosition(Vector.Add(pozycja , Vector.Direction(i))))
            {
                if (swiat.freePosition(Vector.Add(pozycja , Vector.Direction(i))))
                {
                    makeChild(Vector.Add(pozycja,Vector.Direction(i)));
                    break;
                }
            }
        }
    }

    public abstract void makeChild(Vector pos);


    public int kolizja(Organizm temp)
    {
        if (temp.checkGatunek(avatar))
        {
            if (!toyoung && !temp.mature())
            {
                swiat.komentator.narodziny(this);
                rozmnazanie();
            }
            return NARODZINY;
        }
        else
        {
            swiat.komentator.atak(temp,this);

            if (sila > temp.getSila())
            {
                swiat.komentator.zabojstwo(this,temp);
                swiat.checkCzlowiek(temp);
                return SMIERC;//aktualny organizm wygrywa
            }
            else
            {
                swiat.komentator.zabojstwo(temp,this);
                swiat.checkCzlowiek(this);
                swiat.setMapElement(pozycja, null);
                remove();
                return ZWYCIESTWO;//aktualny organizm przegrywa
            }
        }
    }



}
