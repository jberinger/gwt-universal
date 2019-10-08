package net.sprd.gwt.server.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sprd.gwt.server.dom.element.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.steadystate.css.dom.CSSStyleDeclarationImpl;

import elemental2.dom.DOMTokenList;
import elemental2.dom.HTMLElement;
import net.sprd.gwt.shared.dom.Attributes;
import net.sprd.gwt.shared.dom.GwtuCss;
import net.sprd.gwt.shared.dom.GwtuDom;
import net.sprd.gwt.shared.dom.IdService;
import net.sprd.gwt.shared.dom.Tags;

public class GwtuDomServer extends GwtuDom {
    
    private static final String BODY_ID = "$body";

    private String rootId = BODY_ID;
    
    private String idPrefix = "";
    
    private Map<String, HTMLElement> elementMap = new HashMap<>();
    
    private Map<String, List<String>> childrenMap = new HashMap<>();
    
    private IdService idService = new IdService();
    
    
    public < T extends HTMLElement> T create(String tag, String id, HTMLElement parent, elemental2.dom.Element nextSibling, elemental2.dom.Element previousSibling, String... props) {
        HTMLElement next = null;
        if (nextSibling != null && nextSibling instanceof HTMLElement) {
            next = (HTMLElement) nextSibling;
        }
        HTMLElement prev = null;
        if (previousSibling != null && previousSibling instanceof HTMLElement) {
            prev = (HTMLElement) previousSibling;
        }
        return create(tag, id, parent!= null?parent.id:null, next!= null?next.id:null, prev!= null?prev.id:null, props);
    }
    
    @Override
    public boolean isServer() {
        return true;
    }


    @Override
    public boolean isInitialPhase() {
        return true;
    }


    @Override
    public void setInitialPhase(boolean initial) {
    }
    
    @Override
    public void setAttribute(elemental2.dom.Element element, String name, String value) {
        ServerHTMLElement serverElement = (ServerHTMLElement) element;
        serverElement.setDataAttribute(name, value);
    }

    @Override
    public GwtuCss getGwtuCss() {
        return GwtuCssServer.getInstance();
    }
    
    
    public < T extends HTMLElement> T create(String tag, String id, String parentId, String nextSiblingId, String previousSiblingId, String... props) {
        id = idService.getId((id==null || id.isEmpty())?idPrefix+getTagId(tag): id);
        T element = createNative(tag, id, parentId, nextSiblingId, previousSiblingId);
        addAttributes(element, props);
        return element;
    }
    
    private < T extends HTMLElement> T createNative(String tag, String id, String parentId, String nextSiblingId, String previousSiblingId) {
        T element = createElement(tag);
        if (element != null) {
            element.id = id;
            elementMap.put(id, element);
            if (parentId== null) {
                parentId = rootId;
            }
            List<String> siblings = childrenMap.get(parentId);
            if (siblings == null) {
                siblings = new ArrayList<>();
                childrenMap.put(parentId, siblings);
            }
            if (nextSiblingId != null) {
                int pos = 0;
                while(pos < siblings.size() && !siblings.get(pos).equals(nextSiblingId)) {
                    pos ++;
                }
                if (pos<siblings.size()) {
                    siblings.add(pos, id);
                } else {
                    siblings.add(id);
                }
            } else if (previousSiblingId != null) {
                int pos = 0;
                while(pos < siblings.size() && !siblings.get(pos).equals(previousSiblingId)) {
                    pos ++;
                }
                if (pos+1<siblings.size()) {
                    siblings.add(pos+1, id);
                } else {
                    siblings.add(id);
                }
            } else {
                siblings.add(id);
            }
        }
        return element;
    }

