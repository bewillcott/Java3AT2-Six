/*
 *  File Name:    AboutController.java
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

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import static com.bewsoftware.tafe.java3.at2.six.util.Constants.BUILD_DATE;
import static com.bewsoftware.tafe.java3.at2.six.util.Constants.PRODUCT_TITLE;
import static com.bewsoftware.tafe.java3.at2.six.util.Constants.VERSION;

/**
 * FXML Controller class for the 'About.fxml' file.
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 */
public class AboutController
{

    private static final String DESCRIPTION
            = "This program was developed for my Diploma in Software Developement "
            + "at the South Metrolpolitan TAFE, Rockingham WA.\n\n"
            + "This application reads any CSV file and displays its "
            + "contents inside a table.\n";

    @FXML
    private Label buildDateLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label productLabel;

    @FXML
    private GridPane rootPane;

    @FXML
    private Label verionLabel;

    /**
     * Instantiate a new copy of the AboutController class.
     */
    public AboutController()
    {
        // Currently: NoOp.
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize()
    {
        productLabel.setText(PRODUCT_TITLE);
        verionLabel.setText(VERSION);
        buildDateLabel.setText(BUILD_DATE);
        descriptionTextArea.setText(DESCRIPTION);
    }
}
