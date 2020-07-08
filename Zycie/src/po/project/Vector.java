package po.project;

import java.lang.Math;

public class Vector {

    public int x;
    public int y;
    static public final int DIRECTIONS=8;

    Vector(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    Vector()
    {;}


    public static final Vector Zero= new Vector(0,0);

    //jeszcze nie wiem jak dziala swing wiec co najwyzej zmienie znaki pozniej
    public static final Vector Down = new Vector(0,1);
    public static final Vector Up = new Vector(0,-1);
    public static final Vector Left = new Vector(-1,0);
    public static final Vector Right = new Vector(1,0);

    public static Vector Add(Vector one , Vector two) { return new Vector(one.x+two.x,one.y+two.y); }

    public static Vector Sub(Vector one , Vector two){return new Vector(one.x-two.x,one.y-two.y);}
    public static boolean Compare(Vector one , Vector two)
    {
        if(one.x == two.x && one.y == two.y) return true;
        else return false;
    }

    public static double Dlugosc(Vector one)
    {
        return (Math.sqrt(one.x*one.x+one.y*one.y));
    }

    public Vector doCelu()
    {
        if(x==0)
        {
            if(y<0) return Up;
            else return Down;
        }
        else
        {
            if(y==0)
            {
                if(x<0) return Left;
                else return Right;
            }
            else
            {
                if(x>0 && y>0) return Add(Right,Down);
                else
                {
                    if(x>0 && y<0) return Add(Right,Up);
                    else
                    {
                        if(x<0 && y>0) return Add(Left,Down);
                        else
                        {
                            if(x<0 && y<0) return Add(Left,Up);
                            else return Zero;
                        }
                    }
                }
            }
        }
    }

    public String toString()
    {
        return (x+","+y);
    }


    public static Vector fromString(String s)
    {
        boolean change=true;
        int sumx=0;
        int sumy=0;
        for(int i=0;i<s.length();i++)
        {
            char c=s.charAt(i);

            if(c==',')
            {
                change=false;
            }
            else
            {
                if (change)
                {
                    sumx *= 10;
                    sumx += Integer.parseInt(String.valueOf(c));
                }
                else
                {
                    sumy *= 10;
                    sumy += Integer.parseInt(String.valueOf(c));
                }
            }
        }

        return  new Vector(sumx,sumy);
    }





    public static Vector Direction(int n)
    {
        switch (n%8)
        {
            case 0: return Up;
            case 1: return Add(Up,Left);
            case 2: return Left;
            case 3: return Add(Down,Left);
            case 4: return Down;
            case 5: return Add(Down,Right);
            case 6: return Right;
            case 7: return Add(Up,Right);
            default: return Zero;
        }
    }

}
