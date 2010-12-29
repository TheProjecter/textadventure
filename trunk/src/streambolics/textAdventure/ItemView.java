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

import streambolics.android.IconAndTextView;
import android.content.Context;
import android.view.View;

public class ItemView extends IconAndTextView
{
    private Item _Item;

    public ItemView (Context aContext, Item aItem)
    {
        super (aContext, aItem.getInventoryDrawable (aContext), aItem.getDescription ());
        _Item = aItem;
    }

    public void setItem (Context aContext, Item aItem)
    {
        setText (aItem.getDescription ());
        setIcon (aItem.getInventoryDrawable (aContext));
        _Item = aItem;
    }

    @Override
    public void onClick (View aView)
    {
        super.onClick (aView);
        _Item.clicked ();
    }
}
