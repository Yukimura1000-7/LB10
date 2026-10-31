import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class VersionChecker {
    public static void main(String[] args) {
        checkClass(Software.class);
        System.out.println();
        checkClass(NoAnnotations.class);
    }

    public static void checkClass(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        int annotated = 0;
        int skipped = 0;

        System.out.println("=== Проверка класса: " + clazz.getSimpleName() + " ===");
        for (Method method : methods) {
            Version version = method.getAnnotation(Version.class);
            if (version != null) {
                annotated++;
                System.out.println("Method: " + method.getName()
                        + ", Version: " + version.number()
                        + ", Author: " + version.author());
            } else {
                skipped++;
                System.out.println("Method: " + method.getName()
                        + " — пропущен (нет @Version)");
            }
        }
        System.out.println("Аннотированных методов: " + annotated
                + ", пропущено: " + skipped);
    }
}