package net.agriblockchain.presentation.certification.comments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.agriblockchain.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CertificationBodyCommentsFragment extends Fragment {

    private static CertificationBodyCommentsFragment instance = null;

    private static String[] farmComments;
    private static String[] plotComments;

    public static CertificationBodyCommentsFragment getInstance(final String[] farmComments, final String[] plotComments) {
        if (instance == null) {
            instance = new CertificationBodyCommentsFragment();
            CertificationBodyCommentsFragment.farmComments = farmComments;
            CertificationBodyCommentsFragment.plotComments = plotComments;
        }

        return instance;
    }

    public CertificationBodyCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_certification_body_comments, container, false);
    }

}
