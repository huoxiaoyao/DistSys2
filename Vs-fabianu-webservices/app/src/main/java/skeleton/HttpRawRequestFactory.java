package skeleton;

//import ch.ethz.inf.vs.a2.solution.http.HttpRawRequestImpl;


import ch.ethz.inf.vs.vs_fabianu_webservices.HttpRawRequestImpl;

public class HttpRawRequestFactory {
	public static HttpRawRequest getInstance(String host, int port, String path) {
		// return HttpRawRequest implementation
		return new HttpRawRequestImpl(host, port, path);
	}
}
