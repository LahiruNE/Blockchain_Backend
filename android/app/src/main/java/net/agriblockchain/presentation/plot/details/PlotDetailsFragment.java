package net.agriblockchain.presentation.plot.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Plot;
import net.agriblockchain.util.DateFormatter;

import org.jetbrains.annotations.NotNull;

public class PlotDetailsFragment extends Fragment implements PlotDetailsContract.View {

    private TextView txtCultivationStartDate;
    private TextView txtPluckedDate;
    private TextView txtExtent;
    private TextView txtNorth;
    private TextView txtEast;
    private TextView txtSouth;
    private TextView txtWest;

    private final PlotDetailsContract.Presenter presenter = new PlotDetailsPresenter(this);
    private String plotId;

    private static PlotDetailsFragment instance;

    public static PlotDetailsFragment getInstance(final String plotId) {
        if (instance == null) {
            instance = new PlotDetailsFragment();
            Bundle args = new Bundle();
            args.putString("plotId", plotId);
            instance.setArguments(args);
        }
        return instance;
    }

    public PlotDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plot_details, container, false);

        txtCultivationStartDate = view.findViewById(R.id.txt_cultivation_start_date);
        txtPluckedDate = view.findViewById(R.id.txt_plucked_date);
        txtExtent = view.findViewById(R.id.txt_extent);
        txtNorth = view.findViewById(R.id.txt_north);
        txtEast = view.findViewById(R.id.txt_east);
        txtSouth = view.findViewById(R.id.txt_south);
        txtWest = view.findViewById(R.id.txt_west);

        presenter.getPlotDetails(getArguments().getString("plotId"));

        return view;
    }

    @Override
    public void onPlotDetailsReceived(@NotNull Plot plot) {
        txtCultivationStartDate.setText(DateFormatter.INSTANCE.getFormattedDateTime(plot.getCultivationStartDate()));
        txtPluckedDate.setText(DateFormatter.INSTANCE.getFormattedDateTime(plot.getSeededDate()));
        txtExtent.setText(String.valueOf(plot.getExtent()));
        txtNorth.setText(plot.getCloserPlots().getNorth());
        txtEast.setText(plot.getCloserPlots().getEast());
        txtSouth.setText(plot.getCloserPlots().getSouth());
        txtWest.setText(plot.getCloserPlots().getWest());
    }

    @Override
    public void onError(@NotNull String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT);
    }
}
