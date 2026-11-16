package se.chalmers.team15.game.model;

public class Score {
  private int player1Score = 0;
  private int player2Score = 0;

  public void addPlayer1() {
    player1Score++;
  }

  public void addPlayer2() {
    player2Score++;
  }

  public void resetScore() {
    player1Score = 0;
    player2Score = 0;
  }

  public int getPlayer1Score() {
    return player1Score;
  }

  public int getPlayer2Score() {
    return player2Score;
  }
}