    private < T extends HTMLElement> T createElement(String tag) {
        switch(tag) {
            case Tags.ADDRESS: return (T) new ServerAddressElement();
            case Tags.ANCHOR: return (T) new ServerAnchorElement();
            case Tags.ARTICLE: return (T) new ServerArticleElement();
            case Tags.ASIDE: return (T) new ServerAsideElement();
            case Tags.BREAK: return (T) new ServerBreakElement();
            case Tags.BUTTON: return (T) new ServerButtonElement();
            case Tags.DATALIST: return (T)new ServerDataListElement();
            case Tags.DIV: return (T)new ServerDivElement();
            case Tags.FIELDSET: return (T)new ServerFieldSetElement();
            case Tags.FIGCAPTION: return (T)new ServerFigcaptionElement();
            case Tags.FIGURE: return (T)new ServerFigureElement();
            case Tags.FOOTER: return (T)new ServerFooterElement();
            case Tags.FORM: return (T)new ServerFormElement();
            case Tags.H1: return (T) new ServerHeadingElement();
            case Tags.H2: return (T) new ServerHeading2Element();
            case Tags.H3: return (T) new ServerHeading3Element();
            case Tags.H4: return (T) new ServerHeading4Element();
            case Tags.H5: return (T) new ServerHeading5Element();
            case Tags.H6: return (T) new ServerHeading6Element();
            case Tags.HEADER: return (T) new ServerHeaderElement();
            case Tags.HGROUP: return (T) new ServerHGroupElement();
            case Tags.IMAGE: return (T)new ServerImageElement();
            case Tags.INPUT: return (T)new ServerInputElement();
            case Tags.LABEL: return (T)new ServerLabelElement();
            case Tags.LEGEND: return (T)new ServerLegendElement();
            case Tags.LI: return (T)new ServerListItemElement();
            case Tags.MAIN: return (T)new ServerMainElement();
            case Tags.METER: return (T)new ServerMeterElement();
            case Tags.NAV: return (T)new ServerNavElement();
            case Tags.OLIST: return (T)new ServerOListElement();
            case Tags.OPTGROUP: return (T)new ServerOptGroupElement();
            case Tags.OPTION: return (T)new ServerOptionElement();
            case Tags.OUTPUT: return (T)new ServerOutputElement();
            case Tags.PARAGRAPH: return (T)new ServerParagraphElement();
            case Tags.PROGRESS: return (T)new ServerProgressElement();
            case Tags.SCRIPT: return (T) new ServerScriptElement();
            case Tags.SECTION: return (T)new ServerSectionElement();
            case Tags.SELECT: return (T)new ServerSelectElement();
            case Tags.SPAN: return (T)new ServerSpanElement();
            case Tags.TABLE: return (T) new ServerTableElement();
            case Tags.TBODY: return (T) new ServerTableSectionElement();
            case Tags.TD: return (T) new ServerTableCellElement();
            case Tags.TEXTAREA: return (T)new ServerTextAreaElement();
            case Tags.TH: return (T) new ServerTableCaptionElement();
            case Tags.TR: return (T) new ServerTableRowElement();
            case Tags.ULIST: return (T)new ServerUListElement();
        }
        
        return null;
    }
    
    public String render() {
        Document doc = Jsoup.parse("<!doctype html>\\n<html><body></body></html>");
        doc.outputSettings().indentAmount(0).prettyPrint(false);
        org.jsoup.nodes.Element rootElement = doc.body();
        List<String> childrenIds = childrenMap.get(rootId);
        List<org.jsoup.nodes.Element> nodes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (childrenIds != null) {
            for(String childId:childrenIds) {
                HTMLElement child = elementMap.get(childId);
                org.jsoup.nodes.Element node = create(child);
                nodes.add(node);
                rootElement.appendChild(node);
                sb.append(node.outerHtml());
            }
        }
        return sb.toString();
        
    }
    
    public void render(Document doc) {
        Element rootElement = BODY_ID.equals(rootId)?doc.body():doc.getElementById(rootId);
        List<String> childrenIds = childrenMap.get(rootId);
        if (childrenIds != null) {
            for(String childId:childrenIds) {
                HTMLElement child = elementMap.get(childId);
                rootElement.appendChild(create(child));
            }
        }
    }

