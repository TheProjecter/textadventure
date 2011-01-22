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

import android.graphics.drawable.Drawable;

public class CornerWallDrawable extends WallDrawable
{
    // TODO : Make sure there is exactly ONE drawn cell at the right.

    @Override
    public void setWall (Drawable aWall)
    {
        // TODO : Transform this to "bend" nicely
        setRight (aWall);

        // TODO : Rotate this 90�
        setBelowRight (aWall);
    }

    @Override
    public void setFloor (Drawable aFloor)
    {
        setBelow (aFloor);
    }

}