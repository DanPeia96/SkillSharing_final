package es.uji.ei1027.SkillSharing.Controller;
import es.uji.ei1027.SkillSharing.Model.Habilidad;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class HabilidadValidador implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Habilidad.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Habilidad habilidad = (Habilidad) target;
        if (habilidad.getNombre().trim().equals("")) //Código vacio
            errors.rejectValue("nombre", "Habilidad_campos_sin_rellenar",
                    "Debe indicar el nombre.");
        if(habilidad.getNivel()==0)
            errors.rejectValue("nivel", "Nivel_campos_sin_rellenar",
                    "Debe indicar el nivel.");
        if (habilidad.getDescripcion().trim().equals("")) //Código vacio
            errors.rejectValue("descripcion", "Descripcion_campos_sin_rellenar",
                    "Debe indicar la descripcion.");

        if (habilidad.getDescripcion().length()>200) //Código vacio
            errors.rejectValue("descripcion", "Excedido_limite_caracteres",
                    "Has introducido demasiados carqacteres. Máximo 200.");
    }

    public void validate2(Object target, Errors errors) {
        Habilidad habilidad = (Habilidad) target;
        if (habilidad.getDescripcion().trim().equals("")) //Código vacio
            errors.rejectValue("descripcion", "Descripcion_campos_sin_rellenar",
                    "Debe indicar la descripcion.");
    }
}
