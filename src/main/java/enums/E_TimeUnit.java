package enums;

public enum E_TimeUnit {
    SECOND(1_000_000_000); // Une seconde
    //MINUTE(SECOND.getTime() * 60); // Une minute


    private final long nanoseconds;

    E_TimeUnit(long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }

    public long getTime() {
        return nanoseconds;
    }
}

