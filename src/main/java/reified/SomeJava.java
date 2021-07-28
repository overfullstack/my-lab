package reified;


import org.jetbrains.annotations.NotNull;

public class SomeJava {
    /*public static <T, P extends SomeOther<T>> void fromPublisher(P publisher, Class<T> elementClass) {
    }*/ // This signature gives be compiler error at call-site 

    public static <T> void fromPublisher(@NotNull SomeOther<? extends T> someOther, @NotNull Class<T> elementClass) {
    } // This works
}
