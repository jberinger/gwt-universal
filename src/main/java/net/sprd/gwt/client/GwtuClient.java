package net.sprd.gwt.client;

import com.google.gwt.core.client.Callback;

import net.sprd.gwt.client.dom.GwtuDomClient;
import net.sprd.gwt.client.state.UrlService;
import net.sprd.gwt.shared.dom.GwtuDom;
import net.sprd.gwt.shared.dom.GwtuDomCreator;
import net.sprd.gwt.shared.page.App;
import net.sprd.gwt.shared.page.data.PageData;
import net.sprd.gwt.shared.page.data.PageDataLoader;
import net.sprd.gwt.shared.page.state.PageState;

public class GwtuClient {
    
    public static void init() {
        GwtuDomCreator.setCreator(getClientCreator());
    }
    
    private static GwtuDomCreator clientCreator = new GwtuDomCreator() {
        @Override
        public GwtuDom createGwtuDom() {
            return new GwtuDomClient();
        }
    };
    
    public static GwtuDomCreator getClientCreator() {
        return clientCreator;
    }
    
    public static <S extends PageState, D extends PageData>  void startApp(App<S, D> app, PageDataLoader<S, D> pageDataLoader, String baseUrl) {
        PageDataLoader.setPageDataLoader(pageDataLoader);
        UrlService.set(new UrlService(baseUrl, new Callback<String, String>() {
            @Override
            public void onSuccess(String token) {
                app.update(token);
            }

            @Override
            public void onFailure(String reason) {
            }
        }));

        app.initPage(UrlService.get().getTokenFromCurrentUrl());
    }

}
