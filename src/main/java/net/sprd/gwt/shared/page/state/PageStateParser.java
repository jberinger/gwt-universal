package net.sprd.gwt.shared.page.state;

public abstract class PageStateParser<S extends PageState> {
    
    @SuppressWarnings("rawtypes")
    private static PageStateParser PARSER;
    
    @SuppressWarnings("unchecked")
    public static <S extends PageState> PageStateParser<S> getParser() {
        return PARSER;
    }
    @SuppressWarnings("rawtypes")
    public static void setParser(PageStateParser parser) {
        PARSER = parser;
    }
    
    public abstract S parse(String token);

}
