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

import streambolics.core.Tokenizer;

public abstract class MessageInstruction extends Instruction
{
    public MessageInstruction (Game aGame)
    {
        super (aGame);
    }

    private String _Message;

    @Override
    public void parse (Tokenizer t)
    {
        _Message = t.getRemainder ();
    }

    protected String getMessage ()
    {
        return _Message;
    }

    @Override
    public boolean isUseful ()
    {
        return true;
    }
}
