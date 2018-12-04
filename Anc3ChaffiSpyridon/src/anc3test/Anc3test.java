/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anc3test;

/**
 *
 * @author Spy
 */
public class Anc3test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Player p1 = new Player("Philippe");
        Player p2 = new Player("Khadija");
        Player p3 = new Player("spyridon");
        Player p4 = new Player("chaffi");
        Player p5 = new Player("lindsay");
        Player p6 = new Player("rodolphe");
        Match m = new Match(p1, p2, Match.RESULTS.DRAW);
        Match m2 = new Match(p2, p1, Match.RESULTS.DRAW);
        Match m3 = new Match(p1, p4, Match.RESULTS.DRAW);
        Match m4 = new Match(p4, p1, Match.RESULTS.DRAW);
        Match m5 = new Match(p6, p2, Match.RESULTS.DRAW);
        Match m6 = new Match(p3, p6, Match.RESULTS.DRAW);

        Tournament t = new Tournament("premier");
        t.addPlayer(p1);
        t.addPlayer(p2);
        t.addPlayer(p3);
        t.addPlayer(p4);
        t.addPlayer(p5);
        t.addPlayer(p6);

        t.addMatch(m);
        t.addMatch(m2);
        t.addMatch(m3);
        t.addMatch(m4);
        t.addMatch(m5);
        t.addMatch(m6);

        System.out.println(t.getSubscribersList() + "\n");

        System.out.println(t.getMatchList());
        //System.out.println(t.getPlayerList());
        System.out.println("********************************");
        System.out.println(t.getOpponentsList());// pour le moment la mem liste de subscribeList
        System.out.println("retourne la liste des adversaires sauf le jouer selectionner");
        t.soutOpponentsListWithoutThisPlayer(p6);

        System.out.println("************************");
        System.out.println("liste des jouer qui ont deja jouer avec player :" + p1.getFirstName());
        t.addMatchPlayed(p2);
        System.out.println(t.getOpponentInvalidList());
//    
        System.out.println(t.getMatchPlayed());
        System.out.println("liste des personnes qui on deja jouer avec avec le jouer choisi"+t.getOpponentInvalidList());
//        
//          System.out.println("liste des match");
//         System.out.println(t.getOpponentInvalidList());
//
//    }
System.out.println("*********************");
        System.out.println("la liste des jouer valide qui n'ont pas encore jouer contre: "+t.getopponentsValidList());

    }
}
