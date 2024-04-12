package IA.ProbServers;

import IA.ProbServers.ProbServersBoard;
import aima.search.framework.GoalTest;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbServersGoalTest implements GoalTest {

    public boolean isGoalState(Object state){

        return((ProbServersBoard) state).is_goal();
    }
}