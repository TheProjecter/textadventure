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

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ItemListViewAdapter extends ArrayAdapter<Item>
{
    private final static String LOGTAG = "ItemListViewAdapter";
    private Context _Context;
    private ThemeProvider _Theme;

    public ItemListViewAdapter (Context aContext)
    {
        super (aContext, 444719);
        _Context = aContext;
    }

    @Override
    public View getView (int aPosition, View aConvertView, ViewGroup aParent)
    {
        ItemView v;
        Item itm = getItem (aPosition);
        if (aConvertView != null)
        {
            v = (ItemView) aConvertView;
            v.setItem (_Context, itm);
        }
        else
        {
            v = new ItemView (_Context, itm);
        }

        v.setTheme (_Theme);
        return v;
    }

    @Override
    public boolean hasStableIds ()
    {
        return false;
    }

    @SuppressWarnings("unused")
    private void debug (String aMessage)
    {
        Log.d (LOGTAG, aMessage);
    }

    public void setTheme (ThemeProvider aTheme)
    {
        _Theme = aTheme;
    }

}
