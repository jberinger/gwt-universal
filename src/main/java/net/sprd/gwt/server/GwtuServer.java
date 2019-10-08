package net.sprd.gwt.server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.sprd.gwt.server.dom.Function;
import net.sprd.gwt.server.dom.GwtuDomServer;
import net.sprd.gwt.shared.dom.GwtuDom;
import net.sprd.gwt.shared.dom.GwtuDomCreator;
import net.sprd.gwt.shared.page.App;
import net.sprd.gwt.shared.page.data.PageData;
import net.sprd.gwt.shared.page.state.PageState;

public class GwtuServer {

    static {
        GwtuDomCreator.setCreator(new GwtuDomCreator() {
            @Override
            public GwtuDom createGwtuDom() {
                return new GwtuDomServer();
            }
        });
    }

    public static void init() {

    }

    public static <D extends PageData> String renderHtml(App<? extends PageState, D> app, String token, String html,
            String scriptUrl, Function<D, String> scriptWriter) {
        GwtuDomServer gwtuDomServer = (GwtuDomServer) app.getGwtuDom();
        app.initPage(token);
        Document doc = Jsoup.parse(html);
        gwtuDomServer.addScript(doc, scriptWriter.apply(app.getPageData()));
        gwtuDomServer.addScriptUrl(doc, scriptUrl);
        gwtuDomServer.render(doc);
        doc.outputSettings().indentAmount(0).prettyPrint(false);
        return doc.html();
    }

}
