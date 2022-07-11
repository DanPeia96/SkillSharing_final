package es.uji.ei1027.SkillSharing.Model;

public enum Nivel {
    BAJO(1),
    MEDIO(2),
    ALTO(3);

    private final int id;
    Nivel(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
