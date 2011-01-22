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

public abstract class Instruction extends GameObject
{
    public Instruction (Game aGame)
    {
        super (aGame);
    }

    /***
     * Executes the instruction.
     * 
     * @return true if execution must continue with the next instruction. false
     *         if subsequent instructions in the rule must be skipped.
     * 
     * @throws GameEngineException
     */

    public abstract boolean run () throws GameEngineException;

    /***
     * Skips the instruction, and optionally ends the skipping.
     * 
     * @return true if following instructions should be executed. false if
     *         following instructions must still be skipped.
     */

    public boolean skip ()
    {
        return false;
    }

    public abstract void parse (Tokenizer t) throws GameEngineException;

    /***
     * Whether this instruction was useful in doing something.
     * 
     * @return true if the instruction actually changes something in the game
     *         when run. false if the instruction is merely a control structure.
     */

    public boolean isUseful ()
    {
        return false;
    }

}
