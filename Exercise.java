package Assignment2;

import java.util.Map;
import java.util.Random;

public class Exercise {
    private ApparatusType at;
    private int duration;
    private Map<WeightPlateSize, Integer> weight;

    private static Map<WeightPlateSize, Integer> temporary;

    //This is the constructor for exercise class
    public Exercise(ApparatusType at, Map<WeightPlateSize, Integer> weight, int duration) {
        this.at = at;
        this.weight = weight;
        this.duration = duration;

    }

    // recursive class definition, from in class method SRC : https://espressoprogrammer.com/recursive-class-initialization-java/
    public static Exercise generateRandom(Map<WeightPlateSize, Integer> weight) {
        /*
        duration can be anything between 1ms - 10ms [cannot be 0 as it is not a workout then]
        and 1ms equates to 1 minute;
        SRC: https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
        */
        int dur = new Random().nextInt(10) + 1;

        /*
        in this we notice that random().next int(10) will generate values within range [0...9],
        we dont want 0 as workout takes more than 0ms and we need 10 to be the max; hence we add + 1
        */
        Exercise tempEx = new Exercise(ApparatusType.getRamdomApparatus(), weight, dur);
        return tempEx;
    }

    /*
    Since apparatus type at is a private variable, they cannot be accessed and changed, to figure out which apparatus is being used
     We define a getApparatusType Class which returns the current randomly generated apparatus to be used
    */
    public ApparatusType getApparatusType() {
        return at;
    }

    /*
    Since weight is also a private variable, they cannot be accessed and changed, to figure out which weight and how many of them are used
     We define a getWeight Class which returns the current randomly generated Weight and the number of weights used.
    */
    public Map<WeightPlateSize, Integer> getWeight() {
        return weight;
    }

    /*
    Since duration is also a private variable, they cannot be accessed and changed, to figure out the duration of the exercise
     We define a getWeight Class which returns the current randomly generated duration of the Exercise.
    */
    public int getDuration() {
        return duration;
    }

}
