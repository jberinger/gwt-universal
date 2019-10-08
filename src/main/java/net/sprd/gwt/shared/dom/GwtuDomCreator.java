package net.sprd.gwt.shared.dom;

import net.sprd.gwt.client.GwtuClient;

public abstract class GwtuDomCreator {
    
    private static GwtuDomCreator CREATOR = GwtuClient.getClientCreator();
    
    public static GwtuDom create() {
        return CREATOR != null ? CREATOR.createGwtuDom() : null;
    }
    
    public static void setCreator(GwtuDomCreator instance) {
        CREATOR = instance;
    }
    
    public abstract GwtuDom createGwtuDom();

}
