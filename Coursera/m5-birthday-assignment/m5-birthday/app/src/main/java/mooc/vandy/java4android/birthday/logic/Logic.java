package mooc.vandy.java4android.birthday.logic;

import java.util.Random;

import mooc.vandy.java4android.birthday.ui.OutputInterface;

/**
 * This is where the logic of this App is centralized for this assignment.
 * <p>
 * The assignments are designed this way to simplify your early Android interactions.
 * Designing the assignments this way allows you to first learn key 'Java' features without
 * having to beforehand learn the complexities of Android.
 *
 */
public class Logic
        implements LogicInterface {
    /**
     * This is a String to be used in Logging (if/when you decide you
     * need it for debugging).
     */
    public static final String TAG =
            Logic.class.getName();

    /**
     * This is the variable that stores our OutputInterface instance.
     * <p>
     * This is how we will interact with the User Interface
     * [MainActivity.java].
     * <p>
     * It is called 'mOut' because it is where we 'out-put' our
     * results. (It is also the 'in-put' from where we get values
     * from, but it only needs 1 name, and 'mOut' is good enough).
     */
    OutputInterface mOut;

    /**
     * This is the constructor of this class.
     * <p>
     * It assigns the passed in [MainActivity] instance
     * (which implements [OutputInterface]) to 'out'
     */
    public Logic(OutputInterface out){
        mOut = out;
    }

    /**
     * This is the method that will (eventually) get called when the
     * on-screen button labelled 'Process...' is pressed.
     */
    public void process() {
        int groupSize = mOut.getSize();
        int simulationCount = mOut.getCount();

        if (groupSize < 2 || groupSize > 365) {
            mOut.makeAlertToast("Group Size must be in the range 2-365.");
            return;
        }
        if (simulationCount <= 0) {
            mOut.makeAlertToast("Simulation Count must be positive.");
            return;
        }

        double percent = calculate(groupSize, simulationCount);

        // report results
        mOut.println("For a group of " + groupSize + " people, the percentage");
        mOut.println("of times that two people share the same birthday is");
        mOut.println(String.format("%.2f%% of the time.", percent));

    }

    /**
     * This is the method that actually does the calculations.
     * <p>
     * We provide you this method that way we can test it with unit testing.
     */
    // size -- the size of the group of people
    // count -- the count of the number of simulations to run
    public double calculate(int size, int count) {

        // the number of runs that had two birthdays on the same day
        int sameBdayCount = 0;

        for (int i=1; i<=count; i++) { // run the simulations for the specified number of times

            // create an array corresponding to each day of a year
            int[] days = new int[365]; // each element gets initialized to 0 value here

            // create a new Random object for each simulation run
            // seed the Random object with the number of the run (=i)
            Random rand = new Random(i);

            for (int j=1; j<=size; j++) { // generate a random birthday for each person in the group
                int bday = rand.nextInt(365); // generate a random integer in the range 0-364
                if (days[bday] == 0) { // a person of the same birthday does not exist yet
                    days[bday] = 1;
                } else {
                    // two people have the same birthday
                    sameBdayCount++;
                    break; // stop the current simulation and start the next simulation
                }
            }
        }

        // return the percent of the simulations that had two people with the same birthday
        return (double)sameBdayCount/count*100;

    }

}