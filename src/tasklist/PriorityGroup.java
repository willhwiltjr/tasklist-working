package tasklist;

public enum PriorityGroup {
    ALPHA("ALPHA", 1),
    BETA("BETA", 2),
    CHARLIE("CHARLIE", 3);

    private final String name;
    private final int level;

    PriorityGroup(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}

