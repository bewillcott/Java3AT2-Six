/*
 *  File Name:    CSVTableController.java
 *  Project Name: Java3AT2-Six
 *
 *  Copyright (c) 2021 Bradley Willcott
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ****************************************************************
 * Name: Bradley Willcott
 * ID:   M198449
 * Date: 13 Oct 2021
 * ****************************************************************
 */

package com.bewsoftware.tafe.java3.at2.six.view;

import com.bewsoftware.tafe.java3.at2.six.App;
import com.bewsoftware.tafe.java3.at2.six.util.ViewController;
import com.bewsoftware.tafe.java3.at2.six.util.Views;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.bewsoftware.tafe.java3.at2.six.util.Constants.log;
import static com.bewsoftware.tafe.java3.at2.six.util.Views.CSVTABLE;

/**
 * FXML Controller CSVTable view.
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 */
public class CSVTableController implements ViewController
{
    @FXML
    private AnchorPane anchorPane;

    private App app;

    private ObservableList<String> columns = null;

    @FXML
    private TableView<ObservableList<String>> csvTableView;

    private ObservableList<ObservableList<String>> data = null;

    private EditFormController editFormController = null;

    @Override
    public void setApp(App app)
    {
        this.app = app;

        try
        {
            loadCSVData(app.getFileName(), true);
        } catch (IOException | CsvValidationException ex)
        {
            log(ex.toString());
        }

        buildTableView();
        app.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case App.PROP_ACTIVEVIEW ->
            {
                if ((Views) evt.getOldValue() == CSVTABLE)
                {
                    app.removePropertyChangeListener(this);
                }
            }

            case EditFormController.PROP_UPDATE ->
            {
                app.setDataIsDirty((boolean) evt.getNewValue());
            }

            case App.PROP_DATAISDIRTY ->
            {
                if ((boolean) evt.getNewValue())
                {
                    csvTableView.refresh();
                    editFormController = null;
                }
            }

            default ->
            {
            }
        }
    }

    @Override
    public void setFocus()
    {
        // TODO: Add code
    }

    /**
     * Build the CSV TableView
     */
    private void buildTableView()
    {

        // Setup the columns
        int i = 0;

        for (String column : columns)
        {
            final int j = i;

            TableColumn<ObservableList<String>, String> col = new TableColumn<>(column);

            // TODO: Work out how to let user select column cell data type
            col.setCellValueFactory((CellDataFeatures<ObservableList<String>, String> param)
                    -> new SimpleStringProperty(param.getValue().get(j)));

            // TODO: Work out how to make the cells Editable
            // This is not the full story ...
//            col.setEditable(true);
            csvTableView.getColumns().add(col);
            i++;
        }

        // Add the data
        csvTableView.setItems(data);
        csvTableView.setEditable(true);

        csvTableView.setRowFactory(param ->
        {
            final TableRow<ObservableList<String>> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();

            // Edit menu item
            final MenuItem editMenuItem = new MenuItem("Edit");

            editMenuItem.setOnAction(t ->
            {
                ObservableList<String> currentItem = csvTableView.getItems().get(row.getIndex());
                showEditFormDialog(columns, currentItem);
            });

            contextMenu.getItems().add(editMenuItem);

            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });
    }

    /**
     * Load the data from the CSV file into the lists.
     *
     * @param csvPath    Path to the CSV file.
     * @param headerLine Is the first line the header line?
     *
     * @throws IOException            if any
     * @throws CsvValidationException if any
     */
    private void loadCSVData(final Path csvPath, final boolean headerLine) throws IOException, CsvValidationException
    {

        try (Reader reader = Files.newBufferedReader(csvPath);
                CSVReader csvReader = new CSVReader(reader))
        {
            data = FXCollections.observableArrayList();
            boolean firstline = headerLine;
            String[] line;

            while ((line = csvReader.readNext()) != null)
            {
                ObservableList<String> row = FXCollections.observableArrayList(Arrays.asList(line));

                if (firstline)
                {
                    columns = row;
                    firstline = false;
                } else
                {
                    data.add(row);
                }
            }
        }

        log(Objects.requireNonNull(columns, "Failed").toString());
        log("=".repeat(100));

        data.forEach((ObservableList<String> t) -> log(t.toString()));
    }

    /**
     * Opens the popup Edit Form dialog.
     *
     * @param columns The CSV file header.
     * @param rowData The currently selected table row.
     */
    private void showEditFormDialog(ObservableList<String> columns, ObservableList<String> rowData)
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class
                    .getResource("view/EditForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Get the Controller
            editFormController = loader.getController();
            editFormController.addPropertyChangeListener(this);
            editFormController.setData(columns, rowData);

            // Create the dialog Stage.
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(app.getPrimaryStage());
            Scene scene = new Scene(page);

            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException ex)
        {
            Logger.getLogger(RootLayoutController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
