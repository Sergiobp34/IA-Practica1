package src.ProbServers;


import aima.search.framework.HeuristicFunction;

public class ProbServersHeuristicFunction2 implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((ProbServersBoard) n).heuristic2();
    }
}