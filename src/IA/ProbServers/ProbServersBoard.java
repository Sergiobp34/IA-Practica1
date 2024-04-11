package IA.ProbServers;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.util.Pair;

import java.util.ArrayList;
import java.util.Set;

public class ProbServersBoard {

    /* State data structure

     */
    private ArrayList<Integer>[] FS;
// private ArrayList<Pair<Integer,Pair<Integer, Integer>>> Req; eliminat perque no es pot declarar tipus dins el Pair
    private ArrayList<Pair> Req;            //vector[Pair<Pair<usuari,fitxer>,time>] vector de peticions. time es el temps a calcular
    private int [][] FSFiles;                                               //vector de FS de vectors amb els fitxers
    //private ArrayList<Pair<Integer,Integer>> Files;                         //NO SE SI CAL. vector de Pair<Files, num> on num es el numero de vegades que esta el fitxer en un FS
    private int [][] sol;           // vector solució dels FS amb la distribució dels fitxers

    /* Constructor */
    public ProbServersBoard(Servers servers, Requests requests, int nserv) {

        FS = new ArrayList[nserv];

        for (Integer i=0; i< servers.size(); ++i){
            Set<Integer> locations = servers.fileLocations(i);
            for (Integer location : locations) {
                //Imprimeix be però falta que ho afegeixi al vector d'arraylist
                //FS[location].add(i);
                System.out.println("fitxer "+i+ " a servidor "+location);
            }
        }

        //board = new int[init.length];

//        for (int i = 0; i< init.length; i++) {
//           // board[i] = init[i];
//        }

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