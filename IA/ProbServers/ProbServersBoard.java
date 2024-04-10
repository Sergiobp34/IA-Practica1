package IA.ProbServers;


public class ProbServersBoard {

    /* State data structure

     */

    private int [] board;

    /* Constructor */
    public ProbServersBoard(int []init) {

        board = new int[init.length];

        for (int i = 0; i< init.length; i++) {
            board[i] = init[i];
        }

    }

    /* vvvvv TO COMPLETE vvvvv */
    public void flip_it(int i){
        // flip the coins i and i + 1
    }

    /* Heuristic function1 */
    public double heuristic1(){
        // compute the number of coins out of place respect to solution
        return 0;
    }

    /* Heuristic function2 */
    public double heuristic2(){
        // compute the number of coins out of place respect to solution
        return 0;
    }

    /* Goal test */
    public boolean is_goal(){
        // compute if board = solution
        return false;
    }


}