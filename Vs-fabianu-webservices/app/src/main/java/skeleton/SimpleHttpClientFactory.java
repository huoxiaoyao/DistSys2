package skeleton;

/*import ch.ethz.inf.vs.a2.solution.http.LibHttpClient;
import ch.ethz.inf.vs.a2.solution.http.RawHttpClient;*/

import ch.ethz.inf.vs.vs_fabianu_webservices.LibHttpClient;
import ch.ethz.inf.vs.vs_fabianu_webservices.RawHttpClient;
import ch.ethz.inf.vs.vs_fabianu_webservices.Server.EntityHttpClient;

/**
 * Created by leynas on 25.09.15.
 */
public abstract class SimpleHttpClientFactory {
    public static SimpleHttpClient getInstance(Type type) {
        switch (type) {
            case RAW:
                return new RawHttpClient();
            case LIB:
                return new LibHttpClient();
            case ENTI:
                return new EntityHttpClient();
            default:
                return null;
        }
    }

    public enum Type {
        RAW, LIB, ENTI;
    }
}
