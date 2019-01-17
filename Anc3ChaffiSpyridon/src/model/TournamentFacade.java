/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author Spy
 */
public class TournamentFacade extends Observable {

    public List<Tournament> tournamentList = new ArrayList<>();

    public int indexTournament;
    public Player actual;
    public Match selectedMatch;
    public Tournament tournois;
    public int indexMatch;

    public TournamentFacade() {
        initData();
    }

    public Tournament getTournament() {
        return tournamentList.get(indexTournament);
    }

    public int getIndexTournament() {
        return indexTournament;
    }

    public Player getActual() {
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
        Tournament t1 = new Tournament("E-Sport");
        Tournament t2 = new Tournament("T2");

        Player p1 = new Player("Philippe");
        Player p2 = new Player("Khadija");
        Player p3 = new Player("spyridon");
        Player p4 = new Player("chaffi");
        Player p5 = new Player("lindsay");
        Player p6 = new Player("rodolphe");
        t1.addPlayer(p1);
        t1.addPlayer(p2);
        t1.addPlayer(p3);
        t1.addPlayer(p4);
        t1.addPlayer(p5);
        t1.addPlayer(p6);
        t2.addPlayer(p1);
        t2.addPlayer(p2);
        t2.addPlayer(p3);
        Match m = new Match(p1, p2, RESULTS.EX_AEQUO);
        Match m2 = new Match(p2, p1, RESULTS.EX_AEQUO);
        Match m3 = new Match(p1, p4, RESULTS.EX_AEQUO);
        Match m4 = new Match(p4, p1, RESULTS.EX_AEQUO);
        Match m5 = new Match(p6, p2, RESULTS.EX_AEQUO);
        Match m6 = new Match(p3, p6, RESULTS.EX_AEQUO);

        t1.addMatch(m);
        t1.addMatch(m2);
        t1.addMatch(m3);
        t1.addMatch(m4);
        t1.addMatch(m5);
        t1.addMatch(m6);
        t2.addMatch(m);
        t2.addMatch(m2);
        t2.addMatch(m3);
        t2.addMatch(m4);
        tournamentList.add(t1);
        tournamentList.add(t2);
    }

}
