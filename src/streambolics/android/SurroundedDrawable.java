package streambolics.android;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class SurroundedDrawable extends Drawable
{
    private final String TAG = "SurroundedDrawable";
    private Drawable _Center;
    private Drawable _Above;
    private Drawable _Below;
    private Drawable _Left;
    private Drawable _Right;
    private Drawable _AboveLeft;
    private Drawable _BelowRight;
    private Drawable _BelowLeft;
    private Drawable _AboveRight;

    private Rect _Bounds;

    private Rect _Cell;

    private Rect _CenterPos;

    public SurroundedDrawable (Drawable aCenter)
    {
        setCenter (aCenter);
    }

    protected Rect computeCenterPos ()
    {
        // TODO : make this method easier to redefine.

        if (_CenterPos == null)
        {
            int t;
            int l;

            if (_Above == null)
            {
                t = 0;
            }
            else if (_Below == null)
            {
                t = _Bounds.bottom - _Cell.height ();
            }
            else
            {
                t = _Bounds.centerY () - _Cell.height () / 2;
            }

            if (_Left == null)
            {
                l = 0;
            }
            else if (_Right == null)
            {
                l = _Bounds.left - _Cell.width ();
            }
            else
            {
                l = _Bounds.centerX () - _Cell.width () / 2;
            }

            _CenterPos = new Rect (l, t, l + _Cell.width (), t + _Cell.height ());
        }
        return _CenterPos;
    }

    @Override
    public void draw (Canvas aCanvas)
    {
        if (_Bounds == null || _Center == null)
        {
            return;
        }

        Rect r = computeCenterPos ();
        if (_Above != null)
        {
            Rect rr = new Rect (r);
            rr.offset (0, -_Cell.height ());
            while (Rect.intersects (_Bounds, rr))
            {
                drawLine (aCanvas, _AboveLeft, _Above, _AboveRight, rr);
                rr.offset (0, -_Cell.height ());
            }
        }
        if (_Below != null)
        {
            Rect rr = new Rect (r);
            rr.offset (0, _Cell.height ());
            while (Rect.intersects (_Bounds, rr))
            {
                drawLine (aCanvas, _BelowLeft, _Below, _BelowRight, rr);
                rr.offset (0, _Cell.height ());
            }
        }
        drawLine (aCanvas, _Left, _Center, _Right, r);
    }

    private void drawItem (Canvas aCanvas, Drawable aItem, Rect aPos)
    {
        if (aItem == null)
        {
            return;
        }
        try
        {
            aItem.setBounds (new Rect (aPos));
            aItem.draw (aCanvas);
        }
        catch (Throwable t)
        {
            Log.e (TAG, "DrawItem", t);
        }
    }

    private void drawLine (Canvas aCanvas, Drawable aLeft, Drawable aCenter, Drawable aRight, Rect aPos)
    {
        if (aRight != null)
        {
            Rect r = new Rect (aPos);
            r.offset (_Cell.width (), 0);
            while (Rect.intersects (_Bounds, r))
            {
                drawItem (aCanvas, aRight, r);
                r.offset (_Cell.width (), 0);
            }
        }
        if (aLeft != null)
        {
            Rect r = new Rect (aPos);
            r.offset (-_Cell.width (), 0);
            while (Rect.intersects (_Bounds, r))
            {
                drawItem (aCanvas, aLeft, r);
                r.offset (-_Cell.width (), 0);
            }
        }
        drawItem (aCanvas, aCenter, aPos);
    }

    public Drawable getAbove ()
    {
        return _Above;
    }

    public Drawable getAboveLeft ()
    {
        return _AboveLeft;
    }

    public Drawable getAboveRight ()
    {
        return _AboveRight;
    }

    public Drawable getBelow ()
    {
        return _Below;
    }

    public Drawable getBelowLeft ()
    {
        return _BelowLeft;
    }

    public Drawable getBelowRight ()
    {
        return _BelowRight;
    }

    public Drawable getCenter ()
    {
        return _Center;
    }

    public Drawable getLeft ()
    {
        return _Left;
    }

    @Override
    public int getOpacity ()
    {
        return 0;
    }

    public Drawable getRight ()
    {
        return _Right;
    }

    @Override
    protected void onBoundsChange (Rect aBounds)
    {
        _Bounds = new Rect (aBounds);
        _CenterPos = null;
        super.onBoundsChange (aBounds);
    }

    public void setAbove (Drawable above)
    {
        _Above = above;
        _CenterPos = null;
    }

    public void setAboveLeft (Drawable aAboveLeft)
    {
        _AboveLeft = aAboveLeft;
    }

    public void setAboveRight (Drawable aAboveRight)
    {
        _AboveRight = aAboveRight;
    }

    @Override
    public void setAlpha (int aArg0)
    {
    }

    public void setBelow (Drawable below)
    {
        _Below = below;
        _CenterPos = null;
    }

    public void setBelowLeft (Drawable aBelowLeft)
    {
        _BelowLeft = aBelowLeft;
    }

    public void setBelowRight (Drawable aBelowRight)
    {
        _BelowRight = aBelowRight;
    }

    public void setCenter (Drawable center)
    {
        _Center = center;
        if (_Center != null)
        {
            _Cell = new Rect (0, 0, center.getIntrinsicWidth (), center.getIntrinsicHeight ());
        }
        _CenterPos = null;
    }

    @Override
    public void setColorFilter (ColorFilter aArg0)
    {
    }

    public void setLeft (Drawable left)
    {
        _Left = left;
        _CenterPos = null;
    }

    public void setRight (Drawable right)
    {
        _Right = right;
        _CenterPos = null;
    }

}
