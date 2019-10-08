package net.sprd.gwt.client.state;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Location;

import elemental2.dom.DomGlobal;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.PopStateEvent;

public class UrlService {
    
    private static UrlService INSTANCE;
    
    public static UrlService get() {
        return INSTANCE;
    }
    
    public static void set(UrlService urlService) {
        INSTANCE = urlService;
    }
    
    public static void newUrlToken(String token) {
        get().newItem(token);
    }
    
    private String lastToken = null;
    private String currentToken = null;
    private String baseUrl;
    private Callback<String, String> historyChangedCallback;

    public UrlService(String baseUrl, Callback<String, String> historyChangedCallback) {
        this.baseUrl = baseUrl;
        this.historyChangedCallback = historyChangedCallback;
        DomGlobal.window.addEventListener("popstate", new EventListener() {
            
            @Override
            public void handleEvent(Event event) {
                PopStateEvent popStateEvent = (PopStateEvent) event;
                updatePopState((String)popStateEvent.state);
            }
        });
    }

    public void updatePopState(String token) {
        update(token);
    }

    public void update(String token) {
        if (currentToken == null || !currentToken.equals(token)) {
            lastToken = currentToken;
            currentToken = token;
            historyChangedCallback.onSuccess(token);
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setLastToken(String token) {
        lastToken = token;
    }

    public String getLastToken() {
        return lastToken;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String token) {
        currentToken = token;
    }

    public void newItem(String token) {
        newItem(token, true);
    }

    public void newItem(final String token, boolean issueEvent) {
        String url = getUrl(token);
        if (issueEvent) {
            pushState(token, token, url);
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    update(token);
                }
            });
        } else {
            replaceState(token, token, url);
            setCurrentToken(getTokenFromUrl(baseUrl, url));
        }

    }

    public void replaceUrl(String token) {
        String url = getUrl(token);
        replaceState(token, token, url);
    }

    public String getUrl(String token) {
        token = (token == null ? "" : token.replace("%26", "&"));
        return baseUrl + token;

    }

    public static String encodeParam(String param) {
        return URL.encodeQueryString(param).replaceAll("%2F", "/").replaceAll("%3A", ":");
    }

    private static void pushState(String value, String title, String url) {
        DomGlobal.window.history.pushState(value, title, url);
    }

    private static void replaceState(String value, String title, String url) {
        DomGlobal.window.history.replaceState(value, title, url);
    }

    public void start() {
        String token = getTokenFromCurrentUrl(baseUrl);
        update(token);
    }

    public String getTokenFromUrl(String url) {
        return getTokenFromUrl(getBaseUrl(), url);
    }
    
    public String getTokenFromCurrentUrl() {
        return getTokenFromUrl(baseUrl, Location.getHref());
    }

    public static String getTokenFromCurrentUrl(String baseUrl) {
        return getTokenFromUrl(baseUrl, Location.getHref());
    }
    
    public static String decodeToken(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        return URL.decodePathSegment(path);
    }

    public static String getTokenFromUrl(String baseUrl, String url) {
        String checkBaseUrl = baseUrl;
        String token = "";
        if (url.indexOf('#') >= 0) {
            url = url.substring(0, url.indexOf('#'));
        }
        if (checkBaseUrl.length() > 1) {
            if (url.contains(checkBaseUrl)) {
                token = url.substring(url.indexOf(checkBaseUrl) + checkBaseUrl.length());
                token = decodeToken(token);
            } else if (url.contains(checkBaseUrl.substring(0, checkBaseUrl.length() - 1))) {
                token = url.substring(
                        url.indexOf(checkBaseUrl.substring(0, checkBaseUrl.length() - 1)) + checkBaseUrl.length() - 1);
                token = decodeToken(token);
            } else {
                if (url.startsWith("/")) {
                    token = url.substring(1);
                } else {
                    token = url;
                }
            }
        } else {
            checkBaseUrl = Location.getHost() + checkBaseUrl;
            if (url.contains(checkBaseUrl)) {
                int base = url.indexOf(checkBaseUrl);
                token = url.substring(base + checkBaseUrl.length());
                token = decodeToken(token);
            } else if (url.startsWith("http") || url.startsWith("//") || url.startsWith("www.")) {
                token = null;
            } else {
                if (url.startsWith("/")) {
                    token = url.substring(1);
                } else {
                    token = url;
                }
            }
        }
        return token;
    }

}
