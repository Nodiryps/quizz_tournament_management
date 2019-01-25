package presenter;

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

    private final TournamentFacade facade;
    private View view;
    private RESULTS res;

    public Presenter(TournamentFacade facade) {
        this.facade = facade;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void initView() {
        Set<Match> match = facade.getMatchList();
        List<Player> player = facade.getSubscrib();
        List<Tournament> tournamentList = facade.getTournamentList();
        Tournament tournament = facade.getTournament();
        view.initView(match, player, tournamentList, tournament);
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
        view.selectedTournament(match, players);
    }

    public void setMatchSelected(Match m, int index) {
        facade.setIndexSelectedMatch(m, index);
        Set<Match> match = facade.getMatchList();
        view.removeMatch(match);
    }

    public void setPlayer(Player p) {
        facade.setPlayer(p);
        List<Player> validPlayerList = facade.addOppponentValidList();
        view.playerOneSelected(validPlayerList);
    }

    public Match getSelectedMatch() {
        return facade.getSelectedMatch();
    }

    public Set<Match> getAllMatch() {
        return facade.getTournament().getMatchList();
    }

    public void DelMatch() {
        facade.removeMatch();
    }

    public RESULTS getresults() {
        return this.res;
    }
}
