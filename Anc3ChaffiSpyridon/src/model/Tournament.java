/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Spy
 */
public class Tournament {

    private final String name;
    private List<Player> subscribersList = new ArrayList<>();// modifier en hashSet peut etre.
    private Set<Match> matchList = new HashSet<>();
    private List<String> playersList = new ArrayList<>();// a peut etre virer.
    private List<String> opponentsList = new ArrayList<>();//liste des adversaire valide.
    private Set<String> opponentInvalidList = new HashSet<>();// liste des jouer a jarter de l'affochage de comboBox.
    private List<Match> matchPlayed = new ArrayList<>();// match deja jouer par le player
    private Set<String> opponentsValidList = new TreeSet<>();
    private Set<String> testJouerValide = new HashSet<>();//liste  teste de jouer qui sont encore valide 
   private List<String> ListMatchString = new ArrayList<>();
    public Tournament(String s) {
        name = s;
    }

    public Set<String> getopponentsValidList() {
        return opponentsValidList;
    }

    public String getName() {
        return name;
    }

    public Set<String> getTestJouerValide() {
        return testJouerValide;
    }

    public List<Player> getSubscribersList() {
        return subscribersList;
    }

    public Set<Match> getMatchList() {
        return matchList;
    }

    public Set<String> getOpponentInvalidList() {
        return opponentInvalidList;
    }

    public List<String> getPlayerList() {
        return playersList;
    }

    public List<String> getOpponentsList() {
        return opponentsList;
    }

    public boolean existMatch(Match t) {
        return matchList.contains(t);
    }

    public void addMatch(Match m) {
        this.matchList.add(m);
    }

    public List<Match> getMatchPlayed() {
        return matchPlayed;
    }

    public void addPlayer(Player p) {
        this.subscribersList.add(p);
        this.opponentsList.add(p.getFirstName());
    }

    public void soutPlayer(Player p) {
        this.playersList.add(p.getFirstName());
    }

    public List<String> getListMatchString() {
        return ListMatchString;
    }
    
//    return la liste des adversaire sauf Player p

    public void ConvertMatchList(){
       for(Match m:matchList){
           String res= m.getPlayer1()+ "->"+m.getPlayer1()+"="+ m.getResults();
         ListMatchString.add(res);
       }
    }
    public void soutOpponentsListWithoutThisPlayer(Player p) {
        for (int i = 0; i < this.opponentsList.size(); ++i) {
            if (!opponentsList.get(i).equals(p.getFirstName())) {
                //System.out.println(opponentsList.get(i));
                opponentsValidList.add(opponentsList.get(i));

            }
        }
        addMatchPlayed(p);
    }

// ajouter les match deja jouer par le player p dans matchPlayed.
    public void addMatchPlayed(Player p) {
        for (Match m : matchList) {
            if (m.getPlayer1() == p || m.getPlayer2() == p) {
                matchPlayed.add(m);
                addOpponentInvalidList(p, matchPlayed);// vas cree directement la liste jouer qui ont deja jouer contre player p ont lui pasant les matchs qu'il a jouer 

            }
        }
        ConvertMatchList();
    }

    // ajouter les adversaire de player p dans la liste opponentsListInvalid(les joueur qui n'ont deja jouer contre player p et recois aussi la liste des matchs deja jouer par player p)
    public void addOpponentInvalidList(Player p, List<Match> li) {
        for (Match m : li) {
            if (!m.getPlayer1().getFirstName().equals(p.getFirstName())) {
                opponentInvalidList.add(m.getPlayer1().getFirstName());

            }
            if (!m.getPlayer2().getFirstName().equals(p.getFirstName())) {
                opponentInvalidList.add(m.getPlayer2().getFirstName());

            }
        }
        addOppponentValidList(p);// vas cree directement la liste jouer qui ont pas encore  jouer contre player p. 
    }

    public void addOppponentValidList(Player selected) {
        for (Player s : subscribersList) {
            if (!opponentInvalidList.contains(s.getFirstName())) {
                //soutOpponentsListWithoutThisPlayer(s);
                // System.out.println(s.getName());
                testJouerValide.add(s.getFirstName());
            }
            if (testJouerValide.contains(selected.getFirstName())) {
                testJouerValide.remove(selected.getFirstName());
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }

}
