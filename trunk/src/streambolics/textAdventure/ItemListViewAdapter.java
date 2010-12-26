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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ItemListViewAdapter extends ArrayAdapter<Item>
{
    private Context _Context;

    public ItemListViewAdapter (Context aContext)
    {
        super (aContext, 0);
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
            v.setItem (itm);
        }
        else
        {
            v = new ItemView (_Context, itm);
            // aParent.addView (v);
        }

        return v;
    }

    protected void Debug (String aMessage)
    {
        new AlertDialog.Builder (_Context).setTitle ("ItemListViewAdapter").setMessage (aMessage)
                .setNeutralButton ("Continue", new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                    }
                }).show ();
    }

}
