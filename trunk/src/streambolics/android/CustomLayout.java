package streambolics.android;

/*---------------------------------------------------------------------------------------------------

 Part of : Generic tools for Android

 Copyright (C) 2010-2011  Stephan Leclercq

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 ---------------------------------------------------------------------------------------------------*/

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/***
 * The base class for all custom layouts.
 * 
 * @author Stéphan Leclercq
 * 
 */

public abstract class CustomLayout extends ViewGroup
{
    private Context _Context;

    protected void debug (String aMessage)
    {
        Log.d (getClass ().getSimpleName (), aMessage);
    }

    public CustomLayout (Context aContext)
    {
        super (aContext);
        _Context = aContext;
    }

    protected void optAdd (View aView)
    {
        if (aView != null)
        {
            addView (aView);
        }
    }

    protected void optRemove (View aView)
    {
        if (aView != null)
        {
            removeView (aView);
        }
    }

    protected void Debug (String aMessage)
    {
        new AlertDialog.Builder (_Context).setTitle ("Debug").setMessage (aMessage).setNeutralButton ("Continue", new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
            }
        }).show ();
    }
}
