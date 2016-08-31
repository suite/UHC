package net.gr8bit.uhc.gamehandler;

/**
 * Created by Matthew on 8/25/2016.
 */
public class GameManager {



    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    GameState gameState = GameState.Lobby;

    public enum GameState {
        Lobby, Ingame, Ended
    }

}
