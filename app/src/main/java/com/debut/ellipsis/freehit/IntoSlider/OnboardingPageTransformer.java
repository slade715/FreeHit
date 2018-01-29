package com.debut.ellipsis.freehit.IntoSlider;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.debut.ellipsis.freehit.R;

public class OnboardingPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {

        int pageWidth = page.getWidth();
        float pageWidthTimesPosition = pageWidth * position;

        if (position >= -1.0f || position <= 1.0f) {

            //Page is currently being swiped , perform animations here

            //Page 1
            final ImageView screen1image1 = page.findViewById(R.id.imageView1);
            if (screen1image1 != null) screen1image1.setTranslationX(pageWidthTimesPosition * 0.5f);

            final TextView heading1 = page.findViewById(R.id.heading1);
            if (heading1 != null) heading1.setTranslationX(pageWidthTimesPosition * 0.7f);

            final TextView description1 = page.findViewById(R.id.description1);
            if (description1 != null) description1.setTranslationX(pageWidthTimesPosition * 1.3f);

            //Page 2
            final ImageView screen1image2 = page.findViewById(R.id.imageView2);
            if (screen1image2 != null) screen1image2.setTranslationX(pageWidthTimesPosition * 0.5f);

            final TextView heading2 = page.findViewById(R.id.heading2);
            if (heading2 != null) heading2.setTranslationX(pageWidthTimesPosition * 0.7f);

            final TextView description2 = page.findViewById(R.id.description2);
            if (description2 != null) description2.setTranslationX(pageWidthTimesPosition * 1.3f);

            //Page 3
            final ImageView screen1image3 = page.findViewById(R.id.imageView3);
            if (screen1image3 != null) screen1image3.setTranslationX(pageWidthTimesPosition * 0.5f);

            final TextView heading3 = page.findViewById(R.id.heading3);
            if (heading3 != null) heading3.setTranslationX(pageWidthTimesPosition * 0.7f);

            final TextView description3 = page.findViewById(R.id.description3);
            if (description3 != null) description3.setTranslationX(pageWidthTimesPosition * 1.3f);

            //Page 4
            final ImageView screen1image4 = page.findViewById(R.id.imageView4);
            if (screen1image4 != null) screen1image4.setTranslationX(pageWidthTimesPosition * 0.5f);

            final TextView heading4 = page.findViewById(R.id.heading4);
            if (heading4 != null) heading4.setTranslationX(pageWidthTimesPosition * 0.7f);

            final TextView description4 = page.findViewById(R.id.description4);
            if (description4 != null) description4.setTranslationX(pageWidthTimesPosition * 1.3f);

            //Page 5 : For the page with the country select option
            final ImageView country_flag = page.findViewById(R.id.country_flag);
            if (country_flag != null) country_flag.setTranslationX(pageWidthTimesPosition * 0.5f);

            final TextView country_name = page.findViewById(R.id.country_name);
            if (country_name != null) country_name.setTranslationX(pageWidthTimesPosition * 1.0f);

            final TextView slide5description = page.findViewById(R.id.slide5description);
            if (slide5description != null)
                slide5description.setTranslationX(pageWidthTimesPosition * 1.3f);

            final Button country_select = page.findViewById(R.id.country_select);
            if (country_select != null)
                country_select.setTranslationX(pageWidthTimesPosition * 0.7f);

            final TextView night_mode = page.findViewById(R.id.night_mode);
            if (night_mode != null) night_mode.setTranslationX(pageWidthTimesPosition * 0.3f);

            final Switch switch_night_mode = page.findViewById(R.id.switch_night_mode);
            if (switch_night_mode != null)
                switch_night_mode.setTranslationX(pageWidthTimesPosition * 0.3f);

        }

    }
}
