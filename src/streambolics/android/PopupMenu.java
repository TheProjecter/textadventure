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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopupMenu extends LinearLayout
{
    private PopupWindow _Popup;
    private int _TextColor = Color.BLACK;

    public PopupMenu (Context aContext)
    {
        super (aContext);
        setOrientation (VERTICAL);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins (4, 4, 4, 4);
        setLayoutParams (p);
        setPadding (4, 4, 4, 4);
    }

    public void addEntry (Drawable aIcon, String aText, View.OnClickListener aAction)
    {
        PopupIconAndTextView v = new PopupIconAndTextView (getContext (), aIcon, aText, this, aAction);
        v.setTextColor (_TextColor);
        addView (v);
    }

    public void addTitle (String aText)
    {
        TextView t = new TextView (getContext ());
        addView (t);
        t.setText (aText);
        t.setTextColor (_TextColor);
    }

    public void dismiss ()
    {
        if (_Popup != null)
        {
            _Popup.dismiss ();
        }
        _Popup = null;
    }

    public void show (View aParent)
    {
        dismiss ();
        measure (0, 0);
        _Popup = new PopupWindow (this, getMeasuredWidth (), getMeasuredHeight (), false);
        int xoff = (aParent.getWidth () - getMeasuredWidth ()) / 2;
        _Popup.showAsDropDown (aParent, xoff, 0);
    }
}
