package kov.develop.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import kov.develop.client.ui.Binder;
import kov.develop.client.ui.WidgetPanel;
import kov.develop.model.Point;
import kov.develop.model.PointType;
import kov.develop.shared.FieldValidator;

/** Entry point classes define <code>onModuleLoad()</code>. */
public class GwtApp implements EntryPoint {

    final Button confirmButton = new Button("Confirm");
    final TextBox countryField = new TextBox();
    final TextBox sityField = new TextBox();
    final TextBox adressField = new TextBox();
    final TextBox nameField = new TextBox();
    final TextBox phoneField = new TextBox();
    final TextBox typeField = new TextBox();

    final Label errorLabel = new Label();
    final Label helloLabel = new Label();

    VerticalPanel dialogVPanel = new VerticalPanel();
    final DialogBox dialogBox = new DialogBox();
    final HTML serverResponseHtml = new HTML();
    final Label sendToServerLabel = new Label();
    final Button closeButton = new Button("Close");

    private final GwtAppServiceAsync gwtAppService = GWT.create(GwtAppService.class);

    /** This is the entry point method.*/
    public void onModuleLoad() {
        /*
        Виджет и биндер
         */
/*
        Binder helloWorld = new Binder();
        helloWorld.setName("World (div element)");
        RootPanel.getBodyElement().appendChild(helloWorld.getElement());

        WidgetPanel helloWidgetPanel = new WidgetPanel("driver", "biker", "walker");
        helloWidgetPanel.setStyleName("helloWidgetPanel");
        helloWidgetPanel.getListBox().setStyleName("listBoxStyle");

        RootPanel.get("panelId").add(helloWidgetPanel);*/


        helloLabel.setText("GwtApp Application hello world");

        final Label countryLabel = new Label();
        countryLabel.setText("country: ");
        final Label sityLabel = new Label();
        sityLabel.setText("sity: ");
        final Label adressLabel = new Label();
        adressLabel.setText("adress: ");
        final Label nameLabel = new Label();
        nameLabel.setText("name: ");
        final Label phoneLabel = new Label();
        phoneLabel.setText("phone: ");
        final Label typeLabel = new Label();
        typeLabel.setText("type: ");
        /*Связываем id='' на html странице с компонентами */
        RootPanel.get("helloId").add(helloLabel);

        RootPanel.get("countryLabelId").add(countryLabel);
        RootPanel.get("countryId").add(countryField);
        RootPanel.get("sityLabelId").add(sityLabel);
        RootPanel.get("sityId").add(sityField);
        RootPanel.get("adressLabelId").add(adressLabel);
        RootPanel.get("adressId").add(adressField);
        RootPanel.get("nameLabelId").add(nameLabel);
        RootPanel.get("nameId").add(nameField);
        RootPanel.get("phoneLabelId").add(phoneLabel);
        RootPanel.get("phoneId").add(phoneField);
        RootPanel.get("typeLabelId").add(typeLabel);
        RootPanel.get("typeId").add(typeField);

        RootPanel.get("confirmButtonId").add(confirmButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Create the popup dialog box
        dialogBox.setText("Remote procedure call from server");
        dialogBox.setAnimationEnabled(true);

        closeButton.getElement().setId("closeButtonId");

        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Отправленные поля на сервер:</b>"));
        dialogVPanel.add(sendToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Ответ сервера:</b>"));
        dialogVPanel.add(serverResponseHtml);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        //обработчик для клика по кнопке 'Confirm'
        confirmButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                confirmButton.setEnabled(false);
                sendInfoToServer();
            }
        });

        //обработчик по нажатию enter в текстовом поле
        nameField.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendInfoToServer();
                }
            }
        });
        //обработчик по клику на кнопку 'Close' в диалоговом окне
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                confirmButton.setEnabled(true);
                confirmButton.setFocus(true);
            }
        });
    }

    private void sendInfoToServer() {
        //validate input text
        errorLabel.setText("");
        Point point = new Point();
        point.setCountry(countryField.getText());
        point.setSity(sityField.getText());
        point.setAdress(adressField.getText());
        point.setName(nameField.getText());
        point.setPhone(phoneField.getText());
       /* point.setType(typeField.getElement());*/

        if (!FieldValidator.isValidData(point.getName())) { //отобразить ошибку на html странице
            errorLabel.setText("Имя должно содержать больше трех символов");
            return;
        }
        sendToServerLabel.setText(point.toString());
        confirmButton.setEnabled(false);
        serverResponseHtml.setText("");
        gwtAppService.gwtAppCallServer(point, new AsyncCallback<Point>() {
            public void onFailure(Throwable caught) {
                dialogBox.setText("Remote Procedure Call - Failure");
                serverResponseHtml.addStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML("ERROR ON SERVER");
                dialogBox.center();
                closeButton.setFocus(true);
            }
            public void onSuccess(Point pointResult) {
                dialogBox.setText("Remote Procedure Call");
                serverResponseHtml.removeStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML(pointResult.toString());
                dialogBox.center();
                closeButton.setFocus(true);
            }
        });

    }

}
