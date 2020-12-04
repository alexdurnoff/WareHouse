package ru.durnov.warehouse.print;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Order;

public class ViewForm {
    private final PrintForm printForm;
    private Scene scene;
    private GridPane rootNode;

    public ViewForm(Order order){
        this.printForm = new PrintForm(order);
    }

    public void show(){
        this.rootNode = new GridPane();
        this.scene = new Scene(rootNode, 430, 640);
        Stage stage = new Stage();
        stage.setScene(scene);
        Button buttonPrint = new Button("Печать");
        Button buttonClose = new Button("Закрыть");
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
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
        jobSettings.setPageLayout(pageLayout);
        double scaleX = pageLayout.getPrintableWidth() / this.printForm.getGridPane().getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / this.printForm.getGridPane().getBoundsInParent().getHeight();
        this.rootNode.getTransforms().add(new Scale(scaleX, scaleY));
        if (job == null) return;
        boolean proceed = job.showPrintDialog(null);
        if (proceed) {
            boolean succes = job.printPage(pageLayout, this.printForm.getGridPane());
            if (succes) job.endJob();
        }
    }
}
