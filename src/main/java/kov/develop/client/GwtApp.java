package kov.develop.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import kov.develop.client.ui.WidgetPanel;
import kov.develop.shared.PointResult;

import java.util.List;

/** Entry point classes define <code>onModuleLoad()</code>. */
public class GwtApp implements EntryPoint {

    final VerticalPanel mainPanel = new VerticalPanel();

    final Button confirmButton = new Button("Confirm");
    final TextBox countryField = new TextBox();
    final TextBox sityField = new TextBox();
    final TextBox adressField = new TextBox();
    final TextBox nameField = new TextBox();
    final TextBox phoneField = new TextBox();
    final TextBox typeField = new TextBox();

    final Label errorLabel = new Label();
    final Label tableName = new Label();

    VerticalPanel dialogVPanel = new VerticalPanel();
    final DialogBox dialogBox = new DialogBox();
    final HTML serverResponseHtml = new HTML();
    final Label sendToServerLabel = new Label();
    final Button closeButton = new Button("Close");

    private final GwtAppServiceAsync gwtAppService = GWT.create(GwtAppService.class);

    /**
     * Create table with dynamic loading data
     * @param table base widget.
     * @return data provider.
     */
    private ListDataProvider<PointResult> createTable(CellTable<PointResult> table) {
        TextColumn<PointResult> countryColumn = new TextColumn<PointResult>() {
            @Override
            public String getValue(PointResult point) {
                return point.getCountry();
            }
        };
        TextColumn<PointResult> sityColumn = new TextColumn<PointResult>() {
            @Override
            public String getValue(PointResult point) {
                return point.getSity();
            }
        };
        TextColumn<PointResult> adressColumn = new TextColumn<PointResult>() {
            @Override
            public String getValue(PointResult point) {
                return point.getAdress();
            }
        };
        TextColumn<PointResult> nameColumn = new TextColumn<PointResult>() {
            @Override
            public String getValue(PointResult point) {
                return point.getName();
            }
        };
        TextColumn<PointResult> phoneColumn = new TextColumn<PointResult>() {
            @Override
            public String getValue(PointResult point) {
                return point.getPhone();
            }
        };
        TextColumn<PointResult> typeColumn = new TextColumn<PointResult>() {
            @Override
            public String getValue(PointResult point) {
                return point.getType().toString();
            }
        };
        table.addColumn(countryColumn, "Страна");
        table.addColumn(sityColumn, "Город");
        table.addColumn(adressColumn, "Адрес");
        table.addColumn(nameColumn, "Имя");
        table.addColumn(phoneColumn, "Телефон");
        table.addColumn(typeColumn, "Услуги");
        ListDataProvider<PointResult> dataProvider = new ListDataProvider<PointResult>();
        dataProvider.addDataDisplay(table);

        this.gwtAppService.getAllPoints(new AsyncCallback<List<PointResult>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("error!!", throwable);
            }

            @Override
            public void onSuccess(List<PointResult> point) {
                dataProvider.getList().addAll(point);
            }
        });
        return dataProvider;
    }

    /** This is the entry point method.*/
    public void onModuleLoad() {
        /*
        Виджет и биндер
         */
       /* Binder helloWorld = new Binder();
        helloWorld.setName("World (div element)");
        RootPanel.getBodyElement().appendChild(helloWorld.getElement());*/

        WidgetPanel typePanel = new WidgetPanel("Отправка", "Выдача", "Отправка и выдача");
        typePanel.setStyleName("typePanel");
        typePanel.getListBox().setStyleName("listBoxStyle");

        RootPanel.get().add(typePanel);


        tableName.setText("Список пунктов обслуживания");

        CellTable<PointResult> table = new CellTable<PointResult>();
        ListDataProvider<PointResult> dataProvider = createTable(table);

        mainPanel.add(table);
        mainPanel.setStyleName("mainPanel");
        RootPanel.get().add(tableName);
        RootPanel.get().add(mainPanel);

        /*final Label countryLabel = new Label();
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
        *//*Связываем id='' на html странице с компонентами *//*
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
    }*/

/*    private void sendInfoToServer() {
        //validate input text
        errorLabel.setText("");
        final Point[] point = {new Point()};
        gwtAppService.getAllPoints(new AsyncCallback<List<Point>>() {
            @Override
            public void onFailure(Throwable caught) {
                dialogBox.setText("Remote Procedure Call - Failure");
                serverResponseHtml.addStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML("ERROR ON GET_ALL");
                dialogBox.center();
                closeButton.setFocus(true);
            }

            @Override
            public void onSuccess(List<Point> result) {
                point[0] = result.get(0);
            }
        });
        *//*Point point = new Point();
        point.setCountry(countryField.getText());
        point.setSity(sityField.getText());
        point.setAdress(adressField.getText());
        point.setName(nameField.getText());
        point.setPhone(phoneField.getText());*//*
       *//* point.setType(typeField.getElement());*//*

        if (!FieldValidator.isValidData(point[0].getName())) { //отобразить ошибку на html странице
            errorLabel.setText("Имя должно содержать больше трех символов");
            return;
        }
        sendToServerLabel.setText(point[0].toString());
        confirmButton.setEnabled(false);
        serverResponseHtml.setText("");
        gwtAppService.gwtAppCallServer(point[0], new AsyncCallback<Point>() {
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
        });*/



    }

}
