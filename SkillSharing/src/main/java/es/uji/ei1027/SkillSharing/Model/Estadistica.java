package es.uji.ei1027.SkillSharing.Model;

public class Estadistica {
    private String id;
    private String nombre;
    private int numero;

    public Estadistica(String id, String nombre, int numero){
        this.id=id;
        this.nombre=nombre;
        this.numero=numero;
    }

    public String getId(){return id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Estadistica{" +
                "id='" + id + '\'' +
                "nombre='" + nombre + '\'' +
                ", numero=" + numero +
                '}';
    }
}
