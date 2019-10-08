package net.sprd.gwt.shared.page;

public class Child {
    
    public static Child create(String tag, String id, String[] attributes, Child... childs) {
        return new Child(tag, id, attributes, childs);
    }
    
    public static Child create(String tag, String[] attributes, Child... childs) {
        return new Child(tag, attributes, childs);
    }
    
    private Child[] childs;
    
    private String tag;
    private String[] attributes;
    
    private String id;
    
    protected Child() {
    }
    
    public Child(String tag, String[] attributes, Child... childs) {
        this(tag, null, attributes, childs);
    }
    
    public Child(String tag, String id, String[] attributes, Child... childs) {
        super();
        this.tag = tag;
        this.attributes = attributes;
        this.childs = childs;
        this.id = id;
    }
    
    public Child[] getChilds() {
        return childs;
    }
    public void setChilds(Child[] childs) {
        this.childs = childs;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String[] getAttributes() {
        return attributes;
    }
    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
}
