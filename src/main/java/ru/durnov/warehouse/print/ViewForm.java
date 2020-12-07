package ru.durnov.warehouse.print;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Order;

public class ViewForm {
    private final PrintForm printForm;
    private Scene scene;
    private GridPane rootNode;
    private Stage stage;
    private Order order;

    public ViewForm(Order order){
        this.order = order;
        this.printForm = new PrintForm(order);
    }

    public void show(){
        this.rootNode = new GridPane();
        this.scene = new Scene(rootNode);
        this.stage = new Stage();
        stage.setScene(scene);
        Button buttonPrint = new Button("Печать");
        Button buttonClose = new Button("Закрыть");
        //buttonPrint.setOnAction(ae -> this.printForm.print());
        buttonPrint.setOnAction(ae -> print());
        buttonClose.setOnAction(ae -> stage.close());
        printForm.getGridPane().setAlignment(Pos.CENTER);
        rootNode.add(printForm.getGridPane(), 0,0, 2, 1);
        rootNode.add(buttonPrint, 0,1);
        rootNode.add(buttonClose, 0, 2);
        rootNode.setVgap(20);
        rootNode.setPadding(new Insets(10,10,10,10));
        stage.setResizable(false);
        stage.show();
    }

    public void print(){
        javafx.print.PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 10, 50, 10, 10);
        jobSettings.setPageLayout(pageLayout);
        GridPane printPane = new GridPane();
        printPane.getTransforms().add(new Scale(0.9,0.9));
        PrintForm printForm1 = new PrintForm(this.order);
        printPane.add(this.printForm.getGridPane(), 0, 0);
        printPane.setHgap(20);
        printPane.add(printForm1.getGridPane(), 2, 0);
        stage.close();
        boolean proceed = job.showPrintDialog(null);
        if (proceed) {
            boolean succes = job.printPage(pageLayout, printPane);
            if (succes) job.endJob();
        }
    }
}
