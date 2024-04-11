package IA.ProbServers.hide;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import IA.ProbServers.ProbServersBoard;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

// Inspirat en la classe de probTSP
public class ProbServersDemo {
    public static void main(String[] args){
        ProbServersBoard SP=new ProbServersBoard(new int[8]); // A canviar
        SPHillClimbingSearch(SP);
        SPSimulatedAnnealingSearch(SP);
    }

    private static void SPHillClimbingSearch(ProbServersBoard SP) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem =  new Problem(SP,new ProbServersSuccesorFunctionHC(), new ProbServersGoalTest(),new ProbServersHeuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void SPSimulatedAnnealingSearch(ProbServersBoard SP) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            // Atenció! SA fa servir una altra Successor Function que seria ProbServersSuccesorFunctionSA en comptes de ProbServersSuccesorFunction
            Problem problem =  new Problem(SP,new ProbServersSuccesorFunctionSA(), new ProbServersGoalTest(),new ProbServersHeuristicFunction());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001); // Falta posar els paràmetres que toca
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
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