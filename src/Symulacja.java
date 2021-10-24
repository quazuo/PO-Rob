package zad1;

import zad1.swiat.Plansza;
import zad1.swiat.Pole;
import zad1.swiat.Rob;
import zad1.io.*;
import zad1.util.IncorrectParametersException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

/* Główna klasa projektu. Zajmuje się całym
 * ogółem symulacji, od inicjacji do przeprowadzenia. */

public class Symulacja {
    private static Plansza plansza;
    private static ArrayList<Rob> roby;

    public static void main(String[] args) throws FileNotFoundException, IncorrectParametersException {
        String sciezkaDoPlanszy = args[0];
        String sciezkaDoParametrow = args[1];

        inicjuj(sciezkaDoPlanszy, sciezkaDoParametrow);
        przeprowadzSymulacje();
    }

    /* Funkcja inicjująca symulację. Inicjuje planszę,
     * wczytuje parametry i wypełnia tablicę (roby) robami. */
    private static void inicjuj(String sciezkaDoPlanszy, String sciezkaDoParametrow)
            throws FileNotFoundException, IncorrectParametersException {
        Wejscie.wczytajParametry(sciezkaDoParametrow);
        plansza = Wejscie.wczytajPlansze(sciezkaDoPlanszy);
        if (!Parametry.czyWczytanoWszystkie())
            throw new IncorrectParametersException("parametry.txt: za mało parametrów.");

        roby = new ArrayList<>();
        int poczIleRobow = Parametry.getCalkowite().get("pocz_ile_robów");
        for (int i = 0; i < poczIleRobow; i++) {
            Rob nowyRob = new Rob();
            roby.add(nowyRob);
        }
    }

    /* Funkcja symulująca całą, jedną turę. */
    private static void przeprowadzTure() {
        /* Resetujemy programy każdego roba. */
        for (Rob obecnyRob : roby) {
            obecnyRob.odswiez();
        }

        ArrayList<Rob> noweRoby = new ArrayList<>();

        /* Wykonujemy programy każdego roba. */
        boolean czyWszyscySkonczyli = false;
        while (!czyWszyscySkonczyli) {
            Collections.shuffle(roby);
            czyWszyscySkonczyli = true;

            for (Rob obecnyRob : roby) {
                if (!obecnyRob.czyKoniecProgramu())
                    czyWszyscySkonczyli = false;

                if (!obecnyRob.czyUmiera()) {
                    obecnyRob.wykonajInstrukcje(plansza);
                    obecnyRob.powielSie(noweRoby);
                }
            }

            /* Usuwamy martwe roby. */
            int ileRobow = roby.size();
            for (int i = 0; i < ileRobow; i++) {
                Rob obecnyRob = roby.get(i);
                if (obecnyRob.czyUmiera()) {
                    roby.remove(obecnyRob);
                    ileRobow--;
                    i--;
                }
            }
        }

        /* Na każdym polu rośnie jedzenie. */
        for (Pole[] rzadPol : plansza.getPola()) {
            for (Pole pole : rzadPol)
                pole.rosnij();
        }

        /* Kończymy turę i odejmujemy (koszt_tury) energii każdemu robowi. */
        int ileRobow = roby.size();
        for (int i = 0; i < ileRobow; i++) {
            Rob obecnyRob = roby.get(i);

            obecnyRob.zakonczSwojaTure();

            if (obecnyRob.czyUmiera()) {
                roby.remove(obecnyRob);
                ileRobow--;
                i--;
            }
        }

        /* Dodajemy dzieci do tablicy (roby). */
        roby.addAll(noweRoby);
    }

    /* Funkcja przeprowadzająca całość symulacji.
     * Symulacja kończy się przedwcześnie, jeśli wszystkie roby umrą. */
    private static void przeprowadzSymulacje() {
        for (int i = 0; i < Parametry.getCalkowite().get("ile_tur"); i++) {
            przeprowadzTure();

            Wyjscie.wypiszDaneSymulacji(i, plansza, roby);
            if (i % Parametry.getCalkowite().get("co_ile_wypisz") == 0)
                Wyjscie.wypiszDaneRobow(roby);

            if (roby.isEmpty()) {
                System.out.println("Symulacja zakończona przedwcześnie w turze " + i + ".");
                break;
            }
        }
    }
}
