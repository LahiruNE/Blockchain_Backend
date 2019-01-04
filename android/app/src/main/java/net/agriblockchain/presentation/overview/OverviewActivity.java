package net.agriblockchain.presentation.overview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Product;
import net.agriblockchain.presentation.certification.comments.CertificationBodyCommentsFragment;
import net.agriblockchain.presentation.certification.details.CertificationDetailsFragment;
import net.agriblockchain.presentation.farm.FarmDetailsFragment;
import net.agriblockchain.presentation.ph.PHFragment;
import net.agriblockchain.presentation.plot.activities.PlotActivitiesFragment;
import net.agriblockchain.presentation.plot.details.PlotDetailsFragment;
import net.agriblockchain.presentation.trace.TraceFragment;
import net.agriblockchain.util.ProductDataHolder;

public class OverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OverviewContract.View {

    private OverviewContract.Presenter presenter;
    private ProductDataHolder dataHolder = ProductDataHolder.Companion.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        loadFragment(item.getItemId());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(final int id) {
        final Fragment fragment = getFragment(id);

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    private Fragment getFragment(int id) {
        Fragment fragment = null;
        if (id == R.id.nav_plot_details) {
            fragment = PlotDetailsFragment.getInstance(dataHolder.getPlot());
        } else if (id == R.id.nav_farm_details) {
            fragment = FarmDetailsFragment.getInstance(dataHolder.getPlot());
        } else if (id == R.id.nav_certification_details) {
            fragment = CertificationDetailsFragment.getInstance(dataHolder.getCertification());
        } else if (id == R.id.nav_product_path) {
            fragment = TraceFragment.getInstance(dataHolder.getProductPath());  //todo needs to check what is the content of the string path array
        } else if (id == R.id.nav_plot_activities) {
            fragment = PlotActivitiesFragment.getInstance(dataHolder.getPlot());
        } /*else if (id == R.id.nav_certification_body_comments) {
            fragment = CertificationBodyCommentsFragment.getInstance(dataHolder.sdgvdfh, dataHolder.getPlotCertificationComments());
        } else if (id == R.id.nav_ph_reading) {
            fragment = PHFragment.getInstance(dataHolder.getPlotPhReadings());
        }*/
        return fragment;
    }

}
