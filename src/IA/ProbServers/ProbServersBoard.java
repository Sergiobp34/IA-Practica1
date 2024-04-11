package IA.ProbServers;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.util.Pair;

import java.util.ArrayList;
import java.util.Set;

public class ProbServersBoard {

    //* State data structure

    private ArrayList<ArrayList<Integer>> FileServer;
// private ArrayList<Pair<Integer,Pair<Integer, Integer>>> Req; eliminat perque no es pot declarar tipus dins el Pair

    private ArrayList<ArrayList<Integer>> Requests;              //vector de servidors on un int diu quin es el servidor que la facilita

    private int [][] FSFiles;                                               //vector de FS de vectors amb els fitxers
    //private ArrayList<Pair<Integer,Integer>> Files;                         //NO SE SI CAL. vector de Pair<Files, num> on num es el numero de vegades que esta el fitxer en un FS

    private int [][] sol;           // vector solució dels FS amb la distribució dels fitxers

    /* Constructor */
    public ProbServersBoard(Servers servers, Requests requests, int nserv) {

        FileServer = new ArrayList<>(nserv);
        for (int i = 0; i < nserv; ++i) {
            ArrayList<Integer> ini = new ArrayList<>();
            FileServer.add(i, ini);
        }

        for (Integer i = 0; i < servers.size(); ++i) {
            Set<Integer> locations = servers.fileLocations(i);
            for (Integer location : locations) {                                //location es l'index del servidor
                //Imprimeix be però falta que ho afegeixi al vector d'arraylist
                FileServer.get(location).add(i);
                System.out.println("fitxer " + i + " a servidor " + location);
            }
        }


        Requests = new ArrayList<>(nserv);
        for (int i = 0; i < nserv; ++i) {
            ArrayList<Integer> ini = new ArrayList<>();
            Requests.add(i, ini);
        }
        for (int req = 0; req < requests.size(); ++req){
            boolean exit = false;
            for (int i = 0; i < nserv && !exit; ++i) {
                for (int j = 0; j < FileServer.get(i).size(); ++j) {
                    if (requests.getRequest(req)[1] == j) {
                        Requests.get(i).add(j);
                        exit = true;
                        break;
                    }
                }
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