package es.uji.ei1027.SkillSharing.Controller;
import es.uji.ei1027.SkillSharing.Dao.UsuarioDao;
import es.uji.ei1027.SkillSharing.Model.Colaboracion;
import es.uji.ei1027.SkillSharing.Model.Habilidad;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class ColaboracionValidador implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Colaboracion.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Colaboracion colaboracion = (Colaboracion) target;

        if (colaboracion.getHoras()<0 ||colaboracion.getHoras()>20)
            errors.rejectValue("saldo_horas", "Cantidad_horas_anormal",
                    "La duración de un curso no puede ser negativo o durar más de 20h.");
    }

    public void validateValoracion(Object target, Errors errors) {
        Colaboracion colaboracion = (Colaboracion) target;

        if (colaboracion.getEvaluacion()==0)
            errors.rejectValue("evaluacion", "Evaluacion_campos_sin_rellenar",
                    "Debe evaluar la colaboracion.");
    }
}