package IA.ProbServers.hide;

/**
 * Created by bejar on 17/01/17.
 */

import IA.ProbServers.ProbServersBoard;
import aima.search.framework.HeuristicFunction;

public class ProbServersHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((ProbServersBoard) n).heuristic1();
    }
}