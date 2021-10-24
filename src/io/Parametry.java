package zad1.io;

import zad1.instrukcja.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/* Klasa przechowująca wszystkie parametry. Warto dodać, że dla
 * wygody do parametrów całkowitych dodaję również wymiary planszy. */

public abstract class Parametry {
    private static boolean czyZainicjowane = false;
    private static boolean czyDodanoRozmiar = false;
    private static HashMap<String, Integer> calkowite;
    private static HashMap<String, Float> ulamkowe;
    private static ArrayList<Instrukcja> poczProgr;
    private static HashSet<Instrukcja> spisInstr;

    public static HashMap<String, Integer> getCalkowite() {
        return new HashMap<>(calkowite);
    }
    public static void dodajRozmiarDoCalk(int rozmiarPlanszyX, int rozmiarPlanszyY) {
        if (czyDodanoRozmiar)
            return;
        czyDodanoRozmiar = true;

        calkowite.put("rozmiar_planszy_x", rozmiarPlanszyX);
        calkowite.put("rozmiar_planszy_y", rozmiarPlanszyY);
    }
    public static HashMap<String, Float> getUlamkowe() {
        return new HashMap<>(ulamkowe);
    }
    public static ArrayList<Instrukcja> getPoczProgr() {
        return new ArrayList<>(poczProgr);
    }
    public static Instrukcja getLosowaInstr() {
        Random rand = new Random();
        int numerInstr = rand.nextInt(spisInstr.size());
        int i = 0;
        for (Instrukcja instr : spisInstr) {
            if (i == numerInstr)
                return instr;
            i++;
        }
        return null;
    }

    public static void inicjuj(HashMap<String, Integer> c, HashMap<String, Float> u,
                               HashSet<Instrukcja> i, ArrayList<Instrukcja> p) {
        if (czyZainicjowane)
            return;
        czyZainicjowane = true;

        calkowite = c;
        ulamkowe = u;
        spisInstr = i;
        poczProgr = p;
    }

    public static boolean czyWczytanoWszystkie() {
        boolean wynik = calkowite.containsKey("ile_tur");
        wynik &= calkowite.containsKey("rozmiar_planszy_x");
        wynik &= calkowite.containsKey("rozmiar_planszy_y");
        wynik &= calkowite.containsKey("pocz_ile_robów");
        wynik &= calkowite.containsKey("pocz_energia");
        wynik &= calkowite.containsKey("ile_daje_jedzenie");
        wynik &= calkowite.containsKey("ile_rośnie_jedzenie");
        wynik &= calkowite.containsKey("koszt_tury");
        wynik &= calkowite.containsKey("limit_powielania");
        wynik &= calkowite.containsKey("co_ile_wypisz");

        wynik &= ulamkowe.containsKey("pr_powielenia");
        wynik &= ulamkowe.containsKey("ułamek_energii_rodzica");
        wynik &= ulamkowe.containsKey("pr_usunięcia_instr");
        wynik &= ulamkowe.containsKey("pr_dodania_instr");
        wynik &= ulamkowe.containsKey("pr_zmiany_instr");

        wynik &= (poczProgr != null);
        wynik &= (spisInstr != null);

        return wynik;
    }
}
