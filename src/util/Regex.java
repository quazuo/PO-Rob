package zad1.util;

/* Klasa zawierająca wyrażenia regularne, używane w klasie io.Wejscie
 * w celu sprawdzenia poprawności wczytywanych danych. */

public abstract class Regex {
    private final static String regexInt = "^((pocz_ile_robów|pocz_energia|ile_daje_jedzenie|" +
            "ile_rośnie_jedzenie|co_ile_wypisz|koszt_tury|ile_tur|limit_powielania) \\d+)$";
    private final static String regexFloat = "^((pr_powielenia|pr_usunięcia_instr|pr_dodania_instr|" +
            "pr_zmiany_instr|ułamek_energii_rodzica) 0?\\.\\d+)$";
    private final static String regexInstr = "^(spis_instr [lpiwj]*)$";
    private final static String regexProgr = "^(pocz_progr [lpiwj]*)$";

    public static String getRegexInt() {
        return regexInt;
    }
    public static String getRegexFloat() {
        return regexFloat;
    }
    public static String getRegexInstr() {
        return regexInstr;
    }
    public static String getRegexProgr() {
        return regexProgr;
    }
}
