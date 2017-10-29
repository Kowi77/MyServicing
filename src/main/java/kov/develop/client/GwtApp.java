package kov.develop.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import kov.develop.client.ui.WidgetPanel;
import kov.develop.shared.Point;
import kov.develop.shared.PointResult;
import kov.develop.shared.PointType;

import java.util.*;
import java.util.stream.Collectors;

/**
 *     Entry point class
**/
public class GwtApp implements EntryPoint {

    //Main Table Panel
    final VerticalPanel mainPanel = new VerticalPanel();
    //Choice Panel
    final HorizontalPanel choicePanel = new HorizontalPanel();
    //Cache data
    List<PointResult> pointsList ;
    //Type of point menu
    final WidgetPanel typePanel = new WidgetPanel(Arrays.asList(PointType.values()).stream().map(t -> t.toString()).collect(Collectors.toSet()));
    //Country menu
    WidgetPanel countryPanel;
    //sity menu
    WidgetPanel sityPanel;

    private final GwtAppServiceAsync gwtAppService = GWT.create(GwtAppService.class);

    //Create main table with dynamic loading data
    private ListDataProvider<PointResult> createFullTable(CellTable<PointResult> table) {
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
                GWT.log("Ошибка при загрузке списка", throwable);
            }

            @Override
            public void onSuccess(List<PointResult> points) {
                pointsList = new ArrayList<>(points);
                RootPanel.get().add(new HTML("%%%%%%%%List" + pointsList.size()));
                //refreshChoicePanel(points);
                dataProvider.getList().addAll(points);
            }
        });
        return dataProvider;
    }

    //Create main table with dynamic loading data by type
    private ListDataProvider<PointResult> createTableByType(PointType type, CellTable<PointResult> table) {
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

        this.gwtAppService.getAllPointsByType(type, new AsyncCallback<List<PointResult>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Ошибка при загрузке списка", throwable);
            }

            @Override
            public void onSuccess(List<PointResult> points) {
               /* pointsList = new ArrayList<>(points);
                RootPanel.get().add(new HTML("----------List" + pointsList.size()));*/
                dataProvider.getList().addAll(points);
            }
        });
        return dataProvider;
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        // Set main elements
        RootPanel.get("choicePanelContainer").add(choicePanel);
        RootPanel.get("mainPanelContainer").add(mainPanel);

        // Fill table
        CellTable<PointResult> table = new CellTable<PointResult>();
        ListDataProvider<PointResult> dataProvider = createFullTable(table);
        mainPanel.add(table);

        // Fill choicePanel
        choicePanel.setSpacing(5);
        choicePanel.add(typePanel.getListBox());
        /*countryPanel = new WidgetPanel(pointsList.stream().map(p -> p.getCountry()).collect(Collectors.toSet()));
        sityPanel = new WidgetPanel(pointsList.stream().map(p -> p.getSity()).collect(Collectors.toSet()));
        choicePanel.add(countryPanel);
        choicePanel.add(sityPanel);*/



        typePanel.getListBox().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                PointType type =  PointType.valueOf(typePanel.getListBox().getSelectedItemText());
                if (!type.toString().equals("")){
                     for (PointResult point : pointsList) {
                         if (point.getType().toString().equals(typePanel.getListBox().getSelectedItemText()))
                             RootPanel.get().add(new HTML(point.getName()));
                     }
                    List<PointResult> list = pointsList.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());
                    RootPanel.get().add(new HTML(list.stream().map(p -> p.getCountry()).collect(Collectors.toSet()).toString()));

                   // choicePanel.remove(countryPanel.getListBox());
                   // choicePanel.remove(sityPanel.getListBox());
                    countryPanel = new WidgetPanel(list.stream().map(p -> p.getCountry()).collect(Collectors.toSet()));
                    sityPanel = new WidgetPanel(list.stream().map(p -> p.getSity()).collect(Collectors.toSet()));
                    choicePanel.add(countryPanel.getListBox());
                    choicePanel.add(sityPanel.getListBox());
                    mainPanel.clear();
                    CellTable<PointResult> table = new CellTable<PointResult>();
                    ListDataProvider<PointResult> dataProvider = createTableByType(type, table);
                    mainPanel.add(table);
                 }
            }
        });
        countryPanel.getListBox().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                   RootPanel.get().add(new HTML("Hello world!"));
            }
        });
    }



    /*public void refreshChoicePanel(List<PointResult> points){
        countryPanel = new WidgetPanel(points.stream().map(p -> p.getCountry()).collect(Collectors.toSet()));
        sityPanel = new WidgetPanel(points.stream().map(p -> p.getSity()).collect(Collectors.toSet()));
        choicePanel.add(countryPanel);
        choicePanel.add(sityPanel);
    }*/
}







        /*
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
