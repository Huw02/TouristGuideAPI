package tourism.model;

public enum Tags {
    FORLYSTELSESPARK("Forlystelsespark"),
    KUNST("Kunst"),
    MUSEUM("Museum"),
    KONCERT("Koncert"),
    KULTUR("Kultur"),
    UNDERHOLDNING("Underholdning"),
    RESTAURANT("Restaurant"),
    BØRNEVENLIG("Børnevenlig"),
    GRATIS("Gratis"),
    NATUR("Natur");

    private String displayName;

    Tags(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}