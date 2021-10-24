package zad1.swiat;

import zad1.io.Parametry;

public class Zywieniowe extends Pole {
    private int ileTurOdZjedzenia;

    public Zywieniowe() { ileTurOdZjedzenia = Parametry.getCalkowite().get("ile_rośnie_jedzenie"); }

    public void rosnij() {
        if (ileTurOdZjedzenia == Parametry.getCalkowite().get("ile_rośnie_jedzenie"))
            return;

        ileTurOdZjedzenia++;
    }

    @Override
    public boolean czyMaJedzenie() {
        return (ileTurOdZjedzenia == Parametry.getCalkowite().get("ile_rośnie_jedzenie"));
    }

    public void oproznij() {
        ileTurOdZjedzenia = 0;
    }
}
