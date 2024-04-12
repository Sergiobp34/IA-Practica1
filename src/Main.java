import IA.DistFS.Servers;
import IA.DistFS.Requests;

import IA.ProbServers.ProbServersBoard;
import IA.ProbServers.ProbServersGoalTest;
import IA.ProbServers.ProbServersHeuristicFunction1;
import IA.ProbServers.ProbServersSuccesorFunctionHC;

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
        System.out.println("Hello world!");
        initializationProblem();
    }

    private static void initializationProblem() throws Servers.WrongParametersException {
        int nserv = 50;
        int nrep = 5;
        int seed = 1234;
        Servers servers = new Servers(nserv, nrep, seed);

        int users = 200;
        int nrequests = 5;
        Requests requests = new Requests(users, nrequests, seed);

        System.out.println("servers size: " + servers.size());
        System.out.println("requests size: " + requests.size());

        ProbServersBoard serversBoard = new ProbServersBoard(servers, requests, nserv);
        ServersHillClimbing(serversBoard);
    }

    private static void ServersHillClimbing(ProbServersBoard board) {
        System.out.println("\nHill Climbing -->");
        try {
            Problem problem = new Problem(board,
                    new ProbServersSuccesorFunctionHC(),
                    new ProbServersGoalTest(),
                    new ProbServersHeuristicFunction1());
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

// El simulated annealing que teniem a ProbServersDemo. Comentat pq no doni errors de compilació.
//    private static void SPSimulatedAnnealingSearch(ProbServersBoard SP) {
//        System.out.println("\nTSP Simulated Annealing  -->");
//        try {
//            // Atenció! SA fa servir una altra Successor Function que seria ProbServersSuccesorFunctionSA en comptes de ProbServersSuccesorFunction
//            Problem problem =  new Problem(SP,new ProbServersSuccesorFunctionSA(), new ProbServersGoalTest(),new ProbServersHeuristicFunction());
//            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001); // Falta posar els paràmetres que toca
//            //search.traceOn();
//            SearchAgent agent = new SearchAgent(problem,search);
//
//            System.out.println();
//            printActions(agent.getActions());
//            printInstrumentation(agent.getInstrumentation());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

//    Main anterior, guardat per si fa falta
//    public static void main(String[] args) throws Exception{
//        /**
//         *  For a problem to be solvable:
//         *    count(0,prob) % 2 == count(0,sol) %2
//         */
//        int [] prob = new int []{1 ,0, 1, 1, 0};
//
//        ProbServersBoard board = new ProbServersBoard(prob);
//
//        // Create the Problem object
//        Problem p = new  Problem(board,
//                new ProbServersSuccesorFunctionHC(),
//                new ProbServersGoalTest(),
//                new ProbServersHeuristicFunction());
//
//        // Instantiate the search algorithm
//        // AStarSearch(new GraphSearch()) or IterativeDeepeningAStarSearch()
//        Search alg = new AStarSearch(new GraphSearch());
//
//        // Instantiate the SearchAgent object
//        SearchAgent agent = new SearchAgent(p, alg);
//
//        // We print the results of the search
//        System.out.println();
//        printActions(agent.getActions());
//        printInstrumentation(agent.getInstrumentation());
//
//        // You can access also to the goal state using the
//        // method getGoalState of class Search
//
//    }