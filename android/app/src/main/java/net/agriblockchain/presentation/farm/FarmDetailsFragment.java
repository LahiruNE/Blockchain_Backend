package net.agriblockchain.presentation.farm;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Farm;

import org.jetbrains.annotations.NotNull;

public class FarmDetailsFragment extends Fragment implements FarmDetailsContract.View {

    private static FarmDetailsFragment instance;

    private final FarmDetailsContract.Presenter presenter = new FarmDetailsPresenter(this);

    private TextView txtFarmLocation;
    private TextView txtFarmOwner;
    private TextView txtOwnerAddress;
    private TextView txtNorth;
    private TextView txtEast;
    private TextView txtSouth;
    private TextView txtWest;

    public static FarmDetailsFragment getInstance(final String plotId) {
        if (instance == null) {
            instance = new FarmDetailsFragment();
            Bundle args = new Bundle();
            args.putString("plotId", plotId);
            instance.setArguments(args);
        }
        return instance;
    }

    public FarmDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_farm_details, container, false);

        txtFarmLocation = view.findViewById(R.id.txt_farm_location);
        txtFarmOwner = view.findViewById(R.id.txt_farm_owner);
        txtOwnerAddress = view.findViewById(R.id.txt_owner_address);
        txtNorth = view.findViewById(R.id.txt_north);
        txtEast = view.findViewById(R.id.txt_east);
        txtSouth = view.findViewById(R.id.txt_south);
        txtWest = view.findViewById(R.id.txt_west);

        return view;
    }

    @Override
    public void onFarmDetailsReceived(@NotNull Farm farm) {
        txtFarmLocation.setText(farm.getFarmLocation());
        txtNorth.setText(farm.getNearFactories().getNorth());
        txtEast.setText(farm.getNearFactories().getEast());
        txtSouth.setText(farm.getNearFactories().getSouth());
        txtWest.setText(farm.getNearFactories().getWest());
    }

    @Override
    public void onError(@NotNull String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