    private Element create(HTMLElement element) {
        if (element instanceof ServerAddressElement) {
            return createAddress((ServerAddressElement)element);
        }
        if (element instanceof ServerAnchorElement) {
            return createAnchor((ServerAnchorElement)element);
        }
        if (element instanceof ServerArticleElement) {
            return createArticle((ServerArticleElement)element);
        }
        if (element instanceof ServerAsideElement) {
            return createAside((ServerAsideElement)element);
        }
        if (element instanceof ServerBodyElement) {
            return createBody((ServerBodyElement)element);
        }
        if (element instanceof ServerBreakElement) {
            return createBreak((ServerBreakElement)element);
        }
        if (element instanceof ServerButtonElement) {
            return createButton((ServerButtonElement)element);
        }
        if (element instanceof ServerDataListElement) {
            return createDataList((ServerDataListElement)element);
        }
        if (element instanceof ServerDivElement) {
            return createDiv((ServerDivElement)element);
        }
        if (element instanceof ServerFigcaptionElement) {
            return createFigcaption((ServerFigcaptionElement) element);
        }
        if (element instanceof ServerFigureElement) {
            return createFigure((ServerFigureElement) element);
        }
        if (element instanceof ServerFieldSetElement) {
            return createFieldSet((ServerFieldSetElement)element);
        }
        if (element instanceof ServerFooterElement) {
            return createFooter((ServerFooterElement)element);
        }
        if (element instanceof ServerFormElement) {
            return createForm((ServerFormElement)element);
        }
        if (element instanceof ServerHeaderElement) {
            return createHeader((ServerHeaderElement)element);
        }
        if (element instanceof ServerHeadingElement) {
            return createHeading((ServerHeadingElement)element);
        }
        if (element instanceof ServerHeading2Element) {
            return createHeading2((ServerHeading2Element)element);
        }
        if (element instanceof ServerHeading3Element) {
            return createHeading3((ServerHeading3Element)element);
        }
        if (element instanceof ServerHeading4Element) {
            return createHeading4((ServerHeading4Element)element);
        }
        if (element instanceof ServerHeading5Element) {
            return createHeading5((ServerHeading5Element)element);
        }
        if (element instanceof ServerHeading6Element) {
            return createHeading6((ServerHeading6Element)element);
        }
        if (element instanceof ServerHGroupElement) {
            return createHGroup((ServerHGroupElement)element);
        }
        if (element instanceof ServerImageElement) {
            return createImage((ServerImageElement)element);
        }
        if (element instanceof ServerInputElement) {
            return createInput((ServerInputElement)element);
        }
        if (element instanceof ServerLabelElement) {
            return createLabel((ServerLabelElement)element);
        }
        if (element instanceof ServerLegendElement) {
            return createLegend((ServerLegendElement)element);
        }
        if (element instanceof ServerListItemElement) {
            return createListItem((ServerListItemElement)element);
        }
        if (element instanceof ServerMainElement) {
            return createMain((ServerMainElement)element);
        }
        if (element instanceof ServerMeterElement) {
            return createMeter((ServerMeterElement)element);
        }
        if (element instanceof ServerNavElement) {
            return createNav((ServerNavElement)element);
        }
        if (element instanceof ServerOListElement) {
            return createOList((ServerOListElement)element);
        }
        if (element instanceof ServerOptGroupElement) {
            return createOptGroup((ServerOptGroupElement)element);
        }
        if (element instanceof ServerOptionElement) {
            return createOption((ServerOptionElement)element);
        }
        if (element instanceof ServerOutputElement) {
            return createOutput((ServerOutputElement)element);
        }
        if (element instanceof ServerParagraphElement) {
            return createParagraph((ServerParagraphElement)element);
        }
        if (element instanceof ServerProgressElement) {
            return createProgress((ServerProgressElement)element);
        }
        if (element instanceof ServerScriptElement) {
            return createScript((ServerScriptElement)element);
        }
        if (element instanceof ServerSectionElement) {
            return createSection((ServerSectionElement)element);
        }
        if (element instanceof ServerSelectElement) {
            return createSelect((ServerSelectElement)element);
        }
        if (element instanceof ServerSpanElement) {
            return createSpan((ServerSpanElement)element);
        }
        if (element instanceof ServerTableElement) {
            return createTable((ServerTableElement)element);
        }
        if (element instanceof ServerTableCaptionElement) {
            return createTableCaption((ServerTableCaptionElement)element);
        }
        if (element instanceof ServerTableCellElement) {
            return createTableCell((ServerTableCellElement)element);
        }
        if (element instanceof ServerTableRowElement) {
            return createTableRow((ServerTableRowElement)element);
        }
        if (element instanceof ServerTableSectionElement) {
            return createTableSection((ServerTableSectionElement)element);
        }
        if (element instanceof ServerTextAreaElement) {
            return createTextArea((ServerTextAreaElement)element);
        }
        if (element instanceof ServerUListElement) {
            return createUList((ServerUListElement)element);
        }
        return null;
    }

