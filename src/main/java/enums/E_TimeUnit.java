package enums;

public enum E_TimeUnit {
    MILLISECONDS(1_000_000),
    SECOND(1_000_000_000), // Une seconde
    MINUTE(SECOND.getTime() * 60), // Une minute
    HOUR(MINUTE.getTime() * 60); // Une heure

    private final long nanoseconds;

    E_TimeUnit(long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }

    public long getTime() {
        return nanoseconds;
    }
}

