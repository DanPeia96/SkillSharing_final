package es.uji.ei1027.SkillSharing.Model;

public class Usuario {
    private String  userId;
    private String  nombre_completo;
    private String  email;
    private String  password;
    private boolean activado;
    private boolean admin;
    private float   saldo_horas;

    public Usuario(){}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public boolean isActivado() {
        return activado;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return nombre_completo;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {
        return password;
    }
    //Se usa solo para encriptzr las contraseñas.
    public void setPassword(String password){
        this.password=password;
    }

    public boolean estaActivo() {
        return activado;
    }
    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public boolean esAdmin() {
        return admin;
    }
   // public void setAdmin(String tipo) {this.admin = admin;}

    public float getSaldo_horas(){ return saldo_horas;}
    public void setSaldo_horas(float saldo_horas){ this.saldo_horas=saldo_horas;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario='" + userId + '\'' +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", activado='" + activado + '\'' +
                ", admin='" + admin + '\'' +
                ", saldo_horas='" + saldo_horas + '\'' +
                '}';
    }
}