    private Element createAddress(ServerAddressElement element) {
        Element addressElement = new Element(Tag.valueOf(Tags.ADDRESS), "");
        handleElement(addressElement, element);
        return addressElement;
    }

    private Element createAnchor(ServerAnchorElement element) {
        Element anchorElement = new Element(Tag.valueOf(Tags.ANCHOR), "");
        if (element.href != null) {
            anchorElement.attr(Attributes.HREF, element.href);
        }
        handleElement(anchorElement, element);
        return anchorElement;
    }

    private Element createArticle(ServerArticleElement element) {
        Element articleElement = new Element(Tag.valueOf(Tags.ARTICLE), "");
        handleElement(articleElement, element);
        return articleElement;
    }

    private Element createAside(ServerAsideElement element) {
        Element asideElement = new Element(Tag.valueOf(Tags.ASIDE), "");
        handleElement(asideElement, element);
        return asideElement;
    }

    private Element createBody(ServerBodyElement element) {
        Element divElement = new Element(Tag.valueOf(Tags.BODY), "");
        handleElement(divElement, element);
        return divElement;
    }

    private Element createBreak(ServerBreakElement element) {
        Element breakElement = new Element(Tag.valueOf(Tags.BREAK), "");
        handleElement(breakElement, element);
        return breakElement;
    }

    private Element createButton(ServerButtonElement element) {
        Element buttonElement = new Element(Tag.valueOf(Tags.BUTTON), "");
        handleElement(buttonElement, element);
        if (element.type != null) {
            buttonElement.attr(Attributes.TYPE, element.type);
        }
        return buttonElement;
    }

    private Element createDataList(ServerDataListElement element) {
        Element dataListElement = new Element(Tag.valueOf(Tags.DATALIST), "");
        handleElement(dataListElement, element);
        return dataListElement;
    }

    private Element createDiv(ServerDivElement element) {
        Element divElement = new Element(Tag.valueOf(Tags.DIV), "");
        handleElement(divElement, element);
        return divElement;
    }

    private Element createFieldSet(ServerFieldSetElement element) {
        Element fieldSetElement = new Element(Tag.valueOf(Tags.FIELDSET), "");
        handleElement(fieldSetElement, element);
        return fieldSetElement;
    }

    private Element createFigcaption(ServerFigcaptionElement element) {
        Element figcaptionElement = new Element(Tag.valueOf(Tags.FIGCAPTION), "");
        handleElement(figcaptionElement, element);
        return figcaptionElement;
    }

    private Element createFigure(ServerFigureElement element) {
        Element figureElement = new Element(Tag.valueOf(Tags.FIGURE), "");
        handleElement(figureElement, element);
        return figureElement;
    }

    private Element createFooter(ServerFooterElement element) {
        Element footerElement = new Element(Tag.valueOf(Tags.FOOTER), "");
        handleElement(footerElement, element);
        return footerElement;
    }

