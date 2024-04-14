package IA.ProbServers;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProbServersSuccesorFunctionSA implements SuccessorFunction {
    static private ProbServersHeuristicFunction1 h1;        //per debugging
    static private ProbServersHeuristicFunction2 h2;        //per debugging

    static private Integer operador;

    //Constructora amb les inicialitzacions
    public ProbServersSuccesorFunctionSA() {
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

        public List getSuccessors(Object aState) {
            ArrayList retval = new ArrayList();

            ProbServersBoard estat = (ProbServersBoard) aState;
            ProbServersBoard clone = estat.clone();

            Random rand = new Random();
            String s;

            if(rand.nextInt(2) == 0){       //Fem transfer

                int s1, s2; s2 = -1;

                Integer[] p1;
                s1 = rand.nextInt(estat.getPeticions().size());

                while(estat.getPeticions().get(s1).isEmpty()){
                    s1 = rand.nextInt(estat.getPeticions().size());
                }

                int randP = rand.nextInt(estat.getPeticions().get(s1).size());
                p1 = estat.getPeticions().get(s1).get(randP);

                Set<Integer> candidatsS2 = estat.getServers().fileLocations(p1[1]);
                int aux = rand.nextInt(candidatsS2.size());

                Integer current=0;
                for (Integer location : candidatsS2) {
                    if (current.equals(aux)){            //aux funciona com a Index
                        s2 = location;                  //Assignem el servidor a s2
                        break;
                    }
                    ++current;
                }

                while(s1 == s2){
                    aux = rand.nextInt(candidatsS2.size());

                    current=0;
                    for (Integer location : candidatsS2) {
                        if (current.equals(aux)){           //aux funciona com a Index
                            s2 = location;                  //Assignem el servidor a s2
                            break;
                        }
                        ++current;
                    }
                }

                clone.transferPetition(s1, p1, s2);
                s = "TRANSFER";
            }
            else{                                 //Fem SWAP



                int s1, s2; s2 = 0;

                Integer[] p1;
                s1 = rand.nextInt(estat.getPeticions().size());

                while(estat.getPeticions().get(s1).isEmpty()){
                    s1 = rand.nextInt(estat.getPeticions().size());
                }

                int randP = rand.nextInt(estat.getPeticions().get(s1).size());
                p1 = estat.getPeticions().get(s1).get(randP);

                Set<Integer> candidatsS2 = estat.getServers().fileLocations(p1[1]);
                int aux = rand.nextInt(candidatsS2.size());

                Integer current=0;
                for (Integer location : candidatsS2) {
                    if (current.equals(aux)){            //aux funciona com a Index
                        s2 = location;                  //Assignem el servidor a s2
                        break;
                    }
                    ++current;
                }

                while(s1 == s2){
                    aux = rand.nextInt(candidatsS2.size());

                    current=0;
                    for (Integer location : candidatsS2) {
                        if (current.equals(aux)){           //aux funciona com a Index
                            s2 = location;                  //Assignem el servidor a s2
                            break;
                        }
                        ++current;
                    }
                }

                clone.transferPetition(s1, p1, s2);

                if(estat.getPeticions().get(s2).isEmpty()){
                    s = "TRANSFER";
                }
                else {
                    int randP2 = rand.nextInt(estat.getPeticions().get(s2).size());
                    Integer[] p2 = estat.getPeticions().get(s2).get(randP2);
                    clone.transferPetition(s2, p2, s1);

                    s = "SWAP";
                }
            }

            retval.add(new Successor(s,clone));
            return retval;
        }

    }
