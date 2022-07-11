package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Model.Incidencia;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IncidenciaValidador implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Incidencia.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Incidencia incidencia = (Incidencia) target;
        if (incidencia.getDescripcion().trim().equals("")) //descripción vacía
            errors.rejectValue("descripcion", "Incidencia_campo_descripcion_sin_rellenar",
                    "Debe facilitarse la descripción.");
    }
}
