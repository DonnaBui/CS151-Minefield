package mineField;

public enum Direction {
    /* (row, column) Modifiers:
    Negative row = going up, positive row = going down
    Negative column = going left, positive column = going right
    ↖ = NW(-1,-1)     ↑ = N(-1,0)     ↗ = NE(-1,1) 
    ← = W(0, -1)                     → = E(0,1) 
    ↙ = SW(1,-1)     ↓ = S(1,0)     ↘ = SE(1,1)
    */ 
    NW(-1,-1), N(-1,0), NE(-1,1), W(0,-1), 
    E(0,1), SW(1,-1), S(1,0), SE(1,1);

    private final int rowDir;
    private final int colDir;
    
    private Direction (int rowDir, int colDir) {
        this.rowDir = rowDir;
        this.colDir = colDir;
    }

    public int getRowDir() {
        return rowDir;
    }

    public int getColDir() {
        return colDir;
    }

}
