/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anc3test;

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
    private Set<Match> matchList = new TreeSet<>();
    private List<String> playersList = new ArrayList<>();// a peut etre virer.
    private List<String> opponentsList = new ArrayList<>();//liste des adversaire valide.
    private Set<String> opponentInvalidList = new HashSet<>();// liste des jouer a jarter de l'affochage de comboBox.
    private List<Match> matchPlayed = new ArrayList<>();// match deja jouer par le player
    private Set<String> opponentsValidList = new TreeSet<>();

    public Tournament(String s) {
        name = s;
    }
    
    
    public Set<String> getopponentsValidList(){
      return opponentsValidList;
    }
    public String getFirstName() {
        return name;
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
//    return la liste des adv sauf Player p

    public void soutOpponentsListWithoutThisPlayer(Player p) {
        for (int i = 0; i < this.opponentsList.size(); ++i) {
            if (!opponentsList.get(i).equals(p.getFirstName())) {
                System.out.println(opponentsList.get(i));
            }
        }
    }

// ajouter les match deja jouer par le player p dans matchPlayed.
    public void addMatchPlayed(Player p) {
        for (Match m : matchList) {
            if (m.getPlayer1() == p || m.getPlayer2() == p) {
                matchPlayed.add(m);
                addOpponentInvalidList(p, matchPlayed);
                
            }
        }
    }

    // ajouter les adversaire de player p dans la liste opponentsListInvalid
    public void addOpponentInvalidList(Player p, List<Match> li) {
        for (Match m : li) {
            if (!m.getPlayer1().getFirstName().equals(p.getFirstName())) {
                opponentInvalidList.add(m.getPlayer1().getFirstName());
                
            }
            if (!m.getPlayer2().getFirstName().equals(p.getFirstName())) {
                opponentInvalidList.add(m.getPlayer2().getFirstName());
                
            }
        }
        addOppponentValidList();
    }
    
    public void addOppponentValidList() {
        for (Player s : subscribersList) {
            for (String t : opponentInvalidList) {
                if (s.getFirstName().equals(t)) {
                    this.opponentsValidList.add(s.getFirstName());
                    
                }
            }
        }
        
    }
    
    @Override
    public String toString() {
        return "Tournament{" + "name: " + name + ", subscribersList: " + subscribersList + ", matchList: " + matchList + '}';
    }
    
}
