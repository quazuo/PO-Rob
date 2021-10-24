package zad1.swiat;

import zad1.instrukcja.*;
import zad1.util.Pair;
import zad1.io.Parametry;

import java.util.ArrayList;
import java.util.Random;

public class Rob {
    private Pair<Integer, Integer> koordynaty;
    private Pair<Integer, Integer> zwrot;
    private final ArrayList<Instrukcja> program;
    private int energia;
    private boolean czyKoniecProgramu;
    private boolean czyProbowalSiePowielac;
    private int ktoraInstrukcja;
    private int wiek;

    public int getX() {
        return koordynaty.getFirst();
    }
    public int getY() {
        return koordynaty.getSecond();
    }
    public void setKoordynaty(int x, int y) {
        this.koordynaty = new Pair<>(x, y);
    }

    public int getZwrotX() {
        return zwrot.getFirst();
    }
    public int getZwrotY() {
        return zwrot.getSecond();
    }
    public void setZwrot(int x, int y) {
        this.zwrot = new Pair<>(x, y);
    }

    public ArrayList<Instrukcja> getProgram() {
        return new ArrayList<>(program);
    }
    public int getDlugoscProgramu() {
        return program.size();
    }

    public int getEnergia() { return energia; }

    public boolean czyKoniecProgramu() {
        return czyKoniecProgramu;
    }

    public void odswiez() {
        this.ktoraInstrukcja = 0;
        this.czyProbowalSiePowielac = false;
    }

    public int getWiek() {
        return wiek;
    }

    public boolean czyUmiera() {
        return (energia < 0);
    }

    public String toString() {
        return ": poz: (" + this.getX() + "," + this.getY() + "), prg: " +
               this.getDlugoscProgramu() + ", energ: " + this.energia + ", wiek: " + this.wiek;
    }

    /* Konstruktor roba tworzonego przy inicjowaniu symulacji. */
    public Rob() {
        Random rand = new Random();

        /* Losujemy koordynaty, na których pojawi się nowy rob. */
        int x = rand.nextInt(Parametry.getCalkowite().get("rozmiar_planszy_x"));
        int y = rand.nextInt(Parametry.getCalkowite().get("rozmiar_planszy_y"));
        this.koordynaty = new Pair<>(x, y);

        /* Losujemy zwrot nowego roba. */
        int pom;
        if (rand.nextBoolean())
            pom = 1;
        else
            pom = -1;
        if (rand.nextBoolean())
            this.zwrot = new Pair<>(pom, 0);
        else
            this.zwrot = new Pair<>(0, pom);

        this.program = Parametry.getPoczProgr();
        this.energia = Parametry.getCalkowite().get("pocz_energia");
    }

    /* Konstruktor roba tworzonego podczas powielania. */
    public Rob(Rob rodzic) {
        this.koordynaty = new Pair<>(rodzic.getX(), rodzic.getY());
        this.zwrot = new Pair<>(-1 * rodzic.getZwrotX(), -1 * rodzic.getZwrotY());
        this.program = rodzic.getProgram();
        this.energia = Math.round(rodzic.getEnergia() * Parametry.getUlamkowe().get("ułamek_energii_rodzica"));
        this.zmutuj();
    }

    private void zmutuj() {
        Random rand = new Random();

        if (!program.isEmpty() && rand.nextFloat() <= Parametry.getUlamkowe().get("pr_usunięcia_instr"))
            program.remove(program.size() - 1);

        if (rand.nextFloat() <= Parametry.getUlamkowe().get("pr_dodania_instr"))
            program.add(Parametry.getLosowaInstr());

        if (!program.isEmpty() && rand.nextFloat() <= Parametry.getUlamkowe().get("pr_zmiany_instr")) {
            int index = rand.nextInt(program.size());
            program.set(index, Parametry.getLosowaInstr());
        }
    }

    public void jedz(Plansza plansza) {
        Pole obecne = plansza.getPola()[this.getX()][this.getY()];

        if (!obecne.czyMaJedzenie())
            return;

        this.energia += Parametry.getCalkowite().get("ile_daje_jedzenie");
        obecne.oproznij();
    }

    public void zakonczSwojaTure() {
        energia -= Parametry.getCalkowite().get("koszt_tury");
        wiek++;
    }

    public void wykonajInstrukcje(Plansza plansza) {
        if (czyKoniecProgramu)
            return;
        if (ktoraInstrukcja == program.size()) {
            czyKoniecProgramu = true;
            return;
        }

        program.get(ktoraInstrukcja).wykonaj(this, plansza);
        ktoraInstrukcja++;

        energia--;
    }

    public void powielSie(ArrayList<Rob> noweRoby) {
        if (czyProbowalSiePowielac || energia < Parametry.getCalkowite().get("limit_powielania"))
            return;

        czyProbowalSiePowielac = true;

        Random rand = new Random();
        if (rand.nextFloat() > Parametry.getUlamkowe().get("pr_powielenia"))
            return;

        Rob dziecko = new Rob(this);
        noweRoby.add(dziecko);
        this.energia -= Math.round(this.energia * Parametry.getUlamkowe().get("ułamek_energii_rodzica"));
    }
}