    private Element createForm(ServerFormElement element) {
        Element formElement = new Element(Tag.valueOf(Tags.FORM), "");
        handleElement(formElement, element);
        return formElement;
    }

    private Element createHeader(ServerHeaderElement element) {
        Element headerElement = new Element(Tag.valueOf(Tags.HEADER), "");
        handleElement(headerElement, element);
        return headerElement;
    }

    private Element createHeading(ServerHeadingElement element) {
        Element headlineElement = new Element(Tag.valueOf(Tags.H1), "");
        handleElement(headlineElement, element);
        return headlineElement;
    }

    private Element createHeading2(ServerHeading2Element element) {
        Element headlineElement = new Element(Tag.valueOf(Tags.H2), "");
        handleElement(headlineElement, element);
        return headlineElement;
    }

    private Element createHeading3(ServerHeading3Element element) {
        Element headlineElement = new Element(Tag.valueOf(Tags.H3), "");
        handleElement(headlineElement, element);
        return headlineElement;
    }

    private Element createHeading4(ServerHeading4Element element) {
        Element headlineElement = new Element(Tag.valueOf(Tags.H4), "");
        handleElement(headlineElement, element);
        return headlineElement;
    }

    private Element createHeading5(ServerHeading5Element element) {
        Element headlineElement = new Element(Tag.valueOf(Tags.H5), "");
        handleElement(headlineElement, element);
        return headlineElement;
    }

    private Element createHeading6(ServerHeading6Element element) {
        Element headlineElement = new Element(Tag.valueOf(Tags.H6), "");
        handleElement(headlineElement, element);
        return headlineElement;
    }

    private Element createHGroup(ServerHGroupElement element) {
        Element hGroupElement = new Element(Tag.valueOf(Tags.HGROUP), "");
        handleElement(hGroupElement, element);
        return hGroupElement;
    }

    private Element createImage(ServerImageElement element) {
        Element imageElement = new Element(Tag.valueOf(Tags.IMAGE), "");
        if (element.alt != null) {
            imageElement.attr(Attributes.ALT, element.alt);
        }
        if (element.src != null) {
            imageElement.attr(Attributes.SRC, element.src);
        }
        handleElement(imageElement, element);
        return imageElement;
    }

    private Element createInput(ServerInputElement element) {
        Element inputElement = new Element(Tag.valueOf(Tags.INPUT), "");
        if (element.type != null) {
            inputElement.attr(Attributes.TYPE, element.type);
        }
        if (element.value != null) {
            inputElement.attr(Attributes.VALUE, element.value);
        }
        if (element.checked) {
            inputElement.attr(Attributes.CHECKED, true);
        }
        if (element.disabled) {
            inputElement.attr(Attributes.DISABLED, true);
        }
        handleElement(inputElement, element);
        return inputElement;
    }

    private Element createLabel(ServerLabelElement element) {
        Element labelElement = new Element(Tag.valueOf(Tags.LABEL), "");
        handleElement(labelElement, element);
        return labelElement;
    }

    private Element createLegend(ServerLegendElement element) {
        Element legendElement = new Element(Tag.valueOf(Tags.LEGEND), "");
        handleElement(legendElement, element);
        return legendElement;
    }

    private Element createListItem(ServerListItemElement element) {
        Element listItemElement = new Element(Tag.valueOf(Tags.LI), "");
        handleElement(listItemElement, element);
        return listItemElement;
    }

    private Element createMain(ServerMainElement element) {
        Element mainElement = new Element(Tag.valueOf(Tags.MAIN), "");
        handleElement(mainElement, element);
        return mainElement;
    }

    private Element createMeter(ServerMeterElement element) {
        Element meterElement = new Element(Tag.valueOf(Tags.METER), "");
        handleElement(meterElement, element);
        return meterElement;
    }

    private Element createNav(ServerNavElement element) {
        Element navElement = new Element(Tag.valueOf(Tags.NAV), "");
        handleElement(navElement, element);
        return navElement;
    }

