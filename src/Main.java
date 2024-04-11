import IA.DistFS.Servers;
import IA.DistFS.Requests;

import IA.ProbServers.ProbServersBoard;
//import IA.ProbServers.ProbServersGoalTest;
//import IA.ProbServers.ProbServersHeuristicFunction;
//import IA.ProbServers.ProbServersSuccesorFunctionHC;


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
    }


//    Main anterior
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
//
//    private static void printInstrumentation(Properties properties) {
//        Iterator keys = properties.keySet().iterator();
//        while (keys.hasNext()) {
//            String key = (String) keys.next();
//            String property = properties.getProperty(key);
//            System.out.println(key + " : " + property);
//        }
//
//    }
//
//    private static void printActions(List actions) {
//        for (int i = 0; i < actions.size(); i++) {
//            String action = (String) actions.get(i);
//            System.out.println(action);
//        }
//    }
}