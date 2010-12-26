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

import streambolics.android.BitmapTransformation;
import streambolics.android.SizeProxyDrawable;
import streambolics.android.TransformedDrawable;
import android.content.Context;
import android.view.animation.Animation;
import android.widget.Button;

public class WallButton extends Button
{
    private Animation _Apparate;
    private SizeProxyDrawable _Background;
    private WallDrawable _WallDrawable;
    private TransformedDrawable _Rotator;

    public WallButton (Context context, Animation aApparateAnimation, WallDrawable aWall, BitmapTransformation aTransformation)
    {
        super (context);
        _Apparate = aApparateAnimation;
        _Background = new SizeProxyDrawable ();
        _WallDrawable = aWall;
        setBackgroundDrawable (_Background);

        if (aTransformation == null)
        {
            _Background.setProxyed (aWall);
        }
        else
        {
            _Rotator = new TransformedDrawable (aWall);
            _Rotator.addTransformation (aTransformation);
            _Background.setProxyed (_Rotator);
        }
    }

    public void Apparate ()
    {
        if (_Apparate != null)
        {
            startAnimation (_Apparate);
        }
    }

    public void setInvisible ()
    {
        setEnabled (false);
        setText ("");
        _WallDrawable.setFloor (StockDrawables.PitchBlack (getContext ()));
        _WallDrawable.setWall (StockDrawables.PitchBlack (getContext ()));
        _WallDrawable.setDoor (StockDrawables.PitchBlack (getContext ()));
    }

    private void setDoor (Item aRoom, Exit aExit, Item aDoor)
    {
        if (aDoor == null)
        {
            _WallDrawable.setWall (aExit.getFloorDrawable (getContext ()));
            _WallDrawable.setDoor (aExit.getFloorDrawable (getContext ()));
        }
        else if (aDoor.isOpen ())
        {
            _WallDrawable.setWall (aExit.getWallDrawable (getContext ()));
            _WallDrawable.setDoor (aExit.getOpenDoorDrawable (getContext ()));
        }
        else
        {
            _WallDrawable.setWall (aExit.getWallDrawable (getContext ()));
            _WallDrawable.setDoor (aExit.getClosedDoorDrawable (getContext ()));
        }
    }

    public void setExit (Item aRoom, Exit aExit)
    {
        if (aExit == null)
        {
            setEnabled (false);
            setText ("");
            _WallDrawable.setFloor (aRoom.getFloorDrawable (getContext ()));
            _WallDrawable.setWall (aRoom.getWallDrawable (getContext ()));
            _WallDrawable.setDoor (aRoom.getWallDrawable (getContext ()));
        }
        else
        {
            setText (aExit.getDescription ());
            setEnabled (true);
            _WallDrawable.setFloor (aExit.getFloorDrawable (getContext ()));
            setDoor (aRoom, aExit, aExit.getDoor ());
        }
        if (_Rotator != null)
        {
            _Rotator.flush ();
        }
        invalidate ();
    }
}
