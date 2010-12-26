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

import streambolics.android.StandardListView;
import android.content.Context;

public class InventoryView extends StandardListView implements ItemVisitor
{
    ItemListViewAdapter _Adapter;

    public InventoryView (Context aContext)
    {
        super (aContext);
        _Adapter = new ItemListViewAdapter (aContext);
        setAdapter (_Adapter);
    }

    public void setContainer (Item aContainer)
    {
        _Adapter.clear ();
        if (aContainer.hasLight ())
        {
            setBackgroundDrawable (StockDrawables.TiledFloor (getContext ()));
            aContainer.visitContents (this);
        }
        else
        {
            setBackgroundDrawable (StockDrawables.PitchBlack (getContext ()));
        }
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
}
