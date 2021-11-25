package config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class BeforeTestConfig {
    public static String address;
    public static DefaultHttpClient defaultHttpClient;
    public static CookieStore store;
}
