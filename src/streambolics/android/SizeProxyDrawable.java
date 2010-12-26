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

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/***
 * A proxy drawable that keeps its size.
 * 
 * When multiple widgets use the same drawable object, it happens that they do
 * not always set the appropriate size; they just call draw.
 * 
 * SizeProxyDrawable acts as a lightweight proxy for sharing drawables between
 * multiple widgets, by correctly calling setBounds each time the drawable needs
 * to be redrawn.
 * 
 * @author Stéphan Leclercq
 * 
 */

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
