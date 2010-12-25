package streambolics.android;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class SizeProxyDrawable extends Drawable
{
    private Rect _Bounds;
    private Drawable _Drawable;

    @Override
    public void draw (Canvas aCanvas)
    {
        if (_Drawable != null)
        {
            if (_Bounds != null)
            {
                _Drawable.setBounds (_Bounds);
            }
            _Drawable.draw (aCanvas);
        }
    }

    public Drawable getProxied ()
    {
        return _Drawable;
    }

    public void setProxyed (Drawable aDrawable)
    {
        _Drawable = aDrawable;
    }

    @Override
    protected void onBoundsChange (Rect aBounds)
    {
        _Bounds = new Rect (aBounds);
        super.onBoundsChange (aBounds);
    }

    @Override
    public int getOpacity ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setAlpha (int aAlpha)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setColorFilter (ColorFilter aCf)
    {
        // TODO Auto-generated method stub

    }

}
