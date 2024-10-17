package classes.connectfour;


public enum Disc {
    YELLOW("Y"),
    RED("R");

    private final String symbol;

    Disc(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
