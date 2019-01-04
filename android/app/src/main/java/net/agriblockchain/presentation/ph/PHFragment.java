package net.agriblockchain.presentation.ph;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.agriblockchain.R;
import net.agriblockchain.data.model.PHReading;
import net.agriblockchain.util.DateFormatter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PHFragment extends Fragment {

    private static PHFragment instance;

    private static PHReading[] phReadings;

    public static PHFragment getInstance(final PHReading[] phReadings) {
        if (instance == null) {
            instance = new PHFragment();
            PHFragment.phReadings = phReadings;
        }
        return instance;
    }

    public PHFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_ph, container, false);
        final TableLayout tblPhReading = view.findViewById(R.id.tbl_ph_readings);

        for (final PHReading phReading : phReadings) {
            View phView = inflater.inflate(R.layout.table_row_2, container);

            final TextView txtReadingTime = phView.findViewById(R.id.txt_col_1);
            txtReadingTime.setText(DateFormatter.INSTANCE.getFormattedDateTime(phReading.getReadingTime()));

            final TextView txtPHValue = phView.findViewById(R.id.txt_col_2);
            txtPHValue.setText(String.valueOf(phReading.getPh()));

            tblPhReading.addView(phView);
        }

        return view;
    }

}
