package zad1.instrukcja;

import zad1.swiat.Plansza;
import zad1.swiat.Rob;

public class Prawo implements Instrukcja {
    private static Prawo instance;

    private Prawo() { }

    public static Prawo getInstance() {
        if (instance == null)
            instance = new Prawo();
        return instance;
    }
    
    public void wykonaj(Rob rob, Plansza plansza) {
        rob.setZwrot(rob.getZwrotY(), -1 * rob.getZwrotX());
    }
}
