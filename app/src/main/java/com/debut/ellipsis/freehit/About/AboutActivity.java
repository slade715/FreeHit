package com.debut.ellipsis.freehit.About;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.debut.ellipsis.freehit.R;

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutActivity extends Activity {

    @BindView(R.id.draggable_frame)
    ElasticDragDismissFrameLayout draggableFrame;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    InkPageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        pager.setAdapter(new AboutPagerAdapter(AboutActivity.this));
        pager.setPageMargin(getResources().getDimensionPixelSize(R.dimen._14sdp));
        pageIndicator.setViewPager(pager);

        draggableFrame.addListener(
                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
                    @Override
                    public void onDragDismissed() {
                        // if we drag dismiss downward then the default reversal of the enter
                        // transition would slide content upward which looks weird. So reverse it.
                        if (draggableFrame.getTranslationY() > 0) {
                            getWindow().setReturnTransition(
                                    TransitionInflater.from(AboutActivity.this)
                                            .inflateTransition(R.transition.about_return_downward));
                        }
                        finishAfterTransition();
                    }
                });
    }

    class AboutPagerAdapter extends PagerAdapter {

        private View aboutFreehit;
        @Nullable
        @BindView(R.id.about_description)
        TextView freehitDescription;
        private View aboutIcon;
        @Nullable
        @BindView(R.id.icon_description)
        TextView iconDescription;
        private View aboutProg;
        @Nullable
        @BindView(R.id.progress_list)
        RecyclerView progressList;


        private final LayoutInflater layoutInflater;
        private final Activity host;

        AboutPagerAdapter(@NonNull Activity host) {
            this.host = host;
            layoutInflater = LayoutInflater.from(host);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {
            View layout = getPage(position, collection);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        private View getPage(int position, ViewGroup parent) {
            switch (position) {
                case 0:
                    if (aboutFreehit == null) {
                        aboutFreehit = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? layoutInflater.inflate(R.layout.about_freehit_dark, parent, false) : layoutInflater.inflate(R.layout.about_freehit, parent, false);
                        ButterKnife.bind(this, aboutFreehit);
                        freehitDescription.setMovementMethod(LinkMovementMethod.getInstance());
                        freehitDescription.setText(Html.fromHtml(getString(R.string.freehit_description)));
                    }
                    return aboutFreehit;
                case 1:
                    if (aboutIcon == null) {
                        aboutIcon = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? layoutInflater.inflate(R.layout.about_boilerplate_dark, parent, false) : layoutInflater.inflate(R.layout.about_boilerplate, parent, false);
                        ButterKnife.bind(this, aboutIcon);
                        iconDescription.setMovementMethod(LinkMovementMethod.getInstance());
                        iconDescription.setText(Html.fromHtml(getString(R.string.boilerplate_description)));
                    }
                    return aboutIcon;
                case 2:
                    if (aboutProg == null) {
                        aboutProg = layoutInflater.inflate(R.layout.about_progress, parent, false);
                        ButterKnife.bind(this, aboutProg);
                        progressList.setAdapter(new ProgressAdapter(host));
                    }
                    return aboutProg;
            }
            throw new InvalidParameterException();
        }
    }

    private static class ProgressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_TYPE_INTRO = 0;
        private static final int VIEW_TYPE_PROGRESS = 1;
        static final Porgress[] libs = {
                new Porgress("Out of public beta phase",
                        "The app was made available to the public in the release state on 26 november 2017. Since then out user base has grown and we look to improve each and every aspect about the app based on your suggestions",
                        "completed",
                        false),
                new Porgress("Scorecard for live and past matches",
                        "After a long delay , scorecard was released which has been met with positive feedback. We are still perfecting the scorecard to give users the best possible experience",
                        "completed",
                        true),
                new Porgress("Home tab and navigation drawer",
                        "A much requested home tab and a navigation drawer was added to make it easy for you to use the app to it's complete potential",
                        "completed",
                        true),
                new Porgress("Minor UI changes and app performance and functionality improvement",
                        "some minor changes to the UI have been made based on your suggestions . Keep sending us feedback",
                        "completed",
                        false),
                new Porgress("Major UI Changes",
                        "A complete app UI change has been planned to give the app a more material look and feel. Also to make it more user friendly",
                        "Planned",
                        true),
                new Porgress("Commentary for live matches",
                        "Commentary has been in works for a while now .Expect it to be released in the coming weeks",
                        "Planned",
                        false),
                new Porgress("In Depth match analysis",
                        "complete analysis for matches and tournaments have been planned to enhance the user experience",
                        "Planned",
                        false)};

        final Activity host;

        ProgressAdapter(Activity host) {
            this.host = host;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case VIEW_TYPE_INTRO:
                    return new ProgressIntroHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.about_progress_intro, parent, false));
                case VIEW_TYPE_PROGRESS:
                    return createProgressHolder(parent);
            }
            throw new InvalidParameterException();
        }

        private @NonNull
        ProgressHolder createProgressHolder(ViewGroup parent) {
            final ProgressHolder holder;
            holder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? new ProgressHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.about_progress_item_dark, parent, false)) : new ProgressHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.about_progress_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == VIEW_TYPE_PROGRESS) {
                bindProgress((ProgressHolder) holder, libs[position - 1]); // adjust for intro
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? VIEW_TYPE_INTRO : VIEW_TYPE_PROGRESS;
        }

        @Override
        public int getItemCount() {
            return libs.length + 1; // + 1 for the static intro view
        }

        private void bindProgress(final ProgressHolder holder, final Porgress lib) {
            holder.name.setText(lib.name);
            holder.description.setText(lib.description);
            if (lib.imageProgress.equals("completed")) {
                holder.image.setBackgroundResource(R.drawable.done);
            } else if (lib.imageProgress.equals("Planned")) {
                holder.image.setBackgroundResource(R.drawable.in_progress);
            }
           /* GlideRequest<Drawable> request = GlideApp.with(holder.image.getContext())
                    .load(lib.imageProgress)
                    .transition(withCrossFade());
            if (lib.circleCrop) {
                request.circleCrop();
            }
            request.into(holder.image);*/
        }
    }

    static class ProgressHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_status_image)
        ImageView image;
        @BindView(R.id.progress_name)
        TextView name;
        @BindView(R.id.progress_description)
        TextView description;

        ProgressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ProgressIntroHolder extends RecyclerView.ViewHolder {

        TextView intro;

        ProgressIntroHolder(View itemView) {
            super(itemView);
            intro = (TextView) itemView;
        }
    }

    /* Models of task completed and yet to be done */
    private static class Porgress {
        final String name;
        final String description;
        final String imageProgress;
        final boolean circleCrop;

        Porgress(String name, String description, String imageProgress, boolean circleCrop) {
            this.name = name;
            this.description = description;
            this.imageProgress = imageProgress;
            this.circleCrop = circleCrop;
        }
    }

}
