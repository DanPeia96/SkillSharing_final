package es.uji.ei1027.SkillSharing.Model;

public class Habilidad {
    private String nombre;
    private int nivel;
    private String descripcion;
    private Boolean disponible;

    public Habilidad() {
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String n) {
        this.nombre = n;
    }

    public int getNivel() {
        return nivel;
    }
    public void setNivel(int e) {
        this.nivel = e;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String d) {
        this.descripcion = d;
    }

    public Boolean getDisponible(){return disponible; }
    public void setDisponible(boolean disponible) { this.disponible=disponible; }

    @Override
    public String toString() {
        return "Habilidad{" +
         /*       "codigo_habilidad='" + codigo_habilidad + "\'" +   */
                ", nombre='" + nombre + "\'" +
                ", nivel='" + nivel + "\'" +
                ", descripcion='" + descripcion + "\'" +
                "}";
    }


}
