package com.rasjdd.udacity.mybakingapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.rasjdd.udacity.mybakingapp.Models.Objects.Recipe;
import com.rasjdd.udacity.mybakingapp.Models.Objects.Step;
import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.RecipeDetailActivity;
import com.rasjdd.udacity.mybakingapp.RecipeListActivity;
import com.rasjdd.udacity.mybakingapp.Utilities.Constants;
import com.rasjdd.udacity.mybakingapp.Utilities.NetUtils;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeStepFragment extends Fragment {
//    /**
//     * The fragment argument representing the item ID that this fragment
//     * represents.
//     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepFragment() {
    }

    private Recipe mRecipe;
    private Step mStep;
    private int mCurrentStep;

    // Exoplayer Variables
    private SimpleExoPlayer mVideoPlayer;
    private long mVideoTimeStamp;
    private SimpleExoPlayerView mPlayerView;
    private String mVideoUrlString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentStep = 2;

        if (getArguments().containsKey(Constants.keyFullRecipe)) {
            mRecipe = (Recipe) getArguments().getSerializable(Constants.keyFullRecipe);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.layoutRecipeStepsToolbar);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRecipe.getName());
            }

        }

        mStep = mRecipe.getSteps().get(mCurrentStep);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        String[] localizedTitleNames = this.getResources().getStringArray(R.array.step_title_for_localization);
        if (mStep.getId() <= localizedTitleNames.length) this.getActivity().setTitle(localizedTitleNames[mStep.getId()]);
        else this.getActivity().setTitle(String.valueOf(this.getString(R.string.step)));

        mPlayerView = rootView.findViewById(R.id.playerStepVideo);
        TextView textView = rootView.findViewById(R.id.textStepInstructions);

        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.keyPlayerPosition)) {
            mVideoTimeStamp = savedInstanceState.getLong(Constants.keyPlayerPosition, 0);
        }

        if (mStep != null) {
            textView.setText(mStep.getDescription());
            mVideoUrlString = NetUtils.detectVideoUrl(mStep.getVideoURL(),mStep.getThumbnailURL());
        }

        if (!mVideoUrlString.equals(Constants.InvalidString)) {
            initializeVideoPlayer(mVideoUrlString, this.getContext());
        }
        return rootView;
    }

    private void initializeVideoPlayer(String videoURL, Context context) {
        if (mVideoPlayer == null) {
            mVideoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(mVideoPlayer);
            mVideoPlayer.seekTo(mVideoTimeStamp);
            mPlayerView.setVisibility(View.VISIBLE);
        }
        MediaSource mediaSource = new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory("UdacityProject - Exoplayer"))
                .createMediaSource(Uri.parse(videoURL));
        mVideoPlayer.prepare(mediaSource, true, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(Constants.keyPlayerPosition, mVideoPlayer.getContentPosition());
    }

}
