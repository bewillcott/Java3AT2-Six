/*
 *  File Name:    ViewController.java
 *  Project Name: GUIClient
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
 * Date: 9 Oct 2021
 * ****************************************************************
 */

package com.bewsoftware.tafe.java3.at2.six.util;

import com.bewsoftware.tafe.java3.at2.six.App;
import java.beans.PropertyChangeListener;
/**
 * ViewController interface description.
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 *
 * @since 1.0
 * @version 1.0
 */
public interface ViewController extends PropertyChangeListener
{

    /**
     * Store a reference to the App instance.
     *
     * @param app instance to store
     */
    public void setApp(App app);

    /**
     * Called from {@link App#showView(com.bewsoftware.tafe.java3.at2.four.gui.Views)
     * App.showView(view)} to allow the view controller to set one of its
     * view's controls to have the focus.
     */
    public void setFocus();
}
