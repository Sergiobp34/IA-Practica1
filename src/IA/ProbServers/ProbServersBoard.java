package IA.ProbServers;

import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ProbServersBoard{

    //State data structure

    private ArrayList<ArrayList<Integer>> FileServer; // Els fitxers que conté cada servidor
    private ArrayList<ArrayList<Integer[]>> Peticions; //vector de servidors on un int diu quin es el servidor que facilita la peticio (tenint en compte el fitxer). Primer element és usuari, segon és fitxer.

    //Hauriem de posar el Temps com un Map(HashMap a Java) que ordeni pel temps(per que l'heuristic sigui eficient)
    private ArrayList<Integer> Temps; // Vector amb el temps total que triga un servidor a atendre les peticions que té assignades

    private int numFitxers;
    private int numServers;
    private int numPeticions;


    private Servers servs;        //Per fer clone
    private Requests requ;        //Per fer clone
    private int opcioInicPriv;



    //Constructor
    public ProbServersBoard(Servers servers, Requests requests, int nserv, int opcioInic) {

        servs = servers;
        requ = requests;
        opcioInicPriv = opcioInic;

        numServers = nserv;
        numFitxers = servers.size();
        numPeticions = requests.size();

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
                //System.out.println("fitxer " + i + " a servidor " + location);
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

        if(opcioInic != -1) {
            if (opcioInic == 0) iniciRandom();
            else if (opcioInic == 1) iniciMillor1();
            else if (opcioInic == 2) iniciMillor2();
            else System.out.println("Paràmetre dolent");
        }

    }

    // Inicialització
    public void iniciRandom(){
        for (int req = 0; req < requ.size(); ++req){
            Set<Integer> disp = servs.fileLocations(requ.getRequest(req)[1]);
            Random randomNumbers = new Random();
            Integer ind = randomNumbers.nextInt(disp.size());
            Integer current=0;
            for (Integer location : disp) {
                if (current.equals(ind)){
                    Peticions.get(location).add(new Integer[]{requ.getRequest(req)[0], requ.getRequest(req)[1]});
                    Temps.set(location, Temps.get(location)+servs.tranmissionTime(location, requ.getRequest(req)[0]));
                    break;
                }
                ++current;
            }
        }
    }

    public void iniciMillor1(){
        for (int req = 0; req < requ.size(); ++req){
            boolean exit = false;
            for (int i = 0; i < numServers && !exit; ++i) { // Aquí servs.size() abans era nserv
                for (int j = 0; j < FileServer.get(i).size(); ++j) {
                    if (requ.getRequest(req)[1] == FileServer.get(i).get(j)) {
                        Integer[] requestVec = {requ.getRequest(req)[0], requ.getRequest(req)[1]};
                        // Assignar request a aquell servidor
                        Peticions.get(i).add(requestVec);
                        // Sumar el temps d'aquesta petició al total del servidor
                        Temps.set(i, Temps.get(i)+servs.tranmissionTime(i, requ.getRequest(req)[0]));
                        //System.out.println("peticio " + req + " de fitxer " + FileServer.get(i).get(j) + " al servidor " + i);
                        exit = true;
                        break;
                    }
                }
            }
        }
    }

    public void iniciMillor2(){
        for (int req = 0; req < requ.size(); ++req){
            boolean exit = false;
            // lloc on guardar el servidor més ràpid
            int millorTemps=Integer.MAX_VALUE;
            int millorServ=0;
            for (int i = 0; i < numServers && !exit; ++i) { // Aquí servs.size() abans era nserv
                for (int j = 0; j < FileServer.get(i).size(); ++j) {
                    if (requ.getRequest(req)[1] == FileServer.get(i).get(j)) {

                        // si és més ràpid passa a ser el més ràpid
                        if (servs.tranmissionTime(i, requ.getRequest(req)[0])<millorTemps){
                            millorTemps=servs.tranmissionTime(i, requ.getRequest(req)[0]);
                            millorServ=i;
                        }

                        exit = true;
                        break;
                    }
                }
            }
            // assignar request al servidor més ràpid
            Integer[] requestVec = {requ.getRequest(req)[0], requ.getRequest(req)[1]};
            Peticions.get(millorServ).add(requestVec);
            Temps.set(millorServ, Temps.get(millorServ)+servs.tranmissionTime(millorServ, requ.getRequest(req)[0]));
        }
    }

    //Operators

    public void transferPetition(int server1, Integer[] peticio1, int server2){
        //restar el temps de la peticio1 i eliminar la peticio1 del server1
        Temps.set(server1, Temps.get(server1)-servs.tranmissionTime(server1, peticio1[0]));
        Peticions.get(server1).remove(peticio1);

        //afegir la peticio1 i sumar el temps de la peticio1 al server2
        Temps.set(server2, Temps.get(server2)+servs.tranmissionTime(server2, peticio1[0]));
        Peticions.get(server2).add(peticio1);
    }

    public void swapPetition(int server1, Integer[] petition1, int server2, Integer[] petition2){
        transferPetition(server1, petition1, server2);
        transferPetition(server2, petition2, server1);
    }


    //Heuristic1
    public int heuristic1(){
        int t = 0;
        for (Integer temp : Temps) {
            if (temp > t) t = temp;
        }
        return t;
    }

    //Heuristic2
    public int heuristic2(){
        int sum = 0;

        //suma tots els temps de transmissio
        for (Integer temp : Temps) sum += temp;

        //penalitzacio1: rang m que ha d'estar entre [m-1,m+1]        sumem 30000ms per servidor que no estigui al rang

        int numPenal = 0;
        int[] mLim = new int[2];
        mLim[0] = (numPeticions/numServers)-1;
        mLim[1] = (numPeticions/numServers)+1;

        for (ArrayList<Integer[]> server : Peticions) {
            if (server.size() < mLim[0]){
                numPenal-=server.size()-(numPeticions/numServers);
            }
            if (server.size() > mLim[1]){
                numPenal+=server.size()-(numPeticions/numServers);
            }
        }

        int penalitzacio1 = numPenal * 30000;

        //penalitzacio2: diferencia de temps entre mes gran i mes petit       sumem 70000ms*(diferencia/1000)
        int min= Integer.MAX_VALUE;
        int max=0;
        for (Integer temp : Temps) {
            if (temp > max) max = temp;
            if(temp < min) min = temp;
        }
        int diff= max-min;
        int penalitzacio2 = 70000 * (diff/1000);

        return sum + penalitzacio1 +penalitzacio2;
    }

    public boolean is_goal(){
        // Ara retorno false per que pugui executar sense errors, però s'ha de plantejar com fer-ho
        return false;
    }

    public void imprimirBoard(){
        for (int i = 0; i < numServers; ++i) {
            System.out.println("Servidor " + i + ", temps total: " + Temps.get(i));
            for (int j = 0; j < Peticions.get(i).size(); ++j){
                System.out.println("    Servidor " + i + " atèn la petició [usuari "+ Peticions.get(i).get(j)[0] +", fitxer "+ Peticions.get(i).get(j)[1] +"]");
            }
        }
    }

    public Integer getMaxTime(){
        int max=0;
        for(int i=0; i< Temps.size(); ++i){
            if (Temps.get(i)>max) max=Temps.get(i);
        }
        return max;
    }




    //Funcions auxiliars

    public ArrayList<ArrayList<Integer>> getFileServer(){ return FileServer; }

    public ArrayList<ArrayList<Integer[]>> getPeticions(){ return Peticions; }

    public ArrayList<Integer> getTemps(){ return Temps; }

    public Servers getServers(){ return servs; }

    @Override
    public ProbServersBoard clone(){
        // Crear una nueva instancia de ProbServersBoard
        ProbServersBoard clone = new ProbServersBoard(this.servs, this.requ, this.numServers, -1);

        // Copiar los valores de los atributos del objeto original a la nueva instancia
        for (int i = 0; i < this.FileServer.size(); ++i) {
            clone.FileServer.set(i, new ArrayList<>(this.FileServer.get(i)));
        }

        for (int i = 0; i < this.Peticions.size(); ++i) {
            clone.Peticions.set(i, new ArrayList<>(this.Peticions.get(i)));
        }

        clone.Temps = new ArrayList<>(this.Temps);

        // Devolver la nueva instancia
        return clone;
    }



}