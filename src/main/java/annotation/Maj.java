package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Inspection de l'annotation à l'exécution pour obtenir des informations sur les éléments annotés.
@Target({ElementType.METHOD, ElementType.TYPE}) //usable sur méthode et sur type (class par exemple)
public @interface Maj {
}
