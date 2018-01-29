package com.debut.ellipsis.freehit.About;

public class MathUtils {

    public static float constrain(float min,float max,float val)
    {
        if(val>max)
        {
            return max;
        }
        else if(val<min)
        {
            return min;
        }
        else
        {
            return val;
        }
    }
}
