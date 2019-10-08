package net.sprd.gwt.shared.page;

import com.google.gwt.core.client.Callback;

import elemental2.dom.HTMLElement;
import net.sprd.gwt.shared.page.data.PageData;
import net.sprd.gwt.shared.page.data.PageDataLoader;
import net.sprd.gwt.shared.page.state.PageState;
import net.sprd.gwt.shared.page.state.PageStateParser;

public abstract class App<S extends PageState, D extends PageData> extends AppDom {
    
    protected D pageData;
    protected S pageState;
    
    public void initPage(String token) {
        S pageState = parseState(token);
        D pageData = PageDataLoader.<S,D>getPageDataLoader().getInitPageData();
        if (pageData != null) {
            App.this.pageData = pageData;
            render(pageState, pageData);
            setInitialPhase(false);
        } else {
            PageDataLoader.<S,D>getPageDataLoader().load(pageState, new Callback<D, String>() {
                public void onSuccess(D pageData) {
                    App.this.pageData = pageData;
                    render(pageState, pageData);
                    setInitialPhase(false);
                }
                @Override
                public void onFailure(String reason) {
                 // TODO Auto-generated method stub
                }
    
            });
        }
    }

    public void update(String token) {
        S pageState = parseState(token);
        PageDataLoader.<S,D>getPageDataLoader().load(pageState, new Callback<D, String>() {
            public void onSuccess(D pageData) {
                App.this.pageData = pageData;
                doUpdate(pageState, pageData);
            }

            @Override
            public void onFailure(String reason) {
                // TODO Auto-generated method stub
            }
        });
    }
    
    protected S parseState(String token) {
        S pageState = PageStateParser.<S>getParser().parse(token);
        return pageState;
    }
    
    public D getPageData() {
        return pageData;
    }
    
    public S getPageState() {
        return pageState;
    }
    
    public final void doRender(S pageState, D pageData) {
        this.pageState = pageState;
        this.pageData = pageData;
        render(pageState, pageData);
    }
    
    public final void doUpdate(S pageState, D pageData) {
        this.pageState = pageState;
        this.pageData = pageData;
        update(pageState, pageData);
    }
    
    protected abstract void render(S pageState, D pageData);
    
    protected abstract void update(S pageState, D pageData);

    abstract public HTMLElement getMainElement();

}
