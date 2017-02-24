
package logic;

public enum Severity {
    UNKNOWN(-10), CLEAR(0), INFO(10), MINOR(20), WARNING(30), MAJOR(40), CRITICAL(50);

    private final int value;

    Severity(int value) {
        this.value = value;
    }

    public static Severity parse(int value) {
        switch (value) {
            case 0:
                return CLEAR;
            case 10:
                return INFO;
            case 20:
                return MINOR;
            case 30:
                return WARNING;
            case 40:
                return MAJOR;
            case 50: 
                return CRITICAL;
            default:
                return UNKNOWN;
        }
    }

    public int getValue() {
        return value;
    }
    
}