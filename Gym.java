package Assignment2;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class Gym implements Runnable {
    private static final int GYM_SIZE = 30;
    private static final int GYM_REGISTERED_CLIENTS = 10000;
    private Set<Integer> clientID; //for generating fresh client id's
    private List<Client> client;
    private ExecutorService executor;
    private static int small_plate = 110;
    private static int medium_plate = 90;
    private static int Large_plate = 75;
    private static int num_apparatus = 5;


    /*
    semaphores for weight plates in order small, medium and large
     75 - Size large ; 90 - Size medium ; 110 - Size small;
     Order: Small, Medium and Large
    */
    private static final Semaphore[] weightPlateSemaphore = new Semaphore[]{new Semaphore(small_plate), new Semaphore(medium_plate), new Semaphore(Large_plate)};

    /*
     semaphores for apparatus, there are 5 of each
     Order :
     LEGPRESSMACHINE, BARBELL, HACKSQUATMACHINE, LEGEXTENSIONMACHINE,
     LEGCURLMACHINE, LATPULLDOWNMACHINE, PECDECKMACHINE, CABLECROSSOVERMACHINE;
    */
    private static final Semaphore[] apparatusTypeSemaphore = new Semaphore[]
            {
                    new Semaphore(num_apparatus), new Semaphore(num_apparatus), new Semaphore(num_apparatus), new Semaphore(num_apparatus),
                    new Semaphore(num_apparatus), new Semaphore(num_apparatus), new Semaphore(num_apparatus), new Semaphore(num_apparatus)
            };

    /*
     set of semaphores that makes sure that the Weight Plate object a Client acquires cannot be acquired by another Client.
    Order Again Small, Medium and Large
    */
    private static final Semaphore[] plate_access_semaphore = new Semaphore[]{new Semaphore(1), new Semaphore(1), new Semaphore(1)};


    public Gym() {

        //Now we generate a list of unique id's
        clientID = new HashSet<>();
        for (int i = 1; i <= GYM_REGISTERED_CLIENTS; i++) {
            clientID.add(i);
        }
        //Now we generate a random client to enter the gym from the pool of clients in the Unique id's list.
        client = new ArrayList<>();
        for (Integer id : clientID) {
            client.add(Client.generateRandomClient(id));
        }
        /*
        We know that it is not necessary for a client to arrive in the order of 1, 2, 3, 4 ........ 10000;
        Arrival is a random thing hence we shuffle the set of clients to simulate real world arrival patterns;
        Initializing executor service
        */

        /*
        //Before Shuffle
        System.out.println("before Shuffle");
        for(Client cl: client)
        {
            cl.print();
        }
      SRC : https://www.baeldung.com/java-shuffle-collection
        */
        Collections.shuffle(client);
        /*
        //After Shuffle
        System.out.println("after Shuffle");
        for(Client cl: client)
        {
            cl.print();
        }
        */
        executor = Executors.newFixedThreadPool(GYM_SIZE);
        // Start the executor thread pool of 30
    }

    @Override
    public void run() {
        for (Client ClITer : client) { //looping through all GYM_REGISTERED_CLIENTS in batches of GYM_SIZE
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (Exercise exercise : ClITer.getRoutine()) { //looping through all exercises in the routine
                        ApparatusType temp = exercise.getApparatusType();
                        Map<WeightPlateSize, Integer> weight_requirements = exercise.getWeight();

                        int small = weight_requirements.get(WeightPlateSize.SMALL_3KG);  //get the number of small plates for exercise
                        int medium = weight_requirements.get(WeightPlateSize.MEDIUM_5KG); //get the number of medium plates for exercise
                        int large = weight_requirements.get(WeightPlateSize.LARGE_10KG); //get the number of medium plates for exercise

                        try {
                            //we need to make sure a client can acquire all the required weights to perform his exercise
                            //System.out.println(ClITer.getID() + " is waitiing for plates");
                            //This is for Plate Station Access;
                            //System.out.println("waitiing for Small");
                            plate_access_semaphore[0].acquire();
                            //System.out.println("waitiing for medium");
                            plate_access_semaphore[1].acquire();
                            //System.out.println("waitiing for Large");
                            plate_access_semaphore[2].acquire();

                            //System.out.println(ClITer.getID() + " waiting for " + temp);
                            /*
                            Accessing semaphores for Apparatus Type
                            */

                            if (temp == ApparatusType.LEGPRESSMACHINE) {
                                //System.out.println("0");
                                apparatusTypeSemaphore[0].acquire();
                            } else if (temp == ApparatusType.BARBELL) {
                                //System.out.println("1");
                                apparatusTypeSemaphore[1].acquire();
                            } else if (temp == ApparatusType.HACKSQUATMACHINE) {
                                //System.out.println("2");
                                apparatusTypeSemaphore[2].acquire();
                            } else if (temp == ApparatusType.LEGEXTENSIONMACHINE) {
                                //System.out.println("3");
                                apparatusTypeSemaphore[3].acquire();
                            } else if (temp == ApparatusType.LEGCURLMACHINE) {
                                //System.out.println("4");
                                apparatusTypeSemaphore[4].acquire();
                            } else if (temp == ApparatusType.LATPULLDOWNMACHINE) {
                                //System.out.println("5");
                                apparatusTypeSemaphore[5].acquire();
                            } else if (temp == ApparatusType.PECDECKMACHINE) {
                                //System.out.println("6");
                                apparatusTypeSemaphore[6].acquire();
                            } else if (temp == ApparatusType.CABLECROSSOVERMACHINE) {
                                //System.out.println("7");
                                apparatusTypeSemaphore[7].acquire();
                            }
                            //Accessing semaphores for weight plates , ultil all required weights are acquired
                            //small
                            //System.out.println(ClITer.getID() + " has acquired " + temp);

                            for (int ptr = 0; ptr < small; ptr++) {
                                weightPlateSemaphore[0].acquire();
                            }
                            //medium
                            for (int ptr = 0; ptr < medium; ptr++) {
                                weightPlateSemaphore[1].acquire();
                            }
                            //large
                            for (int ptr = 0; ptr < large; ptr++) {
                                weightPlateSemaphore[2].acquire();
                            }

                            //If it comes till here; client has acquired the plates and the corresponding machine

                            System.out.println(new StringBuilder().append("*************************************************************************************").append("\nThe client : ").append(ClITer.getID()).append(" has acquired the machine: ").append(temp).append(" and the Client has acquired the following Weights : [ SMALL_3KG = ").append(small).append(", MEDIUM_5KG = ").append(medium).append(", LARGE_10KG = ").append(large).append(" ]").toString());

                            // after acquiring the client releases the binary semaphore so  other clients can access all required weights and hence begin their workout
                            plate_access_semaphore[0].release();
                            plate_access_semaphore[1].release();
                            plate_access_semaphore[2].release();
                            // Client now performs the exercise

                            System.out.println(new StringBuilder().append("*************************************************************************************").append("\nThe client : ").append(ClITer.getID()).append(" is using the machine: ").append(temp).append(" For ").append(exercise.getDuration()).append(" \"minutes\" ").toString());
                            Thread.sleep(exercise.getDuration());


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Releasing semaphores for Apparatus Type

                        if (temp == ApparatusType.LEGPRESSMACHINE) {
                            apparatusTypeSemaphore[0].release();
                        } else if (temp == ApparatusType.BARBELL) {
                            apparatusTypeSemaphore[1].release();
                        } else if (temp == ApparatusType.HACKSQUATMACHINE) {
                            apparatusTypeSemaphore[2].release();
                        } else if (temp == ApparatusType.LEGEXTENSIONMACHINE) {
                            apparatusTypeSemaphore[3].release();
                        } else if (temp == ApparatusType.LEGCURLMACHINE) {
                            apparatusTypeSemaphore[4].release();
                        } else if (temp == ApparatusType.LATPULLDOWNMACHINE) {
                            apparatusTypeSemaphore[5].release();
                        } else if (temp == ApparatusType.PECDECKMACHINE) {
                            apparatusTypeSemaphore[6].release();
                        } else if (temp == ApparatusType.CABLECROSSOVERMACHINE) {
                            apparatusTypeSemaphore[7].release();
                        }
                        //Releasing semaphores for weight plates , ultill all weights acquired are released

                        //small
                        for (int ptr = 0; ptr < small; ptr++) {
                            weightPlateSemaphore[0].release();
                        }

                        //medium

                        for (int ptr = 0; ptr < medium; ptr++) {
                            weightPlateSemaphore[1].release();
                        }

                        //large

                        for (int ptr = 0; ptr < large; ptr++) {
                            weightPlateSemaphore[2].release();
                        }

                        //Now the client has released all the weight plates and the apparatus
                        //System.out.println("The client : " + ClITer.getID() + " has released the machine: " + temp);
                        System.out.println(new StringBuilder().append("*************************************************************************************").append("\nThe client : ").append(ClITer.getID()).append(" has released the machine: ").append(temp).append("The Client has released the following Weights : [ SMALL_3KG = ").append(small).append(", MEDIUM_5KG = ").append(medium).append(", LARGE_10KG = ").append(large).append(" ]").toString());


                    }

                }
            });

        }
        executor.shutdown();
    }
}
