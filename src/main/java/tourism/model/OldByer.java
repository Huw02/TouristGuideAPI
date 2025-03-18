package tourism.model;

public enum OldByer {
    AALBORG("Aalborg"),
    AARHUS("Aarhus"),
    ALBERTSLUND("Albertslund"),
    ALLERØD("Allerød"),
    ASSENS("Assens"),
    BILLUND("Billund"),
    BJERRINGBRO("Bjerringbro"),
    BORNHOLM("Bornholm"),
    BRØNDBY("Brøndby"),
    DRAGØR("Dragør"),
    ESBJERG("Esbjerg"),
    FAABORG("Faaborg"),
    FREDERICIA("Fredericia"),
    FREDERIKSHAVN("Frederikshavn"),
    FREDERIKSBERG("Frederiksberg"),
    FREDERIKSSUND("Frederikssund"),
    GLADSAXE("Gladsaxe"),
    GREVE("Greve"),
    GRINDSTED("Grindsted"),
    HADERSLEV("Haderslev"),
    HILLERØD("Hillerød"),
    HIRTSHALS("Hirtshals"),
    HJALLERUP("Hjallerup"),
    HOLBÆK("Holbæk"),
    HOLSTEBRO("Holstebro"),
    HORSENS("Horsens"),
    HVIDOVRE("Hvidovre"),
    IKAST("Ikast"),
    KERTEMINDE("Kerteminde"),
    KOLDING("Kolding"),
    KORSØR("Korsør"),
    KØGE("Køge"),
    KØBENHAVN("København"),
    LEMVIG("Lemvig"),
    MARIBO("Maribo"),
    NYKØBING_FALSTER("Nykøbing Falster"),
    NYKØBING_MORS("Nykøbing Mors"),
    NYBORG("Nyborg"),
    ODENSE("Odense"),
    RANDERS("Randers"),
    RINGKØBING("Ringkøbing"),
    RINGSTED("Ringsted"),
    ROSKILDE("Roskilde"),
    RUDKØBING("Rudkøbing"),
    SKAGEN("Skagen"),
    SKANDERBORG("Skanderborg"),
    SKIVE("Skive"),
    SLAGELSE("Slagelse"),
    SORØ("Sorø"),
    SVENDBORG("Svendborg"),
    THISTED("Thisted"),
    TØNDER("Tønder"),
    VEJLE("Vejle"),
    VIBORG("Viborg"),
    VORDINGBORG("Vordingborg");

    private String displayName;

    OldByer(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
