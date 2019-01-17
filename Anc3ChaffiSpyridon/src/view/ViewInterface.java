/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import java.util.Set;
import model.Match;
import model.Player;
import model.Tournament;

/**
 *
 * @author 2707chshyaka
 */
public interface ViewInterface {

    void initView(Set<Match> match, List<Player> subscribes, List<Tournament> tournament,Tournament tournois);

    void tournament_selected(Set<Match> match, List<Player> subscribes);

    void player_one_selected(List<Player> player_valid);

    void add_match(Set<Match> match);

    void remove_match(Set<Match> match);
}
