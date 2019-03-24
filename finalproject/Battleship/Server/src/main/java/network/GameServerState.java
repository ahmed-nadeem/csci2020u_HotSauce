package network;

// creating the states for the game server
public enum GameServerState {
    INIT_STATE,
    WAIT_FOR_SECOND_PLAYER,
    WAIT_FOR_FIRST_READY,
    WAIT_FOR_SECOND_READY,
    WAIT_FOR_MOVE,
    END
}
