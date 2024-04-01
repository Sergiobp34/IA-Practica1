package IA.ProbServers;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ProbServersHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((ProbServersBoard) n).heuristic();
    }
}