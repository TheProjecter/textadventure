package streambolics.android;

import android.content.Context;
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
    private View _Board;
    private View _Panel1;
    private View _Panel2;

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

        _Board.layout (l, t, l + SquareSize, t + SquareSize);

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
}
