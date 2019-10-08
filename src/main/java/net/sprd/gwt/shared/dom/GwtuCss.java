package net.sprd.gwt.shared.dom;

import elemental2.dom.CSSProperties.HeightUnionType;
import elemental2.dom.CSSProperties.MarginTopUnionType;
import elemental2.dom.CSSProperties.MarginUnionType;
import elemental2.dom.CSSProperties.MaxHeightUnionType;
import elemental2.dom.CSSProperties.MaxWidthUnionType;
import elemental2.dom.CSSProperties.WidthUnionType;

public interface GwtuCss {

    public HeightUnionType height(String height);

    public WidthUnionType width(String width);

    public MarginUnionType margin(String margin);

    public MaxHeightUnionType maxHeight(String maxHeight);

    public MaxWidthUnionType maxWidth(String maxWidth);

    public MarginTopUnionType marginTop(String marginTop);

}
