package myenum;

public enum E_EntityCategory {
    OBJECT("Object"),
    NPC("NPC"),
    MONSTER("Monster"),
    INTERACTIVE_TILE("InteractiveTile");



    private final String typeName;

    E_EntityCategory(String typeName) {
        this.typeName = typeName;
    }

    public String Name() {
        return typeName;
    }
}
