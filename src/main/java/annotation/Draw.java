package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // Inspection de l'annotation à l'exécution pour obtenir des informations sur les éléments annotés.
@Target({ElementType.METHOD, ElementType.TYPE}) //usable sur méthode et sur type (class par exemple)
public @interface Draw {
}
