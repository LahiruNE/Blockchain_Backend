package net.agriblockchain.presentation.certification.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Certification;

/**
 * A simple {@link Fragment} subclass.
 */
public class CertificationDetailsFragment extends Fragment {

    private static CertificationDetailsFragment instance;

    private static Certification certification;

    public static CertificationDetailsFragment getInstance(final Certification certification) {
        if (instance == null) {
            instance = new CertificationDetailsFragment();
            CertificationDetailsFragment.certification = certification;
        }

        return instance;
    }

    public CertificationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_certification_details, container, false);
    }

}
