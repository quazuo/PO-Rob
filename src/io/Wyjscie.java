package zad1.io;

import zad1.swiat.Plansza;
import zad1.swiat.Pole;
import zad1.swiat.Rob;

import java.util.ArrayList;

/* Klasa zajmująca się wypisywaniem statystyk na wyjście. */

public class Wyjscie {
    public static void wypiszDaneSymulacji(int numerTury, Plansza plansza, ArrayList<Rob> roby) {
        Pole[][] pola = plansza.getPola();
        int rozmiarPlanszyX = Parametry.getCalkowite().get("rozmiar_planszy_x");
        int rozmiarPlanszyY = Parametry.getCalkowite().get("rozmiar_planszy_y");

        int ileJedzenia = 0;
        for (int i = 0; i < rozmiarPlanszyX; i++) {
            for (int j = 0; j < rozmiarPlanszyY; j++) {
                if (pola[i][j].czyMaJedzenie())
                    ileJedzenia++;
            }
        }

        if (roby.isEmpty()) {
            System.out.println(numerTury + ", rob: 0, żyw: " + ileJedzenia +
                    ", prg: 0/0/0, energ: 0/0/0, wiek: 0/0/0");
            return;
        }

        /* Obliczamy minimalne, średnie i maksymalne wartości  */
        int minDlProg = Integer.MAX_VALUE, maxDlProg = Integer.MIN_VALUE, sumaDlProg = 0;
        int minEnergia = Integer.MAX_VALUE, maxEnergia = Integer.MIN_VALUE, sumaEnergii = 0;
        int minWiek = Integer.MAX_VALUE, maxWiek = Integer.MIN_VALUE, sumaWiekow = 0;
        for (Rob obecnyRob : roby) {
            int dlProg = obecnyRob.getDlugoscProgramu();
            sumaDlProg += dlProg;
            minDlProg = Math.min(minDlProg, dlProg);
            maxDlProg = Math.max(maxDlProg, dlProg);

            int energia = obecnyRob.getEnergia();
            sumaEnergii += energia;
            minEnergia = Math.min(minEnergia, energia);
            maxEnergia = Math.max(maxEnergia, energia);

            int wiek = obecnyRob.getWiek();
            sumaWiekow += wiek;
            minWiek = Math.min(minWiek, wiek);
            maxWiek = Math.max(maxWiek, wiek);
        }
        float srDlProg = (float)sumaDlProg / roby.size();
        float srEnergia = (float)sumaEnergii / roby.size();
        float srWiek = (float)sumaWiekow / roby.size();

        System.out.println(numerTury + ", rob: " + roby.size() + ", żyw: " + ileJedzenia +
                ", prg: " + minDlProg + "/" + String.format("%.2f", srDlProg) + "/" + maxDlProg +
                ", energ: " + minEnergia + "/" + String.format("%.2f", srEnergia) + "/" + maxEnergia +
                ", wiek: " + minWiek + "/" + String.format("%.2f", srWiek) + "/" + maxWiek);
    }

    public static void wypiszDaneRobow(ArrayList<Rob> roby) {
        System.out.println();
        System.out.println("Dane na temat robów:");
        if (roby.isEmpty()) {
            System.out.println("Brak robów.");
            return;
        }

        int ileRobow = roby.size();
        for (int i = 0; i < ileRobow; i++) {
            Rob obecnyRob = roby.get(i);
            System.out.println(i + obecnyRob.toString());
        }

        System.out.println();
    }
}
