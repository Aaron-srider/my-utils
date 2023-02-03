package fit.wenchao.utils.other.exception;

public class ExceptionUtils {

    public static Throwable getRootCause(Throwable ex) {
        Throwable cause = ex.getCause();

        Throwable result = null;
        while (cause != null) {
            result = cause;
            cause = cause.getCause();
        }

        return result;
    }

}
