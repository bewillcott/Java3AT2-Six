/*
 *  File Name:    EditFormController.java
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
 * Date: 14 Oct 2021
 * ****************************************************************
 */

package com.bewsoftware.tafe.java3.at2.six.view;

import com.bewsoftware.tafe.java3.at2.six.util.Ref;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 */
public class EditFormController
{
    public static final String PROP_UPDATE = "update";

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button cancelButton;

    private ObservableList<String> columns;

    @FXML
    private GridPane editPane;

    private final ArrayList<MyTextField> fields;

    private final PropertyChangeSupport propertyChangeSupport;

    private ObservableList<String> rowData;

    private boolean update;

    @FXML
    private Button updateButton;

    public EditFormController()
    {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.fields = new ArrayList<>();
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Get the value of update
     *
     * @return the value of update
     */
    public boolean isUpdate()
    {
        return update;
    }

    private void buildForm()
    {
        // Add Labels to form
        final Ref<Integer> gridRow = new Ref<>(0);

        columns.forEach(t ->
        {
            Label label = new Label(t + ":");
            editPane.add(label, 0, gridRow.val++);
        });

        // Add the TextFields with their initial values
        gridRow.val = 0;

        rowData.forEach(t ->
        {
            MyTextField textField = new MyTextField(t);
            fields.add(textField);
            editPane.add(textField, 1, gridRow.val++);
        });
    }

    /**
     * Set the value of update
     *
     * @param value the value of update
     */
    private void setUpdate(boolean value)
    {
        boolean oldUpdate = this.update;
        this.update = value;
        propertyChangeSupport.firePropertyChange(PROP_UPDATE, oldUpdate, value);

    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Set the data to be edited.
     *
     * @param columns The column headings - field labels.
     * @param rowData The data to be edited.
     */
    public void setData(ObservableList<String> columns, ObservableList<String> rowData)
    {
        this.columns = columns;
        this.rowData = rowData;

        buildForm();

    }

    @FXML
    private void handleCancelButton(ActionEvent event)
    {
        ((Stage) cancelButton.getScene().getWindow()).close();
        setUpdate(false);
        event.consume();
    }

    @FXML
    private void handleUpdateButton(ActionEvent event)
    {
        ((Stage) cancelButton.getScene().getWindow()).close();
        updateRow();
        event.consume();
    }

    private void updateRow()
    {
        boolean changed = false;

        for (int i = 0; i < fields.size(); i++)
        {
            if (fields.get(i).isChanged())
            {
                rowData.set(i, fields.get(i).getText());
                changed = true;
            }
        }

        setUpdate(changed);
    }

    /**
     * This class adds the ability to compare the resulting
     * text string with the initial text string.
     */
    public static class MyTextField extends TextField
    {
        private final String initialText;

        public MyTextField(String string)
        {
            super(string);
            this.initialText = string;
        }

        /**
         * Has the text been changed?
         *
         * @return result
         */
        public boolean isChanged()
        {
            return initialText == null
                    ? getText() != null
                    : !initialText.equals(getText());
        }

        @Override
        public String toString()
        {
            return super.toString() + "\nInitial text: " + initialText;
        }

    }

}
