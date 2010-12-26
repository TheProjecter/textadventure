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

public class StockTransformations
{
    private static BitmapTransformation _Rotate180;
    private static BitmapTransformation _Rotate270;
    private static BitmapTransformation _Rotate90;

    public static BitmapTransformation Rotate90 ()
    {
        if (_Rotate90 == null)
        {
            _Rotate90 = new Rotate90BitmapTransformation ();
        }
        return _Rotate90;
    }

    public static BitmapTransformation Rotate180 ()
    {
        if (_Rotate180 == null)
        {
            _Rotate180 = new Rotate180BitmapTransformation ();
        }
        return _Rotate180;
    }

    public static BitmapTransformation Rotate270 ()
    {
        if (_Rotate270 == null)
        {
            _Rotate270 = new Rotate270BitmapTransformation ();
        }
        return _Rotate270;
    }
}
