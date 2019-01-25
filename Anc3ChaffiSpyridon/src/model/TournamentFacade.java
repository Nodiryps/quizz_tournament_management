/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Spy
 */
public class TournamentFacade extends Observable {

    private List<Tournament> tournamentList = new ArrayList<>();
    private int indexTournament;
    private Player actual;
    private Match selectedMatch;
    private int indexMatch;

    public TournamentFacade() {
        initData();
    }

    public Tournament getTournament() {
        return tournamentList.get(indexTournament);
    }

    public int getIndexTournament() {
        return indexTournament;
    }

    public Player getActualPlayer() {
        return actual;
    }

    public void setIndexTournament(int indexMatch) {
        this.indexTournament = indexMatch;
    }

    public void setPlayer(Player player) {
        this.actual = player;
    }

    public void setIndexSelectedMatch(Match m, int index) {
        this.indexMatch = index;
        this.selectedMatch = m;
    }

    public void createNewMatch(Player p1, Player p2, RESULTS res) {
        Match m = new Match(p1, p2, res);
        this.getTournament().addMatch(m);
    }

    public Match getSelectedMatch() {
        return selectedMatch;
    }

    public void removeMatch() {
        if (indexMatch == 0) {
            this.getTournament().pollFirstMatchList();
        } else {
            this.getTournament().getMatchList().remove(selectedMatch);
        }
    }

    // ajouter les match deja jouer par le player p dans matchPlayed.
    public List<Match> addMatchPlayed() {
        List<Match> matchPlayed = new ArrayList<>();
        for (Match m : this.getTournament().getMatchList()) {
            if (m.getPlayer1() == actual || m.getPlayer2() == actual) {
                matchPlayed.add(m);
            }
        }
        return matchPlayed;
    }

    // ajouter les adversaire de player p dans la liste opponentsListInvalid(les joueur qui n'ont deja jouer contre player p et recois aussi la liste des matchs deja jouer par player p)
    public List<Player> addOpponentInvalidList() {
        List<Player> playerInvalid = new ArrayList<>();
        for (Match m : addMatchPlayed()) {
            if (!m.getPlayer1().equals(actual)) {
                playerInvalid.add(m.getPlayer1());
            }
            if (!m.getPlayer2().equals(actual)) {
                playerInvalid.add(m.getPlayer2());
            }
        }
        return playerInvalid;
    }

    public List<Player> addOppponentValidList() {
        List<Player> playerValid = new ArrayList<>();
        for (Player s : this.getTournament().getSubscribersList()) {
            if (!addOpponentInvalidList().contains(s) && !s.equals(actual)) {
                playerValid.add(s);
            }
        }
        return playerValid;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public List<Player> getSubscrib() {
        return this.getTournament().getSubscribersList();
    }

    public Set<Match> getMatchList() {
        return this.getTournament().getMatchList();
    }

    public void initData() {
        List<String> listPlayersName = new ArrayList<>();
        List<Player> listPlayers = new ArrayList<>();
        List<Match> listMatches = new ArrayList<>();

        addPlayersNameTo(listPlayersName);
        addPlayersTo(listPlayers, listPlayersName);
        addMatchesTo(listMatches, listPlayers);

        Tournament t1 = new Tournament("EA Sports Cup");
        Tournament t2 = new Tournament("Quiditch WorldCup");

        addIntoTournament(t1, t2, listPlayers, listMatches);
        addTournamentToTournList(t1, t2);
    }

    private void addPlayersNameTo(List<String> list) {
        list.add("Philippine");
        list.add("Khadija");
        list.add("Spyridon");
        list.add("Chaffi");
        list.add("Lindsay");
        list.add("Rodolphe");
    }

    private void addMatchesTo(List<Match> list, List<Player> listPlayers) {
        int beg = 0, begg = 0;
        int end = listPlayers.size() - 1;
        while (beg < listPlayers.size() - 1) {
            list.add(new Match(listPlayers.get(beg), listPlayers.get(end), RESULTS.EX_AEQUO));
            ++beg;
            --end;
        }
        while (begg < listPlayers.size() - 1) {
            int next = begg + 1;
            list.add(new Match(listPlayers.get(begg), listPlayers.get(next), RESULTS.EX_AEQUO));
            ++begg;
        }
    }

    private void addPlayersTo(List<Player> listPlayers, List<String> listPlayersName) {
        for (int i = 0; i < listPlayersName.size(); ++i) {
            listPlayers.add(new Player(listPlayersName.get(i)));
        }
    }

    private void addIntoTournament(Tournament t1, Tournament t2, List<Player> listPlayers, List<Match> listMatches) {
        addPlayersToTourn(t1, listPlayers, listPlayers.size());
        addPlayersToTourn(t2, listPlayers, listPlayers.size());
        addMatchesToTourn(t1, listMatches, listMatches.size());
        addMatchesToTourn(t2, listMatches, listMatches.size());
    }

    private void addPlayersToTourn(Tournament t, List<Player> listPlayers, int nbP) {
        for (int i = 0; i < nbP; ++i) {
            t.addPlayer(listPlayers.get(i));
        }
    }

    private void addMatchesToTourn(Tournament t, List<Match> listMatches, int nbM) {
        for (int i = 0; i < nbM; ++i) {
            t.addMatch(listMatches.get(i));
        }
    }

    private void addTournamentToTournList(Tournament t1, Tournament t2) {
        tournamentList.add(t1);
        tournamentList.add(t2);
    }
    
//    private void addMatchTo(List<Match> list, List<Player> listPlayers) {
//        for (int i = 0; i < listPlayers.size(); ++i) {
//            list.add(newMatch(listPlayers));
//        }
//    }
//    private void addMatchTo(List<Match> list, List<Player> listPlayers) {
//        for (int i = 0; i < listPlayers.size(); ++i) {
//            List<Player> tmp = listPlayers;
//            Player p1 = randomPlayer(listPlayers, listPlayers.size());
//            tmp.remove(p1); //pour éviter les matches contre soi-même
//            Player p2 = randomPlayer(tmp, tmp.size());
//            list.add(new Match(p1, p2, RESULTS.EX_AEQUO));
//        }
//    }

//    private Match newMatch(List<Player> listPlayers) {
//        List<Player> tmp = listPlayers; 
//        Player p1 = randomPlayer(listPlayers, listPlayers.size());
//        tmp.remove(p1); //pour éviter les matches contre soi-même
//        Player p2 = randomPlayer(tmp, tmp.size());
//        return new Match(p1, p2, RESULTS.EX_AEQUO);
//    }
//    private Player randomPlayer(List<Player> list, int nbPlayers) {
//        Random rand = new Random();
//        int index = rand.nextInt(nbPlayers); //random de 0 à 5
//        if (index < 0) {
//            index = 0;
//        }
//        if (index >= nbPlayers) {
//            index = nbPlayers - 1;
//        }
//        return list.get(index); //on choppe le player à cet index
//    }
}