    private Element createOList(ServerOListElement element) {
        Element oListElement = new Element(Tag.valueOf(Tags.OLIST), "");
        handleElement(oListElement, element);
        return oListElement;
    }

    private Element createOptGroup(ServerOptGroupElement element) {
        Element optGroupElement = new Element(Tag.valueOf(Tags.OPTGROUP), "");
        handleElement(optGroupElement, element);
        return optGroupElement;
    }

    private Element createOption(ServerOptionElement element) {
        Element optionElement = new Element(Tag.valueOf(Tags.OPTION), "");
        handleElement(optionElement, element);
        return optionElement;
    }

    private Element createOutput(ServerOutputElement element) {
        Element outputElement = new Element(Tag.valueOf(Tags.OUTPUT), "");
        handleElement(outputElement, element);
        return outputElement;
    }

    private Element createParagraph(ServerParagraphElement element) {
        Element paragraphElement = new Element(Tag.valueOf(Tags.PARAGRAPH), "");
        handleElement(paragraphElement, element);
        return paragraphElement;
    }

    private Element createProgress(ServerProgressElement element) {
        Element progressElement = new Element(Tag.valueOf(Tags.PROGRESS), "");
        handleElement(progressElement, element);
        return progressElement;
    }

    private Element createScript(ServerScriptElement element) {
        Element scriptElement = new Element(Tag.valueOf(Tags.SCRIPT), "");
        if (element.src != null) {
            scriptElement.attr(Attributes.SRC, element.src);
        }
        if (element.type != null) {
            scriptElement.attr(Attributes.TYPE, element.type);
        }
        handleElement(scriptElement, element);
        return scriptElement;
    }

    private Element createSection(ServerSectionElement element) {
        Element sectionElement = new Element(Tag.valueOf(Tags.SECTION), "");
        handleElement(sectionElement, element);
        return sectionElement;
    }

    private Element createSelect(ServerSelectElement element) {
        Element selectElement = new Element(Tag.valueOf(Tags.SELECT), "");
        handleElement(selectElement, element);
        return selectElement;
    }

    private Element createSpan(ServerSpanElement element) {
        Element spanElement = new Element(Tag.valueOf(Tags.SPAN), "");
        handleElement(spanElement, element);
        return spanElement;
    }

    private Element createTable(ServerTableElement element) {
        Element tableElement = new Element(Tag.valueOf(Tags.TABLE), "");
        handleElement(tableElement, element);
        return tableElement;
    }

    private Element createTableCaption(ServerTableCaptionElement element) {
        Element tableCaptionElement = new Element(Tag.valueOf(Tags.TH), "");
        handleElement(tableCaptionElement, element);
        return tableCaptionElement;
    }

    private Element createTableCell(ServerTableCellElement element) {
        Element tableCellElement = new Element(Tag.valueOf(Tags.TD), "");
        handleElement(tableCellElement, element);
        return tableCellElement;
    }

    private Element createTableRow(ServerTableRowElement element) {
        Element tableRowElement = new Element(Tag.valueOf(Tags.TR), "");
        handleElement(tableRowElement, element);
        return tableRowElement;
    }

    private Element createTableSection(ServerTableSectionElement element) {
        Element tableSectionElement = new Element(Tag.valueOf(Tags.TBODY), "");
        handleElement(tableSectionElement, element);
        return tableSectionElement;
    }

    private Element createTextArea(ServerTextAreaElement element) {
        Element textAreaElement = new Element(Tag.valueOf(Tags.TEXTAREA), "");
        handleElement(textAreaElement, element);
        return textAreaElement;
    }

    private Element createUList(ServerUListElement element) {
        Element uListElement = new Element(Tag.valueOf(Tags.ULIST), "");
        handleElement(uListElement, element);
        return uListElement;
    }

