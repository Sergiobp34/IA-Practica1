package IA.ProbServers;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;


public class ProbServersSuccesorFunctionHC implements SuccessorFunction{

    static private ProbServersHeuristicFunction1 h1;        //per debugging
    static private ProbServersHeuristicFunction2 h2;        //per debugging

    static private Integer operador;

    //Constructora amb les inicialitzacions
    public ProbServersSuccesorFunctionHC(Integer op) {
        operador=op;
        if (h1 == null)
            h1 = new ProbServersHeuristicFunction1();       //per debugging

        if (h2 == null)
            h2 = new ProbServersHeuristicFunction2();       //per debugging
    }

    //Funcio per fer debugger
    private Successor genSuccessor(String action, ProbServersBoard state) {
        return new Successor(action, state);
        // expensive version for debugging.         NO SE QUE ES, HO DEIXO PER SI DE CAS, ESTA INSPIRAT EN UN TREBALL DE GITHUB
		/*
		double v = TSPHF.getHeuristicValue(state);
		String S = action + " Coste("+String.valueOf(v)+")->" + state.toString();
		return new Successor(S, state);
		*/
    }


    public List<Successor> getSuccessors(Object state){
        ArrayList<Successor> ret = new ArrayList();
        ProbServersBoard estat = (ProbServersBoard) state;      //Estat actual

        if(operador==0||operador==2) {
            //crear successors TRANSFER
            for (int s1 = 0; s1 < estat.getFileServer().size(); ++s1) {
                for (int p = 0; p < estat.getPeticions().get(s1).size(); ++p) {
                    for (int s2 = 0; s2 < estat.getFileServer().size(); ++s2) {
                        for (int d = 0; d < estat.getFileServer().get(s2).size(); ++d) {
                            // No pot ser mateix servidor i el servidor ha de tindre disponible aquell fitxer
                            if (s2 != s1 && estat.getFileServer().get(s2).get(d).equals(estat.getPeticions().get(s1).get(p)[1])) {
                                ProbServersBoard clone = estat.clone();
                                clone.transferPetition(s1, estat.getPeticions().get(s1).get(p), s2);
                                ret.add(genSuccessor("TRANSFER", clone));
                            }
                        }
                    }
                }
            }
        }

        if (operador==1||operador==2) {
            //crear successors SWAP
            for (int s1 = 0; s1 < estat.getFileServer().size(); ++s1) {
                for (int p1 = 0; p1 < estat.getPeticions().get(s1).size(); ++p1) {
                    for (int s2 = s1 + 1; s2 < estat.getFileServer().size(); ++s2) {        //Fem s2= s1+1 per evitar fer swaps innecessaris
                        for (int p2 = 0; p2 < estat.getPeticions().get(s2).size(); ++p2) {
                            ProbServersBoard clone = estat.clone();
                            clone.swapPetition(s1, estat.getPeticions().get(s1).get(p1), s2, estat.getPeticions().get(s2).get(p2));
                            ret.add(genSuccessor("SWAP", clone));
                        }
                    }
                }
            }
        }

        return ret;

    }

}