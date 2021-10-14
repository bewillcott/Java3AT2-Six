/*
 *  File Name:    BlankController.java
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
 * Date: 10 Oct 2021
 * ****************************************************************
 */

package com.bewsoftware.tafe.java3.at2.six.view;

import com.bewsoftware.tafe.java3.at2.six.App;
import com.bewsoftware.tafe.java3.at2.six.util.ViewController;
import java.beans.PropertyChangeEvent;

/**
 * This is a dummy controller, just to be compatible with the
 * {@link App#showView(com.bewsoftware.tafe.java3.at2.four.gui.Views) App.setview(view)}
 * method.
 *
 * @author <a href="mailto:bw.opensource@yahoo.com">Bradley Willcott</a>
 *
 * @since 1.0
 * @version 1.0
 */
public class BlankController implements ViewController
{
    @Override
    public void setApp(App app)
    {
        // NoOp
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // NoOp
    }

    @Override
    public void setFocus()
    {
        // NoOp
    }

}
