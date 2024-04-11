package IA.ProbServers;

import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.ArrayList;
import java.util.Set;

public class ProbServersBoard {

    //State data structure

    private ArrayList<ArrayList<Integer>> FileServer; // Els fitxers que conté cada servidor
    private ArrayList<ArrayList<Integer[]>> Peticions; //vector de servidors on un int diu quin es el servidor que facilita la peticio (tenint en compte el fitxer)
    private ArrayList<Integer> Temps; // Vector amb el temps total que triga un servidor a atendre les peticions que té assignades

    //Constructor
    public ProbServersBoard(Servers servers, Requests requests, int nserv) {

        //Inicialització de FileServer
        FileServer = new ArrayList<>(nserv);
        for (int i = 0; i < nserv; ++i) {
            ArrayList<Integer> ini = new ArrayList<>();
            FileServer.add(i, ini);
        }

        for (Integer i = 0; i < servers.size(); ++i) {
            Set<Integer> locations = servers.fileLocations(i);
            for (Integer location : locations) {                                //location es l'index del servidor
                FileServer.get(location).add(i);
                System.out.println("fitxer " + i + " a servidor " + location);
            }
        }

        //Inicialització de Peticions i Temps
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
    }


    //Operators

    public void transferPetition(Servers servers, Requests requests, int server1, int peticio1, int server2){
        //restar el temps de la peticio1 i eliminar  la peticio1 del server1
        Temps.set(server1, Temps.get(server1)-servers.tranmissionTime(server1, requests.getRequest(peticio1)[0]));
        FileServer.get(server1).remove(requests.getRequest(peticio1)[1]);

        //afegir la peticio1 i sumar el temps de la peticio1 al server2
        Temps.set(server2, Temps.get(server2)+servers.tranmissionTime(server2, requests.getRequest(peticio1)[0]));
        FileServer.get(server2).add(requests.getRequest(peticio1)[1]);
    }

    public void swapPetition(int server1, int petition1, int server2, int petition2){

    }



}