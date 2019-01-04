package net.agriblockchain.presentation.plot.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Activity;
import net.agriblockchain.util.DateFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlotActivitiesFragment extends Fragment implements PlotActivitiesContract.View {

    private static PlotActivitiesFragment instance;

    private static String plotId;

    private final PlotActivitiesContract.Presenter presenter = new PlotActivitiesPresenter(this);

    private TableLayout tblPlotActivities;

    public static PlotActivitiesFragment getInstance(final String plotId) {
        if (instance == null) {
            instance = new PlotActivitiesFragment();
            PlotActivitiesFragment.plotId = plotId;
        }
        return instance;
    }

    public PlotActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plot_activities, container, false);

        tblPlotActivities = view.findViewById(R.id.tbl_plot_activities);

        presenter.getPlotActivities(plotId);

        return view;
    }

    @Override
    public void onPlotActivitiesReceived(@NotNull List<? extends Activity> activities) {
        for (final Activity activity : activities) {
            View activityView = getLayoutInflater().inflate(R.layout.table_row_4_button_1, null);

            final TextView txtDate = activityView.findViewById(R.id.txt_col_1);
            txtDate.setText(DateFormatter.INSTANCE.getFormattedDate(activity.getTime()));

            final TextView txtTime = activityView.findViewById(R.id.txt_col_2);
            txtTime.setText(DateFormatter.INSTANCE.getFormattedTime(activity.getTime()));

            final TextView txtActivity = activityView.findViewById(R.id.txt_col_3);
            txtActivity.setText(activity.getActivitytype());

            final Button btnMore = activityView.findViewById(R.id.btn_col_4);
            btnMore.setText("View More");
            btnMore.setOnClickListener(v -> {
                //todo apply conditions

            });

            tblPlotActivities.addView(activityView);
        }
    }

    @Override
    public void onError(@NotNull String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
