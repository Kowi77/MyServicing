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

    //Fill table with dynamic loading data
    private ListDataProvider<PointResult> fillTable(ListDataProvider<PointResult> dataProvider) {
        this.gwtAppService.getAllPoints(new AsyncCallback<List<PointResult>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Ошибка при загрузке списка", throwable);
            }

            @Override
            public void onSuccess(List<PointResult> points) {
                pointsList = new ArrayList<>(points);
                dataProvider.getList().addAll(points);
            }
        });
        return dataProvider;
    }

    //Fill table with dynamic loading data by type
    private ListDataProvider<PointResult> fillTableByType(PointType type, ListDataProvider<PointResult> dataProvider) {
        this.gwtAppService.getAllPointsByType(type, new AsyncCallback<List<PointResult>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Ошибка при загрузке списка", throwable);
            }

            @Override
            public void onSuccess(List<PointResult> points) {
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

        // Create table
        CellTable<PointResult> table = new CellTable<PointResult>();
        ListDataProvider<PointResult> dataProvider = GwtUtil.createTable(table);

        //Fill table
        fillTable(dataProvider);
        mainPanel.add(table);

        // Fill choicePanel
        choicePanel.setSpacing(5);
        choicePanel.add(typePanel.getListBox());



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
              /*      countryPanel = new WidgetPanel(list.stream().map(p -> p.getCountry()).collect(Collectors.toSet()));
                    sityPanel = new WidgetPanel(list.stream().map(p -> p.getSity()).collect(Collectors.toSet()));
                    choicePanel.add(countryPanel.getListBox());
                    choicePanel.add(sityPanel.getListBox());*/
                    refreshChoicePanel(list);
                    mainPanel.clear();
                    CellTable<PointResult> table = new CellTable<PointResult>();
                    ListDataProvider<PointResult> dataProvider = GwtUtil.createTable(table);
                    //Fill table
                    fillTableByType(type, dataProvider);
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



    public void refreshChoicePanel(List<PointResult> points){
        choicePanel.clear();
        choicePanel.add(typePanel.getListBox());
        if (typePanel.getListBox().getSelectedItemText().equals(""))
            return;
        countryPanel = new WidgetPanel(points.stream().map(p -> p.getCountry()).collect(Collectors.toSet()));
        sityPanel = new WidgetPanel(points.stream().map(p -> p.getSity()).collect(Collectors.toSet()));
        choicePanel.add(countryPanel);
        choicePanel.add(sityPanel);
    }
}

