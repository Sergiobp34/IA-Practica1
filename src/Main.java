import IA.DistFS.Servers;
import IA.DistFS.Requests;

import IA.ProbServers.*;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws Servers.WrongParametersException {

        // Per defecte fer servir els paràmetres de l'experiment 1. Executant amb -e int, indiques l'experiment que vols executar, i et modifica els paràmetres si cal.
        int nserv = 10;
        int nrep = 5;
        int seed = 1234;
        int users = 50;
        int nrequests = 3;
        int exp = 1;


        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-e":
                    try {
                        exp = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println(args[i] + "is not an integer.");
                        System.exit(0);
                    }
                    break;
            }

            if (exp == 1) {
                // Experiment 1: Provar diversos conjunts d'operadors
                // Executar amb un conjunt d'operadors
                Servers servers = new Servers(nserv, nrep, seed);
                Requests requests = new Requests(users, nrequests, seed);
                System.out.println("servers size: " + servers.size());
                System.out.println("requests size: " + requests.size());
                ProbServersBoard serversBoard = new ProbServersBoard(servers, requests, nserv, 1);

                // Executar amb només transfer
                ServersHillClimbing(serversBoard, 0, 1);

                // Executar amb només swap
                ServersHillClimbing(serversBoard, 1, 1);

                // Executar amb els dos operadors
                ServersHillClimbing(serversBoard, 2, 1);

            } else if (exp == 2) {
                // Experiment 2: Provar diferents generacions de l'estat inicial. Mateixa configuració que l'experiment 1, però amb el conjunt d'operacions que ha donat millor resultat

                Servers servers = new Servers(nserv, nrep, seed);
                Requests requests = new Requests(users, nrequests, seed);
                System.out.println("servers size: " + servers.size());
                System.out.println("requests size: " + requests.size());

                // Generació d'estat inicial aleatòria
                ProbServersBoard serversBoard = new ProbServersBoard(servers, requests, nserv, 0);
                ServersHillClimbing(serversBoard, 2, 1);

                // Generació d'estat inicial millorada 1
                ProbServersBoard serversBoard2 = new ProbServersBoard(servers, requests, nserv, 1);
                ServersHillClimbing(serversBoard2, 2, 1);

                return;
            } else if (exp == 3) {
                // Experiment 3: Amb els paràmetres vencedors de l'experiment 1 i 2, provar diferents paràmetres per Simulated Annealing.

                Servers servers = new Servers(nserv, nrep, seed);
                Requests requests = new Requests(users, nrequests, seed);
                System.out.println("servers size: " + servers.size());
                System.out.println("requests size: " + requests.size());
                ProbServersBoard serversBoard = new ProbServersBoard(servers, requests, nserv, 1);

                SPSimulatedAnnealingSearch(serversBoard);

                return;
            } else if (exp == 4) {
                // Experiment 4: Fent servir Hill Climbing i les millors opcions trobades, anar augmentant només el nombre d'usuaris i el nombre de servidor, i observar com varia el temps d'execució.

                // Augmentar usuaris
                nserv = 50;
                nrep = 5;
                users = 100;
                nrequests = 3;
                // Provar diferents nombres d'iteracions, 5 per exemple
                for (int iter =0; iter<10; ++iter){
                    // Fer board
                    // Fer HC
                    // Incrementar paràmetres
                    users+=100;
                }

                // Augmentar servidors
                nserv = 50;
                nrep = 5;
                users = 200;
                nrequests = 3;
                // Provar diferents nombres d'iteracions, 5 per exemple
                for (int iter =0; iter<10; ++iter){
                    // Fer board
                    // Fer HC
                    // Incrementar paràmetres
                    nserv+=50;
                }
                return;
            } else if (exp == 5) {
                // Experiment 5: Fent servir Hill Climbing diferencia entre el temps total de transmissió i el temps per trobar la solució.
                // I experimentar amb les penalitzacions del h2()



            } else if (exp == 6) {
                // Experiment 6: Fent servir Simmulated annealing i les mateixes heurístiques que exp. 5 entre el temps total de
                // transmissió i el temps per trobar la solució.



            } else if (exp == 7) {
                // Experiment 7: Fent servir HC anar augmentant les replicacions de 5 en 5, desde 5 fins 25.

            }
        }
    }

    private static void ServersHillClimbing (ProbServersBoard board, Integer op, Integer he) {
        System.out.println("\nHill Climbing -->");
        try {
            Problem problem;
            if (he==1) {
                problem = new Problem(board,
                        new ProbServersSuccesorFunctionHC(op),
                        new ProbServersGoalTest(),
                        new ProbServersHeuristicFunction1());
            } else if(he==2){
                problem = new Problem(board,
                        new ProbServersSuccesorFunctionHC(op),
                        new ProbServersGoalTest(),
                        new ProbServersHeuristicFunction2());
            } else { return; }
            Search search = new HillClimbingSearch();
            SearchAgent searchAgent = new SearchAgent(problem, search);

            //Imprimir estat
            ProbServersBoard nouBoard = (ProbServersBoard) search.getGoalState();
            nouBoard.imprimirBoard(); //Fer print board d'alguna manera

            System.out.println();
            //Imprimir dades searchAgent
            printActions(searchAgent.getActions());
            printInstrumentation(searchAgent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void SPSimulatedAnnealingSearch(ProbServersBoard SP) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            // Atenció! SA fa servir una altra Successor Function que seria ProbServersSuccesorFunctionSA en comptes de ProbServersSuccesorFunction
            Problem problem =  new Problem(SP,new ProbServersSuccesorFunctionSA(), new ProbServersGoalTest(),new ProbServersHeuristicFunction1());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001); // Falta posar els paràmetres que toca
            search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);

            //Imprimir estat
            ProbServersBoard nouBoard = (ProbServersBoard) search.getGoalState();
            nouBoard.imprimirBoard(); //Fer print board d'alguna manera

            System.out.println();
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
