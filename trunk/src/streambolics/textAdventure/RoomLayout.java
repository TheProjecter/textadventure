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
import streambolics.android.StandardTextView;
import streambolics.android.StockAnimations;
import streambolics.android.StockTransformations;
import android.content.Context;

public class RoomLayout extends CustomLayout
{
    private WallButton _North;
    private WallButton _South;
    private WallButton _East;
    private WallButton _West;
    private WallButton _NorthEast;
    private WallButton _SouthEast;
    private WallButton _NorthWest;
    private WallButton _SouthWest;
    private StandardTextView _Description;
    private InventoryView _Contents;
    private int _GridSize = 32;

    public RoomLayout (Context aContext)
    {
        super (aContext);

        _GridSize = StockDrawables.Floor (aContext).getIntrinsicHeight ();
        _North = new WallButton (aContext, StockAnimations.getApparateNorth (), new StraightWallDrawable (), null);
        addView (_North);

        _South = new WallButton (aContext, StockAnimations.getApparateSouth (), new StraightWallDrawable (), StockTransformations.Rotate180 ());
        addView (_South);

        _East = new WallButton (aContext, StockAnimations.getApparateEast (), new StraightWallDrawable (), StockTransformations.Rotate90 ());
        addView (_East);

        _West = new WallButton (aContext, StockAnimations.getApparateWest (), new StraightWallDrawable (), StockTransformations.Rotate270 ());
        addView (_West);

        _NorthEast = new WallButton (aContext, StockAnimations.getApparateNorthEast (), new CornerWallDrawable (), null);
        addView (_NorthEast);

        _SouthEast = new WallButton (aContext, StockAnimations.getApparateSouthEast (), new CornerWallDrawable (), StockTransformations.Rotate90 ());
        addView (_SouthEast);

        _NorthWest = new WallButton (aContext, StockAnimations.getApparateNorthWest (), new CornerWallDrawable (), StockTransformations.Rotate270 ());
        addView (_NorthWest);

        _SouthWest = new WallButton (aContext, StockAnimations.getApparateSouthWest (), new CornerWallDrawable (), StockTransformations.Rotate180 ());
        addView (_SouthWest);

        _Description = new StandardTextView (aContext);
        addView (_Description);

        _Contents = new InventoryView (aContext);
        addView (_Contents);
    }

    @Override
    protected void onLayout (boolean aChanged, int aL, int aT, int aR, int aB)
    {
        int ww = _GridSize * 2;
        int width = aR - aL;

        ww -= ww % _GridSize;
        int hh = (width - 2 * ww) / 2;

        // debug ("Width = " + Integer.toString (width));
        // debug ("ww = " + Integer.toString (ww));

        _NorthWest.layout (0, 0, ww, ww);
        _North.layout (ww, 0, width - ww, ww);
        _NorthEast.layout (width - ww, 0, width, ww);
        _West.layout (0, ww, ww, width - ww);
        _East.layout (width - ww, ww, width, width - ww);
        _SouthWest.layout (0, width - ww, ww, width);
        _South.layout (ww, width - ww, width - ww, width);
        _SouthEast.layout (width - ww, width - ww, width, width);

        _Description.layout (ww, ww, width - ww, ww + hh);
        _Contents.layout (ww, ww + hh, width - ww, width - ww);
    }

    public WallButton getNorthButton ()
    {
        return _North;
    }

    public WallButton getSouthButton ()
    {
        return _South;
    }

    public WallButton getEastButton ()
    {
        return _East;
    }

    public WallButton getWestButton ()
    {
        return _West;
    }

    public WallButton getNorthEastButton ()
    {
        return _NorthEast;
    }

    public WallButton getNorthWestButton ()
    {
        return _NorthWest;
    }

    public WallButton getSouthEastButton ()
    {
        return _SouthEast;
    }

    public WallButton getSouthWestButton ()
    {
        return _SouthWest;
    }

    public StandardTextView getDescriptionView ()
    {
        return _Description;
    }

    public InventoryView getContentsView ()
    {
        return _Contents;
    }

    public int getGridSize ()
    {
        return _GridSize;
    }
}
