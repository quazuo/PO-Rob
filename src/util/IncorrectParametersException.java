package zad1.util;

/* Klasa implementująca wyjątek, który kończy program
 * gdy wczytana plansza lub parametry będą niepoprawne. */

public class IncorrectParametersException extends Exception {
    public IncorrectParametersException(String errInfo) {
        super("wczytano niepoprawne parametry symulacji w pliku: " + errInfo);
    }
}
