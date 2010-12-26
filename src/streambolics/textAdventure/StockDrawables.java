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

import streambolics.android.SurroundedDrawable;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class StockDrawables
{
    private static Drawable _ClosedDoor;
    private static Drawable _OpenDoor;
    private static Drawable _Wall;
    private static Drawable _Floor;
    private static Drawable _Floor1;
    private static Drawable _PitchBlack;
    private static Drawable _Grass;
    private static Drawable _Bush;

    private static SurroundedDrawable _TiledFloor;

    public static Drawable ClosedDoor (Context aContext)
    {
        if (_ClosedDoor == null)
        {
            _ClosedDoor = aContext.getResources ().getDrawable (R.drawable.closeddoor);
        }
        return _ClosedDoor;
    }

    public static Drawable OpenDoor (Context aContext)
    {
        if (_OpenDoor == null)
        {
            _OpenDoor = aContext.getResources ().getDrawable (R.drawable.opendoor);
        }
        return _OpenDoor;
    }

    public static Drawable Wall (Context aContext)
    {
        if (_Wall == null)
        {
            _Wall = aContext.getResources ().getDrawable (R.drawable.wall);
        }
        return _Wall;
    }

    public static Drawable Floor (Context aContext)
    {
        if (_Floor == null)
        {
            _Floor = aContext.getResources ().getDrawable (R.drawable.floor2);
        }
        return _Floor;
    }

    public static Drawable Floor1 (Context aContext)
    {
        if (_Floor1 == null)
        {
            _Floor1 = aContext.getResources ().getDrawable (R.drawable.floor1);
        }
        return _Floor1;
    }

    public static Drawable PitchBlack (Context aContext)
    {
        if (_PitchBlack == null)
        {
            _PitchBlack = aContext.getResources ().getDrawable (R.drawable.pitchblack);
        }
        return _PitchBlack;
    }

    public static Drawable Bush (Context aContext)
    {
        if (_Bush == null)
        {
            _Bush = aContext.getResources ().getDrawable (R.drawable.bush);
        }
        return _Bush;

    }

    public static Drawable Grass (Context aContext)
    {
        if (_Grass == null)
        {
            _Grass = aContext.getResources ().getDrawable (R.drawable.grass);
        }
        return _Grass;
    }

    public static Drawable TiledFloor (Context aContext)
    {
        if (_TiledFloor == null)
        {
            _TiledFloor = new SurroundedDrawable (Floor (aContext));
            _TiledFloor.setAbove (Floor1 (aContext));
            _TiledFloor.setLeft (_Floor);
            _TiledFloor.setRight (_Floor1);
            _TiledFloor.setAboveLeft (_Floor1);
            _TiledFloor.setAboveRight (_Floor);
        }
        return _TiledFloor;
    }

}
