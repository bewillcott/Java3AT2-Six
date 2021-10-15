/*
 *  File Name:    RootLayoutController.java
 *  Project Name: GUIApp
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
 * Date: 1 Oct 2021
 * ****************************************************************
 */

package com.bewsoftware.tafe.java3.at2.six.view;

import com.bewsoftware.tafe.java3.at2.six.App;
import com.bewsoftware.tafe.java3.at2.six.util.ViewController;
import com.bewsoftware.tafe.java3.at2.six.util.Views;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static com.bewsoftware.tafe.java3.at2.six.util.Views.BLANK;
import static com.bewsoftware.tafe.java3.at2.six.util.Views.CSVTABLE;
import static javafx.scene.control.ButtonType.NO;
import static javafx.scene.control.ButtonType.YES;

/**
 * FXML Controller class for the 'RootLayout.fxml' file.
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 *
 * @since 1.0
 * @version 1.0
 */
public class RootLayoutController implements ViewController
{
    /**
     * Used by the
     * {@link #handleOpenMenuItem(javafx.event.ActionEvent) OpenMenuItem}
     * handler.
     *
     * @param fileChooser
     */
    private static void configureFileChooser(final FileChooser fileChooser)
    {
        fileChooser.setTitle("View CSV Files");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
    }

    @FXML
    private MenuItem aboutMenuItem;

    private App app;

    @FXML
    private MenuItem closeMenuItem;

    private Views currentView;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private Label statusLabel;

    /**
     * Instantiate a new copy of RootLayoutController class.
     */
    public RootLayoutController()
    {
        // NoOp
    }

    @Override
    public void setApp(App app)
    {
        this.app = app;
        app.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case App.PROP_ACTIVEVIEW ->
            {
                // Views being opened:
                currentView = (Views) evt.getNewValue();

                switch ((Views) evt.getNewValue())
                {
                    case BLANK ->
                    {
                        statusLabel.setText("");
                    }

                    case CSVTABLE ->
                    {
                        openMenuItem.setDisable(true);
                        closeMenuItem.setDisable(false);
                    }

                    default ->
                    {
                    }
                }

                // Views being closed:
                if (evt.getOldValue() != null)
                {
                    switch ((Views) evt.getOldValue())
                    {
                        case BLANK ->
                        {
                        }

                        case CSVTABLE ->
                        {
                            openMenuItem.setDisable(false);
                            closeMenuItem.setDisable(true);
                        }

                        default ->
                        {
                        }
                    }
                }
            }

            case App.PROP_DATAISDIRTY ->
            {
                if ((boolean) evt.getNewValue())
                {
                    saveMenuItem.setDisable(false);
                    saveAsMenuItem.setDisable(false);
                } else
                {
                    saveMenuItem.setDisable(true);
                    saveAsMenuItem.setDisable(true);
                }
            }

            case App.PROP_FILENAME ->
            {
                if (evt.getNewValue() != null)
                {
                    app.showView(CSVTABLE);

                } else
                {
                    app.showView(BLANK);
                }
            }

            case App.PROP_STATUSTEXT ->
            {
                statusLabel.setText((String) evt.getNewValue());
            }

            default ->
            {
            }
        }
    }

    @Override
    public void setFocus()
    {
        // NoOp
    }

    /**
     * Handle the Help/About menu item event.
     *
     * @param event
     */
    @FXML
    private void handleAboutMenuItem(ActionEvent event)
    {
        showAboutDialog();
        event.consume();
    }

    /**
     * Handle the File/Close menu item event.
     *
     * @param event
     */
    @FXML
    private void handleCloseMenuItem(ActionEvent event)
    {
        if (app.isDataDirty())
        {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "You have made some changes to the data!\n"
                    + "Are you sure you want to close this view without Saving?",
                    NO, YES);

            alert.setHeaderText("Confirm closing view without Saving!");

            alert.showAndWait().ifPresent(response ->
            {
                if (response == ButtonType.YES)
                {
                    app.setFileName(null);
                }
            });
        } else
        {
            app.setFileName(null);
        }

        event.consume();
    }

    /**
     * Handle the File/Close menu item event.
     *
     * @param event
     */
    @FXML
    private void handleExitMenuItem(ActionEvent event)
    {
        app.getPrimaryStage().fireEvent(
                new WindowEvent(
                        app.getPrimaryStage(),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );

        event.consume();
    }

    /**
     * Handle the File/Open menu item event.
     *
     * @param event
     */
    @FXML
    private void handleOpenMenuItem(ActionEvent event)
    {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File fileName = fileChooser.showOpenDialog(app.getPrimaryStage());

        if (fileName != null)
        {
            app.setFileName(fileName.toPath());
        }

        event.consume();
    }

    /**
     * Handle the File/Save As menu item event.
     *
     * @param event
     */
    @FXML
    private void handleSaveAsMenuItem(ActionEvent event)
    {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File fileName = fileChooser.showSaveDialog(app.getPrimaryStage());

        if (fileName != null)
        {
            app.saveFile(fileName.toPath());
        }

        event.consume();
    }

    /**
     * Handle the File/Save menu item event.
     *
     * @param event
     */
    @FXML
    private void handleSaveMenuItem(ActionEvent event)
    {
        app.saveFile(app.getFileName());
        event.consume();
    }

    /**
     * Controller initialization.
     */
    @FXML
    private void initialize()
    {
        closeMenuItem.setDisable(true);
        saveMenuItem.setDisable(true);
        saveAsMenuItem.setDisable(true);
    }

    /**
     * Opens the About dialog.
     */
    private void showAboutDialog()
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/About.fxml"));
            GridPane page = (GridPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("About");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(app.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException ex)
        {
            Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
