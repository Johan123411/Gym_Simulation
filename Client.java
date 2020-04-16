package Assignment2;

import java.util.*;

public class Client {
    private int id;
    private List<Exercise> routine;

    //SRC : https://www.geeksforgeeks.org/arraylist-in-java/
    public Client(int id) {
        this.id = id;
        routine = new ArrayList<Exercise>();
    }

    public void addExercise(Exercise ex) {
        routine.add(ex);
    }

    public static Client generateRandomClient(int id) {
        Client newbie = new Client(id);
        int number_of_exercises = new Random().nextInt(6); //maximum of 20 exercises in a routine and minimum of 15
        number_of_exercises += 15;


        while (number_of_exercises != 0) { //generating a random number of exercises for the client Routine;
            Map<WeightPlateSize, Integer> something = new HashMap<>();
            int[] intArray = new int[3]; // 0 - small weights , 1 = medium weights, 2 = large weights
            intArray[0] = WeightPlateSize.GetSmall();
            intArray[1] = WeightPlateSize.GetMedium();
            intArray[2] = WeightPlateSize.GetLarge();
            // we know that the simulation is to generate 0 - 10 weights for each weight type

            // if for some reason we have small + medium + large = 0 then we generate random for small again
            if (intArray[0] + intArray[1] + intArray[2] == 0) {
                intArray[0] += new Random().nextInt(10) + 1;

            }

            something.put(WeightPlateSize.SMALL_3KG, intArray[0]);
            something.put(WeightPlateSize.MEDIUM_5KG, intArray[1]);
            something.put(WeightPlateSize.LARGE_10KG, intArray[2]);

            Exercise exercise = Exercise.generateRandom(something); //generating a random exercise;
            newbie.addExercise(exercise);
            number_of_exercises--;
        }
        return newbie;
    }


//    public void print() {
//        System.out.println("ID :" + id + " ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("Routine : { ");
//        System.out.println("Number of Exercises in the routine: " + getRoutine().size());
//        for (int i = 0; i < getRoutine().size(); i++) {
//            System.out.println("[ Apparatus Type :" + getRoutine().get(i).getApparatusType());
//            System.out.println("Weight :" + getRoutine().get(i).getWeight());
//            System.out.println("Duration :" + getRoutine().get(i).getDuration() + "] , ");
//        }
//        System.out.println("}");
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//    }

    public List<Exercise> getRoutine() {
        return routine;
    }

    public int getID() {
        return id;
    }
}