package skeleton;

/*import ch.ethz.inf.vs.a2.solution.http.LibHttpClient;
import ch.ethz.inf.vs.a2.solution.http.RawHttpClient;*/

/**
 * Created by leynas on 25.09.15.
 */
public abstract class SimpleHttpClientFactory {
    public static SimpleHttpClient getInstance(Type type) {
        switch (type) {
            case RAW:
                //TODO
                return null;//new RawHttpClient();
            case LIB:
                //TODO
                return null;//new LibHttpClient();
            default:
                return null;
        }
    }

    public enum Type {
        RAW, LIB;
    }
}
