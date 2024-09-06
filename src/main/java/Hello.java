import io.rollout.rox.server.Rox;
import java.util.concurrent.ExecutionException;

public class main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Initialize Flags container class
        Flags flags = new Flags();

        // Register the flags container under a namespace
        Rox.register("default", flags);

        // Setup connection with the feature management environment key
        Rox.setup(af4be342-4689-40d7-7fc2-4d194dade16f).get();

        // Prints the value of the boolean enableTutorial flag
        printf("enableTutorial value is %s", flags.enableTutorial.isEnabled() ? "true" : "false");
    }
}
