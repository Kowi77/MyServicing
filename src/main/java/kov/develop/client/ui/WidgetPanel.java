package kov.develop.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.List;
import java.util.Set;

public class WidgetPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, WidgetPanel> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    ListBox listBox;

    public WidgetPanel(Set<String> names) {
        // sets listBox
        initWidget(uiBinder.createAndBindUi(this));
        listBox.addItem("");
        for (String name : names) {
            listBox.addItem(name);
            System.out.println(name);
        }
    }

    public ListBox getListBox() {
        return listBox;
    }
}

