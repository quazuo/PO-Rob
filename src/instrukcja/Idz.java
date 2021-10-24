package zad1.instrukcja;

import zad1.swiat.Plansza;
import zad1.swiat.Rob;

public class Idz implements Instrukcja {
    private static Idz instance;

    private Idz() { }

    public static Idz getInstance() {
        if (instance == null)
            instance = new Idz();
        return instance;
    }

    public void wykonaj(Rob rob, Plansza plansza) {
        rob.setKoordynaty(Plansza.obliczX(rob.getX() + rob.getZwrotX()),
                          Plansza.obliczY(rob.getY() + rob.getZwrotY()));

        rob.jedz(plansza);
    }
}
