package IA.ProbServers;

import aima.search.framework.HeuristicFunction;

public class ProbServersHeuristicFunction2 implements HeuristicFunction{

    public double getHeuristicValue(Object o) {
        return ((ProbServersBoard) o).heuristic2();
    }
}
