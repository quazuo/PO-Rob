package zad1.instrukcja;

import zad1.swiat.Plansza;
import zad1.swiat.Pole;
import zad1.swiat.Rob;

public class Jedz implements Instrukcja {
    private static Jedz instance;

    private Jedz() { }

    public static Jedz getInstance() {
        if (instance == null)
            instance = new Jedz();
        return instance;
    }

    public void wykonaj(Rob rob, Plansza plansza) {
        Pole[][] pola = plansza.getPola();

        outerloop: for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;

                int xSprawdzane = Plansza.obliczX(rob.getX() + i);
                int ySprawdzane = Plansza.obliczY(rob.getY() + j);
                Pole sprawdzane = pola[xSprawdzane][ySprawdzane];

                if (sprawdzane.czyMaJedzenie()) {
                    rob.setKoordynaty(xSprawdzane, ySprawdzane);
                    rob.jedz(plansza);
                    break outerloop;
                }
            }
        }
    }
}
