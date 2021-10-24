package zad1.instrukcja;

import zad1.swiat.Plansza;
import zad1.swiat.Rob;

public class Wachaj implements Instrukcja {
    private static Wachaj instance;

    private Wachaj() { }

    public static Wachaj getInstance() {
        if (instance == null)
            instance = new Wachaj();
        return instance;
    }
    
    public void wykonaj(Rob rob, Plansza plansza) {
        Lewo lewo = Lewo.getInstance();

        for (int i = 0; i < 4; i++) {
            int xPrzedRobem = Plansza.obliczX(rob.getX() + rob.getZwrotX());
            int yPrzedRobem = Plansza.obliczY(rob.getY() + rob.getZwrotY());

            if (plansza.getPola()[xPrzedRobem][yPrzedRobem].czyMaJedzenie()) {
                break;
            }

            lewo.wykonaj(rob, plansza);
        }
    }
}
