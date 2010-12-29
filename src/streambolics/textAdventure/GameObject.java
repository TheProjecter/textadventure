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

import streambolics.core.Logger;

public class GameObject implements Logger
{
    private Game _Game;

    public GameObject (Game aGame)
    {
        _Game = aGame;
    }

    protected Game getGame ()
    {
        return _Game;
    }

    protected Item accessItem (String aName)
    {
        return _Game.accessItem (aName);
    }

    @Override
    public void log (String aMessage)
    {
        _Game.log (aMessage);
    }
}
