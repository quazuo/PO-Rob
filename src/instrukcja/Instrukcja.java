package zad1.instrukcja;

import zad1.swiat.Plansza;
import zad1.swiat.Rob;

public interface Instrukcja {
    void wykonaj(Rob rob, Plansza plansza);
}
