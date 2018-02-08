package com.kingkingyyk.uhunter.ui;

import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingkingyyk.uhunter.R;
import com.kingkingyyk.uhunter.databinding.FragmentProfileBinding;
import com.kingkingyyk.uhunter.uhunt.User;

import java.util.Random;


public class FragmentProfile extends Fragment {
    private static boolean isAnimated=false;
    private OnFragmentInteractionListener mListener;
    private FragmentProfileBinding binding;
    private User u;

    public FragmentProfile() {}

    public static FragmentProfile newInstance(User u) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putInt("rank", u.getRank());
        args.putInt("ac", u.getAc());
        args.putInt("nos", u.getNoOfSubmissions());
        fragment.setArguments(args);
        fragment.u=u;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        if (getArguments() != null) {
            Random rdn=new Random();
            ValueAnimator animator = ValueAnimator.ofInt(rdn.nextInt(100001)+100000, getArguments().getInt("rank"));
            animator.setDuration(!isAnimated ? rdn.nextInt(1000)+500 : 0);
            animator.addUpdateListener(animation -> binding.textViewRankingValue.setText("#"+animation.getAnimatedValue().toString()));
            animator.start();

            animator = ValueAnimator.ofInt(0, getArguments().getInt("ac"));
            animator.setDuration(!isAnimated ? rdn.nextInt(1000)+500 : 0);
            animator.addUpdateListener(animation -> binding.textViewAnswerCorrectValue.setText(animation.getAnimatedValue().toString()));
            animator.start();

            animator = ValueAnimator.ofInt(0, getArguments().getInt("nos"));
            animator.setDuration(!isAnimated ? rdn.nextInt(1000)+500 : 0);
            animator.addUpdateListener(animation -> binding.textViewNoOfSubmissionsValue.setText(animation.getAnimatedValue().toString()));
            animator.start();

            if (!isAnimated) isAnimated=true;
        }
        return binding.getRoot();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
