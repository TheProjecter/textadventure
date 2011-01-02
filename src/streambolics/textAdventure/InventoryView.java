package streambolics.textAdventure;

/*---------------------------------------------------------------------------------------------------

 Part of : Text Adventure Creator

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

import streambolics.android.CustomLayout;
import streambolics.android.StandardListView;
import android.content.Context;

public class InventoryView extends CustomLayout implements ItemVisitor
{
    private ItemListViewAdapter _Adapter;
    private FloorDrawable _Background;
    private StandardListView _CurrentView;

    public InventoryView (Context aContext)
    {
        super (aContext);
        _Adapter = new ItemListViewAdapter (aContext);
        _Background = new FloorDrawable (null);
    }

    private void createNewViewSinceOldWillNotUpdate ()
    {
        if (_CurrentView != null)
        {
            _CurrentView.setAdapter (null);
            removeView (_CurrentView);
        }

        _CurrentView = new StandardListView (getContext ());
        _CurrentView.setDividerHeight (0);
        addView (_CurrentView);
    }

    public void setContainer (Item aContainer, ThemeProvider aTheme)
    {
        _Adapter.setTheme (aTheme);
        createNewViewSinceOldWillNotUpdate ();
        _Background.setFloor (aTheme.getFloorDrawable (getContext ()));

        _Adapter.clear ();
        if (aContainer.hasLight ())
        {
            aContainer.visitContents (this);
        }
        _CurrentView.setBackgroundDrawable (_Background);
        _CurrentView.setAdapter (_Adapter);
        _CurrentView.layout (0, 0, getWidth (), getHeight ());
    }

    @Override
    public void visit (Item aItem)
    {
        if (aItem.getName ().equals ("Player"))
        {
            return;
        }
        _Adapter.add (aItem);
    }

    @Override
    protected void onLayout (boolean aChanged, int aL, int aT, int aR, int aB)
    {
        if (_CurrentView != null)
        {
            _CurrentView.layout (0, 0, aR - aL, aB - aT);
        }
    }
}
