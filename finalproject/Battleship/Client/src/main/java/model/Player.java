//Player Class

package model;              //Package for model class

public class Player {
    private Map playerMap;
    private Integer playerId;

    public Player(Integer playerId) {
        this.playerMap = new Map();
        this.playerId = playerId;
    }

    public Map getPlayerMap() { 
        return playerMap;                                   //Returns Player Map (Grid)     
    }

    public Integer getPlayerId() {
        return playerId;                                    //Returns Player ID
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;                           //Sets Player ID
    }
}
