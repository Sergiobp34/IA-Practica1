package IA.ProbServers;

import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.ArrayList;
import java.util.Set;

public class ProbServersBoard {

    //* State data structure

    private ArrayList<ArrayList<Integer>> FileServer; // Els fitxers que conté cada servidor
    private ArrayList<ArrayList<Integer[]>> Peticions; //vector de servidors on un int diu quin es el servidor que la facilita
    private ArrayList<Integer> Temps; // Vector amb el temps total que triga un servidor a atendre les peticions que té assignades

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


        Peticions = new ArrayList<>(nserv);
        for (int i = 0; i < nserv; ++i) {
            ArrayList<Integer[]> ini = new ArrayList<>();
            Peticions.add(i, ini);
        }

        Temps = new ArrayList<>(nserv);
        for (int i = 0; i < nserv; ++i) {
            Temps.add(0);
        }

        for (int req = 0; req < requests.size(); ++req){
            boolean exit = false;
            for (int i = 0; i < nserv && !exit; ++i) {
                for (int j = 0; j < FileServer.get(i).size(); ++j) {
                    if (requests.getRequest(req)[1] == FileServer.get(i).get(j)) {
                        Integer[] requestVec = {requests.getRequest(req)[0], requests.getRequest(req)[1]};
                        // Assignar request a aquell servidor
                        Peticions.get(i).add(requestVec);
                        // Sumar el temps d'aquesta petició al total del servidor
                        Temps.set(i, Temps.get(i)+servers.tranmissionTime(i, requests.getRequest(req)[0]));
                        System.out.println("peticio " + req + " de fitxer " + FileServer.get(i).get(j) + " al servidor " + i);
                        exit = true;
                        break;
                    }
                }
            }
        }

        // Per imprimir el temps de cada servidor a l'estat inicial
        for (int i = 0; i < nserv; ++i) {
            System.out.println("Servidor " + i + ": temps " + Temps.get(i));
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