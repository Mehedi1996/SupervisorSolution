package com.mehedihasan.supervisorsolution.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.mehedihasan.supervisorsolution.R;
import com.mehedihasan.supervisorsolution.utils.IntentAndBundleKey;

public class HomeAndRuleFragment extends Fragment {

    private View view;

    public HomeAndRuleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            String bundleKey = getArguments().getString(IntentAndBundleKey.KEY_FRAGMENT_HOME_OR_RULE);

            if (bundleKey != null) {
                switch (bundleKey) {
                    case IntentAndBundleKey.KEY_FRAGMENT_HOME:
                        view = inflater.inflate(R.layout.fragment_home, container, false);
                        if (getActivity() != null) {
                            getActivity().setTitle("DIU Supervisor Solution");
                            view.findViewById(R.id.ivHome).setAnimation(AnimationUtils.loadAnimation(container.getContext(), R.anim.zoom_in));
                        }
                        break;

                    case IntentAndBundleKey.KEY_FRAGMENT_RULE:
                        view = inflater.inflate(R.layout.fragment_rule, container, false);

                        if (getActivity() != null) {
                            getActivity().setTitle("Rules and Regulations");
                            view.findViewById(R.id.ivDayShift).setAnimation(AnimationUtils.loadAnimation(container.getContext(), R.anim.zoom_in));
                        }
                        break;
                }
            }
        }

        if (view != null) {
            return view;
        } else {
            return inflater.inflate(R.layout.fragment_home, container, false);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}
