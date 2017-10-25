package kov.develop.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;

public class Binder {
    interface MyUiBinder extends UiBinder<DivElement, Binder> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField SpanElement nameSpan;

    private DivElement root;

    public Binder() {
        root = uiBinder.createAndBindUi(this);
    }

    public Element getElement() {
        return root;
    }

    public void setName(String name) { nameSpan.setInnerText(name); }
}