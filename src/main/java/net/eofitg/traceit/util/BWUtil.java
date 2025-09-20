package net.eofitg.traceit.util;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

public class BWUtil {

    public static boolean notIBw() {
        if (PlayerUtil.containsNull()) return true;
        Scoreboard scoreboard = PlayerUtil.getThePlayer().getWorldScoreboard();
        if (scoreboard == null) return true;
        ScoreObjective scoreboardTitle = scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreboardTitle == null) return true;
        String gameFromScoreboard = StringUtil.stripColor(scoreboardTitle.getDisplayName()).replace(" ", "");
        if (!Objects.equals(gameFromScoreboard, "BEDWARS")) return true;

        Collection<Score> scores = scoreboard.getSortedScores(scoreboardTitle);
        for (Score score : scores) {
            String displayText = getScoreboardDisplayText(scoreboard, score);
            String cleanText = StringUtils.stripControlCodes(displayText);
            if (cleanText.contains("YOU")) return false;
        }
        return true;
    }

    private static String getScoreboardDisplayText(Scoreboard scoreboard, Score score) {
        String playerName = score.getPlayerName();
        ScorePlayerTeam team = scoreboard.getPlayersTeam(playerName);
        if (team != null) {
            return team.getColorPrefix() + playerName + team.getColorSuffix();
        } else {
            return playerName;
        }
    }

}
