package streambolics.android;

/*---------------------------------------------------------------------------------------------------

 Part of : Generic tools for Android

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

/***
 * A custom layout that maintains a square area and two smaller comment areas.
 * 
 * This layout is typically used for board games, where the square area is used
 * to maintain the play board area, while the two other ares contain messages.
 * 
 * @author Stéphan Leclercq
 * 
 */

public class SquareBoardLayout extends CustomLayout
{
    private final String TAG = "SquareBoardLayout";
    private View _Board;
    private View _Panel1;
    private View _Panel2;
    private int _GridSize = 1;

    public SquareBoardLayout (Context context)
    {
        super (context);
    }

    public SquareBoardLayout (Context aContext, View aBoard, View aPanel1, View aPanel2)
    {
        this (aContext);
        setBoard (aBoard);
        setPanel1 (aPanel1);
        setPanel2 (aPanel2);
    }

    public void setBoard (View aBoard)
    {
        optRemove (_Board);
        _Board = aBoard;
        optAdd (_Board);
    }

    public View getBoard ()
    {
        return _Board;
    }

    public void setPanel1 (View panel1)
    {
        optRemove (_Panel1);
        _Panel1 = panel1;
        optAdd (_Panel1);
    }

    public View getPanel1 ()
    {
        return _Panel1;
    }

    public void setPanel2 (View panel2)
    {
        optRemove (_Panel2);
        _Panel2 = panel2;
        optAdd (_Panel2);
    }

    public View getPanel2 ()
    {
        return _Panel2;
    }

    @Override
    protected void onLayout (boolean aChanged, int l, int t, int r, int b)
    {
        int Width = r - l;
        int Height = b - t;

        boolean Landscape = Width > Height;
        int SquareSize;
        int HalfSize;
        if (Landscape)
        {
            SquareSize = Math.min (Height, Width * 3 / 4);
            HalfSize = Height / 2;
        }
        else
        {
            SquareSize = Math.min (Width, Height * 3 / 4);
            HalfSize = Width / 2;
        }
        int border = SquareSize % _GridSize;
        int truesize = SquareSize - border;
        border /= 2;
        Log.d (TAG, "SquareSize = " + Integer.toString (SquareSize));
        Log.d (TAG, "GridSize = " + Integer.toString (_GridSize));
        Log.d (TAG, "Border = " + Integer.toString (border));
        Log.d (TAG, "TrueSize = " + Integer.toString (truesize));
        _Board.layout (l + border, t + border, l + truesize + border, t + truesize + border);

        if (Landscape)
        {
            _Panel1.layout (l + SquareSize, t, r, t + HalfSize);
            _Panel2.layout (l + SquareSize, t + HalfSize, r, b);
        }
        else
        {
            _Panel1.layout (l, t + SquareSize, l + HalfSize, b);
            _Panel2.layout (l + HalfSize, t + SquareSize, r, b);
        }
    }

    public void setGridSize (int n)
    {
        _GridSize = n;
    }
}
