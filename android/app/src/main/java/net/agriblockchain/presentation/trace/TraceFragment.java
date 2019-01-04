package net.agriblockchain.presentation.trace;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Stakeholder;
import net.agriblockchain.data.model.StakeholderType;
import net.agriblockchain.data.model.Trace;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TraceFragment extends Fragment implements TraceContract.View {

    private static TraceFragment instance;

    private static Trace[] path;

    private LinearLayout linearLayout;
    private TraceContract.Presenter presenter;

    public static TraceFragment getInstance(final Trace[] path) {
        if (instance == null) {
            instance = new TraceFragment();
            TraceFragment.path = path;
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace, container, false);

        presenter = new TracePresenter(this);

//        generateTestData();

        onTraceReceived(path);

        return view;
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void generateTestData() {
        Trace[] traces = new Trace[5];

        Trace trace0 = new Trace();
        trace0.setAuthperson(new Stakeholder());
        trace0.getAuthperson().setType(StakeholderType.FARMER.value);
        trace0.getAuthperson().setName("Farmer");
        traces[0] = trace0;
        Trace trace1 = new Trace();
        trace1.setAuthperson(new Stakeholder());
        trace1.getAuthperson().setType(StakeholderType.DISTRIBUTION.value);
        trace1.getAuthperson().setName("Distribution");
        traces[1] = trace1;
        Trace trace2 = new Trace();
        trace2.setAuthperson(new Stakeholder());
        trace2.getAuthperson().setType(StakeholderType.PACKAGING.value);
        trace2.getAuthperson().setName("Packaging");
        traces[2] = trace2;
        Trace trace3 = new Trace();
        trace3.setAuthperson(new Stakeholder());
        trace3.getAuthperson().setType(StakeholderType.WAREHOUSE.value);
        trace3.getAuthperson().setName("Warehouse");
        traces[3] = trace3;
        Trace trace4 = new Trace();
        trace4.setAuthperson(new Stakeholder());
        trace4.getAuthperson().setType(StakeholderType.RETAIL.value);
        trace4.getAuthperson().setName("Retail");
        traces[4] = trace4;

        onTraceReceived(traces);
    }

    @SuppressLint("InflateParams")
    @Override
    public void onTraceReceived(final Trace[] traces) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;

        View view = null;
        for (Trace trace : traces) {
            view = inflater.inflate(R.layout.list_item, null);

            ImageView imgSymbol = view.findViewById(R.id.thumb_view);
            TextView txtName = view.findViewById(R.id.txt_stakeholder_name);
            int resId;
            if (trace.getAuthperson().getType().equals(StakeholderType.FARMER.value)) {
                resId = R.drawable.farmer;
            } else if (trace.getAuthperson().getType().equals(StakeholderType.DISTRIBUTION.value)) {
                resId = R.drawable.distribution;
            } else if (trace.getAuthperson().getType().equals(StakeholderType.PACKAGING.value)) {
                resId = R.drawable.packaging;
            } else if (trace.getAuthperson().getType().equals(StakeholderType.WAREHOUSE.value)) {
                resId = R.drawable.warehouse;
            } else if (trace.getAuthperson().getType().equals(StakeholderType.RETAIL.value)) {
                resId = R.drawable.retail;
            } else {
                resId = R.drawable.down_arrow;
            }
            imgSymbol.setImageDrawable(requireContext().getDrawable(resId));

            txtName.setText(trace.getAuthperson().getName());
            linearLayout.addView(view);
        }

        if (view != null) {
            ImageView imgArrow = view.findViewById(R.id.img_arrow);
            imgArrow.setVisibility(View.GONE);
        }
    }

}
