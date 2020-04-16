package Assignment2;

public enum ApparatusType {
    LEGPRESSMACHINE, BARBELL, HACKSQUATMACHINE, LEGEXTENSIONMACHINE,
    LEGCURLMACHINE, LATPULLDOWNMACHINE, PECDECKMACHINE,
    CABLECROSSOVERMACHINE;

/*
    private static final ApparatusType[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();
    src : https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
    by : Adilli Adil
*/

    public static ApparatusType getRamdomApparatus() {
        return values()[(int) (Math.random() * values().length)];
    }
}

