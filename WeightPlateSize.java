package Assignment2;

import java.util.Random;

public enum WeightPlateSize {

    SMALL_3KG, MEDIUM_5KG, LARGE_10KG;

    private static int max = 11;
    // SRC : SRC: https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
    public static int GetSmall() {
        return new Random().nextInt(max); // generates btw 0....10 numbers of Small Plates
    }

    public static int GetMedium() {
        return new Random().nextInt(max); // generates btw 0....10 numbers of Small Plates
    }

    public static int GetLarge() {
        return new Random().nextInt(max); // generates btw 0....10 numbers of Small Plates
    }


}

