package helper;

/**
 * Class to print helpful log statements.
 */
public class MaedenLog {
    private static MaedenLog ourInstance = new MaedenLog();

    public static MaedenLog getInstance() {
        return ourInstance;
    }

    public void info(Object callingClass, String message){
        System.out.println(String.format("[INFO][%s] %s", callingClass.getClass().getSimpleName(), message));
    }

    public void debug(Object callingClass, String message){
        System.out.println(String.format("[DEBUG][%s] %s", callingClass.getClass().getSimpleName(), message));
    }

    public void error(Object callingClass, String message){
        System.err.println(String.format("[ERROR][%s] %s", callingClass.getClass().getSimpleName(), message));
    }
}