    private void handleElement(Element soupElement, HTMLElement element) {
        soupElement.attr(Attributes.ID, element.id);
        
        if (element.textContent != null) {
            soupElement.text(element.textContent);
        }
        
        if (element.innerHTML != null) {
            soupElement.html(element.innerHTML);
        }
        
        if (element.className != null) {
            soupElement.classNames(addToSet(soupElement.classNames(), element.className));
        }
        
        if (element.classList != null && element.classList.getLength() > 0) {
            soupElement.classNames(addToSet(soupElement.classNames(),element.classList));
        }

        if (element.style != null) {
            handleCssStyle(soupElement, element);
        }

        if (element.title != null) {
            soupElement.attr(Attributes.TOOLTIP, element.title);
        }
        
        if (element instanceof ServerHTMLElement) {
            ServerHTMLElement serverElement = (ServerHTMLElement) element;
            Map<String, String> attributes = serverElement.getDataAttributes();
            if (!attributes.isEmpty()) {
                for(String name:attributes.keySet()) {
                    String value = attributes.get(name);
                    if (value != null) {
                        soupElement.attr(name, value);
                    }
                }
            }
        }

        List<String> childrenIds = childrenMap.get(element.id);
        if (childrenIds != null) {
            for(String childId:childrenIds) {
                HTMLElement child = elementMap.get(childId);
                Element soupChild = create(child);
                soupElement.appendChild(soupChild);
            }
        }
    }

    private Set<String> addToSet(Set<String> classNames, DOMTokenList classList) {
        if (classNames == null) {
            classNames = new HashSet<>();
        }
        if (classList != null && classList.getLength() > 0)
        classNames.addAll(classList.asList());
        return classNames;
    }

    private Set<String> addToSet(Set<String> classes, String className) {
        if (classes == null) {
            classes = new HashSet<>();
        }
        if (className!= null && !className.trim().isEmpty()) {
            for (String clazz:className.trim().split(" ")) {
                classes.add(clazz.trim());
            }
        }
        return classes;
    }

    private void handleCssStyle(Element soupElement, HTMLElement element) {
        CSSStyleDeclarationImpl style = new CSSStyleDeclarationImpl();
        if (element.style.cssText != null) {
            style.setCssText(element.style.cssText);
        }
        if (element.style.display != null) {
            style.setDisplay(element.style.display);
        }
        if (element.style.verticalAlign != null) {
            style.setVerticalAlign(element.style.verticalAlign);
        }
        
        if (element.style.fontFamily != null) {
            style.setFontFamily(element.style.fontFamily);
        }
        
        if (element.style.backgroundColor != null) {
            style.setBackgroundColor(element.style.backgroundColor);
        }
        
        if (element.style.margin != null) {
            style.setMargin(element.style.margin.asString());
        }
        
        if (element.style.height != null) {
            style.setHeight(element.style.height.asString());
        }
        
        if (element.style.width != null) {
            style.setWidth(element.style.width.asString());
        }
        
        if (element.style.maxHeight != null) {
            style.setMaxHeight(element.style.maxHeight.asString());
        }
        
        if (element.style.maxWidth != null) {
            style.setMaxWidth(element.style.maxWidth.asString());
        }
        
        String cssText = style.getCssText();
        if (cssText != null && !cssText.isEmpty()) {
            soupElement.attr(Attributes.STYLE, style.getCssText());
        }
        
    }
    
    public void addScriptUrl(Document doc, String scriptUrl) {
        Element scriptElement = doc.createElement("script");
        scriptElement.attr(Attributes.SRC, scriptUrl);
        doc.body().appendChild(scriptElement);
    }
    
    public void addScript(Document doc, String script) {
        Element scriptElement = doc.createElement("script");
        DataNode dataNode = new DataNode(script, "");
        scriptElement.appendChild(dataNode);
        doc.body().appendChild(scriptElement);
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }
    
    public void setIdPrefix(String idPrefix) {
        if (idPrefix == null) {
            this.idPrefix = "";
        } else {
            this.idPrefix = idPrefix;
        }
    }
    
    public void addGwtuDomServer(GwtuDomServer child) {
        childrenMap.putAll(child.childrenMap);
        elementMap.putAll(child.elementMap);
    }



}
