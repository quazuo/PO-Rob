package zad1.io;

import zad1.instrukcja.*;
import zad1.swiat.*;
import zad1.util.IncorrectParametersException;
import zad1.util.Regex;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/* Klasa zajmująca się wczytywaniem danych z wejścia. */

public class Wejscie {
    /* Funkcja dodająca instrukcję do spisu instrukcji. */
    private static void dodajDoSpisu(HashSet<Instrukcja> spisInstr,
                                     Instrukcja instr, String err) throws IncorrectParametersException {
        if (spisInstr.contains(instr))
            throw new IncorrectParametersException(err);

        spisInstr.add(instr);
    }

    private static boolean czyPoczProgrWSpisie(ArrayList<Instrukcja> poczProgr,
                                               HashSet<Instrukcja> spisInstr) {
        for (Instrukcja instr : poczProgr) {
            if (!spisInstr.contains(instr))
                return false;
        }
        return true;
    }

    public static void wczytajParametry(String sciezka)
            throws FileNotFoundException, IncorrectParametersException {
        File plik = new File(sciezka);
        Scanner scanner = new Scanner(plik);

        HashMap<String, Integer> calkowite = new HashMap<>();
        HashMap<String, Float> ulamkowe = new HashMap<>();
        HashSet<Instrukcja> spisInstr = new HashSet<>();
        ArrayList<Instrukcja> poczProgr = new ArrayList<>();

        int i = 0;
        while (scanner.hasNextLine()) {
            i++;
            String errInfo = "parametry.txt, linia: " + i;
            String linia = scanner.nextLine();
            String[] podzielonaLinia = linia.split(" ");
            String nazwaParametru = podzielonaLinia[0];
            String wartoscParametru = podzielonaLinia[1];

            /* Dla każdej linii sprawdzamy jakiego typu parametr zawiera,
             * potem go odpowiednio parsujemy i dodajemy do parametrów. */
            if (linia.matches(Regex.getRegexInt())) {
                /* Linia zawiera parametr całkowity. */
                if (calkowite.containsKey(nazwaParametru)) {
                    scanner.close();
                    throw new IncorrectParametersException(errInfo);
                }

                calkowite.put(nazwaParametru, Integer.parseInt(wartoscParametru));
            } else if (linia.matches(Regex.getRegexFloat())) {
                /* Linia zawiera parametr wymierny. */
                if (ulamkowe.containsKey(nazwaParametru)) {
                    scanner.close();
                    throw new IncorrectParametersException(errInfo);
                }

                ulamkowe.put(nazwaParametru, Float.parseFloat(wartoscParametru));
            } else if (linia.matches(Regex.getRegexProgr())) {
                /* Linia zawiera początkowy program. */
                int dlugosc = wartoscParametru.length();

                for (int j = 0; j < dlugosc; j++) {
                    switch (wartoscParametru.charAt(j)) {
                        case 'l': poczProgr.add(Lewo.getInstance()); break;
                        case 'p': poczProgr.add(Prawo.getInstance()); break;
                        case 'i': poczProgr.add(Idz.getInstance()); break;
                        case 'j': poczProgr.add(Jedz.getInstance()); break;
                        case 'w': poczProgr.add(Wachaj.getInstance()); break;
                    }
                }
            } else if (linia.matches(Regex.getRegexInstr())) {
                /* Linia zawiera spis instrukcji. */
                int dlugosc = wartoscParametru.length();

                for (int j = 0; j < dlugosc; j++) {
                    switch (wartoscParametru.charAt(j)) {
                        case 'l': dodajDoSpisu(spisInstr, Lewo.getInstance(), errInfo); break;
                        case 'p': dodajDoSpisu(spisInstr, Prawo.getInstance(), errInfo); break;
                        case 'i': dodajDoSpisu(spisInstr, Idz.getInstance(), errInfo); break;
                        case 'j': dodajDoSpisu(spisInstr, Jedz.getInstance(), errInfo); break;
                        case 'w': dodajDoSpisu(spisInstr, Wachaj.getInstance(), errInfo); break;
                    }
                }
            } else {
                /* Linia nie zawiera poprawnego parametru. */
                scanner.close();
                throw new IncorrectParametersException(errInfo);
            }
        }

        scanner.close();

        if (!czyPoczProgrWSpisie(poczProgr, spisInstr))
            throw new IncorrectParametersException("parametry.txt: początkowy program " +
                    "zawiera instrukcje spoza spisu instrukcji.");

        Parametry.inicjuj(calkowite, ulamkowe, spisInstr, poczProgr);
    }

    public static Plansza wczytajPlansze(String sciezka)
            throws FileNotFoundException, IncorrectParametersException {
        File plik = new File(sciezka);
        Scanner scanner = new Scanner(plik);

        ArrayList<String> input = new ArrayList<>();
        int rozmiarPlanszyX = -1, rozmiarPlanszyY = 0;

        /* Wypełniamy tablicę input, zawierającą wszystkie linijki tekstu z pliku. */
        while (scanner.hasNextLine()) {
            String linia = scanner.nextLine();
            rozmiarPlanszyY++;

            /* Sprawdzamy długość pierwszej linii. */
            if (rozmiarPlanszyX == -1)
                rozmiarPlanszyX = linia.length();
            /* Sprawdzamy, czy długość linii jest poprawna i czy się zgadza z resztą. */
            if (rozmiarPlanszyX == 0 || rozmiarPlanszyX != linia.length()) {
                scanner.close();
                throw new IncorrectParametersException("plansza.txt, linia " + rozmiarPlanszyY);
            }

            input.add(linia);
        }

        Pole[][] pola = new Pole[rozmiarPlanszyX][];
        for (int i = 0; i < rozmiarPlanszyX; i++)
            pola[i] = new Pole[rozmiarPlanszyY];

        /* Wypełniamy odpowiednio tablicę (pola). */
        for (int i = 0; i < rozmiarPlanszyX; i++) {
            for (int j = 0; j < rozmiarPlanszyY; j++) {
                switch (input.get(j).charAt(i)) {
                    case ' ':
                        pola[i][j] = new Puste();
                        break;
                    case 'x':
                        pola[i][j] = new Zywieniowe();
                        break;
                    default:
                        scanner.close();
                        throw new IncorrectParametersException("plansza.txt, niepoprawny znak w linii " + i);
                }
            }
        }

        scanner.close();

        Parametry.dodajRozmiarDoCalk(rozmiarPlanszyX, rozmiarPlanszyY);

        return new Plansza(pola);
    }
}
