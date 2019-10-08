package net.sprd.gwt.shared.page.data;


import com.google.gwt.core.client.Callback;

import net.sprd.gwt.shared.page.state.PageState;

public abstract class PageDataLoader<S extends PageState, D extends PageData> {
    
    @SuppressWarnings("rawtypes")
    private static PageDataLoader PAGE_DATA_LOADER;
    
    @SuppressWarnings("unchecked")
    public static <S extends PageState, D extends PageData> PageDataLoader<S, D> getPageDataLoader() {
        return PAGE_DATA_LOADER;
    }
    @SuppressWarnings("rawtypes")
    public static void setPageDataLoader(PageDataLoader pageDataLoader) {
        PAGE_DATA_LOADER = pageDataLoader;
    }
    
    public abstract void load(S pageState, Callback<D, String> callback);
    
    public D getInitPageData() {
        return null;
    }

}
