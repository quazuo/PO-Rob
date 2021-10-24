package zad1.instrukcja;

import zad1.swiat.Plansza;
import zad1.swiat.Rob;

public class Lewo implements Instrukcja {
    private static Lewo instance;

    private Lewo() { }

    public static Lewo getInstance() {
        if (instance == null)
            instance = new Lewo();
        return instance;
    }

    public void wykonaj(Rob rob, Plansza plansza) {
        rob.setZwrot(-1 * rob.getZwrotY(), rob.getZwrotX());
    }
}
