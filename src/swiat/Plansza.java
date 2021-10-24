package zad1.swiat;

import zad1.io.Parametry;

public class Plansza {
    private final Pole[][] pola;

    public Pole[][] getPola() {
        return pola;
    }

    public Plansza(Pole[][] pola) {
        this.pola = pola;
    }

    public static int obliczX(int liczba) {
        return Math.floorMod(liczba, Parametry.getCalkowite().get("rozmiar_planszy_x"));
    }
    public static int obliczY(int liczba) {
        return Math.floorMod(liczba, Parametry.getCalkowite().get("rozmiar_planszy_y"));
    }
}
