package controller;

import java.util.List;
import java.util.Set;
import model.Match;
import model.Player;
import model.Tournament;
import model.TournamentFacade;
import model.RESULTS;
import view.View;

/**
 *
 * @author Spy
 */
public final class Presenter {

    TournamentFacade facade;
    View view;
    RESULTS res;

    public Presenter(TournamentFacade facade) {
        this.facade = facade;

    }

    public void setView(View view) {
        this.view = view;

    }

    public void init_view() {
        Set<Match> match = facade.getMatchList();
        List<Player> player = facade.getSubscrib();
        List<Tournament> tournamant = facade.getTournamentList();
        Tournament tournois = facade.getTournament();
        view.initView(match, player, tournamant, tournois);
    }

    public void createMatch(Player p1, Player p2, RESULTS res) {
        facade.createNewMatch(p1, p2, res);
        Set<Match> match = facade.getMatchList();
        view.add_match(match);
    }

    public void setIndex(int index) {
        facade.setIndexTournament(index);
        Set<Match> match = facade.getMatchList();
        List<Player> players = facade.getSubscrib();
        view.tournament_selected(match, players);
    }

    public void setMatchSelected(Match m, int index) {
        facade.setIndexSelectedMatch(m, index);
        Set<Match> match = facade.getMatchList();
        view.remove_match(match);

    }

    public void setPlayer(Player p) {
        facade.setPlayer(p);
        List<Player> valid_player = facade.addOppponentValidList();
        view.player_one_selected(valid_player);
    }

    public Match getSelectedMatch() {
        return facade.getSelectedMatch();
    }

    public Set<Match> getAllMatch() {
        return facade.getTournament().getMatchList();
    }

    public void DelMatch(Match m) {
        facade.removeMatch();
    }

    public RESULTS getresulst() {
        return this.res;
    }
}
