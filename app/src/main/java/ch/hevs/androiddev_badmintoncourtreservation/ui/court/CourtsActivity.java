package ch.hevs.androiddev_badmintoncourtreservation.ui.court;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.hevs.androiddev_badmintoncourtreservation.R;
import ch.hevs.androiddev_badmintoncourtreservation.adapter.CourtsRecyclerAdapter;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.hevs.androiddev_badmintoncourtreservation.ui.BaseActivity;
import ch.hevs.androiddev_badmintoncourtreservation.ui.reservation.CourtReservationActivity;
import ch.hevs.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import ch.hevs.androiddev_badmintoncourtreservation.viewmodel.court.CourtListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class CourtsActivity extends BaseActivity {

    private static final String TAG = "CourtsActivity";

    private CourtsRecyclerAdapter<CourtEntity> adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private CourtListViewModel listViewModel;
    private List<CourtEntity> courts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_courts, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        setTitle(R.string.courtsActivity_homePage);

        recyclerView = findViewById(R.id.CourtRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CourtsRecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Access the ReservationsActivity
                Log.d(TAG, "clicked on position: " + position);
                Log.d(TAG, "clicked on court: " + courts.get(position).getCourtsName());
                Intent intent = new Intent(CourtsActivity.this, CourtReservationActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("courtId", courts.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                //Access the EditCourtActivity
                Log.d(TAG, "long clicked on position: " + position);
                Log.d(TAG, "long clicked on court: " + courts.get(position).getCourtsName());
                Intent intent = new Intent(CourtsActivity.this, EditCourtActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("courtId", courts.get(position).getId());
                startActivity(intent);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        fab = findViewById(R.id.floatingActionButtonCourts);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(CourtsActivity.this, EditCourtActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        CourtListViewModel.Factory factory = new CourtListViewModel.Factory(getApplication());
        listViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(CourtListViewModel.class);
        listViewModel.getCourts().observe(this, courtEntities -> {
            if(courtEntities != null){
                courts = courtEntities;
                adapter.setData(courts);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        /*
        The activity has to be finished manually in order to guarantee the navigation hierarchy working.
        */
        finish();
        return super.onNavigationItemSelected(item);
    }

}