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
import android.widget.ImageView;
import android.widget.TextView;

public class IconAndTextView extends CustomLayout
{
    private final static String TAG = "IconAndTextView";

    private TextView _Text;
    private ImageView _Icon;
    private int _Height;

    public IconAndTextView (Context aContext, int aIcon, String aText, int aHeight)
    {
        super (aContext);
        Initialize (aContext, aIcon, aText, aHeight);
    }

    public IconAndTextView (Context aContext, int aIcon, String aText)
    {
        super (aContext);
        Initialize (aContext, aIcon, aText, 24);
    }

    private void Initialize (Context aContext, int aIcon, String aText, int aHeight)
    {
        _Text = new TextView (aContext);
        addView (_Text);
        setText (aText);
        _Icon = new ImageView (aContext);
        addView (_Icon);
        setIcon (aIcon);
        _Height = aHeight;
    }

    @Override
    protected void onLayout (boolean aChanged, int aL, int aT, int aR, int aB)
    {
        _Icon.layout (aL, aT, aL + _Height, aT + _Height);
        _Text.layout (aL + _Height, aT, aR, aB);
    }

    @Override
    protected void onMeasure (int aWidthMeasureSpec, int aHeightMeasureSpec)
    {
        Log.d (TAG, "onMeasure " + Integer.toString (aWidthMeasureSpec) + "-" + Integer.toString (aHeightMeasureSpec));
        _Icon.measure (aWidthMeasureSpec, aHeightMeasureSpec);
        _Text.measure (aWidthMeasureSpec, aHeightMeasureSpec);

        Log.d (TAG, "setMeasuredDimension " + Integer.toString (measureWidth (aWidthMeasureSpec)) + "-" + Integer.toString (measureHeight (aHeightMeasureSpec)));
        setMeasuredDimension (measureWidth (aWidthMeasureSpec), measureHeight (aHeightMeasureSpec));
    }

    private int measureWidth (int measureSpec)
    {
        return getMeasurement (measureSpec, _Height + 2 + _Text.getMeasuredWidth ());
    }

    private int measureHeight (int measureSpec)
    {
        return getMeasurement (measureSpec, _Height);
    }

    private int getMeasurement (int measureSpec, int preferred)
    {
        // TODO : is this equivalent to resolveSize?

        int specSize = MeasureSpec.getSize (measureSpec);
        int measurement = 0;

        switch (MeasureSpec.getMode (measureSpec))
        {
        case MeasureSpec.EXACTLY:
            // This means the width of this view has been given.
            measurement = specSize;
            break;
        case MeasureSpec.AT_MOST:
            // Take the minimum of the preferred size and what
            // we were told to be.
            measurement = Math.min (preferred, specSize);
            break;
        default:
            measurement = preferred;
            break;
        }

        return measurement;
    }

    public void setText (String aText)
    {
        _Text.setText (aText);
    }

    public void setIcon (int aIcon)
    {
        if (aIcon >= 0)
        {
            _Icon.setImageResource (aIcon);
        }
    }
}
