package net.agriblockchain.presentation.trace;

import net.agriblockchain.data.model.Trace;
import net.agriblockchain.presentation.BaseView;

interface TraceContract {

    interface View extends BaseView<Presenter> {
        void onTraceReceived(final Trace[] traces);

        void onError(final String errorMsg);
    }

    interface Presenter {
        void getTrace(String produceId);
    }
}
