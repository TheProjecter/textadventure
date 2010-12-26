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

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/***
 * A Drawable that applies a set of transformations to a source drawable.
 * 
 * The transformations can be rotations, skews, etc. The TransformedDrawable can
 * be used to take an icon and rotate it.
 * 
 * @author Stéphan Leclercq
 * 
 */
public class TransformedDrawable extends Drawable
{
    private Drawable _Source;
    private Bitmap _Cache;
    private ArrayList<BitmapTransformation> _Transformations = new ArrayList<BitmapTransformation> ();
    private Rect _Bounds;

    public TransformedDrawable (Drawable aSource)
    {
        _Source = aSource;
    }

    public void flush ()
    {
        _Cache = null;
    }

    private Bitmap buildCache ()
    {
        Rect bitmapSize = new Rect (_Bounds);
        for (int i = _Transformations.size () - 1; i >= 0; i--)
        {
            _Transformations.get (i).untransformRect (bitmapSize);
        }

        Bitmap b = Bitmap.createBitmap (bitmapSize.width (), bitmapSize.height (), Bitmap.Config.ARGB_8888);
        _Source.setBounds (bitmapSize);
        _Source.draw (new Canvas (b));
        for (BitmapTransformation t : _Transformations)
        {
            b = t.transform (b);
        }
        return b;
    }

    @Override
    public void draw (Canvas aCanvas)
    {
        try
        {
            if (_Cache == null)
            {
                _Cache = buildCache ();
            }
            aCanvas.drawBitmap (_Cache, null, _Bounds, new Paint ());
        }
        catch (Throwable t)
        {
        }
    }

    @Override
    public int getOpacity ()
    {
        return 0;
    }

    @Override
    public void setAlpha (int aAlpha)
    {
    }

    @Override
    public void setColorFilter (ColorFilter aColorFilter)
    {
    }

    @Override
    protected void onBoundsChange (Rect aBounds)
    {
        _Bounds = aBounds;
        flush ();
        super.onBoundsChange (aBounds);
    }

    public void addTransformation (BitmapTransformation aTransformation)
    {
        _Transformations.add (aTransformation);
    }

}
